package com.geekbrains;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Krestiki {
    private final static int SIZE = 3;
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
        //метод переписан на циклы
        boolean diagonalToRight = true;
        boolean diagonalToLeft = true;
        for (int i = 0; i < SIZE; i++) {
            if (MAP[i][i] != symbol) {
                diagonalToRight = false;
                break;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (MAP[i][2 - i] != symbol) {
                diagonalToLeft = false;
                break;
            }
        }
        if (diagonalToRight || diagonalToLeft)
        { return true;
        } else {
            for (int j = 0; j < SIZE; j++) {
                if (MAP[j][0] == symbol && MAP[j][1] == symbol && MAP[j][2] == symbol) {
                    return true;
                } else if (MAP[0][j] == symbol && MAP[1][j] == symbol && MAP[2][j] == symbol) {
                    return true;
                }
            }
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
        int x;
        int y;
        // комп рандомит здесь, но чуть эффективнее, чем в исходном коде, блокировка ходов юзера осуществлена в классе Noliki
        ArrayList<String> emptyDots = new ArrayList<>();
        for (int i = 0; i < SIZE; i++){
            for (int a = 0; a < SIZE; a++){
                if (MAP[i][a] == EMPTYDOT){
                    String emptyDotsString = i + String.valueOf(a);
                    emptyDots.add(emptyDotsString);
                }
            }
        }
        String randomIndexes = emptyDots.get(RANDOM.nextInt(emptyDots.size()));
        x = Integer.parseInt(randomIndexes.substring(1));
        y = Integer.parseInt(randomIndexes.substring(0,1));
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

