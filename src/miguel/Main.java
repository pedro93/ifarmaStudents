package miguel;

import gui.Window;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import resources.Database;

public class Main {
	static Database DB = null;
	public static void main(String [] args)
	{
		DB = Database.getInstance();
		runGui();
	}

	public static void load(String FilePath) throws IOException,NullPointerException 
	{
		String group = null;
		String subGroup = null;
		String drug=null;
		String action=null;

		InputStream ExcelFileToRead = new FileInputStream(FilePath);

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet = wb.getSheetAt(0);

		/*Ignore row 0 which has Name of column*/
		int size = sheet.getLastRowNum();
		for(int i=1;i<size;i++)
		{
			Row row =sheet.getRow(i);
			Cell cell1 = row.getCell(1);
			Cell cell2 = row.getCell(2);
			Cell cell3 = row.getCell(3);
			Cell cell4 = row.getCell(4);
			if(cell1!=null && cell1.getCellType() != Cell.CELL_TYPE_BLANK )
			{
				group = cell1.toString();
			}
			if(cell2!=null && cell2.getCellType() != Cell.CELL_TYPE_BLANK )
			{
				subGroup = cell2.toString();
			}
			if(cell3!=null && cell3.getCellType() != Cell.CELL_TYPE_BLANK )
			{
				drug = cell3.toString();
			}
			if(cell4==null)
				action="";
			else if(cell4.getCellType() != Cell.CELL_TYPE_BLANK )
				action = cell4.toString();
			if(cell1==null || cell2==null ||cell3==null)
			{System.out.println("ATTENTION in line :"+i);continue;}
			DB.addDrug(group, subGroup, drug, action);
			
			//System.out.println(group+" | "+subGroup+" | "+drug+" | "+action);
			
		}
	}
	private static void runGui()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
