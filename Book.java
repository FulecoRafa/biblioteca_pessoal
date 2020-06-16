// Encapsulate Book object
public class Book{

  enum book_t{
    FISICO("físico"),
    ONLINE("online");

    private String type_name;

    book_t(String type_name){
      this.type_name = type_name;
    }

    public String toString(){
      return this.type_name;
    }
  };

  public String name;
  public String author;
  public String publish;
  public String description;
  public int collection;
  public book_t type;
  public float rate;
  public boolean bought;
  public boolean read;

  // Constructors
  public Book( String name,
               String author,
               String publish,
               String description,
               book_t type,
               float rate,
               boolean bought,
               boolean read){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.type = type;
                this.rate = rate;
                this.bought = bought;
                this.read = read;
  }

  public Book( String name,
               String author,
               String publish,
               String description,
               book_t type,
               float rate,
               boolean bought){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.type = type;
                this.rate = rate;
                this.bought = bought;
                this.read = false;
  }

  public Book( String name,
               String author,
               String publish,
               String description,
               book_t type,
               float rate){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.type = type;
                this.rate = rate;
                this.bought = false;
                this.read = false;
  }

  public Book( String name,
               String author,
               String publish,
               String description,
               book_t type){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.type = type;
                this.rate = -1;
                this.bought = false;
                this.read = false;
  }
}