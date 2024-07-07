import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Person {
    private static String username;
    private static String password;
    private FileHandler fileHandler;
    private static final String studentFilesPath = "src/Student/";
    Person(){
    }

    public boolean login(String username, String password,int selection) {
        String path = getPath(selection);
        System.out.println(path);
        File Directory = new File(path);
        File[] usersFiles = Directory.listFiles();
        ArrayList<String> userData = null;
        FileHandler fileHandler = null;

        if (usersFiles == null) {
            System.out.println("This user not found\uF8FF");
            return false;
        }

        for (File file : usersFiles) {
            if (file.getName().equals(username)) {
                path = path + username;
                System.out.println(path);
                fileHandler = new FileHandler(path);
                break;
            }
        }
        try {
            userData = fileHandler.retrieve();
            if (!userData.isEmpty() && Objects.equals(userData.get(0), password)) {
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println("it is false lesgoo bad");
        return false;
    }
    private static String getPath(int selection){
        switch (selection){
            case 1:
                return Paths.studentPath;
            default:
                break;
        }
        return null;
    }
    /*

    public static double minimumAverage(int[] nums) {
        //3194. Minimum Average of Smallest and Largest Elements
        //https://leetcode.com/problems/minimum-average-of-smallest-and-largest-elements/description/

        Arrays.sort(nums);
        double minAverage = nums[nums.length-1];
        for (int i = 0; i < nums.length/2; i++) {
            if ((double) (nums[i] + nums[nums.length - i - 1]) / 2 < minAverage)
                minAverage = (double) (nums[i] + nums[nums.length - i - 1]) / 2;
        }
        return minAverage;
    }
    */
}
