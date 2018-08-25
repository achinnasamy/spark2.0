import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL {

    public static void main(String args[]) {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("CustomPartitionerJOB")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df = sparkSession.read()
                                        .option("header","true")
                                        .csv("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");



        //df.show();
        //df.printSchema();

        //df.select("aua", "sa").show();

        //df.select(df.col("aua").plus(1)).show();
        df.select(df.col("aua").gt(34000)).show();

    }

}
