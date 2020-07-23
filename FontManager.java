
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FontManager {
	
	XSSFColor red, green, gray, blueish;
	String fontName;
	short fontSize;
	Font font;
	CellStyle set;
	
	//	Default setup
	public FontManager(XSSFWorkbook workbook) {
		fontName = "Calibri";
		fontSize = 11;
		this.font = workbook.createFont();
		set = workbook.createCellStyle();
		colorDefiner(workbook);
	}
	
	//	Change font with default size
	public FontManager(XSSFWorkbook workbook, String font) {
		fontName = font;
		fontSize = 11;
		this.font = workbook.createFont();
		set = workbook.createCellStyle();
		colorDefiner(workbook);
	}
	
	//	Change size with default font
	public FontManager(XSSFWorkbook workbook, short size) {
		fontName = "Calibri";
		fontSize = size;
		this.font = workbook.createFont();
		set = workbook.createCellStyle();
		colorDefiner(workbook);
	}
	
	//	Change both font and size
	public FontManager(XSSFWorkbook workbook, String font, short size) {
		fontName = font;
		fontSize = size;
		this.font = workbook.createFont();
		set = workbook.createCellStyle();
		colorDefiner(workbook);
	}
	
	public void colorDefiner(XSSFWorkbook workbook) {
		//byte[] redder = new byte[] {(byte) 237, (byte) 164, (byte) 159};
		
		this.red = new XSSFColor(workbook.getStylesSource().getIndexedColors());
		this.green = new XSSFColor(workbook.getStylesSource().getIndexedColors());
		this.gray = new XSSFColor(workbook.getStylesSource().getIndexedColors());
		this.blueish = new XSSFColor(workbook.getStylesSource().getIndexedColors());
		
		this.red.setARGBHex("f7ddd7");
		this.green.setARGBHex("d7f7d9");
		this.gray.setARGBHex("707070");
		this.blueish.setARGBHex("293440");
		
		return;
	}
	
	//	Usual procedure for font creation
	private CellStyle procedure(XSSFWorkbook workbook, boolean status, boolean read) {
		CellStyle novo = workbook.createCellStyle();
		font = workbook.createFont();
		
		//	Font size set to 11 by default
		font.setFontHeightInPoints(fontSize);
		//	Set to style (Default: Calibri)
	    font.setFontName(fontName);
	    //	Create a box for it
		createBorder(novo);
		
		novo.setFont(font);
		
		set = novo;
		
		set.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		if(!status) {
			//	If not owned
			((XSSFCellStyle) set).setFillForegroundColor(this.gray);
	    } else if(read) {
	    	//	If owned and read
	    	((XSSFCellStyle) set).setFillForegroundColor(this.green);
	    } else {
	    	//	If owned and not read
	    	((XSSFCellStyle) set).setFillForegroundColor(this.red);
	    }
		
		set.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		return set;
	}
	
	private CellStyle procedure(XSSFWorkbook workbook) {
		CellStyle novo = workbook.createCellStyle();
		font = workbook.createFont();
		
		//	Font size set to 11 by default
		font.setFontHeightInPoints((short) (fontSize + 5));  
		//	Set to style (Default: Calibri)
	    font.setFontName(fontName);
	    //	Create a box for it
		createBorder(novo);
		
		novo.setFont(font);
		
		set = novo;
		
		set.setVerticalAlignment(VerticalAlignment.CENTER);
		
		((XSSFCellStyle) set).setFillForegroundColor(this.blueish);
		set.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		return set;
	}
	
	//	First cell
	public CellStyle indexStyle(XSSFWorkbook workbook) {
		CellStyle set = procedure(workbook);
		
		//	Display text in the center
		set.setAlignment(HorizontalAlignment.CENTER);
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		
		return set;
	}
	
	//	Middle cells
	public CellStyle titleStyle(XSSFWorkbook workbook, boolean status, boolean read) {
		CellStyle set = procedure(workbook, status, read);
		
		//	Display text to the left
		set.setAlignment(HorizontalAlignment.LEFT);
		set.setWrapText(true);
	    
		if(!status) {
			//	If not owned
	    	font.setColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    } else if(read) {
	    	//	If owned and read
	    	font.setColor(IndexedColors.GREEN.getIndex());
	    } else {
	    	//	If owned and not read
	    	font.setColor(IndexedColors.RED.getIndex());
	    }
		
	    return set;
	}
	
	//	Condition cells
	public CellStyle condStyle(XSSFWorkbook workbook, boolean status, boolean read) {
		CellStyle set = procedure(workbook, status, read);

		//	Display text to the center
		set.setAlignment(HorizontalAlignment.CENTER);
		
		if(read) {
			font.setColor(IndexedColors.GREEN.getIndex());
		} else if(!status) {
			font.setColor(IndexedColors.GREY_25_PERCENT.getIndex());
		} else {
			//font.setColor(IndexedColors.GOLD.getIndex());
			font.setColor(IndexedColors.RED.getIndex());
		}
	        
	    return set;
	}
	
	//	Type cells
	public CellStyle typeStyle(XSSFWorkbook workbook, String status) {
		CellStyle set = procedure(workbook);

		//	Display text to the center
		set.setAlignment(HorizontalAlignment.CENTER);
		
		if(status == "físico") {
			font.setColor(IndexedColors.AQUA.getIndex());
		} else {
			font.setColor(IndexedColors.ORANGE.getIndex());
		}
		
		font.setBold(true);
	        
	    return set;
	}
	
	//	Status cells
	public CellStyle statusStyle(XSSFWorkbook workbook, boolean status, boolean read) {
		CellStyle set = procedure(workbook, status, read);

		//	Display text to the center
		set.setAlignment(HorizontalAlignment.CENTER);
		
	    if(!status) {
	    	//	If not owned
	    	font.setColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    } else if(read) {
	    	//	If owned and read
	    	font.setColor(IndexedColors.GREEN.getIndex());
	    } else {
	    	//	If owned and not read
	    	font.setColor(IndexedColors.RED.getIndex());
	    }
		
	    return set;
	}
	
	//	Creates the box-like borders
	private void createBorder(CellStyle change) {
		change.setBorderBottom(BorderStyle.THIN);
		change.setBorderLeft(BorderStyle.THIN);
		change.setBorderRight(BorderStyle.THIN);
		change.setBorderTop(BorderStyle.THIN);
	}
}
