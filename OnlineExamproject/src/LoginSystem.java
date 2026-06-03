import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
/**
 * LoginSystem.java
 * ─────────────────────────────────────────────────────────────
 * Handles:
 *   • Displaying the login screen
 *   • Validating username + password
 *   • Handing off to the main menu on success
 * ─────────────────────────────────────────────────────────────
 */
public class LoginSystem {
 
    // In-memory "database" of users: username → User object
    // In a real app this would be a database; here we pre-load demo accounts.
    private Map<String, User> userDatabase = new HashMap<>();
 
    private Scanner scanner = new Scanner(System.in);
 
    // ── Constructor: seed demo users ─────────────────────────
    public LoginSystem() {
        userDatabase.put("yamuni",  new User("yamuni",  "pass123", "Yamuni",       "yamuni@email.com"));
        userDatabase.put("admin",   new User("admin",   "admin123","Administrator", "admin@email.com"));
        userDatabase.put("student", new User("student", "stu456",  "Student User",  "student@email.com"));
    }
 
    // ── Entry point called from Main ─────────────────────────
    public void start() {
        printBanner();
        loginLoop();
    }
 
    // ── Login loop: keeps asking until correct or user exits ─
    private void loginLoop() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;   // Lock out after 3 wrong tries
 
        while (attempts < MAX_ATTEMPTS) {
 
            System.out.println("┌─────────────────────────────────┐");
            System.out.println("│          USER  LOGIN            │");
            System.out.println("└─────────────────────────────────┘");
            System.out.print("  Enter Username : ");
            String username = scanner.nextLine().trim().toLowerCase();
 
            System.out.print("  Enter Password : ");
            String password = scanner.nextLine().trim();
 
            // Validate credentials against the map
            if (userDatabase.containsKey(username)) {
                User user = userDatabase.get(username);
                if (user.getPassword().equals(password)) {
                    // ✅ Login successful
                    System.out.println("\n  ✅ Login successful! Welcome, " + user.getFullName() + "!\n");
                    pause(800);
                    // Hand control to the main dashboard
                    new Dashboard(user, userDatabase, scanner).show();
                    return;   // After logout, end this method
                }
            }
 
            // ❌ Wrong credentials
            attempts++;
            int remaining = MAX_ATTEMPTS - attempts;
            System.out.println("\n  ❌ Invalid username or password.");
            if (remaining > 0) {
                System.out.println("  You have " + remaining + " attempt(s) remaining.\n");
            }
        }
 
        // Too many failed attempts
        System.out.println("\n  🔒 Account locked due to too many failed attempts.");
        System.out.println("  Please contact the administrator.\n");
        System.out.println("  Exiting system...");
    }
 
    // ── Welcome banner ───────────────────────────────────────
    private void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       ONLINE EXAMINATION SYSTEM  📋          ║");
        System.out.println("║        Oasis Infobyte — Java Task 4          ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  Demo Accounts:                              ║");
        System.out.println("║  Username: yamuni   Password: pass123        ║");
        System.out.println("║  Username: admin    Password: admin123       ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }
 
    // ── Utility: pause so the user can read messages ─────────
    private void pause(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}