package com.example.order.domain.adapter;

import com.example.order.port.Message;
import com.example.order.port.outbound.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String orderId = (String) execution.getVariable("orderId");
    String traceId = (String) execution.getVariable("traceId"); // Business key?

    messageSender.send( //
            new Message<>( //
                    "OrderCompletedEvent", //
                    new OrderCompletedEventPayload() //
                            .setOrderId(orderId), //
                    traceId));
  }
}
