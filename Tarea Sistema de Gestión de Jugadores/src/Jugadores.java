public class Jugadores {

    // Atributos
    private String nombre;
    private String posicion;
    private String equipo;
    private int edad;

    // Constructor vacío (sin parámetros)
    public Jugadores() {
        // Este constructor no hace nada, solo crea el objeto sin inicializar los atributos
    }

    // Constructor con parámetros (normal)
    public Jugadores(String nombre, String posicion, String equipo, int edad) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.equipo = equipo;
        this.edad = edad;
    }

    // Getter y Setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para 'posicion'
    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    // Getter y Setter para 'equipo'
    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    // Getter y Setter para 'edad'
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
