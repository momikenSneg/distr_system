package nsu.fit;

import nsu.fit.osm.StAXProcessor;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Hello World");


        try (InputStream bzIn = new BZip2CompressorInputStream(new FileInputStream(args[0]))){
            new StAXProcessor(bzIn).process();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
























