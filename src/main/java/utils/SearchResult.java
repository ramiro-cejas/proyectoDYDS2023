package utils;

import javax.swing.*;

public class SearchResult extends JMenuItem {
    public final String title;
    public final String pageID;
    public final String snippet;

    public SearchResult(String title, String pageID, String snippet) {
        String itemText = "<html><font face=\"arial\">" + title + ": " + snippet;
        itemText = itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        this.setText(itemText);
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }


}
