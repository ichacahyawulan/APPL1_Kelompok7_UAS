package com.appl.controller;

import com.appl.model.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

/**
 * This the main class used to get details from the user and display the menu driven options
 * This class acts as the businedd layer in our applications
 */

public class  ThriftyRentSystem{
	//cars and vans are two collections used to store cars of type Cars and vans of type Vans respectively
    public Car cars[] = new Car[50];
    public Van vans[] = new Van[50];
    public double totalIncome;

	public static DateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //Basic format expected from the User
	
	/**
	 * Used to add either cars or vans to the list
	 * @param //Scanner variable
	 * @return adds either car or van if the details are correct
	 */
    public void add(Scanner scan)
    {
		String vehicleID="";
                int RentByCust = 0;
		
		System.out.print("Vehicle Type(Van or Car): ");
		String vehicleType = scan.nextLine();
		while (!(vehicleType.equalsIgnoreCase("car") ||vehicleType.equalsIgnoreCase("van")))
		{
			System.out.print("Please enter either van or car: ");
			vehicleType = scan.nextLine();
		}
		System.out.print("Year: ");
		int year = Integer.parseInt(scan.nextLine());
		while (year<0 || year >2020)
		{
			System.out.println("Please enter a valid year");
			year=Integer.parseInt(scan.nextLine());
		}

		System.out.print("Made in: ");
		String make = scan.nextLine();
		System.out.print("Model: ");
		String model = scan.nextLine();
                
		if(vehicleType.equals("car")) {
			addCar(scan, vehicleID, year, make,  model, RentByCust);
		}
		if(vehicleType.equalsIgnoreCase("van"))
		{
			addVan(scan, vehicleID, year, make,  model, RentByCust);
		}
    }
    
    public void addCar(Scanner scan, String vehicleID, int year, String make, String model, int RentByCust) {
    	int i = 0, seats = 0;
                    System.out.print("Vehicle ID: C_");
                    vehicleID = scan.nextLine();
                    vehicleID = "C_"+vehicleID;        
		if(this.cars[0]!=null && vehicleID.contains("C_"))
		{
			for(i=0;this.cars[i]!=null;i++){
				if ((this.cars[i].getVehicleId()).equals(vehicleID))
				{
					System.out.println("ID Already used, Please add a new vehicle");
					return;
				}
			}
		}
		System.out.print("Number of seats: ");
		seats = Integer.parseInt(scan.nextLine());
		while ((seats != 4 && seats != 7)) {
			System.out.println("Please enter seats as either 4 or 7");
			seats = Integer.parseInt(scan.nextLine());
		}
		if(i<50){
			Vehicle newVehicle= new Car(vehicleID,year,make,model,RentByCust,0,new VehicleType(seats));
			this.cars[i]= (Car) newVehicle;
			System.out.println(newVehicle.toString());
		}
    }
    
