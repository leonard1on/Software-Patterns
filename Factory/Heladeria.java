package Factory;

import java.util.ArrayList;
import java.util.Scanner;

public class Heladeria {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String respuesta;
    MaquinaHelado bonaparte = new MaquinaHelado();
    ArrayList<Helado> lista = new ArrayList<>();
    System.out.println("%%%Bienvenido a la Heladeria Napoleon%%%\n");
    do {
      System.out.println("Que tipo de helado desea ordenar?\n"
          + "1. Fresa----------L.20\n2. Vainilla-------L.17\n3. Chocolate------L.23");
      respuesta = sc.next();
      switch (respuesta) {
        case "1":
          lista.add(bonaparte.getHelado("Fresa"));
          break;
        case "2":
          lista.add(bonaparte.getHelado("Vainilla"));
          break;
        case "3":
          lista.add(bonaparte.getHelado("Chocolate"));
          break;
        default:
          System.out.println("Disculpe me puede repetir su orden?");
          break;
      }
      System.out.println("Ordenara algo mas? s/n");
      respuesta = sc.next();
    } while (!respuesta.equals("n"));
    int precio = 0;
    for (Helado helado : lista) {
      precio += helado.getPrecio();
    }
    System.out.println("Su cuenta es de L." + precio);
  }

  static class MaquinaHelado {
    public Helado getHelado(String tipo) {
      switch (tipo) {
        case "Fresa":
          return new Fresa();
        case "Vainilla":
          return new Vainilla();
        case "Chocolate":
          return new Chocolate();
        default:
          return null;
      }
    }
  }

  static interface Helado {
    public void crear();

    public void comer();

    public int getPrecio();
  }

  static final class Fresa implements Helado {

    public Fresa() {
      crear();
    }

    @Override
    public void crear() {
      System.out.println("Se ha creado un helado sabor Fresa");
    }

    @Override
    public void comer() {
      System.out.println("Era un rico helado sabor Fresa.");
    }

    @Override
    public int getPrecio() {
      return 20;
    }

  }

  static final class Vainilla implements Helado {

    public Vainilla() {
      crear();
    }

    @Override
    public void crear() {
      System.out.println("Se ha creado un helado sabor Vainilla");
    }

    @Override
    public void comer() {
      System.out.println("Era un rico helado sabor Vainilla.");
    }

    @Override
    public int getPrecio() {
      return 17;
    }

  }

  static final class Chocolate implements Helado {

    public Chocolate() {
      crear();
    }

    @Override
    public void crear() {
      System.out.println("Se ha creado un helado sabor Chocolate");
    }

    @Override
    public void comer() {
      System.out.println("Era un rico helado sabor Chocolate.");
    }

    @Override
    public int getPrecio() {
      return 23;
    }
  }
}
