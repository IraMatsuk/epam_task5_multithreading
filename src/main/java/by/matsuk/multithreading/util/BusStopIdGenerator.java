package by.matsuk.multithreading.util;

public class BusStopIdGenerator {
    private static long idCounter;

    private BusStopIdGenerator() {
    }

    public static long generateId() {
        return ++idCounter;
    }
}
