package by.matsuk.multithreading.main;

import by.matsuk.multithreading.entity.Bus;
import by.matsuk.multithreading.entity.BusRoute;
import by.matsuk.multithreading.entity.BusStop;
import by.matsuk.multithreading.exception.BusException;
import by.matsuk.multithreading.parser.BusParser;
import by.matsuk.multithreading.parser.BusStopParser;
import by.matsuk.multithreading.parser.impl.BusParserImpl;
import by.matsuk.multithreading.parser.impl.BusStopParserImpl;
import by.matsuk.multithreading.reader.BusFileReader;
import by.matsuk.multithreading.reader.impl.BusFileReaderImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws BusException {
        BusParser busParser = new BusParserImpl();
        BusStopParser busStopParser = new BusStopParserImpl();
        BusFileReader busFileReader = new BusFileReaderImpl();
        List<String> lines = busFileReader.readDataFromFile("data/busStop.txt");
        List<BusStop> busStops = busStopParser.parseBusStop(lines);
        lines = busFileReader.readDataFromFile("data/busData.txt");
        List<Bus> buses = busParser.parseBuses(lines);

        BusRoute busRoute = BusRoute.getInstance();
        busRoute.setBusStops(busStops);
        busRoute.setBusRouteNumber(100);

        ExecutorService executorForBusesService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        buses.forEach(executorForBusesService::execute);
        executorForBusesService.shutdown();

        boolean allBusesFinished = false;
        while (!allBusesFinished) {
            allBusesFinished = buses.stream().allMatch(bus -> bus.getNextStop() == 4);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
