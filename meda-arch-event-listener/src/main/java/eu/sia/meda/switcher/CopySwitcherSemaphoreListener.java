package eu.sia.meda.switcher;

import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.eventlistener.BaseEventListener;
import eu.sia.meda.eventlistener.EventContextHolder;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;

/**
 * The listener interface for receiving copySwitcherSemaphore events. The class
 * that is interested in processing a copySwitcherSemaphore event implements
 * this interface, and the object created with that class is registered with a
 * component using the component's <code>addCopySwitcherSemaphoreListener<code>
 * method. When the copySwitcherSemaphore event occurs, that object's
 * appropriate method is invoked.
 *
 * @see CopySwitcherSemaphoreEvent
 */
public class CopySwitcherSemaphoreListener extends BaseEventListener {

	/** The switcher configuration. */
	private ArchEventListenerConfigurationService.EventListenerConfiguration switcherConfiguration;

	/** The switcher registry. */
	@Autowired
	private CopySwitcherSemaphoreRegistry switcherRegistry;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Instantiates a new copy switcher semaphore listener.
	 *
	 * @param switcherConfiguration the switcher configuration
	 */
	public CopySwitcherSemaphoreListener(
			ArchEventListenerConfigurationService.EventListenerConfiguration switcherConfiguration) {
		this.switcherConfiguration = switcherConfiguration;
	}

	/**
	 * Inits the.
	 */
	protected void init() {
		if (this.switcherConfiguration == null) {
			throw new ExceptionInInitializerError();
		} else if (this.switcherConfiguration.getTopic() == null) {
			throw new ExceptionInInitializerError();
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded SwitcherListener: {}"),
						this.getClass().getSimpleName());
			}
			this.configure(this.switcherConfiguration);
		}
	}

	/**
	 * On received.
	 *
	 * @param payload the payload
	 * @param headers the headers
	 */
	public void onReceived(byte[] payload, Headers headers) {
		try {
			String payloadString = new String(payload);
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("Payload: {}"), payloadString);
			}
			CopySwitcherMessage semaphoreMessage = (CopySwitcherMessage) this.objectMapper.readValue(payload,
					CopySwitcherMessage.class);
			Long messageTimestamp = 0L;
			if (EventContextHolder.getRecord().headers().headers("TIMESTAMP").iterator().hasNext()) {
				byte[] headerTimestamp = (EventContextHolder.getRecord().headers().headers("TIMESTAMP")
						.iterator().next()).value();
				messageTimestamp = this.objectMapper.readValue(headerTimestamp, Long.class);
			} else {
				messageTimestamp = EventContextHolder.getRecord().timestamp();
			}

			this.switcherRegistry.update(semaphoreMessage, messageTimestamp);
		} catch (Exception var7) {
			if (this.logger.isErrorEnabled()) {
				this.logger.error(LoggerUtils.formatArchRow("Error in Kafka message parsing"), var7);
				this.logger.error(LoggerUtils.formatArchRow("Copy Switcher NOT updated"));
			}
		}

	}
}
