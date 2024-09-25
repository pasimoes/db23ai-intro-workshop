/*
  Copyright (c) 2024, Oracle and/or its affiliates.

  This software is dual-licensed to you under the Universal Permissive License
  (UPL) 1.0 as shown at https://oss.oracle.com/licenses/upl or Apache License
  2.0 as shown at http://www.apache.org/licenses/LICENSE-2.0. You may choose
  either license.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
/*
  Copyright (c) 2024, Oracle and/or its affiliates.

  This software is dual-licensed to you under the Universal Permissive License
  (UPL) 1.0 as shown at https://oss.oracle.com/licenses/upl or Apache License
  2.0 as shown at http://www.apache.org/licenses/LICENSE-2.0. You may choose
  either license.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.oracle.dev.jdbc.langchain4j;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class OracleDBUtils {

  // JDBC CONNECTION DETAILS
  private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521/FREEPDB1";
//  private static final String USERNAME = System.getenv("DB_23AI_USERNAME");
//  private static final String PASSWORD = System.getenv("DB_23AI_PASSWORD");

  private static final String USERNAME = "DEMO_USER";
  private static final String PASSWORD = "Orcl#2024";


  private OracleDBUtils(){
    throw new IllegalStateException("Oracle DB Utility class");
  }

  public static Connection getConnectionFromPooledDataSource()
      throws SQLException {
    // Create pool-enabled data source instance
    PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
    // set connection properties on the data source
    pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    pds.setURL(URL);
    pds.setUser(USERNAME);
    pds.setPassword(PASSWORD);
    // Configure pool properties with a Properties instance
    Properties prop = new Properties();
    prop.setProperty("oracle.jdbc.vectorDefaultGetObjectType", "String");
    pds.setConnectionProperties(prop);
    // Override any pool properties directly
    pds.setInitialPoolSize(10);
    // Get a database connection from the pool-enabled data source
    return pds.getConnection();
  }

  public static DataSource getPooledDataSource() throws SQLException {
    // Create pool-enabled data source instance
    PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
    // set connection properties on the data source
    pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    pds.setURL(URL);
    pds.setUser(USERNAME);
    pds.setPassword(PASSWORD);
    // Configure pool properties with a Properties instance
    Properties prop = new Properties();
    prop.setProperty("oracle.jdbc.vectorDefaultGetObjectType", "String");
    pds.setConnectionProperties(prop);
    // Override any pool properties directly
    pds.setInitialPoolSize(10);
    return pds;
  }

}
