package repository;

import model.UsuarioModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("crudHibernatePU");
        }
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public UsuarioModel buscarPorId(Long id) {
        try {
            return entityManager.find(UsuarioModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String salvar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Usuário salvo com sucesso.";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao salvar: " + e.getMessage();
        }
    }

    public List<UsuarioModel> buscarTodos() {
        try {
            return entityManager.createQuery("FROM UsuarioModel", UsuarioModel.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String remover(Long id) {
        try {
            entityManager.getTransaction().begin();

            UsuarioModel usuario = entityManager.find(UsuarioModel.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.getTransaction().commit();
                return "Usuário removido com sucesso!";
            } else {
                entityManager.getTransaction().rollback();
                return "Erro: Usuário com ID " + id + " não encontrado.";
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao remover o usuário: " + e.getMessage();
        }
    }

    public String editar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return "Usuário editado com sucesso!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao editar: " + e.getMessage();
        }
    }
}