package eu.sia.meda.event.service;

public interface ErrorPublisherService {
    void publishErrorEvent(byte[] payload, String errorDesc);
}
