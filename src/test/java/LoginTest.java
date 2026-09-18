import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LoginTest.java
 *
 * Unit tests using the exact test data given in the assignment brief.
 * Run with: mvn test
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    // ---------- Username checks (assertTrue / assertFalse) ----------

    @Test
    public void testUserNameCorrectlyFormatted() {
        // Contains an underscore and is no more than five characters long.
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUserNameIncorrectlyFormatted() {
        // Does not contain an underscore and is more than five characters long.
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // ---------- Password checks (assertTrue / assertFalse) ----------

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ---------- Cell phone number checks (assertTrue / assertFalse) ----------

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---------- Registration messages (assertEquals) ----------

    @Test
    public void testRegisterUser_UsernameIncorrectlyFormatted() {
        String expected = "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_PasswordIncorrectlyFormatted() {
        String expected = "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, "
                + "and a special character.";
        String actual = login.registerUser("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_CellNumberIncorrectlyFormatted() {
        String expected = "Cell phone number is incorrectly formatted or does not contain "
                + "international code; please correct the number and try again.";
        String actual = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_AllDetailsCorrect() {
        String actual = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(actual.contains("registered successfully"));
    }

    // ---------- Login checks (assertTrue / assertFalse) ----------

    @Test
    public void testLoginSuccessful() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"));
    }

    // ---------- Login status messages (assertEquals) ----------

    @Test
    public void testReturnLoginStatus_Success() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String expected = "Welcome Kyle, Smith it is great to see you again.";
        assertEquals(expected, login.returnLoginStatus(success));
    }

    @Test
    public void testReturnLoginStatus_Failed() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("kyl_1", "wrongPassword1!");
        String expected = "Username or password incorrect, please try again.";
        assertEquals(expected, login.returnLoginStatus(success));
    }
}
