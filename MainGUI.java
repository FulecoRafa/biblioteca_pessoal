import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.BorderFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Sets main menu GUI
public class MainGUI extends JFrame implements ActionListener{

  private ExcelManager Excel;
  private Vector<Book> books;
  private int page = 0;

  MainGUI(ExcelManager EM){
    super("Biblioteca Pessoal");
    this.setLayout(new GridLayout(2, 1));
    this.setSize(1280, 720);	//Tamanho da janela
    //this.setTitle("Biblioteca Pessoal");	//Nome da janela
    this.setLocationRelativeTo(null);	//Abrir centralizada
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Finalizar quando sair
    this.setVisible(true);	//Fazer a janela aparecer
    
    this.Excel = EM;
    this.books = EM.obtainBookBlock(this.page);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        Excel.shutDown();
      }
    });

    // Menu bar
    JMenuBar menu = new JMenuBar();

    // Menu
    JMenu arquivo = new JMenu("Arquivo");
    // Itens
    JMenuItem newBook = new JMenuItem("Adicionar livro");
    newBook.addActionListener(this);
    newBook.setActionCommand("addBook");

    JMenuItem export = new JMenuItem("Exportar livros");
    export.addActionListener(this);
    export.setActionCommand("exportXLSX");

    JMenuItem close = new JMenuItem("Fechar");
    close.addActionListener(this);
    close.setActionCommand("close");

    // Adding items
    arquivo.add(newBook);
    arquivo.add(export);
    arquivo.add(new JSeparator());
    arquivo.add(close);

    // Adding menu
    menu.add(arquivo);

    // Top items
    JPanel top = new JPanel();

    // Title
    JLabel title = new JLabel("Seus Livros | ");
    title.setFont(title.getFont().deriveFont(25.0f));
    top.add(title);

    // Search bar
    JTextField searchBar = new JTextField();
    searchBar.setFont(searchBar.getFont().deriveFont(20.0f));
    searchBar.setPreferredSize(new Dimension(200, 20)); // Setting text area dimensions

    // Search button
    JButton searchButton = new JButton("Procurar");
    searchButton.setFont(searchButton.getFont().deriveFont(20.0f));

    // Adding components
    top.add(searchBar);
    top.add(searchButton);
    
    // Bottom items
    JPanel bottom = new JPanel();
    JScrollPane sp = new JScrollPane(bottom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    bottom.setLayout(new GridLayout(0, 1, 0, 0));
    bottom.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    
    bottom.add(new BookTile(new Book("Harry Potter", "JK Rowling", "Editora Panini", "A história de dois aventureiros, o tigre Evan e a raposa Amélia, no início de sua jornada mundo afora em busca do lar perfeito.", book_t.FISICO, 2, true, false), 5, bottom.getWidth()));
    // Iterator<Book> it = this.books.iterator();
    // while(it.hasNext()){
      //   bottom.add(new BookTile(it.next()));
      // }
      
    bottom.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);

    // Adding parts
    this.setJMenuBar(menu);
    this.add(top);
    this.add(sp);

    JPanel panelButtons = new JPanel();
    JButton prior = new JButton("<");
    JButton next = new JButton(">");
    
    panelButtons.add(prior);
    panelButtons.add(next);
    
    this.add(panelButtons, BorderLayout.SOUTH);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if("close".equals(e.getActionCommand())){
      this.Excel.shutDown();
      System.exit(0);
    }else if("addBook".equals(e.getActionCommand())){
      BookForm bf = new BookForm("Novo livro");
      bf.render();
      Vector<Book> vec = new Vector<Book>();
      System.out.println("Vec created");
      vec.add(bf.getBook());
      Excel.insertBooks(vec);
    }else if("exportXLSX".equals(e.getActionCommand())){

      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FileFilter(){
      
        @Override
        public String getDescription() {
          return "*.xlsx";
        }
      
        @Override
        public boolean accept(File f) {
          return  f.isDirectory();
        }
      });

      if(fc.showSaveDialog(null) == 0){
        try{
          FileOutputStream fos = new FileOutputStream(fc.getSelectedFile()+".xlsx");
          File readFile = new File("livros.xlsx");
          System.out.println(readFile);
          FileInputStream fileReader = new FileInputStream(readFile);
          byte[] data = Files.readAllBytes(readFile.toPath());
          fos.write(data);
          fos.close();
          fileReader.close();
        }catch(Exception exc){
          System.out.println("Failed to write file");
        }
      }

    }else{
      System.out.println("Listened to: " + e.getActionCommand());
    }

  }

  public void render(){
    this.setSize(1200, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void updateBooks(int page){
    Vector<Book> temp = Excel.obtainBookBlock(page);
    if(temp.size() < 1) return;
    this.page = page;
    this.books = temp;
  }

}