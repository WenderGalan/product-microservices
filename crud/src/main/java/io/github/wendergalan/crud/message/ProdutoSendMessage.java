package io.github.wendergalan.crud.message;

import io.github.wendergalan.crud.data.vo.ProdutoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoSendMessage {

    @Value("${crud.rabbitmq.exchange}")
    private String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    private String routingkey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(ProdutoVO produtoVO) {
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoVO);
    }
}
