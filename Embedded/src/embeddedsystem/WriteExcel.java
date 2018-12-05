package embeddedsystem;


import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import java.io.*;


public class WriteExcel {
	static int r = 1;
	
	static  ArrayList<Sensor> sensorList = new ArrayList();
	
	public WriteExcel(ArrayList<Sensor> list) {
		
	sensorList = list;

	try {
		HSSFWorkbook workbook = new HSSFWorkbook();
	
	HSSFSheet sheet = workbook.createSheet("List products");
	
	//Create Heading
	Row rowHeading = sheet.createRow(0);
	rowHeading.createCell(0).setCellValue("Sensor Name");
	rowHeading.createCell(1).setCellValue("State");
	rowHeading.createCell(2).setCellValue("Forwarded Message");
	rowHeading.createCell(3).setCellValue("Sensing Range");
	rowHeading.createCell(4).setCellValue("Communication Range");
	rowHeading.createCell(5).setCellValue("Number Of Neighbors");
	
	for(int i = 0; i<6; i++)
	{
		CellStyle stylerowHeading = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 11);
		stylerowHeading.setFont(font);
		stylerowHeading.setVerticalAlignment(VerticalAlignment.CENTER);
		rowHeading.getCell(i).setCellStyle(stylerowHeading);
		
		
}
	int r = 1;
	for(Sensor p : sensorList) {
		Row row = sheet.createRow(r);
		
		//Sensor name Column
		Cell cellQuantity= row.createCell(0);
		cellQuantity.setCellValue(p.getTypeOfSensor());
		
		//State Column
		Cell cellName = row.createCell(1);
		cellName.setCellValue(p.getState());
		
		//Forward message Column
		Cell cellSubTotal= row.createCell(2);
		cellSubTotal.setCellValue(p.getForwardMsg());
		
		//Sensing Range
		Cell cellCreationDate = row.createCell(3);
		cellCreationDate.setCellValue(p.getSenRange());
		
		//Communication Range Column
		Cell cellPrice= row.createCell(4);
		cellPrice.setCellValue(p.getComRange());
		
		//Neighbor Column
		Cell cellId = row.createCell(5);
		cellId.setCellValue(p.getNeighborNumber());
		
		
		r++;
	}
	
	//Autofit
	for(int i = 0; i<6; i++) {
		sheet.autoSizeColumn(i);
	}
	
	//save to excel file
	FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Super\\Desktop\\almostdickpics\\listProducts.xls"));
	workbook.write(out);
	out.close();
	workbook.close();
	System.out.println("Excel written successfully..");
	
} catch (Exception e) {
System.out.println(e.getMessage());
}

}
	public static void PrintConnectedNetwork(int connectedsensors, ArrayList<subNet> subNetList) {
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			HSSFSheet sheet = workbook.getSheet("Subnets");
		    if (sheet == null)
		        sheet = workbook.createSheet("Subnets");
		
		//Create Heading
		Row rowHeading = sheet.createRow(0);
		rowHeading.createCell(0).setCellValue("Amount Of Sensors");
		rowHeading.createCell(1).setCellValue("Connected Sensors");
		rowHeading.createCell(2).setCellValue("Disconnected Sensors");
		rowHeading.createCell(3).setCellValue("Amount of Subnetworks");

		for(int i = 0; i<4; i++)
		{
			CellStyle stylerowHeading = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 11);
			stylerowHeading.setFont(font);
			stylerowHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			rowHeading.getCell(i).setCellStyle(stylerowHeading);
			
			
	}
		
		//for(Sensor p : sensorList) {
			Row row = sheet.createRow(r);
			
			//Sensor name Column
			Cell cellQuantity= row.createCell(0);
			cellQuantity.setCellValue(MyCanvas.SensorAmount);
			
			//State Column
			Cell cellName = row.createCell(1);
			cellName.setCellValue(connectedsensors);
			
			//message Column
			Cell cellDisconnected= row.createCell(2);
			cellDisconnected.setCellValue(MyCanvas.SensorAmount-connectedsensors);
			
			//Forward message Column
			Cell cellSubTotal= row.createCell(3);
			cellSubTotal.setCellValue(subNetList.size());
			

			
			
			r++;
		//}
		
		//Autofit
		for(int i = 0; i<6; i++) {
			sheet.autoSizeColumn(i);
		}
		
		//save to excel file
		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Super\\Desktop\\almostdickpics\\Subnets.xls"));
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("Excel written successfully..");
		
	} catch (Exception e) {
	System.out.println(e.getMessage());
	}

	}
	
