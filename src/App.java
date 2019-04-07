import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.border.Border;

public class App extends JFrame{
	
	
	public App(){
		super("Multilevel Queue Scheduling Simulator");
		setLayout(new BorderLayout());
		HomeActivity homeActivity = new HomeActivity();
		add(homeActivity, BorderLayout.CENTER);
		setVisible(true);
		setMinimumSize(new Dimension(700, 500));
		setLocation(300, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String args[])
	{
		new App();		
	}

}