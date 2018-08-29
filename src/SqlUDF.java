import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

public class SqlUDF {

    public static void main(String args[]) {

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("UDFJob")
                .master("local[*]")
                .getOrCreate();



        Dataset<Row> df = sparkSession.read()
                .option("header","true")
                .csv("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/candidate.csv");


        sparkSession.udf().register("EmailUDF", new EmailUDF(), DataTypes.StringType);


        df.createOrReplaceTempView("CANDIDATE_TABLE");

        sparkSession.sql("SELECT EmailUDF(Email) from CANDIDATE_TABLE").show(500);
    }


}


class EmailUDF implements UDF1<String, String> {
    @Override
    public String call(String input) throws Exception {

        if (input.contains("@"))
            return input.trim().replace("@","").replace("gmail.com", "");
        else
            return "";
    }
}