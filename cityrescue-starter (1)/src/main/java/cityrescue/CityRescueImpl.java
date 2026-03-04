package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;
import cityrescue.model.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // Map
    private CityMap cityMap;
    
    // Storage limits
    private static final int MAX_STATIONS = 20;
    private static final int MAX_UNITS = 50;
    private static final int MAX_INCIDENTS = 200;
    
    // Arrays
    private Station[] stations;
    private Unit[] units;
    private Incident[] incidents;
    
    // Counters
    private int stationCount;
    private int unitCount;
    private int incidentCount;
    private int nextStationId = 1;
    private int nextUnitId = 1;
    
    // Tick counter
    private int tick;

    
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
    
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid grid size");
        }
    
        cityMap = new CityMap(width, height);
    
        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];
    
        stationCount = 0;
        unitCount = 0;
        incidentCount = 0;
        nextStationId = 1;
    
        tick = 0;

    }

    @Override
    public int[] getGridSize() {
        return new int[] { cityMap.getWidth(), cityMap.getHeight() };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {

        if (!cityMap.inBounds(x, y)) {
            throw new InvalidLocationException("Location out of bounds");
        }

        cityMap.addObstacle(x, y);
    }
    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {

        if (!cityMap.inBounds(x, y)) {
            throw new InvalidLocationException("Location out of bounds");
        }

        cityMap.removeObstacle(x, y);
    }

   @Override
public int addStation(String name, int x, int y)
        throws InvalidNameException, InvalidLocationException {

    if (name == null || name.trim().isEmpty()) {
        throw new InvalidNameException("Invalid name");
    }

    if (!cityMap.inBounds(x, y)) {
        throw new InvalidLocationException("Location out of bounds");
    }

    if (stationCount >= MAX_STATIONS) {
        throw new IllegalStateException("Maximum stations reached");
    }

    Station station = new Station(nextStationId, name, x, y, 5);
    stations[stationCount] = station;

    stationCount++;

    return nextStationId++;  // return current ID then increment
}

    @Override
    public void removeStation(int stationId)
            throws IDNotRecognisedException, IllegalStateException {
    
        // 1. Find station
        Station station = null;
        int index = -1;
    
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getStationId() == stationId) {
                station = stations[i];
                index = i;
                break;
            }
        }
    
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
    
        // 2. Check if station still has units
        if (station.getUnitCount() > 0) {
            throw new IllegalStateException("Cannot remove station with units");
        }
    
        // 3. Shift array left to remove gap
        for (int i = index; i < stationCount - 1; i++) {
            stations[i] = stations[i + 1];
        }
    
        stations[stationCount - 1] = null;
        stationCount--;
    }
    
    @Override
    public void setStationCapacity(int stationId, int maxUnits)
            throws IDNotRecognisedException, InvalidCapacityException {
    
        Station station = null;
    
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getStationId() == stationId) {
                station = stations[i];
                break;
            }
        }
    
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
    
        if (maxUnits < 0) {
            throw new InvalidCapacityException("Invalid capacity");
        }
    
        if (maxUnits < station.getUnitCount()) {
            throw new InvalidCapacityException("Capacity less than current units");
        }
    
        station.setCapacity(maxUnits);
    }

    @Override
    public int[] getStationIds() {
    
        int[] ids = new int[stationCount];
    
        for (int i = 0; i < stationCount; i++) {
            ids[i] = stations[i].getStationId();
        }
    
        return ids;
    }

    @Override
    public int addUnit(int stationId, UnitType type)
            throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {

        if (type == null) {
            throw new InvalidUnitException("Invalid unit type");
        }

        // Find station
        Station station = null;
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getStationId() == stationId) {
                station = stations[i];
                break;
            }
        }

        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (unitCount >= MAX_UNITS) {
            throw new IllegalStateException("Maximum units reached");
        }

        if (station.getUnitCount() >= station.getCapacity()) {
            throw new IllegalStateException("Station capacity reached");
        }

        Unit unit = null;

        
        if (type == UnitType.AMBULANCE) {
            unit = new Ambulance(nextUnitId,
                    station.getX(),
                    station.getY(),
                    station.getStationId());
        }
        else if (type == UnitType.FIRE_ENGINE) {
            unit = new FireEngine(nextUnitId,
                    station.getX(),
                    station.getY(),
                    station.getStationId());
        }
        else if (type == UnitType.POLICE_CAR) {
            unit = new PoliceCar(nextUnitId,
                    station.getX(),
                    station.getY(),
                    station.getStationId());
        }

        units[unitCount] = unit;
        station.addUnit(unit);

        unitCount++;

        
        return nextUnitId++;
    }
    @Override
    public void decommissionUnit(int unitId)
            throws IDNotRecognisedException, IllegalStateException {
    
        Unit unit = null;
        int index = -1;
    
        // 1. Find unit
        for (int i = 0; i < unitCount; i++) {
            if (units[i].getUnitId() == unitId) {
                unit = units[i];
                index = i;
                break;
            }
        }
    
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }
    
        // 2. Cannot remove if BUSY
        if (unit.getStatus() == UnitStatus.BUSY) {
            throw new IllegalStateException("Cannot decommission busy unit");
        }
    
        // 3. Remove from its station
        unit.getStation().removeUnit(unit);
    
        // 4. Shift units array left
        for (int i = index; i < unitCount - 1; i++) {
            units[i] = units[i + 1];
        }
    
        units[unitCount - 1] = null;
        unitCount--;
    }

    @Override
    public void transferUnit(int unitId, int newStationId)
            throws IDNotRecognisedException, IllegalStateException {
    
        Unit unit = null;
    
        // 1. Find unit
        for (int i = 0; i < unitCount; i++) {
            if (units[i].getUnitId() == unitId) {
                unit = units[i];
                break;
            }
        }
    
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }
    
        // 2. Cannot transfer if busy
        if (unit.getStatus() == UnitStatus.BUSY) {
            throw new IllegalStateException("Cannot transfer busy unit");
        }
    
        // 3. Find new station
        Station newStation = null;
    
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getId() == newStationId) {
                newStation = stations[i];
                break;
            }
        }
    
        if (newStation == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
    
        // 4. Check capacity
        if (newStation.getCurrentUnitCount() >= newStation.getMaxUnits()) {
            throw new IllegalStateException("Station capacity reached");
        }
    
        // 5. Remove from old station
        Station oldStation = unit.getStation();
        oldStation.removeUnit(unit);
    
        // 6. Add to new station
        newStation.addUnit(unit);
    
        // 7. Update unit's station reference
        unit.setStation(newStation);
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService)
            throws IDNotRecognisedException, IllegalStateException {
    
        Unit unit = null;
    
        // 1. Find the unit
        for (int i = 0; i < unitCount; i++) {
            if (units[i].getUnitId() == unitId) {
                unit = units[i];
                break;
            }
        }
    
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }
    
        // 2. Cannot take unit out of service if it's busy
        if (unit.getStatus() == UnitStatus.BUSY && outOfService) {
            throw new IllegalStateException("Cannot take busy unit out of service");
        }
    
        // 3. Set status
        if (outOfService) {
            unit.setStatus(UnitStatus.OUT_OF_SERVICE);
        } else {
            unit.setStatus(UnitStatus.IDLE);
        }
    }

    @Override
    public int[] getUnitIds() {
    
        int[] ids = new int[unitCount];
    
        for (int i = 0; i < unitCount; i++) {
            ids[i] = units[i].getUnitId();
        }
    
        return ids;
    }
    

    @Override
    public String viewUnit(int id) throws IDNotRecognisedException {

        Unit unit = null;

        for (int i = 0; i < unitCount; i++) {
            if (units[i].getUnitId() == id) {
                unit = units[i];
                break;
            }
        }

        if (unit == null) {
            throw new IDNotRecognisedException("Unit not recognised");
        }

        return "U#" + unit.getUnitId()
        + " TYPE=" + unit.getType()
        + " LOC=(" + unit.getX() + "," + unit.getY() + ")"
        + " STATUS=" + unit.getStatus();
    }
    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y)
            throws InvalidSeverityException, InvalidLocationException {

        // 1. Validate severity
        if (severity < 1 || severity > 10) {
            throw new InvalidSeverityException("Invalid severity");
        }

        // 2. Validate location
        if (!cityMap.inBounds(x, y)) {
            throw new InvalidLocationException("Location out of bounds");
        }

        // 3. Check incident limit
        if (incidentCount >= MAX_INCIDENTS) {
            throw new IllegalStateException("Maximum incidents reached");
        }

        // 4. Create incident
        Incident incident = new Incident(incidentCount, type, severity, x, y);

        // 5. Store it
        incidents[incidentCount] = incident;
        incidentCount++;

        // 6. Return ID
        return incident.getId();
    }

   @Override
    public void cancelIncident(int incidentId)
        throws IDNotRecognisedException, IllegalStateException {

    Incident incident = null;

    // 1. Find incident
    for (int i = 0; i < incidentCount; i++) {
        if (incidents[i].getId() == incidentId) {
            incident = incidents[i];
            break;
        }
    }

    // 2. If not found
    if (incident == null) {
        throw new IDNotRecognisedException("Incident ID not recognised");
    }

    // 3. Cannot cancel if already resolved
    if (incident.getStatus() == IncidentStatus.RESOLVED) {
        throw new IllegalStateException("Incident already resolved");
    }

    // 4. If a unit is assigned, free it
    Unit assigned = incident.getAssignedUnit();
    if (assigned != null) {
        assigned.setStatus(UnitStatus.IDLE);
        assigned.clearIncident();
    }

    // 5. Mark incident as cancelled
    incident.setStatus(IncidentStatus.CANCELLED);
}

    @Override
    public void escalateIncident(int incidentId, int newSeverity)
        throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
    Incident incident = null;

    // 1. Find incident
    for (int i = 0; i < incidentCount; i++) {
        if (incidents[i].getId() == incidentId) {
            incident = incidents[i];
            break;
        }
    }

    if (incident == null) {
        throw new IDNotRecognisedException("Incident ID not recognised");
    }

    // 2. Validate severity
    if (newSeverity < 1 || newSeverity > 10) {
        throw new InvalidSeverityException("Invalid severity");
    }

    // 3. Cannot escalate resolved or cancelled incidents
    if (incident.getStatus() == IncidentStatus.RESOLVED ||
        incident.getStatus() == IncidentStatus.CANCELLED) {
        throw new IllegalStateException("Cannot escalate finished incident");
    }

    // 4. Update severity
    incident.setSeverity(newSeverity);
}

    @Override
    public int[] getIncidentIds() {

        int[] ids = new int[incidentCount];

        for (int i = 0; i < incidentCount; i++) {
            ids[i] = incidents[i].getId();
        }

        return ids;
    }

    @Override
    public String viewIncident(int id) throws IDNotRecognisedException {

        Incident incident = null;

        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i].getIncidentId() == id) {
                incident = incidents[i];
                break;
            }
        }

        if (incident == null) {
            throw new IDNotRecognisedException("Incident not recognised");
        }

        String unitPart = "UNIT=";
        if (incident.getAssignedUnit() != null) {
            unitPart += incident.getAssignedUnit().getUnitId();
        } else {
            unitPart += "NONE";
        }

        return "I#" + incident.getIncidentId()
                + " TYPE=" + incident.getType()
                + " STATUS=" + incident.getStatus()
                + " X=" + incident.getX()
                + " Y=" + incident.getY()
                + " " + unitPart;
    }

    @Override
    public void dispatch() {

        for (int i = 0; i < incidentCount; i++) {

            Incident incident = incidents[i];

            if (incident.getStatus() != IncidentStatus.REPORTED) {
                continue;
            }

            Unit bestUnit = null;
            int bestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < unitCount; j++) {

                Unit unit = units[j];

                // Eligible = NOT busy, NOT out of service, correct type
                if (unit.getStatus() != UnitStatus.BUSY
                        && unit.getStatus() != UnitStatus.OUT_OF_SERVICE
                        && unit.canHandle(incident.getType())) {

                    int distance = Math.abs(unit.getX() - incident.getX())
                            + Math.abs(unit.getY() - incident.getY());

                    if (bestUnit == null
                            || distance < bestDistance
                            || (distance == bestDistance
                                && unit.getUnitId() < bestUnit.getUnitId())) {

                        bestUnit = unit;
                        bestDistance = distance;
                    }
                }
            }

            if (bestUnit != null) {

                incident.setAssignedUnit(bestUnit);
                incident.setStatus(IncidentStatus.DISPATCHED);

                bestUnit.assignIncident(incident);
                bestUnit.setStatus(UnitStatus.BUSY);
            }
        }
    }
    @Override
    public void tick() {

        tick++;  // increase simulation time

        for (int i = 0; i < unitCount; i++) {

            Unit unit = units[i];

            // Only process units that are assigned to an incident
            if (unit.getAssignedIncident() != null) {

                Incident incident = unit.getAssignedIncident();

                int ux = unit.getX();
                int uy = unit.getY();
                int ix = incident.getX();
                int iy = incident.getY();

                // If not yet at incident location → move one step
                if (ux != ix || uy != iy) {

                    int newX = ux;
                    int newY = uy;

                    // Move in X direction first
                    if (ux < ix) {
                        newX++;
                    } else if (ux > ix) {
                        newX--;
                    }
                    // Only move in Y once X matches
                    else if (uy < iy) {
                        newY++;
                    } else if (uy > iy) {
                        newY--;
                    }

                    unit.setLocation(newX, newY);

                } else {

                    // Already at location → resolve incident
                    incident.setStatus(IncidentStatus.RESOLVED);

                    unit.setStatus(UnitStatus.IDLE);
                    unit.clearIncident();
                }
            }
        }
    }
    @Override
    public String getStatus() {

        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("TICK=").append(tick).append("\n");
        sb.append("STATIONS=").append(stationCount).append("\n");
        sb.append("UNITS=").append(unitCount).append("\n");
        sb.append("INCIDENTS=").append(incidentCount).append("\n");
        sb.append("OBSTACLES=").append(cityMap.getObstacleCount()).append("\n");

        // INCIDENTS section
        sb.append("INCIDENTS").append("\n");
        for (int i = 0; i < incidentCount; i++) {
            try {
                sb.append(viewIncident(incidents[i].getIncidentId())).append("\n");
            } catch (IDNotRecognisedException e) {
                // This should never happen because IDs come from our own storage
            }
        }

        // UNITS section
        sb.append("UNITS").append("\n");
        for (int i = 0; i < unitCount; i++) {
            try {
                sb.append(viewUnit(units[i].getUnitId())).append("\n");
            } catch (IDNotRecognisedException e) {
                // Should never happen
            }
        }

        return sb.toString().trim();
    }
}