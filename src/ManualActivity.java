import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.spec.GCMParameterSpec;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.TableModel;
import javax.swing.text.TabExpander;
import javax.swing.text.TabableView;

import org.omg.CORBA.DynamicImplementation;

public class ManualActivity extends JPanel{
	
	private JButton previous;
	private JButton next;
	private JButton add;
	private JButton export;
	private JLabel processID;
	private JLabel arrival;
	private JLabel type;
	private JLabel burst;
	private JTextField processName;
	private JTextField arrivalTime;
	private JTextField burstTime;
	private JComboBox typeComboBox;
	private JTable dataTable;
	private int count=0;
	private int[] indexValues =  new int[3];
	
	private String[] tableHeads = {"Process ID", "Arrival Time", "Burst Time", "Type"};
	private String[][] tableData = new String[50][50];
	
	public ManualActivity() {
		Dimension dimension = getPreferredSize();
		dimension.width=100;
		dimension.height=20;
		typeComboBox = new JComboBox();
		processID = new JLabel("Process ID");
		arrival = new JLabel("Arrival Time");
		type = new JLabel("Process type");
		burst = new JLabel("Burst Time");
		typeComboBox = new JComboBox();
		processName = new JTextField(10);
		arrivalTime = new JTextField(10);
		burstTime = new JTextField(10);
		previous = new JButton("Previous");
		add = new JButton("Add");
		next = new JButton("Next");
		export = new JButton("Export");
		
		processName.setText("P0");
		
		DefaultComboBoxModel typeModel = new DefaultComboBoxModel();
		typeModel.addElement("System");
		typeModel.addElement("Interactive");
		typeModel.addElement("Batch");
		
		typeComboBox.setModel(typeModel);
		typeComboBox.setEditable(false);
		typeComboBox.setPreferredSize(dimension);
		typeComboBox.setSelectedItem(0);
		for(int i=0; i<50; i++) {
			for(int j=0; j<5; j++) {
				tableData[i][j] = "";
			}
		}
		previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(ManualActivity.this);
				setVisible(false);
				f1.add(new HomeActivity());
				f1.setVisible(true);
			}
		});
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tableData[count][0] = processName.getText().toString();
				tableData[count][1] = arrivalTime.getText().toString();
				tableData[count][2] = burstTime.getText().toString();
				tableData[count][3] = typeComboBox.getSelectedItem().toString();
				tableData[count][4] = Integer.toString(count);
				count++;
				processName.setText("P"+(count));
				dataTable.repaint();
				
			}
		});
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(ManualActivity.this);
				f1.setVisible(false);
				f1.dispose();
				
				RunActivity runActivity = new RunActivity(dataTable, indexValues, tableData, count);
			}
		});
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new Input());
		add(new Table());

	}
	
	public class Table extends JPanel
	{
		public Table() {
			
			dataTable = new JTable(tableData, tableHeads);
			dataTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
			dataTable.setFillsViewportHeight(true);
			dataTable.setEnabled(false);
			JScrollPane scrollPane = new JScrollPane(dataTable);
			setLayout(new BorderLayout());
			add(scrollPane, BorderLayout.CENTER);
		}
	}
	
	public class Input extends JPanel
	{
		public Input() {
		setLayout(new GridBagLayout());
		Border innerBorder = BorderFactory.createTitledBorder("Add Processes");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints gc2 = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy= 0;
		gc.weightx=1;
		gc.weighty=1;
		add(processID, gc);
		
		gc.gridx=1;
		add(arrival, gc);
		
		gc.gridx=2;
		add(burst, gc);
		
		gc.gridx=3;
		add(type, gc);
		
		gc.gridy=1;
		gc.gridx=0;
		add(processName, gc);
		
		gc.gridx=1;
		add(arrivalTime, gc);
		
		gc.gridx=2;
		add(burstTime, gc);
		
		gc.gridx=3;
		add(typeComboBox, gc);
		
		gc.gridy=2;
		gc.gridx=0;
		add(previous, gc);
		
		gc.gridx=2;
		add(add, gc);
		gc.gridx = 3;
		add(next, gc);
		
	
		}
	}
	
	public void setData(int indexValues[]) {
		this.indexValues = indexValues;
	}
}
