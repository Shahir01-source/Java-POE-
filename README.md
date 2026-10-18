# PROG5121 – PoE: Registration, Login, and Sending Messages

**Student:** Shahir Ahmed Khan 
**Student Number:** ST10526182
**Module:** PROG5121

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
