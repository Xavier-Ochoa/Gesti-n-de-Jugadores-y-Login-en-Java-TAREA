import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Menu {
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable table1; // Esta tabla mostrará los datos
    private JButton boton_actualizar;
    private JTextField ingreso_de_nombre;
    private JTextField ingreso_de_poscicion;
    private JTextField ingreso_de_equipo;
    private JTextField ingreso_de_edad;
    private JTextField ingreso_de_ID;
    private JButton agregar_a_base;
    private JComboBox comboBox1_de_buscar;
    private JTextField ingreso_de_buscar;
    private JButton boton_de_buscarr;
    private JLabel resultado_de_busqueda;
    private JComboBox comboBox2_de_eliminar;
    private JTextField ingreso_de_eliminar;
    private JButton boton_de_eliminar;

    public Menu() {
        // Crear el modelo para la tabla de datos (table1)
        DefaultTableModel modelData = new DefaultTableModel() {
            // Hacer las celdas no editables en la tabla de datos
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Establecer los títulos de las columnas también para la tabla de datos
        String[] titulos = {"ID", "Nombre", "Posición", "Equipo", "Edad"};
        modelData.setColumnIdentifiers(titulos);  // Asignamos los títulos a las columnas

        // Asignamos el modelo de la tabla a la tabla de datos (table1)
        table1.setModel(modelData);

        // Cargar los jugadores cuando se inicie la pantalla
        cargarJugadores();

        // Acción del botón actualizar: al hacer clic, se recargan los datos en la tabla
        boton_actualizar.addActionListener(e -> cargarJugadores());

        // Acción del botón agregar_a_base: al hacer clic, se guardan los datos en la base de datos
        agregar_a_base.addActionListener(e -> guardarJugador());

        // Acción del botón buscar: buscar jugadores según el criterio seleccionado
        boton_de_buscarr.addActionListener(e -> buscarJugador());

        // Acción del botón eliminar: eliminar jugador según el criterio y valor ingresado
        boton_de_eliminar.addActionListener(e -> eliminarJugador());
    }

    // Método para cargar los jugadores desde la base de datos
    private void cargarJugadores() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);  // Limpiar la tabla antes de agregar nuevos datos

        String query = "SELECT * FROM Jugadores";  // Consulta para obtener todos los jugadores
        try (Connection conn = DatabaseConnector.getConnection();  // Manejo de conexión con try-with-resources
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.next()) {
                JOptionPane.showMessageDialog(panel1, "No hay jugadores en la base de datos");
            } else {
                do {
                    String edadStr = rs.getString("edad");
                    String edad;
                    try {
                        if (edadStr != null && !edadStr.isEmpty()) {
                            Integer.parseInt(edadStr);
                            edad = edadStr;
                        } else {
                            edad = "No disponible";
                        }
                    } catch (NumberFormatException ex) {
                        edad = "EDAD";
                    }

                    model.addRow(new Object[] {
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("posicion"),
                            rs.getString("equipo"),
                            edad
                    });
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al cargar los jugadores: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    // Método para guardar un nuevo jugador en la base de datos
    private void guardarJugador() {
        String id = ingreso_de_ID.getText();
        String nombre = ingreso_de_nombre.getText();
        String posicion = ingreso_de_poscicion.getText();
        String equipo = ingreso_de_equipo.getText();
        String edad = ingreso_de_edad.getText();

        String query = "INSERT INTO Jugadores (id, nombre, posicion, equipo, edad) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, nombre);
            pstmt.setString(3, posicion);
            pstmt.setString(4, equipo);
            pstmt.setInt(5, Integer.parseInt(edad));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(panel1, "Jugador guardado exitosamente.");
            cargarJugadores();  // Actualizar la tabla después de guardar

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al guardar el jugador: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    // Método para buscar un jugador en la base de datos
    private void buscarJugador() {
        String criterio = (String) comboBox1_de_buscar.getSelectedItem();  // Obtener el criterio de búsqueda (nombre, id, etc.)
        String valor = ingreso_de_buscar.getText();  // Obtener el valor de búsqueda ingresado

        String query = "SELECT * FROM Jugadores WHERE " + criterio + " = ?";  // Consulta SQL para buscar por el criterio seleccionado
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, valor);  // Establecer el valor en la consulta
            ResultSet rs = pstmt.executeQuery();  // Ejecutar la consulta

            if (rs.next()) {
                // Si se encuentra el jugador, mostramos toda su información en el JLabel
                String resultado = " ID: " + rs.getInt("id") + "\n" +
                        " Nombre: " + rs.getString("nombre") + "\n" +
                        " Posición: " + rs.getString("posicion") + "\n" +
                        " Equipo: " + rs.getString("equipo") + "\n" +
                        " Edad: " + rs.getString("edad");  // Podría ser necesario un tratamiento especial si la edad no es un número

                resultado_de_busqueda.setText(resultado);  // Mostrar el resultado completo en el JLabel
            } else {
                // Si no se encuentra el jugador, mostramos un mensaje indicando que no fue encontrado
                resultado_de_busqueda.setText("Jugador no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al buscar el jugador: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }

    // Método para eliminar un jugador de la base de datos
    private void eliminarJugador() {
        String criterio = (String) comboBox2_de_eliminar.getSelectedItem();  // Obtener el criterio de eliminación (ID, nombre, etc.)
        String valor = ingreso_de_eliminar.getText();  // Obtener el valor de eliminación ingresado

        String query = "DELETE FROM Jugadores WHERE " + criterio + " = ?";  // Consulta SQL para eliminar según el criterio seleccionado
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, valor);  // Establecer el valor en la consulta

            // Ejecutar la eliminación
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Si se eliminó algún registro, mostramos un mensaje de éxito
                JOptionPane.showMessageDialog(panel1, "Jugador eliminado exitosamente.");
                cargarJugadores();  // Recargar la tabla después de la eliminación
            } else {
                // Si no se encontró el jugador, mostramos un mensaje indicando que no se pudo eliminar
                JOptionPane.showMessageDialog(panel1, "Jugador no encontrado o no se pudo eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error al eliminar el jugador: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Error inesperado: " + e.getMessage());
        }
    }
}
