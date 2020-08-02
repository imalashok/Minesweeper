package minesweeper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class MineSweeperGame {
    final private int fieldSize;
    final private int numberOfMines;
    private final int countOfHiddenCells;
    private ArrayList<String> processedCells = new ArrayList<>();
    private char[][] gameField;
    private char[][] gameFieldWithCalculatedMines;
    private int countOfFlags = 0;

    public MineSweeperGame(int fieldSize, int numberOfMines) {
        this.fieldSize = fieldSize;
        this.countOfHiddenCells = fieldSize * fieldSize;
        this.numberOfMines = numberOfMines;
        this.gameField = new char[fieldSize][fieldSize];
        gameFieldWithCalculatedMines = new char[fieldSize][fieldSize];

        makeNewGameField();
        placeMinesOnGameField();
        checkNumberOfMinesNearEachCell();
        makeNewGameField();
    }

    public void makeNewGameField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                gameField[i][j] = '.';
            }
        }
    }

    public void placeMinesOnGameField() {
        Random random = new Random();
        int countOfPlacedMines = 0;

        while (countOfPlacedMines != numberOfMines) {
            int fieldRow = random.nextInt(fieldSize);
            int fieldColumn = random.nextInt(fieldSize);

            if (gameField[fieldRow][fieldColumn] != 'X') {
                gameField[fieldRow][fieldColumn] = 'X';
                countOfPlacedMines++;
            }
        }


    }

    public void showGameField() {

        System.out.println("\n │123456789│\n—│—————————│");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("—│—————————│");


        /*
        //shows revealed cells on the game field:

        System.out.println();
        System.out.println();

        System.out.println(" │123456789│\n—│—————————│");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(gameFieldWithCalculatedMines[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("—│—————————│");

         */

    }

    public void showGameFieldWithoutMines() {

        System.out.println(" │123456789│\n—│—————————│");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {
                if (gameField[i][j] == 'X') {
                    System.out.print('.');
                } else {
                    System.out.print(gameField[i][j]);
                }
            }
            System.out.println("|");
        }
        System.out.println("—│—————————│");
    }

    public void checkNumberOfMinesNearEachCell() {

        char[][] temporaryField = new char[fieldSize + 2][fieldSize + 2]; //extend original field size by 2 to check edge values;

        for (int i = 0; i < fieldSize; i++) {
            System.arraycopy(gameField[i], 0, temporaryField[i + 1], 1, fieldSize);
        }

        for (int i = 0; i < fieldSize; i++) {
            temporaryField[0][i + 1] = '.'; //extend N side and populate empty values
            temporaryField[fieldSize + 1][i + 1] = '.'; //extend S side and populate empty values
            temporaryField[i + 1][0] = '.'; //extend W side and populate empty values
            temporaryField[i + 1][fieldSize + 1] = '.'; //extend E side and populate empty values
        }

        // set corners with empty values
        temporaryField[0][0] = '.';
        temporaryField[0][fieldSize + 1] = '.';
        temporaryField[fieldSize + 1][0] = '.';
        temporaryField[fieldSize + 1][fieldSize + 1] = '.';


        for (int i = 1; i < fieldSize + 1; i++) {
            for (int j = 1; j < fieldSize + 1; j++) {
                int countOfNeighbourMines = 0;
                if (temporaryField[i][j - 1] == 'X') {
                    countOfNeighbourMines++;
                }
                if (temporaryField[i][j + 1] == 'X') {
                    countOfNeighbourMines++;
                }
                for (int k = 0; k < 3; k++) {
                    if (temporaryField[i - 1][j - 1 + k] == 'X') {
                        countOfNeighbourMines++;
                    }
                    if (temporaryField[i + 1][j - 1 + k] == 'X') {
                        countOfNeighbourMines++;
                    }
                }
                if (temporaryField[i][j] != 'X') {
                    if (countOfNeighbourMines > 0) {
                        temporaryField[i][j] = (char) ('0' + countOfNeighbourMines);
                    }
                }
            }
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                gameFieldWithCalculatedMines[i][j] = temporaryField[i + 1][j + 1];
                //gameField[i][j] = temporaryField[i + 1][j + 1];

            }
        }
    }

    public void revealAllEmptyCellsNearby(int coordinateX, int coordinateY) {

        String stringCoordinate = "" + coordinateX + " " + coordinateY;
        Queue<String> emptyCellsQueue = new ArrayDeque<>();
        emptyCellsQueue.offer(stringCoordinate);
        processedCells.add(stringCoordinate);
        int coordX;
        int coordY;
        int x0 = 0;
        int x1 = 0;

        while (!emptyCellsQueue.isEmpty()) {
            String cell = emptyCellsQueue.poll();

            String[] coordinates = cell.split(" ");
            coordX = Integer.parseInt(coordinates[0]);
            coordY = Integer.parseInt(coordinates[1]);

            if (coordX == 0) {
                x0 = 1;
            }

            if (coordX == fieldSize - 1) {
                x1 = 1;
            }

            for (int i = x0; i < 3 - x1; i++) {

                if (coordY != 0) {

                    stringCoordinate = "" + (coordX - 1 + i) + " " + (coordY - 1);

                    if (gameFieldWithCalculatedMines[coordY - 1][coordX - 1 + i] == '.') {
                        gameField[coordY - 1][coordX - 1 + i] = '/';

                        if (!processedCells.contains(stringCoordinate)) {
                            emptyCellsQueue.add(stringCoordinate);
                            processedCells.add(stringCoordinate);
                        }
                    } else if (!processedCells.contains(stringCoordinate) && gameFieldWithCalculatedMines[coordY - 1][coordX - 1 + i] > '0' && gameFieldWithCalculatedMines[coordY - 1][coordX - 1 + i] < '9') {
                        gameField[coordY - 1][coordX - 1 + i] = gameFieldWithCalculatedMines[coordY - 1][coordX - 1 + i];
                        processedCells.add(stringCoordinate);
                    }
                }

                if (coordY != fieldSize - 1) {

                    stringCoordinate = "" + (coordX - 1 + i) + " " + (coordY + 1);

                    if (gameFieldWithCalculatedMines[coordY + 1][coordX - 1 + i] == '.') {
                        gameField[coordY + 1][coordX - 1 + i] = '/';

                        if (!processedCells.contains(stringCoordinate)) {
                            emptyCellsQueue.add(stringCoordinate);
                            processedCells.add(stringCoordinate);
                        }
                    } else if (!processedCells.contains(stringCoordinate) && gameFieldWithCalculatedMines[coordY + 1][coordX - 1 + i] > '0' && gameFieldWithCalculatedMines[coordY + 1][coordX - 1 + i] < '9') {
                        gameField[coordY + 1][coordX - 1 + i] = gameFieldWithCalculatedMines[coordY + 1][coordX - 1 + i];
                        processedCells.add(stringCoordinate);
                    }
                }
            }

            stringCoordinate = "" + (coordX - 1) + " " + (coordY);

            if (coordX != 0) {
                if (gameFieldWithCalculatedMines[coordY][coordX - 1] == '.') {
                    gameField[coordY][coordX - 1] = '/';

                    if (!processedCells.contains(stringCoordinate)) {
                        emptyCellsQueue.add(stringCoordinate);
                        processedCells.add(stringCoordinate);
                    }
                } else if (!processedCells.contains(stringCoordinate) && gameFieldWithCalculatedMines[coordY][coordX - 1] > '0' && gameFieldWithCalculatedMines[coordY][coordX - 1] < '9') {
                    gameField[coordY][coordX - 1] = gameFieldWithCalculatedMines[coordY][coordX - 1];
                    processedCells.add(stringCoordinate);
                }
            }

            stringCoordinate = "" + (coordX + 1) + " " + (coordY);

            if (coordX != fieldSize - 1) {
                if (gameFieldWithCalculatedMines[coordY][coordX + 1] == '.') {
                    gameField[coordY][coordX + 1] = '/';

                    if (!processedCells.contains(stringCoordinate)) {
                        emptyCellsQueue.add(stringCoordinate);
                        processedCells.add(stringCoordinate);
                    }
                } else if (!processedCells.contains(stringCoordinate) && gameFieldWithCalculatedMines[coordY][coordX + 1] > '0' && gameFieldWithCalculatedMines[coordY][coordX + 1] < '9') {
                    gameField[coordY][coordX + 1] = gameFieldWithCalculatedMines[coordY][coordX + 1];
                    processedCells.add(stringCoordinate);
                }
            }
            x0 = 0;
            x1 = 0;
        }
    }

    public boolean setMineFlag(int coordinateX, int coordinateY) {
        if (gameField[coordinateY - 1][coordinateX - 1] > '0' && gameField[coordinateY - 1][coordinateX - 1] < '9') {
            System.out.println("There is a number here!");
            return false;
        } else if (gameField[coordinateY - 1][coordinateX - 1] == '/') {
            return true;
        } else if (gameField[coordinateY - 1][coordinateX - 1] == '*') {
            gameField[coordinateY - 1][coordinateX - 1] = '.';
            countOfFlags--;
        } else {
            gameField[coordinateY - 1][coordinateX - 1] = '*';
            countOfFlags++;
        }
        return true;
    }

    public boolean setFreeCellFlag(int coordinateX, int coordinateY) {

        String coordinateString = "" + (coordinateX - 1) + " " + (coordinateY - 1);

        /*make sure that the first step cannot be placed in the cell with mine
        tempField is used to store '*' flags that were set on the initial field, and to restore flags on the new field
        if the first step reveals the cell with the mine.
        */
        while (processedCells.isEmpty() && gameFieldWithCalculatedMines[coordinateY - 1][coordinateX - 1] == 'X') {
            char[][] tempField = new char[fieldSize][fieldSize];
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    if (gameField[i][j] == '*') {
                        tempField[i][j] = '*';
                    }
                }
            }

            placeMinesOnGameField();
            checkNumberOfMinesNearEachCell();
            makeNewGameField();

            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    if (tempField[i][j] == '*') {
                        gameField[i][j] = '*';
                    }
                }
            }

        }

        if (!processedCells.contains(coordinateString)) {
            if (gameField[coordinateY - 1][coordinateX - 1] > '0' && gameField[coordinateY - 1][coordinateX - 1] < '9') {
                System.out.println("There is a number here!");
                return false;
            } else if (gameFieldWithCalculatedMines[coordinateY - 1][coordinateX - 1] == '.') {
                gameField[coordinateY - 1][coordinateX - 1] = '/';
                revealAllEmptyCellsNearby(coordinateX - 1, coordinateY - 1);
            } else {
                gameField[coordinateY - 1][coordinateX - 1] = gameFieldWithCalculatedMines[coordinateY - 1][coordinateX - 1];
                processedCells.add(coordinateString);

                if (gameField[coordinateY - 1][coordinateX - 1] == 'X') {
                    showGameField();
                    System.out.println("You stepped on a mine and failed!");
                    System.exit(0);
                }
            }
        }
        return true;
    }

    public boolean isGameFinished() {
        int count = 0;

        if (countOfFlags == numberOfMines) {
            for (int i = 0; i < fieldSize; i++) {
                for (int j = 0; j < fieldSize; j++) {
                    if (gameField[i][j] == '*') {
                        if (gameFieldWithCalculatedMines[i][j] == 'X') {
                            count++;
                        }
                    }
                }
            }

            return count == numberOfMines;

        } else return countOfHiddenCells - processedCells.size() == numberOfMines;
    }
}