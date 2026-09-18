# PRLD5121 – PoE Part 1: Registration and Login Feature

**Student:** Shahir Ahmed Khan
**Student Number:** ST10526182
**Module:** PROG5121

## What this program does

This is a console-based Java application that lets a user register an account
and then log in with it. There's no GUI — it all runs through the terminal,
using `Scanner` to read input.

When registering, the program checks:
- the username contains an underscore and is no more than 5 characters
- the password is at least 8 characters and has a capital letter, a number,
  and a special character
- the cell number starts with an international country code (a `+`) followed
  by up to 10 more digits

If any of these checks fail, the program tells the user exactly what's wrong
and lets them try again. Once all three pass, the details are stored and the
user can log in with the same username and password.

## Project structure

- `src/main/java/Login.java` — all the validation, registration, and login logic
- `src/main/java/Main.java` — the console app that actually runs and talks to the user
- `src/test/java/LoginTest.java` — JUnit 5 tests covering each check
- `pom.xml` — Maven build file, pulls in JUnit
- `.github/workflows/maven.yml` — runs the tests automatically every time I push,
  so I don't have to remember to run them manually

## An assumption I made

The brief only mentions capturing a username, password, and cell number during
registration. But the login success message needs to say "Welcome `<first name>`,
`<last name>`". Since there's nowhere else in the brief that says where those
come from, I added first name and last name as extra fields captured during
registration, so the login message actually has something to display. I explain
this in my video as well.

## How I tested it

I wrote a JUnit test for every condition given in the brief — both the "correct"
and "incorrect" test data for username, password, and cell number, plus the
login success/failure cases. I used a GitHub Actions workflow so the tests run
automatically on every push instead of me having to remember to run `mvn test`
myself each time.

## How to run it

1. Open the project folder in an IDE that supports Maven (I used VS Code with
   the Java Extension Pack).
2. Run `Main.java` to use the console app.
3. Run `mvn test`, or use the Testing panel in the IDE, to run the JUnit tests.

## Reference

The regex used in `checkCellPhoneNumber` follows the general pattern syntax
described in the Oracle Java `Pattern` class documentation:
https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
