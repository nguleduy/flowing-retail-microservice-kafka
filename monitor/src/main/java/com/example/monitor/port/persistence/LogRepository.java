package com.example.monitor.port.persistence;

import com.example.monitor.domain.PastEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogRepository {

  public static LogRepository instance = new LogRepository();

  private Map<String, List<PastEvent>> events = new HashMap<>();

  public Map<String, List<PastEvent>> getAllPastEvents() {
    return events;
  }

  public List<PastEvent> getAllPastEvents(String transactionId) {
    return events.get(transactionId);
  }

  public void addEvent(PastEvent pastEvent) {
    if (!events.containsKey(pastEvent.getTransactionId())) {
      events.put(pastEvent.getTransactionId(), new ArrayList<>());
    }
    events.get(pastEvent.getTransactionId()).add(pastEvent);
  }
}
