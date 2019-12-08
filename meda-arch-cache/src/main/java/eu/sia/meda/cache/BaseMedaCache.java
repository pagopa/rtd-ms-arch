package eu.sia.meda.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.expiry.TouchedExpiryPolicy;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.spi.CachingProvider;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;

/**
 * The Class BaseMedaCache.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BaseMedaCache<K, V> implements Cache<K, V> {

	/** The logger. */
	protected final Logger logger = LoggerUtils.getLogger(this.getClass());

	/** The Constant EH_CACHE_PROVIDER. */
	private static final String EH_CACHE_PROVIDER = "org.ehcache.jsr107.EhcacheCachingProvider";

	/** The Constant HAZELCAST_PROVIDER. */
	private static final String HAZELCAST_PROVIDER = "com.hazelcast.client.cache.impl.HazelcastClientCachingProvider";

	/** The Constant INTERCEPTED_CALL. */
	private static final String INTERCEPTED_CALL = "Intercepted call to the MedaCacheManager";

	/** The properties manager. */
	@Autowired
	private PropertiesManager propertiesManager;

	/** The key class. */
	private Class<K> keyClass;

	/** The value class. */
	private Class<V> valueClass;

	/** The name. */
	private String name;

	/** The expiry policy name. */
	private String expiryPolicyName;

	/** The duration amount. */
	private long durationAmount;

	/** The duration unit. */
	private TimeUnit durationUnit;

	/** The mocked. */
	private boolean mocked;

	/** The cache. */
	private Cache<K, V> cache;

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init() {
		Type[] parameterizedTypes = ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments();
		if (parameterizedTypes[0] instanceof ParameterizedType) {
			this.keyClass = (Class) ((ParameterizedType) parameterizedTypes[0]).getRawType();
		} else if (parameterizedTypes[0] instanceof TypeVariable) {
			this.keyClass = (Class) ((TypeVariable) parameterizedTypes[0]).getBounds()[0]; // TODO check
		} else {
			this.keyClass = (Class) parameterizedTypes[0];
		}

		if (parameterizedTypes[1] instanceof ParameterizedType) {
			this.valueClass = (Class) ((ParameterizedType) parameterizedTypes[1]).getRawType();
		} else if (parameterizedTypes[1] instanceof TypeVariable) {
			this.valueClass = (Class) ((TypeVariable) parameterizedTypes[1]).getBounds()[0]; // TODO check
		} else {
			this.valueClass = (Class) parameterizedTypes[1];
		}

		this.name = this.getClass().getSimpleName();
		this.expiryPolicyName = this.propertiesManager.get(String.format("cache.items.%s.expiryPolicy", this.name));
		this.durationAmount = new Long(
				this.propertiesManager.get(String.format("cache.items.%s.durationAmount", this.name)));
		this.durationUnit = TimeUnit
				.valueOf(this.propertiesManager.get(String.format("cache.items.%s.durationUnit", this.name)));
		this.mocked = new Boolean(this.propertiesManager.get(String.format("cache.items.%s.mocked", this.name)));
		this.logger.debug(
				"Creating cache with name={}, expireyPolicy={}, durationAmount={}, durationUnit={}, mocked={}",
				new Object[] { this.name, this.expiryPolicyName, this.durationAmount, this.durationUnit, this.mocked });
		this.cache = this.createCache();
		this.logger.debug("Cache {} {}", this.name, this.mocked ? "mocked" : "configured");
	}

	/**
	 * Creates the cache.
	 *
	 * @return the cache
	 */
	private Cache createCache() {
		Duration duration = new Duration(this.durationUnit, this.durationAmount);
		MutableConfiguration mutableConfiguration = (new MutableConfiguration()).setTypes(Object.class, Object.class)
				.setStoreByValue(false).setStatisticsEnabled(true);
		String var3 = this.expiryPolicyName;
		byte var4 = -1;
		switch (var3.hashCode()) {
		case -1973178104:
			if (var3.equals("AccessedExpiryPolicy")) {
				var4 = 1;
			}
			break;
		case -137695550:
			if (var3.equals("EternalExpiryPolicy")) {
				var4 = 4;
			}
			break;
		case -113667229:
			if (var3.equals("TouchedExpiryPolicy")) {
				var4 = 3;
			}
			break;
		case 119937837:
			if (var3.equals("CreatedExpiryPolicy")) {
				var4 = 0;
			}
			break;
		case 439697870:
			if (var3.equals("ModifiedExpiryPolicy")) {
				var4 = 2;
			}
		}

		switch (var4) {
		case 0:
			mutableConfiguration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new CreatedExpiryPolicy(duration)));
			break;
		case 1:
			mutableConfiguration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new AccessedExpiryPolicy(duration)));
			break;
		case 2:
			mutableConfiguration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new ModifiedExpiryPolicy(duration)));
			break;
		case 3:
			mutableConfiguration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new TouchedExpiryPolicy(duration)));
			break;
		case 4:
			mutableConfiguration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new EternalExpiryPolicy()));
			break;
		default:
			throw new IllegalArgumentException("Expiry Policy not found");
		}

		CachingProvider cachingProvider = Caching
				.getCachingProvider(this.mocked ? EH_CACHE_PROVIDER : HAZELCAST_PROVIDER);
		CacheManager cacheManager = cachingProvider.getCacheManager();
		return cacheManager.createCache(this.name, mutableConfiguration);
	}

	/**
	 * Gets the.
	 *
	 * @param k the k
	 * @return the v
	 */
	public V get(K k) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.get(k);
	}

	/**
	 * Gets the all.
	 *
	 * @param set the set
	 * @return the all
	 */
	public Map<K, V> getAll(Set<? extends K> set) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getAll(set);
	}

	/**
	 * Contains key.
	 *
	 * @param k the k
	 * @return true, if successful
	 */
	public boolean containsKey(K k) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.containsKey(k);
	}

	/**
	 * Load all.
	 *
	 * @param set                the set
	 * @param b                  the b
	 * @param completionListener the completion listener
	 */
	public void loadAll(Set<? extends K> set, boolean b, CompletionListener completionListener) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.loadAll(set, b, completionListener);
	}

	/**
	 * Put.
	 *
	 * @param k the k
	 * @param v the v
	 */
	public void put(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.put(k, v);
	}

	/**
	 * Associates the specified value with the specified key in this cache,returning
	 * an existing value if one existed.
	 * 
	 * @param k the key
	 * @param v the value
	 * @return the value associated with the key at the start of the operation or
	 *         null if none was associated
	 */
	public V getAndPut(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getAndPut(k, v);
	}

	/**
	 * Put all.
	 *
	 * @param map the map
	 */
	public void putAll(Map<? extends K, ? extends V> map) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.putAll(map);
	}

	/**
	 * Put if absent.
	 *
	 * @param k the k
	 * @param v the v
	 * @return true, if successful
	 */
	public boolean putIfAbsent(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.putIfAbsent(k, v);
	}

	/**
	 * Removes the.
	 *
	 * @param k the k
	 * @return true, if successful
	 */
	public boolean remove(K k) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.remove(k);
	}

	/**
	 * Removes the.
	 *
	 * @param k the k
	 * @param v the v
	 * @return true, if successful
	 */
	public boolean remove(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.remove(k, v);
	}

	/**
	 * Gets the and remove.
	 *
	 * @param k the k
	 * @return the and remove
	 */
	public V getAndRemove(K k) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getAndRemove(k);
	}

	/**
	 * Replace.
	 *
	 * @param k  the k
	 * @param v  the v
	 * @param v1 the v 1
	 * @return true, if successful
	 */
	public boolean replace(K k, V v, V v1) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.replace(k, v, v1);
	}

	/**
	 * Replace.
	 *
	 * @param k the k
	 * @param v the v
	 * @return true, if successful
	 */
	public boolean replace(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.replace(k, v);
	}

	/**
	 * Gets the and replace.
	 *
	 * @param k the k
	 * @param v the v
	 * @return the and replace
	 */
	public V getAndReplace(K k, V v) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getAndReplace(k, v);
	}

	/**
	 * Removes the all.
	 *
	 * @param set the set
	 */
	public void removeAll(Set<? extends K> set) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.removeAll(set);
	}

	/**
	 * Removes the all.
	 */
	public void removeAll() {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.removeAll();
	}

	/**
	 * Clear.
	 */
	public void clear() {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.clear();
	}

	/**
	 * Gets the configuration.
	 *
	 * @param <C>    the generic type
	 * @param aClass the a class
	 * @return the configuration
	 */
	public <C extends Configuration<K, V>> C getConfiguration(Class<C> aClass) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getConfiguration(aClass);
	}

	/**
	 * Invoke.
	 *
	 * @param <T>            the generic type
	 * @param k              the k
	 * @param entryProcessor the entry processor
	 * @param objects        the objects
	 * @return the t
	 * @throws EntryProcessorException the entry processor exception
	 */
	public <T> T invoke(K k, EntryProcessor<K, V, T> entryProcessor, Object... objects) {
		return this.cache.invoke(k, entryProcessor, objects);
	}

	/**
	 * Invoke all.
	 *
	 * @param <T>            the generic type
	 * @param set            the set
	 * @param entryProcessor the entry processor
	 * @param objects        the objects
	 * @return the map
	 */
	public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> set, EntryProcessor<K, V, T> entryProcessor,
			Object... objects) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.invokeAll(set, entryProcessor, objects);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getName();
	}

	/**
	 * Gets the cache manager.
	 *
	 * @return the cache manager
	 */
	public CacheManager getCacheManager() {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.getCacheManager();
	}

	/**
	 * Close.
	 */
	public void close() {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.close();
	}

	/**
	 * Checks if is closed.
	 *
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.isClosed();
	}

	/**
	 * Unwrap.
	 *
	 * @param <T>    the generic type
	 * @param aClass the a class
	 * @return the t
	 */
	public <T> T unwrap(Class<T> aClass) {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.unwrap(aClass);
	}

	/**
	 * Register cache entry listener.
	 *
	 * @param cacheEntryListenerConfiguration the cache entry listener configuration
	 */
	public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.registerCacheEntryListener(cacheEntryListenerConfiguration);
	}

	/**
	 * Deregister cache entry listener.
	 *
	 * @param cacheEntryListenerConfiguration the cache entry listener configuration
	 */
	public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		this.logger.debug(INTERCEPTED_CALL);
		this.cache.deregisterCacheEntryListener(cacheEntryListenerConfiguration);
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public Iterator<Entry<K, V>> iterator() {
		this.logger.debug(INTERCEPTED_CALL);
		return this.cache.iterator();
	}
}
