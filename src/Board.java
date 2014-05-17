import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Board extends JFrame {

	int BLANK = 0;
	int RED = 1;
	int YELLOW = 2;
	static int MAXCOLUMNS = 7;
	static int MAXROWS = 6;
	static int CONNECTNUM = 4;

	Image redplayer;
	Image yellowplayer;
	Image winImage;
	Image drawImage;

	private JPanel panel;
	private int[][] board;

	int currentColour = 1;
	boolean gameOver;
	int winner; //0 means draw


	public Board() {

		winner = -1;
		gameOver = false;

		//get the images
		ImageIcon ii1 = new ImageIcon(this.getClass().getResource("red.png"));
		redplayer = ii1.getImage();
		ImageIcon ii2 = new ImageIcon(this.getClass().getResource("yellow.png"));
		yellowplayer = ii2.getImage();
		ImageIcon ii3 = new ImageIcon(this.getClass().getResource("winner.png"));
		winImage = ii3.getImage();
		ImageIcon ii4 = new ImageIcon(this.getClass().getResource("draw.png"));
		drawImage = ii4.getImage();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		//add panel
		panel = new ButtonPanel(this);
		getContentPane().add(panel);
		panel.setLayout(null);	

		setTitle("Connect Four");
		setSize(1000,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		board = new int[MAXROWS][MAXCOLUMNS]; //this is number of columns, then number of rows (x, y)

		//iterate over the whole board and set it as blank
		for (int i = 0; i < MAXROWS; i++) { // i < 6 rows (for each row)
			for (int j = 0; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)
				board[i][j] = BLANK;
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;

		for (int i = 0; i < MAXROWS; i++) { // i < 6 rows (for each row)
			for (int j = 0; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)

				int boardValue = board[i][j];

				if (boardValue == 1) {
					g2d.drawImage(redplayer, 10 + 50 + 100*j, 100 + 50 + 100*i, this);
				}
				if (boardValue == 2) {
					g2d.drawImage(yellowplayer, 10 + 50 + 100*j, 100 + 50 + 100*i, this);
				}
			}
		}

		if (gameOver) {
			if (winner == 0) {
				g2d.drawImage(drawImage, (MAXCOLUMNS - 1)*100 / 2, 30, this);
			} else {
				g2d.drawImage(winImage, (MAXCOLUMNS - 1)*100 / 2 - 155, 30, this); //image not customizable
				if (winner == 1) {
					g2d.drawImage(redplayer, MAXCOLUMNS*100 / 2 + 50, 30, this);
				} else {
					g2d.drawImage(yellowplayer, MAXCOLUMNS*100 / 2 + 50, 30, this);
				}
			}
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void addDisk(int currentColumn) {
		if (!gameOver) {
			int currentRow;

			//find the row with a disk in it
			for (currentRow = 0; currentRow < MAXROWS; currentRow++) {

				if (board[currentRow][currentColumn]>0) {
					break;
				}
			}


			if (currentRow > 0) {
				board[currentRow - 1][currentColumn] = currentColour;

				//change the currentColour
				if (currentColour == RED) {
					currentColour = YELLOW;
				} else {
					currentColour = RED;
				}
			}
			
			checkGameOver(); //since the game changes every time a disk is added we call 
							// checkGameOver() here
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				Board newBoard = new Board();
				newBoard.setVisible(true);
			}
		});
	}
	

	

	private void checkGameOver() {
		//horizontal
		for (int i = 0; i < MAXROWS; i++) { // i < 6 rows (for each row)
			for (int j = 0; j < MAXCOLUMNS - CONNECTNUM + 1; j++) { // j < 7 columns (for each column)
				int boardValue = board[i][j];
				boolean isWin = boardValue > 0; 
				if (isWin) {  // if current position isn't blank, check
					for	(int k = 1; k < CONNECTNUM; k++) {
						isWin = isWin && boardValue == board[i][j + k]; 
					}

					if (isWin) {
						gameOver = true;
						winner = board[i][j];
					}
				}
			}
		}
		
		//vertical
		for (int i = 0; i < MAXROWS - CONNECTNUM + 1; i++) { // i < 6 rows (for each row)
			for (int j = 0; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)
				int boardValue = board[i][j];
				boolean isWin = board[i][j] > 0; 
				if (isWin) {  // if current position isn't blank, check
					for	(int k = 1; k < CONNECTNUM; k++) {
						isWin = isWin && boardValue == board[i + k][j]; 
					}

					if (isWin) {
						gameOver = true;
						winner = board[i][j];
					}
				}
			}
		}

		//lower left to top right 
		for (int i = 0; i <= MAXROWS - CONNECTNUM; i++) { // i < 6 rows (for each row)
			for (int j = CONNECTNUM - 1; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)
				int boardValue = board[i][j];
				boolean isWin = boardValue > 0; 
				if (isWin) {  
					for	(int k = 1; k < CONNECTNUM; k++) {
						isWin = isWin && boardValue == board[i + k][j - k];
					}

					if (isWin) {
						gameOver = true;
						winner = board[i][j];
					}
				}
			}
		}
		
		//top left to lower right
		for (int i = CONNECTNUM - 1; i < MAXROWS; i++) { // i < 6 rows (for each row)
			for (int j = CONNECTNUM - 1; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)
				boolean isWin = board[i][j] > 0; 
				if (isWin) { 
					for	(int k = 1; k < CONNECTNUM; k++) {
						isWin = isWin && board[i - k][j - k] == board[i - k + 1][j - k + 1]; 
					}

					if (isWin) {
						gameOver = true;
						winner = board[i][j];
					}
				}
			}
		}
		

		if (!gameOver) { //draw
			boolean isFilled = true;
			for (int i = 0; i < MAXROWS; i++) { // i < 6 rows (for each row)
				for (int j = 0; j < MAXCOLUMNS; j++) { // j < 7 columns (for each column)
					if (board[i][j] == 0)
						isFilled = false;
				}
			}

			if (isFilled) {
				gameOver = true;
				winner = 0;
			}
		}
	}

}
