package cityrescue.model;

import cityrescue.enums.*;

public abstract class Unit {

    protected int unitId;
    protected UnitType type;
    protected int x;
    protected int y;
    protected int homeStationId;
    protected UnitStatus status;
    protected Station station;

    protected Incident assignedIncident;
    protected int workTicksRemaining;

    public Unit(int unitId, UnitType type, int x, int y, int homeStationId) {
        this.unitId = unitId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.homeStationId = homeStationId;
        this.status = UnitStatus.IDLE;
        this.station = null;
    }

    public int getUnitId() { return unitId; }
    public UnitType getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHomeStationId() { return homeStationId; }
    public UnitStatus getStatus() { return status; }
    
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Incident getAssignedIncident() {
        return assignedIncident;
    }

    public void assignIncident(Incident incident) {
        this.assignedIncident = incident;
    }

    public void clearIncident() {
        this.assignedIncident = null;
    }

    public int getWorkTicksRemaining() {
        return workTicksRemaining;
    }

    public void setWorkTicksRemaining(int ticks) {
        this.workTicksRemaining = ticks;
    }

    public abstract boolean canHandle(IncidentType type);

    public abstract int getTicksToResolve(int severity);
}
