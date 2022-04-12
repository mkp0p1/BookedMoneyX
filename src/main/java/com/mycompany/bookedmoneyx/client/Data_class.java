package com.mycompany.bookedmoneyx.client;
import java.util.Date;
import java.util.Calendar;
import com.mycompany.bookedmoneyx.client.Operation_class;
import com.mycompany.bookedmoneyx.client.OperationsList_class;
import com.mycompany.bookedmoneyx.client.AccountList_class;
import com.mycompany.bookedmoneyx.client.Func;
import java.util.ArrayList;
import java.util.GregorianCalendar;


enum ePeriodType{
	DAY,
	WEEK,
	MONTH,
	YEAR,
	INTERVAL,
	ALL;

	public static ePeriodType parseCaption(String str){
		switch(str){
			case "День":
				return ePeriodType.DAY;
			case "Неделя":
				return ePeriodType.WEEK;
			case "Месяц":
				return ePeriodType.MONTH;
			case "Год":
				return ePeriodType.YEAR;
			case "Интервал":
				return ePeriodType.INTERVAL;
			case "Всё":
				return ePeriodType.ALL;
			default:
				return ePeriodType.DAY;
		}
	}
	
	public static ePeriodType parse(String str){
		ePeriodType type;
		try{
			type = valueOf(str);
		}
		catch(Exception IllegalArgumentException){
			type = ePeriodType.DAY;
		}
		return type;
	}
}

public class Data_class {
	private int SQL_ID;							//	ID в таблице SQL
    private String SQL_Host;
	private int SQL_Port;
    private String SQL_User;
    private String SQL_Password;
    private String SQL_DB;
	private ePeriodType PeriodType;				//	0 - День, 1 - Неделя, 2 - Месяц, 3 - Год, 4 - Интервал, 5 - Всё
	private Date PeriodBegin;
	private Date PeriodEnd;
	private long DefaultAccountIncomingID;		//	ID счёта зачисления (по умолчанию)
	private long DefaultAccountOutgoingID;		//	ID счёта списания (по умолчанию)
	private boolean DefaultOperationType;		//	Тип операции по умолчанию: true - приход, false - расход
	private int StoreDataMode;					//	Режим хранения данных: 0 - файл, 1 - БД, 2 - файл и БД
	
	private String appFolder;					//	Путь к папке приложения
	private Calendar calendar;
	public AccountList_class Accounts;
	public CategoryList_class Categories;
	public OperationsList_class Operations;
	//

	public Data_class() {
		this.SQL_ID = 0;
		this.SQL_Port = 0;
		this.PeriodType = ePeriodType.DAY;
		this.PeriodBegin = new Date();
		this.PeriodEnd = new Date();
		this.Accounts = new AccountList_class();
		this.Categories = new CategoryList_class();
		this.Operations = new OperationsList_class();
		this.calendar = new java.util.GregorianCalendar();
		this.calendar.setFirstDayOfWeek(Calendar.MONDAY);
		this.appFolder = "";
		this.DefaultAccountIncomingID = 0;
		this.DefaultAccountOutgoingID = 0;
		this.DefaultOperationType = false;
		this.StoreDataMode = 0;
	}

	public int getSQL_ID() {
		return SQL_ID;
	}//	for	Hibernate

	public void setSQL_ID(int SQL_ID) {
		this.SQL_ID = SQL_ID;
	}//	for	Hibernate
	
	public String getSQL_Host() {
		return SQL_Host;
	}

	public void setSQL_Host(String SQL_Host) {
		this.SQL_Host = SQL_Host;
	}

	public int getSQL_Port() {
		return SQL_Port;
	}

	public void setSQL_Port(int SQL_Port) {
		this.SQL_Port = SQL_Port;
	}
	
	public String getSQL_User() {
		return SQL_User;
	}

	public void setSQL_User(String SQL_User) {
		this.SQL_User = SQL_User;
	}

	public String getSQL_Password() {
		return SQL_Password;
	}

	public void setSQL_Password(String SQL_Password) {
		this.SQL_Password = SQL_Password;
	}

	public String getSQL_DB() {
		return SQL_DB;
	}

	public void setSQL_DB(String SQL_DB) {
		this.SQL_DB = SQL_DB;
	}

	public ePeriodType getPeriodType() {
		return PeriodType;
	}
	
	public void setPeriodType(ePeriodType periodType){
		this.PeriodType = periodType;
	}//	for	Hibernate
	
	public void setPeriodType(String value){
		PeriodType = ePeriodType.parse(value);
	}
	
	public Date getPeriodBegin() {
		return PeriodBegin;
	}
	
	public Date getPeriodEnd() {
		return PeriodEnd;
	}

