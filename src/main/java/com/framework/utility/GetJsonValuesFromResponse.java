/**
 * 
 */
package main.java.com.framework.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import com.google.common.base.Joiner;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import de.taimos.totp.TOTP;
import io.restassured.response.Response;
import main.java.com.framework.constants.Constants.DownloadExcelFileHelperConstants;
import main.java.com.framework.constants.Constants.ExcelColumnNameConstant;
import main.java.com.framework.constants.Constants.InMemoryDatabaseHelperConstant;
import main.java.com.framework.inmemorydatabase.InMemoryDatabaseHelper;


/**
 * @author adeeb
 *
 */
public class GetJsonValuesFromResponse {
	InMemoryDatabaseHelper inMemoryDatabasehelperObj = new InMemoryDatabaseHelper();

	/*
	 * Get path name for storing in database.
	 */
	public String getJsonPath(String jsonPath, ExtentTest extentTest) {
		String result = "";
		try {
			result = jsonPath.substring(jsonPath.lastIndexOf('.') + 1).trim().toLowerCase();
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for getJsonPath:" + e);
		}
		return result;

	}

	/*
	 * Return List of String from response. Use for reading response
	 * dynamically.
	 * 
	 */

	public void extractStringList(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			List<String> listValues = JsonPath.read(response.getBody().asString(), jsonPath);
			String values = String.join(",", listValues);
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					values, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println("Values added to database: "+values);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractStringList:" + e);
		}
	}
	
	
	/*
	 * Return Number List from response. Use for reading response dynamically.
	 * 
	 */

	public void extractNumberList(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			List<Number> numberlist = JsonPath.read(response.getBody().asString(), jsonPath);
			String values = Joiner.on(',').join(numberlist);
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					values, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(values);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractNumberList:" + e);
		}
	}

	/*
	 * Return Long List from response. Use for reading response dynamically.
	 * 
	 */

	public void extractLongList(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			List<Long> longList = JsonPath.read(response.getBody().asString(), jsonPath);
			String values = Joiner.on(',').join(longList);
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					values, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(values);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractLongList:" + e);
		}
	}

	/*
	 * Return Number from response. Use for reading response dynamically.
	 * 
	 */
	public void extractNumber(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			Number numberValue = JsonPath.read(response.getBody().asString(), jsonPath);
			String number = numberValue.toString();
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					number, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println("CreatedDatain Database for number: "+number);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractNumber:" + e);
		}
	}
	public void extractTOTP(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String totpnumber =getTOTPCode();
			
			String path = "totp";
			//Number numberValue = JsonPath.read(response.getBody().asString(), jsonPath);
			//String number = numberValue.toString();
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					totpnumber, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println("CreatedDatain Database for number: "+totpnumber);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractNumber:" + e);
		}
	}
	
	public static String getTOTPCode() {
	    Base32 base32 = new Base32();
	    byte[] bytes = base32.decode(DownloadExcelFileHelperConstants.TOTPSECRETKEY);
	    String hexKey = Hex.encodeHexString(bytes);
	    return TOTP.getOTP(hexKey);
	}
	/*
	 * Return String from response. Use for reading response dynamically.
	 * 
	 */

	public void extractString(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			String value = JsonPath.read(response.getBody().asString(), jsonPath);
			System.out.println("Value tobe fetched from response: "+value);
			if(value.contains(",")) {
				value=value.replace(",", "");
			}
			if(value.contains(";")) {
				value=value.replace(";", "");
			}
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					value, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println("Value added to database: "+value);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractString:" + e);
		}
	}

	/*
	 * Return boolean value from response. Use for reading response dynamically.
	 * 
	 */
	public void extractBoolean(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			Boolean booleanValue = JsonPath.read(response.getBody().asString(), jsonPath);
			String value = String.valueOf(booleanValue);
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					value, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(value);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractBooleanValue:" + e);
		}
	}

	/*
	 * Return Long value from response. Use for reading response dynamically.
	 */
	public void extractLong(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			int longvValue = JsonPath.read(response.getBody().asString(), jsonPath);
			System.out.println("Value tobe fetched from response: "+longvValue);
			
			String value = Integer.toString(longvValue);
			
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					value, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(value);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractLong:" + e);
		}
	}
	
	public void extractCalculatedValue(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			//String path = "calculatedValue";
			System.out.println("path for calculation: "+path);
			String[] alljson = jsonPath.split("-");
			String firstjsonpath = jsonPath.split("-")[0];
			String secondjsonpath = jsonPath.split("-")[1];
				
			int firstlongvValue = JsonPath.read(response.getBody().asString(), firstjsonpath);
			System.out.println("Value from substract: "+firstlongvValue);
			
			int lastlongvValue = JsonPath.read(response.getBody().asString(), secondjsonpath);
			System.out.println("Value to substract: "+lastlongvValue);
			int finalValueToWrite = firstlongvValue-lastlongvValue;
			String value = Integer.toString(finalValueToWrite);
			
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					value, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(value);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractcalculatedValue:" + e);
		}
	}

	/*
	 * Return List of array list from response. Use for reading response
	 * dynamically.
	 * 
	 */

	public void extractListOfLists(Response response, String jsonPath, LinkedHashMap<String, String> data,
			ExtentTest extentTest, String sheetName) {
		try {
			String path = getJsonPath(jsonPath, extentTest);
			List<ArrayList<String>> arrayList = JsonPath.read(response.getBody().asString(), jsonPath);
			String values = Joiner.on(',').join(arrayList);
			inMemoryDatabasehelperObj.createData(data.get(ExcelColumnNameConstant.TESTID.toString()), sheetName, path,
					values, InMemoryDatabaseHelperConstant.TABLENAME.toString());
			System.out.println(values);
		} catch (PathNotFoundException e) {
			extentTest.log(LogStatus.SKIP, "Path Not Found :" + e);
		} catch (Exception e) {
			extentTest.log(LogStatus.ERROR, "Exception for extractListWithJsonPathForListOfLists:" + e);
		}

	}

}