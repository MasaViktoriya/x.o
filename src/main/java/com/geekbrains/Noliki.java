package com.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Noliki {
    private final static int SIZE = 5;
    private final static char EMPTYDOT = '.';
    private final static char XDOT = 'x';
    private final static char ODOT = 'o';
    private static char[][] MAP;
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Random RANDOM = new Random();


    public static void main(String[] args) {
        createMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(XDOT)){
                System.out.println("Былинная победа! Комп повержен.");
                break;
            }
            if (fullMap()){
                System.out.println("Победила дружба: все в обломе.");
                break;
            }
            compTurn();
            printMap();
            if (checkWin(ODOT)){
                System.out.println("Этот комп возглавит восстание машин. А ты проиграл.");
                break;
            }
            if (fullMap()){
                System.out.println("Победила дружба: все в обломе.");
                break;
            }
        }
        System.out.println("GAMEOVER");
    }

    private static boolean checkWin(char symbol) {
//проверка строк и столбцов
        Character[] full = {EMPTYDOT, symbol, symbol, symbol, symbol};
        Character[] full2 = {symbol, symbol, symbol, symbol, EMPTYDOT};
        Character[] full3 = {symbol, symbol, symbol, symbol, ODOT};
        Character[] full4 = {ODOT, symbol, symbol, symbol, symbol};
        Character[] full5 = {symbol, symbol, symbol, symbol, XDOT};
        Character[] full6 = {XDOT, symbol, symbol, symbol, symbol};
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Character> checkRow = new ArrayList<>();
            ArrayList<Character> checkColumn = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                checkRow.add(MAP[i][j]);
                checkColumn.add(MAP[j][i]);
            }
            Character[] row = checkRow.toArray(new Character[0]);
            if (Arrays.equals(row, full) || Arrays.equals(row, full2) || Arrays.equals(row, full3) || Arrays.equals(row, full4) || Arrays.equals(row, full5) || Arrays.equals(row, full6)) {
                return true;
            }
            Character[] column = checkColumn.toArray(new Character[0]);
            if (Arrays.equals(column, full) || Arrays.equals(column, full2) || Arrays.equals(column, full3) || Arrays.equals(column, full4) || Arrays.equals(column, full5) || Arrays.equals(column, full6)) {
                return true;
            }
        }

// проверка наибольших диагоналей
        ArrayList<Character> checkDiagonal = new ArrayList<>();
        ArrayList<Character> checkReverseDiagonal = new ArrayList<>();
        for (int j = 0; j < SIZE; j++) {
            checkDiagonal.add(MAP[j][j]);
            checkReverseDiagonal.add(MAP[j][4-j]);
        }
        Character[] diagonal = checkDiagonal.toArray(new Character[0]);
        if (Arrays.equals(diagonal, full) || Arrays.equals(diagonal, full2) || Arrays.equals(diagonal, full3) || Arrays.equals(diagonal, full4) || Arrays.equals(diagonal, full5) || Arrays.equals(diagonal, full6)) {
            return true;
        }
        Character[] reverseDiagonal = checkReverseDiagonal.toArray(new Character[0]);
        if (Arrays.equals(reverseDiagonal, full) || Arrays.equals(reverseDiagonal, full2) || Arrays.equals(reverseDiagonal, full3) || Arrays.equals(reverseDiagonal, full4) || Arrays.equals(reverseDiagonal, full5) || Arrays.equals(reverseDiagonal, full6)) {
            return true;
        }

