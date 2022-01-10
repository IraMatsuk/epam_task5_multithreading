package by.matsuk.multithreading.entity;

import by.matsuk.multithreading.util.BusStopIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    static final Logger logger = LogManager.getLogger();
    private long busStopId;
    private String busStopName;
    private int busesMax;
    private long timeStop;
    private Semaphore semaphore;
    private AtomicInteger passengers;
    private Lock passengersLock = new ReentrantLock(true);
    private Random random = new Random();

    public BusStop() {
        busStopId = BusStopIdGenerator.generateId();
        passengers = new AtomicInteger(10);
        semaphore = new Semaphore(2, true);
    }

    public BusStop(String busStopName, int busesMax, long timeStop, int passengers) {
        busStopId = BusStopIdGenerator.generateId();
        this.busStopName = busStopName;
        this.busesMax = busesMax;
        this.timeStop = timeStop;
        semaphore = new Semaphore(busesMax, true);
        this.passengers = new AtomicInteger(passengers);
    }

    public long getBusStopId() {
        return busStopId;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public int getBusesMax() {
        return busesMax;
    }

    public void setBusesMax(int busesMax) {
        this.busesMax = busesMax;
    }

    public long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(long timeStop) {
        this.timeStop = timeStop;
    }

    public AtomicInteger getPassengers() {
        return passengers;
    }

    public void setPassengers(AtomicInteger passengers) {
        this.passengers = passengers;
    }

    public void busParking(Bus bus) {
        try {
            semaphore.acquire();
            logger.info(String.format("Bus with ID = %d has been stopped on the bus stop name = %s. Amount of passengers on the bus stop: %d, in the bus: %d", bus.getBusId(), busStopName, passengers.get(), bus.getPassengers()));
            passengersGetOff(bus);
            passengersGetOn(bus);
            TimeUnit.SECONDS.sleep(timeStop);
            logger.info(String.format("Bus with ID = %d has been driven to the next bus stop. Amount of passengers on the bus stop: %d, in the bus: %d", bus.getBusId(), passengers.get(), bus.getPassengers()));
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void passengersGetOff(Bus bus) {
        boolean getOff = false;
        int busPassengers = bus.getPassengers();
        int passengersGetOff = random.nextInt(busPassengers);
        int newPassengersOnStation = 0;
        busPassengers = busPassengers - passengersGetOff;
        bus.setPassengers(busPassengers);
        do {
            int passengersOnStation = passengers.get();
            newPassengersOnStation = passengersOnStation + passengersGetOff;
            getOff = passengers.compareAndSet(passengersOnStation, newPassengersOnStation);
        } while (!getOff);
        logger.info(String.format("Passengers get off the Bus with ID = %d on the bus stop %s, amount of passengers get off: %d. Amount of passengers on the bus stop: %d", bus.getBusId(), busStopName, passengersGetOff, newPassengersOnStation));
    }

    private void passengersGetOn(Bus bus) {
        boolean getOn = false;
        int passengersGetOn = 0;
        int passengersOnStation = 0;
        int passengersCanGetOn = bus.getMaxPassengers() - bus.getPassengers();
        int newPassengersOnStation = 0;
        do {
            passengersOnStation = passengers.get();

            if (passengersCanGetOn >= passengersOnStation) {
                passengersGetOn = random.nextInt(passengersOnStation);
            } else {
                passengersGetOn = random.nextInt(passengersCanGetOn);
            }

            newPassengersOnStation = passengersOnStation - passengersGetOn;
            getOn = passengers.compareAndSet(passengersOnStation, newPassengersOnStation);
        } while (!getOn);

        int newPassengersInBus = passengersGetOn + bus.getPassengers();
        bus.setPassengers(newPassengersInBus);
        logger.info(String.format("Passengers get on the bus with ID = %d on the bus stop %s count %d. Passengers on the bus stop %d", bus.getBusId(), busStopName, passengersGetOn, newPassengersOnStation));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusStop busStop = (BusStop) o;
        if (busStopId != busStop.busStopId) return false;
        if (busesMax != busStop.busesMax) return false;
        if (timeStop != busStop.timeStop) return false;
        if (busStopName != null ? !busStopName.equals(busStop.busStopName) : busStop.busStopName != null) return false;
        if (semaphore != null ? !semaphore.equals(busStop.semaphore) : busStop.semaphore != null) return false;
        if (passengers != null ? !passengers.equals(busStop.passengers) : busStop.passengers != null) return false;
        return random != null ? random.equals(busStop.random) : busStop.random == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (busStopId ^ (busStopId >>> 32));
        result = 31 * result + (busStopName != null ? busStopName.hashCode() : 0);
        result = 31 * result + busesMax;
        result = 31 * result + (int) (timeStop ^ (timeStop >>> 32));
        result = 31 * result + (semaphore != null ? semaphore.hashCode() : 0);
        result = 31 * result + (passengers != null ? passengers.hashCode() : 0);
        result = 31 * result + (random != null ? random.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusStop{");
        sb.append("busStopId=").append(busStopId);
        sb.append(", name='").append(busStopName).append('\'');
        sb.append(", busesMax=").append(busesMax);
        sb.append(", timeStop=").append(timeStop);
        sb.append(", semaphore=").append(semaphore);
        sb.append(", passengers=").append(passengers);
        sb.append(", random=").append(random);
        sb.append('}');
        return sb.toString();
    }
}
