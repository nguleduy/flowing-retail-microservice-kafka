package com.example.order.domain.adapter;

import com.example.order.domain.adapter.base.CommandPubEventSubAdapter;
import com.example.order.domain.Order;
import com.example.order.port.Message;
import com.example.order.port.outbound.MessageSender;
import com.example.order.repository.OrderRepository;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipGoodsAdapter extends CommandPubEventSubAdapter {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(ActivityExecution execution) throws Exception {
    Order order = orderRepository.getOrder((String) execution.getVariable("orderId"));
    String pickId = (String) execution.getVariable("pickId"); // TODO read from step before!
    String traceId = (String) execution.getVariable("traceId"); // Business key?

    addMessageSubscription(execution, "GoodsShippedEvent");

    messageSender.send(new Message<>( //
            "ShipGoodsCommand", //
            new ShipGoodsCommandPayload() //
                    .setPickId(pickId) //
                    .setRecipientName(order.getCustomer().getName()) //
                    .setRecipientAddress(order.getCustomer().getAddress()), //
            traceId));
  }
}
