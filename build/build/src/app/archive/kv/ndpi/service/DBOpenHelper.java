package app.archive.kv.ndpi.service;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by konstr on 17.12.2015.
 */
public class DBOpenHelper {

    public static Connection connectArchiveDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:realArchive.sqlite");
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
