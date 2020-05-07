package eu.sia.meda.layers.connector.query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dummy")
public class DummyController  {

    @GetMapping("criteria")
    public DummyCriteria returnCriteria(DummyCriteria criteria){
        return criteria;
    }
}
