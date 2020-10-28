package io.github.wendergalan.crud.services;

import io.github.wendergalan.crud.data.vo.ProdutoVO;
import io.github.wendergalan.crud.entities.Produto;
import io.github.wendergalan.crud.exceptions.ResourceNotFoundException;
import io.github.wendergalan.crud.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoVO create(ProdutoVO produtoVO) {
        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::converToProdutoVO);
    }

    private ProdutoVO converToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO) {
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());

        if (optionalProduto.isEmpty())
            throw new ResourceNotFoundException("No records found for this ID");

        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        produtoRepository.delete(entity);
    }
}
