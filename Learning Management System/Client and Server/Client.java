import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Program Name: Client
 * A client class that send information to the server
 * The information sent is used by the server to determine
 * what things to do such as take a quiz or create a course.
 * The Client also contains the graphical user interface.
 * NOTE: ALL USES OF SYSTEM.OUT ARE SOLELY FOR DEBUGGING PURPOSES ONLY
 * User interaction is handled through the GUI exclusively
 *
 * @author Erick Moises Xu Li, Luke Bowlin, Aidan Cummings, Eric Kang, and Ryan Newman
 * @version May 1, 2022
 */

public class Client extends JComponent implements Runnable {
    JButton createCourse;
    JButton accessCourses;
    JButton editAccount;
    JButton viewQuiz;
    JButton studentAccessCourse;
    JButton studentEditAccount;
    JButton viewGradedQuiz;
    static PrintWriter writer;
    static BufferedReader reader;
    static Socket socket;
    static boolean isTeacher = false;
    static boolean isStudent = false;

    ActionListener actionListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createCourse) {
                String createdCourseName =
                        JOptionPane.showInputDialog(null, "Enter new course name");

                sendToServer("CREATING_COURSE");
                if (createdCourseName == null) {
                    JOptionPane.showMessageDialog(null,
                            "Error: null course name", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                sendToServer(createdCourseName);
                System.out.println("SENDING TO SERVER: " + createdCourseName);
            }
            if (e.getSource() == accessCourses) {
                sendToServer("ACCESS_COURSES");
                try {
                    int limiter = Integer.parseInt(reader.readLine());
                    String[] courses = new String[limiter];
                    if (limiter == 0) {
                        JOptionPane.showMessageDialog(null,
                                "No available courses", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    for (int i = 0; i < limiter; i++) {
                        courses[i] = reader.readLine();
                    }

                    String accessedCourse =
                            (String) JOptionPane.showInputDialog(null,
                                    "List of courses:", "Course List",
                                    JOptionPane.QUESTION_MESSAGE, null, courses,
                                    courses[0]);
                    String[] options = {"Add Quiz", "Edit quiz",
                            "Edit course name", "Add quiz file"};
                    String option = (String) JOptionPane.showInputDialog(null,
                            "Select an option", "Course Options",
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
                    if (option.equals("Add Quiz")) {
                        PrintWriter addQuizWriter = null;
                        addQuizWriter =
                                new PrintWriter(socket.getOutputStream());
                        sendToServer("ADD_QUIZ");
                        sendToServer(accessedCourse);
                        String nameOfQuiz = JOptionPane.showInputDialog(null,
                                "What's the title of this quiz?", "Quiz Name",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(nameOfQuiz);
                        String numberOfQuestions = JOptionPane.showInputDialog(
                                null,
                                "How many questions would you like in this quiz?",
                                "Number of Questions",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(numberOfQuestions);
                        String numberOfAnswers = JOptionPane.showInputDialog(
                                null,
                                "How many answers would you like per question?",
                                "Number of Answers", JOptionPane.QUESTION_MESSAGE);
                        sendToServer(numberOfAnswers);
                        String randQOrder = JOptionPane.showInputDialog(null,
                                "Randomize question order (y/n)?", "Q order",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(randQOrder);
                        System.out.println("rand1: " + randQOrder);
                        String randChOrder = JOptionPane.showInputDialog(null,
                                "Randomize answer-choice order (y/n)?", "Ch order",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(randChOrder);
                        System.out.println("rand1: " + randChOrder);
                        int intNumberOfQuestions =
                                Integer.parseInt(numberOfQuestions);
                        int intNumberOfAnswers =
                                Integer.parseInt(numberOfAnswers);
                        String[] questions = new String[intNumberOfQuestions];
                        String[] answers = new String[intNumberOfAnswers
                                * intNumberOfQuestions];
                        for (int i = 0; i < intNumberOfQuestions; i++) {
                            String question = JOptionPane.showInputDialog(null,
                                    "Write the question", "Quiz maker",
                                    JOptionPane.QUESTION_MESSAGE);
                            questions[i] = question;
                            addQuizWriter.write(
                                    questions[i]); // sendToServer method doesn't
                            // work in for loops
                            addQuizWriter
                                    .println(); // so that is why this is here
                            String correctAnswer = JOptionPane.showInputDialog(
                                    null, "Write the correct answer option",
                                    "Quiz maker", JOptionPane.QUESTION_MESSAGE);
                            addQuizWriter.println(correctAnswer);
                            addQuizWriter.println();
                            for (int j = 1; j < intNumberOfAnswers; j++) {
                                String answerOption =
                                        JOptionPane.showInputDialog(null,
                                                "Write an answer option", "Quiz maker",
                                                JOptionPane.QUESTION_MESSAGE);
                                addQuizWriter.println(answerOption);
                                addQuizWriter.println();
                            }
                        }

                        JOptionPane.showMessageDialog(null,
                                "Quiz has been created", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        addQuizWriter.flush();
                    }
                    if (option.equals("Edit quiz")) {
                        sendToServer("EDIT_QUIZ");
                        sendToServer(accessedCourse);
                        int limiter2 = Integer.parseInt(reader.readLine());
                        System.out.println(
                                "Number of quizzes in total for the selected course: "
                                        + limiter2);
                        String[] quizzes = new String[limiter2];
                        for (int i = 0; i < limiter2; i++) {
                            quizzes[i] = reader.readLine();
                        }
                        String accessedQuiz =
                                (String) JOptionPane.showInputDialog(null,
                                        "List of quizzes:", "Title",
                                        JOptionPane.QUESTION_MESSAGE, null, quizzes,
                                        quizzes[0]);

                        sendToServer(accessedQuiz);
                        String[] editOptions = {"Edit quiz title",
                                "Edit question", "Edit answer", "Delete Quiz"};
                        String editOption = createDropDown("Select an option",
                                "Course Options", editOptions, editOptions[0]);
                        if (editOption.equals("Edit quiz title")) {
                            sendToServer("EDIT_TITLE");
                            String newTitle = JOptionPane.showInputDialog(
                                    null, "Enter new title");
                            sendToServer(newTitle);
                        }
                        if (editOption.equals("Edit question")) {
                            sendToServer("EDIT_QUESTION");
                            int questionSize =
                                    Integer.parseInt(reader.readLine());
                            System.out.println(
                                    "Received size: " + questionSize);
                            System.out.println("Success");

                            String[] questions = new String[questionSize];
                            for (int i = 0; i < questionSize; i++) {
                                System.out.println("Debugging");
                                String receivedQuestion = reader.readLine();
                                System.out.println(
                                        "Received: " + receivedQuestion);
                                questions[i] = receivedQuestion;
                            }
                            String selectedQ =
                                    createDropDown("Select question to edit",
                                            "Title", questions, questions[0]);
                            sendToServer(selectedQ);
                            String newQ = JOptionPane.showInputDialog(
                                    null, "Enter new question name");
                            sendToServer(newQ);
                        }
                        if (editOption.equals("Edit answer")) {
                            sendToServer("EDIT_ANSWER");
                            int ansSize = Integer.parseInt(reader.readLine());
                            System.out.println("Received size: " + ansSize);
                            System.out.println("Success");

                            String[] answers = new String[ansSize];
                            for (int i = 0; i < ansSize; i++) {
                                System.out.println("Debugging");
                                String receivedAns = reader.readLine();
                                System.out.println("Received: " + receivedAns);
                                answers[i] = receivedAns;
                            }
                            String selectedAns =
                                    createDropDown("Select answer to edit", "Title",
                                            answers, answers[0]);
                            sendToServer(selectedAns);
                            String newAns = JOptionPane.showInputDialog(
                                    null, "Enter new answer name");
                            sendToServer(newAns);
                        }

                        if (editOption.equals("Delete Quiz")) {
                            sendToServer("Delete Quiz");
                        }
                    }

                    if (option.equals("Edit course name")) {
                        sendToServer("EDIT_COURSE_NAME");
                        sendToServer(accessedCourse);
                        String editedCourse = JOptionPane.showInputDialog(
                                null, "Enter new course name");
                        sendToServer(editedCourse);
                        JOptionPane.showMessageDialog(
                                null, "Successfully edited course name!");
                    }
                    if (option.equals("Add quiz file")) {
                        PrintWriter aqfWriter = null;
                        aqfWriter = new PrintWriter(socket.getOutputStream());
                        PrintWriter aqfWriter2 = null;
                        aqfWriter2 = new PrintWriter(socket.getOutputStream());
                        sendToServer("ADD_QUIZ_FILE");

                        JFileChooser fc = new JFileChooser();
                        fc.setDialogTitle("Choose quiz question file");
                        int val = fc.showOpenDialog(null);
                        System.out.println(val);

                        File f = fc.getSelectedFile();
                        FileReader fr = new FileReader(f);
                        BufferedReader bfr = new BufferedReader(fr);
                        ArrayList<String> fileList = new ArrayList<>();

                        String fileName = JOptionPane.showInputDialog(
                                null, "Enter the file name");

                        sendToServer(fileName);
                        String quizName = JOptionPane.showInputDialog(
                                null, "Enter the quiz name");
                        sendToServer(quizName);

                        String line = bfr.readLine();
                        int sizeOfFile = 0;
                        while (line != null) {
                            fileList.add(line);
                            System.out.println("reading: " + line);
                            line = bfr.readLine();
                            sizeOfFile++;
                        }

                        sendToServer(String.valueOf(sizeOfFile));
                        for (int i = 0; i < sizeOfFile; i++) {
                            aqfWriter.write(fileList.get(i));
                            aqfWriter.println();
                        }
                        aqfWriter.flush();

                        JFileChooser fc2 = new JFileChooser();
                        fc2.setDialogTitle("Choose quiz answer file");
                        int val2 = fc2.showOpenDialog(null);
                        System.out.println(val2);

                        File f2 = fc2.getSelectedFile();
                        FileReader fr2 = new FileReader(f2);
                        BufferedReader bfr2 = new BufferedReader(fr2);
                        ArrayList<String> fileList2 = new ArrayList<>();

                        String fileName2 = JOptionPane.showInputDialog(
                                null, "Enter the file name");

                        sendToServer(fileName2);

                        String line2 = bfr2.readLine();
                        int sizeOfFile2 = 0;
                        while (line2 != null) {
                            fileList2.add(line2);
                            System.out.println("reading: " + line2);
                            line2 = bfr2.readLine();
                            sizeOfFile2++;
                        }

                        sendToServer(String.valueOf(sizeOfFile2));
                        for (int i = 0; i < sizeOfFile2; i++) {
                            aqfWriter2.write(fileList2.get(i));
                            aqfWriter2.println();
                        }
                        aqfWriter2.flush();
                        //
                        String randQOrder = JOptionPane.showInputDialog(null,
                                "Randomize question order (y/n)?", "Q order",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(randQOrder);
                        String randChOrder = JOptionPane.showInputDialog(null,
                                "Randomize answer-choice order (y/n)?", "Ch order",
                                JOptionPane.QUESTION_MESSAGE);
                        sendToServer(randChOrder);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == editAccount) {
                sendToServer("TEACH_EDIT_ACCOUNT");
                String[] editoptions = {
                        "Change User Name", "Change Password", "Delete Account"};
                String editaccountteacher =
                        (String) JOptionPane.showInputDialog(null,
                                "How would you like to edit your account?",
                                "Edit Account", JOptionPane.QUESTION_MESSAGE, null,
                                editoptions, editoptions[0]);
                sendToServer(editaccountteacher);

                if (editaccountteacher.equals("Change User Name")) {
                    String newusername = JOptionPane.showInputDialog(null,
                            "New user name:", "UserName",
                            JOptionPane.QUESTION_MESSAGE);
                    sendToServer(newusername);
                    String errorCheck = null;
                    try {
                        errorCheck = reader.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (errorCheck.equals("NOT_UNIQUE_ERROR")) {
                        JOptionPane.showMessageDialog(null,
                                "Error: username is already taken", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (editaccountteacher.equals("Change Password")) {
                    String newpass = JOptionPane.showInputDialog(null,
                            "New password:", "Password",
                            JOptionPane.QUESTION_MESSAGE);
                    sendToServer(newpass);
                }

                if (editaccountteacher.equals("Delete Account")) {
                    sendToServer("Delete Account");

                    JOptionPane.showMessageDialog(null, "Account Deleted",
                            "Delete", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
            if (e.getSource() == viewQuiz) {
                sendToServer("VIEW_QUIZ");
                try {
                    PrintWriter viewQuizWriter = null;
                    viewQuizWriter = new PrintWriter(socket.getOutputStream());

                    int viewQuizSize = Integer.parseInt(reader.readLine());
                    String[] viewQuizList = new String[viewQuizSize];
                    String viewQuizLine;
                    for (int i = 0; i < viewQuizSize; i++) {
                        viewQuizLine = reader.readLine();
                        viewQuizList[i] = viewQuizLine;
                    }

                    String viewedSub = (String) JOptionPane.showInputDialog(
                            null, "Which submission would you like to view?",
                            "Title", JOptionPane.QUESTION_MESSAGE, null,
                            viewQuizList, viewQuizList[0]);
                    sendToServer(viewedSub);

                    int viewedSize = Integer.parseInt(reader.readLine());
                    String[] viewedQuestions = new String[viewedSize];
                    String[] viewedAnswers = new String[viewedSize];
                    String[] viewedGrades = new String[viewedSize];

                    for (int i = 0; i < viewedSize; i++) {
                        viewedQuestions[i] = reader.readLine();
                    }
                    for (int i = 0; i < viewedSize; i++) {
                        viewedAnswers[i] = reader.readLine();
                        if (viewedAnswers[i].contains("$&*")) {
                            viewedAnswers[i] =
                                    viewedAnswers[i].replace("$&*", "\n");
                        }
                    }
                    for (int i = 0; i < viewedSize; i++) {
                        viewedGrades[i] = reader.readLine();
                    }

                    for (int i = 0; i < viewedSize; i++) {
                        String grade = JOptionPane.showInputDialog(null,
                                viewedQuestions[i] + "\nAnswer: " + viewedAnswers[i]
                                        + "\nEnter points earned if you want to grade this question.");
                        if (!grade.equals("")) {
                            viewedGrades[i] = grade;
                        }
                        viewQuizWriter.println(viewedGrades[i]);
                    }
                    viewQuizWriter.flush();

                } catch (IOException en) {
                    en.printStackTrace();
                }
            }
            if (e.getSource() == studentAccessCourse) {
                PrintWriter accessCourseWriter = null;
                PrintWriter accessCourseWriter2 = null;
                PrintWriter accessCourseWriter3 = null;

                try {
                    accessCourseWriter =
                            new PrintWriter(socket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                accessCourseWriter.write("ACCESS_COURSES_STUDENT");
                accessCourseWriter.println();
                accessCourseWriter.flush();

                try {
                    accessCourseWriter2 =
                            new PrintWriter(socket.getOutputStream());
                    accessCourseWriter3 =
                            new PrintWriter(socket.getOutputStream());
                    int limiter = Integer.parseInt(reader.readLine());
                    String[] courses = new String[limiter];
                    for (int i = 0; i < limiter; i++) {
                        courses[i] = reader.readLine();
                    }
                    // reader.close();
                    String accessedCourse =
                            (String) JOptionPane.showInputDialog(null,
                                    "List of courses:", "Title",
                                    JOptionPane.QUESTION_MESSAGE, null, courses,
                                    courses[0]);
                    accessCourseWriter2.write(accessedCourse);
                    accessCourseWriter2.println();
                    accessCourseWriter2.flush();

                    int limiter2 = Integer.parseInt(reader.readLine());
                    System.out.println(
                            "Number of quizzes in total for the selected course: "
                                    + limiter2);
                    String[] quizzes = new String[limiter2];
                    for (int i = 0; i < limiter2; i++) {
                        quizzes[i] = reader.readLine();
                    }
                    String accessedQuiz = (String) JOptionPane.showInputDialog(
                            null, "List of quizzes for your course:", "Title",
                            JOptionPane.QUESTION_MESSAGE, null, quizzes,
                            quizzes[0]);

                    accessCourseWriter3.write(accessedQuiz);
                    accessCourseWriter3.println();
                    accessCourseWriter3.flush();

                    int receivedQuestionArrSize =
                            Integer.parseInt(reader.readLine());
                    int receivedAnsArrSize =
                            Integer.parseInt(reader.readLine());

                    String randQs = reader.readLine();
                    String randOs = reader.readLine();
                    System.out.println("Received question arr size: "
                            + receivedQuestionArrSize);
                    System.out.println(
                            "Received ans arr size: " + receivedAnsArrSize);
                    String[] takingQuizQuestions =
                            new String[receivedQuestionArrSize];
                    String[] takingQuizAns = new String[receivedAnsArrSize];
                    for (int i = 0; i < receivedQuestionArrSize; i++) {
                        takingQuizQuestions[i] = reader.readLine();
                        System.out.println(takingQuizQuestions[i]);
                    }
                    System.out.println("received questions");

                    String[][] questionArrays =
                            new String[receivedQuestionArrSize][receivedAnsArrSize
                                    / receivedQuestionArrSize];
                    for (int i = 0; i < receivedQuestionArrSize; i++) {
                        for (int j = 0;
                             j < (receivedAnsArrSize / receivedQuestionArrSize);
                             j++) {
                            questionArrays[i][j] = "[placeholder]";
                        }
                    }

                    System.out.println(
                            "received questions size: " + receivedQuestionArrSize);
                    String message = "ljhgf";

                    System.out.println(
                            "received answers size: " + receivedAnsArrSize);
                    /*

                    for (int i = 0; i < receivedQuestionArrSize; i++) {
                        for (int j = 0;
                             j < (receivedAnsArrSize / receivedQuestionArrSize);
                             j++) {
                            questionArrays[i][j] = reader.readLine();
                            System.out.println("received answers[" + i + "]["
                                    + j + "]: " + questionArrays[i][j]);
                        }
                    }
                    */
                    ArrayList<String> sqaList = new ArrayList<>();
                    ArrayList<String> sqqList =
                            new ArrayList<>(Arrays.asList(takingQuizQuestions));
                    System.out.println(sqqList.size());
                    for (int i = 0; i < receivedAnsArrSize; i++) {
                        sqaList.add(reader.readLine());
                    }

                    ArrayList<String[][]> quizListo = new ArrayList<>();
                    limiter = sqaList.size() / sqqList.size();
                    int itter = 0;
                    for (int i = 0; i < sqaList.size(); i += limiter) {
                        ArrayList<String> tempAnswersList = new ArrayList<>();
                        String[] tempAnswersArray; // = new String[limiter];
                        String[] tempCorrAns = new String[limiter];
                        String[] tempQuestion = new String[limiter];
                        for (int j = i; j < limiter + i; j++) {
                            tempAnswersList.add(sqaList.get(j));
                        }
                        try {
                            if (randOs.equals(
                                    "y")) { //(randList.get(1).equals("y"))
                                Collections.shuffle(tempAnswersList);
                            }
                            // Collections.shuffle(tempAnswersList);
                        } catch (Exception ex) {
                            System.out.println("Shuffle Error1");
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
                        // im not sure why it's not working
                        // System.out.println(quizListo.get(0)[1][0]);//changes
                        // between iterations (Should stay the same)
                        itter++;
                    }
                    /// System.out.print(QuizList.get(0)[1][0]);
                    try {
                        if (randQs.equals(
                                "y")) { //(randList.get(0).equals("y"))
                            Collections.shuffle(quizListo);
                        }
                        // Collections.shuffle(quizListo);
                    } catch (Exception exc) {
                        System.out.println("Shuffle Error2");
                    }

                    String[][][] quizArray = quizListo.toArray(
                            new String[quizListo.size()][3][limiter]);
                    System.out.println(quizListo.size());
                    System.out.println(quizArray.length);

                    UIManager.put("OptionPane.cancelButtonText", "Attach File");

                    for (int i = 0; i < receivedQuestionArrSize; i++) {
                        System.out.println(quizArray[i][1][0]); ///////////
                        System.out.println(quizArray[i][0]);
                        System.out.println(quizArray[i][2][0]);
                        String newStr = (String) JOptionPane.showInputDialog(
                                null, quizArray[i][1][0], "Title",
                                JOptionPane.QUESTION_MESSAGE, null, quizArray[i][0],
                                quizArray[i][2][0]);

                        if (newStr == null) {
                            JFileChooser fc2 = new JFileChooser();
                            fc2.setDialogTitle("Choose quiz answer file");
                            int val2 = fc2.showOpenDialog(null);
                            System.out.println(val2);

                            File f2 = fc2.getSelectedFile();
                            FileReader fr2 = new FileReader(f2);
                            BufferedReader bfr2 = new BufferedReader(fr2);

                            String line = bfr2.readLine();
                            String temp = bfr2.readLine();
                            while (temp != null) {
                                line += "$&*" + temp;
                                temp = bfr2.readLine();
                            }
                            takingQuizAns[i] = line;
                        } else {
                            takingQuizAns[i] = newStr;
                        }
                    }
                    UIManager.put("OptionPane.cancelButtonText", "Cancel");

                    // send back list of correct answers as well.
                    PrintWriter quizAttempt = null;
                    quizAttempt = new PrintWriter(socket.getOutputStream());
                    for (int i = 0; i < receivedQuestionArrSize; i++) {
                        quizAttempt.println(quizArray[i][1][0]);
                        quizAttempt.println(takingQuizAns[i]);
                    }
                    quizAttempt.flush();

                    // PrintWriter quizAttemptAns = null;
                    // quizAttemptAns =
                    //        new PrintWriter(socket.getOutputStream());
                    // for (int i = 0; i < receivedQuestionArrSize; i++) {
                    //    quizAttemptAns.println(quizArray[i][2][0]);
                    //}
                    // quizAttemptAns.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == studentEditAccount) {
                sendToServer("STUDENT_EDIT_ACCOUNT");
                String[] stuEditOptions = {
                        "Change username", "Change password", "Delete Account"};
                String chosenOption = createDropDown("Select an option",
                        "Title", stuEditOptions, stuEditOptions[0]);
                sendToServer(chosenOption);
                if (chosenOption.equals("Change username")) {
                    String newUserName =
                            JOptionPane.showInputDialog(null, "Enter new username");
                    sendToServer(newUserName);
                    try {
                        String errorCheck = reader.readLine();
                        if (errorCheck.equals("NOT_UNIQUE_USER")) {
                            JOptionPane.showMessageDialog(null,
                                    "Error: username is already taken", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (chosenOption.equals("Change password")) {
                    String newPass =
                            JOptionPane.showInputDialog(null, "Enter new password");
                    sendToServer(newPass);
                }
                if (chosenOption.equals("Delete Account")) {
                    sendToServer("Delete Account");

                    JOptionPane.showMessageDialog(null, "Account Deleted",
                            "Delete", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
            if (e.getSource() == viewGradedQuiz) {
                sendToServer("ViewGradedQuiz");
                try {
                    int viewQuizSize = Integer.parseInt(reader.readLine());
                    String[] stuQuizzes = new String[viewQuizSize];

                    for (int i = 0; i < viewQuizSize; i++) {
                        stuQuizzes[i] = reader.readLine();
                    }
                    String stuQuiz = (String) JOptionPane.showInputDialog(null,
                            "Which submission would you like to view?",
                            "Quiz Selection", JOptionPane.QUESTION_MESSAGE, null,
                            stuQuizzes, stuQuizzes[0]);
                    sendToServer(stuQuiz);

                    int viewedSize = Integer.parseInt(reader.readLine());
                    String[] viewedQuestions = new String[viewedSize];
                    String[] viewedAnswers = new String[viewedSize];
                    String[] viewedGrades = new String[viewedSize];

                    for (int i = 0; i < viewedSize; i++) {
                        viewedQuestions[i] = reader.readLine();
                    }
                    for (int i = 0; i < viewedSize; i++) {
                        viewedAnswers[i] = reader.readLine();
                        if (viewedAnswers[i].contains("$&*")) {
                            viewedAnswers[i] =
                                    viewedAnswers[i].replace("$&*", "\n");
                        }
                    }
                    for (int i = 0; i < viewedSize; i++) {
                        viewedGrades[i] = reader.readLine();
                    }

                    for (int i = 0; i < viewedSize; i++) {
                        JOptionPane.showMessageDialog(null,
                                "Question: " + viewedQuestions[i]
                                        + "\nYour answer: " + viewedAnswers[i]
                                        + "\nYour points: " + viewedGrades[i],
                                "Quiz Submission", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException es) {
                    es.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args) throws IOException {
        /*
        128.211.189.172 (this is the ip of the server running on Eric's
        computer) If you make changes to server and want to test them, make sure
        you are on localhost and not on the ip. If you are on localhost, you
        would have to run the Server class and then the Client class. If you are
        using the ip address, first change "localhost" to "128.211.189.172" Then
        you just have to run the Client, but that server has an entirely fresh
        set of text files, so you would have to create a bunch of things
        before testing*/
        socket = new Socket("localhost", 4242);
        // This writer lets the client send information to the server
        writer = new PrintWriter(socket.getOutputStream());
        // This reader lets the client receive information from the server
        reader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JOptionPane.showMessageDialog(
                null, "Welcome to the Learning Management System!");
        UIManager.put("OptionPane.yesButtonText", "Create Account");
        UIManager.put("OptionPane.noButtonText", "Log in");
        int createOrLogin = JOptionPane.showConfirmDialog(null,
                "Do you want to create an account or log in?", "",
                JOptionPane.YES_NO_OPTION);
        if (createOrLogin
                == JOptionPane.YES_OPTION) { // CREATE AN ACCOUNT; ACCOUNT CREATION;
            // CREATING AN ACCOUNT
            // writer.write("CREATING_ACCOUNT");
            // writer.println();
            sendToServer("CREATING_ACCOUNT");
            String errorMessage = "";

            try {
                do {
                    String newUsername =
                            JOptionPane.showInputDialog(null, "Enter username");
                    sendToServer(newUsername);
                    errorMessage = reader.readLine();
                    System.out.println("Received from server: " + errorMessage);

                    if (errorMessage.equals("ERROR_USERNAME_IS_TAKEN")) {
                        JOptionPane.showMessageDialog(null,
                                "Error: username is taken!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } while (errorMessage.equals("ERROR_USERNAME_IS_TAKEN"));
                String newPassword =
                        JOptionPane.showInputDialog(null, "Enter password");
                sendToServer(newPassword);

                String[] choices = {"Student", "Teacher"};
                String userChoice = (String) JOptionPane.showInputDialog(null,
                        "What is your choice?", "Choice?",
                        JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                sendToServer(userChoice);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error! User was not made properly.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        // if (createOrLogin
        //     == JOptionPane.NO_OPTION) { // LOG IN; LOGIN; LOGGING IN
        sendToServer("LOGGING_IN");
        String loginCheck;
        do {
            String loginUser =
                    JOptionPane.showInputDialog(null, "Enter username");
            if (loginUser == null) {
                return;
            }
            sendToServer(loginUser);

            String loginPass =
                    JOptionPane.showInputDialog(null, "Enter password");
            if (loginPass == null) {
                return;
            }
            sendToServer(loginPass);
            System.out.println("SOCKET IS CLOSED");
            loginCheck = reader.readLine();

            if (loginCheck.equals("Success!")) {
                JOptionPane.showMessageDialog(null, "Success!");
            }
            if (loginCheck.equals("Error!")) {
                JOptionPane.showMessageDialog(null,
                        "Incorrect login credentials!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } while (loginCheck.equals("Error!"));

        String accountType = reader.readLine();
        if (accountType.equals("Teacher")) {
            isTeacher = true;
            SwingUtilities.invokeLater(new Client());
        }
        if (accountType.equals("Student")) {
            isStudent = true;
            SwingUtilities.invokeLater(new Client());
        }

        // }
    }

    @Override
    public void run() {
        if (isTeacher) {
            JFrame teacherFrame = new JFrame("Teacher Page");
            teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            teacherFrame.setSize(640, 480);

            JPanel teacherPanel = new JPanel();

            createCourse = new JButton("Create course");
            createCourse.addActionListener(actionListener);
            accessCourses = new JButton("Access courses");
            accessCourses.addActionListener(actionListener);
            editAccount = new JButton("Edit account");
            editAccount.addActionListener(actionListener);
            viewQuiz = new JButton("View quiz submissions");
            viewQuiz.addActionListener(actionListener);

            teacherPanel.add(createCourse);
            teacherPanel.add(accessCourses);
            teacherPanel.add(editAccount);
            teacherPanel.add(viewQuiz);

            teacherFrame.getContentPane().add(BorderLayout.NORTH, teacherPanel);
            teacherFrame.setVisible(true);
        }
        if (isStudent) {
            JFrame studentFrame = new JFrame("Student Page");
            studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            studentFrame.setSize(640, 480);

            JPanel studentPanel = new JPanel();

            studentAccessCourse = new JButton("Access Courses");
            studentAccessCourse.addActionListener(actionListener);
            studentEditAccount = new JButton("Edit Account");
            studentEditAccount.addActionListener(actionListener);
            viewGradedQuiz = new JButton("View graded quiz");
            viewGradedQuiz.addActionListener(actionListener);

            studentPanel.add(studentAccessCourse);
            studentPanel.add(studentEditAccount);
            studentPanel.add(viewGradedQuiz);

            studentFrame.getContentPane().add(BorderLayout.NORTH, studentPanel);
            studentFrame.setVisible(true);
        }
    }

    public static void sendToServer(String message) {
        PrintWriter methodPw = null;

        try {
            methodPw = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        methodPw.write(message);
        methodPw.println();
        methodPw.flush();
    }

    public static String createDropDown(String message, String title,
                                        String[] dropdownlist, String defaultOption) {
        return (String) JOptionPane.showInputDialog(null, message, title,
                JOptionPane.QUESTION_MESSAGE, null, dropdownlist, defaultOption);
    }
} //
