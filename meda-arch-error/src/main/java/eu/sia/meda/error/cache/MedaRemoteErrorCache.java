package eu.sia.meda.error.cache;

import eu.sia.meda.cache.BaseMedaCache;
import eu.sia.meda.error.condition.RemoteErrorManagerCondition;
import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.List;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class MedaRemoteErrorCache.
 */
@Service
@Conditional({RemoteErrorManagerCondition.class})
public class MedaRemoteErrorCache extends BaseMedaCache<String, List<ErrorMessage>> {
}
