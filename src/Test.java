import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Test extends JPanel{
		JLabel hello = new JLabel("HELLO");
	public Test() {
		setLayout(new BorderLayout());
		add(hello, BorderLayout.CENTER);
	}
	
	public void helloWorld() {
		JOptionPane.showMessageDialog(this, "HELLO WORLD");
	}
}
