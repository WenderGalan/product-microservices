package io.github.wendergalan.pagamento.services;

import io.github.wendergalan.pagamento.data.vo.VendaVO;
import io.github.wendergalan.pagamento.entities.ProdutoVenda;
import io.github.wendergalan.pagamento.entities.Venda;
import io.github.wendergalan.pagamento.exceptions.ResourceNotFoundException;
import io.github.wendergalan.pagamento.repositories.ProdutoVendaRepository;
import io.github.wendergalan.pagamento.repositories.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    public VendaVO create(VendaVO vendaVO) {
        Venda venda = vendaRepository.save(Venda.create(vendaVO));
        List<ProdutoVenda> produtosSalvos = new ArrayList<>();
        if (vendaVO.getProdutos() != null && !vendaVO.getProdutos().isEmpty()) {
            vendaVO.getProdutos().forEach(p -> {
                ProdutoVenda pv = ProdutoVenda.create(p);
                pv.setVenda(venda);
                produtosSalvos.add(produtoVendaRepository.save(pv));
            });
            venda.setProdutos(produtosSalvos);
        }
        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::converToVendaVO);
    }

    private VendaVO converToVendaVO(Venda produto) {
        return VendaVO.create(produto);
    }

    public VendaVO findById(Long id) {
        var entity = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return VendaVO.create(entity);
    }

    public VendaVO update(VendaVO vendaVO) {
        Optional<Venda> optionalVenda = vendaRepository.findById(vendaVO.getId());

        if (optionalVenda.isEmpty())
            throw new ResourceNotFoundException("No records found for this ID");

        return VendaVO.create(vendaRepository.save(Venda.create(vendaVO)));
    }

    public void delete(Long id) {
        var entity = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        vendaRepository.delete(entity);
    }
}
