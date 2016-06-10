package org.transitime.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import java.awt.Font;

public class InformationPanel {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void InformationPanelstart() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformationPanel window = new InformationPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InformationPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 522, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane txtpnInfopanel = new JTextPane();
		txtpnInfopanel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnInfopanel.setText("GTFS File location: This is the location of the GTFS file you want transitime to use eg:\r\n C:\\Users\\Brendan\\Documents\\GTFS\\GTFSAUS \r\nThere should be a series of txt files at this location such as agency.txt, calender.txt, stops.txt...etc\r\n\r\nGTFS Realtime feed location: Generally a URL, more information can found at:https://developers.google.com/transit/gtfs-realtime/\r\n\r\n Install location: where any need on disc objects will be stored");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnInfopanel, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnInfopanel, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
