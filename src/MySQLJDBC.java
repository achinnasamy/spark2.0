import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class MySQLJDBC {

    public static void main(String args[]) {

        SparkSession sparkSession = SparkSession
                .builder()
                .appName("JDBCJob")
                .master("local[*]")
                .getOrCreate();


        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "rooter123");


        Dataset<Row> ds = sparkSession.read().jdbc("jdbc:mysql:localhost:3307/amp_mysql",
                                    "book_details",
                                         connectionProperties);


        ds.show();



    }


}
