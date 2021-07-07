import com.googlecode.lanterna.TextColor;
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
		TextGraphics textGraphics = terminal.newTextGraphics();
		textGraphics.putString(2, 1, "Test string. Testing Lanterna.");
	}
}
