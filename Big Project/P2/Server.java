import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Program Name: Server
 * A server class that takes in information sent by the client
 * to do things like set up the creation/login for student and teacher accounts.
 * It also handles the creation/accessing/editing of courses and quizzes.
 * Additionally, it lets students access/take courses and quizzes.
 * NOTE: ALL USES OF SYSTEM.OUT ARE SOLELY FOR DEBUGGING PURPOSES ONLY
 * User interaction is handled through the GUI exclusively
 *
 * @author Erick Moises Xu L, Luke Bowlin, Aidan Cummings, Eric Kang, and Ryan Newman
 * @version May 1, 2022
 */

public class Server implements Runnable {
    File userFile = new File("users.txt");
    FileOutputStream userFos = new FileOutputStream(userFile, true);
    PrintWriter userPw = new PrintWriter(userFos);
    FileReader userReader = new FileReader(userFile);
    BufferedReader userBfr = new BufferedReader(userReader);
    ArrayList<String> userList = new ArrayList<>();

    Socket socket;

    File passwordFile = new File("passwords.txt");
    FileOutputStream passwordFos = new FileOutputStream(passwordFile, true);
    PrintWriter passwordPw = new PrintWriter(passwordFos);
    FileReader passReader = new FileReader(passwordFile);
    BufferedReader passBfr = new BufferedReader(passReader);
    ArrayList<String> passList = new ArrayList<>();

    File accountTypeFile = new File("accountTypes.txt");
    FileOutputStream typeFos = new FileOutputStream(accountTypeFile, true);
    PrintWriter typePw = new PrintWriter(typeFos);
    FileReader typeReader = new FileReader(accountTypeFile);
    BufferedReader typeBfr = new BufferedReader(typeReader);
    ArrayList<String> typeList = new ArrayList<>();

    File courseFile = new File("courses.txt");
    FileOutputStream courseFos = new FileOutputStream(courseFile, true);
    PrintWriter coursePw;
    FileReader courseReader = new FileReader(courseFile);
    BufferedReader courseBfr = new BufferedReader(courseReader);
    ArrayList<String> courseList = new ArrayList<>();
    /*
 String courseLine = courseBfr.readLine();
 while (courseLine != null) {
     courseList.add(courseLine);
     courseLine = courseBfr.readLine();
 }
 courseBfr.close();*/

    File listOfQuizzes = new File("listOfQuizzes.txt");
    FileOutputStream LOQ_FOS = new FileOutputStream(listOfQuizzes, true);
    PrintWriter LOQ_PW;
    FileReader LOQ_READER = new FileReader(listOfQuizzes);
    BufferedReader LOQ_Bfr = new BufferedReader(LOQ_READER);
    ArrayList<String> quizList = new ArrayList<>();

    File lsa = new File("quizAttempts.txt");
    FileOutputStream lsaFos = new FileOutputStream(lsa, true);
    PrintWriter lsaPw;
    FileReader lsaReader = new FileReader(lsa);
    BufferedReader lsaBfr = new BufferedReader(lsaReader);
    ArrayList<String> lsaList = new ArrayList<>();

    public Server(Socket socket) throws FileNotFoundException {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242); // 4242 portnumber

