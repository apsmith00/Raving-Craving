import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import static java.lang.Boolean.*;

public class Categories {

    static HashSet<String> h = new HashSet<String>();
    static ArrayList<String> entreeID = new ArrayList<>();
    static ArrayList<String> values = new ArrayList<>();
    static boolean dinged = FALSE;
    static int bad_line = 0;

    //these are the words to ding for vegans
    //add words to the hashset h
    public static void keywordList(String keywords){
        try {
            File file = new File(keywords);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                for(String tempStr : tempArr) {
                    //do something here
                    h.add(tempStr);
                    //System.out.println("added " + tempStr);
                }
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // from online
    // https://www.baeldung.com/java-check-string-number
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void filter(String csvFile) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null) {
                System.out.println("**************************************************************************");
                //System.out.println("\nThis is the line : " + line);
                tempArr = line.split(",");
                //String tempArrString = Arrays.toString(tempArr);
                System.out.println("This is the tempArr : " + Arrays.toString(tempArr));
                //System.out.println("This is the size of tempArr : " + tempArr.length);

                //look at the entree line
                for(int i=0; i<tempArr.length; i++) {
                    System.out.print("\nIteration " + i + " : " + tempArr[i]);

                    //add entreeID to array
                    if (i == 0) {

                        String potentialID = tempArr[i];

                        //if is an integer
                        if (isNumeric(potentialID)) {
                            entreeID.add(tempArr[i]);
                        }
                        else {
                            //if null, this is a bad line
                            entreeID.add("NO_VAL");
                            bad_line = 1;
                        }
                        System.out.println("\nDone with the entreeID");
                    }

                    //if this is not a bad line
                    if (bad_line==0) {
                        //parse the line
                        String[] parsedLine = tempArr[i].split(" ");
                        System.out.println("\nparsed line for " + tempArr[i] + " : " + Arrays.toString(parsedLine));

                        // iterate through the parsed line
                        for (int j = 0; j < parsedLine.length; j++) {
//                        if(dinged){
//                            break;
//                        }
                            //format the word so inconsistencies are gone
                            String word = parsedLine[j].toLowerCase(Locale.ROOT);
                            //System.out.println("Formatted word : " + word);

                            System.out.println("Checking for " + word);
                            //no good for value
                            if (h.contains(word)) {
                                System.out.println("FOUND " + word + " in H");
                                dinged = TRUE;
                                break;
                            } else {
                                //good for value
                                System.out.println("did not find " + word + " in H");
                                dinged = FALSE;
                            }

//                        System.out.println(parsedLine[j] + " Dinged word? : " + dinged);
                        }
                        //System.out.println("Dinged entree? : " + dinged);
                        if (dinged) {
                            break;
                        }
                    }
                    else{
                        System.out.println("\nThis was a bad line = " + Arrays.toString(tempArr));
                        break;
                    }
                }

                //if not a bad line
                if (bad_line == 0) {
                    if (dinged) {
                        //this entree is not wanted in the values
                        values.add("0");
                        System.out.println("\n0 was added to the array");
                        //reset value of dinged
                        dinged = FALSE;
                    } else {
                        //this entree is wanted in the values
                        values.add("1");
                        System.out.println("\n1 was added to the array");
                    }
                }
                else{
                    values.add("NO_VAL");
                    System.out.println("added 999 to the database");
                    bad_line = 0;
                }
                //System.out.println("\nThis is the end of the iteration " + Arrays.toString(tempArr));
            }
            br.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.out.println("**********\nmain() for testing and debugging\n**********\n");
        keywordList("vegan.txt");
        System.out.println("Key words to ding: " + h);
        System.out.println("");
        //small file
        filter("debug.csv");
        System.out.println("These are the entreeIDs : " + entreeID);
        System.out.println("This is values : " + values);
    }
}
