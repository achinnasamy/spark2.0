import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;

public class StructuredStreamingSocket {


    public static void main(String args[]) {


        SparkSession sparkSession = SparkSession
                .builder()
                .appName("SocketJOB")
                .master("local[*]")
                .getOrCreate();



        Dataset<Row> ds = sparkSession.readStream().format("socket")
                                    .option("host","localhost")
                                    .option("port", "1234").load();

        ds.createOrReplaceTempView("DATA_TABLE");
        Dataset<Row> ds1 = sparkSession.sql("SELECT * FROM DATA_TABLE");

        StreamingQuery query = ds1.writeStream().outputMode("append")
                                    .format("console").start();


        query.awaitTermination();



    }
}
