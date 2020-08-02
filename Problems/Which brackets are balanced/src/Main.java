import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deque<Character> deq = new ArrayDeque<>();
        boolean isBalanced = true;

        String brackets = scanner.nextLine();

        for (int i = 0; i < brackets.length(); i++) {
            char ch = brackets.charAt(i);
            if (ch == '{' || ch == '[' || ch == '(') {
                deq.offer(ch);
            } else if (ch == '}' && (deq.isEmpty() || deq.pollLast() != '{')) {
                isBalanced = false;
                break;
            } else if (ch == ']' && (deq.isEmpty() || deq.pollLast() != '[')) {
                isBalanced = false;
                break;
            } else if (ch == ')' && (deq.isEmpty() || deq.pollLast() != '(')) {
                isBalanced = false;
                break;
            }
        }

        if (!deq.isEmpty()) {
            isBalanced = false;
        }
        System.out.println(isBalanced);
    }
}