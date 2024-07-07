import java.io.File;
import java.util.*;

class Student extends Person{
    private String username;
    private String studentName;
    private String password;
    private ArrayList<Integer> grades;
    private FileHandler fileHandler;
    private ArrayList<String> studentInformation;
    private ArrayList<String> myCourses = null;
    Student(){

    }
    Student(String username){
        this.username = username;
        fileHandler = new FileHandler(Paths.studentPath+username);
        readStudentData();
    }

    Student(String username,String password){
        super.login(username,password,1);
        readStudentData();
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentName() {
        return studentName;
    }
    private void readStudentData(){
        ArrayList<String> info = fileHandler.retrieve();
        this.studentName = info.get(1);
        myCourses = new ArrayList<>();
        if (info.size()>1){
            for (int i = 2; i < info.size(); i++) {
                myCourses.add(info.get(i));
            }
        }
        /*
        hana123
        Hana Ahmad
        course1 CS50 90
        course3 TT 85
         */
    }
    public ArrayList<String> getAllCourses() {
        ArrayList<String> allCourses = fileHandler.retrieve(Paths.coursePath);
        return allCourses;
    }
    public ArrayList<String> getMyCourses() {
        ArrayList<String> courseNames = new ArrayList<>();
        for (String courseInfo : myCourses) { // Assuming 'myCourses' contains "course1 90", etc.
            String[] parts = courseInfo.split(" ");
            courseNames.add(parts[0]); // Extract and add only the course name
        }
        return courseNames;
    }
    public ArrayList<String> getMyGrades() {
        ArrayList<String> courseNames = new ArrayList<>();
        for (String courseInfo : myCourses) { // Assuming 'myCourses' contains "course1 90", etc.
            String[] parts = courseInfo.split(" ");
            courseNames.add(parts[1]); // Extract and add only the course name
        }
        return courseNames;
    }
    public void assignCourse(int courseNumber){
        fileHandler.append("\ncourse"+courseNumber+" 0");
    }

    public void updateUsername(String newUsername) {
        try {
            System.out.print("Enter your current username: ");
            String oldUsername = Main.scanner.nextLine();

            if (!oldUsername.equals(this.username)) {
                System.out.println("Incorrect username. Username update failed.");
                return;
            }

            String oldPath = Paths.studentPath + oldUsername;
            String newPath = Paths.studentPath + newUsername;

            if (fileHandler.fileExists(newPath)) {
                System.out.println("Username already exists. Please choose a different one.");
            } else {
                ArrayList<String> data = fileHandler.retrieve();

                // Update username in the data (usually the first line)
                if (!data.isEmpty()) {
                    data.set(0, newUsername);
                }

                fileHandler.setPath(newPath);
                fileHandler.writeFile(data);

                // Option 1: Delete old file if write is successful
                File oldFile = new File(oldPath);
                if (!oldFile.delete()) {
                    System.out.println("Warning: Unable to delete old file.");
                }

                // Option 2: Move file if your FileHandler supports it
                // fileHandler.moveFile(oldPath, newPath);

                this.username = newUsername;
                System.out.println("Username updated successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error updating username: " + e.getMessage());
        }
    }

    public void updatePassword(String newPassword) {
        try {
            List<String> data = fileHandler.retrieve();
            if (data.isEmpty()) {
                System.out.println("Error: Student data file is empty or could not be read.");
                return;
            }

            // 1. Get old password from the user
            System.out.print("Enter your old password: ");
            String oldPasswordInput = Main.scanner.nextLine();

            // 2. Check if the entered old password matches the stored one
            if (!oldPasswordInput.equals(data.get(0))) {
                System.out.println("Incorrect old password. Password update failed.");
                return;
            }

            // 3. Check if the new password is the same as the old one
            if (newPassword.equals(oldPasswordInput)) {
                System.out.println("New password is the same as the old password. No changes made.");
                return;
            }

            // 4. If passwords match, proceed with the update
            data.set(0, newPassword);
            fileHandler.writeFile(data);
            this.password = newPassword; // Update password in memory (optional)
            System.out.println("Password updated successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred while updating the password: " + e.getMessage());
        }
    }

    public boolean isValid() {
        return fileHandler != null;
    }
}
