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
public class FetchGoodsAdapter extends PublishSubscribeAdapter {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(ActivityExecution context) throws Exception {
    Order order = orderRepository.getOrder( //
            (String)context.getVariable("orderId"));
    String traceId = context.getProcessBusinessKey();

    // publish
    messageSender.send(new Message<>( //
            "FetchGoodsCommand", //
            traceId, //
            new FetchGoodsCommandPayload() //
                    .setRefId(order.getId()) //
                    .setItems(order.getItems())));

    // subscribe
    addMessageSubscription(context, "GoodsFetchedEvent");
  }
}
