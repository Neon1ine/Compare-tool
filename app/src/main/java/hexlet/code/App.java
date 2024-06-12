package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")

public final class App implements Callable {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private static String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private static String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]",
            defaultValue = "stylish", paramLabel = "format")
    private static String format;

    @Override
    public Integer call() {
        String diff;
        try {
            diff = Differ.generate(filepath1, filepath2, format);
        } catch (Exception e) {
            System.err.println("Exception in App.java method call()");
            return 1;
        }
        System.out.println(diff);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