	public void setPeriodBegin(Date PeriodBegin) {
		this.PeriodBegin = PeriodBegin;
	}//	for	Hibernate

	public void setPeriodEnd(Date PeriodEnd) {
		this.PeriodEnd = PeriodEnd;
	}//	for	Hibernate	
	
	public void setPeriod(ePeriodType periodType, Date periodBegin){
		this.PeriodType = periodType;

		switch(PeriodType){
			case DAY:
				calendar.setTime(periodBegin);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				PeriodBegin.setTime(calendar.getTimeInMillis());
				break;
			case WEEK:
				calendar.setTime(Func.getFirstDayOfWeek(periodBegin));
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				PeriodBegin.setTime(calendar.getTimeInMillis());
				calendar.add(Calendar.DATE, 6);
				break;
			case MONTH:
				calendar.setTime(periodBegin);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				PeriodBegin.setTime(calendar.getTimeInMillis());
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				break;
			case YEAR:
				calendar.setTime(periodBegin);
				calendar.set(calendar.get(Calendar.YEAR), 0, 1);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				PeriodBegin.setTime(calendar.getTimeInMillis());
				calendar.set(calendar.get(Calendar.YEAR), 11, 31);
				break;
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		PeriodEnd.setTime(calendar.getTimeInMillis());
	}
	
	public void setPeriod_Interval(Date periodBegin, Date periodEnd){
		if(periodBegin == null  ||  periodEnd == null)
			return;
		PeriodType = ePeriodType.INTERVAL;
		PeriodBegin.setTime(periodBegin.getTime());
		PeriodEnd.setTime(periodEnd.getTime());
	}
	
	public String getPeriodInterval(){
		return Func.toString_Date(PeriodBegin) + " - " + Func.toString_Date(PeriodEnd);
	}

	public boolean getDefaultOperationType() {
		return DefaultOperationType;
	}

	public void setDefaultOperationType(boolean isIncoming) {
		this.DefaultOperationType = isIncoming;
	}

	public long getDefaultAccountIncomingID() {
		return DefaultAccountIncomingID;
	}

	public void setDefaultAccountIncomingID(long DefaultAccountIncomingID) {
		this.DefaultAccountIncomingID = DefaultAccountIncomingID;
	}

	public long getDefaultAccountOutgoingID() {
		return DefaultAccountOutgoingID;
	}

	public void setDefaultAccountOutgoingID(long DefaultAccountOutgoingID) {
		this.DefaultAccountOutgoingID = DefaultAccountOutgoingID;
	}

	public int getStoreDataMode() {
		return StoreDataMode;
	}

	public void setStoreDataMode(int StoreDataMode) {
		this.StoreDataMode = StoreDataMode;
	}

	public String getAppFolder() {
		return appFolder;
	}

	public void setAppFolder(String appFolder) {
		this.appFolder = appFolder;
	}	

	public void clearConfig(){
		SQL_ID = 0;
		SQL_Host = "";
		SQL_Port = 0; 
		SQL_User = "";
		SQL_Password = "";
		SQL_DB = "";
		PeriodType = PeriodType.DAY;
		PeriodBegin.setTime(0);
		PeriodEnd.setTime(0);
		DefaultAccountIncomingID = 0;
		DefaultAccountOutgoingID = 0;
		DefaultOperationType = false;
		StoreDataMode = 0;
		appFolder = "";
	}
	
	public void clearData(){
		Accounts.clear();
		Categories.clear();
		Operations.clear();
	}
	
	public boolean parseAndSet_configParam(String key, String value){
		int res = 0;	//	0 - Ключ не опознан, 1 - ключ опознан, значение не опознано, 2 - ключ и значение опознаны
		Integer Int;
		//System.out.println("parseAndSet():\t'" + key + "', '" + value + "'");		//	DEBUG
		if(key.compareTo("SQL_ID") == 0)		{ res = 2;	Int = Func.parseInt(value);
			if(Int == null)	res = 1; else SQL_ID = Int;
		}
				
		if(key.compareTo("SQL_Host") == 0)		{ res = 2; 	SQL_Host = value;	}
		if(key.compareTo("SQL_Port") == 0)		{ res = 2;	Int = Func.parseInt(value);
			if(Int == null)	res = 1; else SQL_Port = Int;
		}
		
		if(key.compareTo("SQL_User") == 0)		{ res = 2;	SQL_User = value;	}
		if(key.compareTo("SQL_Password") == 0)	{ res = 2;	SQL_Password = value;	}
		if(key.compareTo("SQL_DB") == 0)		{ res = 2;	SQL_DB = value;	}
		if(key.compareTo("PeriodType") == 0)	{ res = 2;	PeriodType = ePeriodType.parse(value);}
		
		if(key.compareTo("PeriodBegin") == 0)	{ res = 2;	PeriodBegin = Func.parseDateTime(value);
			if(PeriodBegin == null)	res = 1;
		}
		
		if(key.compareTo("PeriodEnd") == 0)		{ res = 2;	PeriodEnd = Func.parseDateTime(value);
			if(PeriodEnd == null)	res = 1;
		}
		
		if(key.compareTo("DefaultAccountIncomingID") == 0)	{ res = 2;	Int = Func.parseInt(value);
			if(Int == null)	res = 1; else DefaultAccountIncomingID = Int;
		}
		
		if(key.compareTo("DefaultAccountOutgoingID") == 0)	{ res = 2;	Int = Func.parseInt(value);
			if(Int == null)	res = 1; else DefaultAccountOutgoingID = Int;
		}
		
		if(key.compareTo("DefaultOperationType") == 0){
			res = 2;
			if(value.compareTo("true") == 0  ||  value.compareTo("True") == 0)
				DefaultOperationType = true;
			else
				DefaultOperationType = false;
		}
		
		if(key.compareTo("StoreDataMode") == 0)	{ res = 2;	Int = Func.parseInt(value);
			if(Int == null)	res = 1; else StoreDataMode = Int;
		}
				
		return res == 2  ||  res == 0;
	}
	
	public String toString_ConfigForFile(){
		String s = "";
		s += "SQL_ID: " + SQL_ID + "\n";
		s += "SQL_Host: " + SQL_Host + "\n";
		s += "SQL_Port: " + SQL_Port + "\n";
		s += "SQL_User: " + SQL_User + "\n";
		s += "SQL_Password: " + SQL_Password + "\n";
		s += "SQL_DB: " + SQL_DB + "\n";
		s += "PeriodType: " + PeriodType.toString() + "\n";
		s += "PeriodBegin: " + Func.toString_DateTime(PeriodBegin) + "\n";
		s += "PeriodEnd: " + Func.toString_DateTime(PeriodEnd) + "\n";
		s += "DefaultAccountIncomingID: " + DefaultAccountIncomingID + "\n";
		s += "DefaultAccountOutgoingID: " + DefaultAccountOutgoingID + "\n";
		s += "DefaultOperationType: " + DefaultOperationType + "\n";
		s += "StoreDataMode: " + StoreDataMode + "\n";
		return s;
	}
	
	public String toString_DataForFile(boolean withoutOperations){
		String str = Accounts.toString(0) + "\n" + Categories.toString(0);
		if(!withoutOperations)
			str += "\n" + Operations.toString(3);
		return str;
	}
	
	public String toString_DataForFile_DEBUG(){
		return "Accounts: " + Accounts.size() + "\n" + Accounts.toString(2) + "\nCategories: " + Categories.size() 
				+ "\n" + Categories.toString(2) + "\nOperations: " + Operations.size() + "\n" + Operations.toString(0);
	}
	
	public java.util.ArrayList<Operation_class> getOperationsByPeriod(){
		java.util.ArrayList<Operation_class> list;
		if(PeriodType == ePeriodType.ALL)
			list = Operations.getOperationsAll();
		else
			list = Operations.getOperationsByInterval(PeriodBegin, PeriodEnd);
		return list;
	}
	
	//	Копировать всё кроме операций
	public void copyWithoutOperations(Data_class rhs){
		SQL_ID = rhs.getSQL_ID();
		SQL_Host = rhs.getSQL_Host();
		SQL_Port = rhs.getSQL_Port();
		SQL_User = rhs.getSQL_User();
		SQL_Password = rhs.getSQL_Password();
		SQL_DB = rhs.getSQL_DB();
		PeriodType = rhs.getPeriodType();
		PeriodBegin.setTime(rhs.getPeriodBegin().getTime());
		PeriodEnd.setTime(rhs.getPeriodEnd().getTime());
		DefaultAccountIncomingID = rhs.getDefaultAccountIncomingID();
		DefaultAccountOutgoingID = rhs.getDefaultAccountOutgoingID();
		DefaultOperationType = rhs.getDefaultOperationType();
		StoreDataMode = rhs.getStoreDataMode();
		
		appFolder = rhs.getAppFolder();
		this.setPeriod(PeriodType, PeriodBegin);
		
		Account_class account;
		Category_class category;
		Accounts.clear();
		for(int i = 0; i < rhs.Accounts.size(); i++){
			account = rhs.Accounts.getItem(i);
			Accounts.addItem(new Account_class(account.getID(), account.getName(), account.getBalance()));
		}
		
		Categories.clear();
		for(int i = 0; i < rhs.Categories.size(); i++){
			category = rhs.Categories.getItem(i);
			Categories.addItem(new Category_class(category.getID(), category.getNameParent(),
					category.getName(), category.getIsIncoming()));
		}
	}
}
