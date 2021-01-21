package com.example.monitor.web;

import com.example.monitor.domain.PastEvent;
import com.example.monitor.port.persistence.LogRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MonitorRestController {

  @RequestMapping(path = "/event", method = RequestMethod.GET)
  public Map<String, List<PastEvent>> getAllEvents() {
    return LogRepository.instance.getAllPastEvents();
  }

  @RequestMapping(path = "/event/{traceId}", method = RequestMethod.GET)
  public List<PastEvent> getAllEvents(@PathVariable String traceId) {
    return LogRepository.instance.getAllPastEvents(traceId);
  }
}
