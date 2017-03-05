package com.stephen.crawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	private int count = 0;
	private Workbook wb = null;
	private Sheet sheet;
	private String filename;
	private String sheetname;

	public Excel(String filename) {
		initExcel(filename, 0, "data");
	}

	public Excel(String filename, int num) {
		initExcel(filename, num, "data");
	}

	public Excel(String filename, int num, String sheetname) {
		initExcel(filename, num, sheetname);
	}

	private void initExcel(String filename, int num, String sheetname) {

		this.filename = filename;
		this.sheetname = sheetname;
		this.count = num;

		// 创建工作文档对象
		if (new File(filename).exists()) {
			FileInputStream fs;
			try {
				fs = new FileInputStream(filename);
				POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
				wb = new HSSFWorkbook(ps);
				sheet = wb.getSheetAt(0);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String root = filename.substring(0, filename.lastIndexOf("/"));
			new File(root).mkdirs();
			String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			if (fileType.equals("xls")) {
				this.wb = new HSSFWorkbook();
			} else if (fileType.equals("xlsx")) {
				this.wb = new XSSFWorkbook();
			} else {
				System.out.println("您的文档格式不正确！");
			}
			this.sheet = wb.createSheet("data");
		}
	}

	public void save() {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(filename);
			wb.write(stream);
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

}
