import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Main
{
	public int rotate(int pieceX, int pieceY, int pieceRotation)
	{
		return switch (pieceRotation % 4)
		{
			case 0 -> pieceY * 4 + pieceX;
			case 1 -> 12 + pieceY - (pieceX * 4);
			case 2 -> 15 - (pieceY * 4) - pieceX;
			default -> 3 - pieceY + (pieceX * 4);
		};
	}

	public static void main(String[] args) throws IOException
	{
		// Set up Lanterna terminal object.
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		terminal.setCursorVisible(false);
		TextGraphics textGraphics = terminal.newTextGraphics();

		final String[] tetromino = {"..X...X...X...X.", "..X..XX...X.....", ".....XX..XX.....", "..X..XX..X......", ".X...XX...X.....", ".X...X...XX.....", "..X...X..XX....."};
		final int fieldWidth = 12, fieldHeight = 18;
		boolean gameOver = false;

		int[] playingField = new int[fieldHeight * fieldHeight];
		for(int i = 0; i < fieldWidth; i++)
			for(int j = 0; j < fieldHeight; j++)
				playingField[j * fieldWidth + i] = (i == 0 || i == fieldWidth - 1 || j == fieldHeight - 1) ? 9 : 0;

		while(!gameOver)
		{
			for(int i = 0; i < fieldWidth; i++)
			{
				for(int j = 0; j < fieldHeight; j++)
				{
					textGraphics.putString(i + 2, j + 2, String.valueOf(" ABCDEFG=#".charAt(playingField[j * fieldWidth + i])));
				}
			}
		}
	}
}
