package nl.rocnijmegen.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private App app;

    @BeforeEach
    public void setUp() {
        app = new App();
    }

    //(TC001)
    @Test
    void testControleerPostcode_ValidPostcode() {
        assertTrue(app.controleerPostcode("1234"));  // Postcode buiten aardbevingsgebied
    }

    @Test
    void testControleerPostcode_InvalidPostcode() {
        assertFalse(app.controleerPostcode("9679"));  // Postcode in aardbevingsgebied
    }

    //(TC002)
    @Test
    void testBerekenMaximaalLeenbedrag_ZonderPartnerZonderStudieschuld() {
        double inkomen = 3000.0;
        double partnerInkomen = 0.0;
        boolean heeftStudieschuld = false;

        double maximaalLeenbedrag = app.berekenMaximaalLeenbedrag(inkomen, partnerInkomen, heeftStudieschuld);
        assertEquals(180000.0, maximaalLeenbedrag, 0.01);  // 5x annual income
    }

    //(TC003)
    @Test
    void testBerekenMaximaalLeenbedrag_MetPartnerZonderStudieschuld() {
        double inkomen = 4000.0;
        double partnerInkomen = 2000.0;
        boolean heeftStudieschuld = false;

        double maximaalLeenbedrag = app.berekenMaximaalLeenbedrag(inkomen, partnerInkomen, heeftStudieschuld);
        assertEquals(360000.0, maximaalLeenbedrag, 0.01);
    }

    //(TC004)
    @Test
    void testBerekenMaximaalLeenbedrag_MetPartnerMetStudieschuld() {
        double inkomen = 4000.0;
        double partnerInkomen = 1500.0;
        boolean heeftStudieschuld = true;

        double maximaalLeenbedrag = app.berekenMaximaalLeenbedrag(inkomen, partnerInkomen, heeftStudieschuld);
        assertEquals(247500.0, maximaalLeenbedrag, 0.01);  // 25% reduction due to student debt
    }

    //(TC005)
    @Test
    void testBepaalRente_InvalidPeriod() {
        double rente = app.bepaalRente(15);  // Invalid period
        assertEquals(-1, rente);  // Expect -1 for invalid period
    }

    @Test
    void testBepaalRente_ValidPeriods() {
        assertEquals(2.0, app.bepaalRente(1), 0.01);
        assertEquals(3.0, app.bepaalRente(5), 0.01);
        assertEquals(3.5, app.bepaalRente(10), 0.01);
        assertEquals(4.5, app.bepaalRente(20), 0.01);
        assertEquals(5.0, app.bepaalRente(30), 0.01);
    }



}
