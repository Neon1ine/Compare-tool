package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")

public class App implements Callable {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private static String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private static String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]",
            defaultValue = "stylish", paramLabel = "format")
    private static String format;

    @Override
    public String call() throws Exception {
        String diff = Differ.findDiff();
        System.out.println(diff);
        return diff;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    public static Path getFilePath1() {
        return fixPath(filepath1);
    }

    public static Path getFilePath2() {
        return fixPath(filepath2);
    }

    private static Path fixPath(String path) {
        String newPath = "./src/test/resources/" + path;
        return Paths.get(newPath).toAbsolutePath().normalize();
    }
}
