package view;

import controller.UsuarioController;
import model.UsuarioModel;
import repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BuscarUsuario extends JFrame {
    private UsuarioController usuarioController = new UsuarioController();
    private JPanel panelPrincipal;
    private JTextField textFieldBusca;
    private JButton buttonBuscar;
    private JTable tableBuscaUsuario;
    private JScrollPane scrollPaneUsuario;
    private JButton removerButton;
    private JButton editarButton;

    public BuscarUsuario() {
        this.setTitle("Sistema de Gestão de Biblioteca");
        UsuarioModeloDeTabela usuarioModeloDeTabela = new UsuarioModeloDeTabela();
        tableBuscaUsuario.setModel(usuarioModeloDeTabela);
        tableBuscaUsuario.setAutoCreateRowSorter(true);

        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        // listener pra remover, editar e buscar
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
                if (linhaSelecionada != -1) {
                    try {
                        Long idUsuario = Long.parseLong(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());
                        String resultado = usuarioController.remover(idUsuario);
                        JOptionPane.showMessageDialog(null, resultado);

                        atualizarTabela();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover o usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
                if (linhaSelecionada != -1) {
                    try {
                        Long idUsuario = Long.parseLong(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());
                        UsuarioModel usuario = usuarioController.buscarPorId(idUsuario);

                        if (usuario != null) {
                            String novoNome = JOptionPane.showInputDialog("Editar Nome:", usuario.getNome());
                            String novoSexo = JOptionPane.showInputDialog("Editar Sexo:", usuario.getSexo());
                            String novoNumeroCelular = JOptionPane.showInputDialog("Editar Número de Celular:", usuario.getNumeroCelular());
                            String novoEmail = JOptionPane.showInputDialog("Editar Email:", usuario.getEmail());

                            usuario.setNome(novoNome);
                            usuario.setSexo(novoSexo);
                            usuario.setNumeroCelular(novoNumeroCelular);
                            usuario.setEmail(novoEmail);

                            String resultado = usuarioController.editar(usuario);
                            JOptionPane.showMessageDialog(null, resultado);

                            atualizarTabela();
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário com ID " + idUsuario + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar o usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
                    atualizarTabela();
                }
            }
        });
    }

    private void atualizarTabela() {
        UsuarioModeloDeTabela usuarioModeloDeTabela = new UsuarioModeloDeTabela();
        tableBuscaUsuario.setModel(usuarioModeloDeTabela);
    }

    private void atualizarTabelaPorId(Long id) {
        UsuarioModeloDeTabela usuarioModeloDeTabela = new UsuarioModeloDeTabela(id);
        tableBuscaUsuario.setModel(usuarioModeloDeTabela);
    }

    private void createUIComponents() {
        panelPrincipal = new JPanel();
        scrollPaneUsuario = new JScrollPane();
        tableBuscaUsuario = new JTable();
        removerButton = new JButton("Remover");
        editarButton = new JButton("Editar");
        textFieldBusca = new JTextField(20);
        buttonBuscar = new JButton("Buscar");
    }

    private static class UsuarioModeloDeTabela extends AbstractTableModel {
        private UsuarioRepository usuarioRepository = new UsuarioRepository();
        private final String[] colunasDaTabela = new String[]{"Id", "Nome", "Sexo", "NumeroCelular", "Email"};
        private List<UsuarioModel> listaDeUsuarios;

        public UsuarioModeloDeTabela() {
            listaDeUsuarios = usuarioRepository.buscarTodos();
        }

        public UsuarioModeloDeTabela(Long id) {
            UsuarioModel usuario = usuarioRepository.buscarPorId(id);
            listaDeUsuarios = usuario != null ? List.of(usuario) : Collections.emptyList();
        }

        @Override
        public int getRowCount() {
            return listaDeUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return colunasDaTabela.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listaDeUsuarios.get(rowIndex).getId();
                case 1 -> listaDeUsuarios.get(rowIndex).getNome();
                case 2 -> listaDeUsuarios.get(rowIndex).getSexo();
                case 3 -> listaDeUsuarios.get(rowIndex).getNumeroCelular();
                case 4 -> listaDeUsuarios.get(rowIndex).getEmail();
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
