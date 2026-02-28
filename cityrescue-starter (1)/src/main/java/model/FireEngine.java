package cityrescue.model;

import enums.*;

public class FireEngine extends Unit {

    public FireEngine(int unitId, int x, int y, int homeStationId) {
        super(unitId, UnitType.FIRE_ENGINE, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.FIRE;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 4;
    }
}
