package com.appl.view;

import com.appl.controller.ThriftyRentSystem;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Menu extends ThriftyRentSystem{
	//public ThriftyRentSystem rentSystem;
	/**
	 * This the method called from main method
	 * this contains the menudriven interface to communicate with the user
	 */
    public void run() {

		while (true) {

			System.out.println("\n**** ThriftyRent SYSTEM MENU ****\n");
			System.out.println("Add vehicle:           	 	1");
			System.out.println("Rent vehicle:          	 	2");
			System.out.println("Return vehicle:        		3");
			System.out.println("Vehicle Maintenance:    	4");
			System.out.println("Complete Maintenance:   	5");
			System.out.println("Display All Vehicles:   	6");
			System.out.println("Display Available Vehicles:	7");
			System.out.println("Search by Year:           	8");
			System.out.println("Search by Made in:	        9");
			System.out.println("Filter by Year and Made in:	10");
			System.out.println("Total Income:           	11");
            System.out.println("Exit Program:           	12");

            System.out.println("Enter your choice:");
			Scanner sc= new Scanner(System.in);
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {
				case 1:
					super.add(sc); //Method used to add either cars or vans
					break;
				case 2:
					super.rent(sc); //Method used to rent either cars or vans
					break;
				case 3:
					super.returnVehicle(sc); //Method used to return a car after being rented
					break;
				case 4:
					super.vehicleMaintenance(sc); //Method used to set either car or van to maintenance
					break;
				case 5:
					super.completeMaintenance(sc);  //Method used to complete the maintenance
					break;
				case 6:
					super.getDetails();  //Method used to get the details of cars or vans if present
					break;
				case 7:
					super.getAvailableDetails();  //Method used to get the details of cars or vans if available
					break;
				case 8:
					super.getByYear(sc);  //Method used to get the details of cars or vans if present by year
					break;
				case 9:
					super.getByMadeIn(sc);  //Method used to get the details of cars or vans if present by made in
					break;
				case 10:
					super.getByFilter(sc); //Method used to get the details of cars or vans if present by year and made in
					return;
                case 11 :
                    super.getTotalIncome(sc);
                    break;
				case 12:
					sc.close(); //Closing the scanner if 12 is selected by the user
					return;

			}

		}
	}
}
