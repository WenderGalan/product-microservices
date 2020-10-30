package io.github.wendergalan.pagamento.repositories;

import io.github.wendergalan.pagamento.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
