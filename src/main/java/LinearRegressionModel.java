import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LinearRegressionModel {

//  public static void main(String[] args) {
//    // Fetch the last 24-hour timestamp and CPU utilization data from MongoDB
//    String host = "127.0.0.1";
//    int port = 27017;
//    String databaseName = "mydatabase";
//    String collectionName = "serverinfo";
//
//    MongoDBConnector connector = new MongoDBConnector(host, port, databaseName, collectionName);
//    List<Double> timestampData = connector.getTimestamps();
//    List<Double> cpuUtilizationData = connector.getCPUtemperature();

    // Build a linear regression model

  public void buildRegressionModel(List<Double>  timestampData,List<Double> cpuUtilizationData){
    SimpleRegression regression = new SimpleRegression();
    for (int i = 0; i < timestampData.size(); i++) {
      regression.addData(timestampData.get(i), cpuUtilizationData.get(i));
    }

    // Print the regression equation (y = mx + c)
    double slope = regression.getSlope();
    double intercept = regression.getIntercept();
    System.out.println("Regression Equation: y = " + slope + "x + " + intercept);

    // Plot the CPU utilization against timestamp
    plotData(timestampData, cpuUtilizationData, regression);
  }

  private static void plotData(List<Double> xData, List<Double> yData, SimpleRegression regression) {
    XYSeries series = new XYSeries("CPU Utilization vs. Timestamp");
    for (int i = 0; i < xData.size(); i++) {
      series.add(xData.get(i), yData.get(i));
    }

    XYSeries regressionLine = new XYSeries("Regression Line");
    double minX = xData.get(0);
    double maxX = xData.get(xData.size() - 1);

    System.out.println(regression.predict(minX));
    regressionLine.add(minX, regression.predict(minX));
    regressionLine.add(maxX, regression.predict(maxX));

    XYDataset dataset = new XYSeriesCollection(series);
    ((XYSeriesCollection) dataset).addSeries(regressionLine);

    JFreeChart  plot = ChartFactory.createXYLineChart("CPU Utilization vs. Timestamp", "Timestamp", "CPU Utilization", dataset, PlotOrientation.VERTICAL, true, true, false);

    //ChartPanel chartPanel = new ChartPanel(plot);
    ChartFrame frame = new ChartFrame("Linear Regression", plot);
    frame.pack();
    frame.setVisible(true);
 }
}
