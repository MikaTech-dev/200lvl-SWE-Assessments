import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses;

    public CourseManager() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course added successfully.");
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses recorded yet.");
            return;
        }
        System.out.println("---------------------------------------------------------");
        System.out.println(String.format("%-10s | %-30s | %s", "CODE", "TITLE", "UNITS"));
        System.out.println("---------------------------------------------------------");
        for (Course c : courses) {
            System.out.println(c.toString());
        }
        System.out.println("---------------------------------------------------------");
    }

    // Recursive search function as required by instructions
    public Course searchCourse(String code) {
        return searchCourseRecursive(code, 0);
    }

    private Course searchCourseRecursive(String code, int index) {
        if (index >= courses.size()) {
            return null; // Base case: not found
        }
        if (courses.get(index).getCourseCode().equalsIgnoreCase(code)) {
            return courses.get(index); // Base case: found
        }
        return searchCourseRecursive(code, index + 1); // Recursive call
    }

    public int computeTotalUnits() {
        int total = 0;
        for (Course c : courses) {
            total += c.getUnits();
        }
        return total;
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course c : courses) {
                writer.write(c.getCourseCode() + "," + c.getCourseTitle() + "," + c.getUnits());
                writer.newLine();
            }
            System.out.println("Courses saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No previous saved data found at '" + filename + "'.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            courses.clear(); // Clear existing to load fresh
            while ((line = reader.readLine()) != null) {
                // Limit split to 3 parts so title can contain commas if needed
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String code = parts[0];
                    String title = parts[1];
                    int units = Integer.parseInt(parts[2]);
                    courses.add(new Course(code, title, units));
                }
            }
            System.out.println("Courses loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing units from file data.");
        }
    }
}
