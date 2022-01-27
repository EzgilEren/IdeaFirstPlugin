package askAndSearch;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class AskQuestionAction extends AnAction {

    @Override
    public final void actionPerformed(@NotNull AnActionEvent e) {
        // We use the built-in BrowserUtil class because it handles all the nuances of opening a web page on different operating systems and browsers.
        BrowserUtil.browse(" https://forums.developer.huawei.com/forumPortal/en/topicpost");
    }
}
