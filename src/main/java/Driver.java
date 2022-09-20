import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Driver {
    private static final String DB_DRIVER_CLASS="driver.class.name";
    private static final String DB_USERNAME="db.username";
    private static final String DB_PASSWORD="db.password";
    private static final String DB_URL ="db.url";

    private static Connection connection = null;
    private static Properties properties = null;

    static{
        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            Class.forName(properties.getProperty(DB_DRIVER_CLASS));
            connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME) ,
                    properties.getProperty(DB_PASSWORD) );




        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}


//    public void updateData (BufferedReader bufferedReader, Connection sqlConnection){
//        String csvLine = null;
//        PreparedStatement preparedStatement = null;
//        String sqlString = "UPDATE items SET price = ?, rrp = ?, stock = ? WHERE departmentid = ? AND barcode = ?";
//        try {
//            preparedStatement = sqlConnection.prepareStatement(sqlString);
//            while ((csvLine = bufferedReader.readLine()) != null) {
//                String[] splitLine = csvLine.split(";");
//                preparedStatement.setBigDecimal(1, new BigDecimal(splitLine[4].trim()).setScale(2, RoundingMode.CEILING));
//                preparedStatement.setBigDecimal(2, new BigDecimal(splitLine[5].trim()).setScale(2, RoundingMode.CEILING));
//                preparedStatement.setInt(3, Integer.parseInt(splitLine[6].trim()));
//                preparedStatement.setString(4, splitLine[2].trim());
//                preparedStatement.setString(5, splitLine[8].trim());
//                preparedStatement.executeUpdate();
//            }
//        } catch (IOException | SQLException exc) {
//            System.out.println(exc.getMessage());
//        } finally {
//            try {
//                sqlConnection.commit();
//                preparedStatement.close();
//                sqlConnection.close();
//            } catch (SQLException exc) {
//                exc.printStackTrace();
//            }
//        }
//    }