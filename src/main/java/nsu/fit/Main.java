package nsu.fit;

import nsu.fit.osm.OSMReader;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        Option option = new Option("f", "file", true, "File name");
        option.setArgs(1);
        option.setArgName("file name ");
        option.setRequired(true);
        Options options = new Options();
        options.addOption(option);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, args);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }


        logger.info("Start read file");
        try (InputStream bzIn = new BZip2CompressorInputStream(new FileInputStream(cmd.getOptionValue("f")))){
            OSMReader reader = new OSMReader();
            reader.read(bzIn);
            logger.info("Start print info");
            reader.printChanges();
            reader.printKeys();
        } catch (XMLStreamException e) {
            logger.error("XML read error", e);
        } catch (FileNotFoundException e){
            logger.error("Wrong file name", e);
        }

    }
}
























