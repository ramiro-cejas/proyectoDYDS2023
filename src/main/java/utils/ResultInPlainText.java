package utils;

public class ResultInPlainText {
    public String title = null;
    public String pageID = null;
    public String snippet = null;
    public String extract = null;

    public ResultInPlainText(String title, String pageID, String snippet, String extract){
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
        this.extract = extract;
    }

    public String toString(){
        return ("Titulo: "+title+"\nPageID: "+pageID+"\nSnippet : "+snippet+"\nExtract: "+extract);
    }

}
