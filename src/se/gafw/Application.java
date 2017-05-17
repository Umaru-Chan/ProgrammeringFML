package se.gafw;

import java.awt.EventQueue;

public class Application {
	public static void main(String...args)
	{
		EventQueue.invokeLater(() -> 
		{
			try {
				new GUI().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}

