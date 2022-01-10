package by.matsuk.multithreading.parser;

import by.matsuk.multithreading.entity.Bus;

import java.util.List;

public interface BusParser {
    List<Bus> parseBuses(List<String> lines);
}
