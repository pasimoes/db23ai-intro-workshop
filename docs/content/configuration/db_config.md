+++
title = "Database Configuration"
date = 2024-09-10T13:57:37Z
draft = false
+++

<!--
Copyright (c) 2023, 2024, Oracle and/or its affiliates.
Licensed under the Universal Permissive License v1.0 as shown at http://oss.oracle.com/licenses/upl.
-->

## Configuration

## Oracle Database 23ai Free Installation

As previously mentioned Oracle Database 23ai makes it easier than ever to develop using the Oracle Database. Thus offers multiple free versions of the database (built on the same code as the paid version) and option to straightforward deployment from Cloud to container images.

For this workshop, we will adopt the Oracle Database 23ai Free Container Image, which contains Oracle Database 23ai Free based on an Oracle Linux base image. Reference [Oracle Database 23ai (23.5.0.0) Free Container Image Documentation](https://container-registry.oracle.com/ords/f?p=113:4:5759255742203:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:1863,1863,Oracle%20Database%20Free,Oracle%20Database%20Free,1,0&cs=3c0O79B2sQoXhCvaAnkRgscp8Nv7PCQ4N-o99ahlTo902ul1cu4r0G9oyyF-yeQutEmuSoJaEphjVdmKrOCLnVA)

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

   {{< hint type=[tip] icon=gdoc_info_outline title="..on Mac Books with ARM chips" >}}
   On the 16th of September 2024, Oracle released Oracle Database 23ai (23.5) Free on Linux for ARM, downloadable as an Oracle Linux 8 RPM file at <https://www.oracle.com/database/free/get-started/>. At the moment we published this workshop, the ARM image was not available at `container-registry.oracle.com` but we will use an image provided by [Gerald Venzl](https://www.linkedin.com/in/geraldvenzl/), VP Developer Initiatives, Oracle Database.

   ```bash
   podman run --name=db23aifree \
           -e ORACLE_PASSWORD=<your database passwords> \
           --publish 1521:1521 \
           --detach \
           ghcr.io/gvenzl/oracle-free:23.5-full
   ```

   {{< /hint >}}

2. Connecting to the Oracle Database Free Container.

   After the Oracle Database indicates that the container has started, and the STATUS field shows (healthy), client applications can connect to the database.

3. Connecting from Within the Container.

   You can connect to the Oracle Database by running a SQL*Plus command from within the container using one of the following commands:

   ```cmd
   podman exec -it db23aifree sqlplus sys/<your_password>@FREE as sysdba

   podman exec -it db23aifree sqlplus system/<your_password>@FREE

   podman exec -it db23aifree sqlplus pdbadmin/<your_password>@FREEPDB1
   ```

### Connecting to Oracle Database

There are many ways to interact with Oracle Database, on this workshop we listed the three interactive sql query tools described below. We chose the VS Code Oracle Dev Tools for this workshop.

- An interactive and batch query tool
  - Oracle SQL Developer Command Line ([SQLcl](https://docs.oracle.com/en/database/oracle/sql-developer-command-line/24.2/index.html))
  - [SQL*Plus Instant Client](https://docs.oracle.com/en/database/oracle/oracle-database/23/sqpug/SQL-Plus-quick-start.html#GUID-DCF33419-3BE4-4FC7-824E-D0CA0C0951D9)
  - [Oracle Developer Tools for VS Code (SQL and PLSQL)](https://docs.oracle.com/en/database/oracle/developer-tools-for-vscode/getting-started/gettingstarted.html)

1. Connecting using Oracle Developer Tools for VS Code

   We will connecting using Host Name/IP Address and Service Name.

   - To connect from Oracle Database Explorer, click the plus (+) sign.
   - A connection dialog will open. In the **Connection Type** dropdown, select **Basic (Host, Port, Service Name)**.
   - Enter the database hostname or IP Address, port number, and service name.
   - Select the database role from the **Role** drop down list.
   - Enter the username and password.
   - If you wish to use a different schema than the default schema associated with your username, check the Show more options checkbox and select the schema name from the Current Schema dropdown.
   - Provide a connection name to be used to reference this connection in Database Explorer and elsewhere.
   - Click the **Create Connection** button.

   ![Database Connection... ](../images/vscode-oracledb-connection.png)

2. Check Database Version

   Provided that everything was configured properly, you can run a query to validate it all. Copy the sample query below:

   ```sql
   SELECT BANNER_FULL FROM V$VERSION;
   ```

   Check the results and look for the BANNER_FULL column in the resulting record. You should see a result similar to the one below, which confirms you’re connected to an instance of Oracle Database 23ai.

   ![Database version... ](../images/vscode-oracledb-version.png)

### Create User for Workshop tasks

Click with the right button on the Database connection name to open the menu, and then click on `Open SQL Worksheet`.

```sql
CREATE USER demo_user identified BY `<your_password>` QUOTA UNLIMITED ON USERS;
```
CREATE USER demo_user identified BY Orcl#2024 QUOTA UNLIMITED ON USERS;;

```sql
GRANT DB_DEVELOPER_ROLE TO demo_user;  
GRANT RESOURCE, CONNECT, UNLIMITED tablespace TO demo_user;
GRANT CREATE SESSION TO demo_user;  
GRANT SELECT ANY TABLE ON SCHEMA demo_user TO demo_user;
```
