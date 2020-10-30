package io.github.wendergalan.pagamento.data.vo;

import io.github.wendergalan.pagamento.entities.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {
    private Long id;
    private Date data;
    private List<ProdutoVendaVO> produtos;
    private Double valorTotal;

    public static VendaVO create(Venda venda) {
        return new ModelMapper().map(venda, VendaVO.class);
    }
}
