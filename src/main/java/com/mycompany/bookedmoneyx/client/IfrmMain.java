package com.mycompany.bookedmoneyx.client;

public interface IfrmMain {
	void ShowData();
	boolean InitHibernate();
	boolean InitHibernate(Data_class data);
	int SaveConfigToFile(String path);
	int SaveDataToFile(boolean withoutOperations);
	int LoadDataFromDB();
	int SaveDataToDB(boolean withoutOperations);
}
