import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner user_input = new Scanner(System.in);
        System.out.println("File to read : ");
        String filename = user_input.nextLine();
        System.out.println("User wants to read " + filename);
        System.out.println("parser keyword file : ");
        String keywordFile = user_input.nextLine();
        System.out.println("Keyword file is " + keywordFile);
        System.out.println("CSV name for results : ");
        String csvName = user_input.nextLine();
        System.out.println("new CSV name for results : " + csvName);

        //grunt work
        //vegan categorization
        Categories.keywordList(keywordFile);
        System.out.println("Key words to ding: " + Categories.h);
        System.out.println("");

        //big file
        Categories.filter(filename);

        //create csv
        CSVcreator.makeIntoCSV(csvName, Categories.entreeID, Categories.values);
        System.out.println("\nDone with creating csv. ");

    }
}
