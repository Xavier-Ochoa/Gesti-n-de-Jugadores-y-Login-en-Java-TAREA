import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Crear un JFrame con un título
        JFrame frame = new JFrame("Aplicación de Login");
        // Establecer el contenido de la ventana con el panel principal de la clase login1
        frame.setContentPane(new login1().mainPanel);
        // Configurar el comportamiento de cierre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Establecer el tamaño de la ventana
        frame.setSize(600, 700);
        // Hacer visible la ventana
        frame.setVisible(true);
        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);
    }
}
