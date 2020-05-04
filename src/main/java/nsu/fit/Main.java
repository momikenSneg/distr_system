package nsu.fit;

import org.apache.commons.compress.compressors.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) throws CompressorException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Hello World");
    }
}
