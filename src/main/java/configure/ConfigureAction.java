package configure;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import configure.exception.FindDependencyBlockException;
import configure.exception.ReadBuildGradleException;
import configure.exception.WriteBuildGradleException;
import groovyjarjarantlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;

class ConfigureAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String message;

        try {
            GradleDependency dependency = new GradleDependency("implementation", "com.huawei.agconnect:agconnect-function-harmony", "1.2.2.300");
            GradleManager.getInstance().addDependency(project, dependency);
            message = "Success!";
        } catch (TokenStreamException ex) {
            message = "TokenStreamException";
        } catch (FindDependencyBlockException ex) {
            message = "FindDependencyBlockException";
        } catch (ReadBuildGradleException ex) {
            message = "ReadBuildGradleException";
        } catch (WriteBuildGradleException ex) {
            message = "WriteBuildGradleException";
        }

        Messages.showMessageDialog(project, message, "Configuration Result", Messages.getInformationIcon());
    }
}
