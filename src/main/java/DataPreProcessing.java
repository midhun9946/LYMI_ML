import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;


public class DataPreProcessing {
  MongoDBConnector connector = new MongoDBConnector();

  public static void main(String[] args) {
    DataPreProcessing dataPreProcessing = new DataPreProcessing();
    List<Double> cpuUtilization = dataPreProcessing.getCPUUtilization();
    List<Double> timestamps = dataPreProcessing.getTimestamps();
    int predictiveTimeStampSample = 10;
    LinearRegressionModel linearRegressionModel = new LinearRegressionModel();
    linearRegressionModel.buildRegressionModel(timestamps, cpuUtilization, predictiveTimeStampSample);
    dataPreProcessing.close();
  }

  public List<Double> getCPUUtilization() {
    List<Double> cpuUtilization = new ArrayList<>();

    MongoCursor<Document> cursor = connector.getCollection().find().iterator();
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

    MongoCursor<Document> cursor = connector.getCollection().find().iterator();
    ;
    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        String timestamp = document.getString("timestamp");
        System.out.println("timestamp ::" + timestamp);
        String[] timeComponents = timestamp.split(" ");
        String[] minutesHour = timeComponents[1].split(":");
        Double time = Double.valueOf(minutesHour[0] + "." + minutesHour[1]);
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
    List<Double> cpuTemperatures = new ArrayList<>();
    MongoCursor<Document> cursor = connector.getCollection().find().iterator();
    ;
    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        Double cpuTemperature = document.getDouble("cpu_temperature");
        cpuTemperatures.add(cpuTemperature);
      }
    } finally {
      cursor.close();
    }
    return cpuTemperatures;
  }
  public void close() {
    connector.getMongoClient().close();
  }
}
