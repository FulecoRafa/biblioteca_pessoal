import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

// Generates a tile with book information and a button for dialog box to edit book
public class BookTile extends JPanel implements ActionListener {

  private Book book;

  // Registers book to be shown in tile
  public BookTile(Book b, int h, int w) {
    this.book = b;
    this.setLayout(new GridLayout(3, 2));
    w+=10;
    this.setPreferredSize(new Dimension(w, h));
    System.out.println("H: " + h + " W: " + w);

    JPanel bookInfo = new JPanel();
    bookInfo.setLayout(new GridLayout(6, 3));

    String eachInfo[] = book.toString().split("\\r?\\n");
    Vector<String> allInfo = new Vector<String>();
    allInfo.add(eachInfo[0]);
    allInfo.add(eachInfo[1].split(" -> ")[0]);
    allInfo.add(eachInfo[1].split(" -> ")[1]);
    allInfo.add(eachInfo[2].split(" ")[0]);
    allInfo.add(eachInfo[2].split(" ")[1]);
    allInfo.add(eachInfo[3].split("   ")[0]);
    allInfo.add(eachInfo[3].split("   ")[1]);
    allInfo.add(eachInfo[4]);

    Vector<String> allLabels = new Vector<String>();
    allLabels.add("Nome: ");
    allLabels.add("Autor: ");
    allLabels.add("Editora: ");
    allLabels.add("Tipo: ");
    allLabels.add("Nota: ");
    allLabels.add("");
    allLabels.add("");
    allLabels.add("Descrição: ");

    Iterator<String> labels = allLabels.iterator();
    Iterator<String> info = allInfo.iterator();
    while (info.hasNext()) {

      bookInfo.add(new JLabel(labels.next() + info.next()));

    }

    JPanel buttons = new JPanel();

    JButton editButton = new JButton("Editar");
    editButton.addActionListener(this);
    editButton.setActionCommand("edit");

    JButton eraseButton = new JButton("Deletar");
    eraseButton.addActionListener(this);
    eraseButton.setActionCommand("erase");

    buttons.add(editButton);
    buttons.add(eraseButton);

    this.add(bookInfo);
    this.add(buttons);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if("erase".equals(e.getActionCommand())){
      int input = JOptionPane.showConfirmDialog(null, "Todas as informações do livro serão deletadas definitivamente, tem certeza que deseja continuar?", "Deletar livro", JOptionPane.YES_NO_OPTION);
      if(input == 1)return;
      System.out.println("Deletando");
      // TODO Delete function here
    }else if("edit".equals(e.getActionCommand())){
      BookForm bf = new BookForm("Editar livro");
      bf.setBook(book);
      bf.render();
    }

  }

}