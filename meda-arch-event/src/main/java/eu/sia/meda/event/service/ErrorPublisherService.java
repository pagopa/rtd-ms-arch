package eu.sia.meda.event.service;

public interface ErrorPublisherService {
    Boolean publishErrorEvent(byte[] payload, String errorDesc);
}
