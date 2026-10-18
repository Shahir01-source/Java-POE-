import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MessageTest {

    @AfterEach
    public void tearDown() {
        Message.resetForTesting();
    }

    @Test
    public void testMessageLength_Success() {
        String message = "Hi Mike, can you join us for dinner tonight?";
        assertEquals("Message ready to send.", Message.checkMessageLength(message));
    }

    @Test
    public void testMessageLength_Failure() {
        String longMessage = "a".repeat(260);
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expected, Message.checkMessageLength(longMessage));
    }

    @Test
    public void testMessageLength_NullInput() {
        assertEquals("Message cannot be null.", Message.checkMessageLength(null));
    }

    @Test
    public void testRecipientCell_CorrectlyFormatted() {
        assertEquals("Cell phone number successfully captured.",
                Message.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientCell_IncorrectlyFormatted() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                        + "an international code. Please correct the number and try again.",
                Message.checkRecipientCell("08575975889"));
    }

    @Test
    public void testMessageHash_TestCase1() {
        String hash = Message.createMessageHash("0012345678", 0,
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageHash_LoopsOverMultipleMessages() {
        String[] ids = {"0012345678", "1198765432", "2255566778"};
        String[] texts = {
                "Hi Mike, can you join us for dinner tonight?",
                "Hi Keegan, did you receive the payment?",
                "Please call me back as soon as possible"
        };
        String[] expected = {"00:0:HITONIGHT", "11:1:HIPAYMENT", "22:2:PLEASEPOSSIBLE"};

        for (int i = 0; i < texts.length; i++) {
            assertEquals(expected[i], Message.createMessageHash(ids[i], i, texts[i]));
        }
    }

    @Test
    public void testMessageID_Generated() {
        String id = Message.generateMessageId();
        assertEquals(10, id.length());
    }

    @Test
    public void testCheckMessageID_ValidLength() {
        assertTrue(Message.checkMessageID("1234567890"));
    }

    @Test
    public void testCheckMessageID_TooLong() {
        assertTrue(!Message.checkMessageID("12345678901"));
    }

    @Test
    public void testSentMessage_Send() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    public void testSentMessage_CannotProcessTwice() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message.sentMessage(1);

        assertEquals("Message has already been processed.", message.sentMessage(1));
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testSentMessage_Disregard() {
        Message message = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
    }

    @Test
    public void testSentMessage_Store() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    public void testReturnTotalMessages() {
        Message first = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        first.sentMessage(1);

        Message second = new Message("+27718693002", "Hi Keegan, did you receive the payment?");
        second.sentMessage(2);

        Message third = new Message("+27718693002", "See you soon!");
        third.sentMessage(1);

        assertEquals(2, Message.returnTotalMessages());
    }
}