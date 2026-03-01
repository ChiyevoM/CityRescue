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
    
        tick = 0;
    }

    @Override
    public int[] getGridSize() {
        return new int[] { cityMap.getWidth(), cityMap.getHeight() };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {

        // Validate location first
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
    
        Station station = new Station(stationCount, name, x, y);
        stations[stationCount] = station;
    
        stationCount++;
    
        return station.getId();
    }

    @Override
    public void removeStation(int stationId)
            throws IDNotRecognisedException, IllegalStateException {
    
        // 1. Find station
        Station station = null;
        int index = -1;
    
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getId() == stationId) {
                station = stations[i];
                index = i;
                break;
            }
        }
    
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
    
        // 2. Check if station still has units
        if (station.getCurrentUnitCount() > 0) {
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
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
    
        int[] ids = new int[stationCount];
    
        for (int i = 0; i < stationCount; i++) {
            ids[i] = stations[i].getId();
        }
    
        return ids;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