//проверка меньших диагоналей
        Character[] fullDiagonal = {symbol, symbol, symbol, symbol};

        ArrayList<Character> checkUpRightDiagonal = new ArrayList<>();
        ArrayList<Character> checkDownRightDiagonal = new ArrayList<>();
        ArrayList<Character> checkDownLeftDiagonal = new ArrayList<>();
        for (int i = 1; i < SIZE; i++) {
            checkUpRightDiagonal.add(MAP[i-1][i]);
            checkDownRightDiagonal.add(MAP[i][i-1]);
            checkDownLeftDiagonal.add(MAP[5-i][i]);
        }
        Character[] upRightDiagonal = checkUpRightDiagonal.toArray(new Character[0]);
        if (Arrays.equals(upRightDiagonal, fullDiagonal)){
            return true;
        }
        Character[] downRightDiagonal = checkDownRightDiagonal.toArray(new Character[0]);
        if (Arrays.equals(downRightDiagonal, fullDiagonal)){
            return true;
        }
        Character[] downLeftDiagonal = checkDownLeftDiagonal.toArray(new Character[0]);
        if (Arrays.equals(downLeftDiagonal, fullDiagonal)){
            return true;
        }

        ArrayList<Character> checkUpLeftDiagonal = new ArrayList<>();
        for (int i = 0; i < SIZE-1; i++) {
            checkUpLeftDiagonal.add(MAP[3-i][i]);
        }
        Character[] upLeftDiagonal = checkUpLeftDiagonal.toArray(new Character[0]);
        if (Arrays.equals(upLeftDiagonal, fullDiagonal)){
            return true;
        }

        return false;
    }


    private static void createMap() {
        MAP = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MAP[i][j] = EMPTYDOT;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(MAP[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean cellValid(int x, int y) {
        if (x < 0 || x > SIZE || y < 0 || y > SIZE) {
            return false;
        }
        if (MAP[y][x] == EMPTYDOT){
            return  true;
        }
        return false;
    }

    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты в формате Х и У");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!cellValid(x, y));
        MAP[y][x] = XDOT;
    }

    private static void compTurn() {
        //стопит всё, кроме диагоналей
        int x = 0;
        int y = 0;
        boolean isTurnReady = false;
        String xxdot = "xx.";
        String dotxx = ".xx";
        String xdotx = "x.x";
        String xxx = "xxx";

        for (int i = 0; i < 3; i++) {
            if (isTurnReady){
                break;
            }
            String checkStringRow = "";
            String checkStringColumn = "";
            for (int j = 0; j < 3; j++) {

                checkStringRow = checkStringRow.concat(Character.toString(MAP[i][j]));
                checkStringColumn = checkStringColumn.concat(Character.toString(MAP[j][i]));
            }
            if (checkStringRow.equals(xxx)){
                if (cellValid(3, i)){
                    x = 3;
                    y = i;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringColumn.equals(xxx)){
                if (cellValid(i, 3)){
                    x = i;
                    y = 3;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringRow.equals(xxdot)) {
                x = 2;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringRow.equals(dotxx)) {
                x = 0;
                y = i;
                isTurnReady = true;
                break;
            }  else if (checkStringRow.equals(xdotx)){
                x = 1;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xxdot)) {
                x = i;
                y = 2;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(dotxx)) {
                x = i;
                y = 0;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xdotx)){
                x = i;
                y = 0;
                isTurnReady = true;
                break;
            }

        }
        for (int i = 2; i < SIZE; i++) {
            if (isTurnReady){
                break;
            }
            String checkStringRow = "";
            String checkStringColumn = "";
            for (int j = 0; j < 3; j++) {
                checkStringRow = checkStringRow.concat(Character.toString(MAP[i][j]));
                checkStringColumn = checkStringColumn.concat(Character.toString(MAP[j][i]));
            }
            if (checkStringRow.equals(xxx)){
                if (cellValid(3, i)){
                    x = 3;
                    y = i;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringColumn.equals(xxx)) {
                if (cellValid(i, 3)) {
                    x = i;
                    y = 3;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringRow.equals(xxdot)) {
                x = 2;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringRow.equals(dotxx)) {
                x = 0;
                y = i;
                isTurnReady = true;
                break;
            }  else if (checkStringRow.equals(xdotx)){
                x = 1;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xxdot)) {
                x = i-2;
                y = 4;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(dotxx)) {
                x = i-2;
                y = 2;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xdotx)){
                x = i;
                y = 3;
                isTurnReady = true;
                break;
            }

        }
        for (int i = 0; i < 3; i++) {
            if (isTurnReady){
                break;
            }
            String checkStringRow = "";
            String checkStringColumn = "";
            for (int j = 2; j < SIZE; j++) {

                checkStringRow = checkStringRow.concat(Character.toString(MAP[i][j]));
                checkStringColumn = checkStringColumn.concat(Character.toString(MAP[j][i]));
            }
            if (checkStringRow.equals(xxx)){
                if (cellValid(1, i)){
                    x = 1;
                    y = i;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringColumn.equals(xxx)) {
                if (cellValid(i, 3)) {
                    x = i;
                    y = 3;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringRow.equals(xxdot)) {
                x = 4;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringRow.equals(dotxx)) {
                x = 2;
                y = i;
                isTurnReady = true;
                break;
            }  else if (checkStringRow.equals(xdotx)){
                x = 3;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xxdot)) {
                x = i;
                y = 4;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(dotxx)) {
                x = i;
                y = 2;
                isTurnReady = true;
                break;
            }  else if (checkStringColumn.equals(xdotx)){
                x = i;
                y = 1;
                isTurnReady = true;
                break;
            }

        }

        for (int i = 2; i < SIZE; i++) {
            if (isTurnReady){
                break;
            }
            String checkStringRow = "";
            String checkStringColumn = "";
            for (int j = 2; j < SIZE; j++) {
                checkStringRow = checkStringRow.concat(Character.toString(MAP[i][j]));
                checkStringColumn = checkStringColumn.concat(Character.toString(MAP[j][i]));
            }
            if (checkStringRow.equals(xxx)){
                if (cellValid(3, i)){
                    x = 1;
                    y = i;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringColumn.equals(xxx)) {
                if (cellValid(i, 3)) {
                    x = i;
                    y = 1;
                    isTurnReady = true;
                    break;
                }
            } else if (checkStringRow.equals(xxdot)) {
                x = 4;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringRow.equals(dotxx)) {
                x = 2;
                y = i;
                isTurnReady = true;
                break;
            }  else if (checkStringRow.equals(xdotx)){
                x = 3;
                y = i;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(xxdot)) {
                x = i;
                y = 4;
                isTurnReady = true;
                break;
            } else if (checkStringColumn.equals(dotxx)) {
                x = i;
                y = 2;
                isTurnReady = true;
                break;
            }  else if (checkStringColumn.equals(xdotx)){
                x = i;
                y = 3;
                isTurnReady = true;
                break;
            }

        }

        if (!isTurnReady) {
            ArrayList<String> emptyDots = new ArrayList<>();
            for (int a = 0; a < SIZE; a++) {
                for (int b = 0; b < SIZE; b++) {
                    if (MAP[a][b] == EMPTYDOT) {
                        String emptyDotsString = a + String.valueOf(b);
                        emptyDots.add(emptyDotsString);
                    }
                }
            }
            String randomIndexes = emptyDots.get(RANDOM.nextInt(emptyDots.size()));
            x = Integer.parseInt(randomIndexes.substring(1));
            y = Integer.parseInt(randomIndexes.substring(0, 1));
        }

        System.out.println("Компьютер сходил в точку " + (x + 1) + "." + (y + 1));
        MAP[y][x] = ODOT;

    }

    private static boolean fullMap(){
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                if (MAP[i][j] == EMPTYDOT){
                    return false;
                }
            }
        }
        return true;
    }
}

