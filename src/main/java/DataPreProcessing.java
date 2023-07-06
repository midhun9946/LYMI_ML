import java.time.LocalDateTime;
import java.util.List;


public class DataPreProcessing {
  public static void main(String[] args) {
    String host = "127.0.0.1";
    int port = 27017;
    String databaseName = "mydatabase";
    String collectionName = "serverinfo";

    MongoDBConnector connector = new MongoDBConnector(host, port, databaseName, collectionName);
    List<Double> cpuUtilization = connector.getCPUUtilization();
    List<Double> timestamps = connector.getTimestamps();
    List<Double> cpuTemperature=connector.getCPUtemperature();

    System.out.println(cpuUtilization);
    System.out.println(timestamps);
    System.out.println(cpuTemperature);

    LinearRegressionModel linearRegressionModel=new LinearRegressionModel();
    linearRegressionModel.buildRegressionModel(timestamps,cpuUtilization);
    // Perform data preprocessing or any other operations with the retrieved data

    connector.close();
  }
}
