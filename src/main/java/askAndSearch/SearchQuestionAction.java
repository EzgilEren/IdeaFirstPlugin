package askAndSearch;

import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NonNls;

import java.util.Locale;
import java.util.Objects;

public class SearchQuestionAction extends AnAction {
    /**
     * Convert selected text to a URL friendly string.
     *
     * @param event
     */
    @Override
    public final void actionPerformed(AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        // For searches from the editor, we should also get file type information
        // to help add scope to the search using the Huawei search syntax.

        // Using of Plugin DevKit -> https://plugins.jetbrains.com/docs/intellij/getting-started.html?from=jetbrains.org#using-devkit
        @NonNls String languageTag = "";
        PsiFile file = event.getData(CommonDataKeys.PSI_FILE);
        if (file != null) {
            Language lang = event.getData(CommonDataKeys.PSI_FILE).getLanguage();
            languageTag = "+[" + lang.getDisplayName().toLowerCase(Locale.ROOT) + "]";
        }

        // The update method below is only called periodically so need
        // to be careful to check for selected text
        if (caretModel.getCurrentCaret().hasSelection()) {
            String query = Objects.requireNonNull(caretModel.getCurrentCaret().getSelectedText()).replace(' ', '+') + languageTag;
            BrowserUtil.browse("https://developer.huawei.com/consumer/en/doc/search?type=all&val=" + query);
        }
    }

    /**
     * Only make this action visible when text is selected.
     *
     * @param event
     */
    @Override
    public void update(AnActionEvent event) {
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        event.getPresentation().setEnabledAndVisible(caretModel.getCurrentCaret().hasSelection());
    }
}
