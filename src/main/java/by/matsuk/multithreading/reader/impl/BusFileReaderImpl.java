package by.matsuk.multithreading.reader.impl;

import by.matsuk.multithreading.exception.BusException;
import by.matsuk.multithreading.reader.BusFileReader;
import by.matsuk.multithreading.validator.FileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusFileReaderImpl implements BusFileReader {
    static final Logger logger = LogManager.getLogger();

    public BusFileReaderImpl() {
    }

    @Override
    public List<String> readDataFromFile(String filePath) throws BusException {
        if (!FileValidator.isFileValidate(filePath)) {
            logger.error("File name is null or empty " + filePath);
            throw new BusException("File name is null or empty " + filePath);
        }
        try (Stream<String> streamLines = Files.lines(Paths.get(filePath))) {
            List<String> buses = streamLines.collect(Collectors.toList());
            logger.info("File was read. Result: " + buses);
            return buses;
        } catch (IOException e) {
            logger.error("Error while reading file " + filePath, e.getMessage());
            throw new BusException("Error while reading file " + filePath + e.getMessage());
        }
    }
}
