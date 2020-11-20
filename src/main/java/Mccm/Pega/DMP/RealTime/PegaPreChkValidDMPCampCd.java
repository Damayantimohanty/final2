package Mccm.Pega.DMP.RealTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import Mccm.Pega.Outbound.PegaTestBase.TestBase;
import Mccm.Pega.QAUtil.TestUtil;
import Mccm.Pega.excel.utility.Excel_Reader;
 

 

 
 

public class PegaPreChkValidDMPCampCd extends TestBase  {

	public static String ExcelFilePath;


	@FindBy(xpath="(//i[@class='pi pi-caret-down'])[2]")
	WebElement ClkPegaMrkt;

	@FindBy(xpath="(//span[@class='menu-item-title'])[5]")
	WebElement pegamrkting1;

	@FindBy(xpath="//a[@id='appview-nav-toggle-one']")
	WebElement ExpandPegMrkPage;

	@FindBy(xpath="//span[text()='Campaigns']")
	WebElement Campaigns;

	@FindBy(xpath="//*[@name='PortalCardListingHeader_pyLanding_264']")
	WebElement Filter;


	@FindBy(xpath="//input[@type='text'][@name='$PTempModalCardContainerPage$pFilterCriteriaDisplay$ppyValue']")
	WebElement CampaignVlSrch;


	@FindBy(xpath="(//button[text()='View'])[1]")
	WebElement View;

	@FindBy(xpath="//div[text()='There are no results returned, please try a new search term.']")
	WebElement DisplayMsg;





	Excel_Reader obj= new Excel_Reader(ExcelFilePath+"/src/main/java/Mccm/Pega/TestData/PegaTestData.xlsx");
 	
	String DMPCampValue = obj.getCellValue("PegaTestData", 1, 18);




	public PegaPreChkValidDMPCampCd( ) {
		PageFactory.initElements(driver, this);
	}

	public void pegamarkting() throws InterruptedException
	{
		Thread.sleep(6000);
		pegamrkting1.click( );
		Thread.sleep(6000);
	}


	public void ExpandPegMrkPage() throws InterruptedException
	{
		Thread.sleep(6000);
		ExpandPegMrkPage.click( );
		Thread.sleep(6000);
	}

	public void Campaigns() throws InterruptedException
	{
		Thread.sleep(6000);
		Campaigns.click( );
		Thread.sleep(6000);
	}


	public void Filter() throws InterruptedException
	{
		Thread.sleep(6000);
		Filter.click( );
		Thread.sleep(6000);
	}

	public void CampaignVlSrch() throws InterruptedException
	{
		Thread.sleep(6000);
		CampaignVlSrch.sendKeys(DMPCampValue);
		Thread.sleep(6000);
		System.out.println(DMPCampValue);
	}
	public void View() throws InterruptedException
	{
		Thread.sleep(6000);
		View.click( );
		Thread.sleep(6000);
	}
	public void PreChkValdDMPCampCd() throws InterruptedException
	{
		for(int i=0;i<1;i++){

			String DMPCampCodeExits = "";

			try
			{
				WebElement w2=driver.findElement(By.xpath("//div[text()='There are no results returned, please try a new search term.']"));
				Thread.sleep(8000);
				DMPCampCodeExits= w2.getText();

				System.out.println(DMPCampCodeExits);

				//			Assert.assertEquals(NBACampValueExits, "There are no results returned, please try a new search term.");

				System.out.println(DMPCampValue   +  " is Valid DMP Camp Code ");

			}catch(Exception e){

			}

			if (DMPCampCodeExits.equals("There are no results returned, please try a new search term.")) {

				break;
			}
			else
			{

				Excel_Reader obj1= new Excel_Reader(ExcelFilePath+"/src/main/java/Mccm/Pega/TestData/PegaTestData.xlsx");

				String DMPCampCode = obj1.getCellValue("PegaTestData", 2, 18);

				System.out.print(DMPCampCode + "  ");

				try {

					InputStream inp = new FileInputStream(ExcelFilePath+"/src/main/java/Mccm/Pega/TestData/PegaTestData.xlsx");


					//	Workbook wb = WorkbookFactory.create(inp);
					XSSFWorkbook wb = new XSSFWorkbook(inp);
					XSSFSheet sheet = wb.getSheet("PegaTestData");
					Row row = sheet.getRow(1);
					Cell cell = row.getCell(18);
					if (cell == null)
						cell = row.createCell(18);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(DMPCampCode);

					// Write the output to a file
					FileOutputStream fileOut = new FileOutputStream(ExcelFilePath+"/src/main/java/Mccm/Pega/TestData/PegaTestData.xlsx");
					wb.write(fileOut);
					fileOut.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

}







