package com.appl.model;

import com.appl.controller.*;

/**
 * Class to save all the vehicle details
 * This class contains all the vehicle attributes required for this application
 * This is the data access layer and main logic for this application is written here
 */
public class Vehicle {
    protected String Vehicle_id;
    protected int Year;
    protected String Make;
    protected String Model;
    private int RentByCust;
    private int vehicleStatus;
    protected VehicleType vehicleType;
    public RentalRecord records[]= new RentalRecord[10];

    // Constructor to accept the details of a vehicle
    Vehicle(String VehicleId,int Year,String Make,String Model, int RentByCust, int status,VehicleType vehicleType){
        this.Vehicle_id=VehicleId;
        this.Year=Year;
        this.Make=Make;
        this.Model= Model;
        this.RentByCust = RentByCust;
        this.setVehicleStatus(status);
        this.vehicleType=vehicleType;
    }

    /**
     * Method to get vehicle ID
     */
    public String getVehicleId(){
        return this.Vehicle_id;
    }

    /**
     * Used to rent either available car or available van
     * @param customerId,rentDate,numOfRentDay accepts customeID, date of rent, no of renting days
     * @return True or false as to vehicle is successfully rented or not
     */
    public boolean rent(String customerId, DateTime rentDate, int numOfRentDay)
    {
          String typeOfVehicle;
          if(this.Vehicle_id.contains("C_"))
              typeOfVehicle="car";
          else
              typeOfVehicle="van";
          if(this.getVehicleStatus()!=0 || numOfRentDay<this.vehicleType.canBeRentedForMinimumDays(rentDate,typeOfVehicle) || numOfRentDay>=14||numOfRentDay<2)
          {
              return false;
          }
          else if( typeOfVehicle.equals("van") )
          {
			  if(this.getVehicleStatus()!=0 || this.vehicleType.IsUnderMaintenance(rentDate,typeOfVehicle,numOfRentDay) ||  numOfRentDay==0)
              return false;
		      else
			  {
			  String rentId= this.Vehicle_id+"_"+customerId+"_"+rentDate. getEightDigitDate();
              this.getRecords()[this.getLastElementIndex()+1]=new RentalRecord(rentId,rentDate,new DateTime(rentDate,numOfRentDay));
              this.setVehicleStatus(1);
              return true;
			  }
          }
          else {
              String rentId= this.Vehicle_id+"_"+customerId+"_"+rentDate. getEightDigitDate();
              this.getRecords()[this.getLastElementIndex()+1]=new RentalRecord(rentId,rentDate,new DateTime(rentDate,numOfRentDay));
              this.setVehicleStatus(1);
              this.RentByCust++;
              return true;
          }
    }
    /**
     * sets the vehicle status to available after maintenance
     * @return Returns true if returned else false
     */
    public boolean performMaintenance()
    {
        if(this.getVehicleStatus()==1 || this.getVehicleStatus()==2)
            return false;
        this.setVehicleStatus(2);
        return true;
    }
    /**
     * Method used to get details of vehicle
     */
    public String toString()
    {
        String repository="";
        if(this.Vehicle_id.contains("V_"))
        {
            repository=this.Vehicle_id+":"+String.valueOf(this.Year)+":"+this.Make+":"+this.Model+":15"+":";
        }
        if(this.Vehicle_id.contains("C_")) {
            repository = this.Vehicle_id + ":" + String.valueOf(this.Year) + ":" + this.Make + ":" + this.Model + ":" + String.valueOf(this.vehicleType.getSeats("car")) + ":";
        }
        switch(this.getVehicleStatus())
        {
            case 0:repository+="Available";
                break;
            case 1: repository+="Rented";
                break;
            case 2: repository+="Maintenance";
                break;
        }
        return repository;
    }

    /**
     * Method used to get details of car or van with their rental history
     */
    public String getDetails()
    {
        String data = "Vehicle ID:\t"+this.Vehicle_id+"\n Year:\t "+String.valueOf(this.Year)+"\n Made in:\t"+this.Make+"\n Model:\t"+this.Model+"\nRent by Cust:\t"+this.RentByCust+"\n Number of Seats:\t";
                if(this.Vehicle_id.contains("C_")){
                     
			data+=String.valueOf(this.vehicleType.getSeats("car"))+"\n Status:\t";
                }else {
            data += String.valueOf(this.vehicleType.getSeats("van")) + "\n Status:\t";
        }
        switch(this.getVehicleStatus())
        {
            case 0:data+="Available";
                break;
            case 1: data+="Rented";
                break;
            case 2: data+="Maintenance";
                break;
        }
        return data;

    }

    /**
     * Method used to get last element index
     */
    public int getLastElementIndex()
	{
		int index=0;
		for(index=0;this.getRecords()[index]!=null;index++);
		return index-1;
	}

	public int getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(int vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public RentalRecord[] getRecords() {
		return records;
	}

	public void setRecords(RentalRecord records[]) {
		this.records = records;
	}
	
	public int getYear() {
		return Year;
	}

	public void setYear(int Year) {
		this.Year = Year;
	}
	
	public String getMake() {
		return Make;
	}

	public void setMake(String Make) {
		this.Make = Make;
	}

    public int getRentByCust() {
        return RentByCust;
    }

    public void setRentByCust(int RentByCust) {
        this.RentByCust = RentByCust;
    }

}