    public void addVan(Scanner scan, String vehicleID, int year, String make, String model, int RentByCust) {
    	int i = 0, seats = 15;
    	String maintenanceDate=null;
    	System.out.print("Vehicle ID: V_");
		vehicleID = scan.nextLine();
		vehicleID="V_"+vehicleID;
		if(this.vans[0]!=null && vehicleID.contains("V_"))
		{
			for(i=0;this.vans[i]!=null;i++){
				if ((this.vans[i].getVehicleId()).equals(vehicleID))
				{
					System.out.println("ID Already used, Please add a new vehicle");
					return;
				}
			}
		}
		System.out.print("Last Maintenance (dd/mm/yyyy): ");
		maintenanceDate = scan.next();
		format.setLenient(false);
		while(maintenanceDate.trim().length() != ((SimpleDateFormat) format).toPattern().length())
		{
			System.out.println("Please enter a valid date in the format dd/mm/yyyy: ");
			maintenanceDate = scan.nextLine();
		}
		String dateSplit[] = maintenanceDate.split("/");
		DateTime Lastmain= new DateTime(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),Integer.parseInt(dateSplit[2]));
		if(i<50){
			Vehicle newVehicle= new Van(vehicleID,year,make,model,RentByCust,0,new VehicleType(seats,Lastmain));
			this.vans[i]= (Van) newVehicle;
			System.out.println(newVehicle.toString());
		}
    }


	/**
	 * Used to rent either available car or available van
	 * @param //Scanner variable
	 * @return Rents a car or van if the details are correct
	 */
	public void rent(Scanner sc)
	{
            int RentByCust = 0;
		System.out.print("Vehicle id: ");
		String id = sc.nextLine();
		String type="";
		
		if(id.contains("C_")) {
			rentCar(sc, id, type);
			type = "car";
		}
		
		if(id.contains("V_")) {
			rentVan(sc, id, type);
			type = "van";
		}

		if(!(id.contains("V_") || id.contains("C_")))
		{
			System.out.println("Please Enter a Valid ID either starting from 'V_' or 'C_'.");
			return;
		}

		System.out.print("Customer ID: ");
		String cusId = sc.next();
		System.out.print("Rent date( dd/mm/yyyy): ");
		String date = sc.next();
			format.setLenient(false);
			while(date.trim().length() != ((SimpleDateFormat) format).toPattern().length())
			{
				System.out.println("Please enter a valid date in the format dd/mm/yyyy: ");
				date = sc.nextLine();
			}
		String dates[] = date.split("/");
		DateTime rentDate= new DateTime(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
		System.out.print("How many days?: ");
		int days= sc.nextInt();
		if(type.equals("car")) {
			for(int i=0;this.cars[0]!=null;i++){
	                if ((this.cars[i].getVehicleId()).equals(id))
	                {
					   if(this.cars[i].rent(cusId,rentDate,days))
					   break;
                                           if ((this.cars[i].getVehicleId()).equals(id)) {
                                               this.addCar(sc, cusId, i, date, date, RentByCust);
                                               RentByCust++;
                            
                        }
				       else{
						   System.out.println("Vehicle "+id+" could not be rented.");
						   return;
					   }
	                }
                        
	            }
			System.out.println("Vehicle "+id+" is now rented by customer "+cusId);
		}
		
		if(type.equals("van")) {
			for(int i=0;this.vans[i]!=null;i++){
	                if ((this.vans[i].getVehicleId()).equals(id))
	                {
					   if(this.vans[i].rent(cusId,rentDate,days))
					   break;
				       else{
						   System.out.println("Vehicle "+id+" could not be rented");
						   return;
					   }
	                }
	            }
			System.out.println("Vehicle "+id+" is now rented by customer "+cusId);
		}

	}
	
	public void rentCar(Scanner sc, String id, String type) {
		if(this.cars[0]!=null) {
			boolean flag =false;
            for(int i=0;this.cars[i]!=null;i++){
                if ((this.cars[i].getVehicleId()).equals(id)) {
					if(this.cars[i].getVehicleStatus()!=0) {
						System.out.println("The car with ID : "+id+" is already either rented or under maintenance, please choose another car.");
						return;
					}

				   type="car";
                   flag=true;
				   break;
                }
            }
			if(!flag) {
				System.out.println("ID is incorrect, please try again!");
				return;
			}
        } else {
        	System.out.println("There are no cars currently at the moment.");
			return;
        }
	}
	
	public void rentVan(Scanner sc, String id, String type) {
		if(this.vans[0]!=null) {
			boolean flag =false;
            for(int i=0;this.vans[i]!=null;i++){
                if ((this.vans[i].getVehicleId()).equals(id)) {
					if(this.vans[0].getVehicleStatus()!=0) {
						System.out.println("The van with ID : "+id+" is already either rented or under maintenance. \nPlease choose another van.");
						return;
					}
					type="van";
                   flag=true;
				   break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect, please try again!");
				return;
			}
        } else {
        	System.out.println("There are no vans currently at the moment.");
			return;
        }
	}

	/**
	 * Used to return a rented car or van
	 * @param //Scanner variable
	 * @return prints the details of the car along with rental fee and charges if it is returned late
	 */
	public void returnVehicle(Scanner sc)
	{
		System.out.print("VehicleId: ");
		String id = sc.next();
		
		if(id.contains("C_")) {
			returnCar(sc, id);
		}
		
		if(id.contains("V_")) {
			returnVan(sc, id);
		}
	}
	
	public void returnCar(Scanner sc, String id) {
		if(this.cars[0]!=null) {
			boolean flag =false;
            for(int i=0;this.cars[i]!=null;i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {
				   System.out.print("Return date( dd/mm/yyyy): ");
				   String date = sc.next();
				   String dates[] = date.split("/");
				   DateTime returnDate= new DateTime(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
				   if(this.cars[i].returnVehicle(returnDate)) {
                                       //total
                                        totalIncome = totalIncome + this.cars[i].getRecords()[this.cars[i].getLastElementIndex()].getRentalFee() + this.cars[i].getRecords()[this.cars[i].getLastElementIndex()].getLateFee() ;
					   
                                        System.out.println(this.cars[i].getRecords()[this.cars[i].getLastElementIndex()].getDetails());
				   }
				   else {
					   System.out.println("Vehicle cannot be returned as it may have been never rented");
					   return;
				   }
                   flag=true;
				   break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect");
				return;
			}
        } else {
        	System.out.println("There are no cars, please add cars.");
			return;
        }
	}
	
	public void returnVan(Scanner sc, String id) {
		if(this.vans[0]!=null) {
			boolean flag =false;
            for(int i=0;this.vans[i]!=null;i++){
                if ((this.vans[i].getVehicleId()).equals(id)) {
					System.out.print("Return date( dd/mm/yyyy): ");
					String date = sc.next();
					String dates[] = date.split("/");
					DateTime returndate= new DateTime(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
					if(this.vans[i].returnVehicle(returndate)) {
					   //total
                       totalIncome = totalIncome + this.vans[i].getRecords()[this.vans[i].getLastElementIndex()].getRentalFee() + totalIncome + this.vans[i].getRecords()[this.vans[i].getLastElementIndex()].getLateFee();
                       System.out.println(this.vans[i].getRecords()[this.vans[i].getLastElementIndex()].getDetails());
				    }
				    else {
					   System.out.println("Vehicle cannot be returned");
					   return;
				    }
					flag=true;
					break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect");
				return;
			}
        } else {
        	System.out.println("There are no vans, please add cars.");
			return;
        }
	}

	/**
	 * Method used to set either car or van to maintenance
	 * @param //Scanner variable
	 * @return prints appropriate message if sent for maintenance
	 */
	
	public void vehicleMaintenance(Scanner sc){
		System.out.print("Vehicle id: ");
		String id = sc.next();
		
		if(id.contains("C_")) {
			carMaintenance(sc, id);
		}
		
		if(id.contains("V_")) {
			vanMaintenance(sc, id);
		}	
	}
	
	public void carMaintenance(Scanner sc, String id) {
		if(this.cars[0]!=null) {
			boolean flag =false;
            for(int i=0;this.cars[i]!=null;i++){
                if ((this.cars[i].getVehicleId()).equals(id)) {
				   if(this.cars[i].performMaintenance()) {
					   System.out.println("Vehicle "+id+" is now under maintenance");
				   } else {
					   System.out.println("Vehicle "+id+" could not be sent for maintenance");
					   return;
				   }
                   flag=true;
				   break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect");
				return;
			}
        } else {
        	System.out.println("There are no cars, please add cars.");
			return;
        }
	}
	
	public void vanMaintenance(Scanner sc, String id) {
		if(this.vans[0]!=null)
        {
			boolean flag =false;
            for(int i=0;this.vans[i]!=null;i++){
                if ((this.vans[i].getVehicleId()).equals(id))
                {
					if(this.vans[i].performMaintenance()) {
						System.out.println("Vehicle "+id+" is now under maintenance");
					} else {
					   System.out.println("Vehicle "+id+" could not be sent for maintenance");
					   return;
					}
					flag=true;
					break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect");
				return;
			}
        } else {
        	System.out.println("There are no vans, please add cars.");
			return;
        }
	}

	/**
	 * Method used to complete maintenance of either car or van
	 * @param //Scanner variable
	 * @return prints appropriate message after completing maintenance
	 */
	public void completeMaintenance(Scanner sc)
	{
		System.out.print("Enter vehicle ID: ");
		String id = sc.next();
		
		if(id.contains("C_")) {
			carCompleteMaintenance(sc, id);
		}
		
		if(id.contains("V_")) {
			vanCompleteMaintenance(sc, id);
		}				
	}
	
	public void carCompleteMaintenance(Scanner sc, String id) {
		if(this.cars[0]!=null) {
			boolean flag =false;
            for(int i=0;this.cars[i]!=null;i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {
				    System.out.print("Maintenance completion date (dd/mm/yyyy): ");
				    String date = sc.next();
				   if(this.cars[i].completeMaintenance()) {
					   System.out.println("Vehicle "+id+" has all maintenance completed and ready for rent");
				   } else {
					   System.out.println("Vehicle "+id+" could not complete maintenance");
					   return;
				   }
                   flag=true;
				   break;
                }
            }
			if(!flag) {
				System.out.println("ID is incorrect, Please try again");
				return;
			}
        } else {
        	System.out.println("There are no cars, please add cars.");
			return;
        }
	}
	
	public void vanCompleteMaintenance(Scanner sc, String id) {
		if(this.vans[0]!=null && id.contains("V_")) {
			boolean flag =false;
            for(int i=0;this.vans[i]!=null;i++){
                if ((this.vans[i].getVehicleId()).equals(id)) {
					System.out.print("Maintenance completion date (dd/mm/yyyy) :");
				    String date = sc.next();
					String dates[] = date.split("/");
					DateTime maintenanceDate= new DateTime(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
					if(this.vans[i].completeMaintenance(maintenanceDate)) {
						System.out.println("Vehicle "+id+" has all maintenance completed and ready for rent");
					} else {
					   System.out.println("Vehicle "+id+" could not complete maintenance");
					   return;
				    }
					flag=true;
					break;
                }
            }
			if(!flag) {
				System.out.println("Id is incorrect");
				return;
			}
        } else {
        	System.out.println("There are no vans, please add cars.");
			return;
        }
	}

	/**
	 * Method used to get details of car or van with their rental history
	 */
	
	public void getDetails()
	{
		if(cars[0]==null && vans[0]==null)
		{
			System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
			return;
		}
		if(cars[0]!=null)
		{
			System.out.println("***********Cars**********");
			for (int i = 0; this.cars[i] != null; i++)
				System.out.println(this.cars[i].getDetails());
		}
		if(vans[0]!=null)
		{
			System.out.println("***********Vans**********");
			for (int i = 0; this.vans[i] != null; i++)
				System.out.println(this.vans[i].getDetails());
		}
	}
	
	/**
	 * Method used to get details of available car or van with their rental history
	 */
	
	public void getAvailableDetails()
	{
		if(cars[0]==null && vans[0]==null)
		{
			System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
			return;
		}
		if(cars[0]!=null) {
			System.out.println("***********Cars**********");
			for (int i = 0; this.cars[i] != null; i++) {
				if(cars[i].getVehicleStatus()==0) {
					System.out.println(this.cars[i].getDetails());
				}
			}	
		} else {
			System.out.println("***********Cars**********");
			System.out.println("There are no cars available for rent");
		}
		
		if(vans[0]!=null && vans[0].getVehicleStatus()==0) {
			System.out.println("***********Vans**********");
			for (int i = 0; this.vans[i] != null; i++) {
				if(vans[i].getVehicleStatus()==0) {
					System.out.println(this.vans[i].getDetails());
				}
			}
		} else {
			System.out.println("***********Vans**********");
			System.out.println("There are no vans available for rent");
		}
	}
	
	/**
	 * Method used to get details by year of car or van with their rental history
	 */
	
	public void getByYear(Scanner sc)
	{
		System.out.println("Enter year : ");
		int year = sc.nextInt();
		if(cars[0]==null && vans[0]==null)
		{
			System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
			return;
		}
		if(cars[0]!=null) {
			System.out.println("***********Cars**********");
			for (int i = 0; this.cars[i] != null; i++) {
				if(cars[i].getYear()==year) {
					System.out.println(this.cars[i].getDetails());
				}
			}	
		} else {
			System.out.println("***********Cars**********");
			System.out.println("There are no cars were made in " +year);
		}
		
		if(vans[0]!=null && vans[0].getVehicleStatus()==0) {
			System.out.println("***********Vans**********");
			for (int i = 0; this.vans[i] != null; i++) {
				if(vans[i].getYear()==year) {
					System.out.println(this.vans[i].getDetails());
				}
			}
		} else {
			System.out.println("***********Vans**********");
			System.out.println("There are no vans were made in " +year);
		}
	}
	
	/**
	 * Method used to get details by made in of car or van with their rental history
	 */
	
	public void getByMadeIn(Scanner sc)
	{
		System.out.println("Enter made in : ");
		String made = sc.next();
		if(cars[0]==null && vans[0]==null)
		{
			System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
			return;
		}
		if(cars[0]!=null) {
			System.out.println("***********Cars**********");
			for (int i = 0; this.cars[i] != null; i++) {
				if(cars[i].getMake().equals(made)) {
					System.out.println(this.cars[i].getDetails());
				}
			}	
		} else {
			System.out.println("***********Cars**********");
			System.out.println("There are no cars were made in " +made);
		}
		
		if(vans[0]!=null && vans[0].getVehicleStatus()==0) {
			System.out.println("***********Vans**********");
			for (int i = 0; this.vans[i] != null; i++) {
				if(vans[i].getMake().equals(made)) {
					System.out.println(this.vans[i].getDetails());
				}
			}
		} else {
			System.out.println("***********Vans**********");
			System.out.println("There are no vans were made in " +made);
		}
	}
	
	/**
	 * Method used to get details by year and made in of car or van with their rental history
	 */
	
	public void getByFilter(Scanner sc)
	{
		System.out.println("Enter year : ");
		int year = sc.nextInt();
		System.out.println("Enter made in : ");
		String made = sc.next();
		if(cars[0]==null && vans[0]==null)
		{
			System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
			return;
		}
		if(cars[0]!=null) {
			System.out.println("***********Cars**********");
			for (int i = 0; this.cars[i] != null; i++) {
				if(cars[i].getMake().equals(made) && cars[i].getYear()==year) {
					System.out.println(this.cars[i].getDetails());
				}
			}	
		} else {
			System.out.println("***********Cars**********");
			System.out.println("There are no cars were made in " +made + " " +year);
		}
		
		if(vans[0]!=null && vans[0].getVehicleStatus()==0) {
			System.out.println("***********Vans**********");
			for (int i = 0; this.vans[i] != null; i++) {
				if(vans[i].getMake().equals(made) && vans[i].getYear()==year) {
					System.out.println(this.vans[i].getDetails());
				}
			}
		} else {
			System.out.println("***********Vans**********");
			System.out.println("There are no vans were made in " +made + " " +year);
		}
	}
        
        public void getTotalIncome(Scanner sc)
        {
            System.out.println("Total Income : " + totalIncome);
        }
}