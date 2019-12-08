package eu.sia.meda.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * The Class BeanOverriding.
 */
public class BeanOverriding implements EnvironmentPostProcessor {

	/**
	 * Post-process the given {@code environment}.
	 * 
	 * @param environment the environment to post-process
	 * @param application the application to which the environment belongs
	 */
	public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
		application.setAllowBeanDefinitionOverriding(true);
	}
}
