package com.javaTest.appRunner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.javaTask.appRunner.Runner;
import com.javaTask.entity.Website;

class RunnerTest {

	@Test
	void testCollectData() {
		Website test = null;
		test = Runner.collectData("https://prom.ua/p895411456-igrovoj-noutbu-omen.html");
		
		assertTrue(test != null);
	}

}
