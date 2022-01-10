package by.matsuk.multithreading.util;

public class BusRouteIdGenerator {
    private static long idCounter;

    private BusRouteIdGenerator() {
    }

    public static long generateId() {
        return ++idCounter;
    }
}
