package by.matsuk.multithreading.reader;

import by.matsuk.multithreading.exception.BusException;

import java.util.List;

public interface BusFileReader {
    List<String> readDataFromFile(String filePath) throws BusException;
}
