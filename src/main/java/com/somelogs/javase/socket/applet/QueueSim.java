package com.somelogs.javase.socket.applet;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * https://media.pearsoncmg.com/aw/aw_kurose_network_2/applets/queuing/queuing.html
 *
 * Queuing and Loss Applet
 * As we learned in Chapter 1, the most complicated and interesting component of end-to-end delay is queuing delay.
 * In this applet, you specify the packet arrival rate and the link transmission speed.
 * You'll then see packets arrive and queue for service.
 * When the queue becomes full, you'll see the queue overflow--that is, packet loss.
 *
 * A particularly interesting case is when the emission and transmission rates are the same,
 * for example when both are 500 packets/sec. If you let the applet run for a very long time, you'll eventually see the queue fill up and overflow.
 * Indeed when the two rates are the same (that is, ρ = 1), the queue grows without bound (with random inter-arrival times), as described in the text.
 */
public class QueueSim extends Applet
{
	Button start=new Button("Start");
	Button reset=new Button("Reset");
	MyChoice emitRate=new MyChoice(new String[] {"500  packet/s","350 packet/s"}, new double[] {2E-3,3E-3},1);
	MyChoice processorRate=new MyChoice(new String[] {"1000 packet/s","500  packet/s","350 packet/s"}, new double[] {1E-3,2E-3,3E-3},3);
	SimTimer myTimer;

	public void init()
	{
		try
		{
			myTimer=new SimTimer(1E-4,1000,this);
			setBackground(Color.white);
			add(new Label("Emission rate",Label.RIGHT));
			add(emitRate);
			add(new Label("Transmission rate",Label.RIGHT));
			add(processorRate);
			start.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent event)
				{
					emitRate.setEnabled(false);
					processorRate.setEnabled(false);
					start.setEnabled(false);
					myTimer.launchSim(emitRate.getVal(), processorRate.getVal());
				}
			});
			add(start);
			reset.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent event)
				{
					emitRate.setEnabled(true);
					processorRate.setEnabled(true);
					start.setEnabled(true);
					myTimer=new SimTimer(1E-4,1000,QueueSim.this);
				}
			});
			add(reset);
		}
		catch(Exception e) {}
	}

	public void paint (Graphics g)
	{
		update(g); // eliminate flashing : update is overriden
	}

	public void update (Graphics g)
	{
		//work on a offscreen image
		Dimension offDimension = getSize();
		Image offImage = createImage(offDimension.width, offDimension.height);
		Graphics offGraphics = offImage.getGraphics();
		myTimer.draw(offGraphics);
		g.drawImage(offImage, 0, 0, this);
	}

}

//////////////////////////////////////////////////////////////////////////////
class SimTimer implements Runnable
		//////////////////////////////////////////////////////////////////////////////
{
	private double time;
	private double tic;
	private Line line1;
	private Sender sender1;
	private Dropper dropper1;
	private Queue queue1;
	private Line line2;
	private Processor proc1;
	private Applet target;
	private Thread timerThread;

	public SimTimer(double tick, double length, Applet tgt)
	{
		line1=new Line(1E-2,new Rectangle(10,60,200,10));
		sender1=new Sender(1E-3,line1);
		dropper1= new Dropper(.6E-2,new Rectangle(200,70,10,60));
		queue1=new Queue(10,line1,dropper1,new Rectangle(210,40,100,50));
		line2=new Line(1E-2,new Rectangle(340,60,200,10));
		proc1=new Processor(2E-3,queue1,line2,new Rectangle(310,50,30,30));
		target=tgt;
		time=0;
		tic=tick;
		timerThread=new Thread(this);
	}

	public void launchSim(double emitRate, double processorRate)
	{
		sender1.setEmitInterval(emitRate);
		proc1.setWrkLength(processorRate);
		timerThread.start();
	}

	public void run()
	{
		while (true)
		{
			dropper1.setTime(time);
			sender1.setTime(time);
			line1.setTime(time);
			queue1.setTime(time);
			proc1.setTime(time);
			line2.setTime(time);
			target.repaint();
			time+=tic;
			try {timerThread.sleep(50);} catch (Exception e) { };
		}
	}

	public void draw(Graphics g)
	{
		dropper1.draw(g);
		line1.draw(g);
		line2.draw(g);
		queue1.draw(g);
		proc1.draw(g);
		g.drawString(TimedClass.timeToString(time),10,110);
		g.drawString(queue1.getDropStat(),10,125);
	}
}
//////////////////////////////////////////////////////////////////////////////
class Packet
		//////////////////////////////////////////////////////////////////////////////
{
	static final double emmissionDelay=1E-3;
	double emmissionTime;
	Color color;

	public Packet(double eT, Color c)
	{
		emmissionTime=eT;
		color=c;
	}
}

