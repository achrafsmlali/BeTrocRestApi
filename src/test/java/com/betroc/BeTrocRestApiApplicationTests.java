package com.betroc;

import com.betroc.controller.AuthControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@Suite.SuiteClasses({AuthControllerTest.class})
@TestPropertySource("classpath:/com/betroc/application.properties")
public class BeTrocRestApiApplicationTests {

	@Test
	public void contextLoads() {
	}
}