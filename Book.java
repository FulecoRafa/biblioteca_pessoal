// Encapsulate Book object
public class Book{

  public String name;
  public String author;
  public String publish;
  public String description;
  public int collection;
  public book_t type;
  public int rate;
  public boolean bought;
  public boolean read;

  // Constructors
  public Book( String name,
               String author,
               String publish,
               String description,
               book_t type,
               int rate,
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
               int rate,
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
               int rate){
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

  public String toString(){
    return String.format("%s\n%s -> %s\n%s %d\nRead: %s   Bought: %s\n%s\n", this.name, this.author, this.publish, this.type.toString(), this.rate, (this.read)? "✔": "X", (this.bought)? "✔": "X", this.description);
  }
}