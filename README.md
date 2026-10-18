# PoE Part 2 — QuickChat

A console-based Java application that registers and authenticates a user, then
supports composing, sending, disregarding, and storing chat messages. Built with
Maven and tested with JUnit 5 on Java 25.

## What's in this project

```
poe-part1/
├── pom.xml                              Maven build file (JUnit 5 dependency, test runner)
├── .github/workflows/maven.yml          Runs the tests automatically on every push (GitHub Actions)
├── src/main/java/Login.java             Registration and login validation
├── src/main/java/Main.java              Part 1 registration and login console app
├── src/main/java/ChatApp.java           Part 2 QuickChat console app
├── src/main/java/Message.java           Message validation, hashing, lifecycle, and storage
├── src/test/java/LoginTest.java         Registration and login tests
└── src/test/java/MessageTest.java       Message validation and lifecycle tests
```

## Message workflow

`ChatApp` requires registration and a successful login before opening QuickChat.
Users can enter a recipient number and a message of up to 250 characters, then
send, disregard, or store it. Sent messages can be listed in the current run;
stored messages are written to `storedMessages.json` in the working directory.

`Message` generates a 10-digit ID and a hash from the ID prefix, message number,
and first/last message words. Recipient numbers are checked using the existing
international phone-number validation in `Login`.

## Login logic

- **`checkUserName`**: returns `true` only if the username contains an underscore
  AND is 5 characters or fewer.
- **`checkPasswordComplexity`**: loops through every character of the password and
  flags whether it saw an uppercase letter, a digit, and a special character. Returns
  `true` only if the password is 8+ characters and all three flags are true.
- **`checkCellPhoneNumber`**: uses a regular expression (`^\+\d{1,3}\d{1,10}$`) that
  requires a leading `+` (the international code) followed by up to 10 more digits.
- **`registerUser`**: calls the three checks above in order (username → password →
  cell number) and returns the message for the first one that fails. If all three
  pass, it stores the user's details in memory and returns a success message.
- **`loginUser`**: compares the entered username/password against what was stored
  at registration and returns `true`/`false`.
- **`returnLoginStatus`**: turns that `true`/`false` into the welcome message or the
  "incorrect" message.

**One assumption made:** the brief's registration section only lists username,
password and cell number, but the login success message needs a first and last
name ("Welcome `<first name>`, `<last name>`..."). Since there's no field for that
elsewhere in the extract you shared, this solution also asks for first name and
last name during registration so the welcome message can be produced. Mention this
assumption out loud in your video — it shows you understood the requirement instead
of just copying code.

## What this program does

This is a console-based Java application (no GUI) that lets a user register
an account, log in, and then send messages through a simple chat menu.

**Part 1 — Registration and Login**
- Registers a user with a username, password, and cell number, each checked
  against specific formatting rules.
- Logs the user in with the same username and password.

**Part 2 — Sending Messages**
- Once logged in, the user sees a "Welcome to QuickChat" menu with three
  options: Send Messages, Show recently sent messages (still in development),
  and Quit.
- The user sets how many messages they want to send for the session up front.
- Each message gets a recipient cell number, message text (capped at 250
  characters), an auto-generated 10-digit message ID, and an auto-generated
  message hash.
- For each message, the user chooses to send it, disregard it, or store it
  for later (stored messages get written out to a JSON file).
- Once the session ends, the program shows the total number of messages sent
  and the full details of each one.

## Project structure

- `src/main/java/Login.java` — Part 1: validation, registration, and login logic
- `src/main/java/Main.java` — Part 1 console app
- `src/main/java/Message.java` — Part 2: message validation, hashing, sending/storing logic
- `src/main/java/ChatApp.java` — Part 2 console app (logs in via `Login`, then runs the chat menu)
- `src/test/java/LoginTest.java` — JUnit tests for Part 1
- `src/test/java/MessageTest.java` — JUnit tests for Part 2
- `pom.xml` — Maven build file, pulls in JUnit
- `.github/workflows/maven.yml` — runs all tests automatically on every push

## Assumptions I made

**Part 1:** The brief only mentions capturing a username, password, and cell
number during registration, but the login success message needs to say
"Welcome `<first name>`, `<last name>`". Since there's nowhere else in the
brief that says where those come from, I added first name and last name as
extra fields captured during registration.

**Part 2:** A few things in the brief weren't fully spelled out, so here's how
I read them:
- The brief's overview section shows the message hash example as
  `00:0:HITHANKS`, but the actual test data later in the brief expects
  `00:0:HITONIGHT` for the dinner message. I built the hash to match the
  test data, since that's what's explicitly marked.
- `returnTotalMessages()` only counts messages the user actually chose to
  **send** — ones that were stored or disregarded don't add to that count.
- I didn't use an external JSON library for `storeMessage()` — I built the
  JSON structure manually, since the brief says to research a way to do it
  rather than naming a specific tool, and it's simpler to explain and defend
  in the video.
- `checkRecipientCell()` reuses `Login.checkCellPhoneNumber()` from Part 1
  rather than duplicating the same regex, since the brief specifically hints
  at reusing the method and tests from the last task.

## How I tested it

I wrote a JUnit test for every piece of test data given in both briefs —
the correct/incorrect username, password, and cell number cases, the message
hash example, the message length boundary, and all three send/disregard/store
paths. I used a GitHub Actions workflow so every test runs automatically on
every push instead of me having to remember to run `mvn test` myself.

## How to run it

1. Open the project folder in an IDE that supports Maven (I used VS Code with
   the Java Extension Pack).
2. Run `Main.java` for Part 1, or `ChatApp.java` for Part 2 (it includes
   login, so you register and log in first either way).
3. Run `mvn test`, or use the Testing panel in the IDE, to run the JUnit tests.

## Reference

The regex used in `checkCellPhoneNumber` follows the general pattern syntax
described in the Oracle Java `Pattern` class documentation:
https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
