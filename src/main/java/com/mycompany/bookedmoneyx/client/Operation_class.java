package com.mycompany.bookedmoneyx.client;
import java.util.Date;
import com.mycompany.bookedmoneyx.client.Func;
import com.mycompany.bookedmoneyx.client.Category_class;
import com.mycompany.bookedmoneyx.client.CategoryList_class;
import java.util.Objects;

public class Operation_class {  
    private long ID;					//	ID операции
	private int Type;					//	0 - простая операция, 1 - перевод	
	private long AccountIncomingID;		//	ID счёта зачисления
	private long AccountOutgoingID;		//	ID счёта списания
	private boolean IsIncoming;			//	Приход / Расход
	private long CategoryID;				//	ID категории
    private Date DateTime;				//	ДатаВремя
    private String Description;			//	Описание
    private double Amount;				//	Сумма	
    
    public Operation_class(){
		DateTime = new Date();
		clear();
	}
    
    public Operation_class(long id, int type, long accountIncomingID, long accountOutgoingID,
			boolean isIncoming, long categoryID, Date dateTime, String description, double amount){
		set(id, type, accountIncomingID, accountOutgoingID, isIncoming, categoryID, dateTime, description, amount);
    }
	
	public Operation_class(Operation_class rhs){
		this(rhs.getID(), rhs.getType(), rhs.getAccountIncomingID(), rhs.getAccountOutgoingID(),
			rhs.getIsIncoming(), rhs.getCategoryID(), new Date(rhs.getDateTime().getTime()), rhs.getDescription(), rhs.getAmount());
    }
	
	public void clear(){
		ID = 0;
		Type = 0;
		AccountIncomingID = 0;
		AccountOutgoingID = 0;
		IsIncoming = true;
		CategoryID = 0;
		DateTime.setTime(0);
        Description = "";
        Amount = 0;		
	}

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
	
	public int getType() {
		return Type;
	}

	public void setType(int Type) {
		this.Type = Type;
	}

	public long getAccountIncomingID() {
		return AccountIncomingID;
	}

	public void setAccountIncomingID(long AccountIncomingID) {
		this.AccountIncomingID = AccountIncomingID;
	}

	public long getAccountOutgoingID() {
		return AccountOutgoingID;
	}

	public void setAccountOutgoingID(long AccountOutgoingID) {
		this.AccountOutgoingID = AccountOutgoingID;
	}

	public boolean getIsIncoming() {
		return IsIncoming;
	}

	public void setIsIncoming(boolean IsIncoming) {
		this.IsIncoming = IsIncoming;
	}
	
