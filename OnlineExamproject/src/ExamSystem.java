import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ExamSystem.java
 * ─────────────────────────────────────────────────────────────
 * Full exam flow:
 *   1. Show exam instructions
 *   2. Countdown timer runs on a background thread
 *   3. Player answers MCQs one by one
 *   4. Auto-submit when time runs out OR player answers all
 *   5. Show final score and result
 * ─────────────────────────────────────────────────────────────
 */
public class ExamSystem {

    // ── Exam configuration ────────────────────────────────────
    private static final int EXAM_DURATION_SECONDS = 120;  // 2 minutes
    private static final int TOTAL_MARKS_PER_Q     = 10;

    private User    currentUser;
    private Scanner scanner;

    // Shared flag: becomes true when time is up
    private AtomicBoolean timeUp = new AtomicBoolean(false);

    // Stores the player's chosen answer index for each question (-1 = skipped)
    private int[] playerAnswers;

    // ── Question bank (10 Java MCQs) ─────────────────────────
    private Question[] questions = {

        new Question(
            "Which keyword is used to create an object in Java?",
            new String[]{"create", "new", "object", "make"},
            1
        ),
        new Question(
            "What is the default value of an int variable in Java?",
            new String[]{"null", "1", "0", "-1"},
            2
        ),
        new Question(
            "Which class is used to read user input in Java?",
            new String[]{"System", "Scanner", "Reader", "Input"},
            1
        ),
        new Question(
            "What does OOP stand for?",
            new String[]{"Object Oriented Programming",
                         "Open Oriented Processing",
                         "Optional Object Program",
                         "Output Oriented Protocol"},
            0
        ),
        new Question(
            "Which loop always executes its body at least once?",
            new String[]{"for", "while", "do-while", "foreach"},
            2
        ),
        new Question(
            "What is the correct way to declare a String in Java?",
            new String[]{"string name;", "String name;", "STRING name;", "str name;"},
            1
        ),
        new Question(
            "Which method is the entry point of a Java program?",
            new String[]{"start()", "run()", "main()", "begin()"},
            2
        ),
        new Question(
            "What does JVM stand for?",
            new String[]{"Java Virtual Machine",
                         "Java Variable Method",
                         "Java Verified Module",
                         "Just Virtual Memory"},
            0
        ),
        new Question(
            "Which symbol is used for single-line comments in Java?",
            new String[]{"#", "//", "/* */", "--"},
            1
        ),
        new Question(
            "What is the size of an int data type in Java?",
            new String[]{"8 bits", "16 bits", "32 bits", "64 bits"},
            2
        )
    };

    // ── Constructor ───────────────────────────────────────────
    public ExamSystem(User user, Scanner scanner) {
        this.currentUser   = user;
        this.scanner       = scanner;
        this.playerAnswers = new int[questions.length];
        // Default all answers to -1 (unattempted)
        for (int i = 0; i < playerAnswers.length; i++) playerAnswers[i] = -1;
    }

    // ── Entry point ───────────────────────────────────────────
    public void start() {
        showInstructions();

        System.out.print("  Press ENTER to begin the exam...");
        scanner.nextLine();

        // Start the background countdown timer
        startTimer();

        // Run through all questions
        conductExam();

        // Show result (timer thread stops after exam ends)
        showResult();
    }

