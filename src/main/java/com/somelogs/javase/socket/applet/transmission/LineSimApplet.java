package com.somelogs.javase.socket.applet.transmission;///////////////////////////////////////
//LineSimApllet
//written by David Grangier, Institut Eurecom, France
//david.grangier@eurecom.fr
///////////////////////////////////////
//imports
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Applet Class
public class LineSimApplet extends Applet {
	//buttons
	Button start=new Button ("Start");
	Button stop=new Button ("Reset");
	//features lists
	MyChoice length=new MyChoice(new String[] {"10 km","100 km","1000 km"},new double[] {10E3,100E3,1E6},3);
	MyChoice rate=new MyChoice(new String[] {"512 kps","1 Mbps","10 Mbps","100 Mbps"},new double[] {512E3,1E6,10E6,100E6},2);
	MyChoice size=new MyChoice(new String[] {"100 Bytes","500 Bytes","1 kBytes"},new double[] {8E2,4E3,8E3},1);
	//to simulate time
	Thread timerThread;
	TickTask timerTask;
	boolean simulationRunning=false;
	//communication line
	Line myLine;

	public void init() {
		try {
			setBackground(Color.white);
			add(new Label ("Length",Label.RIGHT));
			add(length);
			add(new Label("Rate",Label.RIGHT));
			add(rate);
			add(new Label("Packet size",Label.RIGHT));
			add(size);
			//start
			start.addActionListener(
					new ActionListener()
					{
						public void actionPerformed (ActionEvent event)
						{
							launchSim();
						}
					});
			add(start);
			//stop
			Button stop=new Button ("Reset");
			stop.addActionListener(
					new ActionListener()
					{
						public void actionPerformed (ActionEvent event)
						{
							stopSim();
							//clear line
							myLine.sendTime(0);
							//redraw cleared line
							LineSimApplet.this.repaint();
						}
					});
			add(stop);
			//line
			myLine= new Line(40,50,450,10);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void paint (Graphics g)
	{
		update(g); // eliminate flashing : update is overriden
	}

	public void update (Graphics g)
	{ //work on a offscreen image

		Dimension offDimension = getSize();
		Image offImage = createImage(offDimension.width, offDimension.height);
		Graphics offGraphics = offImage.getGraphics();
		myLine.drawLine(offGraphics);

		//sender
		offGraphics.setColor(Color.blue);
		offGraphics.fillRect(10,40,30,30);
		offGraphics.setColor(Color.black);
		offGraphics.drawString("Sender",5,90);
		offGraphics.drawRect(10,40,30,30);

		//receiver
		offGraphics.setColor(Color.blue);
		offGraphics.fillRect(490,40,30,30);
		offGraphics.setColor(Color.black);
		offGraphics.drawString("Receiver",485,90);
		offGraphics.drawRect(490,40,30,30);

		offGraphics.drawString("Propagation speed : 2.8 x 10^8 m/sec",175,105);
		//display offscreen image
		g.drawImage(offImage, 0, 0, this);
	}

	private void launchSim()
	{
		setupEnabled(false);
		//setup line
		myLine.setup(length.getVal(), rate.getVal() );
		myLine.emitPacket(size.getVal(),0);
		//setup timer
		timerTask=new TickTask(1E-5,myLine.totalTime());
		timerThread=new Thread(timerTask);
		//start simulation
		simulationRunning=true;
		timerThread.start();
	}

	private void stopSim()
	{
		timerTask.endNow();
		simulationRunning=false;
		setupEnabled(true);
	}

	public void setupEnabled(boolean value)
	{
		start.setEnabled(value);
		length.setEnabled(value);
		rate.setEnabled(value);
		size.setEnabled(value);
	}

	//my choice
	class MyChoice extends Choice
	{
		private double vals[];

		public MyChoice(String items[], double values[],int defaultValue)
		{
			for (int i=0; i<items.length;i++) {super.addItem(items[i]);}
			vals=values;
			select(defaultValue-1);
		}

		public double getVal() {return  vals[super.getSelectedIndex()];}
	}
	//tickTask
	class TickTask implements Runnable
	{
		private double counter;
		private double length;
		private double tick;

		public TickTask(double t,double l)
		{
			length=l;
			tick=t;
			counter=0;
		}

		public void run()
		{
			while (LineSimApplet.this.simulationRunning)
			{
				counter+=tick;
				LineSimApplet.this.myLine.sendTime(counter);
				LineSimApplet.this.repaint();
				if (counter>=length)
				{
					LineSimApplet.this.myLine.clearPackets();
					LineSimApplet.this.timerThread.suspend();
				}
				try  {LineSimApplet.this.timerThread.sleep(50);} catch (Exception e) { }
			}
		}

		public void endNow() {
			length=counter;
		}
	}
}

//Line class
class Line
{
	//graphic variables
	private int gX;
	private int gY;
	private int gWidth;
	private int gHeight;
	//characteristic variables
	final double celerity = 2.8E+8;
	private double length;
	private double rate;
	//simulation variables
	private double time;
	private Packet myPacket;

	public Line(int x, int y, int w, int h)
	{
		//graphic init
		gX=x;
		gY=y;
		gWidth=w;
		gHeight=h;
	}

	public void setup(double l, double r)
	{
		length=l;
		rate=r;
	}

	void sendTime(double now)
	{
		time=now; //update time
		removeReceivedPackets(now);
	}

	void emitPacket(double s, double eT)
	{
		myPacket= new Packet(s,eT);
	}

	private void removeReceivedPackets(double now)
	{
		if (!(myPacket==null))
		{
			if ( now>myPacket.emissionTime+(myPacket.size/rate)+length*celerity )
			{
				clearPackets();
			}
		}
	}

	public void clearPackets()
	{
		myPacket=null;
	}

	public double totalTime()
	{
		double emmissionTime=(myPacket.size/rate);
		double onLineTime=(length/celerity);
		return (emmissionTime+onLineTime);
	}

	public void drawLine(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(gX,gY+1,gWidth,gHeight-2);
		g.setColor(Color.black);
		g.drawRect(gX,gY,gWidth,gHeight);
		g.setColor(Color.red);
		g.drawString(timeToString(time),gX+gWidth/2-10,gY+gHeight+15);
		drawPackets(g);
	}

	private void drawPackets(Graphics g)
	{
		if (!(myPacket==null))
		{
			double xfirst;
			double xlast;
			//compute time units
			xfirst=time-myPacket.emissionTime;
			xlast=xfirst-(myPacket.size/rate);
			//compute position
			xfirst=xfirst*celerity*gWidth/length;
			xlast=xlast*celerity*gWidth/length;
			if (xlast<0) {xlast=0;}
			if (xfirst>gWidth ) {xfirst=gWidth;}
			//draw
			g.setColor(Color.red);
			g.fillRect(gX+(int)(xlast),gY+1,(int)(xfirst-xlast),gHeight-2);
		}
	}

	static private String timeToString(double now)
	{
		String res=Double.toString(now*1000);
		int dot=res.indexOf('.');
		String deci=res.substring(dot+1)+"000";
		deci=deci.substring(0,3);
		String inte=res.substring(0,dot);
		return inte+"."+deci+" ms";
	}
}

class Packet
{
	double size;
	double emissionTime;

	Packet(double s, double eT)
	{
		size=s;
		emissionTime=eT;
	}
}
 