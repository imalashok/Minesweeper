import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Deque<Integer> deq = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            if (number % 2 == 0) {
                deq.offerFirst(number);
            } else {
                deq.offerLast(number);
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(deq.poll());
        }
    }
}