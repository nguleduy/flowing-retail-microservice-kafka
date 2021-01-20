package com.example.order.port.message;

import com.example.order.domain.Order;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.spin.plugin.variable.SpinValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@EnableBinding(Sink.class)
public class MessageListener {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private ProcessEngine camunda;

  /**
   * Handles incoming OrderPlacedEvents.
   */
  @StreamListener(target = Sink.INPUT,
          condition = "payload.messageType.toString()=='OrderPlacedEvent'")
  @Transactional
  public void orderPlacedReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<Order> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<Order>>() {
    });
    Order order = message.getPayload();
    System.out.println(order);

    // persist domain entity
    repository.persistOrder(order);

    // and kick of a new flow instance
    camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
            .processInstanceBusinessKey(message.getTraceId())
            .setVariable("orderId", order.getId())
            .correlateWithResult();
  }

  /**
   * Very generic listener for simplicity. It takes all events and checks, if a
   * flow instance is interested. If yes, they are correlated,
   * otherwise they are just discarded.
   * <p>
   * It might make more sense to handle each and every message type individually.
   *
   * @param messageJson
   */
  @StreamListener(target = Sink.INPUT,
          condition = "payload.messageType.toString().endsWith('Event')")
  @Transactional
  public void messageReceived(String messageJson) throws Exception {
    Message<JsonNode> message = new ObjectMapper().readValue( //
            messageJson, //
            new TypeReference<Message<JsonNode>>() {
            });
    System.out.println(message);

    long correlatingInstances = camunda.getRuntimeService().createExecutionQuery() //
            .messageEventSubscriptionName(message.getMessageType()) //
            .processInstanceBusinessKey(message.getTraceId()) //
            .count();

    if (correlatingInstances == 1) {
      camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
              .processInstanceBusinessKey(message.getTraceId())
              .setVariable(//
                      "PAYLOAD_" + message.getMessageType(), //
                      SpinValues.jsonValue(message.getPayload().toString()).create())//
              .correlateWithResult();
    } else {
      // ignoring event, not interested
      System.out.println("Order context ignores event '" + message.getMessageType() + "'");
    }

  }

//  @StreamListener(target = Sink.INPUT)
//  public void handleDefaultEvent(@Payload String payload) {
//    System.out.println("Received payload: " + payload);
//  }
}
