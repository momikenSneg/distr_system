package nsu.fit;

import org.apache.commons.cli.*;

class ArgsParser {

    private static final Option option1 = new Option("f1", "file", true, "File name");
    private static final Option option21 = new Option("f21", "file", true, "File name");
    private static final Option option22 = new Option("f22", "file", true, "File name");
    private static final Option option3 = new Option("f3", "http", false, "http");
    private static final CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();

    static {
        option1.setArgs(1);
        option1.setArgName("file name ");
        option1.setRequired(false);

        option21.setArgs(1);
        option21.setArgName("file name ");
        option21.setRequired(false);

        option22.setArgs(1);
        option22.setArgName("file name ");
        option22.setRequired(false);

        option3.setRequired(false);

        options.addOption(option1);
        options.addOption(option21);
        options.addOption(option22);
        options.addOption(option3);
    }

    static CommandLine parse(String[] args) throws ParseException {
        return parser.parse(options, args);
    }


    static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "distr-1.0.jar [OPTIONS]", options );
    }
}
