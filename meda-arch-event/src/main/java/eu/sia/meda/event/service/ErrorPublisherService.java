package eu.sia.meda.event.service;

public interface ErrorPublisherService {
    boolean publishErrorEvent(byte[] payload, String errorDesc);
}
