import javax.swing.JButton;


public class ColumnButton extends JButton{
	private int column;
	private Board b;

	public ColumnButton(String s, int i, Board b) {
		super(s);
		column = i;
		this.b = b;
		addActionListener(new ButtonListener(column, b));
	}
}
