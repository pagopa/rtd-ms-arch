package eu.sia.meda.audit.connector.rest.transformer;

import eu.sia.meda.audit.dto.TraceGenDto;
import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.tracing.config.condition.AuditRealCondition;
import java.util.HashMap;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * The Class AuditRestRequestTransformer.
 */
@Service
@Conditional({AuditRealCondition.class})
public class AuditRestRequestTransformer implements IRestRequestTransformer<TraceGen, TraceGenDto> {
   
   /**
    * Transform.
    *
    * @param om the om
    * @param args the args
    * @return the rest connector request
    */
   public RestConnectorRequest<TraceGenDto> transform(TraceGen om, Object... args) {
      HttpHeaders headers = new HttpHeaders();
      TraceGenDto dto = new TraceGenDto();
      dto.setPrefixMessage(om.getPrefixMessage());
      dto.setReturnCode(om.getReturnCode());
      dto.setReturnDesc(om.getReturnDesc());
      dto.setInputXmlMessage(om.getInputXmlMessage());
      dto.setOutputXmlMessage(om.getOutputXmlMessage());
      dto.setCodMultichannelid(om.getCodMultichannelid());
      dto.setCodAbi(om.getCodAbi());
      dto.setCodBt(om.getCodBt());
      dto.setCodChannel(om.getCodChannel());
      dto.setCodService(om.getCodService());
      dto.setCodSmash(om.getCodSmash());
      dto.setDataInoltro(om.getDataInoltro());
      dto.setDataRicezione(om.getDataRicezione());
      return new RestConnectorRequest(headers, dto, new HashMap(), new HashMap(), HttpMethod.POST);
   }
}
