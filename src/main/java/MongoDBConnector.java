import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class MongoDBConnector {
  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> collection;

  public MongoDBConnector(String host, int port, String databaseName, String collectionName) {
    mongoClient = new MongoClient(host, port);
    database = mongoClient.getDatabase(databaseName);
    collection = database.getCollection(collectionName);
  }

  public List<Double> getCPUUtilization() {
    List<Double> cpuUtilization = new ArrayList<>();

    MongoCursor<Document> cursor = collection.find().iterator();
    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        double utilization = document.getDouble("cpuutilization_server");
        //System.out.println("Utilizatioon ::" +utilization);
        cpuUtilization.add(utilization);
      }
    } finally {
      cursor.close();
    }

    return cpuUtilization;
  }

  public List<Double> getTimestamps() {
    List<Double> timestamps = new ArrayList<>();

    MongoCursor<Document> cursor = collection.find().iterator();
    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        String timestamp = document.getString("timestamp");
        System.out.println("timestamp ::" + timestamp);
        String[] timeComponents=timestamp.split(" ");
        String[] minutesHour=timeComponents[1].split(":");
        Double time=Double.valueOf(minutesHour[0]+"."+minutesHour[1]);
        System.out.println("timeStamp in double ::" + time);
        timestamps.add(time);
      }
    } finally {
      cursor.close();
    }
    System.out.println(timestamps);
    return timestamps;
  }
  public List<Double> getCPUtemperature() {
  List<Double> cpuTemperatures=new ArrayList<>();
  MongoCursor<Document> cursor=collection.find().iterator();
  try {
    while (cursor.hasNext()){
      Document document=cursor.next();
      Double cpuTemperature=document.getDouble("cpu_temperature");
      cpuTemperatures.add(cpuTemperature);
    }
  }finally {
    cursor.close();
  }
  return cpuTemperatures;
  }

  public void close() {
    mongoClient.close();
  }


}
