// Main class of program
public class BibliotecaPessoal {
  public static void main(String[] args) {
    ExcelManager EM = new ExcelManager();
    MainGUI gui = new MainGUI(EM);
    gui.render();
  }
}