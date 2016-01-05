import java.sql.*;
import java.util.ArrayList;

/**
 * Created by konstr on 17.12.2015.
 */
public class ArchiveDataSource {

    private Connection connection;

    public ArchiveDataSource(){
        connection = DBOpenHelper.connectArchiveDB();
        if(connection == null){
            System.out.println("connection not successful!");
            System.exit(1);
        }
    }

    public boolean dbConnected(){
        try {
            return !connection.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }   

    public Document addDoc(Document doc) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "INSERT into ARCHIVE (Name, Type, Kind, Place, Level, Number) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doc.getName());
            //System.out.println("name -"+doc.getName());
            preparedStatement.setString(2, doc.getType());
            //System.out.println("type - "+doc.getType());
            preparedStatement.setString(3, doc.getKind());
            //System.out.println("kind - "+doc.getKind());
            preparedStatement.setString(4, doc.getPlace().getRoom());
            //System.out.println("place room - "+doc.getPlace().getRoom());
            preparedStatement.setString(5, doc.getPlace().getLevel());
            //System.out.println("level - "+doc.getPlace().getLevel());
            preparedStatement.setString(6, doc.getPlace().getNumber());
            //System.out.println("number - "+doc.getPlace().getNumber());
            int affected = preparedStatement.executeUpdate();
            if(affected==1){
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int id = resultSet.getInt(1);
                doc.setId(id);
                return doc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


   
}
