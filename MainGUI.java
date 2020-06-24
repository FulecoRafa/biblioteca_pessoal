import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.GridLayout;

// Sets main menu GUI
public class MainGUI extends JFrame implements ActionListener{

  MainGUI(){
    super("Biblioteca Pessoal");
    this.setLayout(new GridLayout(2, 1));

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

    // Adding parts
    this.setJMenuBar(menu);
    this.add(top);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if("close".equals(e.getActionCommand())){
      System.exit(0);
    }else if("addBook".equals(e.getActionCommand())){
      BookForm bf = new BookForm("Novo livro");
      bf.render();
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
          byte[] data = fileReader.readAllBytes();
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
    this.setSize(1000, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

}