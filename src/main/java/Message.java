import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a chat message and its validation, lifecycle, and storage.
 */
public class Message {

    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final String STORAGE_FILE = "storedMessages.json";

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String status;

    public Message(String recipient, String messageText) {
        this.messageId = generateMessageId();
        this.messageNumber = messageCounter++;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageId, this.messageNumber, this.messageText);
        this.status = "Pending";
    }

    public static String generateMessageId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public static Boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public static String checkRecipientCell(String cellNumber) {
        boolean valid = new Login().checkCellPhoneNumber(cellNumber);
        if (valid) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    public static String checkMessageLength(String messageText) {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int over = messageText.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    public static String createMessageHash(String messageId, int messageNumber, String messageText) {
        String idPrefix = messageId.substring(0, Math.min(2, messageId.length()));
        String[] words = messageText.trim().split("\\s+");
        String firstWord = stripPunctuation(words[0]).toUpperCase();
        String lastWord = stripPunctuation(words[words.length - 1]).toUpperCase();
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    private static String stripPunctuation(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                status = "Sent";
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                status = "Disregarded";
                return "Press 0 to delete the message.";
            case 3:
                status = "Stored";
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder output = new StringBuilder();
        for (Message message : sentMessages) {
            output.append("Message ID: ").append(message.messageId).append("\n")
                    .append("Message Hash: ").append(message.messageHash).append("\n")
                    .append("Recipient: ").append(message.recipient).append("\n")
                    .append("Message: ").append(message.messageText).append("\n")
                    .append("------------------------\n");
        }
        return output.toString();
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message message = storedMessages.get(i);
            json.append("  {\n")
                    .append("    \"messageId\": \"").append(escape(message.messageId)).append("\",\n")
                    .append("    \"messageHash\": \"").append(escape(message.messageHash)).append("\",\n")
                    .append("    \"recipient\": \"").append(escape(message.recipient)).append("\",\n")
                    .append("    \"message\": \"").append(escape(message.messageText)).append("\"\n")
                    .append("  }");
            if (i < storedMessages.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");

        try {
            Files.writeString(Path.of(STORAGE_FILE), json.toString());
        } catch (IOException e) {
            System.out.println("Could not write to " + STORAGE_FILE + ": " + e.getMessage());
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String getMessageId() {
        return messageId;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getStatus() {
        return status;
    }

    static void resetForTesting() {
        messageCounter = 0;
        totalMessagesSent = 0;
        sentMessages.clear();
        storedMessages.clear();
    }
}