- Step 1: Install or download Connector JAR from net.
- Step 2: Configure IDE to ADD EXTERNAL JAR(Right click on project-> Build Path -> Libraries -> Add External Jars..)
- Step 3: Coding
//This part to be done everytime to make a connection with database
- 1. Connect - Connection String server:port/db, userid,password...
- 2. Create Statement/ PrepareStatement/CallableStatement
- 3. Execute the statement
- 3A. Fetching data from ResultSet( only for Select )
- 4. Close the connection
