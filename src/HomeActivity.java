import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


public class HomeActivity extends JPanel{
	private JLabel system = new JLabel("System");
	private JLabel interative = new JLabel("Interactive");
	private JLabel batch = new JLabel("Batch");
	private JLabel data = new JLabel("Process Data");
	private JComboBox systemComboBox;
	private JComboBox interactiveComboBox;
	private JComboBox batchComboBox;
	private JComboBox dataComboBox;
	private JButton next;
	private JTable dataTable;
	private ManualActivity manualActivity;
	private int[] indexValues = new int[3];
	
	public HomeActivity() {
		Dimension dimension = getPreferredSize();
		dimension.width=113;
		dimension.height=26;
		systemComboBox = new JComboBox();
		interactiveComboBox = new JComboBox();
		batchComboBox = new JComboBox();
		dataComboBox = new JComboBox();
		next = new JButton("Next");
		setLayout(new BorderLayout());
		DefaultComboBoxModel systemModel = new DefaultComboBoxModel<>();
		systemModel.addElement("FCFS");
		systemModel.addElement("SJF");
		
		DefaultComboBoxModel interactiveModel = new DefaultComboBoxModel<>();
		interactiveModel.addElement("FCFS");
		interactiveModel.addElement("SJF");
		
		DefaultComboBoxModel batchModel = new DefaultComboBoxModel<>();
		batchModel.addElement("FCFS");
		batchModel.addElement("SJF");
		
		DefaultComboBoxModel dataModel = new DefaultComboBoxModel();
		dataModel.addElement("Import");
		dataModel.addElement("Manual");
		
		systemComboBox.setModel(systemModel);
		systemComboBox.setSelectedIndex(0);
		systemComboBox.setEditable(false);
		systemComboBox.setPreferredSize(dimension);
		
		interactiveComboBox.setModel(interactiveModel);
		interactiveComboBox.setSelectedIndex(1);
		interactiveComboBox.setEditable(false);
		interactiveComboBox.setPreferredSize(dimension);
		
		batchComboBox.setModel(batchModel);
		batchComboBox.setSelectedIndex(0);
		batchComboBox.setEditable(false);
		batchComboBox.setPreferredSize(dimension);
		
		dataComboBox.setModel(dataModel);
		dataComboBox.setSelectedIndex(0);
		dataComboBox.setEditable(false);
		dataComboBox.setPreferredSize(dimension);
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				indexValues[0] = systemComboBox.getSelectedIndex();
				indexValues[1] = interactiveComboBox.getSelectedIndex();
				indexValues[2] = batchComboBox.getSelectedIndex();
				
				if (dataComboBox.getSelectedItem().toString()=="Manual") {
					JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(HomeActivity.this);
					manualActivity = new ManualActivity();
					manualActivity.setData(indexValues);
					f1.add(manualActivity, BorderLayout.CENTER);
					f1.setVisible(true);
					setVisible(false);
				}else if(dataComboBox.getSelectedItem().toString()=="Import"){
					JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(HomeActivity.this);
					ImportActivity importActivity = new ImportActivity(indexValues);
					importActivity.setData(indexValues);
					f1.add(importActivity, BorderLayout.CENTER);
					f1.setVisible(true);
					setVisible(false);
					
				}
				
			}
		});
		add(new components(), BorderLayout.CENTER);
		
	}
	
	public class components extends JPanel{
		
		public components() {
			setLayout(new GridBagLayout());
			Border innerBorder = BorderFactory.createTitledBorder("Set up Ready Queues");
			Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx=0;
			gc.gridy=0;
			gc.weightx=1;
			gc.weighty=1;
			gc.insets = new Insets(0, 50, 0, -50);
			gc.fill = GridBagConstraints.NONE;
			add(system, gc);
			
			gc.gridx = 1;
			gc.insets = new Insets(0, -50, 0, 50);
			add(systemComboBox, gc);
			
			gc.gridy=1;
			gc.gridx=0;
			gc.insets = new Insets(0, 50, 0, -50);
			add(interative, gc);
			
			gc.gridx=1;
			gc.gridy=1;
			gc.insets = new Insets(0, -50, 0, 50);
			add(interactiveComboBox, gc);
			
			gc.gridy=2;
			gc.gridx=0;
			gc.insets = new Insets(0, 50, 0, -50);
			add(batch, gc);
			
			gc.gridx=1;
			gc.gridy=2;
			gc.insets = new Insets(0, -50, 0, 50);
			add(batchComboBox, gc);
			
			gc.gridx=0;
			gc.gridy=3;
			gc.insets = new Insets(0, 50, 0, -50);
			add(data, gc);
			
			gc.gridx=1;
			gc.insets = new Insets(0, -50, 0, 50);
			add(dataComboBox, gc);
			
			gc.gridy=4;
			add(next, gc);
		}
		
	}
	

}
