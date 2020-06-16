// Encapsulate Book object
public class Book{

  public String name;
  public String author;
  public String publish;
  public String description;
  public float rate;
  public boolean bought;
  public boolean read;

  // Constructors
  public Book( String name,
               String author,
               String publish,
               String description,
               float rate,
               boolean bought,
               boolean read){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.rate = rate;
                this.bought = bought;
                this.read = read;
  }

  public Book( String name,
               String author,
               String publish,
               String description,
               float rate,
               boolean bought){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.rate = rate;
                this.bought = bought;
                this.read = false;
  }

  public Book( String name,
               String author,
               String publish,
               String description,
               float rate){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.rate = rate;
                this.bought = false;
                this.read = false;
  }

  public Book( String name,
               String author,
               String publish,
               String description){
                this.name = name;
                this.author = author;
                this.publish = publish;
                this.description = description;
                this.rate = -1;
                this.bought = false;
                this.read = false;
  }
}