import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;

// Generates a tile with book information and a button for dialog box to edit book
public class BookTile extends JPanel {

  private Book book;

  // Registers book to be shown in tile
  public BookTile(Book b, int h, int w) {
    this.book = b;
    this.setLayout(new GridLayout(1, 2));
    this.setMaximumSize(new Dimension(w, h));

    JPanel bookInfo = new JPanel();
    bookInfo.setLayout(new GridLayout(3, 3));

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
    allLabels.add("Descrição");

    Iterator<String> labels = allLabels.iterator();
    Iterator<String> info = allInfo.iterator();
    while(info.hasNext()){
      
      bookInfo.add(new JLabel(labels.next() + info.next()));
      
    }

    this.add(bookInfo);
  }

}