package app.archive.kv.ndpi.service;

import app.archive.kv.ndpi.domain.Document;
import app.archive.kv.ndpi.domain.Placing;

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


    public ArrayList<Document> findDocInDB (String book, String type, String kind) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Document> list = new ArrayList<>();
        String query = "SELECT * FROM ARCHIVE WHERE Name LIKE '%"+book +"%'"+ " AND Type = ? AND Kind = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, kind);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Document doc = getDocumentFromRS(resultSet);
                list.add(doc);
            }
            if(list.isEmpty()){
                System.out.println("Empty");
                return null;
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ArrayList<Document> findAllDocs(){
        String query = "SELECT id, Name, Type, Kind, Place, Level, Number FROM ARCHIVE";
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Document> list = new ArrayList<>();
        try {
            statement = connection.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                Document doc = getDocumentFromRS(rs);
                list.add(doc);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            preparedStatement.setString(2, doc.getType());
            preparedStatement.setString(3, doc.getKind());
            preparedStatement.setString(4, doc.getPlace().getRoom());
            preparedStatement.setString(5, doc.getPlace().getLevel());
            preparedStatement.setString(6, doc.getPlace().getNumber());
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


    public boolean editDocument(Document doc){
        if(doc==null){
            return false;
        }
        PreparedStatement preparedStatement = null;
        String query = "UPDATE ARCHIVE SET Name = ?,Type = ?, Kind = ?," +
                " Place = ?, Level = ?, Number = ? WHERE Id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, doc.getName());
            preparedStatement.setString(2, doc.getType());
            preparedStatement.setString(3, doc.getKind());
            preparedStatement.setString(4, doc.getPlace().getRoom());
            preparedStatement.setString(5, doc.getPlace().getLevel());
            preparedStatement.setString(6, doc.getPlace().getNumber());
            preparedStatement.setInt(7, doc.getId());
            int result = preparedStatement.executeUpdate();
            if(result==1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Document getDocumentFromRS(ResultSet rs) throws SQLException {
        Document doc = new Document();
        doc.setId(rs.getInt("Id"));
        doc.setName(rs.getString("Name"));
        doc.setKind(rs.getString("Kind"));
        doc.setType(rs.getString("Type"));
        Placing place = new Placing();
        place.setRoom(rs.getString("Place"));
        place.setLevel(rs.getString("Level"));
        place.setNumber(rs.getString("Number"));
        doc.setPlace(place);
        return doc;
    }

    public boolean deleteDocument(int id) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM ARCHIVE WHERE Id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if(result==1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
