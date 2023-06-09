package testUtilities;

public class ResultInStrings implements Comparable<ResultInStrings>{
    private final String title;
    private final String pageID;
    private final String snippet;

    public ResultInStrings(String title, String pageID, String snippet){
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }

    public String toString(){
        return ("Title: " +title+", PageID: "+pageID+", Snippet: "+snippet);
    }

    @Override
    public int compareTo(ResultInStrings o) {
        return pageID.compareTo(o.pageID);
    }
}