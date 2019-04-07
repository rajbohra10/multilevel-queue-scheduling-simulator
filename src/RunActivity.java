import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.omg.CORBA.PUBLIC_MEMBER;

//import RunActivity.Table.ProgressBars;

public class RunActivity extends JFrame{
	
	private JTable dataTable;
	private JLabel testLabel;
	private String[][] tableData = new String[50][50];
	private int[] indexValues = new int[3];
	private int rowCount;
	private int colCount;
	private int count;
	private int k=0;
	private String[][] systemData = new String[10][10];
	private String[][] batchData = new String[10][10];
	private String[][] interactiveData = new String[10][10];
	private JButton runButton;
	private JButton exitButton;
	private Timer timer[] = new Timer[10];
	private Timer t;
	private static int interval = 1000;
	private int tCount=0;
	private int currentBurstCount=0;
	private int systemCount=0, intCount=0, batchCount=0;
	private Test test;
	private Timer newTimer;
	private FCFS fcfs;
	private SJF sjf;
	private int currentIndex=0;
	public RunActivity(JTable dataTable, int indexValues[], String[][] tableData, int count) {
		super("Simulator");
		testLabel = new JLabel("Hello");
		setLayout(new BorderLayout());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		runButton = new JButton("Run");
		exitButton = new JButton("Exit");
		
		this.indexValues = indexValues;
		this.tableData = tableData;
		this.dataTable = dataTable;
		this.count = count;
		
		for(int i=0; i<this.count; i++) {

			if (this.tableData[i][3].equals("System")) {
				for(int j=0; j<5; j++) {
					systemData[k][j] = this.tableData[i][j];
					systemData[k][4] = Integer.toString(k);
				}k++;
				systemCount++;
			}
		}
		k=0;
		for(int i=0; i<this.count; i++) {
			if (this.tableData[i][3].equals("Interactive")) {
				for(int j=0; j<5; j++) {
					interactiveData[k][j] = this.tableData[i][j];
					interactiveData[k][4] = Integer.toString(k);
				}k++;
				intCount++;
			}
		}
		k=0;
		for(int i=0; i<this.count; i++) {
			if (this.tableData[i][3].equals("Batch")) {
				for(int j=0; j<5; j++) {
					batchData[k][j] = this.tableData[i][j];
					batchData[k][4] = Integer.toString(k);
				}k++;
				batchCount++;
			}
		}
		fcfs = new FCFS();
		sjf = new SJF();
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(false);
				JFrame f1;
					switch(indexValues[currentIndex]) {
							
					case 0:
							fcfs = new FCFS(systemData, interactiveData, batchData, systemCount, intCount, batchCount, indexValues, currentIndex);
							f1 = (JFrame) SwingUtilities.getWindowAncestor(RunActivity.this);
							getContentPane().add(fcfs, BorderLayout.CENTER);
							getContentPane().repaint();
							getContentPane().revalidate();
							break;
					case 1:
							sjf = new SJF(systemData, interactiveData, batchData, systemCount, intCount, batchCount, indexValues, currentIndex);
							f1 = (JFrame) SwingUtilities.getWindowAncestor(RunActivity.this);
							getContentPane().add(sjf, BorderLayout.CENTER);
							getContentPane().repaint();
							getContentPane().revalidate();
							break;
					}
					
			}
		});
		
		add(new Table(), BorderLayout.WEST);
		add(runButton, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(700, 550));
		setLocation(300, 70);
		
}

	/* Contains the table and the progress bars*/
	public class Table extends JPanel{
		public Table() {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx=0;
			gc.gridy=0;
			gc.weightx=1;
			gc.fill = GridBagConstraints.NONE;
			JScrollPane scrollPane = new JScrollPane(dataTable);
			add(scrollPane);
			scrollPane.setPreferredSize(new Dimension(300, 300));
			gc.gridy=1;
			gc.weightx=1;
			setMinimumSize(new Dimension(700, 500));
			setLocation(300, 70);
			
		}
	}
	
	public String[][] getSystemData() {
		return systemData;
	}
	
	public String[][] getBatchData(){
		return batchData;
	}
	
	public String[][] getInteractiveData(){
		return interactiveData;
	}
}
	
