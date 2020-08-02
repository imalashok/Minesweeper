import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = Integer.parseInt(scanner.nextLine());

        Deque<Integer> stackAllElements = new ArrayDeque<>();
        Deque<Integer> stackMaxElements = new ArrayDeque<>();

        for (int i = 0; i < size; i++) {
            String com = scanner.nextLine();
            if (com.startsWith("push")) {
                String[] s = com.split(" ");
                int number = Integer.parseInt(s[1]);
                stackAllElements.offer(number);
                if (stackMaxElements.isEmpty()) {
                    stackMaxElements.offer(number);
                } else {
                    stackMaxElements.offer(number > stackMaxElements.peekLast() ? number : stackMaxElements.peekLast());
                }
            } else if ("pop".equals(com)) {
                stackAllElements.pollLast();
                stackMaxElements.pollLast();
            } else if ("max".equals(com)) {
                System.out.println(stackMaxElements.peekLast());
            }
        }
    }
}