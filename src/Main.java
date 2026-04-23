import javax.swing.*;
import java.awt.*;

import Util.Settings;
import Util.DB;
import service.Canvas;

class Main {
	static void main() {
		DB.init ();
		JFrame frame = new JFrame(Settings.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		service.Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(Settings.width*Settings.scale, Settings.height*Settings.scale));
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);

		canvas.init();
	}
}