package com.taxholic.core.util;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelView extends AbstractXlsxView{


	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileName=URLEncoder.encode(model.get("fileName").toString(),"UTF-8");
		
		XSSFWorkbook wb = (XSSFWorkbook) workbook;
		XSSFSheet sheet = wb.createSheet("Sheet1");						//시트 생성
		
		//스타일
		XSSFCellStyle style = wb.createCellStyle();							
		style.setFillPattern ((short) 1);												
		style.setFillForegroundColor (HSSFColor.GREY_25_PERCENT.index);	// 배경색 지정 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		//정렬
		
		//데이터 스타일
		XSSFCellStyle dataStyle = wb.createCellStyle();	
		XSSFDataFormat dataFormat = wb.createDataFormat(); 
		dataStyle.setDataFormat(dataFormat.getFormat("#,##0"));
		
		String header[] = (String[]) model.get("header");
		
		//데이터 생성
		@SuppressWarnings("unchecked")
		List<Map<Integer, Object>> list = (List<Map<Integer, Object>>) model.get("excelList");
		
		for(int i = 0; i < list.size(); i++){
			Map<Integer, Object> map = list.get(i);
			XSSFRow row = sheet.createRow( i+1 );
			for( int key : map.keySet()){
				if(map.get(key) instanceof Double){
	//        		XSSFCell cell = row.createCell(key);
	//				cell.setCellStyle(dataStyle);
	//				cell.setCellValue ((Double)map.get(key));
					row.createCell(key).setCellValue ((Long)map.get(key));
	        	}else if(map.get(key) instanceof Long){
	        		row.createCell(key).setCellValue ((Long)map.get(key));
	        	}else if(map.get(key) instanceof Integer){
	        		row.createCell(key).setCellValue ((Integer)map.get(key));
	        	}else{
	        		 row.createCell(key).setCellValue ((String)map.get(key));
	        	}
			}
			
		}
		
		//헤더 생성
		XSSFRow initRow = sheet.createRow(0);	
		for(int i = 0; header !=null && i <  header.length; i++){
			//셀 너비
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512 );
			if(sheet.getColumnWidth(i) < 2500) sheet.setColumnWidth(i, 2500);
			
			XSSFCell cell = initRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		// 틀고정
		sheet.createFreezePane(0, 1);
		
		rowMerge(sheet,1,1);
		
		response.setHeader("Content-Disposition", "ATTachment; Filename="+fileName+".xlsx");
		
	}
	
	/**
	 * @param sheet
	 * @param colum : 머지대상 컬럼 번호 0 ~
	 * @param initRow : 시작로우 0 ~
	 */
	public void rowMerge(XSSFSheet sheet, int colum, int initRow){
		
		int startRow = initRow;
		int endRow = 0;
		
		int rowNum = sheet.getPhysicalNumberOfRows();
		for (int i = initRow; i < rowNum; i++) {
			
			XSSFRow currentRow = sheet.getRow(i);
		   	XSSFCell currentCell = currentRow.getCell(colum); 
		   	String current = (currentCell != null) ? currentCell.toString() : "";	
		   	
			XSSFRow nextRow = sheet.getRow( i + 1 > rowNum-1 ? rowNum-1 : i+1 );
		   	XSSFCell nextCell = nextRow.getCell(colum); 
		   	
		   	String next = (nextCell != null) ? nextCell.toString() : "";	
		   	
			if( current.equals(next) ){
				//마지막 머지 삽질
				if (endRow == rowNum-1){
					sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, colum,colum)); 
				}
				endRow = i + 1;
			}else{
//				System.out.println("i : " + i + " startRow : " + startRow +" endRow :"+ endRow);
				sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, colum,colum)); 
				startRow = i + 1;
			}
		}
	}
	
}

