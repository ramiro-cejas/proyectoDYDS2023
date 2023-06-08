package model;

import utils.ResultInPlainText;

public class TextHandler {
    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text.replace("'", "`");
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }

    public static String doHTMLFromResultInPlainText(ResultInPlainText resultInPlainText){
        String toReturn = "";
        toReturn += ("<h1 face=\"roboto\" color=\"white\">" + resultInPlainText.title + "</h1>");
        toReturn += "<font face=\"roboto\" color=\"white\">" + resultInPlainText.extract + "</font>";
        return toReturn;
    }
}