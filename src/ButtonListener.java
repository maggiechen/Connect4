import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	private Board b;
	private int column;
	
	public ButtonListener(int column, Board b) {
		this.b = b;
		this.column = column;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		b.addDisk(column);
		b.repaint();
	}
}
