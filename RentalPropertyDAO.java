package com.wipro.hms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wipro.hms.bean.RentalPropertyBean;
import com.wipro.hms.util.DBUtil;

public class RentalPropertyDAO 
{
	Connection conn = DBUtil.getDBConnection();
	public String generatePropertyID(String city)
	{
		String propertyId = "";
		int seqVal = 0;
		try
		{
			PreparedStatement ps = conn.prepareStatement("select Rental_seq.nextval from dual");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				seqVal = rs.getInt(1);
			}
			propertyId = (city.substring(0,3)).toUpperCase();
			propertyId = propertyId + seqVal;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return propertyId;
	}
	
	public int createRentalProperty(RentalPropertyBean bean)
	{
		int recAffcted = 0;
		try
		{
			PreparedStatement ps = conn.prepareStatement("insert into RENTAL_TBL(PROPERTYID,RENTALAMOUNT,NOOFBEDROOMS,LOCATION,CITY)values(?,?,?,?,?)");
			ps.setString(1,new RentalPropertyDAO().generatePropertyID(bean.getCity()));
			ps.setFloat(2, bean.getRentalAmount());
			ps.setInt(3, bean.getNoOfBedRooms());
			ps.setString(4, bean.getLocation());
			ps.setString(5, bean.getCity());
			recAffcted = ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return recAffcted;
	}
	
	public List<RentalPropertyBean> findPropertyByCriteria(float minRentalAmount,float maxRentalAmount,RentalPropertyBean bean)
	{
		List<RentalPropertyBean> rentalProperty = new ArrayList<RentalPropertyBean>();
		try
		{
			PreparedStatement ps = conn.prepareStatement("select * from RENTAL_TBL where NOOFBEDROOMS=? and LOCATION like ? and CITY like ? and (RENTALAMOUNT between ? and ?) order by RENTALAMOUNT");
			ps.setInt(1, bean.getNoOfBedRooms());
			ps.setString(2,bean.getLocation());
			ps.setString(3, bean.getCity());
			ps.setFloat(4, minRentalAmount);
			ps.setFloat(5, maxRentalAmount);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				bean.setPropertyId(rs.getString(1));
				bean.setRentalAmount(rs.getFloat(2));
				bean.setNoOfBedRooms(rs.getInt(3));
				bean.setLocation(rs.getString(4));
				bean.setCity(rs.getString(5));
				
				rentalProperty.add(bean);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rentalProperty;
	}
}
