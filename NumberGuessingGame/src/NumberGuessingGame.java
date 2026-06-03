import java.util.Random;   // Import Random class for generating secret number
import java.util.Scanner;  // Import Scanner class for reading user input

/**
 * =====================================================================
 *  Number Guessing Game
 *  A console-based game where the player guesses a randomly generated
 *  number within a limited number of attempts.
 *
 *  Author  : Senior Java Developer
 *  Version : 1.0
 * =====================================================================
 */
public class NumberGuessingGame {

    // ── Constants ─────────────────────────────────────────────────────
    private static final int MAX_ATTEMPTS = 5;   // Maximum guesses per round
    private static final int HINT_RANGE   = 10;  // ±10 triggers "very close" hint

    // ── Shared utilities (created once, reused across methods) ────────
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random  random  = new Random();

    // ═════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═════════════════════════════════════════════════════════════════

    /**
     * main() – program entry point.
     * Controls the play-again loop so the whole game restarts cleanly
     * whenever the player chooses Y.
     */
    public static void main(String[] args) {

        displayWelcomeBanner();   // Show the welcome screen once

        boolean keepPlaying = true;

        while (keepPlaying) {
            playGame();                         // Run one full round
            keepPlaying = askPlayAgain();       // Ask whether to continue
        }

        // Farewell message shown when the player exits
        System.out.println("\n  Thanks for playing! Goodbye! 👋\n");
        scanner.close();   // Close the Scanner to release system resources
    }

    // ═════════════════════════════════════════════════════════════════
    //  1. WELCOME BANNER
    // ═════════════════════════════════════════════════════════════════

    /**
     * displayWelcomeBanner() – prints a decorative welcome screen.
     * Called once at program start.
     */
    private static void displayWelcomeBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       NUMBER  GUESSING  GAME  🎯         ║");
        System.out.println("║  Guess the secret number & earn points!  ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════
    //  2. DIFFICULTY MENU
    // ═════════════════════════════════════════════════════════════════

    /**
     * displayMenu() – shows the three difficulty options and returns
     * the player's validated choice (1, 2, or 3).
     *
     * Uses a do-while loop so the prompt is shown at least once,
     * and repeats until a valid option is entered.
     *
     * @return int – 1 (Easy), 2 (Medium), or 3 (Hard)
     */
    private static int displayMenu() {
        int choice = 0;   // Will hold the validated menu selection

        do {
            System.out.println("┌─────────────────────────────┐");
            System.out.println("│   SELECT DIFFICULTY LEVEL   │");
            System.out.println("├─────────────────────────────┤");
            System.out.println("│  1.  Easy    (1 – 50)       │");
            System.out.println("│  2.  Medium  (1 – 100)      │");
            System.out.println("│  3.  Hard    (1 – 500)      │");
            System.out.println("└─────────────────────────────┘");
            System.out.print("  Enter your choice (1/2/3): ");

            // readInt() handles non-numeric input gracefully
            choice = readInt();

            // Validate: only 1, 2, or 3 are acceptable
            if (choice < 1 || choice > 3) {
                System.out.println("  ⚠  Invalid choice. Please enter 1, 2, or 3.\n");
            }

        } while (choice < 1 || choice > 3);  // Keep looping until valid

        return choice;
    }

    // ═════════════════════════════════════════════════════════════════
    //  3. DIFFICULTY RANGE RESOLVER
    // ═════════════════════════════════════════════════════════════════

    /**
     * getDifficultyRange() – converts a menu choice into the upper
     * bound of the guessing range.
     *
     * Uses a switch statement (as required) for clean mapping.
     *
     * @param choice – the difficulty level selected by the player
     * @return int   – the upper bound (50, 100, or 500)
     */
    private static int getDifficultyRange(int choice) {
        int upperBound;   // The maximum value the secret number can be

        switch (choice) {
            case 1:
                upperBound = 50;    // Easy  – smaller range, easier to guess
                break;
            case 2:
                upperBound = 100;   // Medium – standard range
                break;
            case 3:
                upperBound = 500;   // Hard  – large range, challenging
                break;
            default:
                upperBound = 100;   // Fallback (should never be reached)
        }

        return upperBound;
    }

    // ═════════════════════════════════════════════════════════════════
    //  4. CORE GAME LOOP
    // ═════════════════════════════════════════════════════════════════

    /**
     * playGame() – orchestrates one complete round of the game:
     *   a) Show difficulty menu and resolve the number range.
     *   b) Generate a random secret number within that range.
     *   c) Loop up to MAX_ATTEMPTS times, reading and evaluating guesses.
     *   d) Display the final result when the round ends.
     */
    private static void playGame() {

        // ── Step A: Difficulty selection ──────────────────────────────
        int difficultyChoice = displayMenu();             // Get validated choice
        int upperBound       = getDifficultyRange(difficultyChoice); // Map to range

        // ── Step B: Generate secret number ───────────────────────────
        // random.nextInt(n) returns 0 … n-1, so add 1 to get 1 … upperBound
        int secretNumber = random.nextInt(upperBound) + 1;

        // ── Confirm difficulty to the player ─────────────────────────
        System.out.println();
        System.out.println("  ✅ Difficulty selected! Guess a number between 1 and " + upperBound);
        System.out.println("  You have " + MAX_ATTEMPTS + " attempts. Good luck! 🍀");
        System.out.println();

        // ── Step C: Guessing loop ─────────────────────────────────────
        int  attemptsUsed = 0;     // Counter – incremented after each guess
        boolean playerWon = false; // Flag – set true when guess is correct

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            // Show remaining attempts so the player can plan strategy
            System.out.println("  ─── Attempt " + attempt + " of " + MAX_ATTEMPTS + " ───");
            System.out.print("  Enter your guess: ");

            int guess = readInt();   // Read and parse the player's guess

            // Input validation: reject non-positive numbers
            if (guess <= 0) {
                System.out.println("  ⚠  Please enter a positive number.\n");
                attempt--;           // Don't count this as a real attempt
                continue;            // Restart the loop iteration
            }

            attemptsUsed++;   // Count this as a valid attempt

            // ── Evaluate the guess ─────────────────────────────────
            if (guess == secretNumber) {
                // ✔ Correct!
                System.out.println("  🎉 Correct! Amazing guess!\n");
                playerWon = true;
                break;   // Exit the for-loop immediately – round is over

            } else if (guess > secretNumber) {
                System.out.println("  📉 Too High!");
            } else {
                System.out.println("  📈 Too Low!");
            }

            // ── Hint system: is the guess within ±HINT_RANGE? ─────
            // Math.abs() gives the absolute (unsigned) difference
            if (Math.abs(guess - secretNumber) <= HINT_RANGE) {
                System.out.println("  🔥 You are very close!");
            }

            System.out.println();   // Blank line for readability
        }

        // ── Step D: Show result ───────────────────────────────────────
        int finalScore = calculateScore(playerWon, attemptsUsed);
        showResult(playerWon, attemptsUsed, finalScore, secretNumber);
    }

