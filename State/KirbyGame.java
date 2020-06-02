package State;

import java.util.ArrayList;
import java.util.Scanner;

public class KirbyGame {
  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    KirbyContext juego = new KirbyContext();
    ArrayList<Enemigo> enemigos = crearEnemigos();
    String resp;
    int cont = -1;
    System.out.println("%%% Bienvenido a Kirby Star Allies %%%\n"
        + "Instrucciones: Kirby puede usar dos tipos de ataques, Fisico y Especial.\n"
        + "Si no tiene poder, Kirby puede patear o absorber el enemigo.\n"
        + "Si tiene un poder, Kirby puede usar su poder o liberarlo\n"
        + "Si esta bajo el agua, Kirby puede escupir agua, o hacer un dash\n"
        + "Guia a Kirby a vencer a todos los enemigos!\n");
    for (Enemigo enemigo : enemigos) {
      if (juego.isAlive()) {
        cont++;
        System.out.println();
        System.out.println(enemigo.getNombre() + " aparece");
        juego.setEnemigo(enemigo);
        if (cont == 3) {
          System.out.println("Kirby entro al agua");
          juego.changeState(new WaterState());
        }
        do {
          System.out.println("Elige tu ataque (F / E)");
          resp = sc.next();
          switch (resp) {
            case "F":
            case "f":
              juego.accionFisicaKirby();
              break;
            case "E":
            case "e":
              juego.accionEspecialKirby();
              break;
            default:
              System.out.println("Eleccion equivocada, has perdido turno");
              break;
          }
          if (enemigo.estaVivo()) {
            juego.ataquedirecto();
            System.out.println(enemigo.getNombre() + " ataco a Kirby y recibio 1 de daño");
          } else {
            enemigo.muere();
          }
        } while (enemigo.estaVivo() && juego.isAlive());
      } else {
        System.out.println("Kirby murio");
        break;
      }
    }

    if (juego.isAlive()) {
      System.out.println("Kirby ha derrotado todos los enemigos, hurray!");
    } else {
      System.out.println("Kirby fue cruelmente asesinado por " + enemigos.get(cont).getNombre());
    }
  }

  public static ArrayList<Enemigo> crearEnemigos() {
    ArrayList<Enemigo> enemigos = new ArrayList<>();
    enemigos.add(new Enemigo("Sir Kibble", "Cutter"));
    enemigos.add(new Enemigo("Burning Leo", "Fire"));
    enemigos.add(new Enemigo("Galbo", "Fire"));
    enemigos.add(new Enemigo("Bonkers", "Hammer"));
    enemigos.add(new Enemigo("Blade Knight", "Sword"));
    enemigos.add(new Enemigo("Void Termina", "Death"));
    return enemigos;
  }

  static class Kirby {

    private int vida;
    private String poder;

    public Kirby() {
      this.vida = 5;
      this.poder = "";
    }

    // Especiales
    public void tragarEnemigo(Enemigo enemigo) {
      if (!enemigo.getNombre().equals("Void Termina")) {
        System.out.println("Kirby trago a " + enemigo.getNombre() + " y obtuvo el poder " + enemigo.getPoder());
        this.poder = enemigo.getPoder();
        enemigo.setVida(enemigo.getVida() - 2);
      } else {
        System.out.println("No puedes absorberlo! Muy Grande");
      }

    }

    public void usarPoder(Enemigo enemigo) {
      System.out.println("Kirby ataca usando el " + poder);
      if (enemigo.getPoder().equals(poder)) {
        System.out.println("El enemigo es inmune a ese poder");
      } else {
        enemigo.setVida(enemigo.getVida() - 3);
      }
    }

    public void waterDash(Enemigo enemigo) {
      System.out.println("Kirby hizo un dash en el agua pero recibio 1 de daño");
      vida--;
      enemigo.setVida(enemigo.getVida() - 2);
    }

    // Fisicos
    public void patear(Enemigo enemigo) {
      System.out.println("Kirby pega una patada chingona");
      enemigo.setVida(enemigo.getVida() - 1);
    }

    public void quitarPoder(Enemigo enemigo) {
      System.out.println("Kirby ha liberado su poder");
      this.poder = "";
    }

    public void escupirAgua(Enemigo enemigo) {
      System.out.println("Kirby escupe agua a su enemigo");
      enemigo.setVida(enemigo.getVida() - 2);
    }

    // Siempre
    public void volar() {
      System.out.println("Kirby empieza a volar, los enemigos no lo alcanzan");
    }

    public void recibirDano() {
      vida--;
    }

    public boolean viveKirby() {
      return vida > 0;
    }

    public String getPoder() {
      return poder;
    }
  }

  static class KirbyContext {

    private KirbyInterface state = new NormalState();
    private Enemigo enemigo = null;
    private final Kirby kirby = new Kirby();

    public void changeState(KirbyInterface state) {
      this.state = state;
    }

    public void accionFisicaKirby() {
      state.accionFisica(kirby, enemigo);
      if (state instanceof WaterState) {
        System.out.println("Kirby salio del agua");
        state = new NormalState();
      }
    }

    public void accionEspecialKirby() {
      state.accionEspecial(kirby, enemigo);
      if (state instanceof PowerState) {
        System.out.println("Cambio a estado normal");
        state = new NormalState();
      } else if (state instanceof NormalState) {
        System.out.println("Cambio a estado con poder");
        state = new PowerState();
      } else {
        System.out.println("Kirby salio del agua");
        state = new NormalState();
      }
    }

    public boolean isAlive() {
      return kirby.viveKirby();
    }

    public void ataquedirecto() {
      kirby.recibirDano();
    }

    public void setEnemigo(Enemigo enemigo) {
      this.enemigo = enemigo;
    }

  }

  static interface KirbyInterface {
    public void accionFisica(Kirby k, Enemigo enemigo);

    public void accionEspecial(Kirby k, Enemigo enemigo);

  }

  static class NormalState implements KirbyInterface {

    @Override
    public void accionFisica(Kirby k, Enemigo enemigo) {
      k.patear(enemigo);
    }

    @Override
    public void accionEspecial(Kirby k, Enemigo enemigo) {
      k.tragarEnemigo(enemigo);
    }

  }

  static class WaterState implements KirbyInterface {

    @Override
    public void accionFisica(Kirby k, Enemigo enemigo) {
      k.escupirAgua(enemigo);
    }

    @Override
    public void accionEspecial(Kirby k, Enemigo enemigo) {
      k.waterDash(enemigo);
    }

  }

  static class PowerState implements KirbyInterface {

    @Override
    public void accionFisica(Kirby k, Enemigo enemigo) {
      k.usarPoder(enemigo);
    }

    @Override
    public void accionEspecial(Kirby k, Enemigo enemigo) {
      k.quitarPoder(enemigo);
    }

  }

  static class Enemigo {

    private String nombre;
    private int dano;
    private String poder;
    private int vida;

    public Enemigo(String nombre, String poder) {
      this.nombre = nombre;
      this.dano = 1;
      if (nombre.equals("Void Termina")) {
        vida = 10;
      } else {
        vida = 2;
      }
      this.poder = poder;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public int getDano() {
      return dano;
    }

    public void setDano(int dano) {
      this.dano = dano;
    }

    public String getPoder() {
      return poder;
    }

    public void setPoder(String poder) {
      this.poder = poder;
    }

    public int getVida() {
      return vida;
    }

    public void setVida(int vida) {
      this.vida = vida;
    }

    public boolean estaVivo() {
      return vida > 0;
    }

    public void muere() {
      System.out.println("Enemigo " + "nombre: " + nombre + " ha muerto");
    }

  }

}
