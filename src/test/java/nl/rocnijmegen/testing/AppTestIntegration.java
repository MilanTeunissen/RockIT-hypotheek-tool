package nl.rocnijmegen.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class AppTestIntegration {

    private App app;


    @BeforeEach
    public void setUp() {
        app = new App();
    }

    @Test
    public void testLeenbedragBerekeningEndToEnd_MetPartnerZonderStudieschuld() {
        // Simuleer gebruikersinvoer
        String input = "4000\nja\n2000\nnee\n30\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "Op basis van uw inkomen kunt u maximaal lenen: 360000.0";
        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    void testBepaalRente_InvalidPeriod() {
        // Simuleer gebruikersinvoer
        String input = "5000\nnee\nnee\n30";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "Op basis van uw inkomen kunt u maximaal lenen: 300000.0";
        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    void testBerekenMaximaalLeenbedrag_MetPartnerMetStudieschuld() {
        // Simuleer gebruikersinvoer
        String input = "4000\nja\n1500\nja\n30";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "247500.0";
        assertTrue(actualOutput.contains(expectedOutput));
    }
}