    // ═════════════════════════════════════════════════════════════════
    //  5. SCORE CALCULATOR
    // ═════════════════════════════════════════════════════════════════

    /**
     * calculateScore() – computes the player's score based on how
     * many attempts were used and whether they won.
     *
     * Scoring table:
     *   1 attempt  → 100 pts
     *   2 attempts →  80 pts
     *   3 attempts →  60 pts
     *   4 attempts →  40 pts
     *   5 attempts →  20 pts
     *   Failed     →   0 pts
     *
     * @param playerWon    – true if the secret number was guessed
     * @param attemptsUsed – how many valid guesses were made
     * @return int         – the calculated score
     */
    private static int calculateScore(boolean playerWon, int attemptsUsed) {

        // No points for failing
        if (!playerWon) {
            return 0;
        }

        // Points decrease linearly with each extra attempt
        // Formula: 100 – (attempts - 1) * 20
        // E.g., attempt 1 → 100 - 0 = 100, attempt 3 → 100 - 40 = 60
        return 100 - (attemptsUsed - 1) * 20;
    }

    // ═════════════════════════════════════════════════════════════════
    //  6. RESULT DISPLAY
    // ═════════════════════════════════════════════════════════════════

    /**
     * showResult() – displays a summary box at the end of each round:
     *   • WIN  – congratulations with score breakdown
     *   • LOSE – game-over message revealing the secret number
     *
     * @param playerWon    – outcome flag
     * @param attemptsUsed – attempts consumed this round
     * @param score        – points earned
     * @param secretNumber – the number that was being guessed
     */
    private static void showResult(boolean playerWon, int attemptsUsed,
                                   int score, int secretNumber) {

        System.out.println("╔══════════════════════════════════════════╗");

        if (playerWon) {
            // ── Win path ───────────────────────────────────────────
            System.out.println("║           🏆  YOU  WIN!  🏆              ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.printf( "║  Secret Number  : %-23d║%n", secretNumber);
            System.out.printf( "║  Attempts Used  : %-23d║%n", attemptsUsed);
            System.out.printf( "║  Your Score     : %-23d║%n", score);
        } else {
            // ── Lose path ──────────────────────────────────────────
            System.out.println("║         💀  GAME  OVER  💀               ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.printf( "║  Secret Number  : %-23d║%n", secretNumber);
            System.out.printf( "║  Attempts Used  : %-23d║%n", attemptsUsed);
            System.out.printf( "║  Your Score     : %-23d║%n", score);
            System.out.println("║  Better luck next time!                  ║");
        }

        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════
    //  7. PLAY-AGAIN PROMPT
    // ═════════════════════════════════════════════════════════════════

    /**
     * askPlayAgain() – asks whether the player wants another round.
     * Accepts Y/y (yes) or N/n (no); any other input is rejected
     * and the question is repeated.
     *
     * @return boolean – true if the player wants to play again
     */
    private static boolean askPlayAgain() {
        System.out.print("  Do you want to play again? (Y/N): ");

        while (true) {   // Loop until a valid Y or N is received
            String input = scanner.nextLine().trim();   // Read & strip whitespace

            if (input.equalsIgnoreCase("Y")) {
                System.out.println();
                return true;    // Signal to restart the game loop
            } else if (input.equalsIgnoreCase("N")) {
                return false;   // Signal to exit the game loop
            } else {
                // Invalid input – prompt again without crashing
                System.out.print("  ⚠  Please enter Y or N: ");
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════
    //  8. SAFE INTEGER READER  (input validation helper)
    // ═════════════════════════════════════════════════════════════════

    /**
     * readInt() – safely reads one integer from the console.
     *
     * If the user types something that is NOT a valid integer
     * (e.g., "abc", "12.5", or just hits Enter), it:
     *   1. Catches the NumberFormatException.
     *   2. Prints a friendly error message.
     *   3. Returns Integer.MIN_VALUE as a sentinel so the caller
     *      can detect and handle invalid input.
     *
     * @return int – the parsed integer, or Integer.MIN_VALUE on error
     */
    private static int readInt() {
        try {
            String line = scanner.nextLine().trim();   // Read the whole line
            return Integer.parseInt(line);             // Attempt to parse as int
        } catch (NumberFormatException e) {
            // Non-numeric input was entered
            System.out.println("  ⚠  Invalid input. Please enter a whole number.");
            return Integer.MIN_VALUE;   // Sentinel value indicating bad input
        }
    }

}   // end class NumberGuessingGame