package ca.mcgill.ecse539.vanet.application;

import ca.mcgill.ecse539.vanet.controller.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



public class VanetPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;	
	private JLabel inputLabel = new JLabel();
	private JButton addFileButton = new JButton();
	private JLabel xRangeLabel = new JLabel();
	private JButton addXRangeButton = new JButton();
	private JTextArea inputX = new JTextArea();
	private JLabel yRangeLabel = new JLabel();
	private JButton addYRangeButton = new JButton();
	private JTextArea inputY = new JTextArea();
	private JTextArea inputFilepath = new JTextArea();
    private VanetController Vc;
	public VanetPage() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("VANET");
		inputLabel.setText("Enter full input filepath with coordinates - ");
		addFileButton.setText("GO!");
		addFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addFileButtonActionPerformed(evt);
			}
		});	
		
		xRangeLabel.setText("Enter Maximum X Range View > 0 ");
		yRangeLabel.setText("Enter Maximum Y Range View > 0 ");
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(inputLabel)
				.addComponent(inputFilepath)
				.addComponent(addFileButton)
				.addComponent(xRangeLabel)
				.addComponent(inputX)
				.addComponent(yRangeLabel)
				.addComponent(inputY)
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(inputLabel)
				.addComponent(inputFilepath)
				.addComponent(addFileButton)
				.addComponent(xRangeLabel)
				.addComponent(inputX)
				.addComponent(yRangeLabel)
				.addComponent(inputY)
				);

		
		pack();
	}
	
	private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller

		// TODO
		if(!inputFilepath.getText().equals("") )
		{
			if(inputX.getText().equals("")&&inputY.getText().equals(""))
			{
				Vc = new VanetController(inputFilepath.getText(), -1, -1);
			}
			else{
				if(inputX.getText().equals("")){
					Vc = new VanetController(inputFilepath.getText(), 1000, Double.parseDouble(inputY.getText()));	
				}
				else if(inputY.getText().equals("")){
					Vc = new VanetController(inputFilepath.getText(), Double.parseDouble(inputX.getText()), 100);
				}
				else{
					Vc = new VanetController(inputFilepath.getText(), Double.parseDouble(inputX.getText()), Double.parseDouble(inputY.getText()));	
				}
				
			}
			
		}
		else{
			
		}

	}
	
}




