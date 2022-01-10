package by.matsuk.multithreading.util;

public class BusIdGenerator {
    private static long idCounter;

    private BusIdGenerator() {
    }

    public static long generateId() {
        return ++idCounter;
    }
}
