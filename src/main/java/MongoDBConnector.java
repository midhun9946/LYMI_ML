import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.bson.Document;

public class MongoDBConnector {

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public void setMongoClient(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  private MongoClient mongoClient;
  private MongoDatabase database;

  public MongoCollection<Document> getCollection() {
    return collection;
  }

  public void setCollection(MongoCollection<Document> collection) {
    this.collection = collection;
  }

  private MongoCollection<Document> collection;
  String collectionName = "serverinfo";

  private String host;
  private int port;
  private String databaseName;


  public MongoDBConnector(){
    Properties properties=new Properties();
    try (InputStream input = MongoDBConnector.class.getClassLoader().getResourceAsStream("Application.properties")) {
      if (input == null) {
        throw new RuntimeException("mongodb.properties file not found");
      }
      properties.load(input);
      host=properties.getProperty("mongodb.host");
      port=Integer.parseInt(properties.getProperty("mongodb.port"));
      databaseName = properties.getProperty("mongodb.database");
      mongoClient = new MongoClient(host, port);
      database = mongoClient.getDatabase(databaseName);
      collection = database.getCollection(collectionName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
