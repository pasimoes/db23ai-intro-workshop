+++
title = 'Getting Started'
date = 2024-09-23T10:41:24Z
description = 'Oracle Database 23ai AI & Dev for Data Quick Start Workshop'
keywords = 'oracle database 23ai ai development genai rag vector search'
draft = false
geekdocBreadcrumb = false
+++

<!--
Copyright (c) 2023, 2024, Oracle and/or its affiliates.
Licensed under the Universal Permissive License v1.0 as shown at http://oss.oracle.com/licenses/upl.
-->

Welcome to the **Oracle Database 23ai** AI & Dev for Data Quick Start Workshop! This workshop covers some of the new features and enhancements in Oracle's long-term support release, version 23ai. Please note, this lab provides a high-level overview of some of Oracle Database 23ai’s new features. This is a small set of the 300+ new features in the database. For a comprehensive workshop, please visit the [23ai New Features LiveLab](https://livelabs.oracle.com/pls/apex/r/dbpm/livelabs/view-workshop?wid=3950).

## About Oracle Database 23ai

Oracle Database 23ai is the current long term support release version of the database. This means that it is the suggested version for all enterprise workloads by Oracle Corporation. Oracle Database 23ai is the newest release, combining all the features developed over the past 40 years, similar to the previous long-term support release, version 19c, and the innovation release, version 21c. If you're interested in the new features from 21c, bookmark this lab for later.

The Oracle Database is what is called a 'converged' database. This means the database supports multiple different data types like Relational, JSON, XML, Graph, Vector, and more. Converged also means it has the ability to run all sorts of workloads, from Transactional to IoT to Analytical to Machine Learning and more. It also can also handle a number of different development paradigm, including Microservices, Events, REST, SaaS, and CI/CD, (to name a few). All of this functionality can be used right 'out of the box'. All while still benefiting from Oracles leading performance, security, scalability and availability.

Oracle Database was built on 3 key themes.

- AI for Data
- Dev for Data
- Mission Critical for Data

### AI for Data

AI for Data brings in a new era for the Oracle Database. The goal of AI in the database is to help developers add AI functionality to all their applications in the easiest way possible, as well as adding GenAI capabilities to all of our products to help improve the productivity of developers, DBAs, and data analysts. AI Vector Search is part of Oracle Database 23ai and is available at no additional cost in Enterprise Edition, Standard Edition 2, Database Free, and all Oracle Database Cloud services.

Check out the new AI Vector Search lab for an introduction to some of the new AI capabilities in the Oracle Database.

### Dev for Data

Dev for Data in Oracle Database 23ai makes it easier than ever to develop using the Oracle Database. Oracle now offers multiple free versions of the database (built on the same code as the paid version) like Oracle Database Free, Oracle Free Autonomous Database on the cloud, and Oracle Free Autonomous Database on-premises. In addition, new features like JSON-Relational Duality Views allow you to build your applications using BOTH relational and JSON in the same app. Property Graphs allow you to build and model relations (like those found in the real world) in a new and simple way through the database's built-in support for property graph views and the ability to query them using the new SQL/PGQ standard, plus tons more features.

Check out the Developer section below to try some of the new features offered in Oracle Database 23ai.

### Mission Critical for Data

