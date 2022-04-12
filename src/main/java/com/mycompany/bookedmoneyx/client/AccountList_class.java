package com.mycompany.bookedmoneyx.client;
import com.mycompany.bookedmoneyx.client.Account_class;
import java.util.ArrayList;
import java.util.List;

public class AccountList_class {
	private java.util.ArrayList<Account_class> Data;
	private long LastID;

	public AccountList_class() {
		Data = new java.util.ArrayList<Account_class>();
		LastID = 0;
	}

	//	Возвращает индекс
	public int findItem(long id){
        for(int i = 0; i < Data.size(); i++){
            if(Data.get(i).getID() == id)
                return i;
        }
        return -1;
    }
	
	//	Возвращает индекс
	public int findItem(String name){
        for(int i = 0; i < Data.size(); i++){
            if(Data.get(i).getName().compareTo(name) == 0)
                return i;
        }
        return -1;
    }

    public Account_class getItem(int index){
        Account_class item;
        try{
            item = Data.get(index);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
        return item;
    }
    
	public Account_class getItem_byID(long id){
        int index = findItem(id);
        if(index == -1)
            return null;        
        return getItem(index);
    }
	
    public Account_class getItem_byName(String name){
        int index = findItem(name);
        if(index == -1)
            return null;        
        return getItem(index);
    }
	
	public long getID_byName(String name){
		int index = findItem(name);
		if(index == -1)
			return -1;
		else
			return getItem(index).getID();
	}
	
	public boolean equals(ArrayList<Account_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;
		Account_class item2;
		for(Account_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
	
	public boolean equals(List<Account_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;			
		Account_class item2;
		for(Account_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
	
	public void addItem(Account_class item){
		item.setID(++LastID);
        Data.add(item);
    }
	
	public void addItem(String name, double balance){
        Data.add(new Account_class(++LastID, name, balance));
    }
	
	//	Добавляет или актуализирует элименты из полученного списка
	public void addOrSet(java.util.ArrayList<Account_class> list){
		if(list == null)	return;
		for(Account_class item : list){
			if(setItem_byID(item.getID(), item) == false)
				addItem(item);
		}
	}
	
	//	Добавляет или актуализирует элименты из полученного списка
	public void addOrSet(java.util.List<Account_class> list){
		if(list == null)	return;
		for(Account_class item : list){
			if(setItem_byID(item.getID(), item) == false)
				addItem(item);
		}
	}
    
    public boolean deleteItem(int index){
        if(index < 0  ||  index >= Data.size())
            return false;
        Data.remove(index);           
        return true;
    }
    
    public boolean deleteItem_byID(String name){
        int index = findItem(name);
        if(index == -1)
            return false;
        Data.remove(getItem(index));
        return true;
    }
	
	public boolean deleteItem_byID(long id){
        int index = findItem(id);
        if(index == -1)
            return false;
        Data.remove(getItem(index));
        return true;
    }
            
    public boolean setItem(int index, Account_class item){
        try{
            Data.set(index, item);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;   
    }
    
    public boolean setItem_byID(long id, Account_class item){
        int index = findItem(id);
        if(index == -1)
            return false;
		item.setID(id);
        return setItem(index, item);
    }
    
    public int size(){
        return Data.size();
    }
	
	public void clear(){
		Data.clear();
		LastID = 0;
	}

	public String toString(){
		String s = "";
		for(int i = 0; i < Data.size(); i++)
			s += Data.get(i).toString() + "\n";
		return s;
	}
	
	public String toString(int mode){
		String s = "";
		for(int i = 0; i < Data.size(); i++)
			s += Data.get(i).toString(mode) + "\n";
		return s;
	}
	
	public java.util.ArrayList<Account_class> getAll(){
		if(Data.isEmpty())
			return null;
		java.util.ArrayList<Account_class> list = new java.util.ArrayList<Account_class>(Data);
		return list;
	}
	
}
