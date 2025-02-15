package com.mycompany.pruebaed1;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Proyecto1 {

    public static void main(String[] args) {

        String[][] tablero = new String[3][3];
        ArrayList<ArrayList<Integer>> espaciosVacios = new ArrayList<>();
        ArrayList<ArrayList<Integer>> marcasDelRival = new ArrayList<>();
        ArrayList<ArrayList<Integer>> marcasPropias = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadasChallenger = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadasRival = new ArrayList<>();
        String challenger = JOptionPane.showInputDialog("1. X\n2. O");
        String rival;

        challenger = challenger.equals("1") ? "X" : "O";
        rival = challenger.equals("X") ? "O" : "X";

        int number = 1;

        System.out.println("\n\nmovimiento " + number++);
        smartMove(tablero, challenger);
        visualizacionRecursiva(tablero, espaciosVacios, 0, 0);
        revisionMarcasRecursivo(tablero, marcasPropias, 0, 0, challenger);
        revisionMarcasRecursivo(tablero, marcasDelRival, 0, 0, rival);
        posiblesJugadasRecursivo(tablero, marcasPropias, posiblesJugadasChallenger, 0, challenger);
        posiblesJugadasRecursivo(tablero, marcasDelRival, posiblesJugadasRival, 0, rival);

        System.out.println("\n\nmovimiento " + number++);
        randomMove(tablero, espaciosVacios, rival);
        visualizacionRecursiva(tablero, espaciosVacios, 0, 0);
        revisionMarcasRecursivo(tablero, marcasPropias, 0, 0, challenger);
        revisionMarcasRecursivo(tablero, marcasDelRival, 0, 0, rival);
        posiblesJugadasRecursivo(tablero, marcasPropias, posiblesJugadasChallenger, 0, challenger);
        posiblesJugadasRecursivo(tablero, marcasDelRival, posiblesJugadasRival, 0, rival);

        System.out.println("\n\nmovimiento " + number++);
        smartMove(tablero, challenger);
        visualizacionRecursiva(tablero, espaciosVacios, 0, 0);
        revisionMarcasRecursivo(tablero, marcasPropias, 0, 0, challenger);
        revisionMarcasRecursivo(tablero, marcasDelRival, 0, 0, rival);
        posiblesJugadasRecursivo(tablero, marcasPropias, posiblesJugadasChallenger, 0, challenger);
        posiblesJugadasRecursivo(tablero, marcasDelRival, posiblesJugadasRival, 0, rival);

        boolean salir = false; //Se utilizara para determinar si hay empate, vistoria o derrota

        while (!espaciosVacios.isEmpty()) {

        System.out.println("\n\nmovimiento " + number++);
        randomMove(tablero, espaciosVacios, rival);
        visualizacionRecursiva(tablero, espaciosVacios, 0, 0);
        revisionMarcasRecursivo(tablero, marcasPropias, 0, 0, challenger);
        revisionMarcasRecursivo(tablero, marcasDelRival, 0, 0, rival);
        posiblesJugadasRecursivo(tablero, marcasPropias, posiblesJugadasChallenger, 0, challenger);
        posiblesJugadasRecursivo(tablero, marcasDelRival, posiblesJugadasRival, 0, rival);

            System.out.println("\n\nmovimiento " + number++);
            verificarMejorJugada(tablero, posiblesJugadasChallenger, challenger, posiblesJugadasRival, rival, salir);
            visualizacionRecursiva(tablero, espaciosVacios, 0, 0);
            revisionMarcasRecursivo(tablero, marcasPropias, 0, 0, challenger);
            revisionMarcasRecursivo(tablero, marcasDelRival, 0, 0, rival);
            posiblesJugadasRecursivo(tablero, marcasPropias, posiblesJugadasChallenger, 0, challenger);
            posiblesJugadasRecursivo(tablero, marcasDelRival, posiblesJugadasRival, 0, rival);
        }

    }

    public static void verificarMejorJugada(String[][] tablero, ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadasChallenger, String challenger, ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadasRival, String rival, boolean salir) {
        //Hace falta verificar las diagonales
        
        boolean ataque = siguienteMoviento(tablero, posiblesJugadasChallenger, challenger, challenger);
        if (!ataque) {
            boolean defensa = siguienteMoviento(tablero, posiblesJugadasRival, rival, challenger);
            if (!defensa) {
                while (true) {
                    Random random = new Random();
                    int lineaRandom = random.nextInt(posiblesJugadasChallenger.size());
                    ArrayList<ArrayList<Integer>> lineasDisponibles = posiblesJugadasChallenger.get(lineaRandom);
                    int espacioRandom = random.nextInt(lineasDisponibles.size());
                    int row = posiblesJugadasChallenger.get(lineaRandom).get(espacioRandom).get(0);
                    int column = posiblesJugadasChallenger.get(lineaRandom).get(espacioRandom).get(1);

                    if (tablero[row][column] == null) {
                        tablero[row][column] = challenger;
                        break;
                    }
                }

            }

        } else {
            System.out.println("\n\n!!WINNER -> " + challenger + " <- WINNER!!\n\n");
            salir = true;
        }
    }

    public static boolean siguienteMoviento(String[][] tablero, ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadas, String gamerOne, String gamerTwo) {
        for (int i = 0; i < posiblesJugadas.size(); i++) {
            ArrayList<ArrayList<Integer>> movimiento = new ArrayList<>();
            ArrayList<ArrayList<Integer>> pendiente = new ArrayList<>();
            int picks = 0;

            for (int j = 0; j < posiblesJugadas.get(i).size(); j++) {
                ArrayList<Integer> linea = new ArrayList<>();
                ArrayList<Integer> campoLibre = new ArrayList<>();

                int row = posiblesJugadas.get(i).get(j).get(0);
                int column = posiblesJugadas.get(i).get(j).get(1);

                if (tablero[row][column] == null) {
                    campoLibre.add(row);
                    campoLibre.add(column);
                    pendiente.add(campoLibre);
                }

                if (tablero[row][column] != null && tablero[row][column].contains(gamerOne)) {
                    picks++;
                    linea.add(row);
                    linea.add(column);
                    movimiento.add(linea);
                }
            }

            switch (picks) {

                case 2 -> {
                    System.out.println("\nAtencion!!");
                    System.out.println("Espacios marcados con " + gamerOne + ": " + movimiento);
                    System.out.println("Espacio vacio: " + pendiente);
                    jugadaFinal(tablero, pendiente.get(0), gamerTwo);
                    return true;

                }

            }

        }
        return false;
    }

    public static void jugadaFinal(String[][] tablero, ArrayList<Integer> ubicacionPendiente, String mark) {
        tablero[ubicacionPendiente.get(0)][ubicacionPendiente.get(1)] = mark;
    }

    public static void posiblesJugadasRecursivo(String[][] tablero, ArrayList<ArrayList<Integer>> marcas, ArrayList<ArrayList<ArrayList<Integer>>> posiblesJugadas, int i, String gamer) {

        if (!posiblesJugadas.isEmpty() && i == 0) {
            posiblesJugadas.clear();
        }

        if (i >= marcas.size()) {
            System.out.println("Posibles jugadas para la victoria " + gamer + ": " + posiblesJugadas);
            return;
        }

        ArrayList<ArrayList<Integer>> posiblesJugadasH = new ArrayList<>();
        ArrayList<ArrayList<Integer>> posiblesJugadasV = new ArrayList<>();
        int horizontal = marcas.get(i).get(0);
        int vertical = marcas.get(i).get(1);

        posiblesJugadasRecursivoAuxH(tablero, horizontal, posiblesJugadasH, 0, gamer);
        posiblesJugadasRecursivoAuxV(tablero, vertical, posiblesJugadasV, 0, gamer);

        if (posiblesJugadasH.size() == 3 && !posiblesJugadas.contains(posiblesJugadasH)) {
            posiblesJugadas.add(posiblesJugadasH);
        }
        if (posiblesJugadasV.size() == 3 && !posiblesJugadas.contains(posiblesJugadasV)) {
            posiblesJugadas.add(posiblesJugadasV);
        }

        posiblesJugadasRecursivo(tablero, marcas, posiblesJugadas, i + 1, gamer);
    }

    private static void posiblesJugadasRecursivoAuxH(String[][] tablero, int pos, ArrayList<ArrayList<Integer>> jugadas, int j, String gamer) {

        if (j >= tablero.length) {
            return;
        }

        if (tablero[pos][j] == null || tablero[pos][j].equals(gamer)) {
            ArrayList<Integer> jugadaH = new ArrayList<>();
            jugadaH.add(pos);
            jugadaH.add(j);

            jugadas.add(jugadaH);
        }

        posiblesJugadasRecursivoAuxH(tablero, pos, jugadas, j + 1, gamer);
    }

    private static void posiblesJugadasRecursivoAuxV(String[][] tablero, int pos, ArrayList<ArrayList<Integer>> jugadas, int j, String gamer) {
        if (j >= tablero.length) {
            return;
        }

        if (tablero[j][pos] == null || tablero[j][pos].equals(gamer)) {
            ArrayList<Integer> jugadaV = new ArrayList<>();
            jugadaV.add(j);
            jugadaV.add(pos);

            jugadas.add(jugadaV);
        }

        posiblesJugadasRecursivoAuxV(tablero, pos, jugadas, j + 1, gamer);
    }

    public static void revisionMarcasRecursivo(String[][] tablero, ArrayList<ArrayList<Integer>> marcas, int i, int j, String gamer) {

        if (!marcas.isEmpty() && i == 0 && j == 0) {
            marcas.clear();
        }

        if (i >= tablero.length) {
            System.out.println("Marcas " + gamer + ": " + marcas);
            return;
        }
        if (j >= tablero[i].length) {
            revisionMarcasRecursivo(tablero, marcas, i + 1, 0, gamer);
            return;
        }

        if (gamer.equals(tablero[i][j])) {
            ArrayList<Integer> marca = new ArrayList<>();
            marca.add(i);
            marca.add(j);
            marcas.add(marca);
        }

        revisionMarcasRecursivo(tablero, marcas, i, j + 1, gamer);
    }

    public static void visualizacionRecursiva(String[][] tablero, ArrayList<ArrayList<Integer>> espaciosVacios, int i, int j) {

        if (!espaciosVacios.isEmpty() && i == 0 && j == 0) {
            espaciosVacios.clear();
        }

        if (i >= tablero.length) {
            System.out.println("Espacios vacios: " + espaciosVacios);
            return;
        }
        if (j >= tablero[i].length) {
            System.err.println("\n");
            visualizacionRecursiva(tablero, espaciosVacios, i + 1, 0);
            return;
        }

        System.out.print((tablero[i][j] == null ? "-" : tablero[i][j]) + "    ");
        if (tablero[i][j] == null) {
            ArrayList<Integer> empthySpots = new ArrayList<>();
            empthySpots.add(i);
            empthySpots.add(j);
            if (!espaciosVacios.contains(empthySpots)) {
                espaciosVacios.add(empthySpots);
            }
        }

        visualizacionRecursiva(tablero, espaciosVacios, i, j + 1);
    }

    public static void randomMove(String[][] tablero, ArrayList<ArrayList<Integer>> espaciosVacios, String rival) {
        Random random = new Random();
        int ram = random.nextInt(espaciosVacios.size());

        ArrayList<Integer> pick = espaciosVacios.get(ram);
        tablero[pick.get(0)][pick.get(1)] = rival;
        espaciosVacios.remove(pick);
    }

    public static void smartMove(String[][] tablero, String challenger) {
        Random random = new Random();

        switch (random.nextInt(5)) {
            case 0 -> {
                if (tablero[0][0] == null) {
                    tablero[0][0] = challenger;

                } else {
                    smartMove(tablero, challenger);
                }
            }
            case 1 -> {
                if (tablero[2][0] == null) {
                    tablero[2][0] = challenger;
                } else {
                    smartMove(tablero, challenger);
                }
            }
            case 2 -> {
                if (tablero[0][2] == null) {
                    tablero[0][2] = challenger;
                } else {
                    smartMove(tablero, challenger);
                }
            }
            case 3 -> {
                if (tablero[2][2] == null) {
                    tablero[2][2] = challenger;
                } else {
                    smartMove(tablero, challenger);
                }
            }
            case 4 -> {
                if (tablero[1][1] == null) {
                    tablero[1][1] = challenger;
                } else {
                    smartMove(tablero, challenger);
                }
            }
        }
    }
}
