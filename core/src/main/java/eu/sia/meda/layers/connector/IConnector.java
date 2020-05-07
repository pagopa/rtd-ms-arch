package eu.sia.meda.layers.connector;

import eu.sia.meda.layers.connector.request.BaseConnectorRequest;
import eu.sia.meda.layers.connector.response.BaseConnectorResponse;

/**
 * The Interface IConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 */
public interface IConnector<INPUT extends BaseConnectorRequest, OUTPUT extends BaseConnectorResponse> {
}
