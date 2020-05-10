package nsu.fit;

import nsu.fit.osm.OSMReader;
import nsu.fit.osm.jaxb.JAXBProcessor;
import nsu.fit.osm.jaxb.generated.Osm;
import nsu.fit.osm.stax.StAXProcessor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.*;

//compile group: 'org.glassfish.jaxb', name: 'jaxb-runtime', version: '2.2.7'

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        CommandLine cmd;
        try {
            cmd = ArgsParser.parse(args);
            if (!cmd.hasOption("f1") && !cmd.hasOption("f2"))
                throw new ParseException("Need at leas one arg");
        } catch (ParseException e) {
            logger.error("Wrong args", e);
            printHelp();
            return;
        }


//        try {
//            File file = new File("RU-NVS.osm");
//            JAXBContext jaxbContext;
//            jaxbContext = JAXBContext.newInstance(Osm.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Osm osm = (Osm) jaxbUnmarshaller.unmarshal(file);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }



        String fileName = cmd.hasOption("f1") ? cmd.getOptionValue("f1") : cmd.getOptionValue("f2");

        logger.info("Start read file");
        try (InputStream bzIn = new BZip2CompressorInputStream(new FileInputStream(fileName))) {
            OSMReader reader = new OSMReader();
            XMLProcessor processor = cmd.hasOption("f1") ? new StAXProcessor(bzIn) : new JAXBProcessor(bzIn);
            reader.read(processor);
            logger.info("Start print info");
            reader.printChanges();
            reader.printKeys();
        } catch (XMLStreamException e) {
            logger.error("XML read error", e);
        } catch (FileNotFoundException e) {
            logger.error("Wrong file name", e);
        } catch (JAXBException e) {
            logger.error("Not creating JAXBContainer", e);
        }

    }

    private static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "distr-1.0.jar [OPTIONS]", ArgsParser.getOptions() );
    }
}
























