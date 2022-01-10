package by.matsuk.multithreading.parser;

import by.matsuk.multithreading.entity.BusStop;

import java.util.List;

public interface BusStopParser {
    List<BusStop> parseBusStop(List<String> lines);
}