Oracle Database focuses on providing the highest availability for companies and some of the [world's most important systems](https://www.oracle.com/docs/tech/database/con8821-nyse.pdf). From functionality like Oracle Real Applications Clusters to Oracle Active DataGuard and much, much more, the Oracle Database allows you to safeguard your critical systems against unforeseen downtime.

Check out the High Availability section for an in-depth explanation of some of the new features in Oracle Database 23ai.

If you are currently using Oracle Database 19c or 21c, you can directly upgrade to Oracle Database 23ai. All prior versions should upgrade to 19c first, then 23ai.

Check out this lab for free access to two databases and a hands-on guide to get some experience with upgrading from different versions and architectures like non-PDB to PDB: [Hitchhiker's Guide for Upgrading to Oracle Database 19c & Oracle Database 23ai](https://livelabs.oracle.com/pls/apex/dbpm/r/livelabs/view-workshop?wid=3943).

## Prerequisites

- Oracle Database 23ai incl. Oracle Database 23ai Free
- Java™ Development Kit (JDK)
  - We recommend Oracle JDK or GraalVM version 23.
- An Integrated Developer Environment (IDE)
  - Popular choices include IntelliJ IDEA, Visual Studio Code, or Eclipse, and many more.
  - For this workshop we will use Visual Studio Code.
- An interactive and batch query tool
  - Oracle SQL Developer Command Line ([SQLcl](https://docs.oracle.com/en/database/oracle/sql-developer-command-line/24.2/index.html))
  - [SQL*Plus Instant Client](https://docs.oracle.com/en/database/oracle/oracle-database/23/sqpug/SQL-Plus-quick-start.html#GUID-DCF33419-3BE4-4FC7-824E-D0CA0C0951D9)
  - [Oracle Developer Tools for VS Code (SQL and PLSQL)](https://docs.oracle.com/en/database/oracle/developer-tools-for-vscode/getting-started/gettingstarted.html)
- Container Runtime e.g. docker/podman (for running in a Container)
- Access to an Embedding and Chat Model:
  - API Keys for Third-Party Models (OpenAI)

## Oracle Database 23ai Free Installation

As previously mentioned Oracle Database 23ai makes it easier than ever to develop using the Oracle Database. Thus offers multiple free versions of the database (built on the same code as the paid version) and option to straightforward deployment from Cloud to container images. For this workshop, we will adopt the Oracle Database 23ai Free Container Image, which contains Oracle Database 23ai Free based on an Oracle Linux base image. Reference [Oracle Database 23ai (23.5.0.0) Free Container Image Documentation](https://container-registry.oracle.com/ords/f?p=113:4:5759255742203:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:1863,1863,Oracle%20Database%20Free,Oracle%20Database%20Free,1,0&cs=3c0O79B2sQoXhCvaAnkRgscp8Nv7PCQ4N-o99ahlTo902ul1cu4r0G9oyyF-yeQutEmuSoJaEphjVdmKrOCLnVA)

{{< hint type=[info] icon=gdoc_info_outline title="Same... but Different" >}}
Reference to `podman` commands, if applicable to your environment, can be substituted with `docker`.
{{< /hint >}}

1. Start the DB 23ai Container:

   ```bash
   podman run --name=db23aifree \
           -e ORACLE_PWD=<your database passwords> \
           --publish 1521:1521 \
           --detach \
           container-registry.oracle.com/database/free:latest
   ```

2. Connecting to the Oracle Database Free Container.

   After the Oracle Database indicates that the container has started, and the STATUS field shows (healthy), client applications can connect to the database.

3. Connecting from Within the Container.

   You can connect to the Oracle Database by running a SQL*Plus command from within the container using one of the following commands:

   ```cmd
   podman exec -it db23aifree sqlplus sys/<your_password>@FREE as sysdba

   podman exec -it db23aifree sqlplus system/<your_password>@FREE

   podman exec -it db23aifree sqlplus pdbadmin/<your_password>@FREEPDB1
   ```

## Learn More

- [Announcing Oracle Database 23ai : General Availability](https://blogs.oracle.com/database/post/oracle-23ai-now-generally-available)
- [Oracle Database Features and Licensing](https://apex.oracle.com/database-features/)
- [Oracle Database 23ai : Where to find information](https://blogs.oracle.com/database/post/oracle-database-23ai-where-to-find-more-information)
- [Free sandbox to practice upgrading to 23ai!](https://livelabs.oracle.com/pls/apex/dbpm/r/livelabs/view-workshop?wid=3943)

### Concepts

- **GenAI**: Powers the generation of text, images, or other data based on prompts using pre-trained **LLM**s.
- **RAG**: Enhances **LLM**s by retrieving relevant, real-time information from vector storage allowing models to provide up-to-date and accurate responses.
- **Vector Database**: A database, including Oracle Database 23ai, that can natively store and manage vector embeddings and handle the unstructured data they describe, such as documents, images, video, or audio.

## Need help?

We'd love to hear from you! You can contact us in the
[#oracle-db-microservices](https://oracledevs.slack.com/archives/C06L9CDGR6Z) channel in the
Oracle Developers slack workspace, or [open an issue in GitHub](https://github.com/pasimoes/db23ai-intro-workshop/issues/new).

## Acknowledgements

- Authors:
  - [Juarez Junior](https://www.linkedin.com/in/jujunior/), Sr. Principal Java Developer Evangelist.
  - [Killian Lynch](https://www.linkedin.com/in/killian-lynch/), Senior Product Manager.
  - [Paulo Alberto Simoes](https://www.linkedin.com/in/pasimoes/), Cloud Architect.
- Last Updated By/Date - Paulo Alberto Simoes, September 2024.