        while (true) {
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            PrintWriter accessPw = new PrintWriter(socket.getOutputStream());
            PrintWriter pw2 = new PrintWriter(socket.getOutputStream());
            PrintWriter pwOfQuizzesForSelectedCourse =
                    new PrintWriter(socket.getOutputStream());
            PrintWriter pwForStudentTakingQuiz =
                    new PrintWriter(socket.getOutputStream());
            PrintWriter editQuizPw = new PrintWriter(socket.getOutputStream());
            PrintWriter editQuizQPW = new PrintWriter(socket.getOutputStream());

            Scanner in = new Scanner(socket.getInputStream());

            String userLine = userBfr.readLine();
            while (userLine != null) {
                userList.add(userLine);
                userLine = userBfr.readLine();
            }
            userBfr.close();

            String passLine = passBfr.readLine();
            while (passLine != null) {
                passList.add(passLine);
                passLine = passBfr.readLine();
            }

            String typeLine = typeBfr.readLine();
            while (typeLine != null) {
                typeList.add(typeLine);
                typeLine = typeBfr.readLine();
            }

            String courseLine = courseBfr.readLine();
            while (courseLine != null) {
                courseList.add(courseLine);
                courseLine = courseBfr.readLine();
            }

            String quizListLine = LOQ_Bfr.readLine();
            while (quizListLine != null) {
                quizList.add(quizListLine);
                quizListLine = LOQ_Bfr.readLine();
            }

            String lsaLine = lsaBfr.readLine();
            while (lsaLine != null) {
                lsaList.add(lsaLine);
                lsaLine = lsaBfr.readLine();
            }

            courseBfr.close();

            typeBfr.close();

            passBfr.close();

            while (in.hasNextLine()) {
                String checker = in.nextLine();
                if (checker.equals("CREATING_ACCOUNT")) {
                    System.out.println(
                            "The server knows the client is creating an account");
                    String createUser = in.nextLine();
                    boolean failedUser = false;
                    for (int i = 0; i < userList.size(); i++) {
                        if (createUser.equals(userList.get(i))) {
                            sendToClient("ERROR_USERNAME_IS_TAKEN");
                            failedUser = true;
                            break;
                        }
                    }
                    if (!failedUser) {
                        sendToClient("");
                    }

                    String createPass = in.nextLine();
                    String accountType = in.nextLine();
                    // print statements can be deleted later they are just here
                    // to check if the server is receiving from the client
                    if (!failedUser) {
                        System.out.println("Created Username: " + createUser);
                        userPw.println(createUser);
                        userList.add(createUser);
                        userPw.close();
                        System.out.println("Created Password: " + createPass);
                        passwordPw.println(createPass);
                        passList.add(createPass);
                        passwordPw.close();
                        System.out.println("Account type: " + accountType);
                        typePw.println(accountType);
                        typeList.add(accountType);
                        typePw.close();
                    }
                }
                if (checker.equals("LOGGING_IN")) {
                    System.out.println("Server knows client is logging in");

                    boolean usercheckin = false;
                    String loginUser = "";
                    String loginPass = "";
                    do {
                        loginUser = in.nextLine();
                        loginPass = in.nextLine();
                        System.out.println("Login Username: " + loginUser);
                        System.out.println("Login Password: " + loginPass);

                        int index = -1;
                        for (int i = 0; i < userList.size(); i++) {
                            System.out.println("SIze" + userList.size());
                            System.out.println(
                                    "usercheck beginning" + userList.get(i));

                            if (loginUser.equals(userList.get(i))
                                    && loginPass.equals(passList.get(i))) {
                                usercheckin = true;
                                index = i;
                            }
                        }
                        if (usercheckin == true) {
                            System.out.println(
                                    "usercheck " + userList.get(index));
                            System.out.println(
                                    "passcheck " + passList.get(index));
                            System.out.println("SUCCESSS!!!!!!1");
                            pw.write("Success!");
                            pw.println();
                            pw.write(typeList.get(index));
                            pw.println();
                            pw.flush();

                        } else {
                            System.out.println("ERROR!!!!!!1");
                            pw.write("Error!");
                            pw.println();
                            pw.flush();
                        }
                    } while (usercheckin == false);

                    do {
                        String checker2 = in.nextLine();
                        System.out.println(checker2);

                        if (checker2.equals("CREATING_COURSE")) {
                            System.out.println(
                                    "Server knows client is creating course");
                            String newCourseName = in.nextLine();

                            courseList.add(newCourseName);
                            addCourse(newCourseName);
                        }
                        if (checker2.equals("ACCESS_COURSES_STUDENT")) {
                            System.out.println(
                                    "Server knows Student is accessing course");

                            int actualCourseListSize = 0;
                            for (int i = 0; i < courseList.size(); i++) {
                                if (!courseList.get(i).equals("")) {
                                    actualCourseListSize++;
                                }
                            }
                            System.out.println("Size " + actualCourseListSize);
                            // accessPw.println(courseList.size());
                            accessPw.println(actualCourseListSize);
                            for (int i = 0; i < courseList.size(); i++) {
                                if (!courseList.get(i).equals("")) {
                                    accessPw.write(courseList.get(i));
                                    accessPw.println();
                                }
                            }
                            accessPw.flush();
                            String receivedCourse = null;
                            while (in.hasNextLine()) {
                                receivedCourse = in.nextLine();
                                break;
                            }

                            System.out.println("Received Course"
                                    + receivedCourse); // STUDENT ACCESS QUIZ

                            int SelectedCourseNumOfQuiz = 0;

                            for (int i = 0; i < quizList.size(); i++) {
                                if (quizList.get(i).contains(receivedCourse)) {
                                    SelectedCourseNumOfQuiz++;
                                }
                            }
                            pw2.println(SelectedCourseNumOfQuiz);
                            pw2.flush();
                            for (int i = 0; i < quizList.size(); i++) {
                                if (quizList.get(i).contains(receivedCourse)) {
                                    pwOfQuizzesForSelectedCourse.write(
                                            quizList.get(i));
                                    pwOfQuizzesForSelectedCourse.println();
                                }
                            }
                            pwOfQuizzesForSelectedCourse.flush();

                            String receivedQuiz = null;
                            while (in.hasNextLine()) {
                                receivedQuiz = in.nextLine();
                                break;
                            }

                            System.out.println("Received Quiz" + receivedQuiz);

                            // RQA = received quiz answers
                            File recQuizAns =
                                    new File(receivedQuiz + "answers.txt");
                            // FileOutputStream recQuizFos = new
                            // FileOutputStream(recQuizFile, true); PrintWriter
                            // recQuizPw;
                            FileReader rqaReader = new FileReader(recQuizAns);
                            BufferedReader rqaBfr =
                                    new BufferedReader(rqaReader);
                            ArrayList<String> rqaList = new ArrayList<>();

                            // RQQ = received quiz questions
                            File rqqFile =
                                    new File(receivedQuiz + "questions.txt");
                            // FileOutputStream recQuizFos = new
                            // FileOutputStream(recQuizFile, true); PrintWriter
                            // recQuizPw;
                            FileReader rqqReader = new FileReader(rqqFile);
                            BufferedReader rqqBfr =
                                    new BufferedReader(rqqReader);
                            ArrayList<String> rqqList = new ArrayList<>();

                            File recRand =
                                    new File(receivedQuiz + "random.txt");
                            // FileOutputStream recQuizFos = new
                            // FileOutputStream(recQuizFile, true); PrintWriter
                            // recQuizPw;
                            FileReader RReader = new FileReader(recRand);
                            BufferedReader RBfr = new BufferedReader(RReader);
                            ArrayList<String> RList = new ArrayList<>();

                            String RLine = RBfr.readLine();
                            while (RLine != null) {
                                RList.add(RLine); //////RList is RANDOMLIST
                                RLine = RBfr.readLine();
                            }
                            RBfr.close();
                            String rqaLine = rqaBfr.readLine();
                            while (rqaLine != null) {
                                rqaList.add(rqaLine);
                                rqaLine = rqaBfr.readLine();
                            }
                            rqaBfr.close();

                            String rqqLine = rqqBfr.readLine();
                            while (rqqLine != null) {
                                rqqList.add(rqqLine);
                                rqqLine = rqqBfr.readLine();
                            }
                            rqqBfr.close();

                            pwForStudentTakingQuiz.println(rqqList.size());
                            // pwForStudentTakingQuiz.println(RList.get(0));
                            // pwForStudentTakingQuiz.println(RList.get(1));
                            int counter = 0;
                            for (int i = 0; i < rqaList.size(); i++) {
                                // this should print out all the questions
                                if (!rqaList.get(i).equals("")) {
                                    counter++;
                                }
                            }
                            pwForStudentTakingQuiz.println(counter);
                            System.out.println(RList.get(0));
                            System.out.println(RList.get(1));
                            pwForStudentTakingQuiz.println(RList.get(0));
                            pwForStudentTakingQuiz.println(RList.get(1));
                            for (int i = 0; i < rqqList.size(); i++) {
                                if (!rqqList.get(i).equals("")) {
                                    pwForStudentTakingQuiz.println(
                                            rqqList.get(i));
                                }
                            }
                            for (int i = 0; i < rqaList.size(); i++) {
                                // this should print out all the questions
                                if (!rqaList.get(i).equals("")) {
                                    pwForStudentTakingQuiz.println(
                                            rqaList.get(i));
                                }
                            }

                            /*for (int i = 2; i < rqaList.size(); i++) {
                                //this should print out all the answers
                                if (!rqaList.get(i).equals("")) {
                                    if (!receivedQuiz.contains("?")) {
                                        pwForStudentTakingQuiz.println(rqaList.get(i));
                                    }

                                }
                            }*/

                            // pwForStudentTakingQuiz.println("message");
                            pwForStudentTakingQuiz.flush();

                            Timestamp timestamp =
                                    new Timestamp(System.currentTimeMillis());
                            // SAF = student answer file
                            File saf = new File(loginUser + " " + receivedQuiz
                                    + " responses.txt");
                            // do we need to overwrite the file when it is taken
                            // multiple times
                            FileOutputStream safFos =
                                    new FileOutputStream(saf, false);
                            PrintWriter safPw = new PrintWriter(safFos);
                            FileReader safReader = new FileReader(saf);
                            BufferedReader safBfr =
                                    new BufferedReader(safReader);
                            safPw.println(timestamp);

                            for (int i = 0; i < rqqList.size(); i++) {
                                // safPw.write(rqqList.get(i));
                                // safPw.println();
                                String ques = in.nextLine();
                                String ans = in.nextLine();
                                safPw.println(ques);
                                safPw.println(ans);

                                safPw.println("Not Graded");
                            }
                            safPw.flush();
                            safPw.close();

                            // lsa = List of Student Answers

                            lsaPw = new PrintWriter(lsaFos);
                            boolean contains = false;
                            for (int i = 0; i < lsaList.size(); i++) {
                                if (lsaList.get(i).equals(
                                        loginUser + " " + receivedQuiz)) {
                                    contains = true;
                                }
                            }
                            if (!contains) {
                                lsaPw.println(loginUser + " " + receivedQuiz);
                                lsaList.add(loginUser + " " + receivedQuiz);
                            }
                            lsaPw.flush();
                            lsaPw.close();
                        }
                        if (checker2.equals("STUDENT_EDIT_ACCOUNT")) {
                            String checker3 = in.nextLine();
                            System.out.println("Checker 3 is " + checker3);

                            if (checker3.equals("Change username")) {
                                int userIndex = 0;
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(loginUser)) {
                                        userIndex = i;
                                        break;
                                    }
                                }
                                String newUserName = in.nextLine();
                                boolean failedUniqueTest = false;
                                for (int i = 0; i < userList.size(); i++) {
                                    if (newUserName.equals(userList.get(i))) {
                                        sendToClient("NOT_UNIQUE_USER");
                                        failedUniqueTest = true;
                                        break;
                                    }
                                }
                                String oldUserName = userList.get(userIndex);
                                if (!failedUniqueTest) {
                                    userList.set(userIndex, newUserName);
                                    File userOverwrite = new File("users.txt");
                                    FileWriter userOverwriter =
                                            new FileWriter(userOverwrite, false);
                                    for (int i = 0; i < userList.size(); i++) {
                                        userOverwriter.write(
                                                userList.get(i) + "\n");
                                    }
                                    userOverwriter.close();

                                    for (int i = 0; i < lsaList.size(); i++) {
                                        if (lsaList.get(i).contains(
                                                oldUserName)) {
                                            File stuQuizResFile =
                                                    new File(lsaList.get(i)
                                                            + " responses.txt");
                                            File newStuQuizResFile = new File(
                                                    lsaList.get(i).replace(
                                                            oldUserName, newUserName)
                                                            + " responses.txt");
                                            stuQuizResFile.renameTo(
                                                    newStuQuizResFile);
                                            lsaList.set(i,
                                                    lsaList.get(i).replace(
                                                            oldUserName, newUserName));
                                        }
                                    }
                                    File qAOverwrite =
                                            new File("quizAttempts.txt");
                                    FileWriter qAOverwriter =
                                            new FileWriter(qAOverwrite, false);
                                    for (int i = 0; i < lsaList.size(); i++) {
                                        qAOverwriter.write(
                                                lsaList.get(i) + "\n");
                                    }
                                    qAOverwriter.close();
                                }
                            }
                            if (checker3.equals("Change password")) {
                                int passIndex = 0;
                                for (int i = 0; i < passList.size(); i++) {
                                    if (passList.get(i).equals(loginPass)) {
                                        if (userList.get(i).equals(loginUser)) {
                                            passIndex = i;
                                            break;
                                        }
                                    }
                                }
                                String newPass = in.nextLine();
                                passList.set(passIndex, newPass);
                                File passOverwrite = new File("passwords.txt");
                                FileWriter passOverwriter =
                                        new FileWriter(passOverwrite, false);
                                for (int i = 0; i < passList.size(); i++) {
                                    passOverwriter.write(
                                            passList.get(i) + "\n");
                                }
                                passOverwriter.close();
                            }
                            if (checker3.equals("Delete Account")) {
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(loginUser)) {
                                        userList.remove(i);
                                        passList.remove(i);
                                        typeList.remove(i);
                                        break;
                                    }
                                }
                                File userOverwrite = new File("users.txt");
                                FileWriter userOverwriter =
                                        new FileWriter(userOverwrite, false);
                                for (int i = 0; i < userList.size(); i++) {
                                    userOverwriter.write(
                                            userList.get(i) + "\n");
                                }
                                userOverwriter.close();

                                File passOverwrite = new File("passwords.txt");
                                FileWriter passOverwriter =
                                        new FileWriter(passOverwrite, false);
                                for (int i = 0; i < passList.size(); i++) {
                                    passOverwriter.write(
                                            passList.get(i) + "\n");
                                }
                                passOverwriter.close();

                                File typeOverwrite =
                                        new File("accountTypes.txt");
                                FileWriter typeOverwriter =
                                        new FileWriter(typeOverwrite, false);
                                for (int i = 0; i < typeList.size(); i++) {
                                    typeOverwriter.write(
                                            typeList.get(i) + "\n");
                                }
                                typeOverwriter.close();
                            }
                        }
                        if (checker2.equals("TEACH_EDIT_ACCOUNT")) {
                            System.out.println("debug success");
                            String checker3 = in.nextLine();
                            System.out.println("checker 3 is " + checker3);

                            if (checker3.equals("Change User Name")) {
                                int userIndex = 0;

                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(loginUser)) {
                                        userIndex = i;
                                        break;
                                    }
                                }
                                String newUser = in.nextLine();
                                boolean notUniqueUser = false;
                                for (int i = 0; i < userList.size(); i++) {
                                    if (newUser.equals(userList.get(i))) {
                                        sendToClient("NOT_UNIQUE_ERROR");
                                        notUniqueUser = true;
                                        break;
                                    }
                                }
                                if (!notUniqueUser) {
                                    sendToClient("");
                                }
                                if (!notUniqueUser) {
                                    userList.set(userIndex, newUser);
                                    File userOverwrite = new File("users.txt");
                                    FileWriter userOverwriter =
                                            new FileWriter(userOverwrite, false);
                                    for (int i = 0; i < userList.size(); i++) {
                                        userOverwriter.write(
                                                userList.get(i) + "\n");
                                    }
                                    userOverwriter.close();
                                }
                            }
                            if (checker3.equals("Change Password")) {
                                int passIndex = 0;
                                for (int i = 0; i < passList.size(); i++) {
                                    if (passList.get(i).equals(loginPass)) {
                                        if (userList.get(i).equals(loginUser)) {
                                            passIndex = i;
                                            break;
                                        }
                                    }
                                }
                                String newPass = in.nextLine();
                                passList.set(passIndex, newPass);
                                File passOverwrite = new File("passwords.txt");
                                FileWriter passOverwriter =
                                        new FileWriter(passOverwrite, false);
                                for (int i = 0; i < passList.size(); i++) {
                                    passOverwriter.write(
                                            passList.get(i) + "\n");
                                }
                                passOverwriter.close();
                            }
                            if (checker3.equals("Delete Account")) {
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).equals(loginUser)) {
                                        userList.remove(i);
                                        passList.remove(i);
                                        typeList.remove(i);
                                        break;
                                    }
                                }
                                File userOverwrite = new File("users.txt");
                                FileWriter userOverwriter =
                                        new FileWriter(userOverwrite, false);
                                for (int i = 0; i < userList.size(); i++) {
                                    userOverwriter.write(
                                            userList.get(i) + "\n");
                                }
                                userOverwriter.close();

                                File passOverwrite = new File("passwords.txt");
                                FileWriter passOverwriter =
                                        new FileWriter(passOverwrite, false);
                                for (int i = 0; i < passList.size(); i++) {
                                    passOverwriter.write(
                                            passList.get(i) + "\n");
                                }
                                passOverwriter.close();

                                File typeOverwrite =
                                        new File("accountTypes.txt");
                                FileWriter typeOverwriter =
                                        new FileWriter(typeOverwrite, false);
                                for (int i = 0; i < typeList.size(); i++) {
                                    typeOverwriter.write(
                                            typeList.get(i) + "\n");
                                }
                                typeOverwriter.close();
                            }
                        }
                        if (checker2.equals("ACCESS_COURSES")) {
                            System.out.println(
                                    "Server knows client is accessing course");
                            int actualCourseListSize = 0;
                            for (int i = 0; i < courseList.size(); i++) {
                                if (!courseList.get(i).equals("")) {
                                    actualCourseListSize++;
                                }
                            }
                            System.out.println("Size " + actualCourseListSize);
                            // accessPw.println(courseList.size());
                            accessPw.println(actualCourseListSize);
                            for (int i = 0; i < courseList.size(); i++) {
                                if (!courseList.get(i).equals("")) {
                                    accessPw.write(courseList.get(i));
                                    accessPw.println();
                                }
                            }

                            accessPw.flush();

                            String checker3 = null;
                            while (in.hasNextLine()) {
                                checker3 = in.nextLine();
                                System.out.println("Checker 3 is " + checker3);
                                break;
                            }

                            if (checker3.equals("EDIT_COURSE_NAME")) {
                                String receivedCourse = in.nextLine();
                                System.out.println(
                                        "Received course: " + receivedCourse);
                                String newCourseName = in.nextLine();
                                for (int i = 0; i < courseList.size(); i++) {
                                    if (courseList.get(i).equals(
                                            receivedCourse)) {
                                        courseList.set(i, newCourseName);
                                    }
                                }
                                File courseOverwrite = new File("courses.txt");
                                FileWriter courseOverwriter =
                                        new FileWriter(courseOverwrite, false);
                                for (int i = 0; i < courseList.size(); i++) {
                                    courseOverwriter.write(
                                            courseList.get(i) + "\n");
                                }
                                courseOverwriter.close();

                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).contains(
                                            receivedCourse)) {
                                        File quiQue = new File(
                                                quizList.get(i) + "questions.txt");
                                        File quiAns = new File(
                                                quizList.get(i) + "answers.txt");
                                        File quiRan = new File(
                                                quizList.get(i) + "random.txt");
                                        quizList.set(i,
                                                quizList.get(i).replace(
                                                        receivedCourse, newCourseName));
                                        File nQuiQue = new File(
                                                quizList.get(i) + "questions.txt");
                                        File nQuiAns = new File(
                                                quizList.get(i) + "answers.txt");
                                        File nQuiRan = new File(
                                                quizList.get(i) + "random.txt");
                                        quiQue.renameTo(nQuiQue);
                                        quiAns.renameTo(nQuiAns);
                                        quiRan.renameTo(nQuiRan);
                                    }
                                }
                                File quizLisOverwrite =
                                        new File("listOfQuizzes.txt");
                                FileWriter quizLisOverwriter =
                                        new FileWriter(quizLisOverwrite, false);
                                for (int i = 0; i < quizList.size(); i++) {
                                    quizLisOverwriter.write(
                                            quizList.get(i) + "\n");
                                }
                                quizLisOverwriter.close();

                                for (int i = 0; i < lsaList.size(); i++) {
                                    if (lsaList.get(i).contains(
                                            receivedCourse)) {
                                        File stuQuizResFile = new File(
                                                lsaList.get(i) + " responses.txt");
                                        File newStuQuizResFile = new File(
                                                lsaList.get(i).replace(
                                                        receivedCourse, newCourseName)
                                                        + " responses.txt");
                                        stuQuizResFile.renameTo(
                                                newStuQuizResFile);
                                        lsaList.set(i,
                                                lsaList.get(i).replace(
                                                        receivedCourse, newCourseName));
                                    }
                                }
                                File qAOverwrite = new File("quizAttempts.txt");
                                FileWriter qAOverwriter =
                                        new FileWriter(qAOverwrite, false);
                                for (int i = 0; i < lsaList.size(); i++) {
                                    qAOverwriter.write(lsaList.get(i) + "\n");
                                }
                                qAOverwriter.close();
                            }
                            if (checker3.equals("ADD_QUIZ")) {
                                FileOutputStream loqFos =
                                        new FileOutputStream(listOfQuizzes, true);
                                PrintWriter LOQ_PW = new PrintWriter(LOQ_FOS);
                                /*FileReader loqReader = new FileReader();
                                BufferedReader loqBfr = new BufferedReader();
                                ArrayList<String> loqList = new ArrayList<>();*/
                                System.out.println(
                                        "Server knows client is adding a quiz");
                                String courseName = in.nextLine();
                                System.out.println(
                                        "Server knows client is accessing course "
                                                + courseName);
                                String quizName = in.nextLine();
                                System.out.println("Quiz name is " + quizName);
                                quizList.add(courseName + quizName);

                                File quizAnsFile = new File(
                                        courseName + quizName + "answers.txt");
                                FileOutputStream quizAnsFos =
                                        new FileOutputStream(quizAnsFile, true);
                                PrintWriter quizAnsPw =
                                        new PrintWriter(quizAnsFos);
                                FileReader quizAnsReader =
                                        new FileReader(quizAnsFile);
                                File quizQuestFile = new File(
                                        courseName + quizName + "questions.txt");
                                FileOutputStream quizQuestFos =
                                        new FileOutputStream(quizQuestFile, true);
                                PrintWriter quizQuestPw =
                                        new PrintWriter(quizQuestFos);
                                FileReader quizQuestReader =
                                        new FileReader(quizQuestFile);

                                String randomFilename =
                                        courseName + quizName + "random.txt";
                                File randomFile = new File(randomFilename);
                                randomFile.createNewFile();
                                FileOutputStream randomFos =
                                        new FileOutputStream(randomFile, true);
                                PrintWriter randomPw =
                                        new PrintWriter(randomFos);

                                String numQuestionsStr = in.nextLine();
                                System.out.println(
                                        "Number of questions: " + numQuestionsStr);
                                String numAnswersStr = in.nextLine();

                                String randTemp = in.nextLine();
                                System.out.println(randTemp + "<-");
                                randomPw.println(randTemp);
                                randTemp = in.nextLine();
                                randomPw.println(randTemp);
                                System.out.println(randTemp + "<-");

                                int numQuestions =
                                        Integer.parseInt(numQuestionsStr);
                                // String[] questions = new
                                // String[numQuestions];
                                int numAnswers =
                                        Integer.parseInt(numAnswersStr);
                                LOQ_PW.write(courseName + quizName);
                                LOQ_PW.println();

                                // quizPw.println("Number of questions: " +
                                // numQuestions); quizPw.println("Number of
                                // answers per question: " + numAnswers);

                                for (int i = 0; i < numQuestions; i++) {
                                    String str2 = in.nextLine();
                                    System.out.println(
                                            "Received question: " + str2);
                                    quizQuestPw.println(str2);
                                    for (int j = 0; j < numAnswers * 2; j++) {
                                        String str = in.nextLine();
                                        System.out.println(
                                                "Received answer: " + str);
                                        quizAnsPw.println(str);
                                    }
                                }
                                LOQ_PW.flush();
                                quizAnsPw.close();
                                quizQuestPw.close();
                                randomPw.close();
                            }
                            if (checker3.equals("EDIT_QUIZ")) {
                                String selcCourse = in.nextLine();
                                int numQuiz = 0;
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).contains(selcCourse)) {
                                        numQuiz++;
                                    }
                                }
                                sendToClient("" + numQuiz);

                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).contains(selcCourse))
                                        editQuizPw.write(quizList.get(i));
                                    editQuizPw.println();
                                }
                                editQuizPw.flush();
                                String quizName = in.nextLine();
                                System.out.println(
                                        "teacher wants to edit quiz: " + quizName);
                                File quizQuestFile =
                                        new File(quizName + "questions.txt");
                                File quizAnsFile =
                                        new File(quizName + "answers.txt");
                                File quizRanFile =
                                        new File(quizName + "random.txt");
                                // System.out.p
                                int quizIndex = 0;
                                for (int i = 0; i < quizList.size(); i++) {
                                    if (quizList.get(i).equals(quizName)) {
                                        quizIndex = i;
                                        break;
                                    }
                                }
                                String editOption = in.nextLine();
                                System.out.println(
                                        "teachers wants to: " + editOption);

                                if (editOption.equals("EDIT_TITLE")) {
                                    System.out.println("success");
                                    String newName = in.nextLine();
                                    newName = selcCourse + newName;

                                    System.out.println(
                                            "new quiz name is: " + newName);
                                    quizList.set(quizIndex, newName);
                                    File quizOverwrite =
                                            new File("listOfQuizzes.txt");
                                    FileWriter quizOverwriter =
                                            new FileWriter(quizOverwrite, false);
                                    for (int i = 0; i < quizList.size(); i++) {
                                        quizOverwriter.write(
                                                quizList.get(i) + "\n");
                                    }
                                    quizOverwriter.close();

                                    String newQuizFileName =
                                            newName + "questions.txt";
                                    File newQuizFile =
                                            new File(newQuizFileName);
                                    quizQuestFile.renameTo(newQuizFile);

                                    String newQuizAnsName =
                                            newName + "answers.txt";
                                    File newQuizAnsFile =
                                            new File(newQuizAnsName);
                                    quizAnsFile.renameTo(newQuizAnsFile);

                                    String newQuizRanName =
                                            newName + "random.txt";
                                    File newQuizRanFile =
                                            new File(newQuizRanName);
                                    quizRanFile.renameTo(newQuizRanFile);

                                    for (int i = 0; i < lsaList.size(); i++) {
                                        if (lsaList.get(i).contains(quizName)) {
                                            File stuQuizResFile =
                                                    new File(lsaList.get(i)
                                                            + " responses.txt");
                                            String temp =
                                                    lsaList.get(i).substring(0,
                                                            lsaList.get(i).indexOf(
                                                                    quizName));
                                            File newStuQuizResFile =
                                                    new File(temp + newName
                                                            + " responses.txt");
                                            stuQuizResFile.renameTo(
                                                    newStuQuizResFile);
                                            lsaList.set(i, temp + newName);
                                        }
                                    }
                                    File qAOverwrite =
                                            new File("quizAttempts.txt");
                                    FileWriter qAOverwriter =
                                            new FileWriter(qAOverwrite, false);
                                    for (int i = 0; i < lsaList.size(); i++) {
                                        qAOverwriter.write(
                                                lsaList.get(i) + "\n");
                                    }
                                    qAOverwriter.close();
                                }
                                if (editOption.equals("EDIT_QUESTION")) {
                                    File questionFile =
                                            new File(quizName + "questions.txt");
                                    // FileOutputStream qFos = new
                                    // FileOutputStream(questionFile, true);
                                    // PrintWriter qPw;
                                    FileReader qReader =
                                            new FileReader(questionFile);
                                    BufferedReader qBfr =
                                            new BufferedReader(qReader);
                                    ArrayList<String> questList =
                                            new ArrayList<>();

                                    String questLine = qBfr.readLine();
                                    while (questLine != null) {
                                        questList.add(questLine);
                                        questLine = qBfr.readLine();
                                    }
                                    qBfr.close();

                                    sendToClient(
                                            String.valueOf(questList.size()));
                                    for (int i = 0; i < questList.size(); i++) {
                                        editQuizQPW.write(questList.get(i));
                                        editQuizQPW.println();
                                        System.out.println("Sent question: "
                                                + questList.get(i));
                                    }
                                    editQuizQPW.flush();

                                    String receivedQuestion = in.nextLine();
                                    int questionIndex = 0;
                                    for (int i = 0; i < questList.size(); i++) {
                                        if (questList.get(i).equals(
                                                receivedQuestion)) {
                                            questionIndex = i;
                                            break;
                                        }
                                    }
                                    String newQuestName = in.nextLine();
                                    questList.set(questionIndex, newQuestName);

                                    FileWriter questOverwriter =
                                            new FileWriter(questionFile, false);
                                    for (int i = 0; i < questList.size(); i++) {
                                        questOverwriter.write(
                                                questList.get(i) + "\n");
                                    }
                                    questOverwriter.close();
                                }
                                if (editOption.equals("EDIT_ANSWER")) {
                                    File ansFile =
                                            new File(quizName + "answers.txt");
                                    FileReader ansReader =
                                            new FileReader(ansFile);
                                    BufferedReader ansBfr =
                                            new BufferedReader(ansReader);
                                    ArrayList<String> ansList =
                                            new ArrayList<>();

                                    String ansLine = ansBfr.readLine();
                                    while (ansLine != null) {
                                        ansList.add(ansLine);
                                        ansLine = ansBfr.readLine();
                                    }
                                    ansBfr.close();

                                    int trueAnsListSize = 0;
                                    for (int i = 0; i < ansList.size(); i++) {
                                        if (!ansList.get(i).equals("")) {
                                            trueAnsListSize++;
                                        }
                                    }

                                    sendToClient(
                                            String.valueOf(trueAnsListSize));
                                    // sendToClient(
                                    // String.valueOf(ansList.size()));
                                    for (int i = 0; i < ansList.size(); i++) {
                                        if (!ansList.get(i).equals("")) {
                                            editQuizQPW.write(ansList.get(i));
                                            editQuizQPW.println();
                                            System.out.println("Sent answer: "
                                                    + ansList.get(i));
                                        }
                                    }
                                    editQuizQPW.flush();

                                    String receivedAns = in.nextLine();
                                    int ansIndex = 0;
                                    for (int i = 0; i < ansList.size(); i++) {
                                        if (ansList.get(i).equals(
                                                receivedAns)) {
                                            ansIndex = i;
                                            break;
                                        }
                                    }
                                    String newAnsName = in.nextLine();
                                    ansList.set(ansIndex, newAnsName);

                                    FileWriter ansOverwriter =
                                            new FileWriter(ansFile, false);
                                    for (int i = 0; i < ansList.size(); i++) {
                                        ansOverwriter.write(
                                                ansList.get(i) + "\n");
                                    }
                                    ansOverwriter.close();
                                }
                                if (editOption.equals("Delete Quiz")) {
                                    quizQuestFile.delete();
                                    quizAnsFile.delete();
                                    quizRanFile.delete();
                                    quizList.remove(quizIndex);
                                    File quizOverwrite =
                                            new File("listOfQuizzes.txt");
                                    FileWriter quizOverwriter =
                                            new FileWriter(quizOverwrite, false);
                                    for (int i = 0; i < quizList.size(); i++) {
                                        quizOverwriter.write(
                                                quizList.get(i) + "\n");
                                    }
                                    quizOverwriter.close();

                                    for (int i = 0; i < lsaList.size(); i++) {
                                        if (lsaList.get(i).contains(quizName)) {
                                            File stuQuizResFile =
                                                    new File(lsaList.get(i)
                                                            + " responses.txt");
                                            stuQuizResFile.delete();
                                            lsaList.remove(i);
                                        }
                                    }
                                    File qAOverwrite =
                                            new File("quizAttempts.txt");
                                    FileWriter qAOverwriter =
                                            new FileWriter(qAOverwrite, false);
                                    for (int i = 0; i < lsaList.size(); i++) {
                                        qAOverwriter.write(
                                                lsaList.get(i) + "\n");
                                    }
                                    qAOverwriter.close();
                                }
                            }
                            if (checker3.equals("ADD_QUIZ_FILE")) {
                                String fileName = in.nextLine();
                                String quizName = in.nextLine();
                                FileOutputStream loqFos =
                                        new FileOutputStream(listOfQuizzes, true);
                                PrintWriter LOQ_PW = new PrintWriter(LOQ_FOS);
                                LOQ_PW.println(quizName);
                                LOQ_PW.close();

                                System.out.println(
                                        "recevied filename " + fileName);
                                File questionFile = new File(fileName);
                                FileOutputStream uploadedQFos =
                                        new FileOutputStream(questionFile, true);
                                PrintWriter uploadedQPw =
                                        new PrintWriter(uploadedQFos);
                                String receivedSizeStr = in.nextLine();
                                int receivedSize =
                                        Integer.parseInt(receivedSizeStr);
                                for (int i = 0; i < receivedSize; i++) {
                                    String receivedText = in.nextLine();
                                    uploadedQPw.println(receivedText);
                                }
                                uploadedQPw.close();

                                String fileName2 = in.nextLine();
                                System.out.println(
                                        "recevied filename " + fileName2);
                                File answerFile = new File(fileName2);
                                FileOutputStream uploadedAFos =
                                        new FileOutputStream(answerFile, true);
                                PrintWriter uploadedAPw =
                                        new PrintWriter(uploadedAFos);
                                String receivedSizeStr2 = in.nextLine();
                                int receivedSize2 =
                                        Integer.parseInt(receivedSizeStr2);
                                for (int i = 0; i < receivedSize2; i++) {
                                    String receivedText2 = in.nextLine();
                                    uploadedAPw.println(receivedText2);
                                }
                                uploadedAPw.close();

                                String name =
                                        fileName2.substring(
                                                0, fileName2.indexOf("answers.txt"))
                                                + "random.txt";
                                String randomFilename = name;
                                File randomFile = new File(randomFilename);
                                randomFile.createNewFile();
                                FileOutputStream randomFos =
                                        new FileOutputStream(randomFile, true);
                                PrintWriter randomPw =
                                        new PrintWriter(randomFos);

                                String ran1 = in.nextLine();
                                String ran2 = in.nextLine();
                                randomPw.println(ran1);
                                randomPw.println(ran2);
                                randomPw.flush();
                                randomPw.close();
                            }
                        }
                        // Allows teachers to view and grade quiz submissions
                        // this is only for teachers

                        // answers
                        if (checker2.equals("VIEW_QUIZ")) {
                            PrintWriter pwViewQuiz =
                                    new PrintWriter(socket.getOutputStream());
                            int lsaSize = lsaList.size();

                            pwViewQuiz.println(lsaSize);
                            pwViewQuiz.flush();
                            for (int i = 0; i < lsaSize; i++) {
                                lsaLine = lsaList.get(i);
                                pwViewQuiz.println(lsaLine);
                            }
                            pwViewQuiz.flush();
                            String chosenSub = in.nextLine();

                            File chosen =
                                    new File(chosenSub + " responses.txt");

                            FileReader chosenReader = new FileReader(chosen);
                            BufferedReader chosenBfr =
                                    new BufferedReader(chosenReader);

                            String time = chosenBfr.readLine();
                            // System.out.println("Time " + time);
                            ArrayList<String> chosenQuestions =
                                    new ArrayList<>();
                            ArrayList<String> chosenAnswers = new ArrayList<>();
                            ArrayList<String> chosenCurrQ = new ArrayList<>();
                            ArrayList<String> chosenGrades = new ArrayList<>();
                            String chosenLine;
                            while (
                                    (chosenLine = chosenBfr.readLine()) != null) {
                                chosenQuestions.add(chosenLine);
                                // chosenLine = chosenBfr.readLine();
                                // chosenCurrQ.add(chosenLine);
                                chosenLine = chosenBfr.readLine();
                                chosenAnswers.add(chosenLine);
                                chosenLine = chosenBfr.readLine();
                                chosenGrades.add(chosenLine);
                            }
                            chosenBfr.close();

                            pwViewQuiz.println(chosenAnswers.size());
                            String thing;
                            for (int i = 0; i < chosenQuestions.size(); i++) {
                                thing = chosenQuestions.get(i);
                                pwViewQuiz.println(thing);
                            }
                            pwViewQuiz.flush();
                            for (int i = 0; i < chosenAnswers.size(); i++) {
                                thing = chosenAnswers.get(i);
                                pwViewQuiz.println(thing);
                            }
                            pwViewQuiz.flush();
                            for (int i = 0; i < chosenGrades.size(); i++) {
                                thing = chosenGrades.get(i);
                                pwViewQuiz.println(thing);
                            }
                            pwViewQuiz.flush();

                            FileOutputStream chosenFos =
                                    new FileOutputStream(chosen, false);
                            PrintWriter chosenPw = new PrintWriter(chosenFos);

                            for (int i = 0; i < chosenGrades.size(); i++) {
                                String newGrades = in.nextLine();
                                chosenGrades.set(i, newGrades);
                            }

                            chosenPw.println(time);
                            for (int i = 0; i < chosenGrades.size(); i++) {
                                chosenPw.println(chosenQuestions.get(i));
                                chosenPw.println(chosenAnswers.get(i));
                                chosenPw.println(chosenGrades.get(i));
                            }
                            chosenPw.flush();

                            chosenPw.close();
                        }

                        if (checker2.equals("ViewGradedQuiz")) {
                            PrintWriter pwStuQuiz =
                                    new PrintWriter(socket.getOutputStream());
                            ArrayList<String> studentsQuizzes =
                                    new ArrayList<>();
                            for (int i = 0; i < lsaList.size(); i++) {
                                if (lsaList.get(i).contains(loginUser)) {
                                    studentsQuizzes.add(lsaList.get(i));
                                }
                            }
                            int stuQuizzesSize = studentsQuizzes.size();
                            pwStuQuiz.println(stuQuizzesSize);
                            pwStuQuiz.flush();

                            for (int i = 0; i < stuQuizzesSize; i++) {
                                String stuLine = studentsQuizzes.get(i);
                                pwStuQuiz.println(stuLine);
                            }
                            pwStuQuiz.flush();

                            String stuQuizName = in.nextLine();
                            stuQuizName += " responses.txt";

                            File stuQuiz = new File(stuQuizName);
                            FileReader stuQuizReader = new FileReader(stuQuiz);
                            BufferedReader stuQuizBfr =
                                    new BufferedReader(stuQuizReader);

                            String time = stuQuizBfr.readLine();
                            // System.out.println("Time " + time);
                            ArrayList<String> stuQuizQuestions =
                                    new ArrayList<>();
                            ArrayList<String> stuQuizAnswers =
                                    new ArrayList<>();
                            ArrayList<String> stuQuizGrades = new ArrayList<>();
                            String stuQuizLine;
                            while (
                                    (stuQuizLine = stuQuizBfr.readLine()) != null) {
                                stuQuizQuestions.add(stuQuizLine);
                                stuQuizLine = stuQuizBfr.readLine();
                                stuQuizAnswers.add(stuQuizLine);
                                stuQuizLine = stuQuizBfr.readLine();
                                stuQuizGrades.add(stuQuizLine);
                            }
                            stuQuizBfr.close();

                            pwStuQuiz.println(stuQuizAnswers.size());
                            String thing;
                            for (int i = 0; i < stuQuizQuestions.size(); i++) {
                                thing = stuQuizQuestions.get(i);
                                pwStuQuiz.println(thing);
                            }
                            pwStuQuiz.flush();
                            for (int i = 0; i < stuQuizAnswers.size(); i++) {
                                thing = stuQuizAnswers.get(i);
                                pwStuQuiz.println(thing);
                            }
                            pwStuQuiz.flush();
                            for (int i = 0; i < stuQuizGrades.size(); i++) {
                                thing = stuQuizGrades.get(i);
                                pwStuQuiz.println(thing);
                            }
                            pwStuQuiz.flush();
                        }

                    } while (true);
                }
            }
            pw.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(String message) throws IOException {
        PrintWriter sendPw = new PrintWriter(socket.getOutputStream());
        sendPw.write(message);
        sendPw.println();
        sendPw.flush();
    }

    public void addCourse(String courseName) {
        PrintWriter coursePw = new PrintWriter(courseFos);
        System.out.println("PRINTING: " + courseName);
        coursePw.write(courseName);
        coursePw.println();
        coursePw.flush();
    }
}
