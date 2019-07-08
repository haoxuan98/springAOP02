package geizi.cbb;

import geizi.cbb.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class IOCTest {

    @Autowired
    private IAccountService iAccountService;

    @Test
    public void test() {
        iAccountService.transfer("cbb", "dandan", 11.22f);
    }
}
