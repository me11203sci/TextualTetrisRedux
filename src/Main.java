// Declare dependencies.
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.lang.*;
import java.util.Random;
import java.util.Vector;

// The "Main" class.
public class Main
{
	int nFieldWidth = 12;

	public static void main(String[] args) throws IOException
	{
		/*
		 Terminal terminal = new DefaultTerminalFactory().createTerminal();
		 Screen screen = new TerminalScreen(terminal);

		TextGraphics textGraphics = screen.newTextGraphics();

		// Begin main game loop.
		while(!gameOver)
		{
			// Game loop timing delay.
			Thread.sleep(50);
			speedCounter++;
			forceDown = (speedCounter == gameSpeed);
			KeyStroke keyPressed = terminal.pollInput();

			// User input detection.
			if(keyPressed != null)
			{
				switch (keyPressed.getKeyType()) {
					case ArrowLeft -> currentX -= (doesPieceFit(currentPiece, currentX - 1, currentY, currentRotationState)) ? 1 : 0;
					case ArrowRight -> currentX += (doesPieceFit(currentPiece, currentX + 1, currentY, currentRotationState)) ? 1 : 0;
					case ArrowDown -> currentY += (doesPieceFit(currentPiece, currentX, currentY + 1, currentRotationState)) ? 1 : 0;
					case Character -> currentRotationState += (keyPressed.getCharacter() == 'z' && doesPieceFit(currentPiece, currentX, currentY, currentRotationState + 1)) ? 1 : 0;
				}
			}

			if(forceDown)
			{
				speedCounter = 0;
				pieceCount++;
				if(doesPieceFit(currentPiece, currentX, currentY + 1, currentRotationState))
				{
					currentY++;
				}
				else
				{
					for(int i = 0; i < 4; i++)
					{
						for(int j = 0; j < 4; j++)
						{
							if(tetromino[currentPiece].charAt(rotate(i, j, currentRotationState)) == 'X')
							{
								playerField[(currentY + j) * fieldWidth + (currentX + i)] = currentPiece + 1;
							}
						}
					}

					if(pieceCount % 10 == 0)
					{
						if(gameSpeed >= 10)
						{
							gameSpeed--;
						}
					}

					for(int j = 0; j < 4; j++)
					{
						if(currentY + j < fieldHeight - 1)
						{
							boolean lineExists = true;
							for(int i = 1; i < fieldWidth - 1; i++)
							{
								lineExists &= (playerField[(currentY + j) * fieldWidth + i]) != 0;
							}
							if(lineExists)
							{
								for(int i = 1; i < fieldWidth - 1; i++)
								{
									playerField[(currentY + j) * fieldWidth + i] = 8;
								}

								currentLines.add(currentY + j);
							}
						}
					}

					score += 25;
					if(!currentLines.isEmpty())
					{
						score += (1 << currentLines.size()) * 100;
					}

					// Chose next piece.
					currentX = fieldWidth/2;
					currentY = 0;
					currentRotationState = 0;
					currentPiece = new Random().nextInt(7);

					// Check if game over.
					gameOver = !doesPieceFit(currentPiece, currentX, currentY, currentRotationState);
				}
			}

			// Render the field.
			for(int i = 0; i < fieldWidth; i++)
			{
				for(int j = 0; j < fieldHeight; j++)
				{
					textGraphics.putString(i + 2, j + 2, String.valueOf(" ABCDEFG=#".charAt(playerField[j * fieldWidth + i])));
				}
			}

			// Render current piece.
			for(int i = 0; i < 4; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					if(tetromino[currentPiece].charAt(rotate(i, j, currentRotationState)) == 'X')
					{
						textGraphics.putString(currentX + i + 2, currentY + j + 2, String.valueOf(Character.toChars(currentPiece + 65)));
					}
				}
			}

			textGraphics.drawRectangle(new TerminalPosition(16, 13),new TerminalSize(10,5),  '#');
			textGraphics.putString(17, 13," Score: ");
			textGraphics.putString(18, 15, String.valueOf(score));

			if(!currentLines.isEmpty())
			{
				screen.refresh();
				Thread.sleep(400);

				for(var v : currentLines)
				{
					for(int i = 1; i < fieldWidth - 1; i++)
					{
						for(int j = v; j > 0; j--)
						{
							playerField[j * fieldWidth + i] = playerField[(j - 1) * fieldWidth + i];
						}
						playerField[i] = 0;
					}
				}
				currentLines.clear();
			}
			screen.refresh();
		}
		textGraphics.drawRectangle(new TerminalPosition(3, 9),new TerminalSize(10,3),  '#');
		textGraphics.putString(3,10,"GAME  OVER");
		screen.refresh();
		Thread.sleep(10000);
		System.exit(0);
	}
}