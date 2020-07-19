package com.appl.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.appl.controller.DateTime;
import com.appl.model.Van;
import com.appl.model.VehicleType;

public class VanTest {

	@Test
	public void testGetLateFee() {
		Van van = new Van("C_001",2019,"China","Model1",0,0,new VehicleType(15,new DateTime(5,7,2017)));
		DateTime endDate= new DateTime(7,8,2018);
		DateTime startDate= new DateTime(6,8,2018);
		assertEquals(299.0, van.getLateFee(endDate, startDate), 0.0f);
	}
}
