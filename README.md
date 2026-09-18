# PoE Part 1 — Registration and Login Feature

A console-based Java application (no GUI) that registers a user and logs them in,
built with Maven and tested with JUnit 5.

## What's in this project

```
poe-part1/
├── pom.xml                              Maven build file (JUnit 5 dependency, test runner)
├── .github/workflows/maven.yml          Runs the tests automatically on every push (GitHub Actions)
├── src/main/java/Login.java             The Login class — all validation, registration, login logic
├── src/main/java/Main.java              Console app that uses Login (menus, Scanner input)
└── src/test/java/LoginTest.java         JUnit tests using the exact test data from the brief
```

## How the logic works (for your video explanation)

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

## Step-by-step setup

### 1. Install what you need
- **JDK 17+**: https://adoptium.net/
- **Maven**: https://maven.apache.org/download.cgi (or use your IDE's bundled Maven)
- **GitHub Desktop**: https://desktop.github.com/
- A **GitHub account** signed up with your Connect account: https://github.com/

### 2. Create the GitHub repository first
1. Go to github.com → **New repository**.
2. Name it something like `PoE-Part1-Registration-Login`.
3. Leave it empty (no README/gitignore — you already have these files).
4. Copy the repository URL.

### 3. Get this project into that repository
Using GitHub Desktop:
1. Open GitHub Desktop → **File → Add local repository** → point it at this
   `poe-part1` folder.
2. If it says "this isn't a Git repository yet," click **create a repository here**.
3. Under **Repository → Repository settings**, set the **Remote** to the GitHub URL
   you copied.
4. Write a commit summary (e.g. "Initial project setup") and click **Commit to main**.
5. Click **Publish repository** (or **Push origin** if it's already published).

You need a **minimum of six commits** for this part, so commit in small logical
steps rather than one giant commit — for example:
1. "Add Login class with username and password checks"
2. "Add cell phone number validation"
3. "Add registerUser method"
4. "Add loginUser and returnLoginStatus methods"
5. "Add Main console application"
6. "Add JUnit tests"

### 4. Open and run the project
- In **NetBeans**, **IntelliJ IDEA**, or **VS Code** (with the Java extension),
  open the `poe-part1` folder as a Maven project — it will read `pom.xml`
  automatically and download the JUnit dependency.
- To run the console app: run `Main.java`.
- To run the tests: run `mvn test` in a terminal in this folder, or right-click
  `LoginTest.java` → **Run Tests** in your IDE.

### 5. Automated testing (already set up for you)
The `.github/workflows/maven.yml` file means that every time you push a commit to
GitHub, GitHub Actions will automatically build the project and run every test in
`LoginTest.java`. You can see the results under the **Actions** tab of your
repository — this is what satisfies the "automate these tests" requirement.

### 6. Video presentation
Record an unlisted YouTube video (or similar) with **your own voice, no AI voice**,
covering:
- A run of the console app (registering, then logging in).
- A walk-through of `Login.java` explaining your logic and flow, using the
  explanation above as a guide — but **explain it in your own words**.
- A quick look at the passing tests in `LoginTest.java` and/or the green check
  on the GitHub Actions tab.

### 7. Submit
Push everything, then submit the GitHub repository link on Arc for Part 1.
