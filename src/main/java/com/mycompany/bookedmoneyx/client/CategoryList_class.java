package com.mycompany.bookedmoneyx.client;
import com.mycompany.bookedmoneyx.client.Category_class;
import java.util.List;
import java.util.ArrayList;

public class CategoryList_class {
	private java.util.ArrayList<Category_class> Data;
	private long LastID;
	
	public CategoryList_class(){
		Data = new java.util.ArrayList<Category_class>();
		LastID = 0;
	}
	
	public void clear(){
		Data.clear();
		LastID = 0;
	}
	
	public int size(){
        return Data.size();
    }
	
	//	Возвращает индекс
	public int findItem(long id){
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getID() == id)
				return i;
		return -1;
	}
	
	//	Возвращает индекс
	public int findItem(String name){
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getName().compareTo(name) == 0)
				return i;
		return -1;
	}
	
	public Category_class getItem(int index){
		Category_class item;
		try{
            item = Data.get(index);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
        return item;
	}
	
	public Category_class getItem_byID(long id){
		int index = findItem(id);
        if(index == -1)
            return null;        
        return getItem(index);
	}
	
	public Category_class getItem_byName(String name){
		int index = findItem(name);
        if(index == -1)
            return null;        
        return getItem(index);
	}
	
	public Category_class getItem_byName(String name, String name_parent){
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getName().compareTo(name) == 0 && Data.get(i).getNameParent().compareTo(name_parent) == 0){
				return Data.get(i);
			}
		return null;
	}
	
	public boolean equals(ArrayList<Category_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;
		Category_class item2;
		for(Category_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
	
	public boolean equals(List<Category_class> list){
		if(list == null)
			return false;
		if(list.size() != this.size())
			return false;
		Category_class item2;
		for(Category_class item : list){
			item2 = getItem_byID(item.getID());
			if(!item.equals(item2))
				return false;
		}
		return true;
	}
	
	public void addItem(Category_class item){
		item.setID(++LastID);
		Data.add(item);
	}
	
	public void addItem(String nameParent, String name, boolean isIncoming){
		Data.add(new Category_class(++LastID, nameParent, name, isIncoming));
	}
	
	//	Добавляет или актуализирует элименты из полученного списка
	public void addOrSet(java.util.ArrayList<Category_class> list){
		if(list == null)	return;
		for(Category_class item : list){
			if(setItem_byID(item.getID(), item) == false)
				addItem(item);
		}
	}
	
	//	Добавляет или актуализирует элименты из полученного списка
	public void addOrSet(java.util.List<Category_class> list){
		if(list == null)	return;
		for(Category_class item : list){
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
    
    public boolean deleteItem_byID(long id){
        int index = findItem(id);
        if(index == -1)
            return false;
        Data.remove(getItem(index));
        return true;
    }
	
	public boolean deleteItem_byName(String name){
        int index = findItem(name);
        if(index == -1)
            return false;
        Data.remove(getItem(index));
        return true;
    }
	
	public boolean deleteItem_byName(String name, String name_parent){
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getName().compareTo(name) == 0 && Data.get(i).getNameParent().compareTo(name_parent) == 0){
				Data.remove(getItem(i));
				return true;
			}
		return false;
    }
	
	 public boolean setItem(int index, Category_class item){
        try{
            Data.set(index, item);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;   
    }
    
    public boolean setItem_byID(long id, Category_class item){
        int index = findItem(id);
        if(index == -1)
            return false;
		item.setID(id);
        return setItem(index, item);
    }
	
	//	Выбрать все родительские категории
	public java.util.ArrayList<Category_class> selectParentCategories(){
		java.util.ArrayList<Category_class> list = new  java.util.ArrayList<Category_class>();
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).isParent())
				list.add(Data.get(i));
		return list;
	}
	
	//	Выбрать все подкатегории, принадлежащие укзаной категории
	public java.util.ArrayList<Category_class> selectByParentName(String name){
		java.util.ArrayList<Category_class> list = new  java.util.ArrayList<Category_class>();
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getNameParent().compareTo(name) == 0)
				list.add(Data.get(i));
		return list;
	}
	
	public java.util.ArrayList<Category_class> selectByPartOfName(String substring){
		java.util.ArrayList<Category_class> list = new  java.util.ArrayList<Category_class>();
		for(int i = 0; i < Data.size(); i++)
			if(Data.get(i).getNameParent().indexOf(substring) != -1)
				list.add(Data.get(i));
		return list;
	}
	
	public String toString(){
		return toString(2);
	}
			
	public String toString(int mode){
		String s = "";
		for(int i = 0; i < Data.size(); i++)
			s += Data.get(i).toString(mode) + "\n";
		return s;
	}
}
