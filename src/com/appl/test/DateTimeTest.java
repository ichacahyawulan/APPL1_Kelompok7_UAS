package com.appl.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.appl.controller.DateTime;

public class DateTimeTest {

	@Test
	public void testDiffDays() {
		DateTime endDate= new DateTime(10,8,2018);
		DateTime startDate= new DateTime(6,8,2018);
		assertEquals(4, DateTime.diffDays(endDate, startDate));
	}

}
