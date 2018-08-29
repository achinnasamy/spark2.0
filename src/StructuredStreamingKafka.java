import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;

public class StructuredStreamingKafka {


    public static void main(String args[]) {


        SparkSession sparkSession = SparkSession
                .builder()
                .appName("KafkaJOB")
                .master("local[*]")
                .getOrCreate();


        Dataset<Row> df = sparkSession
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "ETMS-TOPIC")
                .load();

        StreamingQuery query = df.writeStream().outputMode("append")
                .format("console").start();


        query.awaitTermination();


    }


}
