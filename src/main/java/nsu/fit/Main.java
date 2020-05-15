package nsu.fit;

import nsu.fit.db.JDBCPostgreSQL;
import nsu.fit.db.Service;
import nsu.fit.osm.OSMReader;
import nsu.fit.osm.XMLProcessor;
import nsu.fit.osm.jaxb.JAXBProcessor;
import nsu.fit.osm.stax.StAXProcessor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

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
            ArgsParser.printHelp();
            return;
        }

        String fileName = cmd.hasOption("f1") ? cmd.getOptionValue("f1") : cmd.getOptionValue("f2");


        try (InputStream bzIn = new BZip2CompressorInputStream(new FileInputStream(fileName))){
            JDBCPostgreSQL.connect();
            XMLProcessor processor = cmd.hasOption("f1") ? new StAXProcessor(bzIn) : new JAXBProcessor(bzIn);
            OSMReader reader = new OSMReader(processor);
            Service service = new Service(reader);
            service.putBatch();

        } catch (JAXBException e) {
            logger.error("Not creating JAXBContainer", e);
        } catch (XMLStreamException e) {
            logger.error("XML read error", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        logger.info("Start read file");
//        try (InputStream bzIn = new BZip2CompressorInputStream(new FileInputStream(fileName))) {
//            OSMReader reader = new OSMReader();
//            XMLProcessor processor = cmd.hasOption("f1") ? new StAXProcessor(bzIn) : new JAXBProcessor(bzIn);
//            reader.read(processor);
//            logger.info("Start print info");
//            reader.printChanges();
//            reader.printKeys();
//        } catch (XMLStreamException e) {
//            logger.error("XML read error", e);
//        } catch (FileNotFoundException e) {
//            logger.error("Wrong file name", e);
//        } catch (JAXBException e) {
//            logger.error("Not creating JAXBContainer", e);
//        }

    }

    public static void fillDataBase() throws IOException, SQLException {
        JDBCPostgreSQL.connect();
    }
}
























