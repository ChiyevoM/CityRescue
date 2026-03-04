import cityrescue.*;
import cityrescue.enums.*;

public class ManualTest {

    public static void main(String[] args) throws Exception {

        CityRescue cr = new CityRescueImpl();
        cr.initialise(5, 5);

        int station = cr.addStation("A", 0, 0);
        int unit = cr.addUnit(station, UnitType.AMBULANCE);

        int incident = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 1);

        System.out.println("Before dispatch:");
        System.out.println(cr.viewUnit(unit));
        System.out.println(cr.viewIncident(incident));

        cr.dispatch();

        System.out.println("\nAfter dispatch:");
        System.out.println(cr.viewUnit(unit));
        System.out.println(cr.viewIncident(incident));

        cr.tick();
        System.out.println("\nAfter 1 tick:");
        System.out.println(cr.viewUnit(unit));

        cr.tick();
        cr.tick();

        System.out.println("\nAfter resolution:");
        System.out.println(cr.viewUnit(unit));
        System.out.println(cr.viewIncident(incident));

        // Edge case: invalid ID
        try {
            cr.viewUnit(999);
        } catch (Exception e) {
            System.out.println("\nInvalid ID test passed: " + e.getMessage());
        }
    }
}