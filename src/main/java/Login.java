/**
 * Login.java
 *
 * Handles user registration and login for Part 1 of the PoE.
 *
 * Design note: the task brief only lists username, password and cell number
 * as the fields captured during registration, but the login success message
 * requires a first name and last name ("Welcome <first name>, <last name> ...").
 * Since the brief doesn't show where those are captured, this class also
 * collects first name and last name at registration time so that the login
 * welcome message can be produced. Mention this assumption in your video.
 *
 * This is a plain console-based, GUI-free class as required by the brief.
 */
public class Login {

    // Stored details for the single registered user (this PoE only requires
    // one user account to be registered and logged in at a time).
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    private boolean userRegistered = false;

    /**
     * Regex used to validate a South African style cell number entered with
     * an international country code, e.g. "+27838968976".
     * Structure: '+' then a 1-3 digit country code, then up to 10 further digits
     * for the subscriber number (matches the brief's "the number ... is no more
     * than ten characters long" once the country code is separated out).
     *
     * Regex syntax reference: Oracle Java Platform, Standard Edition
     * Pattern class documentation - https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
     */
    private static final String CELL_NUMBER_REGEX = "^\\+\\d{1,3}\\d{1,10}$";

    // ---------- Validation methods ----------

    /**
     * A username is valid if it contains an underscore and is no more than
     * five characters long.
     */
    public Boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * A password is valid if it is at least eight characters long and
     * contains at least one capital letter, one number and one special
     * character.
     */
    public Boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    /**
     * A cell number is valid if it contains the international country code
     * (a leading '+') followed by a number that is no more than ten
     * characters long.
     */
    public Boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        return cellNumber.matches(CELL_NUMBER_REGEX);
    }

    // ---------- Registration ----------

    /**
     * Attempts to register a new user. Checks the username, then the
     * password, then the cell number, in that order, and returns the
     * relevant message the moment one of them fails. If all three checks
     * pass, the user's details are stored and a success message is returned.
     */
    public String registerUser(String firstName, String lastName, String username,
                                String password, String cellNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, "
                    + "and a special character.";
        }

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain "
                    + "international code; please correct the number and try again.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.userRegistered = true;

        return "Username successfully captured. Password successfully captured. "
                + "Cell phone number successfully added. You have been registered successfully!";
    }

    // ---------- Login ----------

    /**
     * Verifies that the given username and password match the details
     * stored at registration.
     */
    public Boolean loginUser(String username, String password) {
        if (!userRegistered || username == null || password == null) {
            return false;
        }
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Returns the message to display after a login attempt.
     *
     * @param loginSuccess the result returned by loginUser()
     */
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
