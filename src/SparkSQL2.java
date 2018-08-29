import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL2 {

    public static void main(String args[]) {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("SQLJob")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df = sparkSession.read()
                .option("header", "true")
                .text("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");


        df.show();
    }

}
