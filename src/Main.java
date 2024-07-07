import java.util.*;

class Main {
    final static Scanner scanner = new Scanner(System.in);
    private static Student student;

    public static void main(String[] args) {
        mainMenu();
    }
    public static int loginSelection(int selection){
        System.out.println("Select a login type:");

        while (true) {
            System.out.print("Enter your selection: ");
            try {
                if (selection >= 1 && selection <= 3) {
                    return selection;
                } else {
                    System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                scanner.next();
            }
        }
    }
    static void mainMenu() {

        while(true){
            String[] studentActivities = {"Main Menu:\"", "1- Student Panel", "2- Instructor Panel","3- Admin Panel", "-1- Exit"};
            for (int i = 0; i < studentActivities.length; i++) {
                System.out.println(studentActivities[i]);
            }
            int selection = scanner.nextInt();
            if (selection==-1){
                System.out.println("Exiting system!");
                System.exit(0);
            }
            loginSelection(selection);
            switch (login(selection)) {
                case 1:
                    studentPanel();
                    break;
                case 2:
                    System.out.println("Instructor panel (not yet implemented)");
                    break;
                case 3:
                    System.out.println("Admin panel (not yet implemented)");
                    break;
                case -1:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    static int login(int selection) {
        while(true){
            System.out.println("Enter your username");
            String username = scanner.next();
            System.out.println("Enter your password");
            String password = scanner.next();
            switch (selection){
                case 1:
                    student = new Student();
                    if (student.login(username,password,selection)){
                        student = new Student(username);
                        return selection;
                    }
                default:
                    System.out.println("Invalid username or password. Please try again.");
            }
        }
    }

    static void studentPanel() {
        System.out.println("\nStudent Panel:");

        int choice;
//        Student student = new Student("hana"); // Replace with actual student data
        do {

            String[] studentActivities = {"1- Show all courses", "2- Show my courses","3- Show grades","4- Assign course","5- Update info", "-1- Sign out"};
            for (int i = 0; i < studentActivities.length; i++) {
                System.out.println(studentActivities[i]);
            }
            while (!scanner.hasNextInt()) { // Input validation loop
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showAvailableCourses();
                    break;
                case 2:
                    getStudentCourses();
                    break;
                case 3:
                    getStudentGrades();
                    break;
                case 4:
                    AssignCourse();
                case 5:
                    updateStudentInformation();
                case -1:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != -1);
    }

    public static void updateStudentInformation() {
        int selection = 0;
        do {

            System.out.println("1- Update username");
            System.out.println("2- Update password");
            System.out.println("-1- Go back");

            try {
                selection = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (selection) {
                    case 1:
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        student.updateUsername(newUsername);
                        break;
                    case 2:
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        student.updatePassword(newPassword);
                        break;
                    case -1:
                        System.out.println("Going back...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        } while (selection != -1);
    }


    private static void showAvailableCourses(){
        ArrayList<String> studentCourses = student.getMyCourses();
        Course course = new Course();
        ArrayList<String> allCourses = course.getAllCourses();
        for (String studentCourseName : studentCourses) {
            allCourses.remove(studentCourseName);
        }
        System.out.println("all courses:");
        for (String courseName : allCourses) {
            System.out.println(courseName);
        }
        System.out.println("------------------------------------------------------------------");
    }
    public static void getStudentGrades() {
        ArrayList<String> studentCourses = student.getMyCourses();
        ArrayList<String> studentGrades = student.getMyGrades();

        if (studentCourses.isEmpty()) {
            System.out.println("You have not registered for any courses yet.");
        } else {
            System.out.println("Your Grades:");
            for (int i = 0; i < studentCourses.size(); i++) {
                int grade = Integer.parseInt(studentGrades.get(i)); // Parse grade to an integer
                if (grade != 0) {  // Only print if grade is not 0
                    System.out.println(studentCourses.get(i) + " " + studentGrades.get(i));
                }
            }
        }
    }
    // ... (rest of the Main class code)

    public static void AssignCourse(){
        int selection;
        do {
            System.out.println("1- Display all courses");
            System.out.println("2- Assign by course number");
            System.out.println("-1- Go back");

            selection = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (selection) {
                case 1:
                    showAvailableCourses();
                    break;
                case 2:
                    System.out.print("Enter course number: ");
                    int courseNumber;

                    // Input validation loop for course number
                    while (true) {
                        if (scanner.hasNextInt()) {
                            courseNumber = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            Course course = new Course(courseNumber);
                            if (course.exists()) { // Check if course exists
                                student.assignCourse(courseNumber);
                                course.addStudentName(student.getStudentName());
                                System.out.println("Course assigned successfully!");
                                break; // Exit the loop
                            } else {
                                System.out.println("Course with number " + courseNumber + " not found. Try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next(); // Discard invalid input
                        }
                    }

                    break; // Exit switch
                case -1:
                    System.out.println("Going back...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (selection != -1);
    }

    public static void getStudentCourses(){
        ArrayList<String> studentCourses = student.getMyCourses();
        System.out.println("Your courses:");
        for (int i = 0; i < studentCourses.size(); i++) {
            System.out.println(studentCourses.get(i));
        }
    }
}

