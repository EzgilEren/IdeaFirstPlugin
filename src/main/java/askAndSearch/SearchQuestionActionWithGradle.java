package askAndSearch;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;

import java.util.Optional;

public class SearchQuestionActionWithGradle extends AnAction {
    /**
     * Convert selected text to a URL friendly string.
     *
     * @param event
     */
    @Override
    public final void actionPerformed(AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();

        // Using of Gradle -> https://plugins.jetbrains.com/docs/intellij/getting-started.html?from=jetbrains.org#using-gradle
        Optional<PsiFile> psiFile = Optional.ofNullable(event.getData(CommonDataKeys.PSI_FILE));
        String typeTag = psiFile
                .map(PsiFile::getFileElementType)
                .map(lang -> "all")
                .orElse("doc");

        BrowserUtil.browse("https://developer.huawei.com/consumer/en/doc/search?type=" + typeTag + "&val=" + selectedText);
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
