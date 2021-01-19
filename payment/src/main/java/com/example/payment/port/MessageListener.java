package com.example.payment.port;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
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
  private ProcessEngine camunda;

  @Autowired
  private MessageSender messageSender;

  @StreamListener(target = Sink.INPUT,
          condition = "payload.messageType.toString()=='RetrievePaymentCommand'")
  @Transactional
  public void retrievePaymentCommandReceived(String messageJson) throws JsonParseException, JsonMappingException, IOException {
    Message<RetrievePaymentCommandPayload> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<RetrievePaymentCommandPayload>>() {
    });
    RetrievePaymentCommandPayload retrievePaymentCommand = message.getPayload();
    System.out.println(retrievePaymentCommand);

    // and directly send response

    messageSender.send( //
            new Message<PaymentReceivedEventPayload>( //
                    "PaymentReceivedEvent", //
                    new PaymentReceivedEventPayload() //
                            .setRefId(retrievePaymentCommand.getRefId()), //
                    message.getTraceId()));
  }
}
