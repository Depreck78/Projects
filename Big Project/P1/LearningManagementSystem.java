import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Program Name: Learning Management System
 * A learning management system with student and teacher accounts.
 * Teachers can create/access/edit courses and quizzes.
 * Students can access/take courses and quizzes.
 *
 * @author Erick Moises Xu Li, Luke Bowlin, Aidan Cummings, Eric Kang, and Ryan Newman
 * @version April 10, 2022
 */

public class LearningManagementSystem {
    public static ArrayList<String> readQuizFile(String fileName)
            throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        File f = new File(fileName);

        if (!f.exists()) {
            throw new FileNotFoundException();
        }

        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }

            bfr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        boolean wrongNumber = false;
        Scanner scanner = new Scanner(System.in);
        String option;
        double autoScore = 0;
        System.out.println("Welcome to the learning management system!");

        do {
            System.out.println("What would you like to do?"
                    + "\n"
                    + "1. Create an Account"
                    + "\n"
                    + "2. Login");
            option = scanner.nextLine();
            if (option.equals("1")) {
                wrongNumber = false;
            } else if (option.equals("2")) {
                wrongNumber = false;
            } else {
                System.out.println("Invalid input. Please try again.");
                wrongNumber = true;
            }
        } while (wrongNumber);

        File userFile = new File("users.txt");
        FileOutputStream userFos = new FileOutputStream(userFile, true);
        PrintWriter userPw = new PrintWriter(userFos);
        FileReader userReader = new FileReader(userFile);
        BufferedReader userBfr = new BufferedReader(userReader);
        ArrayList<String> userList = new ArrayList<>();
        String userLine = userBfr.readLine();
        while (userLine != null) {
            userList.add(userLine);
            userLine = userBfr.readLine();
        }
        userBfr.close();

        File studentAnswerFile = new File("studentAnswer.txt");
        FileOutputStream studentAnswerFos =
                new FileOutputStream(studentAnswerFile, true);
        PrintWriter studentAnswerPw = new PrintWriter(studentAnswerFos);
        FileReader studentAnswerReader = new FileReader(studentAnswerFile);
        BufferedReader studentAnswerBfr =
                new BufferedReader(studentAnswerReader);
        ArrayList<String> studentAnswerList = new ArrayList<>();
        String studentAnswerLine = studentAnswerBfr.readLine();
        while (studentAnswerLine != null) {
            studentAnswerList.add(studentAnswerLine);
            studentAnswerLine = studentAnswerBfr.readLine();
        }
        studentAnswerBfr.close();

        // The program uses file I/O to store the information so data persists
        // even when the program is not running. Arraylists are also used to
        // store each line in the files. The arraylists are helpful for checking
        // to see if things like username/password is correct, course name is
        // correct, and for displaying things like the lists of courses and
        // quizzes, and the quiz questions and answers.
        File passwordFile = new File("passwords.txt");
        FileOutputStream passwordFos = new FileOutputStream(passwordFile, true);
        PrintWriter passwordPw = new PrintWriter(passwordFos);
        FileReader passReader = new FileReader(passwordFile);
        BufferedReader passBfr = new BufferedReader(passReader);
        ArrayList<String> passList = new ArrayList<>();
        String passLine = passBfr.readLine();
        while (passLine != null) {
            passList.add(passLine);
            passLine = passBfr.readLine();
        }
        passBfr.close();

        File accountTypeFile = new File("accountTypes.txt");
        FileOutputStream typeFos = new FileOutputStream(accountTypeFile, true);
        PrintWriter typePw = new PrintWriter(typeFos);
        FileReader typeReader = new FileReader(accountTypeFile);
        BufferedReader typeBfr = new BufferedReader(typeReader);
        ArrayList<String> typeList = new ArrayList<>();
        String typeLine = typeBfr.readLine();
        while (typeLine != null) {
            typeList.add(typeLine);
            typeLine = typeBfr.readLine();
        }
        typeBfr.close();

        File courseFile = new File("courses.txt");
        FileOutputStream courseFos = new FileOutputStream(courseFile, true);
        PrintWriter coursePw = new PrintWriter(courseFos);
        FileReader courseReader = new FileReader(courseFile);
        BufferedReader courseBfr = new BufferedReader(courseReader);
        ArrayList<String> courseList = new ArrayList<>();
        String courseLine = courseBfr.readLine();
        while (courseLine != null) {
            courseList.add(courseLine);
            courseLine = courseBfr.readLine();
        }
        courseBfr.close();

        File fileOfQuizzes = new File("quizzes.txt");
        FileOutputStream quizzesFos = new FileOutputStream(fileOfQuizzes, true);
        PrintWriter quizzesPw = new PrintWriter(quizzesFos);
        FileReader quizzesReader = new FileReader(fileOfQuizzes);
        BufferedReader quizzesBfr = new BufferedReader(quizzesReader);
        ArrayList<String> quizList = new ArrayList<>();
        String quizLine = quizzesBfr.readLine();
        while (quizLine != null) {
            quizList.add(quizLine);
            quizLine = quizzesBfr.readLine();
        }
        quizzesBfr.close();

        if (option.equals("1")) {
            // checks for unique user name
            String username;
            boolean contains = false;
            do {
                System.out.println("Enter new username:");
                username = scanner.nextLine();

                for (int i = 0; i < userList.size(); i++) {
                    contains = false;
                    if (userList.get(i).equals(username)) {
                        contains = true;
                    }
                }
                if (contains) {
                    System.out.println("Username already taken");
                }
            } while (contains);
            System.out.println("Please enter a password:");
            String password = scanner.nextLine();
            userPw.println(username);
            passwordPw.println(password);
            userPw.close();
            passwordPw.close();
            System.out.println("Select an account type:"
                    + "\n"
                    + "1. Teacher"
                    + "\n"
                    + "2. Student");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                typePw.println("Teacher");
                System.out.println("Teacher account Created! Restart Program to see changes!");
            } else if (input.equals("2")) {
                typePw.println("Student");
                System.out.println("Student account Created! Restart Program to see changes!");
            } else {
                System.out.println("Account creation failed! Restart Program to try again!");
            }
            typePw.close();
        }
        if (option.equals("2")) {
            int fileIndex = 0;
            int courseIndex = 0;
            int quizIndex = 0;
            boolean success = false;
            String username = "";
            while (success == false) {
                System.out.println("Enter username:");
                String enteredUsername = scanner.nextLine();
                System.out.println("Enter your password:");
                String enteredPassword = scanner.nextLine();
                // Checks to see if username and password are correct
                for (int i = 0; i < userList.size(); i++) {
                    if (enteredUsername.equals("")
                            || enteredPassword.equals("")) {
                        success = false;
                        break;
                    }
                    if (userList.get(i).equals(enteredUsername)
                            && passList.get(i).equals(enteredPassword)) {
                        System.out.println("Successfully logged in!");
                        fileIndex = i;
                        success = true;
                        username = enteredUsername;
                        break;
                    }
                }
                if (success == false) {
                    System.out.println(
                            "Invalid login credentials! Please try again.");
                }
            }
            String accountType = typeList.get(fileIndex);
            System.out.println("Account Page - Type: " + accountType);
            if (accountType.equals("Teacher")) {
                String teacherOption;
                PrintWriter quizPw = null;
                PrintWriter answerPw = null;
                boolean goBack = false;
                do {
                    System.out.println("1. Create New Course");
                    System.out.println("2. Access available courses");
                    System.out.println("3. Exit");
                    System.out.println("4. Edit Account");
                    System.out.println("5. View quiz submissions");
                    teacherOption = scanner.nextLine();
                    if (teacherOption.equals("1")) { // CREATE A COURSE
                        System.out.println(
                                "Enter name of course you want to create: ");
                        String courseName = scanner.nextLine();
                        coursePw.println(courseName);
                        courseList.add(courseName);
                        // coursePw.close();
                    }
                    if (teacherOption.equals("2")) { // ACCESS AVAILABLE COURSES
                        // display courses
                        System.out.println("List of courses:");
                        for (int i = 0; i < courseList.size(); i++) {
                            System.out.println(courseList.get(i));
                        }
                        boolean successful = false;
                        // Checks if the entered course matches the name of
                        // available courses
                        String enteredCourse = "null";
                        while (successful != true) {
                            System.out.println(
                                    "Enter a course. Make sure you spell it correctly!");
                            enteredCourse = scanner.nextLine();
                            for (int i = 0; i < courseList.size(); i++) {
                                if (enteredCourse.equals(courseList.get(i))) {
                                    successful = true;
                                    break;
                                }
                            }
                        }

                        // TODO allow teacher to view student quiz submissions
                        System.out.println(
                                "Currently accessing course: " + enteredCourse);
                        System.out.println("1. Add quiz");
                        System.out.println("2. Edit quiz");
                        System.out.println("3. Edit course name");
                        System.out.println("4. Add quiz file");
                        String courseOption = scanner.nextLine();
                        if (courseOption.equals("1")) { // CREATE QUIZ
                            System.out.println("Enter the title of the quiz.");
                            String quizTitle = scanner.nextLine();
                            // Writes the title of the quiz to the quiz file
                            quizzesPw.println(enteredCourse + quizTitle);

                            boolean failed = false;
                            int questionCount;
                            // Makes sure that quizzes must have more than 0
                            // questions
                            do {
                                System.out.println(
                                        "How many questions will be on the quiz?");
                                questionCount = scanner.nextInt();
                                scanner.nextLine();
                                if (questionCount <= 0) {
                                    System.out.println(
                                            "Question count cannot be 0 or less than 0.");
                                    failed = true;
                                } else {
                                    failed = false;
                                }

                            } while (failed);

                            String quizfilename =
                                    enteredCourse + quizTitle + "questions.txt";
                            File quizFile = new File(quizfilename);
                            quizFile.createNewFile();
                            FileOutputStream quizFos =
                                    new FileOutputStream(quizFile, true);
                            quizPw = new PrintWriter(quizFos);

                            String answerFilename =
                                    enteredCourse + quizTitle + "answers.txt";
                            File answerFile = new File(answerFilename);
                            answerFile.createNewFile();
                            FileOutputStream answerFos =
                                    new FileOutputStream(answerFile, true);
                            answerPw = new PrintWriter(answerFos);

                            String randomFilename =
                                    enteredCourse + quizTitle + "random.txt";
                            File randomFile = new File(randomFilename);
                            randomFile.createNewFile();
                            FileOutputStream randomFos =
                                    new FileOutputStream(randomFile, true);
                            PrintWriter randomPw = new PrintWriter(randomFos);
                            // Randomization^

                            boolean failed2 = false;
                            int answerCount;

                            do {
                                System.out.println(
                                        "How many answers per question?");
                                answerCount = scanner.nextInt();
                                scanner.nextLine();
                                if (answerCount <= 0) {
                                    System.out.println(
                                            "Answer count cannot be 0 or less than 0.");
                                    failed2 = true;
                                } else {
                                    failed2 = false;
                                }

                            } while (failed2);

                            do {
                                System.out.println(
                                        "Randomize question order?\n1: Yes\n0: No");
                                String randTemp = scanner.nextLine();
                                // scanner.nextLine();
                                randomPw.println(randTemp);
                                System.out.println(randTemp);
                                if (!randTemp.equals("0")
                                        && !randTemp.equals("1")) {
                                    System.out.println("Answer must be 1 or 0");
                                    failed2 = true;
                                } else {
                                    failed2 = false;
                                }

                            } while (failed2);

                            do {
                                System.out.println(
                                        "Randomize option order?\n1: Yes\n0: No");
                                String randTemp = scanner.nextLine();
                                // scanner.nextLine();
                                randomPw.println(randTemp);
                                System.out.println(randTemp);
                                if (!randTemp.equals("0")
                                        && !randTemp.equals("1")) {
                                    System.out.println("Answer must be 1 or 0");
                                    failed2 = true;
                                } else {
                                    failed2 = false;
                                }

                            } while (failed2);
                            randomPw.close();

                            System.out.println(
                                    "Number of questions: " + questionCount);
                            System.out.println(
                                    "Number of answers per question: "
                                            + answerCount);
                            for (int i = 0; i < questionCount; i++) {
                                System.out.println("Enter the question:");
                                String question = scanner.nextLine();
                                quizPw.println(question);
                                // asks for the correct answer first
                                System.out.println(
                                        "Enter the correct answer choice for question "
                                                + (i + 1));
                                String answer = scanner.nextLine();
                                answerPw.println(answer);
                                for (int j = 1; j < answerCount; j++) {
                                    System.out.println(
                                            "Enter an answer option for question "
                                                    + (i + 1));
                                    answer = scanner.nextLine();
                                    answerPw.println(answer);
                                    // quizPw.println(question);
                                }
                            }
                            // quizPw.close();
                            // answerPw.close();
                        }

                        if (courseOption.equals("2")) {
                            boolean quizSuccess = false;
                            String enteredQuiz = "";

                            while (quizSuccess != true) {
                                System.out.println(
                                        "Select a quiz to edit by entering the name of the quiz."
                                                + "Make sure to spell it exactly as it is shown!");
                                int enteredCourseLength =
                                        enteredCourse.length();
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i)
                                            .substring(0, enteredCourseLength)
                                            .equals(enteredCourse)) {
                                        System.out.println(quizList.get(i));
                                    }
                                }
                                enteredQuiz = scanner.nextLine();

                                for (int i = 0; i < quizList.size(); i++) {
                                    if (enteredQuiz.equals(quizList.get(i))) {
                                        quizSuccess = true;
                                        break;
                                    }
                                }
                            }

                            // TODO delete student answer files and
                            // studentAnswerList for 4
                            String quizFilename = enteredQuiz + "questions.txt";
                            File quizFile = new File(quizFilename);
                            String answerFilename = enteredQuiz + "answers.txt";
                            File answerFile = new File(answerFilename);

                            System.out.println("What would you like to do?"
                                    + "\n"
                                    + "1. Edit Quiz Questions"
                                    + "\n"
                                    + "2. Edit Quiz Answers"
                                    + "\n"
                                    + "3. Rename Quiz"
                                    + "\n"
                                    + "4. Delete Quiz");
                            String quizEditOptions = scanner.nextLine();
                            if (quizEditOptions.equals("1")) {
                                boolean failed = false;
                                int questionCount;
                                // Makes sure that quizzes must have more than 0
                                // questions
                                do {
                                    System.out.println(
                                            "How many questions will be on the quiz?");
                                    questionCount = scanner.nextInt();
                                    scanner.nextLine();
                                    if (questionCount <= 0) {
                                        System.out.println(
                                                "Question count cannot be 0 or less than 0.");
                                        failed = true;
                                    } else {
                                        failed = false;
                                    }

                                } while (failed);
                                FileOutputStream quizFos =
                                        new FileOutputStream(quizFile, true);
                                quizPw = new PrintWriter(quizFos, false);

                                System.out.println(
                                        "Number of questions: " + questionCount);
                                for (int i = 0; i < questionCount; i++) {
                                    System.out.println("Enter the question:");
                                    String question = scanner.nextLine();
                                    quizPw.println(question);
                                }
                            }

                            if (quizEditOptions.equals("2")) {
                                FileOutputStream answerFos =
                                        new FileOutputStream(answerFile, true);
                                answerPw = new PrintWriter(answerFos, false);

                                boolean failed = false;
                                int questionCount;
                                // Makes sure that quizzes must have more than 0
                                // questions
                                do {
                                    System.out.println(
                                            "How many questions will be on the quiz?");
                                    questionCount = scanner.nextInt();
                                    scanner.nextLine();
                                    if (questionCount <= 0) {
                                        System.out.println(
                                                "Question count cannot be 0 or less than 0.");
                                        failed = true;
                                    } else {
                                        failed = false;
                                    }

                                } while (failed);

                                boolean failed2 = false;
                                int answerCount;

                                do {
                                    System.out.println(
                                            "How many answers per question?");
                                    answerCount = scanner.nextInt();
                                    scanner.nextLine();
                                    if (answerCount <= 0) {
                                        System.out.println(
                                                "Answer count cannot be 0 or less than 0.");
                                        failed2 = true;
                                    } else {
                                        failed2 = false;
                                    }

                                } while (failed2);

                                System.out.println(
                                        "Number of questions: " + questionCount);
                                System.out.println(
                                        "Number of answers per question: "
                                                + answerCount);
                                for (int i = 0; i < questionCount; i++) {
                                    // asks for the correct answer first
                                    System.out.println(
                                            "Enter the correct answer choice for question "
                                                    + (i + 1));
                                    String answer = scanner.nextLine();
                                    answerPw.println(answer);
                                    for (int j = 1; j < answerCount; j++) {
                                        System.out.println(
                                                "Enter an answer option for question "
                                                        + (i + 1));
                                        answer = scanner.nextLine();
                                        answerPw.println(answer);
                                        // quizPw.println(question);
                                    }
                                }
                            }

                            if (quizEditOptions.equals("3")) {
                                System.out.println("Enter new quiz name:");
                                String newQuizName = scanner.nextLine();
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).equals(enteredQuiz)) {
                                        quizIndex = i;
                                        break;
                                    }
                                }
                                quizList.set(quizIndex, newQuizName);
                                File quizOverwrite = new File("quizzes.txt");
                                FileWriter quizOverwriter =
                                        new FileWriter(quizOverwrite, false);
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (i == 0) {
                                        quizOverwriter.write(quizList.get(i));
                                    } else {
                                        quizOverwriter.write(
                                                "\n" + quizList.get(i));
                                    }
                                }
                                quizOverwriter.close();

                                String newQuizFilename =
                                        newQuizName + "questions.txt";
                                File newQuizFile = new File(newQuizFilename);
                                quizFile.renameTo(newQuizFile);

                                String newAnswerFilename =
                                        newQuizName + "answers.txt";
                                File newAnswerFile =
                                        new File(newAnswerFilename);
                                answerFile.renameTo(newAnswerFile);

                                for (int i = 0; i < studentAnswerList.size();
                                     i++) {
                                    if (studentAnswerList.get(i).contains(
                                            enteredQuiz)) {
                                        String stuAnsFilename =
                                                studentAnswerList.get(i)
                                                        + "answers.txt";
                                        File stuAnsFile =
                                                new File(stuAnsFilename);
                                        String stuGradeFilename =
                                                studentAnswerList.get(i)
                                                        + "grades.txt";
                                        File stuGradeFile =
                                                new File(stuGradeFilename);

                                        studentAnswerList.set(i,
                                                newQuizName
                                                        + studentAnswerList.get(i)
                                                        .substring(
                                                                enteredQuiz
                                                                        .length()));

                                        String newStuAnsFilename =
                                                studentAnswerList.get(i)
                                                        + "answers.txt";
                                        File newStuAnsFile =
                                                new File(newStuAnsFilename);
                                        stuAnsFile.renameTo(newStuAnsFile);
                                        String newStuGradeFilename =
                                                studentAnswerList.get(i)
                                                        + "grades.txt";
                                        File newStuGradeFile =
                                                new File(newStuGradeFilename);
                                        stuGradeFile.renameTo(newStuGradeFile);
                                    }
                                }

                                File stuAnsOverwrite =
                                        new File("studentAnswer.txt");
                                FileWriter stuAnsOverwriter =
                                        new FileWriter(stuAnsOverwrite, false);
                                for (int i = 0; i < studentAnswerList.size();
                                     i++) {
                                    if (i == 0) {
                                        stuAnsOverwriter.write(
                                                studentAnswerList.get(i));
                                    } else {
                                        stuAnsOverwriter.write(
                                                "\n" + studentAnswerList.get(i));
                                    }
                                }
                                stuAnsOverwriter.close();
                            }

                            if (quizEditOptions.equals("4")) {
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).equals(enteredQuiz)) {
                                        quizIndex = i;
                                        break;
                                    }
                                }
                                File quizAnsFile =
                                        new File(enteredQuiz + "answers.txt");
                                File quizQuesFile =
                                        new File(enteredQuiz + "questions.txt");
                                quizAnsFile.delete();
                                quizQuesFile.delete();

                                quizList.remove(quizIndex);
                                File quizOverwrite = new File("quizzes.txt");
                                FileWriter quizOverwriter =
                                        new FileWriter(quizOverwrite, false);
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (i == 0) {
                                        quizOverwriter.write(quizList.get(i));
                                    } else {
                                        quizOverwriter.write(
                                                "\n" + quizList.get(i));
                                    }
                                }
                                quizOverwriter.close();

                                for (int i = 0; i < studentAnswerList.size();
                                     i++) {
                                    if (studentAnswerList.get(i).contains(
                                            enteredQuiz)) {
                                        String stuAnsFilename =
                                                studentAnswerList.get(i)
                                                        + "answers.txt";
                                        File stuAnsFile =
                                                new File(stuAnsFilename);

                                        studentAnswerList.remove(i);
                                        stuAnsFile.delete();
                                    }
                                }

                                File stuAnsOverwrite =
                                        new File("studentAnswer.txt");
                                FileWriter stuAnsOverwriter =
                                        new FileWriter(stuAnsOverwrite, false);
                                for (int i = 0; i < studentAnswerList.size();
                                     i++) {
                                    if (i == 0) {
                                        stuAnsOverwriter.write(
                                                studentAnswerList.get(i));
                                    } else {
                                        stuAnsOverwriter.write(
                                                "\n" + studentAnswerList.get(i));
                                    }
                                }
                                stuAnsOverwriter.close();

                                quizFile.delete();
                                answerFile.delete();
                            }
                        } // EDIT QUIZ
                        if (courseOption.equals("3")) { // EDIT COURSE
                            System.out.println("Enter new course name:");
                            String newCourseName = scanner.nextLine();
                            for (int i = 0; i < courseList.size(); i++) {
                                if (courseList.get(i).equals(enteredCourse)) {
                                    courseIndex = i;
                                    break;
                                }
                            }
                            courseList.set(courseIndex, newCourseName);
                            File courseOverwrite = new File("courses.txt");
                            FileWriter courseOverwriter =
                                    new FileWriter(courseOverwrite, false);
                            for (int i = 0; i < courseList.size(); i++) {
                                if (i == 0) {
                                    courseOverwriter.write(courseList.get(i));
                                } else {
                                    courseOverwriter.write(
                                            "\n" + courseList.get(i));
                                }
                            }
                            courseOverwriter.close();
                            for (int i = 0; i < quizList.size(); i++) {
                                if (quizList.get(i).contains(enteredCourse)) {
                                    String quizFilename =
                                            quizList.get(i) + "questions.txt";
                                    File quizFile = new File(quizFilename);
                                    String answerFilename =
                                            quizList.get(i) + "answers.txt";
                                    File answerFile = new File(answerFilename);

                                    quizList.set(i,
                                            newCourseName
                                                    + quizList.get(i).substring(
                                                    enteredCourse.length()));

                                    String newQuizFilename =
                                            quizList.get(i) + "questions.txt";
                                    File newQuizFile =
                                            new File(newQuizFilename);
                                    quizFile.renameTo(newQuizFile);
                                    String newAnswerFilename =
                                            quizList.get(i) + "answers.txt";
                                    File newAnswerFile =
                                            new File(newAnswerFilename);
                                    answerFile.renameTo(newAnswerFile);
                                }
                            }

                            File quizOverwrite = new File("quizzes.txt");
                            FileWriter quizOverwriter =
                                    new FileWriter(quizOverwrite, false);
                            for (int i = 0; i < quizList.size(); i++) {
                                if (i == 0) {
                                    quizOverwriter.write(quizList.get(i));
                                } else {
                                    quizOverwriter.write(
                                            "\n" + quizList.get(i));
                                }
                            }
                            quizOverwriter.close();

                            for (int i = 0; i < studentAnswerList.size(); i++) {
                                if (studentAnswerList.get(i).contains(
                                        enteredCourse)) {
                                    String stuAnsFilename =
                                            studentAnswerList.get(i)
                                                    + "answers.txt";
                                    File stuAnsFile = new File(stuAnsFilename);
                                    String stuGradeFilename =
                                            studentAnswerList.get(i)
                                                    + "grades.txt";
                                    File stuGradeFile =
                                            new File(stuGradeFilename);

                                    studentAnswerList.set(i,
                                            newCourseName
                                                    + studentAnswerList.get(i)
                                                    .substring(
                                                            enteredCourse.length()));

                                    String newAnswerFilename =
                                            studentAnswerList.get(i)
                                                    + "answers.txt";
                                    File newAnswerFile =
                                            new File(newAnswerFilename);
                                    stuAnsFile.renameTo(newAnswerFile);
                                    String newStuGradeFilename =
                                            studentAnswerList.get(i)
                                                    + "grades.txt";
                                    File newStuGradeFile =
                                            new File(newStuGradeFilename);
                                    stuGradeFile.renameTo(newStuGradeFile);
                                }
                            }

                            File stuAnsOverwrite =
                                    new File("studentAnswer.txt");
                            FileWriter stuAnsOverwriter =
                                    new FileWriter(stuAnsOverwrite, false);
                            for (int i = 0; i < studentAnswerList.size(); i++) {
                                if (i == 0) {
                                    stuAnsOverwriter.write(
                                            studentAnswerList.get(i));
                                } else {
                                    stuAnsOverwriter.write(
                                            "\n" + studentAnswerList.get(i));
                                }
                            }
                            stuAnsOverwriter.close();
                        }

                        if (courseOption.equals("4")) { // ADD QUIZ FILE IMPORT

                            ArrayList<String> QF1 = null;
                            boolean FFF = true;
                            String quizName;
                            String quizFile;
                            System.out.println("Enter the quiz name: ");
                            quizName = scanner.nextLine();
                            quizList.add(enteredCourse + quizName);

                            do {
                                // file name must be CourseQuiz'questions'.txt
                                System.out.println(
                                        "Enter question file name: ");
                                quizFile = scanner.nextLine();
                                quizzesPw.println(quizName);
                                try {
                                    QF1 = readQuizFile(quizFile);
                                    FFF = true;

                                } catch (FileNotFoundException e) {
                                    System.out.println(
                                            "Error! File Not Found!");
                                    FFF = false;
                                }
                            } while (FFF == false);

                            ArrayList<String> AF1 = null;
                            boolean AFF = true;
                            do {
                                // file name must be CourseQuiz'answers'.txt
                                System.out.println("Enter answer file name: ");
                                quizFile = scanner.nextLine();
                                quizzesPw.println(quizName);
                                try {
                                    AF1 = readQuizFile(quizFile);
                                    AFF = true;

                                } catch (FileNotFoundException e) {
                                    System.out.println(
                                            "Error! File Not Found!");
                                    AFF = false;
                                }
                            } while (AFF == false);
                        }
                    }
                    if (teacherOption.equals("4")) {
                        System.out.println("What do you want to do?"
                                + "\n"
                                + "1. Change username"
                                + "\n"
                                + "2. Change Password"
                                + "\n"
                                + "3. Delete Account");
                        String editOptions = scanner.nextLine();
                        // uses the position of the user's original username and
                        // then sets it to the new username
                        if (editOptions.equals("1")) {
                            /// checks for unique user name
                            String newUsername;
                            boolean contains = true;
                            do {
                                System.out.println("Enter new username:");
                                newUsername = scanner.nextLine();
                                contains = false;
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(newUsername)) {
                                        contains = true;
                                    }
                                }
                                if (contains) {
                                    System.out.println(
                                            "Username already taken");
                                }
                            } while (contains);
                            userList.set(fileIndex, newUsername);
                            // updates the old user file with a new file
                            // containing the new user
                            File overwrite = new File("users.txt");
                            FileWriter userOverwriter =
                                    new FileWriter(overwrite, false);
                            for (int i = 0; i < userList.size(); i++) {
                                if (i == 0) {
                                    userOverwriter.write(userList.get(i));
                                } else {
                                    userOverwriter.write(
                                            "\n" + userList.get(i));
                                }
                            }
                            userOverwriter.close();

                            System.out.println("User Name Saved!");
                        }

                        if (editOptions.equals("2")) {
                            System.out.println("Enter new password:");
                            String newPass = scanner.nextLine();
                            passList.set(fileIndex, newPass);
                            // updates the old password file with a new file
                            // containing the new password
                            File overwritePass = new File("passwords.txt");
                            FileWriter passOverwriter =
                                    new FileWriter(overwritePass, false);
                            for (int i = 0; i < passList.size(); i++) {
                                if (i == 0) {
                                    passOverwriter.write(passList.get(i));
                                } else {
                                    passOverwriter.write(
                                            "\n" + passList.get(i));
                                }
                            }
                            passOverwriter.close();
                            System.out.println("Password Saved!");
                        }
                        if (editOptions.equals("3")) {
                            // deletes account
                            userList.remove(fileIndex);
                            passList.remove(fileIndex);
                            typeList.remove(fileIndex);
                            File overwritePass = new File("passwords.txt");
                            FileWriter passOverwriter =
                                    new FileWriter(overwritePass, false);
                            for (int i = 0; i < passList.size(); i++) {
                                if (i == 0) {
                                    passOverwriter.write(passList.get(i));
                                } else {
                                    passOverwriter.write(
                                            "\n" + passList.get(i));
                                }
                            }
                            passOverwriter.close();
                            File overwrite = new File("users.txt");
                            FileWriter userOverwriter =
                                    new FileWriter(overwrite, false);
                            for (int i = 0; i < userList.size(); i++) {
                                if (i == 0) {
                                    userOverwriter.write(userList.get(i));
                                } else {
                                    userOverwriter.write(
                                            "\n" + userList.get(i));
                                }
                            }
                            userOverwriter.close();

                            File overwriteType = new File("accountTypes.txt");
                            FileWriter typeOverwriter =
                                    new FileWriter(overwriteType, false);
                            for (int i = 0; i < typeList.size(); i++) {
                                if (i == 0) {
                                    typeOverwriter.write(typeList.get(i));
                                } else {
                                    typeOverwriter.write(
                                            "\n" + typeList.get(i));
                                }
                            }
                            typeOverwriter.close();
                            goBack = true;

                            System.out.println("Account Deleted");
                            return;
                        }
                    }
                    if (teacherOption.equals("5")) {
                        ArrayList<String> studentNames = new ArrayList<>();
                        ArrayList<String> quizNames = new ArrayList<>();
                        int count = 0;
                        int quizCount = 0;
                        int check = 0;
                        File quizzes = new File("quizzes.txt");
                        Scanner viewQuizzes = new Scanner(quizzes);
                        while (viewQuizzes.hasNextLine()) {
                            check++;
                            String quiznames = viewQuizzes.nextLine();
                            quizNames.add(quiznames);
                        }
                        if (check == 0) {
                            System.out.println("No Quiz found.");
                            break;
                        }
                        System.out.println(
                                "Which quiz would you like to grade?");
                        for (int i = 0; i < quizNames.size(); i++) {
                            quizCount++;
                            System.out.println(
                                    quizCount + ". " + quizNames.get(i));
                        }
                        String quizchoice = scanner.nextLine();
                        int quizchoiceint = Integer.parseInt(quizchoice);
                        String quizchoicename = quizNames.get(
                                quizchoiceint - 1); // TODO: UNCOMMENT THIS LINE

                        System.out.println(
                                "Which student's submission do you want to grade?");
                        // File types = new File("studentAnswer.txt");
                        // Scanner view = new Scanner(types);
                        for (int i = 0; i < studentAnswerList.size(); i++) {
                            if (studentAnswerList.get(i).contains(quizchoicename)) {
                                System.out.println(studentAnswerList.get(i).substring(quizchoicename.length()));
                            }
                        }
                        /*while (view.hasNextLine()) {
                            String type = view.nextLine();
                            int index = type.indexOf("c");
                            System.out.println("index = " + index);
                            if (quizchoicename.equals(type.substring(0, index)))
                        { studentNames.add(type.substring(index + 5,
                        type.length()));
                            }
                        }*/
                        String choose = scanner.nextLine();
                        choose = quizchoicename + choose;
                        File studentAnswers = new File(
                                choose + "answers.txt");
                        File studentQuestions =
                                new File(quizchoicename + "questions.txt");
                        File studentGrades = new File(
                                choose + "grades.txt");
                        FileOutputStream quizFos =
                                new FileOutputStream(studentGrades, false);
                        PrintWriter gradePw = new PrintWriter(quizFos);
                        Scanner viewAnswers = new Scanner(studentAnswers);
                        Scanner viewQuesitons = new Scanner(studentQuestions);
                        while (viewQuesitons.hasNextLine()) {
                            System.out.println(
                                    "Question: " + viewQuesitons.nextLine());
                            System.out.println(
                                    "Answer: " + viewAnswers.nextLine());
                            System.out.println(
                                    "What grade would you like to give?");
                            String grade = scanner.nextLine();
                            gradePw.println(grade);
                        }
                        gradePw.close();
                    }

                    if ((teacherOption.equals("1") == false)
                            && (teacherOption.equals("2") == false)
                            && (teacherOption.equals("3") == false)
                            && (teacherOption.equals("4") == false)
                            && (teacherOption.equals("5") == false)) {
                        System.out.println("Invalid input!");
                    }

                } while (teacherOption.equals("3") == false || goBack);
                try {
                    quizzesPw.close();
                    coursePw.close();
                    quizPw.close();
                    answerPw.close();
                } catch (Exception e) {
                    return;
                }
            }

            if (accountType.equals("Student")) {
                String studentOptions;

                do {
                    System.out.println("1. Access Available Courses");
                    System.out.println("2. Exit");
                    System.out.println("3. Edit account");
                    System.out.println("4. View Graded Quiz");
                    studentOptions = scanner.nextLine();

                    if (studentOptions.equals("1")) { // ACCESS AVAILABLE
                        // COURSES
                        System.out.println("List of courses:");
                        for (int i = 0; i < courseList.size(); i++) {
                            System.out.println(courseList.get(i));
                        }

                        boolean successful = false;

                        String enteredCourse = "null";
                        while (successful != true) {
                            System.out.println(
                                    "Enter a course. Make sure you spell it correctly!");
                            enteredCourse = scanner.nextLine();
                            for (int i = 0; i < courseList.size(); i++) {
                                if (enteredCourse.equals(courseList.get(i))) {
                                    successful = true;
                                    break;
                                }
                            }
                        }

                        System.out.println(
                                "Currently accessing course: " + enteredCourse);
                        boolean quizNameSuccess = true;
                        ArrayList<String> sqaList = null;
                        ArrayList<String> sqqList = null;
                        File studentAnswers = null;
                        ArrayList<String> randList = null;
                        FileOutputStream answerFos = null;
                        PrintWriter answerPw = null;
                        String selectedQuiz = null;

                        do {
                            quizNameSuccess = true;
                            System.out.println(
                                    "List of quizzes for course: " + enteredCourse);
                            System.out.println(
                                    "Enter the name of the quiz you want to take: ");
                            int enteredCourseLength = enteredCourse.length();
                            for (int i = 0; i < quizList.size(); i++) {
                                try {
                                    if (quizList.get(i)
                                            .substring(0, enteredCourseLength)
                                            .equals(enteredCourse)) {
                                        System.out.println(quizList.get(i));
                                    }
                                } catch (Exception e) {
                                }
                            }
                            selectedQuiz = scanner.nextLine();
                            for (int i = 0; i < quizList.size(); i++) {
                                if (selectedQuiz.equals(quizList.get(i))) {
                                    quizNameSuccess = true;
                                    break;
                                }

                            }

                            studentAnswerList.add(
                                    selectedQuiz + userList.get(fileIndex));
                            studentAnswerPw.println(
                                    selectedQuiz + userList.get(fileIndex));

                            Timestamp timestamp =
                                    new Timestamp(System.currentTimeMillis());
                            studentAnswerPw.println(timestamp);

                            try {
                                File selectedQuizQuestions =
                                        new File(selectedQuiz + "questions.txt");
                                FileReader selectedQuizReader =
                                        new FileReader(selectedQuizQuestions);
                                BufferedReader sqqBfr =
                                        new BufferedReader(selectedQuizReader);
                                sqqList = new ArrayList<>();
                                String sqqLine = sqqBfr.readLine();
                                while (sqqLine != null) {
                                    sqqList.add(sqqLine);
                                    sqqLine = sqqBfr.readLine();
                                }
                                sqqBfr.close();

                                File selectedQuizAnswers =
                                        new File(selectedQuiz + "answers.txt");
                                FileReader sqaReader =
                                        new FileReader(selectedQuizAnswers);
                                BufferedReader sqaBfr =
                                        new BufferedReader(sqaReader);
                                sqaList = new ArrayList<>();
                                String sqaLine = sqaBfr.readLine();
                                while (sqaLine != null) {
                                    sqaList.add(sqaLine);
                                    sqaLine = sqaBfr.readLine();
                                }
                                sqaBfr.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                                //System.out.println("YOU FAILED!");
                                quizNameSuccess = false;
                            }
                            try {
                                File selectedQuizRandom =
                                        new File(selectedQuiz + "random.txt");
                                FileReader randReader =
                                        new FileReader(selectedQuizRandom);
                                BufferedReader randBfr =
                                        new BufferedReader(randReader);
                                randList = new ArrayList<>();
                                String randLine = randBfr.readLine();
                                while (randLine != null) {
                                    randList.add(randLine);
                                    randLine = randBfr.readLine();
                                }
                                randBfr.close();
                            } catch (Exception e) {

                            }

                        } while (quizNameSuccess != true);

                        studentAnswers = new File(selectedQuiz
                                + userList.get(fileIndex) + "answers.txt");
                        studentAnswers.createNewFile();
                        answerFos = new FileOutputStream(studentAnswers, true);
                        answerPw = new PrintWriter(answerFos);

                        // this is so that every question has a, b, c, d, etc
                        // for each answer
                        int limiter = sqaList.size() / sqqList.size();
                        String[] letters = {"a.", "b.", "c.", "d.", "e.", "f.",
                                "g.", "h.", "i.", "j.", "k.", "l.", "m.", "n.",
                                "o.", "p.", "q.", "r.", "s.", "t.", "u.", "v.",
                                "w.", "x.", "y.", "z."};
                        // this for loop prints out the answers but with the
                        // letter in front so  if the answers were 2,3,4 it would
                        // print: a. 2 b. 3 c. 4
                        ArrayList<String[][]> quizListo = new ArrayList<>();

                        int itter = 0;
                        for (int i = 0; i < sqaList.size(); i += limiter) {
                            ArrayList<String> tempAnswersList =
                                    new ArrayList<>();
                            String[] tempAnswersArray; // = new String[limiter];
                            String[] tempCorrAns = new String[limiter];
                            String[] tempQuestion = new String[limiter];
                            for (int j = i; j < limiter + i; j++) {
                                tempAnswersList.add(sqaList.get(j));
                            }
                            try {
                                if (randList.get(1).equals("1")) {
                                    Collections.shuffle(tempAnswersList);
                                }
                            } catch (Exception e) {

                            }

                            tempAnswersArray = tempAnswersList.toArray(
                                    new String[tempAnswersList.size()]);
                            tempCorrAns[0] = sqaList.get(i);
                            tempQuestion[0] = sqqList.get(itter);
                            String[][] tempArray = new String[3][limiter];
                            tempArray[2] = tempCorrAns;
                            tempArray[1] = tempQuestion;
                            tempArray[0] = tempAnswersArray;
                            quizListo.add(
                                    tempArray); //<<-----Has to be here I think, but
                            //im not sure why it's not working
                            // System.out.println(quizListo.get(0)[1][0]);//changes
                            // between iterations (Should stay the same)
                            itter++;
                        }
                        /// System.out.print(QuizList.get(0)[1][0]);
                        try {
                            if (randList.get(0).equals("1")) {
                                Collections.shuffle(quizListo);
                            }
                        } catch (Exception e) {

                        }

                        String[][][] quizArray = quizListo.toArray(
                                new String[quizListo.size()][3][limiter]);

                        for (int i = 0; i < sqqList.size(); i++) {
                            System.out.println(quizArray[i][1][0]);
                            for (int j = 0; j < limiter; j++) {
                                System.out.println(
                                        letters[j] + " " + quizArray[i][0][j]);
                            }

                            String answertype = "";

                            do {
                                System.out.println(
                                        "Enter your answer method for Question "
                                                + (i + 1));
                                System.out.println(
                                        "1. Manual Answer\n2. Import File");
                                answertype = scanner.nextLine();

                                if (answertype.equals("1")) {
                                    System.out.println(
                                            "Type your answer choice:");
                                    String selectedAnswer = scanner.nextLine();
                                    // answerPw.println(quizArray[i][2][0]);
                                    if (selectedAnswer.equals(
                                            quizArray[i][2][0])) {
                                        autoScore += (1.0 / sqqList.size());
                                    }
                                    answerPw.println(selectedAnswer);
                                }

                                if (answertype.equals("2")) {
                                    ArrayList<String> AF = null;
                                    boolean AAA = true;

                                    do {
                                        // filename must be
                                        // CourseQuizUsername'answers.txt'
                                        System.out.println("Enter file name:");
                                        String answerFile = scanner.nextLine();

                                        try {
                                            AF = readQuizFile(answerFile);
                                            AAA = true;

                                        } catch (FileNotFoundException e) {
                                            System.out.println(
                                                    "Error! File Not Found!");
                                            AAA = false;
                                        }
                                    } while (AAA == false);
                                }

                                if ((answertype.equals("1") == false)
                                        && (answertype.equals("2") == false)) {
                                    System.out.println(
                                            "Invalid Choice! Try again.");
                                }

                            } while ((answertype.equals("1") == false)
                                    && (answertype.equals("2") == false));
                        }
                        System.out.println(
                                "AutoGrader Score: " + (autoScore * 100) + "%");
                        answerPw.close();

                        File studentAutoGrades = new File(selectedQuiz
                                + userList.get(fileIndex) + "autogrades.txt");
                        FileOutputStream quizAutoFos =
                                new FileOutputStream(studentAutoGrades, true);
                        PrintWriter autoGradePw = new PrintWriter(quizAutoFos);

                        autoGradePw.println(autoScore);

                        autoGradePw.close();

                        // System.out.println(quizTitle);
                    }
                    if (studentOptions.equals("3")) { // EDIT ACCOUNT
                        System.out.println("What do you want to do?"
                                + "\n"
                                + "1. Change username"
                                + "\n"
                                + "2. Change Password"
                                + "\n"
                                + "3. Delete Account");

                        String editOptions = scanner.nextLine();

                        if (editOptions.equals("1")) {
                            // checks for unique user name
                            String oldUsername;
                            String newUsername;
                            boolean contains = true;
                            do {
                                System.out.println("Enter new username:");
                                newUsername = scanner.nextLine();
                                contains = false;
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(newUsername)) {
                                        contains = true;
                                    }
                                }
                                if (contains) {
                                    System.out.println(
                                            "Username already taken");
                                }
                            } while (contains);
                            oldUsername = userList.get(fileIndex);

                            userList.set(fileIndex, newUsername);
                            File overwrite = new File("users.txt");
                            FileWriter userOverwriter =
                                    new FileWriter(overwrite, false);
                            for (int i = 0; i < userList.size(); i++) {
                                if (i == 0) {
                                    userOverwriter.write(userList.get(i));
                                } else {
                                    userOverwriter.write(
                                            "\n" + userList.get(i));
                                }
                            }
                            userOverwriter.close();

                            for (int i = 0; i < studentAnswerList.size(); i++) {
                                if (studentAnswerList.get(i).contains(
                                        oldUsername)) {
                                    String stuAnsFilename =
                                            studentAnswerList.get(i)
                                                    + "answers.txt";
                                    File stuAnsFile = new File(stuAnsFilename);
                                    String stuGradeFilename =
                                            studentAnswerList.get(i)
                                                    + "grades.txt";
                                    File stuGradeFile =
                                            new File(stuGradeFilename);

                                    studentAnswerList.set(i,
                                            studentAnswerList.get(i).substring(0,
                                                    studentAnswerList.get(i).indexOf(
                                                            oldUsername))
                                                    + newUsername);

                                    String newAnswerFilename =
                                            studentAnswerList.get(i)
                                                    + "answers.txt";
                                    File newAnswerFile =
                                            new File(newAnswerFilename);
                                    stuAnsFile.renameTo(newAnswerFile);
                                    String newStuGradeFilename =
                                            studentAnswerList.get(i)
                                                    + "grades.txt";
                                    File newStuGradeFile =
                                            new File(newStuGradeFilename);
                                    stuGradeFile.renameTo(newStuGradeFile);
                                }
                            }

                            File stuAnsOverwrite =
                                    new File("studentAnswer.txt");
                            FileWriter stuAnsOverwriter =
                                    new FileWriter(stuAnsOverwrite, false);
                            for (int i = 0; i < studentAnswerList.size(); i++) {
                                if (i == 0) {
                                    stuAnsOverwriter.write(
                                            studentAnswerList.get(i));
                                } else {
                                    stuAnsOverwriter.write(
                                            "\n" + studentAnswerList.get(i));
                                }
                            }
                            stuAnsOverwriter.close();
                            System.out.println("User Name Saved!");
                        }

                        if (editOptions.equals("2")) {
                            System.out.println("Enter new password:");
                            String newPass = scanner.nextLine();
                            passList.set(fileIndex, newPass);
                            File overwritePass = new File("passwords.txt");
                            FileWriter passOverwriter =
                                    new FileWriter(overwritePass, false);
                            for (int i = 0; i < passList.size(); i++) {
                                if (i == 0) {
                                    passOverwriter.write(passList.get(i));
                                } else {
                                    passOverwriter.write(
                                            "\n" + passList.get(i));
                                }
                            }
                            passOverwriter.close();

                            System.out.println("Password Saved!");
                        }
                        if (editOptions.equals("3")) {
                            userList.remove(fileIndex);
                            passList.remove(fileIndex);
                            typeList.remove(fileIndex);
                            File overwritePass = new File("passwords.txt");
                            FileWriter passOverwriter =
                                    new FileWriter(overwritePass, false);
                            for (int i = 0; i < passList.size(); i++) {
                                if (i == 0) {
                                    passOverwriter.write(passList.get(i));
                                } else {
                                    passOverwriter.write(
                                            "\n" + passList.get(i));
                                }
                            }
                            passOverwriter.close();
                            File overwrite = new File("users.txt");
                            FileWriter userOverwriter =
                                    new FileWriter(overwrite, false);
                            for (int i = 0; i < userList.size(); i++) {
                                if (i == 0) {
                                    userOverwriter.write(userList.get(i));
                                } else {
                                    userOverwriter.write(
                                            "\n" + userList.get(i));
                                }
                            }
                            userOverwriter.close();

                            File overwriteType = new File("accountTypes.txt");
                            FileWriter typeOverwriter =
                                    new FileWriter(overwriteType, false);
                            for (int i = 0; i < typeList.size(); i++) {
                                if (i == 0) {
                                    typeOverwriter.write(typeList.get(i));
                                } else {
                                    typeOverwriter.write(
                                            "\n" + typeList.get(i));
                                }
                            }
                            typeOverwriter.close();

                            System.out.println("Account Deleted");
                            return;
                        }
                    }
                    if (studentOptions.equals("4")) { // TODO VIEW GRADED QUIZ
                        ArrayList<String> quizzes = new ArrayList<>();
                        ArrayList<Integer> totalGrade = new ArrayList<>();
                        int count = 0;
                        System.out.println(
                                "Which quiz would you like to view?");
                        for (int i = 0; i < quizList.size(); i++) {
                            System.out.println(quizList.get(i));
                        }


                        File types = new File("studentAnswer.txt");
                        // Scanner view = new Scanner(types);
                        // System.out.println(" NOTE: BELOW THIS IS");
                        /*while (view.hasNextLine()) {
                            String type = view.nextLine();
                            int index = type.indexOf("title");
                            if (username.equals(type.substring(index + 5,
                        type.length()))) { count++; System.out.println(count +
                        ". " + type.substring(0, index));
                                quizzes.add(type.substring(0, index));
                            }
                        }*/
                        // String quizchoicenumber = scanner.nextLine();
                        // int quizchoicenumberint =
                        // Integer.parseInt(quizchoicenumber);
                        try {
                            String quizChoice = scanner.nextLine();
                            File studentGrades =
                                    new File(quizChoice + username + "grades.txt");
                            File studentQuestions =
                                    new File(quizChoice + "questions.txt");
                            Scanner grades = new Scanner(studentGrades);
                            Scanner questions = new Scanner(studentQuestions);
                            while (grades.hasNextLine()) {
                                String grade = grades.nextLine();
                                int gradeInt = Integer.parseInt(grade);
                                String question = questions.nextLine();
                                System.out.println("Question: " + question);
                                System.out.println("Grade: " + grade);
                                totalGrade.add(gradeInt);
                            }

                            int total = 0;
                            for (int i = 0; i < totalGrade.size(); i++) {
                                total = total + totalGrade.get(i);
                            }
                            // System.out.println("Total grade: " + total);
                            System.out.println("Total grade: " + total);
                        } catch (Exception e) {
                            System.out.println("Quiz hasn't been graded!");

                        }
                    }

                } while (studentOptions.equals("2") == false);
            }
        }
        studentAnswerPw.close();
    }
}
