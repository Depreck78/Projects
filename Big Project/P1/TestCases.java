import java.io.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCases {
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal") private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal") private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    @Test(timeout = 1000)
    public void AccountCreationTest() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input = "1\nTeacher\nPass\n1";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter new username:\n"
                + "Please enter a password:\n"
                + "Select an account type:\n"
                + "1. Teacher\n"
                + "2. Student\n";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void CreateCourse() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input = "2\nTeacher\nPass\n1\nCS 180\n3";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Teacher\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n"
                + "Enter name of course you want to create: \n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n";

        // String expected = "2\nErick\n1234\n2";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void CreateQuiz() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input =
                "2\nTeacher\nPass\n2\nCS 180\n1\nQuizTitle\n1\n1\nq1?\na1\n3";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Teacher\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n"
                + "List of courses:\n"
                + "CS 180\n"
                + "\n"
                + "Enter a course. Make sure you spell it correctly!\n"
                + "Currently accessing course: CS 180\n"
                + "1. Add quiz\n"
                + "2. Edit quiz\n"
                + "3. Edit course name\n"
                + "4. Add quiz file\n"
                + "Enter the title of the quiz.\n"
                + "How many questions will be on the quiz?\n"
                + "How many answers per question?\n"
                + "Number of questions: 1\n"
                + "Number of answers per question: 1\n"
                + "Enter the question:\n"
                + "Enter the correct answer choice for question 1\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n";

        // String expected = "2\nErick\n1234\n2";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void StudentAccountCreationTest() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input = "1\nStudent\nPass\n2";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter new username:\n"
                + "Please enter a password:\n"
                + "Select an account type:\n"
                + "1. Teacher\n"
                + "2. Student\n";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void ManualStudentTakesQuiz() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input = "2\nStudent\nPass\n1\nCS 180\nCS 180QuizTitle\n1\nA1\n2";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Student\n"
                + "1. Access Available Courses\n"
                + "2. Exit\n"
                + "3. Edit account\n"
                + "4. View Graded Quiz\n"
                + "List of courses:\n"
                + "CS 180\n"
                + "\n"
                + "Enter a course. Make sure you spell it correctly!\n"
                + "Currently accessing course: CS 180\n"
                + "List of quizzes for course: CS 180\n"
                + "Enter the name of the quiz you want to take: \n"
                + "CS 180QuizTitle\n"
                + "CS 180QuizTitle\n"
                + "q1?\n"
                + "a. a1\n"
                + "Enter your answer method for Question 1\n"
                + "1. Manual Answer\n"
                + "2. Import File\n"
                + "Type your answer choice:\n"
                + "1. Access Available Courses\n"
                + "2. Exit\n"
                + "3. Edit account\n"
                + "4. View Graded Quiz\n";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void CreateQuizByFileImport() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input =
                "2\nTeacher\nPass\n2\nCS 180\n4\nQuizTitle\nCS 180QuizTitlequestions.txt\nCS 180QuizTitleanswers.txt\n3";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Teacher\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n"
                + "List of courses:\n"
                + "CS 180\n"
                + "\n"
                + "Enter a course. Make sure you spell it correctly!\n"
                + "Currently accessing course: CS 180\n"
                + "1. Add quiz\n"
                + "2. Edit quiz\n"
                + "3. Edit course name\n"
                + "4. Add quiz file\n"
                + "Enter the quiz name: \n"
                + "Enter question file name: \n"
                + "Enter answer file name: \n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n";

        // String expected = "2\nErick\n1234\n2";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void StudentTakesQuizByFileImport() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input =
                "2\nStudent\nPass\n1\nCS 180\nCS 180QuizTitle\n2\nCS 180QuizTitleStudentanswers.txt\n2";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Student\n"
                + "1. Access Available Courses\n"
                + "2. Exit\n"
                + "3. Edit account\n"
                + "4. View Graded Quiz\n"
                + "List of courses:\n"
                + "CS 180\n"
                + "\n"
                + "Enter a course. Make sure you spell it correctly!\n"
                + "Currently accessing course: CS 180\n"
                + "List of quizzes for course: CS 180\n"
                + "Enter the name of the quiz you want to take: \n"
                + "CS 180QuizTitle\n"
                + "q1?\n"
                + "a. a1\n"
                + "Enter your answer method for Question 1\n"
                + "1. Manual Answer\n"
                + "2. Import File\n"
                + "Enter file name:\n"
                + "1. Access Available Courses\n"
                + "2. Exit\n"
                + "3. Edit account\n"
                + "4. View Graded Quiz\n";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
    @Test(timeout = 1000)
    public void Grading() throws IOException {
        // String input = "2\nstudent1\npass1\n2\npasswoord\nuser\npleaseee";
        String input = "2\nTeacher\nPass\n5\n1\nStudnet\n20\n3";
        String expected = "Welcome to the learning management system!\n"
                + "What would you like to do?\n"
                + "1. Create an Account\n"
                + "2. Login\n"
                + "Enter username:\n"
                + "Enter your password:\n"
                + "Successfully logged in!\n"
                + "Account Page - Type: Teacher\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n"
                + "Which quiz would you like to grade?\n"
                + "1. CS 180QuizTitle\n"
                + "Which student's submission do you want to grade?\n"
                + "1. 80QuizTitleStudent\n"
                + "Question: q1?\n"
                + "Answer: a1\n"
                + "What grade would you like to give?\n"
                + "1. Create New Course\n"
                + "2. Access available courses\n"
                + "3. Exit\n"
                + "4. Edit Account\n"
                + "5. View quiz submissions\n";

        // String expected = "2\nErick\n1234\n2";

        receiveInput(input);
        LearningManagementSystem.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals(
                "Verify that the output matches!", expected, actual);
    }
}
