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
            new ShipGoodsCommand() //
                    .setPickId(pickId) //
                    .setRecipientName(order.getCustomer().getName()) //
                    .setRecipientAddress(order.getCustomer().getAddress()), //
            traceId));
  }

  public static class ShipGoodsCommand {
    private String pickId;
    // assume we always use the same provider for customer orders
    private String logisticsProvider = "DHL";
    private String recipientName;
    private String recipientAddress;

    public String getPickId() {
      return pickId;
    }

    public ShipGoodsCommand setPickId(String pickId) {
      this.pickId = pickId;
      return this;
    }

    public String getLogisticsProvider() {
      return logisticsProvider;
    }

    public ShipGoodsCommand setLogisticsProvider(String logisticsProvider) {
      this.logisticsProvider = logisticsProvider;
      return this;
    }

    public String getRecipientName() {
      return recipientName;
    }

    public ShipGoodsCommand setRecipientName(String recipientName) {
      this.recipientName = recipientName;
      return this;
    }

    public String getRecipientAddress() {
      return recipientAddress;
    }

    public ShipGoodsCommand setRecipientAddress(String recipientAddress) {
      this.recipientAddress = recipientAddress;
      return this;
    }
  }
}
