package com.mycompany.bookedmoneyx.client;
import com.mycompany.bookedmoneyx.client.Operation_class;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class OperationsList_class {
    private ArrayList<Operation_class> Data;
	private long LastID;
    
    OperationsList_class(){
        Data = new ArrayList<Operation_class>();
		LastID = 0;
    }

	public long getLastID() {
		return LastID;
	}
    
	//	Возвращает индекс
    public int findItem(long id){
        for(int i = 0; i < Data.size(); i++){
            if(Data.get(i).getID() == id)
                return i;
        }
        return -1;
    }

    public Operation_class getItem(int index){
        Operation_class item;
        try{
            item = Data.get(index);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
        return item;
    }
    
    public Operation_class getItem_byID(long id){
        int index = findItem(id);
        if(index == -1)
            return null;        
        return getItem(index);
    }
	
	public boolean equals(ArrayList<Operation_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;
		Operation_class item2;
		for(Operation_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
	
	public boolean equals(List<Operation_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;
		Operation_class item2;
		for(Operation_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
    
    public void addItem(Operation_class item){
        Data.add(item);
    }
	
	public void addItem_byLastID(Operation_class item){
		item.setID(++LastID);
        Data.add(item);
    }
	
	//	Добавляет или актуализирует элементы из полученного списка
	public void addOrSet(java.util.ArrayList<Operation_class> list){
		if(list == null)	return;
		for(Operation_class item : list){
			if(setItem_byID(item.getID(), item) == false)
				addItem(item);
		}
	}
	
	//	Добавляет или актуализирует элементы из полученного списка
	public void addOrSet(java.util.List<Operation_class> list){
		if(list == null)	return;
		for(Operation_class item : list){
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
    
    public boolean deleteItem(long id){
        int index = findItem(id);
        if(index == -1)
            return false;
        Data.remove(getItem(index));
        return true;
    }
            
    public boolean setItem(int index, Operation_class item){
        try{
            Data.set(index, item);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;   
    }
    
    public boolean setItem_byID(long id, Operation_class item){
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
	
	public ArrayList<Operation_class> getOperationsByInterval(Date begin, Date end){
		if(Data.isEmpty())
			return null;

		ArrayList<Operation_class> list = new ArrayList<Operation_class>();
		Operation_class operation;
		for(int i = 0; i < Data.size(); i++){
			operation = Data.get(i);
			if(Func.inInterval(operation.getDateTime(), begin, end))
				list.add(operation);
		}
		return list;
	}
	
	public java.util.ArrayList<Operation_class> getOperationsAll(){
		if(Data.isEmpty())
			return null;
		ArrayList<Operation_class> list = new ArrayList<Operation_class>(Data);
		return list;
	}
}
