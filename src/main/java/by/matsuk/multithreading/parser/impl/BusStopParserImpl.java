package by.matsuk.multithreading.parser.impl;

import by.matsuk.multithreading.entity.BusStop;
import by.matsuk.multithreading.parser.BusStopParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BusStopParserImpl implements BusStopParser {
    static final Logger logger = LogManager.getLogger();
    private static final String STRING_DELIMITER = "\\s";
    private static final int BUS_STOP_NAME = 0;
    private static final int BUS_STOP_MAX_BUS_AMOUNT = 1;
    private static final int BUS_STOP_TIME_STOP = 2;
    private static final int BUS_STOP_PASSENGERS_AMOUNT = 3;

    @Override
    public List<BusStop> parseBusStop(List<String> lines) {
        List<BusStop> busStops = new ArrayList<>();
        for (String line : lines) {
            String[] parameters = line.split(STRING_DELIMITER);
            BusStop busStop = new BusStop(parameters[BUS_STOP_NAME],
                    Integer.valueOf(parameters[BUS_STOP_MAX_BUS_AMOUNT]),
                    Integer.valueOf(parameters[BUS_STOP_TIME_STOP]),
                    Integer.valueOf(parameters[BUS_STOP_PASSENGERS_AMOUNT]));
            busStops.add(busStop);
        }
        logger.info("Bus stops was parsed successfully");
        return busStops;
    }
}
