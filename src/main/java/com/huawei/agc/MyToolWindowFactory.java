package com.huawei.agc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class MyToolWindowFactory extends AnAction implements DumbAware {

    public static final String LIGHT_SETTING_PNG = "/icons/toolWindow/lightSetting.png";
    public static final Icon AGC_DOWNLOAD = loadIcon(LIGHT_SETTING_PNG);
    public static final String CONFIGURATION_WIZARD_ACTION = "Configuration Wizard";

    public static synchronized Icon loadIcon(String path) {
        return IconLoader.getIcon(path);
    }

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MyToolWindow myToolWindow = new MyToolWindow(toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        if ((!project.isInitialized()) || (!project.isOpen())) {
            return;
        }

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow(CONFIGURATION_WIZARD_ACTION);

        if (toolWindow == null) {
            toolWindow = toolWindowManager.registerToolWindow(CONFIGURATION_WIZARD_ACTION, false,
                    ToolWindowAnchor.RIGHT, project, true);
            toolWindow.setIcon(AGC_DOWNLOAD);
        }

        createToolWindowContent(project, toolWindow);

        toolWindow.setToHideOnEmptyContent(true);
        toolWindow.activate(null);
    }
}
