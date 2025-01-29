package view;

import controller.LivroController;
import model.LivroModel;
import repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

public class BuscarLivro extends JFrame {
    private LivroController livroController = new LivroController();
    private JPanel panelPrincipal;
    private JTextField textFieldBusca;
    private JButton buttonBuscar;
    private JTable tableBuscaLivro;
    private JScrollPane scrollPaneLivro;
    private JButton removerButton;
    private JButton editarButton;

    public BuscarLivro() {
        this.setTitle("Sistema de Gestão de Biblioteca - Buscar Livro");
        LivroModeloDeTabela livroModeloDeTabela = new LivroModeloDeTabela();
        tableBuscaLivro.setModel(livroModeloDeTabela);
        tableBuscaLivro.setAutoCreateRowSorter(true);

        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        // Listener para remover, editar e buscar
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaLivro.getSelectedRow();
                if (linhaSelecionada != -1) {
                    try {
                        Long idLivro = Long.parseLong(tableBuscaLivro.getValueAt(linhaSelecionada, 0).toString());
                        String resultado = livroController.remover(idLivro);
                        JOptionPane.showMessageDialog(null, resultado);

                        atualizarTabela();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover o livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaLivro.getSelectedRow();
                if (linhaSelecionada != -1) {
                    try {
                        Long idLivro = Long.parseLong(tableBuscaLivro.getValueAt(linhaSelecionada, 0).toString());
                        LivroModel livro = livroController.buscarPorId(idLivro);

                        if (livro != null) {
                            String novoTitulo = JOptionPane.showInputDialog("Editar Título:", livro.getTitulo());
                            String novoTema = JOptionPane.showInputDialog("Editar Tema:", livro.getTema());
                            String novoAutor = JOptionPane.showInputDialog("Editar Autor:", livro.getAutor());
                            String novoIsbn = JOptionPane.showInputDialog("Editar ISBN:", livro.getIsbn());
                            String novaQuantidade = JOptionPane.showInputDialog("Editar Quantidade Disponível:", String.valueOf(livro.getQuantidadeDisponivel()));
                            String novaData = JOptionPane.showInputDialog("Editar Data da Publicação (dd/MM/yyyy):", livro.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                            livro.setTitulo(novoTitulo);
                            livro.setTema(novoTema);
                            livro.setAutor(novoAutor);
                            livro.setIsbn(novoIsbn);
                            livro.setQuantidadeDisponivel(Integer.parseInt(novaQuantidade));

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dataPublicacao = LocalDate.parse(novaData, formatter);
                            livro.setDataPublicacao(dataPublicacao);

                            String resultado = livroController.editar(livro);
                            JOptionPane.showMessageDialog(null, resultado);

                            atualizarTabela();
                        } else {
                            JOptionPane.showMessageDialog(null, "Livro com ID " + idLivro + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar o livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoBusca = textFieldBusca.getText().trim();
                if (!textoBusca.isEmpty()) {
                    try {
                        Long idBusca = Long.parseLong(textoBusca);
                        atualizarTabelaPorId(idBusca);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido. Insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    atualizarTabela(); // att a tabela sem filtros
                }
            }
        });
    }

    private void atualizarTabela() {
        LivroModeloDeTabela livroModeloDeTabela = new LivroModeloDeTabela();
        tableBuscaLivro.setModel(livroModeloDeTabela);
    }

    private void atualizarTabelaPorId(Long id) {
        LivroModeloDeTabela livroModeloDeTabela = new LivroModeloDeTabela(id);
        tableBuscaLivro.setModel(livroModeloDeTabela);
    }

    private void createUIComponents() {
        panelPrincipal = new JPanel();
        scrollPaneLivro = new JScrollPane();
        tableBuscaLivro = new JTable();
        removerButton = new JButton("Remover");
        editarButton = new JButton("Editar");
        textFieldBusca = new JTextField(20);
        buttonBuscar = new JButton("Buscar");
    }

    private static class LivroModeloDeTabela extends AbstractTableModel {
        private LivroRepository livroRepository = new LivroRepository();
        private final String[] colunasDaTabela = new String[]{"Id", "Título", "Tema", "Autor", "ISBN", "Quantidade Disponível", "Data da Publicação" };
        private List<LivroModel> listaDeLivros;

        public LivroModeloDeTabela() {
            listaDeLivros = livroRepository.buscarTodos();
        }

        public LivroModeloDeTabela(Long id) {
            LivroModel livro = livroRepository.buscarPorId(id);
            listaDeLivros = livro != null ? List.of(livro) : Collections.emptyList();
        }

        @Override
        public int getRowCount() {
            return listaDeLivros.size();
        }

        @Override
        public int getColumnCount() {
            return colunasDaTabela.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return switch (columnIndex) {
                case 0 -> listaDeLivros.get(rowIndex).getId();
                case 1 -> listaDeLivros.get(rowIndex).getTitulo();
                case 2 -> listaDeLivros.get(rowIndex).getTema();
                case 3 -> listaDeLivros.get(rowIndex).getAutor();
                case 4 -> listaDeLivros.get(rowIndex).getIsbn();
                case 5 -> listaDeLivros.get(rowIndex).getQuantidadeDisponivel();
                case 6 -> listaDeLivros.get(rowIndex).getDataPublicacao() != null
                        ? listaDeLivros.get(rowIndex).getDataPublicacao().format(formatter)
                        : "-"; // garante que não quebre se a data for null
                default -> "-";
            };
        }


        @Override
        public String getColumnName(int columnIndex) {
            return colunasDaTabela[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
