package shared;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyUtility {
    /**
     * copies the given content into the system clipboard
     * @param content the content to copy
     * @see <a href="https://www.baeldung.com/java-clipboard-copy-paste-text#2-retrieving-from-clipboard">...</a>
     */
    public static void setSystemClipboardContent(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }
}
