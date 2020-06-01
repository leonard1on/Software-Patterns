package Builder;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BienesRaices {

  /**
   * @param args the command line arguments
   */
  static Scanner sc = new Scanner(System.in);
  static Random r = new Random();

  public static void main(String[] args) {
    ArrayList<Casa> casas = new ArrayList<>();
    CasaDirector director = new CasaDirector();
    CasaBasicaBuilder builder = new CasaBasicaBuilder();
    char answer;
    int num;
    do {
      num = r.nextInt(5);
      switch (num) {
        case 0:
          director.crearBasica(builder);
          break;
        case 1:
          director.crearLujosa(builder);
          break;
        case 2:
          director.crearChoza(builder);
          break;
        case 3:
          director.crearCasita(builder);
          break;
        case 4:
          director.crearRandom(builder);
          break;
        default:
          System.out.println("La casa se destruyo. Precio = $5");
          break;
      }
      System.out.println("Le puedo ofrecer esta casa. Que le parece? y/n");
      System.out.println(builder.getCasa());
      answer = sc.next().charAt(0);
    } while (answer != 'y' && answer != 'Y');
    System.out.println("Ha comprado una nueva casa! Tiene una deuda de " + builder.getCasa().precio);
    if (builder.getCasa().piscina) {
      System.out.println("Disfrutas de tu nueva piscina.");
    }
  }

  static class CasaDirector {

    public void crearBasica(CasaBuilder casa) {
      casa.addParedes(4);
      casa.addVentanas(2);
      casa.addPuertas(2);
      casa.addCuartos(4);
      casa.addColor("Azul Celeste");
      casa.addPisos(1);
      casa.addTecho(true);
      casa.addJardin(false);
      casa.addGaraje(true);
      casa.addPiscina(false);
      casa.addPorch(false);
      casa.addPrecio(285000);
      casa.getCasa();
    }

    public void crearLujosa(CasaBuilder casa) {
      casa.addParedes(8);
      casa.addVentanas(10);
      casa.addPuertas(4);
      casa.addCuartos(6);
      casa.addColor("Blanca");
      casa.addPisos(2);
      casa.addTecho(true);
      casa.addJardin(true);
      casa.addGaraje(true);
      casa.addPiscina(true);
      casa.addPorch(true);
      casa.addPrecio(100000000);
      casa.getCasa();
    }

    public void crearChoza(CasaBuilder casa) {
      casa.addParedes(3);
      casa.addVentanas(1);
      casa.addPuertas(1);
      casa.addCuartos(1);
      casa.addColor("Marron");
      casa.addPisos(1);
      casa.addTecho(true);
      casa.addJardin(true);
      casa.addGaraje(false);
      casa.addPiscina(false);
      casa.addPorch(false);
      casa.addPrecio(5000);
      casa.getCasa();
    }

    public void crearCasita(CasaBuilder casa) {
      casa.addParedes(4);
      casa.addVentanas(2);
      casa.addPuertas(1);
      casa.addCuartos(2);
      casa.addColor("Amarillo");
      casa.addPisos(1);
      casa.addTecho(true);
      casa.addJardin(false);
      casa.addGaraje(false);
      casa.addPiscina(false);
      casa.addPorch(true);
      casa.addPrecio(200000);
      casa.getCasa();
    }

    public void crearRandom(CasaBuilder casa) {
      Random r = new Random();
      casa.addParedes(r.nextInt(5) + 2);
      casa.addVentanas(r.nextInt(3) + 2);
      casa.addPuertas(r.nextInt(3) + 1);
      casa.addCuartos(r.nextInt(6) + 2);
      casa.addColor("Amarillo");
      casa.addPisos(r.nextInt(3) + 1);
      casa.addTecho(true);
      casa.addJardin(true);
      casa.addGaraje(false);
      casa.addPiscina(true);
      casa.addPorch(true);
      casa.addPrecio(r.nextInt(300000) + 200000);
      casa.getCasa();
    }
  }

  static interface CasaBuilder {
    public void addParedes(int paredes);

    public void addVentanas(int ventanas);

    public void addPuertas(int puertas);

    public void addCuartos(int cuartos);

    public void addColor(String color);

    public void addPisos(int pisos);

    public void addTecho(boolean techo);

    public void addJardin(boolean jardin);

    public void addGaraje(boolean garaje);

    public void addPiscina(boolean piscina);

    public void addPorch(boolean porch);

    public void addPrecio(double precio);

    public Casa getCasa();
  }

  static class CasaBasicaBuilder implements CasaBuilder {
    private Casa casa = new Casa();

    public void empty() {
      casa = new Casa();
    }

    @Override
    public void addParedes(int paredes) {
      casa.paredes = paredes;
    }

    @Override
    public void addVentanas(int ventanas) {
      casa.ventanas = ventanas;
    }

    @Override
    public void addPuertas(int puertas) {
      casa.puertas = puertas;
    }

    @Override
    public void addCuartos(int cuartos) {
      casa.cuartos = cuartos;
    }

    @Override
    public void addColor(String color) {
      casa.color = color;
    }

    @Override
    public void addPisos(int pisos) {
      casa.pisos = pisos;
    }

    @Override
    public void addTecho(boolean techo) {
      casa.techo = techo;
    }

    @Override
    public void addJardin(boolean jardin) {
      casa.jardin = jardin;
    }

    @Override
    public void addGaraje(boolean garaje) {
      casa.garaje = garaje;
    }

    @Override
    public void addPiscina(boolean piscina) {
      casa.piscina = piscina;
    }

    @Override
    public void addPorch(boolean porch) {
      casa.porch = porch;
    }

    @Override
    public void addPrecio(double precio) {
      casa.precio = precio;
    }

    @Override
    public Casa getCasa() {
      return casa;
    }

  }

  static class Casa {
    public int paredes;
    public int ventanas;
    public int puertas;
    public int cuartos;
    public String color;
    public int pisos;
    public boolean techo;
    public boolean jardin;
    public boolean garaje;
    public boolean piscina;
    public boolean porch;
    public double precio;

    @Override
    public String toString() {
      return "Casa contiene:\n" + "paredes: " + paredes + ", ventanas:" + ventanas + ", \npuertas: " + puertas
          + ", cuartos:" + cuartos + ", \nes color: " + color + ", pisos:" + pisos + ", \ntiene techo: " + techo
          + ", tiene jardin: " + jardin + ", \ntiene garaje: " + garaje + ", tiene piscina: " + piscina
          + ", \ntiene porch: " + porch + ", precio: $" + precio;
    }

  }
}
