import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberInputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ImportActivity extends JPanel{
	private JButton previous;
	private JButton next;
	private JButton importData;
	private JTable dataTable;
	private Table table;
	private String fileName = "";
	private int indexValues[] = new int[3];
	private int count=0;
	String lines[] = new String[100];
	private JButton open = new JButton();
	private JFileChooser fc = new JFileChooser();
	private String[] tableHeads = {"Process ID", "Arrival Time", "Burst Time", "Type"};
	private String[][] tableData = new String[50][50];
	
	
	public ImportActivity(int[] indexValues) {
		previous = new JButton("Previous");
		next = new JButton("Next");
		importData = new JButton("Import");
		this.indexValues = indexValues;
		fc.setCurrentDirectory(new File("C:\\Users\\rajbo\\Desktop"));
		fc.setDialogTitle("Choose data file");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		for(int i=0; i<50; i++) {
			for(int j=0; j<5; j++) {
				tableData[i][j] = "";
			}
		}

		table = new Table();
		setLayout(new BorderLayout());
		Border innerBorder = BorderFactory.createTitledBorder("Import Process Data");
		Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(BorderFactory.createCompoundBorder(innerBorder, outerBorder));
		add(new Import(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}
	
	public class Import extends JPanel{
		
		public Import() {
			previous.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(ImportActivity.this);
					setVisible(false);
					f1.dispose();
					new App();
					
				}
			});
			importData.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(fc.showOpenDialog(open)==JFileChooser.APPROVE_OPTION) {
						
					}
					fileName = fc.getSelectedFile().getAbsolutePath();
					readingFile(fileName);
					table.revalidate();
					table.repaint();
				}
			});
			next.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame f1 = (JFrame) SwingUtilities.getWindowAncestor(ImportActivity.this);
					f1.setVisible(false);
					f1.dispose();
					RunActivity runActivity = new RunActivity(dataTable, indexValues, tableData, count);
				}
			});
			setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx=0;
			gc.gridy=0;
			gc.weightx=1;
			gc.weighty=1;
			gc.fill = GridBagConstraints.NONE;
			add(previous, gc);
			
			gc.gridx=1;
			add(importData, gc);
			
			gc.gridx=2;
			add(next, gc);
		}
		
		
		public void readingFile(String fileName) {
			int localCount=0;
			
			String individualLine;
			try {
				File file = new File(fileName);
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
					try {
						lines[localCount] = line;
						localCount++;
					}catch (ArrayIndexOutOfBoundsException e) {
					}
					
				}
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < localCount; i++)
            {
                
                try {
                	try {
                    	String[] dataRow = lines[i].split("/");
                        for(int j=0; j<dataRow.length; j++) {
                        	tableData[i][j] = dataRow[j];
                        	tableData[i][4] = Integer.toString(i);
                        }
                    }catch (Exception e) {
                    	
    				}
                }catch (Exception e) {
				}count++;
            }
		}
	}
	
	public class Table extends JPanel
	{
		public Table() {
			
			dataTable = new JTable(tableData, tableHeads);
			dataTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
			dataTable.setFillsViewportHeight(true);
			dataTable.setEnabled(false);
			Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			setBorder(BorderFactory.createCompoundBorder(null, outerBorder));
			JScrollPane scrollPane = new JScrollPane(dataTable);
			setLayout(new BorderLayout());
			add(scrollPane, BorderLayout.CENTER);
		}
	}
	public void setData(int indexValues[]) {
		this.indexValues = indexValues;
	}
}
