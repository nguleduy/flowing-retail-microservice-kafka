package com.example.inventory.port.message;

import com.example.inventory.application.InventoryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private MessageSender messageSender;

  @Autowired
  private InventoryService inventoryService;

  @StreamListener(target = Sink.INPUT,
          condition = "payload.messageType.toString()=='FetchGoodsCommand'")
  @Transactional
  public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<FetchGoodsCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<FetchGoodsCommandPayload>>() {
    });

    FetchGoodsCommandPayload fetchGoodsCommand = message.getPayload();
    String pickId = inventoryService.pickItems(//
            fetchGoodsCommand.getItems(), fetchGoodsCommand.getReason(), fetchGoodsCommand.getRefId());

    messageSender.send( //
            new Message<GoodsFetchedEventPayload>( //
                    "GoodsFetchedEvent", //
                    message.getTraceId(), //
                    new GoodsFetchedEventPayload() //
                            .setRefId(fetchGoodsCommand.getRefId())
                            .setPickId(pickId)));
  }
}
