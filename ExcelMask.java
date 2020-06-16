public class ExcelMask {
  
  private String fileName;

  public ExcelMask(String fileName){
    this.fileName = fileName + ".xml";
    // Open xml file with fileName
  }

  // Gets 'size' books of xml file starting from 'rrn' entry
  public Book[] getBooks(int rrn, int size){
    
  }

  // Searches for books with name 'name' and returns an array of them
  public Book[] searchBooks(String name, int size){
    Book[] searchedBooks = new Book[size];

  }

  // Closes xml file
  public boolean close(){

  }
}