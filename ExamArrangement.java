import java.io.*;
import java.util.*;

public class ExamArrangement {

    private static final String STUDENTS_FILE = "students.txt";
    private static final String SEATING_FILE = "seating.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exam Seating Arrangement System\n");

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Allocate Seat");
            System.out.println("3. View Arrangement");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear input buffer

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    allocateSeat(scanner);
                    break;
                case 3:
                    viewArrangement();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println(); // empty line for spacing
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE, true))) {
            writer.write(rollNumber + "," + name);
            writer.newLine();
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error saving student: " + e.getMessage());
        }
    }

    private static void allocateSeat(Scanner scanner) {
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine();
        System.out.print("Enter seat number: ");
        String seatNumber = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEATING_FILE, true))) {
            writer.write(seatNumber + "," + rollNumber);
            writer.newLine();
            System.out.println("Seat allocated successfully.");
        } catch (IOException e) {
            System.out.println("Error allocating seat: " + e.getMessage());
        }
    }

    private static void viewArrangement() {
        Map<String, String> studentMap = new HashMap<>();

        // Load student data
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    studentMap.put(parts[0], parts[1]); // rollNumber -> name
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading students: " + e.getMessage());
            return;
        }

        // Load seating and display
        System.out.println("\nSeat Arrangement:");
        try (BufferedReader reader = new BufferedReader(new FileReader(SEATING_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String seatNumber = parts[0];
                    String rollNumber = parts[1];
                    String name = studentMap.getOrDefault(rollNumber, "Unknown");
                    System.out.println(
                            "Seat Number: " + seatNumber + " | Name: " + name + " | Roll Number: " + rollNumber);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading seating: " + e.getMessage());
        }
    }
}