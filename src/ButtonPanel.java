import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	
	private int buttonIndex; //only used in constructor
	int MAXCOLUMNS = Board.MAXCOLUMNS;
	int MAXROWS = Board.MAXROWS;
	
	Board b;

	public ButtonPanel(Board b) {
		this.b = b; //set board

		for (buttonIndex = 0; buttonIndex < MAXCOLUMNS; buttonIndex++) {
			ColumnButton button = new ColumnButton("" + (buttonIndex + 1), buttonIndex, b);
			button.setBounds(buttonIndex*100 + 10, 100, 100, 20);
			//create buttons

			this.add(button);
			//add to panel
		}
		
	}
}