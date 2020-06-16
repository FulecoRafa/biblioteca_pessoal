public class Test {
  public static void main(String[] args) {
    Book b = new Book("Harry Potter", "J. K. Rolling", "Rocco", "Um livro de bruxos");
    System.out.println(b.name);
    b.name = "Harry Potter e a Câmara Secreta";
    System.out.println(b.name);
  }
}