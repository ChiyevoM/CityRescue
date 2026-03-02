package cityrescue.model;

import cityrescue.enums.*;

public class Ambulance extends Unit {

    public Ambulance(int unitId, int x, int y, int homeStationId) {
        super(unitId, UnitType.AMBULANCE, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.MEDICAL;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 2;
    }
}
