package io.github.wendergalan.pagamento.messages;

import io.github.wendergalan.pagamento.data.vo.ProdutoVO;
import io.github.wendergalan.pagamento.entities.Produto;
import io.github.wendergalan.pagamento.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoReceiveMessage {
    private final ProdutoRepository produtoRepository;

    /**
     * Vai ficar escutando a fila do rabbitmq
     *
     * @param produtoVO
     */
    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO) {
        produtoRepository.save(Produto.create(produtoVO));
    }
}
