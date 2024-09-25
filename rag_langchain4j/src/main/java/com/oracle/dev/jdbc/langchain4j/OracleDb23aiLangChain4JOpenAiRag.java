

package com.oracle.dev.jdbc.langchain4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;

public class OracleDb23aiLangChain4JOpenAiRag {

  private static final Logger logger = LoggerFactory
      .getLogger(OracleDb23aiLangChain4JOpenAiRag.class);

  public static void main(String[] args) throws SQLException {

    Path pdfFilePath = Paths.get(
        "/Users/pasimoes/Work/Oracle/Code/Workshops/db23ai-intro/rag_langchain4j/src/main/resource/JuarezBarbosaJunior.pdf");

    // Read and extract text from the PDF
    String pdfContent = extractTextFromPdf(pdfFilePath);

    if (pdfContent != null && !pdfContent.isEmpty()) {

      // Initialize the embedding model
      OpenAiEmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
          .apiKey(System.getenv("OPENAI_API_KEY"))
          .modelName("text-embedding-3-large").build();

      // Embedding model info
      logger.info("MODEL NAME:" + embeddingModel.modelName());
      System.out.println("MODEL NAME:" + embeddingModel.modelName());
      logger.info("MODEL DIMENSION:" + embeddingModel.dimension() + "\n");
      System.out.println("MODEL DIMENSION:" + embeddingModel.dimension() + "\n");

      // Initialize the Oracle embedding store
      OracleEmbeddingStore embeddingStore = new OracleEmbeddingStore(
          OracleDBUtils.getPooledDataSource(), "ORACLE_VECTOR_STORE",
          Integer.valueOf("3072"), Integer.valueOf("95"), OracleDistanceType.COSINE,
          OracleIndexType.IVF, Boolean.valueOf(true), Boolean.valueOf(true),
          Boolean.valueOf(true), Boolean.valueOf(true));

      // Initialize the Oracle embedding store ingestor
      OracleEmbeddingStoreIngestor ingestor = new OracleEmbeddingStoreIngestor(
          embeddingModel, embeddingStore);

      // Ingest the PDF content
      ingestor.ingest(pdfContent);

      // At last but not least RAG with OpenAI (gpt-4o) + Oracle Database 23ai
      RagWithOracleDatabase23ai(embeddingModel, embeddingStore);

    } else {
      logger.info("No content extracted from the PDF.");
    }

  }

  private static String extractTextFromPdf(Path filePath) {
    StringBuilder content = new StringBuilder();
    try {
      // Check if the file exists
      if (Files.exists(filePath)) {
        // Load the PDF document
        try (PDDocument document = PDDocument.load(filePath.toFile())) {
          // Instantiate PDFTextStripper to extract text
          PDFTextStripper pdfStripper = new PDFTextStripper();
          // Extract text from the PDF document
          content.append(pdfStripper.getText(document));
        }
      } else {
        logger
            .info("The specified file does not exist: " + filePath.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    logger.info("\n PDF CONTENT: \n" + content.toString());
    return content.toString();
  }

  private static void RagWithOracleDatabase23ai(
      OpenAiEmbeddingModel embeddingModel,
      OracleEmbeddingStore embeddingStore) {

    String name = "Juarez Barbosa Junior";

    Embedding queryEmbedding = embeddingModel.embed(name).content();

    EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest
        .builder().queryEmbedding(queryEmbedding).build();
    EmbeddingSearchResult<TextSegment> result = embeddingStore
        .search(embeddingSearchRequest);

    List<EmbeddingMatch<TextSegment>> embeddingMatch = result.matches();
    String information = embeddingMatch.getFirst().embedded().text();

    Prompt prompt = PromptTemplate.from("""
        Tell me about {{name}}?

        Use the following information to answer the question:
        {{information}}

        """).apply(Map.of("name", name, "information", information));

    // chat model
    ChatLanguageModel model = OpenAiChatModel.builder()
        .apiKey(System.getenv("OPENAI_API_KEY")).modelName("gpt-4o").build();

    String answer = model.generate(prompt.toUserMessage()).content().text();
    logger.info("\n ANSWER: \n" + answer);
    System.out.println("\n ANSWER: \n" + answer);
  }

}
