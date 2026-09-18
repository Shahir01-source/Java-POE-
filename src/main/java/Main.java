import java.util.Scanner;

/**
 * Main.java
 *
 * Console entry point for Part 1 - Registration and login feature.
 * No GUI is used, as required by the brief (no JOptionPane).
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Welcome! Please read all the instructions before you begin. ===");
        System.out.println("This program lets you register an account and then log in with it.\n");

        boolean registered = false;

        // ---- Registration ----
        while (!registered) {
            System.out.println("--- Registration ---");

            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter a username (must contain '_' and be no more than 5 characters): ");
            String username = scanner.nextLine();

            System.out.print("Enter a password (min 8 chars, 1 capital letter, 1 number, 1 special character): ");
            String password = scanner.nextLine();

            System.out.print("Enter your cell number (with international code, e.g. +27838968976): ");
            String cellNumber = scanner.nextLine();

            String result = login.registerUser(firstName, lastName, username, password, cellNumber);
            System.out.println(result + "\n");

            if (result.contains("registered successfully")) {
                registered = true;
            } else {
                System.out.println("Please try registering again.\n");
            }
        }

        // ---- Login ----
        System.out.println("--- Login ---");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loginSuccess));

        scanner.close();
    }
}
