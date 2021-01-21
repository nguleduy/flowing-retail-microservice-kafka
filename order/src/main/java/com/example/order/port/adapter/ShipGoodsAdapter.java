package com.example.order.port.adapter;

import com.example.order.domain.Order;
import com.example.order.port.message.Message;
import com.example.order.port.message.MessageSender;
import com.example.order.repository.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipGoodsAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    Order order = orderRepository.getOrder( //
            (String) context.getVariable("orderId"));
    String pickId = (String) context.getVariable("pickId"); // TODO read from step before!
    String traceId = context.getProcessBusinessKey();

    messageSender.send(new Message<>( //
            "ShipGoodsCommand", //
            traceId, //
            new ShipGoodsCommandPayload() //
                    .setPickId(order.getId()) //
                    .setRecipientName(order.getCustomer().getName()) //
                    .setRecipientAddress(order.getCustomer().getAddress())));
  }
}
