package com.example.order.application.adapter;

import com.example.order.application.adapter.base.CommandPubEventSubAdapter;
import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.order.port.Message;
import com.example.order.port.MessageSender;
import com.example.order.repository.OrderRepository;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    messageSender.send(new Message<FetchGoodsCommand>( //
            "FetchGoodsCommand", //
            new FetchGoodsCommand() //
                    .setRefId(order.getId()) //
                    .setItems(order.getItems()), //
            traceId));
  }

  public static class FetchGoodsCommand {
    private String refId;
    private String reason = "CustomerOrder";
    private List<OrderItem> items = new ArrayList<>();

    public String getRefId() {
      return refId;
    }

    public FetchGoodsCommand setRefId(String refId) {
      this.refId = refId;
      return this;
    }

    public String getReason() {
      return reason;
    }

    public FetchGoodsCommand setReason(String reason) {
      this.reason = reason;
      return this;
    }

    public List<OrderItem> getItems() {
      return items;
    }

    public FetchGoodsCommand setItems(List<OrderItem> items) {
      this.items = items;
      return this;
    }

  }
}
