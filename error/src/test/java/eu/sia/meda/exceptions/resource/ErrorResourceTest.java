package eu.sia.meda.exceptions.resource;

import eu.sia.meda.exceptions.model.ErrorMessage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;

public class ErrorResourceTest {

  @Test
  public void test0()  throws Throwable  {
      ErrorResource errorResource0 = new ErrorResource();
      Map<String, List<ErrorMessage>> map0 = errorResource0.getReturnMessages();
      assertNull(map0);
  }

    @Test
    public void test(){
        ErrorResource resource = new ErrorResource();
        ErrorMessage message = new ErrorMessage();

        Assert.assertNull(resource.getReturnMessages());

        resource.setReturnMessages(Collections.singletonMap("testKey",Collections.singletonList(message)));

        Assert.assertNotNull(resource.getReturnMessages());
        Assert.assertFalse(resource.getReturnMessages().isEmpty());
        Assert.assertNotNull(resource.getReturnMessages().get("testKey"));
    }
}
