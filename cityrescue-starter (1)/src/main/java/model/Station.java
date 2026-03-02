package cityrescue.model;

public class Station {

    private int stationId;
    private String name;
    private int x;
    private int y;

    private int capacity;
    private Unit[] units;
    private int unitCount;

    public Station(int stationId, String name, int x, int y, int capacity) {
        this.stationId = stationId;
        this.name = name;
        this.x = x;
        this.y = y;
        this.capacity = capacity;
        this.units = new Unit[capacity];
        this.unitCount = 0;
    }

    public int getId() {
        return stationId;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaxUnits() {
        return capacity;
    }

    public int getCurrentUnitCount() {
        return unitCount;
    }
    
    public int getStationId() {
        return stationId;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxUnits(int newCapacity) {
        this.capacity = newCapacity;
    }

    public void addUnit(Unit unit) {
        units[unitCount] = unit;
        unitCount++;
    }

    public void removeUnit(Unit unit) {
        for (int i = 0; i < unitCount; i++) {
            if (units[i] == unit) {

                for (int j = i; j < unitCount - 1; j++) {
                    units[j] = units[j + 1];
                }

                units[unitCount - 1] = null;
                unitCount--;
                break;
            }
        }
    }
}
