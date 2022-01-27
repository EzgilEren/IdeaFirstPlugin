package configure;

import com.intellij.openapi.project.Project;
import configure.exception.FindDependencyBlockException;
import configure.exception.ReadBuildGradleException;
import configure.exception.WriteBuildGradleException;
import groovyjarjarantlr.TokenStream;
import groovyjarjarantlr.TokenStreamException;
import org.codehaus.groovy.antlr.parser.GroovyLexer;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

public class GradleManager {
    private static GradleManager INSTANCE;

    private GradleManager() {
    }

    public static GradleManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GradleManager();
        }

        return INSTANCE;
    }

    private TokenStream getTokenStream(Project project) throws ReadBuildGradleException {
        String gradleFilePath = project.getBasePath() + "/entry/build.gradle";
        try {
            String gradleSource = FileManager.getInstance().readFile(gradleFilePath);
            GroovyLexer lexer = new GroovyLexer(new StringReader(gradleSource));
            return lexer.plumb();
        } catch (IOException e) {
            throw new ReadBuildGradleException();
        }
    }

    public void addDependency(Project project, GradleDependency dependency) throws FindDependencyBlockException, ReadBuildGradleException, TokenStreamException, WriteBuildGradleException {
        TokenStream tokenStream = getTokenStream(project);
        var token = tokenStream.nextToken();
        while (token.getType() != 1)  {
            if(Objects.equals(token.getText(), "dependencies")){
                while (token.getType() != 1)  {
                    if(token.getText().contains(dependency.name)) {
                        String gradleFilePath = project.getBasePath() + "/entry/build.gradle";
                        try {
                            FileManager.getInstance().writeToPosition(gradleFilePath, dependency.onlyNameVersionString(), token.getLine(), token.getColumn(), true);
                        } catch (IOException e) {
                            throw new WriteBuildGradleException();
                        }
                        return;
                    } else if(Objects.equals(token.getText(), "}")){
                        int column = token.getColumn() + 4; // tab for block
                        String gradleFilePath = project.getBasePath() + "/entry/build.gradle";
                        try {
                            FileManager.getInstance().writeToPosition(gradleFilePath, dependency.fullString(), token.getLine(), column, false);
                        } catch (IOException e) {
                            throw new WriteBuildGradleException();
                        }
                        return;
                    } else {
                        token = tokenStream.nextToken();
                    }
                }
            } else {
                token = tokenStream.nextToken();
            }
        }

        throw new FindDependencyBlockException();
    }
}
