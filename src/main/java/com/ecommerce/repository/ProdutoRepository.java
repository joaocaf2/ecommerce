package com.ecommerce.repository;

import com.ecommerce.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void salvar(Produto produto) {
        entityManager.persist(produto);
    }

    public Produto buscarPorid(Long id) {
        var produto = entityManager.find(Produto.class, id);

        if (produto == null) {
            throw new RuntimeException(String.format("Produto com o código %d não encontrado no banco de dados", id));
        }

        return produto;
    }
}
