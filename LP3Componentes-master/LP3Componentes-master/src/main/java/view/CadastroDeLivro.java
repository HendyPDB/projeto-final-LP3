package view;

import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroDeLivro extends JFrame {
    private JPanel jpanelPrincipal;
    private JPanel Principal;
    private JTextField textFieldTitulo;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldISBN;
    private JTextField textFieldDataPublicacao;
    private JTextField textFieldQuantidadeDisponivel;
    private JButton cadastrarButton;
    private JLabel jLabelLivro;
    private JLabel jLabelTitulo;
    private JLabel jLabelTema;
    private JLabel jLabelAutor;
    private JLabel jLabelISBN;
    private JLabel jLabelDataPublicacao;
    private JLabel jLabelQuantidadeDisponivel;
    private LivroController livroController = new LivroController();

    public CadastroDeLivro() {
        this.setTitle("Cadastro de Livro - Sistema de Gestão de Biblioteca");
        this.setContentPane(jpanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LivroModel livro = new LivroModel();
                    livro.setTitulo(textFieldTitulo.getText());
                    livro.setTema(textFieldTema.getText());
                    livro.setAutor(textFieldAutor.getText());
                    livro.setIsbn(textFieldISBN.getText());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataPublicacao;
                    try {
                        dataPublicacao = LocalDate.parse(textFieldDataPublicacao.getText(), formatter);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    livro.setDataPublicacao(dataPublicacao);

                    try {
                        int quantidade = Integer.parseInt(textFieldQuantidadeDisponivel.getText());
                        livro.setQuantidadeDisponivel(quantidade);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Quantidade deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String resultado = livroController.salvar(livro);
                    JOptionPane.showMessageDialog(null, resultado);

                    if (resultado.contains("sucesso")) {
                        dispose();
                        new Principal();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
    }
}
