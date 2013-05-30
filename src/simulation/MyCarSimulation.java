package simulation;

import java.awt.Color;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyCarSimulation extends JApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	private Highway highway;
	private JPanel panel;
	private ControlPanel highwayControl;
	
	public void init()
	{
		this.setLayout(null);
		this.setSize(1200, 600);
		panel = new JPanel();
		
		panel.setBackground(Color.white);
		panel.setBounds(0,0,1200,600);
		panel.setLayout(null);
		this.add(panel);
		try{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					placeHighway(panel);
				}
			});
		}catch(Exception e){System.out.println("creat highway error");}
	}
	public void placeHighway(JPanel panel)
	{
		highway = new Highway(1200);
		highwayControl = new ControlPanel(highway);
		highway.setBounds(0, 50, 1200, 50);
		highwayControl.setBounds(0,150,1200,250);
		panel.add(highway);
		panel.add(highwayControl);
	}
	
}
