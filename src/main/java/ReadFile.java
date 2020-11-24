import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    public static void main (String[] args) {
        StringBuilder allVulnerabilities = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File("./src/main/java/vulnerabilities/TestDataBase.txt"));
            while (scanner.hasNextLine()) {
                allVulnerabilities.append(scanner.nextLine());
                allVulnerabilities.append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String str  = allVulnerabilities.toString();
        List<String> allVulns = Arrays.asList(str.split("\n"));

        for (int i = 0; i < allVulns.size(); i++) {
            List<String> oneLine = Arrays.asList(allVulns.get(i).split(" "));
            System.out.println(oneLine.get(1));
        }
    }
}
