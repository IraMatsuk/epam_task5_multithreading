package by.matsuk.multithreading.parser.impl;

import by.matsuk.multithreading.entity.Bus;
import by.matsuk.multithreading.parser.BusParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BusParserImpl implements BusParser {
    static final Logger logger = LogManager.getLogger();
    private static final String STRING_DELIMITER = "\\s";
    private static final int BUS_NUMBER_POSITION = 0;
    private static final int BUS_PASSENGERS_POSITION = 1;
    private static final int BUS_MAX_PASSENGERS_AMOUNT = 2;

    @Override
    public List<Bus> parseBuses(List<String> lines) {
        List<Bus> buses = new ArrayList<>();
        for (String line : lines) {
            String[] parameters = line.split(STRING_DELIMITER);
            Bus bus = new Bus(Integer.valueOf(parameters[BUS_NUMBER_POSITION]),
                            Integer.valueOf(parameters[BUS_PASSENGERS_POSITION]),
                            Integer.valueOf(parameters[BUS_MAX_PASSENGERS_AMOUNT]));
            buses.add(bus);
        }
        logger.info("Buses was parsed successfully.");
        return buses;
    }
}
