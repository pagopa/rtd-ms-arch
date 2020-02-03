package eu.sia.meda.event.service;

import org.apache.kafka.common.header.Headers;

public interface ErrorPublisherService {
    boolean publishErrorEvent(byte[] payload, Headers headers, String errorDesc);
    boolean publishErrorEvent(String topic, byte[] payload, Headers headers, String errorDesc);
}
