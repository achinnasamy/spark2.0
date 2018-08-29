import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class DStreaming {

    public static void main(String args[]) throws InterruptedException  {


        SparkSession sparkSession = SparkSession
                .builder()
                .appName("DStreamJOB")
                .master("local[*]")
                .getOrCreate();


        JavaStreamingContext jssc= new JavaStreamingContext(new JavaSparkContext(sparkSession.sparkContext()),
                Durations.milliseconds(1000));

        JavaReceiverInputDStream<String> inStream = jssc.socketTextStream("localhost", 9999);

        inStream.print();
        jssc.start();
        jssc.awaitTermination();



    }

}