//////////////////////////////////////////////////////////////////////////////
class PacketFIFO
		//////////////////////////////////////////////////////////////////////////////
{
	private int length;
	private Packet[] content;

	public PacketFIFO()
	{
		length=0;
	}

	public int getLength()
	{
		return length;
	}

	public void add(Packet P)
	{
		if (length==0)
		{
			content=new Packet[1];
			content[0]=P;
		}
		else
		{
			Packet[] temp=new Packet[length+1];
			for (int i=0;i<length;i++) {temp[i]=content[i];}
			temp[length]=P;
			content=temp;
		}
		length++;
	}

	public void rem()
	{
		Packet[] temp=new Packet[length-1];
		for (int i=0;i<length-1;i++) {temp[i]=content[i+1];}
		content=temp;
		length--;
	}

	public Packet getPacket(int index)
	{
		return content[index];
	}

	public Packet getFirstPacket()
	{
		return content[0];
	}
}
//////////////////////////////////////////////////////////////////////////////
class Sender
		/////////////////////////////////////////////////////////////////////////////
{
	final Color packetsColors[]={Color.blue,Color.red,Color.green,Color.gray,Color.magenta,Color.black,Color.pink};
	private double meanInterval;
	private Line outputLine;
	private double time;
	private double nextEmission;
	private int nextColor;
	private Random randomGenerator;

	public Sender(double meanI, Line outputL)
	{
		meanInterval=meanI;
		outputLine=outputL;
		randomGenerator=new Random();
	}

	public void setEmitInterval(double val) {meanInterval=val;}

	public void setTime(double now)
	{
		time=now;
		update();
	}

	private void update()
	{
		if (nextEmission<time)
		{
			outputLine.emitPacket(time,packetsColors[nextColor]);
			nextColor++;
			if (nextColor>=packetsColors.length){nextColor=0;}
			nextEmission=2*randomGenerator.nextDouble()*(meanInterval-Packet.emmissionDelay)+Packet.emmissionDelay;
			nextEmission+=time;
		}
	}
}

//////////////////////////////////////////////////////////////////////////////
class Line extends TimedClass
		//////////////////////////////////////////////////////////////////////////////
{
	private boolean availableArrivedPacket;
	private Packet arrivedPacket;
	protected PacketFIFO packets;
	protected double propDelay;

	public Line(double propD,Rectangle r)
	{
		packets=new PacketFIFO();
		propDelay=propD;
		shape=r;
		availableArrivedPacket=false;
	}

	public void emitPacket(double eT, Color c)
	{
		packets.add(new Packet(eT,c));
	}

	public void draw(Graphics g)
	{
		for (int i=0;i<packets.getLength();i++)
		{
			drawPacket(packets.getPacket(i),g);
		}
		g.setColor(Color.black);
		g.drawRect(shape.x,shape.y,shape.width,shape.height);
	}

	private void drawPacket(Packet P, Graphics g)
	{
		double x1=((double)shape.x)+(time-P.emmissionTime)*((double)shape.width)/propDelay;
		double x2=x1-(P.emmissionDelay)*((double)shape.width)/propDelay;
		if (x1>((double)(shape.x+shape.width))) x1=(double)(shape.x+shape.width);
		if (x2<((double)(shape.x))) x2=(double)(shape.x);
		g.setColor(P.color);
		g.fillRect((int)x2,shape.y,(int)(x1-x2),shape.height);
	}

	protected void update()
	{
		if (packets.getLength()>0)
		{
			Packet fP=packets.getFirstPacket();
			if (fP.emmissionTime+fP.emmissionDelay+propDelay<time)
			{
				packets.rem();
				arrivedPacket=fP;
				availableArrivedPacket=true;
			}
		}
	}
	public boolean getAvailableArrivedPacket() {return availableArrivedPacket;}

	public Packet pickArrivedPacket()
	{
		availableArrivedPacket=false;
		return arrivedPacket;
	}
}

