import javax.swing.*;
import java.awt.*;

import Util.Settings;
import Util.DB;
import service.Canvas;

class Main {
	static void main() {
		// Setup DB
		DB.init ();
		// Setup window
		JFrame frame = new JFrame(Settings.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setup drawable canvas and add to window
		service.Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(Settings.width*Settings.scale, Settings.height*Settings.scale));
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);

		canvas.init();
	}
}