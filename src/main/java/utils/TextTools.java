package utils;

public class TextTools {
    public static String doHTMLFromResultInPlainText(ResultInPlainText resultInPlainText){
        String toReturn = "";
        toReturn += ("<h1 face=\"roboto\" color=\"white\">" + resultInPlainText.title + "</h1>");
        toReturn += "<font face=\"roboto\" color=\"white\">" + resultInPlainText.extract + "</font>";
        return toReturn;
    }
}