package by.matsuk.multithreading.entity;

import by.matsuk.multithreading.util.BusIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bus implements Runnable {
    static final Logger logger = LogManager.getLogger();
    private long busId;
    private int busNumber;
    private int nextStop;
    private int passengers;
    private int maxPassengers;

    public Bus(int busNumber, int passengers, int maxPassengers) {
        this.busId = BusIdGenerator.generateId();
        this.busNumber = busNumber;
        this.nextStop = 0;
        this.passengers = passengers;
        this.maxPassengers = maxPassengers;
    }

    public long getBusId() {
        return busId;
    }

    public void setBusId(long busId) {
        this.busId = busId;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public int getNextStop() {
        return nextStop;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    @Override
    public void run() {
        logger.info(String.format("The BUS ID %d with the route %d started the trip.", busId, busNumber));
        BusRoute busRoute = BusRoute.getInstance();
        int busStopOnRoute = busRoute.getBusStops().size();
        for (int i = 0; i < busStopOnRoute; nextStop++) {
            BusStop busStop = busRoute.getNextBusStop(nextStop);
            busStop.busParking(this);
        }
        logger.info(String.format("The BUS ID %d with the route %d has finished the trip.", busId, busNumber));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        if (busId != bus.busId) return false;
        if (busNumber != bus.busNumber) return false;
        if (nextStop != bus.nextStop) return false;
        if (passengers != bus.passengers) return false;
        return maxPassengers == bus.maxPassengers;
    }

    @Override
    public int hashCode() {
        int result = (int) (busId ^ (busId >>> 32));
        result = 31 * result + busNumber;
        result = 31 * result + nextStop;
        result = 31 * result + passengers;
        result = 31 * result + maxPassengers;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bus{");
        sb.append("busId=").append(busId);
        sb.append(", busNumber=").append(busNumber);
        sb.append(", nextStop=").append(nextStop);
        sb.append(", passengers=").append(passengers);
        sb.append(", maxPassengers=").append(maxPassengers);
        sb.append('}');
        return sb.toString();
    }
}
