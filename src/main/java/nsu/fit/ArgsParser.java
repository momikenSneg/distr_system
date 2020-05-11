package nsu.fit;

import org.apache.commons.cli.*;

class ArgsParser {

    private static final Option option1 = new Option("f1", "file", true, "File name");
    private static final Option option2 = new Option("f2", "file", true, "File name");
    private static final CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();

    static {
        option1.setArgs(1);
        option1.setArgName("file name ");
        option1.setRequired(false);

        option2.setArgs(1);
        option2.setArgName("file name ");
        option2.setRequired(false);

        options.addOption(option1);
        options.addOption(option2);
    }

    static CommandLine parse(String[] args) throws ParseException {
        return parser.parse(options, args);
    }

    static Options getOptions() {
        return options;
    }

    public static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "distr-1.0.jar [OPTIONS]", ArgsParser.getOptions() );
    }
}
