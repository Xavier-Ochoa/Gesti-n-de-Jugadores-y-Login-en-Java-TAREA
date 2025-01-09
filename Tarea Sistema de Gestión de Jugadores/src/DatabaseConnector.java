import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://bbjvuorblnepnr06thuw-mysql.services.clever-cloud.com:3306/bbjvuorblnepnr06thuw";
    private static final String USER = "ur7quvbcyib45yaz";
    private static final String PASSWORD = "zguvsAjoQkpfBCuu9SiB";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validarUsuario(String usuario, String contraseña) {
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
