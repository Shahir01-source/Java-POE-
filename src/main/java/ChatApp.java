import java.util.Scanner;

/**
 * Console entry point for Part 2 message sending.
 */
public class ChatApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        boolean registered = false;
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
            registered = result.contains("registered successfully");
        }

        System.out.println("--- Login ---");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loginSuccess));

        if (!loginSuccess) {
            System.out.println("You must log in successfully before you can send messages. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to send this session? ");
        int messageLimit = Integer.parseInt(scanner.nextLine().trim());
        int messagesCreated = 0;

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messagesCreated >= messageLimit) {
                        System.out.println("You've already entered the " + messageLimit
                                + " message(s) you asked for this session.");
                        break;
                    }
                    messagesCreated++;
                    composeMessage(scanner);
                    break;

                case "2":
                    System.out.println(Message.printMessages());
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Please enter 1, 2 or 3.");
            }
        }

        System.out.println("\nTotal messages sent this session: " + Message.returnTotalMessages());
        System.out.println(Message.printMessages());
        scanner.close();
    }

    private static void composeMessage(Scanner scanner) {
        String recipient;
        while (true) {
            System.out.print("Enter the recipient's cell number (with country code): ");
            recipient = scanner.nextLine();
            String result = Message.checkRecipientCell(recipient);
            System.out.println(result);
            if (result.startsWith("Cell phone number successfully")) {
                break;
            }
        }

        String text;
        while (true) {
            System.out.print("Enter your message (250 characters or fewer): ");
            text = scanner.nextLine();
            String result = Message.checkMessageLength(text);
            System.out.println(result);
            if (result.equals("Message ready to send.")) {
                break;
            }
        }

        Message message = new Message(recipient, text);

        System.out.println("\nMessage ID: " + message.getMessageId());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessageText());

        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());

        System.out.println(message.sentMessage(choice));
    }
}