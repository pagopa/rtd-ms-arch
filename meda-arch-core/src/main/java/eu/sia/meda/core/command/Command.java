package eu.sia.meda.core.command;

public interface Command<R> {
    R execute() throws Exception;
}
