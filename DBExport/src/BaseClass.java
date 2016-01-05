import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;



public class BaseClass {

    public static void main(String[] args) throws IOException {
        
        File file = new File("C:/dev/STSworkspace/DBExport/base2.txt");
        
        Connection connection = DBOpenHelper.connectArchiveDB();
        ArchiveDataSource archiveDBSource = new ArchiveDataSource();
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = "";
        while ((s = br.readLine()) != null){
            Scanner scan = new Scanner(s);
            scan.useDelimiter("%");
            Document doc=new Document();
            Placing place = new Placing();
            doc.setKind(scan.next().replaceAll("^\\s+","").replaceAll("\\s+$", ""));
            System.out.println("kind - "+doc.getKind());
            doc.setName(scan.next().replaceAll("^\\s+","").replaceAll("\\s+$", "")
                                .replaceAll("^\\d\\d", "").replaceAll("\\.","").replaceAll("^\\/", "").replaceAll("^\\d", "").replaceAll("^\\s+",""));
            System.out.println("name - "+doc.getName());
            doc.setType(scan.next().replaceAll("^\\s+","").replaceAll("\\s+$", ""));
            System.out.println("type - "+doc.getType());
            place.setRoom(scan.next().replaceAll("^\\s+","").replaceAll("\\s+$", ""));
            System.out.println("room - "+place.getRoom());
            place.setLevel(scan.next().replaceAll("\\s",""));
            System.out.println(place.getLevel());
            place.setNumber(scan.next().replaceAll("\\s",""));
            System.out.println(place.getNumber());
            doc.setPlace(place);
            try{
                Document addedDoc = archiveDBSource.addDoc(doc);
                System.out.println(addedDoc.getId());                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }
        

    }

}
