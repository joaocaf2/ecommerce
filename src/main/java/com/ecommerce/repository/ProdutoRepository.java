package com.ecommerce.repository;

import com.ecommerce.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void salvar(Produto produto) {
        entityManager.persist(produto);
    }

    @Transactional
    public void atualizar(Produto produto) {
        entityManager.merge(produto);
    }

    public Produto buscarPorId(Long id) {
        var produto = entityManager.find(Produto.class, id);

        if (produto == null) {
            throw new RuntimeException(String.format("Produto com o código %d não encontrado no banco de dados", id));
        }

        return produto;
    }

    public List<Produto> buscarTodos() {
        return entityManager
                .createQuery("SELECT p FROM Produto p", Produto.class)
                .getResultList();
    }
}
