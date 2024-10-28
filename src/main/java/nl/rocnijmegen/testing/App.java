package nl.rocnijmegen.testing;

import java.util.Scanner;

public class App {

    // Method to check if the postcode is in the restricted aardbevingsgebied
    public boolean controleerPostcode(String postcode) {
        return !(postcode.equals("9679") || postcode.equals("9681") || postcode.equals("9682"));
    }

    // Method to calculate the maximum loan amount based on income
    public double berekenMaximaalLeenbedrag(double inkomen, double partnerInkomen, boolean heeftStudieschuld) {
        double totaalInkomen = inkomen + partnerInkomen;
        double maximaalLeenbedrag = totaalInkomen * 12 * 5;

        // Apply 25% reduction if the user has a student loan
        if (heeftStudieschuld) {
            maximaalLeenbedrag *= 0.75;
        }

        return maximaalLeenbedrag;
    }

    // Method to calculate the monthly mortgage payments
    public double berekenMaandlasten(double leenbedrag, double rentePercentage, int looptijdInJaren) {
        double maandRente = rentePercentage / 100 / 12;
        int aantalBetalingen = looptijdInJaren * 12;

        // Mortgage formula
        return leenbedrag * (maandRente * Math.pow(1 + maandRente, aantalBetalingen)) / (Math.pow(1 + maandRente, aantalBetalingen) - 1);
    }

    // Method to determine interest based on fixed-interest period (years)
    public double bepaalRente(int jaren) {
        switch (jaren) {
            case 1:
                return 2.0;
            case 5:
                return 3.0;
            case 10:
                return 3.5;
            case 20:
                return 4.5;
            case 30:
                return 5.0;
            default:
                return -1;  // Invalid period
        }
    }

    // CLI entry point for user input
    public static void main(String[] args) {
        App app = new App();
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.print("Wat is uw maandinkomen? ");
        double inkomen = scanner.nextDouble();

        System.out.print("Heeft u een partner? (ja/nee): ");
        boolean heeftPartner = scanner.next().equalsIgnoreCase("ja");
        double partnerInkomen = 0;
        if (heeftPartner) {
            System.out.print("Wat is het maandinkomen van uw partner? ");
            partnerInkomen = scanner.nextDouble();
        }

        System.out.print("Heeft u een studieschuld? (ja/nee): ");
        boolean heeftStudieschuld = scanner.next().equalsIgnoreCase("ja");

        System.out.print("Kies een rentevaste periode: 1, 5, 10, 20 of 30 jaar: ");
        int jaren = scanner.nextInt();
        double rentePercentage = app.bepaalRente(jaren);

        if (rentePercentage == -1) {
            System.out.println("Ongeldige rentevaste periode.");
            System.exit(0);
        }

        double maximaalLeenbedrag = app.berekenMaximaalLeenbedrag(inkomen, partnerInkomen, heeftStudieschuld);
        double maandlasten = app.berekenMaandlasten(maximaalLeenbedrag, rentePercentage, jaren);

        System.out.println("Op basis van uw inkomen kunt u maximaal lenen: " + maximaalLeenbedrag);
        System.out.println("Maandelijkse hypotheeklasten: " + maandlasten);

        scanner.close();
    }
}
