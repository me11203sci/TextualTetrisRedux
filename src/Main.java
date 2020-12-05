import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main
{
	int nFieldWidth = 12;

	public static void main(String[] args) throws IOException
	{
		/*
		 Terminal terminal = new DefaultTerminalFactory().createTerminal();
		 Screen screen = new TerminalScreen(terminal);

		 TextGraphics textGraphics = screen.newTextGraphics();

		 screen.startScreen();

		 textGraphics.putString(0,0, "Greetings. This a test.");
		 screen.refresh();
		 screen.readInput();
		 */
	}

	int nFieldHeight = 18;
	char pField;
	String tetromino[7];
	int Rotate(int px, int py, int r)
	{
		int pi = 0;


		return pi;
	}

	public boolean DoesPieceFit(int nTetromino, int nRotation, int nPosX, int nPosY)
	{
		for(int px = 0; px < 4; px++)
		{
			for(int py = 0; py < 4; py++)
			{
				int pi = Rotate(px, py, nRotation);
				int fi = (nPosY + py) * nFieldWidth + (nPosX + px);

				if(nPosX + px >= 0 && nPosX + px < nFieldWidth)
				{
					if(nPosY + py >= 0 && nPosY + py < nFieldHeight)
					{
						if(nPosY + py >= 0 && nPosY + py < nFieldHeight)
						{
							if(tetromino[nTetromino][pi] != '.' && pField[fi] !=0 )
							{
								return false;
							}

						}
					}
				}
			}
		}
		return true;
	}

}
