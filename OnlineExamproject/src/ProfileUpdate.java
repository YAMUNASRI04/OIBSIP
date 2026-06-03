import java.util.Scanner;

/**
 * ProfileUpdate.java
 * ─────────────────────────────────────────────────────────────
 * Allows the logged-in user to:
 *   • Update their full name
 *   • Update their email address
 *   • Change their password (with old-password verification)
 * ─────────────────────────────────────────────────────────────
 */
public class ProfileUpdate {

    private User    user;
    private Scanner scanner;

    public ProfileUpdate(User user, Scanner scanner) {
        this.user    = user;
        this.scanner = scanner;
    }

    // ── Show profile menu ─────────────────────────────────────
    public void show() {
        boolean inMenu = true;

        while (inMenu) {
            printCurrentProfile();
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": updateFullName();  break;
                case "2": updateEmail();     break;
                case "3": updatePassword();  break;
                case "4": inMenu = false;    break;   // Back to dashboard
                default:
                    System.out.println("  ⚠  Invalid choice.\n");
            }
        }
    }

    // ── Show current profile details ──────────────────────────
    private void printCurrentProfile() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│         CURRENT PROFILE             │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf( "│  Username : %-24s│%n", user.getUsername());
        System.out.printf( "│  Full Name: %-24s│%n", user.getFullName());
        System.out.printf( "│  Email    : %-24s│%n", user.getEmail());
        System.out.println("└─────────────────────────────────────┘");
    }

    private void printMenu() {
        System.out.println("  1. Update Full Name");
        System.out.println("  2. Update Email");
        System.out.println("  3. Change Password");
        System.out.println("  4. Back to Dashboard");
        System.out.print("  Enter choice: ");
    }

    // ── Update full name ──────────────────────────────────────
    private void updateFullName() {
        System.out.print("  Enter new full name: ");
        String name = scanner.nextLine().trim();

        // Basic validation: name must not be empty
        if (name.isEmpty()) {
            System.out.println("  ⚠  Name cannot be empty.\n");
            return;
        }
        user.setFullName(name);
        System.out.println("  ✅ Full name updated to: " + name + "\n");
    }

    // ── Update email ──────────────────────────────────────────
    private void updateEmail() {
        System.out.print("  Enter new email address: ");
        String email = scanner.nextLine().trim();

        // Simple email format check: must contain @ and .
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("  ⚠  Invalid email format. Must contain @ and .\n");
            return;
        }
        user.setEmail(email);
        System.out.println("  ✅ Email updated to: " + email + "\n");
    }

    // ── Change password ───────────────────────────────────────
    private void updatePassword() {
        System.out.print("  Enter CURRENT password: ");
        String oldPass = scanner.nextLine().trim();

        // Verify old password before allowing change
        if (!user.getPassword().equals(oldPass)) {
            System.out.println("  ❌ Incorrect current password. Password not changed.\n");
            return;
        }

        System.out.print("  Enter NEW password (min 6 characters): ");
        String newPass = scanner.nextLine().trim();

        // Validate new password length
        if (newPass.length() < 6) {
            System.out.println("  ⚠  Password must be at least 6 characters.\n");
            return;
        }

        System.out.print("  Confirm NEW password: ");
        String confirmPass = scanner.nextLine().trim();

        // Both entries must match
        if (!newPass.equals(confirmPass)) {
            System.out.println("  ❌ Passwords do not match. Password not changed.\n");
            return;
        }

        user.setPassword(newPass);
        System.out.println("  ✅ Password changed successfully!\n");
    }
}