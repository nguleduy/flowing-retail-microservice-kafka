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
public class FetchGoodsAdapter extends CommandPubEventSubAdapter {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(ActivityExecution execution) throws Exception {
    Order order = orderRepository.getOrder((String) execution.getVariable("orderId"));
    String traceId = (String) execution.getVariable("traceId"); // Business key?

    addMessageSubscription(execution, "GoodsFetchedEvent");

    messageSender.send(new Message<FetchGoodsCommandPayload>( //
            "FetchGoodsCommand", //
            new FetchGoodsCommandPayload() //
                    .setRefId(order.getId()) //
                    .setItems(order.getItems()), //
            traceId));
  }

}