    // ── Instructions screen ───────────────────────────────────
    private void showInstructions() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              EXAM INSTRUCTIONS               ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf( "║  Total Questions : %-25d║%n", questions.length);
        System.out.printf( "║  Marks per Q     : %-25d║%n", TOTAL_MARKS_PER_Q);
        System.out.printf( "║  Total Marks     : %-25d║%n", questions.length * TOTAL_MARKS_PER_Q);
        System.out.printf( "║  Time Limit      : %-25s║%n", EXAM_DURATION_SECONDS + " seconds");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  • Enter 1, 2, 3, or 4 for each answer      ║");
        System.out.println("║  • Exam auto-submits when time runs out      ║");
        System.out.println("║  • No negative marking                       ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ── Background timer thread ───────────────────────────────
    private void startTimer() {
        Thread timerThread = new Thread(() -> {
            for (int remaining = EXAM_DURATION_SECONDS; remaining >= 0; remaining--) {

                if (timeUp.get()) return;   // Exam ended early — stop timer

                // Show warning at certain thresholds
                if (remaining == 60)
                    System.out.println("\n  ⏰  WARNING: 60 seconds remaining!\n");
                else if (remaining == 30)
                    System.out.println("\n  ⏰  WARNING: 30 seconds remaining!\n");
                else if (remaining == 10)
                    System.out.println("\n  ⏰  WARNING: 10 seconds remaining!\n");

                if (remaining == 0) {
                    // Time is up — trigger auto-submit
                    timeUp.set(true);
                    System.out.println("\n\n  ⏱️  TIME UP! Exam auto-submitted.\n");
                    return;
                }

                try {
                    Thread.sleep(1000);   // Wait 1 second
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        timerThread.setDaemon(true);   // Daemon: dies when main thread ends
        timerThread.start();
    }

    // ── Main exam loop ────────────────────────────────────────
    private void conductExam() {
        for (int i = 0; i < questions.length; i++) {

            // Check if time ran out before this question
            if (timeUp.get()) {
                System.out.println("  Remaining questions auto-submitted as unanswered.");
                break;
            }

            displayQuestion(i);

            // Read answer with timeout awareness
            String input = readAnswer();

            if (timeUp.get()) {
                System.out.println("  Time ran out while answering. Moving to results.");
                break;
            }

            // Parse the answer
            int answerIndex = parseAnswer(input);
            if (answerIndex == -1) {
                System.out.println("  ⚠  Invalid input. Question skipped.\n");
            } else {
                playerAnswers[i] = answerIndex;
                System.out.println("  ✅ Answer recorded.\n");
            }
        }

        // Signal timer thread that exam is done
        timeUp.set(true);
    }

    // ── Display one question ──────────────────────────────────
    private void displayQuestion(int index) {
        Question q = questions[index];
        System.out.println("──────────────────────────────────────────────");
        System.out.printf( "  Q%d of %d:  %s%n", index + 1, questions.length, q.getQuestionText());
        System.out.println();

        String[] options = q.getOptions();
        for (int j = 0; j < options.length; j++) {
            System.out.printf("    %d. %s%n", j + 1, options[j]);
        }
        System.out.println();
        System.out.print("  Your answer (1-4): ");
    }

    // ── Read a line safely ────────────────────────────────────
    private String readAnswer() {
        try {
            return scanner.nextLine().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ── Convert "1"–"4" to 0-based index ─────────────────────
    private int parseAnswer(String input) {
        try {
            int val = Integer.parseInt(input);
            if (val >= 1 && val <= 4) return val - 1;   // Convert to 0-based
        } catch (NumberFormatException ignored) {}
        return -1;   // Invalid
    }

    // ── Calculate and display final result ───────────────────
    private void showResult() {
        int correct   = 0;
        int attempted = 0;

        for (int i = 0; i < questions.length; i++) {
            if (playerAnswers[i] != -1) {
                attempted++;
                if (questions[i].isCorrect(playerAnswers[i])) {
                    correct++;
                }
            }
        }

        int score      = correct * TOTAL_MARKS_PER_Q;
        int totalMarks = questions.length * TOTAL_MARKS_PER_Q;
        int percentage = (score * 100) / totalMarks;
        String grade   = calculateGrade(percentage);
        String status  = percentage >= 40 ? "✅ PASS" : "❌ FAIL";

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║               EXAM  RESULT                   ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf( "║  Student        : %-26s║%n", currentUser.getFullName());
        System.out.printf( "║  Questions      : %-26d║%n", questions.length);
        System.out.printf( "║  Attempted      : %-26d║%n", attempted);
        System.out.printf( "║  Correct        : %-26d║%n", correct);
        System.out.printf( "║  Score          : %-23s║%n", score + " / " + totalMarks);
        System.out.printf( "║  Percentage     : %-25s║%n", percentage + "%");
        System.out.printf( "║  Grade          : %-26s║%n", grade);
        System.out.printf( "║  Result         : %-26s║%n", status);
        System.out.println("╚══════════════════════════════════════════════╝");

        // Detailed review
        System.out.println("\n  ── Answer Review ───────────────────────────");
        for (int i = 0; i < questions.length; i++) {
            String correctAns = questions[i].getOptions()[questions[i].getCorrectOptionIndex()];
            boolean isRight   = playerAnswers[i] != -1 && questions[i].isCorrect(playerAnswers[i]);
            String  mark      = isRight ? "✅" : "❌";
            System.out.printf("  Q%d: %s  Correct: %s%n", i + 1, mark, correctAns);
        }
        System.out.println();

        pause(1500);
    }

    // ── Grade based on percentage ─────────────────────────────
    private String calculateGrade(int percentage) {
        if (percentage >= 90) return "A+ (Outstanding)";
        if (percentage >= 80) return "A  (Excellent)";
        if (percentage >= 70) return "B  (Good)";
        if (percentage >= 60) return "C  (Average)";
        if (percentage >= 40) return "D  (Pass)";
        return                       "F  (Fail)";
    }

    private void pause(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
