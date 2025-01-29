package view;

import controller.UsuarioController;
import model.UsuarioModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroDeUsuario extends JFrame {
    private JPanel jpanelPrincipal;
    private JPanel Principal;
    private JTextField textFieldNome;
    private JTextField textFieldSexo;
    private JTextField textFieldNumeroCelular;
    private JTextField textFieldEmail;
    private JLabel jLabelTitulo;
    private JLabel jLabelNome;
    private JLabel jLabelSexo;
    private JLabel jLabelEmail;
    private JLabel jLabelNumeroCelular;
    private JButton buttonEnviar;
    private UsuarioController usuarioController = new UsuarioController();

    public CadastroDeUsuario() {
        this.setTitle("Cadastro de Usuario - Sistema de Gestão de Biblioteca");
        this.setContentPane(jpanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setNome(textFieldNome.getText());
                usuario.setSexo(textFieldSexo.getText());
                usuario.setNumeroCelular(textFieldNumeroCelular.getText());
                usuario.setEmail(textFieldEmail.getText());

                try {
                    String resultado = usuarioController.salvar(usuario);
                    JOptionPane.showMessageDialog(null, resultado);

                    if (resultado.contains("sucesso")) {
//                        dispose(); //fecha para a pagina inicial
                        new Principal();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
    }
}
