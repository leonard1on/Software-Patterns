package Adapter;

import java.util.ArrayList;
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
public class NintendoSwitch {

  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    ArrayList<ControlesSwitch> controles = new ArrayList<>();
    XboxController xbox = new XboxController("Negro", "Rearmable", "Nuevo");
    controles.add(new Joycon("Drifting", "Azul"));
    controles.add(new ProController("Nuevo"));
    controles.add(new AdaptadorXbox(xbox));
    controles.add(new Joycon("Drifting", "Rojo"));
    controles.add(new Joycon("Nuevo", "Verde Lima"));
    controles.add(new Joycon("Usado", "Verde"));
    Switch consola = new Switch();
    System.out.println("*****SWITCH*****");
    int resp = -1;
    String resp2;
    do {
      controles.forEach(control -> {
        if (!control.isConectado()) {
          System.out.println(controles.indexOf(control) + ". " + control.toString());
        }
      });
      System.out.println("Seleccione un control para conectar al Switch");
      try {
        resp = sc.nextInt();
      } catch (Exception e) {
      }
      if (resp >= 0 && resp < controles.size()) {
        controles.get(resp).sincronizarControl(consola);
      } else {
        System.out.println("Escoja un numero de la lista");
      }
      System.out.println("Desea conectar otro? y/n");
      resp2 = sc.next();
    } while (!resp2.equals("n") && consola.getNumControles() != 4);
    if (consola.getNumControles() == 4) {
      System.out.println("Maximo numero de controles conectados");
    }
    System.out.println("Y todos los jugadores jugaron hasta el amanecer...");
    controles.stream().filter((control) -> (control instanceof AdaptadorXbox)).forEachOrdered((_item) -> {
      System.out.println("Que lindo, jugando Switch con control de xbox");
    });

  }

  static class Switch {

    private ArrayList<ControlesSwitch> controles;
    private int numControles;

    public Switch() {
      controles = new ArrayList<>();
      numControles = 0;
    }

    public int getNumControles() {
      return numControles;
    }

    public void addControl() {
      if (numControles <= 4) {
        numControles++;
      } else {
        System.out.println("Solo se puede conectar un máximo de 4 controles");
      }
    }

    public void desconectarControl() {
      if (numControles == 0) {
        numControles--;
      } else {
        System.out.println("No hay controles conectados");
      }
    }

    public ArrayList<ControlesSwitch> getControles() {
      return controles;
    }

    public void setControles(ArrayList<ControlesSwitch> controles) {
      this.controles = controles;
    }

  }

  static abstract class ControlesSwitch {

    private Switch sincronizado;
    private String condicion;
    private boolean conectado;

    public ControlesSwitch() {
      sincronizado = null;
      conectado = false;
    }

    public ControlesSwitch(String condicion) {
      this.condicion = condicion;
      sincronizado = null;
      conectado = false;
    }

    public void sincronizarControl(Switch consola) {
      if (sincronizado != null) {
        this.desincronizarControl();
      }
      sincronizado = consola;
      consola.getControles().add(this);
      this.conectarAlSwitch();
    }

    public void desincronizarControl() {
      sincronizado.getControles().remove(this);
      sincronizado = null;
    }

    public void conectarAlSwitch() {
      if (!conectado) {
        sincronizado.addControl();
        conectado = true;
        this.print();
      } else {
        System.out.println("Control actualmente conectado");
      }

    }

    public void desconectarDelSwitch() {
      if (conectado) {
        sincronizado.desconectarControl();
        conectado = false;
      } else {
        System.out.println("Control no esta conectado");
      }
    }

    public boolean isConectado() {
      return conectado;
    }

    public Switch getSwitch() {
      return sincronizado;
    }

    public String getCondicion() {
      return condicion;
    }

    public void setCondicion(String condicion) {
      this.condicion = condicion;
    }

    public void print() {
    }
  }

  static class ProController extends ControlesSwitch {

    public ProController(String condicion) {
      super(condicion);
    }

    @Override
    public String toString() {
      return "ProController condicion: " + super.getCondicion();
    }

    @Override
    public void print() {
      System.out.println("ProController ha sido conectado");
      ;
    }
  }

  static class Joycon extends ControlesSwitch {
    private String color;

    public Joycon(String condicion, String color) {
      super(condicion);
      this.color = color;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    @Override
    public String toString() {
      return "Joycon" + " color: " + color + ", condicion: " + super.getCondicion();
    }

    @Override
    public void print() {
      System.out.println("Joycon " + color + " ha sido conectado");
    }

  }

  static class AdaptadorXbox extends ControlesSwitch {
    private final XboxController xbox;

    public AdaptadorXbox(XboxController xbox) {
      super();
      this.xbox = xbox;
    }

    @Override
    public boolean isConectado() {
      return xbox.isConectado(); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void desconectarDelSwitch() {
      if (xbox.isConectado()) {
        super.getSwitch().desconectarControl();
        xbox.setConectado(false);
      } else {
        System.out.println("Control no esta conectado");
        this.toString();
      }
    }

    @Override
    public void conectarAlSwitch() {
      if (!xbox.isConectado()) {
        super.getSwitch().addControl();
        xbox.setConectado(true);
        print();
      } else {
        System.out.println("Control actualmente conectado");
      }
    }

    @Override
    public String toString() {
      return "AdaptadorXbox color: " + this.xbox.getColor() + ", tipo: " + this.xbox.getTipo() + ", condicion: "
          + xbox.getCondicion();
    }

    @Override
    public void print() {
      System.out.println("AdaptadorXbox ha sido conectado");
    }
  }

  static class XboxController {
    private String color;
    private String tipo;
    private String condicion;
    private boolean conectado;

    public XboxController(String color, String tipo, String condicion) {
      this.color = color;
      this.tipo = tipo;
      this.condicion = condicion;
      conectado = false;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getTipo() {
      return tipo;
    }

    public void setTipo(String tipo) {
      this.tipo = tipo;
    }

    public String getCondicion() {
      return condicion;
    }

    public void setCondicion(String condicion) {
      this.condicion = condicion;
    }

    public boolean isConectado() {
      return conectado;
    }

    public void setConectado(boolean conectado) {
      this.conectado = conectado;
    }

  }

}
