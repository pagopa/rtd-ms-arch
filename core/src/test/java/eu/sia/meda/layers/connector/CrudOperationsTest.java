package eu.sia.meda.layers.connector;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;

public class CrudOperationsTest {

    @Test
    public void test(){
        Pageable pageable = PageRequest.of(0,2000, Sort.unsorted());
        Page<String> result = CrudOperations.paginateResult(pageable, Collections.singletonList("testString"), String.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasContent());
        Assert.assertEquals("testString",result.getContent().get(0));

        result = CrudOperations.paginateResult(null, Collections.singletonList("testString"), String.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasContent());
        Assert.assertEquals("testString",result.getContent().get(0));

        pageable = PageRequest.of(0,2000, Sort.by(Sort.Direction.DESC,"hash"));
        result = CrudOperations.paginateResult(pageable, Collections.singletonList("testString"), String.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasContent());
        Assert.assertEquals("testString",result.getContent().get(0));

        pageable = PageRequest.of(0,2000, Sort.by(Sort.Direction.ASC,"hash"));
        result = CrudOperations.paginateResult(pageable, Collections.singletonList("testString"), String.class);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasContent());
        Assert.assertEquals("testString",result.getContent().get(0));
    }
}
