import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Creates Dialog box form to either edit or add books
public class BookForm extends JDialog implements ActionListener, ChangeListener{

  private Book referBook;
  private String[] labels = { "Nome: ", "Autor: ", "Editora: ", "Tipo: ", "Nota: ", "Lido", "Comprado",
      "Descrição: " };
  private JPanel[] panels = new JPanel[this.labels.length];
  private JTextField[] fields = new JTextField[3];
  private JTextArea description = new JTextArea();
  private ButtonGroup type = new ButtonGroup();
  private book_t bookType = book_t.FISICO;
  private int bookGrade = 2;
  private boolean bookRead = false;
  private boolean bookBought = false;

  public BookForm(String message) {
    this.setLayout(new BorderLayout());

    // Adding form
    JPanel top = new JPanel();
    top.setLayout(new FlowLayout());

    // Texts
    int i = 0;
    for (i = 0; i < 3; i++) {
      this.panels[i] = new JPanel();
      this.panels[i].setLayout(new FlowLayout());

      this.fields[i] = new JTextField();
      this.fields[i].setPreferredSize(new Dimension(200, 20));

      this.panels[i].add(new JLabel(this.labels[i]));
      this.panels[i].add(this.fields[i]);

      top.add(this.panels[i]);
    }

    // Radio
    JRadioButton fisicoButton = new JRadioButton("Físico");
    fisicoButton.addActionListener(this);
    fisicoButton.setActionCommand(book_t.FISICO.toString());
    fisicoButton.setSelected(true);

    JRadioButton onlineButton = new JRadioButton("Online");
    fisicoButton.addActionListener(this);
    fisicoButton.setActionCommand(book_t.ONLINE.toString());

    this.type.add(fisicoButton);
    this.type.add(onlineButton);

    top.add(new JLabel(this.labels[i++]));
    top.add(fisicoButton);
    top.add(onlineButton);

    // Slider
    JSlider grade = new JSlider(JSlider.HORIZONTAL, 0, 5, 2);
    grade.setMajorTickSpacing(1);
    grade.setPaintTicks(true);
    grade.setPaintLabels(true);
    grade.addChangeListener(this);
    top.add(new JLabel(this.labels[i++]));
    top.add(grade);

    // Check boxes
    JPanel checks = new JPanel();
    JCheckBox readBox = new JCheckBox(this.labels[i++]);
    readBox.addActionListener(this);
    readBox.setActionCommand("checkRead");
    JCheckBox boughtBox = new JCheckBox(this.labels[i++]);
    boughtBox.addActionListener(this);
    boughtBox.setActionCommand("checkBought");
    checks.add(readBox);
    checks.add(boughtBox);
    top.add(checks);

    // Text area
    description.setPreferredSize(new Dimension(350, 100));
    description.setLineWrap(true);
    top.add(new JLabel(this.labels[i++]));
    top.add(description);

    this.add(top, BorderLayout.CENTER);
    // Adding buttons
    JPanel bottom = new JPanel();
    bottom.setLayout(new FlowLayout());

    JButton cancel = new JButton("Cancelar");
    cancel.addActionListener(this);
    cancel.setPreferredSize(new Dimension(100, 30));
    bottom.add(cancel);

    JButton ok = new JButton("OK");
    ok.addActionListener(this);
    ok.setActionCommand("Ok");
    ok.setPreferredSize(new Dimension(100, 30));
    bottom.add(ok);

    this.add(bottom, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        cancel.doClick();
      }
    });
  }

  public void render(){
    this.setSize(800, 300);
    this.setModal(true);
    this.setVisible(true);
  }

  public Book getBook(){
    return referBook;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Book type
    if(book_t.FISICO.toString().equals(e.getActionCommand()) || book_t.ONLINE.toString().equals(e.getActionCommand())){
      return;
    }
    // Check Boxes
    if("checkRead".equals(e.getActionCommand())){
      this.bookRead = !this.bookRead;
      return;
    }
    if("checkBought".equals(e.getActionCommand())){
      this.bookBought = !this.bookBought;
      return;
    }
    // Exit buttons
    if("Ok".equals(e.getActionCommand())){
      int input = JOptionPane.showConfirmDialog(null, "Todas as alterações serão salvas, tem certeza que deseja continuar?", "Salvar livro...", JOptionPane.YES_NO_OPTION);
      if(input == 1) return;
      if(this.fields[0].getText().length() == 0 || this.fields[1].getText().length() == 0 || this.fields[2].getText().length() == 0){
        JOptionPane.showConfirmDialog(null, "Os campos 'nome', 'autor' e 'editora' devem ser preenchidos.", "Formulário incorreto!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        return;
      }
      this.referBook = new Book(this.fields[0].getText(), this.fields[1].getText(), this.fields[2].getText(), this.description.getText(), this.bookType, this.bookGrade, this.bookRead, this.bookBought);
    }else{
      int input = JOptionPane.showConfirmDialog(null, "Todas as alterações serão descartadas, deseja mesmo continuar?", "Excluindo progresso...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
      if(input == 1) return;
    }
    this.dispose();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    this.bookGrade = ((JSlider)e.getSource()).getValue();
  }
}