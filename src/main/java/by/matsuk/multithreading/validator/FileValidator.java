package by.matsuk.multithreading.validator;

import by.matsuk.multithreading.exception.BusException;

import java.io.File;

public class FileValidator {
    public static boolean isFileValidate(String fileAddress) throws BusException {
        if (fileAddress == null || fileAddress.isBlank()) {
            throw new BusException("Invalid file address" + fileAddress);
        }
        File file = new File(fileAddress);
        if (file.exists() && file.canRead() && file.isFile() && file.length() > 0) {
            return true;
        }
        return false;
    }
}
