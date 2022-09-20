import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CSVRead {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = Driver.getConnection();
        if (connection != null) {
            System.out.println("JDBC:connection is taken..");

            String filepath = "C:\\Users\\shazz\\Desktop\\union_id.csv";
            int batchSize = 6000;
            String query = "UPDATE place_union SET union_name=?, latitude=?, longitude=? WHERE union_id=?"+";";

            PreparedStatement statement = connection.prepareStatement(query);
            BufferedReader lineReader = new BufferedReader(new FileReader(filepath));

         String lineText =null;
            int count = 0;
            lineReader.readLine();
            while ((lineText=lineReader.readLine())!= null) {
                String[] splitLine = lineText.split(",");

                int union_id= Integer.parseInt(splitLine[0]);
//                String union_name = splitLine[1];
//                String latitude = splitLine[2];
//                String longitude = splitLine[3];
                statement.setString(1, splitLine[1]);
                statement.setString(2, splitLine[2]);
                statement.setString(3, splitLine[3]);
                statement.setInt(4, union_id);
                if (count%batchSize==0){
                    statement.addBatch();
                    statement.executeBatch();
                    statement.executeUpdate();
                }
            }
            lineReader.close();

//            statement.executeBatch();
            connection.commit();
            connection.close();
            System.out.println("Data has been updated");

        }
    }
}
