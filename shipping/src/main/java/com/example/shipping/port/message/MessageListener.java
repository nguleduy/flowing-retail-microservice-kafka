package com.example.shipping.port.message;

import com.example.shipping.application.ShippingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@EnableBinding(Sink.class)
public class MessageListener {

  @Autowired
  private MessageSender messageSender;

  @Autowired
  private ShippingService shippingService;

  @StreamListener(target = Sink.INPUT,
          condition="payload.messageType.toString()=='ShipGoodsCommand'")
  @Transactional
  public void shipGoodsCommandReceived(String messageJson) throws Exception {
    Message<ShipGoodsCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<>(){});

    String shipmentId = shippingService.createShipment( //
            message.getPayload().getPickId(), //
            message.getPayload().getRecipientName(), //
            message.getPayload().getRecipientAddress(), //
            message.getPayload().getLogisticsProvider());

    System.out.println("Shipping to " + message.getPayload().getRecipientAddress());

    messageSender.send( //
            new Message<>( //
                    "GoodsShippedEvent", //
                    message.getTraceId(), //
                    new GoodsShippedEventPayload() //
                            .setRefId(message.getPayload().getRefId())
                            .setShipmentId(shipmentId)));
  }
}
