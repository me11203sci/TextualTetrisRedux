import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		 Terminal terminal = new DefaultTerminalFactory().createTerminal();
		 Screen screen = new TerminalScreen(terminal);

		 TextGraphics textGraphics = screen.newTextGraphics();

		 screen.startScreen();

		 textGraphics.putString(0,0, "Greetings from Melesio.");
		 screen.refresh();
		 screen.readInput();
	}
}