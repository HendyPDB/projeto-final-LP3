package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    private JMenuBar menuBar;
    private JPanel panel1;

    public Principal() {
        // Inicialização do menuBar
        menuBar = new JMenuBar();

        // Criação do menu
        criacaoDoMenu();

        // Configurações da janela
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void criacaoDoMenu() {

        this.setJMenuBar(menuBar);

        JMenu manterUsuario = new JMenu("Usuários");
        JMenuItem cadastroUsuario = new JMenuItem("Cadastrar");
        JMenuItem buscarUsuario = new JMenuItem("Buscar");
        manterUsuario.add(cadastroUsuario);
        manterUsuario.add(buscarUsuario);

        JMenu manterLivro = new JMenu("Livros");
        JMenuItem cadastrarLivro = new JMenuItem("Cadastrar");
        JMenuItem buscarLivro = new JMenuItem("Buscar");
        manterLivro.add(cadastrarLivro);
        manterLivro.add(buscarLivro);

        menuBar.add(manterUsuario);
        menuBar.add(manterLivro);

        //add as acoes no menu
        cadastroUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroDeUsuario();
            }
        });

        buscarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarUsuario(); // abre a janela de busca
            }
        });


        cadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroDeLivro();
            }
        });

        buscarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarLivro();
            }
        });

    }
}
