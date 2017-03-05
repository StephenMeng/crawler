package com.stephen.crawler.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class FileUtils {
	public static String filename = "";

	public static void saveInfo(String filename, String info, boolean ifCover) {
		try {
			FileWriter fileWriter = new FileWriter(new File(filename), ifCover);
			fileWriter.write(info + "\r\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveInfo(String filename, String info) {
		saveInfo(filename, info, true);
	}

	public static int save2Excel(List<Object> objects, String filename) {
		Excel excel = new Excel(filename);
		Sheet sheet = excel.getSheet();
		if (objects.size() < 1) {
			return -1;
		}
		for (Object object : objects) {
			saveObject2Sheet(sheet.getLastRowNum(), sheet, object);
		}
		int rowNum = sheet.getLastRowNum();
		excel.save();
		return rowNum;
	}

	public static int save2Excel(List<Object> objects, String filename, int rowNum) {
		Excel excel = new Excel(filename, rowNum);
		Sheet sheet = excel.getSheet();
		if (objects.size() < 1) {
			return rowNum;
		}
		if (rowNum < 1) {
			throw new RuntimeException("excel rownum should start from 1");
		} else if (rowNum == 1) {
			Row row = (Row) excel.getSheet().createRow(0);
			Class tClass = objects.get(0).getClass();
			Field[] fields = tClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Cell cell = row.createCell(i);
				fields[i].setAccessible(true);
				try {
					cell.setCellValue(fields[i].getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (Object object : objects) {
			saveObject2Sheet(rowNum, sheet, object);
			rowNum++;
		}
		excel.save();
		return rowNum;
	}

	private static void saveObject2Sheet(int rowNum, Sheet sheet, Object object) {
		Row row = (Row) sheet.createRow(rowNum);
		Class tClass = object.getClass();
		Field[] fields = tClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Cell cell = row.createCell(i);
			fields[i].setAccessible(true);
			try {
				cell.setCellValue(fields[i].get(object).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int save2txt(List<Object> objects, String filename) {
		return save2txt(objects, filename, "\t");
	}

	public static int save2txt(List<Object> objects, String filename, String seprator) {
		try {
			FileWriter fileWriter = new FileWriter(new File(filename));
			for (Object object : objects) {
				StringBuffer stringBuffer = new StringBuffer();
				Class tClass = object.getClass();
				Field[] fields = tClass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					try {
						stringBuffer.append(fields[i].get(object).toString());
						stringBuffer.append(seprator);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				fileWriter.write(stringBuffer + "\r\n");
			}

			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
