import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deque<String> stack = new ArrayDeque<>();
        Deque<String> deque = new ArrayDeque<>();

        String htmlString = scanner.nextLine();
        String nextTag = "";

        //examples for debug:
        //String htmlString = "<html><a>hello</a><h1><h4>nestedHello</h4><h3>nestedWorld</h3><h6><br>top</br></h6></h1><br>world</br></html>";
        //String htmlString = "<html><input><h2><a>hello</a><br>world</br></h2></input></html>";

        Pattern pattern = Pattern.compile("<([^/].*?)>");
        Matcher matcher = pattern.matcher(htmlString);
        if (matcher.find()) {
            nextTag = matcher.group(1);
        }


        deque.offer(getBetweenStrings(htmlString, "<" + nextTag + ">", "</" + nextTag + ">"));


        while (!deque.isEmpty()) {
            stack.offer(deque.pollLast());

            if (stack.peekLast().contains("<")) {
                matcher = pattern.matcher(stack.peekLast());
                if (matcher.find()) {
                    nextTag = matcher.group(1);
                }
                deque.offer(getBetweenStrings(stack.peekLast(), "<" + nextTag + ">", "</" + nextTag + ">"));

                String substr = stack.peekLast().substring(stack.peekLast().indexOf("</" + nextTag + ">") + nextTag.length() + 3);

                while (substr.length() > 0) {
                    matcher = pattern.matcher(substr);
                    if (matcher.find()) {
                        nextTag = matcher.group(1);
                    }
                    deque.offer(getBetweenStrings(substr, "<" + nextTag + ">", "</" + nextTag + ">"));
                    substr = substr.substring(substr.indexOf("</" + nextTag + ">") + nextTag.length() + 3);
                }
            }
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.pollLast());
        }
    }

    /**
     * Get text between two strings. Passed limiting strings are not
     * included into result.
     *
     * @param text     Text to search in.
     * @param textFrom Text to start cutting from (exclusive).
     * @param textTo   Text to stop cuutting at (exclusive).
     */
    public static String getBetweenStrings(
            String text,
            String textFrom,
            String textTo) {

        String result = "";

        // Cut the beginning of the text to not occasionally meet a
        // 'textTo' value in it:
        result =
                text.substring(
                        text.indexOf(textFrom) + textFrom.length(),
                        text.length());

        // Cut the excessive ending of the text:
        result =
                result.substring(
                        0,
                        result.indexOf(textTo));

        return result;
    }
}