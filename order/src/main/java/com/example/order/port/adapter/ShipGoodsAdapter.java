package com.example.order.port.adapter;

import com.example.order.domain.Order;
import com.example.order.port.adapter.base.PublishSubscribeAdapter;
import com.example.order.port.message.Message;
import com.example.order.port.message.MessageSender;
import com.example.order.repository.OrderRepository;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipGoodsAdapter extends PublishSubscribeAdapter {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(ActivityExecution context) throws Exception {
    Order order = orderRepository.getOrder( //
            (String)context.getVariable("orderId"));
    String pickId = (String)context.getVariable("pickId"); // TODO read from step before!
    String traceId = context.getProcessBusinessKey();

    messageSender.send(new Message<>( //
            "ShipGoodsCommand", //
            traceId, //
            new ShipGoodsCommandPayload() //
                    .setPickId(pickId) //
                    .setRecipientName(order.getCustomer().getName()) //
                    .setRecipientAddress(order.getCustomer().getAddress())));

    addMessageSubscription(context, "GoodsShippedEvent");
  }
}
