package controller;

import model.LivroModel;
import model.UsuarioModel;
import repository.LivroRepository;
import java.sql.SQLException;
import java.util.List;

public class LivroController {
    LivroRepository livroRepository = new LivroRepository();
        public LivroModel buscarPorId(Long id) {
            return livroRepository.buscarPorId(id);
    }

    public String salvar(LivroModel livro) throws SQLException {
        String retornoDaRepository = livroRepository.salvar(livro);
        return retornoDaRepository;
    }

    public List<LivroModel> buscarTodos() throws SQLException {
        return livroRepository.buscarTodos();
    }

    public String remover(Long idLivroSelecionado) throws SQLException {
        LivroModel livro = livroRepository.buscarPorId(idLivroSelecionado);
        return livroRepository.remover(livro);
    }

    public String editar(LivroModel livro) throws SQLException {
        return livroRepository.editar(livro);
    }
}
