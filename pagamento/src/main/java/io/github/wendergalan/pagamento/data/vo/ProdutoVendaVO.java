package io.github.wendergalan.pagamento.data.vo;

import io.github.wendergalan.pagamento.entities.ProdutoVenda;
import io.github.wendergalan.pagamento.entities.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> implements Serializable {
    private Long idProduto;
    private Integer quantidade;
    private Venda venda;

    public static ProdutoVendaVO create(ProdutoVenda produtoVenda) {
        return new ModelMapper().map(produtoVenda, ProdutoVendaVO.class);
    }
}
