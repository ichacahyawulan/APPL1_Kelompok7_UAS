package com.appl.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.appl.controller.DateTime;
import com.appl.model.Car;
import com.appl.model.VehicleType;

public class CarTest {

	@Test
	public void testGetLateFee() {
		Car car = new Car("C_001",2019,"China","Model1",0,0,new VehicleType(4));
		DateTime endDate= new DateTime(7,8,2018);
		DateTime startDate= new DateTime(6,8,2018);
		assertEquals(97.5, car.getLateFee(endDate, startDate), 0.0f);
	}

}
