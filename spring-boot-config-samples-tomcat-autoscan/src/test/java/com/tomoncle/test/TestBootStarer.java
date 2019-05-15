package com.tomoncle.test;

import com.tomoncle.app.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestBootStarer {

    private static Logger logger = LoggerFactory.getLogger(TestBootStarer.class);
    @Test
    public void testRun(){
        logger.info("Exit.");
    }
}
