package nsu.fit;

import org.apache.commons.cli.*;

public class ArgsParser {

    private static final Option option = new Option("f", "file", true, "File name");
    private static final CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();

    static {
        option.setArgs(1);
        option.setArgName("file name ");
        option.setRequired(true);
        options.addOption(option);
    }

    public static CommandLine parse(String[] args) throws ParseException {
        return parser.parse(options, args);
    }
}
