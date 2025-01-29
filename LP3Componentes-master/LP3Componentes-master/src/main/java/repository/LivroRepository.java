package repository;

import model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class LivroRepository {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");

    private EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public String salvar(LivroModel livro) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro); // id será gerado automaticamente aqui
            entityManager.getTransaction().commit();
            return "Livro salvo com sucesso!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao salvar o livro: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    public String remover(LivroModel livro) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            LivroModel livroToRemove = entityManager.find(LivroModel.class, livro.getId());
            if (livroToRemove != null) {
                entityManager.remove(livroToRemove);
                entityManager.getTransaction().commit();
                return "Livro removido com sucesso!";
            } else {
                return "Livro não encontrado.";
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao remover o livro: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    public String editar(LivroModel livro) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(livro); // Atualiza o registro existente
            entityManager.getTransaction().commit();
            return "Livro editado com sucesso!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return "Erro ao editar o livro: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    public List<LivroModel> buscarTodos() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM LivroModel", LivroModel.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar livros: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    public LivroModel buscarPorId(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(LivroModel.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o livro por ID: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }
}
