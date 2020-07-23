import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManager {
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	
	String fileName;
	
	//	Default parameters
	int numSheets;
	int numRows;
	int numCells;
	//	Number of rows returned for each read call
	int pageSize;
	
	Pattern pattern;
	Matcher matcher;
	Vector<Integer> v = new Vector<Integer>();
	
	//	Filename (Verif.)
	private String getFilename() {
		String set = "Biblioteca.xlsx";
		this.fileName = set;
		return set;
	}
	
	//	Open Excel definitive edition??? (Verif.)
	public ExcelManager() {
		
		this.fileName = getFilename();
		
		//	This will try opening the file with said name
		try {
		workbook = new XSSFWorkbook(new FileInputStream(this.fileName));
		numSheets = workbook.getNumberOfSheets();
		
		} catch (FileNotFoundException e){
			//	If it doesn't exist, it will create another FROM SCRATCH
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("Biblioteca");
			createHeader();
			
		} catch (IOException e) {
			//	This one just catches whatever other error it may occur
			System.out.println("Failed to process file.");
		}
		
		setSheet(0);
		
		//	Unknown numRows before first run through
		numRows = 0;
		numCells = 9;
		pageSize = 10;
		
	}
	
	//	SETTER: Switch between sheets (Verif.)
	private void setSheet(int reference) {
		
		if(reference < numSheets) {
			sheet = workbook.getSheetAt(reference);
		} else {
			System.out.println("Unable to complete task.");
		}
		
		return;
	}
	
	//	Creates the header for the file. Meant to be used only once (Verif.)
	public void createHeader() {
		String[] header = new String[] {"Qnt", "Livro", "Autor", "Editora", "Descrição", "Tipo", "Avaliação", "Status", "Leitura"}; 
		FontManager f = new FontManager(workbook);
		
		row = sheet.createRow(0);
		try {
			cell = row.getCell(0);
			cell.getStringCellValue();
			
		} catch (NullPointerException e) {
			for(int count = 0; count < 9; count++) {
				cell = row.createCell(count);
				cell.setCellValue(header[count]);
				cell.setCellStyle(f.indexStyle(workbook));
			}
			
			sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, 8));
			
			sheet.setColumnWidth(0, 2890);
			sheet.setColumnWidth(1, 7500);
			sheet.setColumnWidth(2, 7500);
			sheet.setColumnWidth(3, 7500);
			sheet.setColumnWidth(4, 10000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 5500);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 4000);
			
			numSheets = 1;
			
			//	Unknown numRows before first read
			numRows = 0;
			numCells = 9;
			pageSize = 7;
			
			return;
		}
		
		return;
	}
	
	//	Close Excel (Saves on default name determined when opening the file) (Verif.)
	public void shutDown() {
		try {
			workbook.write(new FileOutputStream(this.fileName));
			workbook.close();
		} catch (IOException e) {
			System.out.println("Failed to process file.");
		}
		
		return;
	}
	
	//	Inserts a book from a VECTOR of books
	public void insertBooks(Vector<Book> b) {
		Iterator<Book> it = b.iterator();
		FontManager f = new FontManager(workbook);
		int pos = 0;
		
		if(this.numRows == 0) {
			pos = getExcelEOF();
		} else {
			pos = this.numRows-1;
		}
		
		while(it.hasNext()) {
		
			//	Acquire row of insertion
			row = sheet.createRow(pos);
			//	Create a new writeable cell in excel
			cell = row.createCell(0);
			//	Write in cell
			cell.setCellValue(pos);
			//	Set created cell to be of 'index' cellstyle
			cell.setCellStyle(f.indexStyle(workbook));
			
			//	Call function to initate line insertion procedure
			insertLine(it.next());
			
			//	Select next book from array
			pos++;
			//	Update this file's number of rows
			this.numRows++;
		}
		return;
	}
	
	//	GETTER: Seeks the last row of the file (Verif.)
	private int getExcelEOF() {
		int rowNumber, c;
		boolean flag = false;
		
		for(rowNumber = 1; !flag; rowNumber++) {
			row = sheet.getRow(rowNumber);
			try {
				cell = row.getCell(0);
				c = (int) cell.getNumericCellValue();
				if(c < rowNumber-1) {
					flag = true;
					rowNumber--;
				}
			} catch(NullPointerException e) {
				flag = true;
				rowNumber--;
			}
			
		}
		
		//	Updates known value for number of rows
		this.numRows = rowNumber;
		
		return this.numRows;
	}
	
	//	Insert a formatted line in correspondence to the current project (Verif.)
	void insertLine(Book b) {
		FontManager f = new FontManager(workbook);
		boolean bType;

		//	Book name
		cell = row.createCell(1);
		cell.setCellValue(b.name);
		cell.setCellStyle(f.titleStyle(workbook, b.bought, b.read));
		
		//	Book author
		cell = row.createCell(2);
		cell.setCellValue(b.author);
		cell.setCellStyle(f.titleStyle(workbook, b.bought, b.read));
		
		//	Book rating
		cell = row.createCell(3);
		cell.setCellValue(b.publish);
		cell.setCellStyle(f.titleStyle(workbook, b.bought, b.read));

		//	Book description
		cell = row.createCell(4);
		cell.setCellValue(b.description);
		cell.setCellStyle(f.titleStyle(workbook, b.bought, b.read));
		
		//	Book type
		cell = row.createCell(5);
		if(b.type.toString() == "físico") {
			cell.setCellValue("Físico");
			cell.setCellStyle(f.typeStyle(workbook, b.type.toString()));
			bType = true;
		} else {
			cell.setCellValue("Online");
			cell.setCellStyle(f.typeStyle(workbook, b.type.toString()));
			bType = false;
		}
		
		//	Book rating
		cell = row.createCell(6);
		cell.setCellValue(b.rate + " Estrelas");
		cell.setCellStyle(f.typeStyle(workbook, b.type.toString()));
		
		//	Book bought
		cell = row.createCell(7);
		if(b.bought == true) {
			if(bType) {
				cell.setCellValue("Comprado");
			} else {
				cell.setCellValue("Baixado");
			}
		} else {
			cell.setCellValue("Faltando");
		}
		cell.setCellStyle(f.statusStyle(workbook, b.bought, b.read));

		//	Book read
		cell = row.createCell(8);
		if(b.read) {
			cell.setCellValue("Feita");
		} else {
			cell.setCellValue("Não feita");
		}
		cell.setCellStyle(f.statusStyle(workbook, b.bought, b.read));
	
		return;
	}

	//	Obtain a page of books, rather than a set number from a certain page
	Vector<Book> obtainBookBlock(int page) {
		return obtainBookBlock(0, pageSize, page);
	}
	
	//	Obtain a set number of books from a certain page
	Vector<Book> obtainBookBlock(int spos, int epos, int page) {
		int total = epos-spos,
		    location = (page*pageSize) + 1 +spos;
		Vector<Book> b = new Vector<Book>();
		Book re;
		boolean flag = false;
		
		//	Loop that obtains a line and processes it into the format of a book
		for(int count = location; (count < (location + total)) && !flag; count++) {
			re = obtainLine(count);
			if(re != null) {
				b.add(re);
			} else {
				flag = true;
			}
		}
		
		return b;
	}
	
	//	Obtains one line from the excel file
	Book obtainLine(int line) {
		Book b;
		String name, author, publish, description, aux;
		int rate = -1, c = 0;
		boolean bought, read, flag = false;
		
		row = sheet.getRow(line);
		try {
			cell = row.getCell(0);
			c = (int) cell.getNumericCellValue();
		} catch(NullPointerException e) {
			flag = true;
			numRows = c;
		}
		if(flag) {
			return null;
		}
		
		//	Obtains first four Strings
		name = row.getCell(1).getStringCellValue();
		author = row.getCell(2).getStringCellValue();
		publish = row.getCell(3).getStringCellValue();
		description = row.getCell(4).getStringCellValue();
		
		//	Obtains number of stars
		aux = row.getCell(6).getStringCellValue();
		rate = Character.getNumericValue(aux.charAt(0));
		
		//	Obtains bought or not
		aux = row.getCell(7).getStringCellValue();
		bought = isBought(aux);
		
		//	Obtains read or not
		aux = row.getCell(8).getStringCellValue();
		read = isRead(aux);
		
		//	Defines book as physical or online and creates them
		if((aux = row.getCell(5).getStringCellValue()) == "Físico") {
			b = new Book(name, author, publish, description, book_t.FISICO, rate, bought, read);
		} else {
			b = new Book(name, author, publish, description, book_t.ONLINE, rate, bought, read);
		}
		
		//	Retrieval of said information
		return b;
	}
	
	//	Returns if a book is bought or not. Used only within class (Verif.)
	private boolean isBought(String s) {
		if(s == "Comprado" || s == "Baixado") {
			return true;
		}
		return false;
	}
	
	//	Returns if a book is read or not. Used only within class (Verif.)
	private boolean isRead(String s) {
		if(s == "Feita") {
			return true;
		}
		return false;
	}

	//	Print for debugging (Vector)
	void debugPrint(Vector<Book> book) {
		Iterator<Book> it = book.iterator();
		Book b;
		
		while(it.hasNext()) {
			b = it.next();
			
			System.out.println("Livro: " + b.name);
			System.out.println("Autor: " + b.author);
			System.out.println("Publicadora: " + b.publish);
			System.out.println("Descrição: " + b.description);
			
			if(b.type == book_t.FISICO) {
				System.out.println("Tipo: Físico");
			} else{
				System.out.println("Tipo: Online");
			}
			
			System.out.println("Nota: " + b.rate + " estrelas");
			
			if(b.bought == true) {
				System.out.println("Situação: Disponível");
			} else {
				System.out.println("Situação: Faltando");
			}
			
			if(b.read == true) {
				System.out.println("Status: Lido");
			} else {
				System.out.println("Status: Não lido");
			}
			
			System.out.printf("\n\n");
		}
		
		return;
	}
	
	//	Research from excel
	public Vector<Book> research(String search, int field, boolean edit) {
		Vector<Book> rValue = new Vector<Book>();
		Book compare;
		boolean flag = false;
		
		//	Set pattern for RegEx
		pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
		
		//	Runs through excel file
		for(int count = 1; !flag; count++) {
			
			//	Extracts a book line from excel
			compare = obtainLine(count);
			
			if(compare == null) {
				return rValue;
			}
			
			//	According to field, compare said field (can only be 1 or 0, aka book author or book name)
			switch(field) {
			case 0:
				matcher = pattern.matcher(compare.name);
				break;
			case 1:
				matcher = pattern.matcher(compare.author);
				break;
			case 2:
				matcher = pattern.matcher(compare.publish);
				break;
			default:
				//	Invalid option
				System.out.println("Opção inválida");
				return null;
			}
			
			//	If the book is found, then add it to the returning Vector
			if(matcher.find()) {
				//	Adds book to Vector
				rValue.add(compare);
				
				if(edit) {
					//	Adds place of existence
					v.add(count);
				}
			}
		}
		
		//	Returns Vector with all matches found
		return rValue;
	}
	
	//	Call this function to edit the book
	public void callBack(Book edited, int locale) {
		Iterator<Integer> it = v.iterator();
		int a = 1;
		
		//	Obtains placement of change
		for(int count = 0; count <= locale && it.hasNext(); count++) {
			a = it.next();
		}
		
		row = sheet.getRow(a);
		insertLine(edited);
		
		v.clear();
		
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
