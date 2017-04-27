package com.wipro.hms.service;

import java.util.ArrayList;
import java.util.List;

import com.wipro.hms.bean.RentalPropertyBean;
import com.wipro.hms.dao.RentalPropertyDAO;
import com.wipro.hms.util.InvalidCityException;

public class RentalPropertyService 
{
	public void validateCity(String city) throws InvalidCityException
	{
		if(!city.equalsIgnoreCase("chennai") && !city.equalsIgnoreCase("bengaluru"))
		{
			throw new InvalidCityException();
		}
	}
	public String addRentalProperty(RentalPropertyBean bean)
	{
		RentalPropertyDAO rp = new RentalPropertyDAO();
		if(bean.getLocation().equals(null) || bean.getCity().equals(null))
		{
			return "NULL VALUES IN INPUT";
		}
		else if(bean.getLocation().length()==0 || bean.getCity().length()==0)
		{
			return "INVALID INPUT";
		}
		else if(!bean.getCity().equalsIgnoreCase("Chennai") && !bean.getCity().equalsIgnoreCase("Bengaluru"))
		{
			try
			{
				new RentalPropertyService().validateCity(bean.getCity());
			}
			catch(InvalidCityException e)
			{
				return e.toString();
			}
		}
		else
		{
			int recAffctd = 0;
			recAffctd = rp.createRentalProperty(bean);
			if(recAffctd>0)
				return "SUCCESS";
			else
				return "FAILURE";
			
		}
		return "";
	}
	
	public List<RentalPropertyBean> getPropertyByCriteria(float minRentalAmount,float maxRentalAmount,int noOfBedRooms,String location,String city)
	{
		List<RentalPropertyBean> rentalArr = new ArrayList<RentalPropertyBean>();
		
		RentalPropertyBean bean = new RentalPropertyBean();
		bean.setLocation(location);
		bean.setCity(city);
		bean.setNoOfBedRooms(noOfBedRooms);
		
		rentalArr = new RentalPropertyDAO().findPropertyByCriteria(minRentalAmount, maxRentalAmount, bean);
		
		return rentalArr;
	}
	
	public String fetchRentalProperty(float minRentalAmount,float maxRentalAmount,int noOfBedRooms,String location,String city)
	{
		if(minRentalAmount == 0 || maxRentalAmount == 0)
		{
			return "INVALID VALUES";
		}
		else if(maxRentalAmount < minRentalAmount)
		{
			return "INVALID VALUES";
		}
		else if(noOfBedRooms<=0)
		{
			return "INVALID VALUES";
		}
		else if(location.equals(null) || city.equals(null))
		{
			return "INVALID VALUES";
		}
		else if(location.length()==0 || city.length()==0)
		{
			return "INVALID VALUES";
		}
		else if(!city.equalsIgnoreCase("Chennai") && !city.equalsIgnoreCase("Bengaluru"))
		{
			try
			{
				new RentalPropertyService().validateCity(city);
			}
			catch(InvalidCityException e)
			{
				return e.toString();
			}
		}
		else
		{
			List<RentalPropertyBean> propList = new RentalPropertyService().getPropertyByCriteria(minRentalAmount, maxRentalAmount, noOfBedRooms, location, city);
			if(propList!=null && propList.size()>0)
			{
				return "RECORDS AVAILABLE:" + propList.size();
			}
			else if(propList.size()==0)
			{
				return "NO MATCHING RECORDS";
			}
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		String result=new RentalPropertyService().fetchRentalProperty(3000,22000,2,"AnnaNagar","Chennai");
		System.out.println(result);
	}
}
