import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login1 {
    private JTextField usuario1;
    private JPasswordField passwordField1;
    private JButton validarDatosButton;
    public JPanel mainPanel;
    private JLabel mensaje_validacion;

    public login1() {
        validarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuario1.getText();
                String contraseña = new String(passwordField1.getPassword());

                if (DatabaseConnector.validarUsuario(usuario, contraseña)) {
                    mensaje_validacion.setText("Acceso correcto");
                    abrirMenu();
                } else {
                    mensaje_validacion.setText("Usuario o contraseña incorrectos");
                }
            }
        });
    }

    private void abrirMenu() {
        JFrame frame = new JFrame("Menú Principal");
        frame.setContentPane(new Menu().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setVisible(true);

        //Cerrar la ventana de login
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel); // Obtener la ventana principal
        loginFrame.dispose(); // Cierra la ventana de login
    }

}
