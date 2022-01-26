import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// Javatehtävien alustusautomaatti!
// Automaatti lukee tehtävänannon läpi ja tunnistaa rivit, jotka alkavat yhdistelmällä numero-piste tai numero-numero-piste.
// Tämän jälkeen automaatti tekee uudet .java -tiedostot omaan tiedostosijaintiinsa, joihin se lisää
// tehtävänannon kommenttiriveiksi alkuun ja tekee public classin ja mainin. Kiikkikii!
// Jos tehtävänanto ei ala suoraan tehtävällä, tulostuu johdanto tiedostoon, jonka nimi on sama kuin tehtävien, mutta ilmman
// numeroa ja .java -päätettä.

// HUOMIO!
// -Ohjelmaa ei ole suunniteltu kursseille, joiden opettaja on psykopaatti ja antaa yli 99 tehtävää kerralla.
// -Tyhjä rivi tehtävänannossa kaataa ohjelman.
public class automaatti1 {
    public static void main(String[] args) throws IOException {
        String fileName         = "tehtavananto.txt";
        String newFileName      = "tehtava";
        String newFileFormat    = ".java";
        String comment          = "// ";

        int assCount = fileDivider(fileName, newFileName, newFileFormat, comment);
        createClassAndMain(newFileName, newFileFormat, assCount);
    }

// FILE READER AND DIVIDER
    public static int fileDivider(String fileName, String file, String format, String comment) throws FileNotFoundException {
        int assignmentCount = 0;
        try {
            final Scanner reader = new Scanner(new File(fileName));
            //String file = "tehtava_0";
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                char first = line.charAt(0);
                char second = line.charAt(1);
                char third = line.charAt(2);
                boolean isAssignment = false;
                boolean isAssignmentToo = false;
                
    // If line starts with a number-dot or number-number-dot -sequence, it's considered a new assignment
                if (Character.isDigit(first) && second == '.') {
                    isAssignment = true;
                }
                else if (Character.isDigit(first) && Character.isDigit(second) && third == '.') {
                    isAssignmentToo = true;
                }
    // In case the line is a new assignment, program writes it to a new file. Filename is based on the assignment number and '.java' is added
                if (isAssignment || isAssignmentToo) {
                    print("TEHTÄVÄ");
                    assignmentCount ++;
                    file = "tehtava_" + first;
                    if (isAssignmentToo) {
                        file = file + second;
                    }
                    file = file + format;

                    printFile(file, comment+ line);

                }
    // If line does not start with numbers and a dot, it will be printed in the file the program lastly opened
                else {
                    print("rivi");
                    printFile(file, comment + line);
                }
            }
        }
        catch (Exception e) {
            print("Jotain meni vikaan! Tarkasta tiedostonimi");
        }
        return assignmentCount;
    }


// CLASS AND MAIN CREATOR
    public static void createClassAndMain(String file, String format, int assCount) throws IOException {
        print("Kirjoitetaan " + assCount + " tehtävätiedostoa.");
        String className = "public class " + file + '_';
        String mainString = " {\n    public static void main(String[] args) {\n\n    }\n}";

        for(int i = 1; i < assCount +1; i++) {
            printFile(file + "_" + i + format , className + i + mainString);
        }
    }

// FILE WRITER
    public static void printFile(String file, String line) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(line);
            printWriter.close();
    }
// CONSOLE PRINTER
    public static void print(String string) {
        System.out.println(string);
    }
}