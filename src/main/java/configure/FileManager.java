package configure;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileManager {
    private static FileManager INSTANCE;

    private FileManager() {
    }

    public static FileManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FileManager();
        }

        return INSTANCE;
    }

    public String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
    }

    public void writeToPosition(String path, String text, int line, int column, boolean replaceLine) throws IOException {
        ArrayList lineList = new ArrayList<String>();
        String fileSource = readFile(path);
        Stream<String> lines = fileSource.lines();

        AtomicInteger lineCounter = new AtomicInteger(1);
        lines.forEach(l -> {
            if(lineCounter.get() == line) {
                if(replaceLine) {
                    String newLine = l.substring(0, column - 1) + text;
                    lineList.add(newLine);
                } else {
                    String newLine = StringUtils.repeat(" ", column - 1) + text;
                    lineList.add(newLine);
                    lineList.add(l);
                }
            } else {
                lineList.add(l);
            }

            lineCounter.getAndIncrement();
        });
        lineList.add("");

        String output = Joiner.on(System.lineSeparator()).join(lineList);
        Files.writeString(Paths.get(path), output);
    }
}