    public long getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(long CategoryID) {
        this.CategoryID = CategoryID;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date DateTime) {
        this.DateTime = DateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

	public void set(long id, int type, long accountIncomingID, long accountOutgoingID,
			boolean isIncoming, long categoryID, Date dateTime, String description, double amount){
        ID = id;
		Type = type;
		AccountIncomingID = accountIncomingID;
		AccountOutgoingID = accountOutgoingID;
		IsIncoming = isIncoming;
        CategoryID = categoryID;
        DateTime = dateTime;
        Description = description;
        Amount = amount;
    }
	
	public void set(Operation_class value){
		set(value.getID(), value.getType(), value.getAccountIncomingID(), value.getAccountOutgoingID(),
			value.getIsIncoming(), value.getCategoryID(), new Date(value.getDateTime().getTime()), value.getDescription(), value.getAmount());
	}
    
    public String toString(int mode){
        String s;
        switch(mode){
            case 1:
                s = "ID: " + ID + "; ";
				s += "Type: " + Type + "; ";
				s += "AccountIncomingID: " + AccountIncomingID + "; ";
				s += "AccountOutgoingID: " + AccountOutgoingID + "; ";
				s += "IsIncoming: " + IsIncoming + "; ";
				s += "CategoryID: " + CategoryID + "; ";
                s += "DateTime: " + Func.toString_DateTime(DateTime) + "; ";
                s += "Description: " + Description + "; ";
                s += "Amount: " + Amount;
            break;
            
            case 2:
				s = "ID: " + ID + "\n";
				s += "Type: " + Type + "\n";
				s += "AccountIncomingID: " + AccountIncomingID + "\n";
				s += "AccountOutgoingID: " + AccountOutgoingID + "\n";
				s += "IsIncoming: " + IsIncoming + "\n";				
				s += "CategoryID: " + CategoryID + "\n";
                s += "DateTime: " + Func.toString_DateTime(DateTime) + "\n";
                s += "Description: " + Description + "\n";
                s += "Amount: " + Amount;
            break;
			
			case 3:
				s = ID + ";";
				s += Type + ";";
				s += AccountIncomingID + ";";
				s += AccountOutgoingID + ";";
				if(IsIncoming)
					s += "1;";
				else
					s += "0;";
				s += CategoryID + ";";
                s += Func.toString_DateTime(DateTime) + ";";
                s += Description + ";";
                s += Amount;
				break;
            
            default:
                s = ID + ";";
				s += Type + ";";
				s += AccountIncomingID + ";";
				s += AccountOutgoingID + ";";
				s += IsIncoming + ";";
				s += CategoryID + ";";
                s += Func.toString_DateTime(DateTime) + ";";
                s += Description + ";";
                s += Amount;
        }
        return s;
    }
    
    public String toString(){
        return toString(1);        
    }
	
	public static Operation_class parse(String str){
		int[] indexes = new int[8];
		Long L;
		Double D;
		Date date;
		Integer I;
		
		indexes[0] = str.indexOf(";", 0);					if(indexes[0] == -1)	return null;
		for(int i = 1; i <= 7	; i++){
			indexes[i] = str.indexOf(";", indexes[i - 1] + 1);
			if(indexes[i] == -1)	return null;
		}			
			
		Operation_class oper = new Operation_class();
		L = Func.parseLong(str.substring(0, indexes[0]));						if(L == null)		return null;
		oper.setID(L);
		
		I = Func.parseInt(str.substring(indexes[0] + 1, indexes[1]));			if(I == null)		return null;
		oper.setType(I);
		
		L = Func.parseLong(str.substring(indexes[1] + 1, indexes[2]));			if(L == null)		return null;
		oper.setAccountIncomingID(L);
		
		L = Func.parseLong(str.substring(indexes[2] + 1, indexes[3]));			if(L == null)		return null;
		oper.setAccountOutgoingID(L);
		
		I = Func.parseInt(str.substring(indexes[3] + 1, indexes[4]));			if(I == null)		return null;
		if(I == 0)	oper.setIsIncoming(false);	else	oper.setIsIncoming(true);
		
		L = Func.parseLong(str.substring(indexes[4] + 1, indexes[5]));			if(L == null)		return null;
		oper.setCategoryID(L);
		
		date = Func.parseDateTime(str.substring(indexes[5] + 1, indexes[6]));	if(date == null)	return null;
		oper.setDateTime(date);
		
		oper.setDescription(str.substring(indexes[6] + 1, indexes[7]));
		D = Func.parseDouble(str.substring(indexes[7] + 1, str.length()));		if(D == null)		return null;
		oper.setAmount(D);
		return oper;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + (int) (this.ID ^ (this.ID >>> 32));
		hash = 43 * hash + this.Type;
		hash = 43 * hash + (int) (this.AccountIncomingID ^ (this.AccountIncomingID >>> 32));
		hash = 43 * hash + (int) (this.AccountOutgoingID ^ (this.AccountOutgoingID >>> 32));
		hash = 43 * hash + (this.IsIncoming ? 1 : 0);
		hash = 43 * hash + (int) (this.CategoryID ^ (this.CategoryID >>> 32));
		hash = 43 * hash + Objects.hashCode(this.DateTime);
		hash = 43 * hash + Objects.hashCode(this.Description);
		hash = 43 * hash + (int) (Double.doubleToLongBits(this.Amount) ^ (Double.doubleToLongBits(this.Amount) >>> 32));
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
		final Operation_class other = (Operation_class) obj;
		if (this.ID != other.ID) {
			return false;
		}
		if (this.Type != other.Type) {
			return false;
		}
		if (this.AccountIncomingID != other.AccountIncomingID) {
			return false;
		}
		if (this.AccountOutgoingID != other.AccountOutgoingID) {
			return false;
		}
		if (this.IsIncoming != other.IsIncoming) {
			return false;
		}
		if (this.CategoryID != other.CategoryID) {
			return false;
		}
		if (Double.doubleToLongBits(this.Amount) != Double.doubleToLongBits(other.Amount)) {
			return false;
		}
		if (!Objects.equals(this.Description, other.Description)) {
			return false;
		}
		return Objects.equals(this.DateTime, other.DateTime);
	}
	
}

