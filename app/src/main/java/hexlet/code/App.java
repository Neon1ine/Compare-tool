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

public final class App implements Callable {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private static String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private static String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]",
            defaultValue = "stylish", paramLabel = "format")
    private static String format;

    private static int exitCode;

    @Override
    public String call() throws Exception {
        String diff = Differ.generate(getFilePath1().toString(), getFilePath2().toString(), format);
        System.out.println(diff);
        return String.valueOf(exitCode);
    }

    public static void main(String[] args) {
        exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    public static Path getFilePath1() {
        return fixPath(filepath1);
    }

    public static Path getFilePath2() {
        return fixPath(filepath2);
    }

    public static String getFormat() {
        return format;
    }

    private static Path fixPath(String path) {
        //path = "./src/test/resources/" + path;
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
