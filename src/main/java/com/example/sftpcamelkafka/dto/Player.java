package com.example.sftpcamelkafka.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

/**
 * 
 * 
Here we are processing a fixed length record in below format

Name, Debut Date, Country , Mathes palyed, Runs scored, Average, Strike rate, Batting posion, Reteriment Date
 
Rahul Dravid      1996-09-10INDIA       00160  9,30060.5480.54 42012-09-01
Sachin Tendulkar  1989-09-10INDIA       00140 49,60064.5440.54 42012-09-01
Steve Waugh       1993-09-10AUSTRALIA   00140 30,90072.5480.54 42012-09-01
Kevin Peterson    1999-09-10INDIA       00140 21,60020.5450.54 42012-09-01
Adam Gilcrist     1996-09-10AUSTRALIA   00140 18,67077.5460.54102012-09-01


 *  @author santosh joshi (santoshjoshi2003@gmail.com)
 *  
 */
@Data
@FixedLengthRecord(length=74, paddingChar=' ')
public class Player implements Serializable {

	
private static final long serialVersionUID = 1L;
	
	@DataField(pos = 1, length=18, trim=true, align="L")
	private String name;

	@DataField(pos = 19, length=10, pattern = "yyyy-MM-dd")
	private Date debutDate;
	
	@DataField(pos = 29, length=12, trim=true, align="L")
	private String country;
	
	@DataField(pos = 41, length=5, paddingChar='0', trim=true)
	private int matchesPlayed;
	
	@DataField(pos = 46, length=7, paddingChar=' ', trim=true, pattern="#0,000")
	private Integer runsScored;
	
	@DataField(pos = 53, length=5, paddingChar='0', precision= 2)
	private float averageRunRate;
	
	@DataField(pos = 58, length=5, precision= 2)
	private BigDecimal strikeRate;
	
	@DataField(pos = 63, length=2, paddingChar=' ', trim=true)
	private int batingPosition;
	
	@DataField(pos = 65, length=10, pattern = "yyyy-MM-dd", defaultValue="2021-12-31")
	private Date retirementDate;


}