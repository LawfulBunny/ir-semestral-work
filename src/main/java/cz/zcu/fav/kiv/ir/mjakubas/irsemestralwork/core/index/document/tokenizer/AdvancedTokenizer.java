package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer;

import org.jsoup.Jsoup;
import us.codecraft.xsoup.Xsoup;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Based of task 2 preprocessing.
 */
public class AdvancedTokenizer implements Tokenizer {

    //cislo |  | html | tecky a ostatni
    public static final String defaultRegex = "(\\d+[.,](\\d+)?)|([\\p{L}\\d]+)|(<.*?>)|([\\p{Punct}])";

    /**
     * Tokenizes the given text.
     *
     * @param text  Text.
     * @param regex Regex.
     * @return Tokenized text.
     */
    public static String[] tokenize(String text, String regex) {
        text = removeAccents(text);

        Pattern pattern = Pattern.compile(regex);

        ArrayList<String> words = new ArrayList<String>();

        String[] by = text.split("\\s+");
        for (String b : by) {
            if (isDate(b)) {
                //words.add(b);
                continue;
            }

            if (isUrl(b) || isCensored(b)) {
                //words.add(b);
                continue;
            }

            if (isSpecialCharacter(b)) {
                continue;
            }

            Matcher matcher = pattern.matcher(b);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();

                String s = b.substring(start, end);
                if (s.equals(".") || s.equals(",")) {
                    continue;
                }

                words.add(s);
            }
        }

        String[] ws = new String[words.size()];
        ws = words.toArray(ws);

        return ws;
    }

    private static boolean isUrl(String text) {
        String urlPattern = "((http|https)://)?(www\\.)?[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private static boolean isSpecialCharacter(String text) {
        String specialCharPattern = "^[^a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(specialCharPattern);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private static boolean isCensored(String text) {
        return text.contains("*");
    }

    private static boolean isDate(String text) {
        Pattern pattern = Pattern.compile("(([0-9][0-9]?)\\.([0-9][0-9]?)\\.([0-9][0-9][0-9][0-9])?)");
        return pattern.matcher(text).find();
    }

    public static String removeAccents(String text) {
        return text == null ? null : Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String removeHtmlTags(String text) {
        return Jsoup.parse(text).text();
    }

    @Override
    public String[] tokenize(String text) {
        return tokenize(removeHtmlTags(text), defaultRegex);
    }
}
