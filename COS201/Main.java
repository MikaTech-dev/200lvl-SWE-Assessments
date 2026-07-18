import java.util.Scanner;

public class Main {
    private static CourseManager manager = new CourseManager();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "courses.txt";

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("  Student Course Management System    (By: Mikatech-dev)  ");
        System.out.println("=========================================================");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice (1-7): ");
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    manager.displayAllCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    System.out.println("Total Units: " + manager.computeTotalUnits());
                    break;
                case 5:
                    manager.saveToFile(FILE_NAME);
                    break;
                case 6:
                    manager.loadFromFile(FILE_NAME);
                    break;
                case 7:
                    System.out.println("Exiting Program. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 7.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
    }

    private static void addCourse() {
        System.out.print("Enter Course Code (e.g. COS201): ");
        // String processing: trim and uppercase
        String code = scanner.nextLine().trim().toUpperCase();
        if (code.isEmpty()) {
            System.out.println("Course code cannot be empty.");
            return;
        }

        System.out.print("Enter Course Title: ");
        // String processing: trim
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Course title cannot be empty.");
            return;
        }

        int units = getIntInput("Enter Course Units: ");
        if (units <= 0) {
            System.out.println("Units must be a positive integer.");
            return;
        }

        manager.addCourse(new Course(code, title, units));
    }

    private static void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = scanner.nextLine().trim().toUpperCase();
        Course result = manager.searchCourse(code);
        if (result != null) {
            System.out.println("\nCourse Found:");
            System.out.println("Code: " + result.getCourseCode());
            System.out.println("Title: " + result.getCourseTitle());
            System.out.println("Units: " + result.getUnits());
        } else {
            System.out.println("Course with code '" + code + "' not found.");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                // Exception handling for invalid numbers
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