public static void PrintConnectedNetwork2(ArrayList<Integer> AmountOfSensors, ArrayList<Integer> FailedSensors , ArrayList<Integer> ConnectedSensors, ArrayList<Integer> Disconnected, ArrayList<Integer> subNetList, ArrayList<Integer> CoverageList) {
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			HSSFSheet sheet = workbook.getSheet("Subnets");
		    if (sheet == null)
		        sheet = workbook.createSheet("Subnets");
		
		//Create Heading
		Row rowHeading = sheet.createRow(0);
		rowHeading.createCell(0).setCellValue("Amount Of Sensors");
		rowHeading.createCell(1).setCellValue("Amount Of Failed Sensors");
		rowHeading.createCell(2).setCellValue("Connected Sensors");
		rowHeading.createCell(3).setCellValue("Disconnected Sensors");
		rowHeading.createCell(4).setCellValue("Amount of Subnetworks");
		rowHeading.createCell(5).setCellValue("Coverage");

		for(int i = 0; i<6; i++)
		{
			CellStyle stylerowHeading = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 11);
			stylerowHeading.setFont(font);
			stylerowHeading.setVerticalAlignment(VerticalAlignment.CENTER);
			rowHeading.getCell(i).setCellStyle(stylerowHeading);
			
			
	}
		
		for(int i = 0; i < AmountOfSensors.size(); i++) {
			Row row = sheet.createRow(r);
			
			//Sensor name Column
			Cell cellQuantity= row.createCell(0);
			cellQuantity.setCellValue(AmountOfSensors.get(i));
			
			//Failed Sensors Column
			Cell cellFailedSensors= row.createCell(1);
			cellFailedSensors.setCellValue(FailedSensors.get(i));
			
			//State Column
			Cell cellName = row.createCell(2);
			cellName.setCellValue(ConnectedSensors.get(i));
			
			//message Column
			Cell cellDisconnected= row.createCell(3);
			cellDisconnected.setCellValue(Disconnected.get(i));
			
			//Forward message Column
			Cell cellSubTotal= row.createCell(4);
			cellSubTotal.setCellValue(subNetList.get(i));
			
			
			Cell cellCoverage= row.createCell(5);
			cellCoverage.setCellValue(CoverageList.get(i));
			

			
			
			r++;
		}
		
		//Autofit
		for(int i = 0; i<7; i++) {
			sheet.autoSizeColumn(i);
		}
		
		//save to excel file
		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Super\\Desktop\\almostdickpics\\Subnets.xls"));
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("Excel written successfully..");
		
	} catch (Exception e) {
	System.out.println(e.getMessage());
	}

	}

public static void PrintCoverage(ArrayList<Integer> AmountOfSensors, ArrayList<Integer> FailedSensors, ArrayList<Integer> ConnectedSensors, ArrayList<Integer> Disconnected, ArrayList<Integer> subNetList) {
	
	try {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.getSheet("Subnets");
	    if (sheet == null)
	        sheet = workbook.createSheet("Subnets");
	
	//Create Heading
	Row rowHeading = sheet.createRow(0);
	rowHeading.createCell(0).setCellValue("Amount Of Sensors");
	rowHeading.createCell(1).setCellValue("Amount Of Failed Sensors");
	rowHeading.createCell(2).setCellValue("Connected Sensors");
	rowHeading.createCell(3).setCellValue("Disconnected Sensors");
	rowHeading.createCell(4).setCellValue("Amount of Subnetworks");

	for(int i = 0; i<5; i++)
	{
		CellStyle stylerowHeading = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 11);
		stylerowHeading.setFont(font);
		stylerowHeading.setVerticalAlignment(VerticalAlignment.CENTER);
		rowHeading.getCell(i).setCellStyle(stylerowHeading);
		
		
}
	
	for(int i = 0; i < AmountOfSensors.size(); i++) {
		Row row = sheet.createRow(r);
		
		//Sensor name Column
		Cell cellQuantity= row.createCell(0);
		cellQuantity.setCellValue(AmountOfSensors.get(i));
		
		//Failed Sensors Column
		Cell cellFailedSensors= row.createCell(1);
		cellFailedSensors.setCellValue(FailedSensors.get(i));
		
		//State Column
		Cell cellName = row.createCell(2);
		cellName.setCellValue(ConnectedSensors.get(i));
		
		//message Column
		Cell cellDisconnected= row.createCell(3);
		cellDisconnected.setCellValue(Disconnected.get(i));
		
		//Forward message Column
		Cell cellSubTotal= row.createCell(4);
		cellSubTotal.setCellValue(subNetList.get(i));
		

		
		
		r++;
	}
	
	//Autofit
	for(int i = 0; i<6; i++) {
		sheet.autoSizeColumn(i);
	}
	
	//save to excel file
	FileOutputStream out = new FileOutputStream(new File("/Users/eleonora/Desktop/DTU/Embedded/gitHub/fire-detection-simulation/Subnets.xls"));
	workbook.write(out);
	out.close();
	workbook.close();
	System.out.println("Excel written successfully..");
	
} catch (Exception e) {
System.out.println(e.getMessage());
}

}

}

