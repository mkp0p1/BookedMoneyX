package com.mycompany.bookedmoneyx.client;

import java.util.Objects;

/**
 *
 * @author User2
 */
public class Account_class {
	private long ID;
	private String Name;
	private double Balance;

	public Account_class() {
		this.ID = 0;
		this.Name = "";
		this.Balance = 0;
	}
	
	public Account_class(String Name) {
		this.ID = 0;
		this.Name = Name;
		this.Balance = 0;
	}
	
	public Account_class(long ID, String Name, double Balance) {
		this.ID = ID;
		this.Name = Name;
		this.Balance = Balance;
	}

	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}
		
	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}
		
	public double getBalance() {
		return Balance;
	}

	public void setBalance(double Balance) {
		this.Balance = Balance;
	}
	
	public void set(String name, double balance){
		this.Name = name;
		this.Balance = balance;
	}
	
	public void set(Account_class value){
		this.ID = value.getID();
		this.Name = value.getName();
		this.Balance = value.getBalance();
	}
	
	public String toString(int mode){
		switch(mode){
			case 1:
				return "ID: " + ID + "\tName: " + Name + "\tBalance: " + Balance;
			case 2:
				return "ID: '" + ID + "'\tName: '" + Name + "'\tBalance: '" + Balance + "'";
			default:
				return ID + ";" + Name + ";" + Balance;
		}
	}
	
	public String toString(){
		return toString(1);
	}
	
	public static Account_class parse(String str){
		int[] indexes = new int[2];
		Long l;
		Double d;		
		indexes[0] = str.indexOf(";", 0);					if(indexes[0] == -1)	return null;
		indexes[1] = str.indexOf(";", indexes[0] + 1);		if(indexes[1] == -1)	return null;		
		l = Func.parseLong(str.substring(0, indexes[0]));						if(l == null)		return null;
		d = Func.parseDouble(str.substring(indexes[1] + 1, str.length()));		if(d == null)		return null;
		return new Account_class(l.intValue(), str.substring(indexes[0] + 1, indexes[1]), d.doubleValue());
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (int) (this.ID ^ (this.ID >>> 32));
		hash = 89 * hash + Objects.hashCode(this.Name);
		hash = 89 * hash + (int) (Double.doubleToLongBits(this.Balance) ^ (Double.doubleToLongBits(this.Balance) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Account_class other = (Account_class) obj;
		if (this.ID != other.ID) {
			return false;
		}
		if (Double.doubleToLongBits(this.Balance) != Double.doubleToLongBits(other.Balance)) {
			return false;
		}
		return Objects.equals(this.Name, other.Name);
	}
}
