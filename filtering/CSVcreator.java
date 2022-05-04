import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVcreator {

    public static void makeIntoCSV(String csvName, ArrayList<String> entreeIDs, ArrayList<String> values) throws IOException {
        FileWriter writer = new FileWriter(csvName);

        System.out.println("Size : " + entreeIDs.size());
        System.out.println("Entree IDs : " + entreeIDs);
        System.out.println("Values : " + values);

        for(int i=0; i<entreeIDs.size(); i++){
            writer.write(entreeIDs.get(i));
            writer.write(",");
            writer.write(values.get(i));
            writer.write("\n");
            //System.out.println("Added : " + entreeIDs.get(i) + " , " + values.get(i));
        }

        writer.close();

    }

    public static void main(String[] args) throws IOException {
        System.out.println("**********\nmain() for testing and debugging\n**********\n");
        makeIntoCSV("test.csv", Categories.entreeID, Categories.values);
        System.out.println("Done with creating csv. ");

    }
}
