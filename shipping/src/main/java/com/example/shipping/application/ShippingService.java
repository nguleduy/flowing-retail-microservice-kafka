package com.example.shipping.application;

import java.util.UUID;

public class ShippingService {

  public static ShippingService instance = new ShippingService();

  /**
   *
   * @param pickId - required to identify the pile of goods to be packed in the parcel
   * @param recipientName
   * @param recipientAddress the shipment is sent to
   * @param logisticsProvider delivering the shipment (e.g. DHL, UPS, ...)
   * @return shipment id created (also printed on the label of the parcel)
   */
  public String createShipment(String pickId, String recipientName, String recipientAddress, String logisticsProvider) {
    return UUID.randomUUID().toString();
  }
}
