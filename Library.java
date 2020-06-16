import java.util.HashMap;

// Acessible books for now
public class Library {
  
  private int page; // Page accessed in Library
  private int pageSize; // Number of books per page
  private HashMap<String, Book> library; // List of books, with name as key, for search purposes
  private ExcelMask em; // File accessed by library
  private int size = 200; // Max library size before reaccess xlsx file

  // Constructor
  public Library(ExcelMask em){
    this.page = 0;
    Book[] books = em.getBooks(0, this.size);
    this.renovateLibrary(books);
    this.em = em;
  }

  // Adds book if there is still space in library
  public boolean addBook(Book b){
    if(library.size() >= this.size) return false;
    library.put(b.name, b);
    return true;
  }

  // Search book in library and, if not found, searches xlsx file
  public Book searchBook(String name){
    Book res = library.get(name);
    if(res != null) return res;
    Book[] newBooks = em.searchBook(name, this.size);
    if(newBooks.length != 0 ){
      this.renovateLibrary(newBooks);
    }else return null;
  }

  // Gets new Books from xlsx file and repopulates library
  public void renovateLibrary(Book[] newBooks){
    this.clearLibrary();
    for(Book b: newBooks){
      this.addBook(b);
    }
  }

  // Clears library to repopulate
  public void clearLibrary(){
    library.clear();
  }

}