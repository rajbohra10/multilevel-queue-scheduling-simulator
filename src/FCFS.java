import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders.ToggleButtonBorder;

import org.omg.CORBA.PRIVATE_MEMBER;

public class FCFS extends JPanel{
	private String processData[][] = new String[10][10];
	private int count;
	private Timer newTimer;
	private Timer newTimer2;
	private int m;
	private int n;
	private int l=0;
	private int currentIndex;
	private int indexValues[] = new int[3];
	private int systemCount=0, intCount=0, batchCount=0;
	private String interactiveData[][] = new String[10][10];
	private String batchData[][] = new String[10][10];
	private String systemData[][] = new String[10][10];
	String temp;
	private int currentBurstTime;
	private ProgressBars progressBars;
	private JPanel panel;
	JProgressBar prg[] = new JProgressBar[10];
	JLabel labels[] = new JLabel[10];
	JLabel waitingTimeLabels[] = new JLabel[10];
	JLabel TATimeLabels[] = new JLabel[10];
	JLabel results = new JLabel();
	private JLabel processName;
	private JLabel progressTitle;
	private JLabel waitingLabel;
	private JLabel TALabel;
	private int processOrder[] = new int[10];
	private String[][] newProcessData = new String[10][10];
	private JButton exitButton;
	private JButton homeButton;
	private JTextArea resultDisplay = new JTextArea("");
	private int atime[] = new int[10];
	private int btime[] = new int[10];
	private int wtime[] = new int[10];
	private int ctime[] = new int[10];
	private int tatime[] = new int[10];
	private int x=0;
	private String currentQueue;
	private JLabel algoName;
	public FCFS(String[][] systemData,String[][] interactiveData,String[][] batchData,int systemCount,int intCount,int batchCount, int[] indexValues, int currentIndex) {
		this.systemData = systemData;
		this.systemCount = systemCount;
		this.batchCount = batchCount;
		this.intCount = intCount;
		this.interactiveData = interactiveData;
		this.batchData = batchData;
		this.indexValues = indexValues;
		this.currentIndex = currentIndex;
		setLayout(new BorderLayout());
		setVisible(true);
		if (this.currentIndex==0) {
			processData = this.systemData;
			count = this.systemCount;
			currentQueue = "System Ready Queue";
		}else if (this.currentIndex==1) {
			processData = this.interactiveData;
			count = this.intCount;
			currentQueue = "Interactive Ready Queue";
		}else {
			processData = this.batchData;
			count = this.batchCount;
			currentQueue = "Batch Ready Queue";
		}
		
		this.currentIndex++;
		add(resultDisplay, BorderLayout.SOUTH);
		progressBars = new ProgressBars();
		add(progressBars, BorderLayout.CENTER);
		
		for(int i=0; i<count; i++) {
			for(int j=0; j<5; j++) {
				newProcessData[i][j] = processData[i][j];
			}
		}
		
		for(int i=0; i<count-1; i++) {
			for(int j=0; j<count-i-1; j++) {
				if(Integer.parseInt(newProcessData[j][1])>Integer.parseInt(newProcessData[j+1][1])) {
					for(int k=0; k<5; k++) {
						temp = newProcessData[j][k];
						newProcessData[j][k] = newProcessData[j+1][k];
						newProcessData[j+1][k] = temp;
					}
				}
			}
		}
		

		for(int i=0;i<count; i++) {
			processOrder[i] = Integer.parseInt(newProcessData[i][4]);
			atime[i] = Integer.parseInt(newProcessData[i][1]);
			btime[i] = Integer.parseInt(newProcessData[i][2]);
		}
		calculate(atime, btime);
		m = processOrder[0];
		x=0;
		newTimer2 = new Timer(150, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (n==(currentBurstTime+1)) {
					
					waitingTimeLabels[m].setText(Integer.toString(wtime[l]));
					TATimeLabels[m].setText(Integer.toString(tatime[l]));
					l++;
					newTimer.start();
					newTimer2.stop();
					
					
				}else {
					newTimer.stop();
					prg[m].setValue(n);
					n++;
			}
			}
		});
		
		newTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l==count) {
					resultDisplay.setVisible(false);
					progressBars.setVisible(false);
					if(currentIndex!=2) {
						switch(indexValues[currentIndex+1]) {
						
						case 0:
								add(new FCFS(processData, interactiveData, batchData, systemCount, intCount, batchCount, indexValues, currentIndex+1), BorderLayout.CENTER);
								break;
						case 1:
								add(new SJF(processData, interactiveData, batchData, systemCount, intCount, batchCount, indexValues, currentIndex+1), BorderLayout.CENTER);
								break;
						case 2:
								break;
						}
					}else {
						exitButton.setVisible(true);
						homeButton.setVisible(true);
						progressBars.setVisible(true);
					}
					newTimer.stop();
					
				}else{
				n=0;
				m = processOrder[l];
				x++;
				currentBurstTime = Integer.parseInt(processData[m][2]);
				newTimer2.start();
				}
				
				
			}
		});
		newTimer.start();	
	}
	public FCFS(){
		
	}
	public class ProgressBars extends JPanel{
		
	
	public ProgressBars() {
		processName = new JLabel("Process ID");
		progressTitle = new JLabel("Progress");
		exitButton = new JButton("Exit");
		homeButton = new JButton("Home");
		algoName = new JLabel("First Come First Serve");
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(300, 300));
		GridBagConstraints gc = new GridBagConstraints();
		Border innerBorder = BorderFactory.createTitledBorder(currentQueue);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		gc.gridy=0;
		gc.gridx=0;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.NONE;
		add(processName, gc);
		for(int i=0; i<10; i++) {
			labels[i] = new JLabel(processData[i][0]);
			gc.gridy=i+1;
			add(labels[i], gc);
			
		}
		gc.gridx=1;
		gc.gridy=0;
		gc.insets = new Insets(10, 10, 10, 10);
		add(progressTitle, gc);
		for(int i=0; i<count; i++) {
			
			prg[i] = new JProgressBar(0, Integer.parseInt(processData[i][2]));
			prg[i].setValue(0);
			prg[i].setStringPainted(true);
			gc.gridy=i+1;
			add(prg[i], gc);
			
		}
		gc.gridx=2;
		gc.gridy=0;
		gc.insets = new Insets(10, 10, 10, 10);
		waitingLabel = new JLabel("Wait");
		add(waitingLabel, gc);
		for(int i=0; i<count; i++) {
			waitingTimeLabels[i] = new JLabel("0");
			gc.gridy=i+1;
			add(waitingTimeLabels[i], gc);
			
		}
		gc.gridx=3;
		gc.gridy=0;
		gc.insets = new Insets(10, 10, 10, 10);
		TALabel = new JLabel("TA");
		add(TALabel, gc);
		for(int i=0; i<count; i++) {
			TATimeLabels[i] = new JLabel("0");
			gc.gridy=i+1;
			add(TATimeLabels[i], gc);
			
		}
		gc.gridy++;
		gc.gridy++;
		gc.gridx=1;
		gc.insets = new Insets(10, 10, 0, 10);
		exitButton.setVisible(false);
		add(exitButton, gc);
		setVisible(true);
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		
		gc.gridy++;
		gc.gridy++;
		gc.gridx=1;
		gc.insets = new Insets(0, 0, 0, 0);
		homeButton.setVisible(false);
		add(homeButton, gc);
		setVisible(true);
		homeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(FCFS.this);
				f1.dispose();
				new App();
			}
		});
		
	}
	}
	
	public void calculate(int atime[], int btime[]) {
		float wt=0, tt=0;
		ctime[0] = atime[0] + btime[0];
		for(int a=1;a<count;a++){
	        ctime[a]=ctime[a-1]+btime[a];
	        if(ctime[a-1]<atime[a]) 
	        ctime[a] +=(atime[a]-ctime[a-1]);
	 
	    }
		
		 for(int a=0;a<count;a++){
		        tatime[a]=ctime[a]-atime[a];
		 
		}
		    for(int a=0;a<count;a++)
		    {
		        wtime[a]=tatime[a]-btime[a];
		}
		    
		    resultDisplay.setText("Algorithm : First Come First Serve(FCFS)"+"\nGantt Chart"+"\n");
		    for(int a=0; a<count; a++) {
		    	resultDisplay.append(newProcessData[a][0]+" | ");
		    }
		    for(int a=0; a<count;a++) {
		    	wt  = wt + wtime[a];
		    	tt = tt + tatime[a];
		    }
		    resultDisplay.append("\nAverage Waiting Time = "+(wt/(count)));
		    resultDisplay.append("\nAverage Turnaround Time = "+(tt/(count)));
	}
}
