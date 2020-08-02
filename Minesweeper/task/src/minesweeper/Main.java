package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isAsking = true;
        int fieldSize = 9;
        int numberOfMines = 0;

        while (isAsking) {
            System.out.print("How many mines do you want on the field? ");
            try {
                numberOfMines = Integer.parseInt(scanner.nextLine());

                if (numberOfMines < fieldSize * fieldSize) {
                    isAsking = false;
                } else {
                    System.out.println("Number of mines should be equal or less than the size of the field.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Number of mines should have INTEGER format.");
            }
        }

        MineSweeperGame mineSweeperGame = new MineSweeperGame(fieldSize, numberOfMines);
        mineSweeperGame.showGameField();

        while (true) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            try {
                String[] move = scanner.nextLine().split(" ");
                if (move.length == 3) {
                    int coordinateX = Integer.parseInt(move[0]);
                    int coordinateY = Integer.parseInt(move[1]);

                    if (coordinateX > 0 && coordinateX <= fieldSize && coordinateY > 0 && coordinateY <= fieldSize) {
                        if ("mine".equals(move[2])) {
                            if (mineSweeperGame.setMineFlag(coordinateX, coordinateY)) {
                                mineSweeperGame.showGameField();
                            }

                        } else if ("free".equals(move[2])) {
                            if (mineSweeperGame.setFreeCellFlag(coordinateX, coordinateY)) {
                                mineSweeperGame.showGameField();
                            }
                        }

                        if (mineSweeperGame.isGameFinished()) {
                            System.out.println("Congratulations! You found all mines!");
                            System.exit(0);
                        }

                    } else {
                        System.out.println("Coordinates should be fit in the size of the field.");
                    }
                } else {
                    System.out.println("User's move should have [coordinateX coordinateY mine OR free] format");
                }
            } catch (NumberFormatException e) {
                System.out.println("Coordinates should have INTEGER format.");
            }
        }
    }
}
