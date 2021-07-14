import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Vector;

public class Main
{
	static final int fieldWidth = 12, fieldHeight = 18;
	static final String[] tetromino = {"..X...X...X...X.", "..X..XX...X.....", ".....XX..XX.....", "..X..XX..X......", ".X...XX...X.....", ".X...X...XX.....", "..X...X..XX....."};
	static int[] playingField = new int[fieldHeight * fieldHeight];

	public static int rotate(int pieceX, int pieceY, int pieceRotation)
	{
		return switch (pieceRotation % 4)
		{
			case 0 -> pieceY * 4 + pieceX;
			case 1 -> 12 + pieceY - (pieceX * 4);
			case 2 -> 15 - (pieceY * 4) - pieceX;
			default -> 3 - pieceY + (pieceX * 4);
		};
	}

	public static boolean doesPieceFit(int currentTetromino, int rotation, int xPosition, int yPosition)
	{
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
			{
				int pieceIndex = rotate(i, j, rotation), fieldIndex = (yPosition + j) * fieldWidth + (xPosition + i);

				// Collision test logic.
				if(xPosition + i >= 0 && xPosition + i < fieldWidth)
					if(yPosition + j >= 0 && yPosition + j < fieldHeight)
						if(tetromino[currentTetromino].charAt(pieceIndex) == 'X' && playingField[fieldIndex] != 0)
							return false;
			}
		return true;
	}

	public static void main(String[] args) throws IOException, InterruptedException
	{
		// Set up Lanterna terminal object.
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		terminal.setCursorVisible(false);
		TextGraphics textGraphics = terminal.newTextGraphics();

		boolean gameOver = false, rotationLatch = true, forceDown = false;
		int currentPiece = (int) (Math.random() * (7)), currentRotation = 0, currentX = fieldWidth / 2, currentY = 0, speed = 20, speedCounter = 0;
		Vector<Integer> lineVector = new Vector<>();

		for(int i = 0; i < fieldWidth; i++)
			for(int j = 0; j < fieldHeight; j++)
				playingField[j * fieldWidth + i] = (i == 0 || i == fieldWidth - 1 || j == fieldHeight - 1) ? 9 : 0;

		// Main game loop.
		while(!gameOver)
		{
			// Game loop tick.
			Thread.sleep(50);
			speedCounter++;
			forceDown = (speedCounter == speed);
			terminal.flush();

			// Poll user input.
			KeyStroke keyPressed = terminal.pollInput();
			if(keyPressed != null)
			{
				switch (keyPressed.getKeyType())
				{
					case ArrowLeft -> currentX -= (doesPieceFit(currentPiece, currentRotation, currentX - 1, currentY)) ? 1 : 0;
					case ArrowRight -> currentX += (doesPieceFit(currentPiece, currentRotation, currentX + 1, currentY)) ? 1 : 0;
					case ArrowDown -> currentY += (doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1)) ? 1 : 0;
					case ArrowUp ->
					{
						if(rotationLatch)
						{
							currentRotation += (rotationLatch && doesPieceFit(currentPiece, currentRotation + 1, currentX, currentY)) ? 1 : 0;
							rotationLatch = false;
						}
						else
							rotationLatch = true;
					}
				}
			}

			// Drop piece down.
			if(forceDown)
			{
				speedCounter = 0;

				if(doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1))
					currentY++;
				else
				{
					// Add current tetromino to the stack.
					for(int i = 0; i < 4; i++)
						for(int j = 0; j < 4; j++)
							if(tetromino[currentPiece].charAt(rotate(i, j, currentRotation)) == 'X')
								playingField[(currentY + j) * fieldWidth + (currentX + i)] = currentPiece + 1;

					// Check for lines.
					for(int j = 0; j < 4; j++)
						if(currentY + j < fieldHeight - 1)
						{
							boolean completeLine = true;
							for(int i = 1; i < fieldWidth - 1; i++)
								completeLine &= (playingField[currentY + j] * fieldWidth + i) != 0;

							if(completeLine)
							{
								for(int i = 1; i < fieldWidth - 1; i++)
									playingField[(currentY + j) * fieldWidth + i] = 8;

								lineVector.add(currentY + j);
							}
						}

					// Select next piece.
					currentX = fieldWidth / 2;
					currentY = 0;
					currentRotation = 0;
					currentPiece = (int) (Math.random() * (7));

					// Check for game over.
					gameOver = !doesPieceFit(currentPiece, currentRotation, currentX, currentY);
				}
			}

			// Render playing field boundary.
			for(int i = 0; i < fieldWidth; i++)
				for(int j = 0; j < fieldHeight; j++)
					textGraphics.putString(i + 2, j + 2, String.valueOf(" ABCDEFG=#".charAt(playingField[j * fieldWidth + i])));

			// Render current piece.
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 4; j++)
					if(tetromino[currentPiece].charAt(rotate(i, j, currentRotation)) == 'X')
						textGraphics.putString(currentX + i + 2, currentY + j + 2, String.valueOf(Character.toChars(currentPiece + 65)));

			if (!lineVector.isEmpty())
			{
				terminal.flush();
				Thread.sleep(400);

				for(var v : lineVector)
				{
					for(int i = 1; i < fieldWidth - 1; i++)
					{
						for(int j = v; j > 0; j--)
							playingField[j * fieldWidth + i] = playingField[(j - 1) * fieldWidth + i];

						playingField[i] = 0;
					}
				}

				lineVector.clear();
			}
		}
	}
}