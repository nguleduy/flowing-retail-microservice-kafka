package com.example.order.application.adapter;

import com.example.order.application.adapter.base.CommandPubEventSubAdapter;
import com.example.order.domain.Order;
import com.example.order.port.Message;
import com.example.order.port.MessageSender;
import com.example.order.repository.OrderRepository;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrievePaymentAdapter extends CommandPubEventSubAdapter {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public void execute(ActivityExecution execution) throws Exception {
    Order order = orderRepository.getOrder( //
            (String) execution.getVariable("orderId"));

    addMessageSubscription(execution, "PaymentReceivedEvent");

    messageSender.send( //
            new Message<>( //
                    "RetrievePaymentCommand", //
                    new RetrievePaymentPayload() //
                            .setRefId(order.getId()) //
                            .setAmount(order.getTotalSum()), //
                    execution.getBusinessKey()));
  }
}
