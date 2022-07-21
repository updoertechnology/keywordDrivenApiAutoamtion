/**
 * 
 */
package main.java.com.framework.utility;

import main.java.com.framework.commans.Commans.FactoryHelperCommans;
import main.java.com.framework.constants.Constants.DataUtilConstants;
import main.java.com.framework.constants.Constants.FactoryHelperConstants;
import main.java.com.framework.epochdate.EpochDate;
import main.java.com.framework.inmemorydatabase.InMemoryDatabaseHelper;

/**
 * @author nikhil
 *
 */
public class FactoryHelper {

	public void downloadFile() {
		try {
			DownloadExcelFileHelper.initialMethod();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createTable() {
		try {
			// TODO Auto-generated method stub
			InMemoryDatabaseHelper inMemoryDatabasehelperObj = new InMemoryDatabaseHelper();
			inMemoryDatabasehelperObj.createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addEpochDates(String sheetName) {
		try {
			EpochDate epochDateObj = new EpochDate(sheetName);
			epochDateObj.insertStartToday();
			epochDateObj.insertEndToday();
			epochDateObj.insertStartYesterday();
			epochDateObj.insertEndYesterday();
			epochDateObj.insertStartThisWeek();
			epochDateObj.insertStartSevenDays();
			epochDateObj.insertStartThisMonth();
			epochDateObj.insertStartLastMonth();
			epochDateObj.insertEndLastMonth();
			epochDateObj.insertStartCustomRange();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getRunnableSheets(String testcasecontrollerSheet) {
		try {
			ExcelReader excelread = new ExcelReader();
			//String sheetName = FactoryHelperConstants.SHEETNAME.toString();
			int rows = excelread.getLastRowNumber(testcasecontrollerSheet);
			for (int i = 1; i < rows; i++) {
				if (excelread.getCellData(testcasecontrollerSheet, 1, i).equalsIgnoreCase(DataUtilConstants.RUNMODE.toString())) {
					FactoryHelperCommans.sheetsName.add(excelread.getCellData(testcasecontrollerSheet, 0, i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getRunnableSuite() { 
		String testcasecontrollerSheet = null;
			
		//try{
			ExcelReader excelread = new ExcelReader();
		
			String suiteName = FactoryHelperConstants.SUITENAME.toString();
			int rows = excelread.getLastRowNumber(suiteName);
			for (int i = 1; i < rows; i++) {
				if (excelread.getCellData(suiteName, 1, i).equalsIgnoreCase(DataUtilConstants.RUNMODE.toString())) {
					testcasecontrollerSheet = excelread.getCellData(suiteName, 0, i);
					break;
					//FactoryHelperCommans.sheetsName.add(excelread.getCellData(sheetName, 0, i));
				}
			}
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return testcasecontrollerSheet;
	}

}
