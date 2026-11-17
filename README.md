# PROG5121 PoE: QuickChat

Student: Shahir Ahmed Khan
Student number: ST10526182

QuickChat is a console application written in Java (no GUI). A user registers, logs in, and then sends, disregards or stores messages. Part 3 adds arrays that hold every message, reads the stored messages back from a JSON file, and adds a "Stored Messages" menu.

## What each part does

Part 1 is registration and login. The username, password and cell number are each checked against the rules in the brief, and the user logs in with the same username and password.

Part 2 is sending messages. After logging in the user chooses how many messages to send. Each message gets a recipient, a text of up to 250 characters, a random 10 digit message ID and a message hash. The user then sends it, disregards it or stores it. Stored messages are written to `storedMessages.json`.

Part 3 is the arrays and the Stored Messages menu. The program fills five arrays while it runs: sent messages, disregarded messages, stored messages, message hashes and message IDs. When the app starts, the stored messages array is filled from `storedMessages.json`, so messages stored in an earlier run are still there. Main menu option 4 opens the Stored Messages menu:

- a: display the sender and recipient of all stored messages
- b: display the longest stored message
- c: search for a message ID and show the recipient and message
- d: search for all sent or stored messages to one recipient
- e: delete a stored message using its message hash
- f: display a report with the full details of all stored messages

## Project structure

- `src/main/java/Login.java`: registration and login checks
- `src/main/java/Main.java`: console app for Part 1
- `src/main/java/Message.java`: message checks, the five arrays, JSON reading and writing, and the Stored Messages operations
- `src/main/java/ChatApp.java`: console app for Parts 2 and 3 (log in first, then QuickChat)
- `src/test/java/LoginTest.java`: Part 1 tests
- `src/test/java/MessageTest.java`: Part 2 tests
- `src/test/java/StoredMessagesTest.java`: Part 3 tests
- `pom.xml`: Maven build file
- `.github/workflows/maven.yml`: runs all the tests on every push

## How the Part 3 code works

The arrays are `ArrayList` objects, because the number of messages is not known in advance. Nothing is hard-coded. A message is added to the sent, disregarded or stored array at the moment the user chooses that option, and its hash and ID are added to the hash and ID arrays.

`storeMessage()` rewrites the whole JSON file from the stored array each time. That keeps the file valid, and it is also how a deleted message disappears from the file.

`loadStoredMessages()` reads the file back into the stored array with a small JSON reader that I wrote for the shape of this file (a list of objects whose values are all strings). It follows the string and escape rules from the JSON specification, https://www.json.org/ and RFC 8259, https://www.rfc-editor.org/rfc/rfc8259. I did not use a library, so the project has no extra dependencies.

## Decisions I made where the brief was not clear

- The brief's test data labels message 4 as "Developer" and has a recipient without an international code. I treated it as a developer entry. It is created with `Message.developerMessage()`, which skips recipient validation, and I gave it the message ID `0838884567`, because the ID search test uses that number.
- The test data has message 4 as "It is dinner time !" but the expected output is "It is dinner time!". I used the text without the space.
- The ID search looks through the sent, disregarded and stored arrays, because the brief's test searches for message 4, which was sent.
- The recipient search covers sent and stored messages, as in the brief's test table.
- The menu says the report is for stored messages, but the test table says the report shows sent messages. `displayReport()` covers the stored messages, and `printMessages()` (main menu option 2) covers the sent messages. Both show the hash, recipient and message.
- The delete message in the brief leaves off the final full stop of the message, so the program does the same.
- The sender of a message is the cell number the logged-in user registered with.
- Deleting by hash removes the message from the stored array, takes its hash and ID out of the hash and ID arrays, and rewrites the JSON file.

## How I tested it

The unit tests use the test data from the briefs. `StoredMessagesTest` creates the five test messages from Part 3 and checks the five arrays, each menu operation, the report, and reading the JSON file back (including a message with quotes in it). The tests that save messages write to a temporary file, so they never change the real `storedMessages.json`. The GitHub Actions workflow runs every test on each push.

## How to run it

1. Open the project folder in an IDE that supports Maven (I used VS Code with the Java Extension Pack).
2. Run `ChatApp.java` for Parts 2 and 3, or `Main.java` for Part 1.
3. Run `mvn test`, or use the Testing panel in the IDE, to run the tests.

## Reference

The regular expression in `checkCellPhoneNumber` uses the pattern syntax from the Oracle Java `Pattern` documentation: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
