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

    public int getStationId() { return stationId; }
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }

    public int getCapacity() { return capacity; }
    public int getUnitCount() { return unitCount; }

    public void setCapacity(int newCapacity) {
        this.capacity = newCapacity
    }

    public boolean hasSpace() {
        return unitCount < capacity;
    }

    public void addUnit(Unit unit) {
        units[unitCount++] = unit;
    }

    public void removeUnit(Unit unit) {
    }
}
