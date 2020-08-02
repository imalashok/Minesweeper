import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfJobs = Integer.parseInt(scanner.nextLine());

        Queue<String> queue1 = new ArrayDeque<>();
        Queue<String> queue2 = new ArrayDeque<>();
        int queueLoad1 = 0;
        int queueLoad2 = 0;

        for (int i = 0; i < numberOfJobs; i++) {
            String[] job = scanner.nextLine().split(" ");

            if (queueLoad1 <= queueLoad2) {
                queueLoad1 += Integer.parseInt(job[1]);
                queue1.offer(job[0]);
            } else {
                queueLoad2 += Integer.parseInt(job[1]);
                queue2.offer(job[0]);
            }
        }

        while (!queue1.isEmpty()) {
            System.out.print(queue1.poll() + " ");
        }

        System.out.println();

        while (!queue2.isEmpty()) {
            System.out.print(queue2.poll() + " ");
        }
    }
}