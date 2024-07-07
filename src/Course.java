import java.nio.file.Path;
import java.util.*;

public class Course {
    private String courseName;
    private String instructorName;
    private int courseID;
    private ArrayList<String> studentUsernames;
    private ArrayList<String> courseList;
    private FileHandler fileHandler;
    private String coursesPath = Paths.coursePath + "course";
    Course(){
        fileHandler = new FileHandler(coursesPath);
    }
    Course (int id){
        fileHandler = new FileHandler(coursesPath+id);
        this.courseID = id;
        this.read();
    }

    public ArrayList<String> getStudentUsernames() {
        return studentUsernames;
    }
     public String getInstructorName(){
        return instructorName;
     }
    public String getCourseName() {
        return courseName;
    }

    public String getCourseInfo(){
        return "Course name = " + courseName + "\ninstructorName = " + instructorName
                + "\nStudentsnames " + this.getStudentUsernames();
     }
    private void read() {
        ArrayList<String> info = fileHandler.retrieve();
        this.courseName = info.get(0);
        this.instructorName = info.get(1);

        // Initialize studentUsernames
        this.studentUsernames = new ArrayList<>();

        if (info.size() > 2) {
            for (int i = 2; i < info.size(); i++) { // Start from 2 to skip name and instructor
                studentUsernames.add(info.get(i));
            }
        }
        courseList = fileHandler.retrieve(Paths.coursePath);
    }


    public void addStudentName(String studentName) {
        if (!studentUsernames.contains(studentName)) { // Check if student is already enrolled
            fileHandler.append(studentName);
            studentUsernames.add(studentName); // Update in-memory list
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Student is already enrolled in this course.");
        }
    }
    public ArrayList<String> getAllCourses(){
        return fileHandler.retrieve(Paths.coursePath);
    }
    public boolean exists() { // New method
        return fileHandler.fileExists(coursesPath + courseID);
    }
}
