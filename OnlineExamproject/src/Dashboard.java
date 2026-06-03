import java.util.Map;
import java.util.Scanner;

/**
 * Dashboard.java
 * ─────────────────────────────────────────────────────────────
 * The main menu shown after a successful login.
 * Offers:
 *   1. Update Profile & Password
 *   2. Start Exam
 *   3. Logout  (closes session)
 * ─────────────────────────────────────────────────────────────
 */
public class Dashboard {

    private User                  currentUser;   // Logged-in user
    private Map<String, User>     userDatabase;  // Shared user store
    private Scanner               scanner;

    public Dashboard(User user, Map<String, User> db, Scanner scanner) {
        this.currentUser  = user;
        this.userDatabase = db;
        this.scanner      = scanner;
    }

    // ── Show menu and handle choices ─────────────────────────
    public void show() {
        boolean sessionActive = true;

        while (sessionActive) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Go to profile update screen
                    new ProfileUpdate(currentUser, scanner).show();
                    break;

                case "2":
                    // Go to exam screen
                    new ExamSystem(currentUser, scanner).start();
                    break;

                case "3":
                    // Logout — close session
                    sessionActive = false;
                    logout();
                    break;

                default:
                    System.out.println("  ⚠  Invalid choice. Please enter 1, 2, or 3.\n");
            }
        }
    }

    // ── Dashboard menu UI ─────────────────────────────────────
    private void printMenu() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.printf( "║  👤  Logged in as: %-25s║%n", currentUser.getFullName());
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  1.  Update Profile & Password               ║");
        System.out.println("║  2.  Start Exam                              ║");
        System.out.println("║  3.  Logout                                  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.print("  Enter choice: ");
    }

    // ── Logout message ────────────────────────────────────────
    private void logout() {
        System.out.println("\n  🔓 Session closed. You have been logged out.");
        System.out.println("  Thank you, " + currentUser.getFullName() + "! Goodbye! 👋\n");
    }
}