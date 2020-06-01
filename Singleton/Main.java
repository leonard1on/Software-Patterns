
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leona
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Singleton tictactoe = Singleton.getInstance();
        int x;
        int y;
        do {
            System.out.println("Turno de " + tictactoe.getChar() + " //Ingrese -1 para salir");
            System.out.println("Ingrese Posicion x (0-2)");
            x = sc.nextInt();
            System.out.println("Ingrese Posicion y (0-2)");
            y = sc.nextInt();
            if (x != -1 || y != -1) {
                tictactoe.signo(x, y);
            }
        } while (x != -1 || y != -1);
    }

    static class Singleton {

        private static final Singleton instance = new Singleton();
        private final char[][] table;
        private static char symbol = 'x';

        private Singleton() {
            table = new char[3][3];
            clean();
        }

        private void check() {
            print();

            for (char[] cs : table) {
                if (cs[0] == symbol && cs[1] == symbol && cs[2] == symbol) {
                    System.out.println("Ganador: " + symbol + "\nGenerando nuevo tablero");
                    clean();
                }
            }

            for (int i = 0; i < 3; i++) {
                if (table[0][i] == symbol && table[1][i] == symbol && table[2][i] == symbol) {
                    System.out.println("Ganador: " + symbol + "\nGenerando nuevo tablero");
                    clean();
                }
            }

            if (table[0][0] == symbol && table[1][1] == symbol && table[2][2] == symbol) {
                System.out.println("Ganador: " + symbol + "\nGenerando nuevo tablero");
                clean();
            } else if (table[0][2] == symbol && table[1][1] == symbol && table[2][0] == symbol) {
                System.out.println("Ganador: " + symbol + "\nGenerando nuevo tablero");
                clean();
            }

            symbolChange();
        }

        private void symbolChange() {
            if (symbol == 'x') {
                symbol = 'o';
            } else {
                symbol = 'x';
            }
        }

        private void clean() {
            for (char[] cs : table) {
                for (int i = 0; i < 3; i++) {
                    cs[i] = ' ';
                }
            }
        }

        public void signo(int y, int x) {
            x = x % 3;
            y = y % 3;
            if (table[x][y] == 'x' || table[x][y] == 'o') {
                System.out.println("Espacio ya ocupado, intente de nuevo");
            } else {
                table[x][y] = symbol;
                check();
            }
        }

        public static Singleton getInstance() {
            return instance;
        }

        public char getChar() {
            return symbol;
        }

        private void print() {
            for (char[] table1 : table) {
                for (int j = 0; j < 3; j++) {
                    System.out.print("[" + table1[j] + "]");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }
}
