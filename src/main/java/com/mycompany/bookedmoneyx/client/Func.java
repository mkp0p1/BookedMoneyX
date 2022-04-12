package com.mycompany.bookedmoneyx.client;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Func {
	public static Integer parseInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Long parseLong(String text) {
		try {
			return Long.parseLong(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double parseDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Date parseDateTime(String str){
		try{
			return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(str);
		}
		catch(ParseException  e){
			return null;
		}
	}
	
	public static Date parseDate(String str){
		try{
			return new SimpleDateFormat("dd.MM.yyyy").parse(str);
		}
		catch(ParseException  e){
			return null;
		}
	}
	
	public static Date parseTime(String str){
		try{
			return new SimpleDateFormat("HH:mm:ss").parse(str);
		}
		catch(ParseException  e){
			return null;
		}
	}
	
	public static String toString_DateTime(Date value){
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(value);
	}
	
	public static String toString_Date(Date value){
		return new SimpleDateFormat("dd.MM.yyyy").format(value);
	}
	
	public static String toString_Time(Date value){
		return new SimpleDateFormat("HH:mm:ss").format(value);
	}
	
	//	Проверяет наличие файла
	public static boolean isExistFile(String path){
		return new java.io.File(path).isFile();
	}
	
	//	Возвращает полный путь к исполняемому файлу
	public static String getFileNameApplication(){
		return System.getProperty("java.class.path");
	}
	
	//	Вывод системных параметров и их значений
	public static String DEBUG_Show_SystemProperties(){
		String s = "";
		for (Object o : System.getProperties().keySet()) {
			s += o + ": '" + System.getProperties().getProperty(o.toString()) + "'\n";
		}
		return s;
	}
	
	public static void ShowMessage(String text){
		JOptionPane.showMessageDialog(null, text);
	}
	
	//	Возвращает полный путь к папке (включая последний слэш) из полного пути файла
	public static String getFolderFromPathOfFile(String filePath){
		int lastSeparatorIndex = filePath.lastIndexOf(System.getProperty("file.separator"));
		if (lastSeparatorIndex == -1) {
			return filePath;
		} else {
			return filePath.substring(0, lastSeparatorIndex + 1);
		}
	}
	
	//	Возвращает полный путь к папке (включая последний слэш) из списка нескольких путей, разделённых ;
	public static String getFolderFromPathOfFile2(String filePath){
		int index = filePath.indexOf(";");
		if(index != -1)
			filePath = filePath.substring(0, index);
		return getFolderFromPathOfFile(filePath);
	}

	//	Проверяет является входит ли метка времени в указанный интервал
	public static boolean inInterval(Date timestamp, Date beginInterval, Date endInterval){
		return beginInterval.getTime() <= timestamp.getTime()  &&  timestamp.getTime() <= endInterval.getTime();
	}
	
	public static Date getFirstDayOfWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		while (calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()) {
			calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
		}
		return calendar.getTime();
	}
	
	public static String roundStr(double value, int precision){
		String str = "#.";
		if(precision <= 0)
			return null;
		for(int i = 0; i < precision; i++)
			str += "#";
		String result = new java.text.DecimalFormat(str).format(value);
		result = result.replace(',', '.');
		int index = result.indexOf('.');
		//System.out.println("!: " + Integer.toString(index));
		//System.out.println("!2: " + result.length());
		if(index == -1){
			result += ".";
			for(int i = 0; i < precision; i++)
				result += "0";
		}
		else{
			int k = result.length() - index - 1;
			//System.out.println("!: " + Integer.toString(result.length()) + " - " + Integer.toString(index) + " - 1 = " + Integer.toString(result.length() - index - 1));
			if(k < precision){
				for(int i = 0; i < precision - k; i++)
					result += "0";
			}
		}
		return result;
	}
}
