package com.example.order.application.adapter;

import com.example.order.port.Message;
import com.example.order.port.MessageSender;
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
                    new OrderCompletedEvent() //
                            .setOrderId(orderId), //
                    traceId));
  }

  public static class OrderCompletedEvent {
    private String orderId;

    public String getOrderId() {
      return orderId;
    }

    public OrderCompletedEvent setOrderId(String orderId) {
      this.orderId = orderId;
      return this;
    }
  }
}
