import java.io.*;
import java.util.*;

public class FileHandler {
    private FileWriter fileWriter;
    private File file;
    private String path;

    public FileHandler(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void append(String text) {
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            fileWriter.append(text).append("\n");
        } catch (IOException e) {
            System.out.println("Cannot append to file: " + e.getMessage());
        }
    }

    public ArrayList<String> retrieve() {
        ArrayList<String> data = new ArrayList<>();
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                data.add(fileReader.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Exception reading file: " + e.getMessage());
        }
        return data;
    }
     public ArrayList<String> retrieve(String path) {
        File studentsDirectory = new File(path);
        File[] studentFiles = studentsDirectory.listFiles();

        if (studentFiles == null) {
            System.out.println("No users Found");
            return new ArrayList<>();
        }

        ArrayList<String> data = new ArrayList<>();
        for (File file : studentFiles) {
            data.add(file.getName()); // Directly get the file name
        }
        return data;
    }
    public boolean fileExists(String path) {
        File fileToCheck = new File(path);
        return fileToCheck.exists() && fileToCheck.isFile(); // Check existence and that it's a file
    }
    public void writeFile(List<String> data) {
        try (PrintWriter writer = new PrintWriter(path)) {
            for (String line : data) {
                writer.println(line); // Write each line with a newline character
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }
}
