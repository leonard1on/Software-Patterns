package Observer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Unitexas {

  static Scanner sc = new Scanner(System.in);
  static Random r = new Random();

  public static void main(String[] args) {
    System.out.println("Bienvenido a Unitexas!\nMe permite, cual es su nombre?");
    String name = sc.next();
    System.out.println("Perfecto, que clase es la que esta dando?");
    String clase = sc.next();
    Maestro maestro = new Maestro(name, clase);
    maestro.registerObserver(new Estudiante("Paco"));
    maestro.registerObserver(new Estudiante("Roman"));
    maestro.registerObserver(new Estudiante("Carlos"));
    maestro.registerObserver(new Estudiante("Suazo"));
    maestro.registerObserver(new Estudiante("Robertotototo"));
    maestro.registerObserver(new Estudiante("Jonas"));
    String tarea;
    System.out
        .println("Ahora que estamos en cuarentena, debes asignar muchas tareas para que los estudiantes aprendan\n"
            + "Ingresa la tarea: (renunciar = exit)");
    sc.nextLine();

    do {
      tarea = sc.nextLine();
      maestro.addTarea(tarea);
      if (maestro.getTareas().size() > 4) {
        int num = r.nextInt(3);
        maestro.removeTarea(num);
      }
      System.out.println("Muy bien, los estudiantes estan aprendiendo, no renuncies, pon otra tarea:");

    } while (!tarea.equals("renunciar"));

  }

  static interface Subject {

    public void notifyAllObservers();

    public void notifyAllObservers(String s);

    public void registerObserver(Observer ob);

    public void removeObserver(Observer ob);

  }

  static class Maestro implements Subject {

    private ArrayList<Observer> obList;
    private ArrayList<String> tareas;
    private String nombre;
    private String clase;

    public Maestro(String nombre, String clase) {
      this.nombre = nombre;
      this.clase = clase;
      obList = new ArrayList<>();
      tareas = new ArrayList<>();
    }

    @Override
    public void notifyAllObservers() {
      obList.forEach((ob) -> {
        ob.updateAsignacion(this);
      });
    }

    @Override
    public void notifyAllObservers(String s) {
      obList.forEach((ob) -> {
        ob.updateTarea(s);
      });
    }

    @Override
    public void registerObserver(Observer ob) {
      obList.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
      obList.remove(ob);
    }

    public void addTarea(String tarea) {
      tareas.add(tarea);
      notifyAllObservers();
    }

    public void removeTarea(int tarea) {
      String task = tareas.get(tarea);
      tareas.remove(tarea);
      notifyAllObservers(task);
    }

    public ArrayList<String> getTareas() {
      return tareas;
    }

    public void setTareas(ArrayList<String> tareas) {
      this.tareas = tareas;
    }

    public ArrayList<Observer> getObList() {
      return obList;
    }

    public void setObList(ArrayList<Observer> obList) {
      this.obList = obList;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public String getClase() {
      return clase;
    }

    public void setClase(String clase) {
      this.clase = clase;
    }

    @Override
    public String toString() {
      return "Maestro " + nombre + " de la clase " + clase;
    }

  }

  static interface Observer {
    public void updateAsignacion(Subject s);

    public void updateTarea(String s);
  }

  static class Estudiante implements Observer {
    private String name;

    public Estudiante(String name) {
      this.name = name;
    }

    @Override
    public void updateAsignacion(Subject sub) {
      System.out.println(this.name + " fue notificado: Se ha asignado una tarea por " + sub.toString());
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public void updateTarea(String s) {
      System.out.println(this.name + "fue notificado: La tarea " + s + " ya expiro");
    }

  }
}
