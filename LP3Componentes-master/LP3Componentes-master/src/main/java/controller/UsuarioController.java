package controller;

import model.LivroModel;
import model.UsuarioModel;
import repository.UsuarioRepository;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public UsuarioModel buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }
    public String salvar(UsuarioModel usuario) throws SQLException {
        String retornoDaRepository = usuarioRepository.salvar(usuario);
        return retornoDaRepository;
    }

    public String remover(Long idUsuarioSelecionado) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorId(idUsuarioSelecionado);
        return usuarioRepository.remover(usuario.getId());
    }

    public String editar(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.editar(usuario);
    }
}
