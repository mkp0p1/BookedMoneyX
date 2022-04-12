package com.mycompany.bookedmoneyx.client;

import java.util.Objects;

public class Category_class {
	private long ID;
	private String Name;
	private String NameParent;
	private boolean IsIncoming;
	
	public Category_class(){
		clear();
	}
	
	public Category_class(long id, String nameParent, String name, boolean isIncoming){
		ID = id;
		NameParent = nameParent;
		Name = name;
		IsIncoming = isIncoming;
	}

	public void clear(){
		ID = 0;
		NameParent = "";
		Name = "";
		IsIncoming = true;
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

	public String getNameParent() {
		return NameParent;
	}

	public void setNameParent(String NameParent) {
		this.NameParent = NameParent;
	}

	public boolean getIsIncoming() {
		return IsIncoming;
	}

	public void setIsIncoming(boolean IsIncoming) {
		this.IsIncoming = IsIncoming;
	}	
		
	public void set(long ID, String NameParent, String Name, boolean IsIncoming) {
		this.ID = ID;
		this.NameParent = NameParent;
		this.Name = Name;
		this.IsIncoming = IsIncoming;
	}
	
	public void set(String NameParent, String Name, boolean IsIncoming) {
		this.NameParent = NameParent;
		this.Name = Name;
		this.IsIncoming = IsIncoming;
	}
	
	public void set(Category_class value){
		this.ID = value.getID();
		this.NameParent = value.getNameParent();
		this.Name = value.getName();
		this.IsIncoming = value.getIsIncoming();
	}
	
	public boolean isParent(){
		if(NameParent.isEmpty())
			return true;
		else
			return false;
	}

	public String toString(int mode){
		switch(mode){
			case 1:
				return "ID: " + ID + "\tParent: " + NameParent + "\tName: " + Name + "\tIsIncoming: " + IsIncoming;
			case 2:
				return "ID: '" + ID + "\tParent: '" + NameParent + "'\tName: '" + Name + "'\tIsIncoming: '" + IsIncoming + "'";
			default:
				return ID + ";" + NameParent + ";" + Name + ";" + IsIncoming;
		}
	}
	
	public String toString(){
		return toString(2);
	}
	
	public static Category_class parse(String str){
		int[] indexes = new int[3];
		indexes[0] = str.indexOf(";", 0);					if(indexes[0] == -1)	return null;
		indexes[1] = str.indexOf(";", indexes[0] + 1);		if(indexes[1] == -1)	return null;
		indexes[2] = str.indexOf(";", indexes[1] + 1);		if(indexes[2] == -1)	return null;
		Long L;
		L = Func.parseLong(str.substring(0, indexes[0]));			if(L == null)		return null;
		boolean b = (str.substring(indexes[2] + 1, str.length()).compareTo("true") == 0  ||  
					 str.substring(indexes[2] + 1, str.length()).compareTo("True") == 0
				)? true : false;
		return new Category_class(L, str.substring(indexes[0] + 1, indexes[1]), str.substring(indexes[1] + 1, indexes[2]), b);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + (int) (this.ID ^ (this.ID >>> 32));
		hash = 79 * hash + Objects.hashCode(this.Name);
		hash = 79 * hash + Objects.hashCode(this.NameParent);
		hash = 79 * hash + (this.IsIncoming ? 1 : 0);
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
		final Category_class other = (Category_class) obj;
		if (this.ID != other.ID) {
			return false;
		}
		if (this.IsIncoming != other.IsIncoming) {
			return false;
		}
		if (!Objects.equals(this.Name, other.Name)) {
			return false;
		}
		return Objects.equals(this.NameParent, other.NameParent);
	}
	
}
