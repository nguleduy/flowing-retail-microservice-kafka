package com.example.order.domain;

import com.example.order.domain.adapter.FetchGoodsAdapter;
import com.example.order.domain.adapter.OrderCompletedAdapter;
import com.example.order.domain.adapter.RetrievePaymentAdapter;
import com.example.order.domain.adapter.ShipGoodsAdapter;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class OrderFlowDefinition {

  @Autowired
  private ProcessEngine engine;

  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  public void createFlow() {
    engine.getRepositoryService().createDeployment() //
            .addModelInstance("order.bpmn", Bpmn.createProcess("order").executable() //
                    .startEvent().message("OrderPlacedEvent")
                    .serviceTask().name("Retrieve payment").camundaDelegateExpression(exp(RetrievePaymentAdapter.class))
                    .serviceTask().name("Fetch goods").camundaDelegateExpression(exp(FetchGoodsAdapter.class))
                    .serviceTask().name("Ship goods").camundaDelegateExpression(exp(ShipGoodsAdapter.class))
                    .endEvent().camundaExecutionListenerClass("end", exp(OrderCompletedAdapter.class))
                    .done()
            ).deploy();
  }

  public String exp(Class delegateClass) {
    String[] beanNames = applicationContext.getBeanNamesForType(delegateClass);
    if (beanNames.length > 1) {
      throw new RuntimeException("More than one Spring bean found for type " + delegateClass);
    }
    return "#{" + beanNames[0] + "}";
  }
}
