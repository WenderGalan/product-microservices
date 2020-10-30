package io.github.wendergalan.pagamento.repositories;

import io.github.wendergalan.pagamento.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
