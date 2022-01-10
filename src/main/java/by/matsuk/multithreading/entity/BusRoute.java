package by.matsuk.multithreading.entity;

import by.matsuk.multithreading.util.BusRouteIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.StringJoiner;

public class BusRoute {
    static final Logger logger = LogManager.getLogger();
    private long busRouteId;
    private int busRouteNumber;
    private List<BusStop> busStops;

    private BusRoute() {
        this.busRouteId = BusRouteIdGenerator.generateId();
    }

    private static class SingletonHolder {
        private static final BusRoute instance = new BusRoute();
    }

    public static BusRoute getInstance() {
        return SingletonHolder.instance;
    }

    public BusStop getNextBusStop(int i) {
        return busStops.get(i);
    }

    public long getBusRouteId() {
        return busRouteId;
    }

    public int getBusRouteNumber() {
        return busRouteNumber;
    }

    public void setBusRouteNumber(int busRouteNumber) {
        this.busRouteNumber = busRouteNumber;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusRoute busRoute = (BusRoute) o;
        if (busRouteId != busRoute.busRouteId) return false;
        if (busRouteNumber != busRoute.busRouteNumber) return false;
        return busStops != null ? busStops.equals(busRoute.busStops) : busRoute.busStops == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (busRouteId ^ (busRouteId >>> 32));
        result = 31 * result + busRouteNumber;
        result = 31 * result + (busStops != null ? busStops.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BusRoute.class.getSimpleName() + "[", "]")
                .add("busRouteId=" + busRouteId)
                .add("busRouteNumber=" + busRouteNumber)
                .add("busStops=" + busStops)
                .toString();
    }
}
