package simulation;

import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ControlPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	private Highway highway;
	
	// highway length
	private JLabel highwayLengthLabel;
	private TextArea highwayLength;
	private JButton LengthCheck;
	
	// highway stop
	private JButton HighwayStop;
	private boolean Stop;
	
	// highway place car
	private String[] CarType = {"Car"};
	private JComboBox<String> CarTypeList;
	private JLabel CarNumberLabel;
	private TextArea CarNumber;
	private JLabel InterchangeLocationLabel;
	private TextArea InterchangeLocation;
	private JButton PlaceCheck;
	
	// place car list handle
	private static int CarPlaceMax = 5;
	PlaceCarList[] CarPlaceList;
	
	// start handel
	private boolean InitStart;
	private boolean[] interchange;
	
	// restart
	private JButton Restart;
	
	public ControlPanel(Highway Highwaypanel)
	{
		highway = Highwaypanel;
		this.setLayout(null);
		
		
		// for Highway Length setting
		highwayLengthLabel = new JLabel("HighwayLength(no more than 1200):");
		highwayLength = new TextArea("1200", 20, 6, TextArea.SCROLLBARS_NONE);
		LengthCheck = new JButton("check");
		highwayLengthLabel.setBounds(80, 0, 220, 80);
		highwayLength.setBounds(80,80,50,30);
		LengthCheck.setBounds(180,80,120,30);
		LengthCheck.addMouseListener(new CheckLengthListener());
		this.add(highwayLengthLabel);
		this.add(highwayLength);
		this.add(LengthCheck);
		
		// for stop
		HighwayStop = new JButton("Run");
		HighwayStop.setBounds(1000, 40, 100, 30);
		HighwayStop.addMouseListener(new stop());
		Stop = true;
		this.add(HighwayStop);
		
		// for highway place car
		CarTypeList = new JComboBox<String>(CarType);
		CarNumberLabel = new JLabel("Car Num:");
		CarNumber = new TextArea("1", 20, 6, TextArea.SCROLLBARS_NONE);
		InterchangeLocationLabel = new JLabel("Interchange Location:");
		InterchangeLocation = new TextArea("0", 20, 6, TextArea.SCROLLBARS_NONE);
		PlaceCheck = new JButton("Add");
		CarTypeList.setBounds(400, 30, 80, 30);
		CarNumberLabel.setBounds(500, 20, 100, 30);
		CarNumber.setBounds(500, 50, 50, 20);
		InterchangeLocationLabel.setBounds(600, 20, 150, 30);
		InterchangeLocation.setBounds(600, 50, 50, 20);
		PlaceCheck.setBounds(750, 40, 100, 30);
		PlaceCheck.addMouseListener(new PlaceCheck());
		this.add(CarTypeList);
		this.add(CarNumberLabel);
		this.add(CarNumber);
		this.add(InterchangeLocationLabel);
		this.add(InterchangeLocation);
		this.add(PlaceCheck);
		
		// for Place car list
		CarPlaceList = new PlaceCarList[CarPlaceMax];
		for(int i=0; i<CarPlaceMax; i++)
		{
			CarPlaceList[i] = new PlaceCarList();
			CarPlaceList[i].setBounds(400, 100+i*30, 600,30);
			this.add(CarPlaceList[i]);
		}
		
		// Start handel
		interchange = new boolean[1200];
		InitStart = false;
		
		// restart
		Restart = new JButton("Restart");
		Restart.setBounds(1000, 80, 100, 30);
		Restart.addMouseListener(new Restart());
		this.add(Restart);
	}
	public class PlaceCar{
		public int CarNumber;
		public int CarLocation;
		public String CarType;
		private Car CarTmp;
		private Thread ThreadTmp;
		
		public PlaceCar(int number, int location, String type)
		{
			CarNumber = number;
			CarLocation = location;
			CarType = type;
		}
		public void Place()
		{
			for(int i=0; i<CarNumber; i++)
			{
				try{
					if(interchange[CarLocation] == false)
					{
						highway.setInterchange(CarLocation);
						interchange[CarLocation] = true;
					}
					CarTmp = (Car)(Class.forName("simulation."+CarType).newInstance());
					ThreadTmp = new Thread(CarTmp);
					highway.addCar(CarLocation, CarTmp, ThreadTmp, 0);
				}catch(Exception e){System.out.println("CarType Not Found");}
			}
		}
	}
	public class PlaceCarList extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1111;
		
		private JLabel CarStatus;
		private JButton Delete;
		private PlaceCar CarPlace;
		private boolean Activate;
		
		public PlaceCarList()
		{
			CarStatus = new JLabel("Nothing");
			Delete = new JButton("Delete");
			CarStatus.setBounds(0,0,400,30);
			Delete.setBounds(400,5,100,20);
			Delete.addMouseListener(new DeleteKey());
			this.setLayout(null);
			this.add(CarStatus);
			this.add(Delete);
		}
		public void setNewCar(PlaceCar NEWCar)
		{
			CarPlace = NEWCar;
			CarStatus.setText(	"CarType: "+CarPlace.CarType+"     CarNum: "+CarPlace.CarNumber+
								"     InterchangeLocation: "+CarPlace.CarLocation);
			Activate = true;
		}
		public void CarRun()
		{
			if(Activate)
				CarPlace.Place();
		}
		public boolean getActivate()
		{
			return Activate;
		}
		public class DeleteKey extends MouseAdapter{
			public void mouseClicked(MouseEvent e)
			{
				Activate = false;
				CarStatus.setText("Nothing");
			}
		}
	}
	public class CheckLengthListener extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				try{
					int length = Integer.valueOf(highwayLength.getText());
					if(length > 0 && length <= 1200)
						highway.setHighwayLength(length);
				}catch(Exception x){}
			}
		}
		
	}
	public class stop extends MouseAdapter{
		
		public void mousePressed(MouseEvent e)
		{
			if(!InitStart)
			{
				for(int i=0; i<CarPlaceMax; i++)
					CarPlaceList[i].CarRun();
				InitStart = true;
			}
			if(Stop)
			{
				HighwayStop.setText("Stop");
				Stop = false;
				highway.changeStop(Stop);
			}else
			{
				HighwayStop.setText("Run");
				Stop = true;
				highway.changeStop(Stop);
			}
		}
	}
	public class PlaceCheck extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e)
		{
			boolean check = true;
			int Carnum, interchangeLocation;
			String CarType;
			try{
				Carnum = Integer.valueOf(CarNumber.getText());
				if(Carnum < 0 )
					check = false;
				interchangeLocation = Integer.valueOf(InterchangeLocation.getText());
				if(interchangeLocation < 0 || interchangeLocation > highway.getWidth())
					check = false;
				CarType = (String)(CarTypeList.getSelectedItem());
				if(check == true)
				{
					for(int i=0; i<CarPlaceMax; i++)
					{
						if(!CarPlaceList[i].getActivate())
						{
							CarPlaceList[i].setNewCar(new PlaceCar(Carnum, interchangeLocation, CarType));
							break;
						}
					}
				}
			}catch(Exception x){System.out.println("Place Error");}
		}
	}
	public class Restart extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e)
		{
			highway.setAlive(false);
			try{
				Thread.sleep(500);
			}catch(Exception x){}
			highway.setAlive(true);
			if(InitStart)
			{
				for(int i=0; i<CarPlaceMax; i++)
					CarPlaceList[i].CarRun();
			}
		}
	}
}