//////////////////////////////////////////////////////////////////////////////
class Queue extends TimedClass
		//////////////////////////////////////////////////////////////////////////////
{
	private int maxSize;
	private int size;
	private int receivedCounter;
	private int droppedCounter;
	private PacketFIFO packets;
	private Line inputLine;
	private Dropper dropper;

	public Queue(int mS,Line inputL,Dropper defDropper,Rectangle r)
	{
		shape=r;
		maxSize=mS;
		inputLine=inputL;
		packets=new PacketFIFO();
		dropper=defDropper;
	}

	protected void update()
	{
		//if a packet is at input then add it to the queue
		if (inputLine.getAvailableArrivedPacket())
		{
			addPacket(inputLine.pickArrivedPacket());
		}
	}

	private void addPacket(Packet P)
	{
		receivedCounter++;
		//if the queue is full drop packet else queue it
		if (size==maxSize)
		{
			dropPacket(P);
		}
		else
		{
			size++;
			packets.add(P);
		}
	}
	private void dropPacket(Packet P)
	{
		dropper.emitPacket(time,P.color);
		droppedCounter++;
	}

	public void draw(Graphics g)
	{
		for (int i=0;i<maxSize;i++)
		{
			int xp=shape.x+shape.width-(i+1)*(shape.width/maxSize);
			int wp=(shape.width/maxSize);
			if (i<(packets.getLength()))
			{
				g.setColor(packets.getPacket(i).color);
				g.fillRect(xp,shape.y,wp,shape.height);
			}
			g.setColor(Color.black);
			g.drawRect(xp,shape.y,wp,shape.height);
		}
	}

	public boolean getAvailableArrivedPacket() {return (packets.getLength()>0);}

	public Packet pickArrivedPacket()
	{
		size--;
		Packet fP=packets.getFirstPacket();
		packets.rem();
		return fP;
	}

	public String getDropStat() {return Integer.toString(droppedCounter)+" packets dropped out of "+Integer.toString(receivedCounter);}
}

//////////////////////////////////////////////////////////////////////////////
class Processor extends TimedClass
		//////////////////////////////////////////////////////////////////////////////
{
	private boolean busy;
	private double workLength;
	private double curWorkStart;
	private Line outputLine;
	private Queue inputQueue;
	private Packet curPacket;

	public Processor(double wrkLength, Queue inputQ, Line outputL,Rectangle r)
	{
		workLength=wrkLength;
		inputQueue=inputQ;
		outputLine=outputL;
		shape=r;
		busy=false;
	}

	public void setWrkLength(double wrkl) {workLength=wrkl;}

	protected void update()
	{ //if there is a packet and the job is done then emit it
		if ((busy)&&(workDone()))
		{
			outputLine.emitPacket(time, curPacket.color);
			busy=false;
		}
		if ((!(busy))&&(inputQueue.getAvailableArrivedPacket()))
		{
			busy=true;
			curWorkStart=time;
			curPacket=inputQueue.pickArrivedPacket();
		}
	}

	private boolean workDone(){
		return (curWorkStart+workLength<=time);
	}

	public void draw(Graphics g)
	{
		if (busy)
		{
			g.setColor(curPacket.color);
			g.fillRect(shape.x,shape.y,shape.width,shape.height);
		}
		g.setColor(Color.black);
		g.drawRect(shape.x,shape.y,shape.width,shape.height);
	}
}

class Dropper extends Line
{
	public Dropper(double propD,Rectangle r){super(propD,r);}

	public void draw(Graphics g)
	{
		for (int i=0;i<packets.getLength();i++)
		{
			drawPacket(packets.getPacket(i),g);
		}
	}

	private void drawPacket(Packet P, Graphics g)
	{
		double y1=((double)shape.y)+(time-P.emmissionTime)*((double)shape.height)/propDelay;
		double y2=y1-(P.emmissionDelay)*((double)shape.height)/propDelay;
		if (y1>((double)(shape.y+shape.height))) y1=(double)(shape.y+shape.height);
		if (y2<((double)(shape.y))) y2=(double)(shape.y);
		g.setColor(P.color);
		g.fillRect(shape.x,(int)y2,shape.width,(int)(y1-y2));
	}
}

//////////////////////////////////////////////////////////////////////////////
abstract class TimedClass
		//////////////////////////////////////////////////////////////////////////////
{
	protected Rectangle shape;
	protected double time;

	public void setTime(double now)
	{
		time=now;
		update();
	}

	protected abstract void update();

	public abstract void draw(Graphics g);

	static String timeToString(double now)
	{
		String res=Double.toString(now*1000);
		int dot=res.indexOf('.');
		String inte=res.substring(0,dot);
		return inte+" msec";
	}
}
//////////////////////////////////////////////////////////////////////////////
class MyChoice extends Choice
		//////////////////////////////////////////////////////////////////////////////
{
	private double vals[];

	public MyChoice(String items[], double values[], int defaultValue)
	{
		for (int i=0; i<items.length;i++) {super.addItem(items[i]);}
		vals=values;
		super.select(defaultValue-1);
	}

	public double getVal() {return  vals[super.getSelectedIndex()];}
}
 
 
 