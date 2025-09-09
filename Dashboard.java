import java.util.Scanner;

public class Dashboard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Dashboard =====");
        System.out.println("1. Add Your Skills");
        System.out.println("2. See Matching Opportunities");
        System.out.println("3. Exit");

        while (true) {
            System.out.print("\nEnter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your skills (comma-separated): ");
                    String skills = scanner.nextLine();
                    System.out.println("Your skills have been added: " + skills);
                    break;

                case 2:
                    System.out.println("\nMatching Opportunities:");
                    System.out.println("1. Software Developer Intern");
                    System.out.println("2. Web Application Tester");
                    System.out.println("3. Mobile App Developer Part-Time");

                    System.out.print("\nSelect an opportunity (1-3): ");
                    int opportunity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    switch (opportunity) {
                        case 1:
                            System.out.println("You selected: Software Developer Intern");
                            break;
                        case 2:
                            System.out.println("You selected: Web Application Tester");
                            break;
                        case 3:
                            System.out.println("You selected: Mobile App Developer Part-Time");
                            break;
                        default:
                            System.out.println("Invalid selection");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Dashboard. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}