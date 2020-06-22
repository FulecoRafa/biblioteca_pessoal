import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

// Creates Dialog box form to either edit or add books
public class BookForm extends JFrame implements ActionListener{
  public BookForm(String message){
    this.setLayout(new FlowLayout());
    JLabel title = new JLabel(message);
    JButton ok = new JButton("OK");
    ok.addActionListener(this);
    this.add(title);
    this.add(ok);
  }

  public void render(){
    this.setSize(200, 150);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    this.dispose();
  }
}