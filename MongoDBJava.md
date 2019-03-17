MongoDB is the leading NoSQL database system which has become very popular for recent years due to its dynamic schema nature and advantages over big data like high performance, horizontal scalability, replication, etc. Unlike traditional relational database systems which provide JDBC-compliant drivers, MongoDB comes with its own non-JDBC driver called Mongo Java Driver. That means we cannot use JDBC API to interact with MongoDB from Java. Instead, we have to use its own Mongo Java Driver API.
 
1. Downloading Mongo Java Driver
Click here to download latest version of Mongo Java Driver (version 2.11.1 as of this writing). The JAR file name is mongo-java-driver-VERSION.jar (around 400KB). Copy the downloaded JAR file into your classpath.
Online API documentation for Mongo Java Driver can be found here.

2. Connecting  to MongoDB using MongoClient
The MongoClientclass is used to make a connection with a MongoDB server and perform database-related operations. Here are some examples:
Creating a MongoClient instance that connects to a default MongoDB server running on localhost and default port: 
`MongoClient mongoClient = new MongoClient();`
Connecting to a named MongoDB server listening on the default port (27017): 
`MongoClient mongoClient = new MongoClient("localhost");`
Or:
`MongoClient mongoClient = new MongoClient("db1.server.com");`
Connecting to a named MongoDB server listening on a specific port: 
`MongoClient mongoClient = new MongoClient("localhost", 27017);
`Or:
`MongoClient mongoClient = new MongoClient("db1.server.com", 27018);`
Connecting to a replica set of servers:
`List<ServerAddress> seeds = new ArrayList<ServerAddress>();
seeds.add(new ServerAddress("db1.server.com", 27017));
seeds.add(new ServerAddress("db2.server.com", 27018));
seeds.add(new ServerAddress("db3.server.com", 27019));`

`MongoClient mongoClient = new MongoClient(seeds);`
 
After the connection is established, we can obtain a database and make authentication (if the server is running in secure mode), for example:
`
MongoClient mongoClient = new MongoClient();
DB db = mongoClient.getDB("test");
 
char[] password = new char[] {'s', 'e', 'c', 'r', 'e', 't'};
boolean authenticated = db.authenticate("root", password);
 
if (authenticated) {
    System.out.println("Successfully logged in to MongoDB!");
} else {
    System.out.println("Invalid username/password");
}
`
By default, MongoDB server is running in trusted mode which doesn’t require authentication.
Let’s see a complete program:
`package net.codejava.mongodb;
 
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
 
import com.mongodb.DB;
import com.mongodb.MongoClient;
 
public class JavaMongoDBConnection {
 
    public static void main(String[] args) {
        try {
             
            MongoClient mongoClient = new MongoClient("localhost");
             
            List<String> databases = mongoClient.getDatabaseNames();
             
            for (String dbName : databases) {
                System.out.println("- Database: " + dbName);
                 
                DB db = mongoClient.getDB(dbName);
                 
                Set<String> collections = db.getCollectionNames();
                for (String colName : collections) {
                    System.out.println("\t + Collection: " + colName);
                }
            }
             
            mongoClient.close();
             
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
         
    }
}
`
This Java program connects to a MongoDB server running on localhost at default port, then lists all database names available on the server. For each database, it lists all collection names (a collection is equivalent to a table in relational database), and finally closes the connection. This program would produce the following output:
- Database: local
       + Collection: startup_log
- Database: mydb
       + Collection: system.indexes
       + Collection: things
- Database: test
       + Collection: system.indexes
       + Collection: test

3. Using MongoDB connection string URI
It’s also possible to use a String that represents a database connection URI to connect to the MongoDB server, for example:

`String dbURI = "mongodb://localhost";
MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));`

Syntax of the URI is as follows:
`mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]`
Here are some connection string URI examples:
Connecting to the MongoDB server running on localhost at the default port:
`mongodb://localhost`

Connecting to the admin database on a named MongoDB server db1.server.com running on port 27027 with user root and password secret:
`mongodb://root:secret@db1.server.com:27027`

Connecting to the users database on server db2.server.com:
`mongodb://db2.server.com/users`

Connecting to the products database on a named MongoDB server db3.server.com running on port 27027 with user tom and password secret:
`mongodb://tom:secret@db3.server.com:27027/products`

Connecting to a replica set of three servers:
`mongodb://db1.server.com,db2.server.com,db3.server.com`

See detailed information about MongoDB’s connection string URI.
