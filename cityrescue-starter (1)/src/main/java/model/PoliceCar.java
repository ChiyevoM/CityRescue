package cityrescue.model;

import cityrescue.enums.*;

public class PoliceCar extends Unit {

    public PoliceCar(int unitId, int x, int y, int homeStationId) {
        super(unitId, UnitType.POLICE_CAR, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.CRIME;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 3;
    }
}
