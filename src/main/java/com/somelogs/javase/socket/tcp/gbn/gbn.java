package com.somelogs.javase.socket.tcp.gbn;
// Java Applet Demonstration of Go-Back-N Protocol.
// Coded by Shamiul Azom. ASU ID: 993456298
// as project assigned by Prof. Martin Reisslein, Arizona State University
// Course No. EEE-459/591. Spring 2001


import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * TCP 滑动窗口演示
 * https://media.pearsoncmg.com/aw/aw_kurose_network_2/applets/go-back-n/go-back-n.html
 */
public class gbn extends Applet implements ActionListener, Runnable
{
	final int window_len_def = 5;    // Default values of parameters
	final int pack_width_def = 10;
	final int pack_height_def = 30;
	final int h_offset_def = 100;
	final int v_offset_def = 50;
	final int v_clearance_def = 300;
	final int total_packet_def = 20;
	final int time_out_sec_def = 30;

	final Color unack_color = Color.red;  //  Default colors of different packets
	final Color ack_color = Color.yellow;
	final Color sel_color = Color.green;
	final Color roam_pack_color = Color.red;
	final Color roam_ack_color = Color.yellow;
	final Color dest_color = Color.blue;

	int base, nextseq, fps, selected=-1;
	boolean timerFlag, timerSleep;
	Button send, stop, fast, slow, kill, reset;
	Thread gbnThread, timerThread;

	Dimension offDimension;    // flashing eliminator: double buffering
	Image offImage;
	Graphics offGraphics;

	String statusMsg, strCurrentValues;

	packet sender[];

	// Declaring properties

	int window_len, pack_width, pack_height, h_offset, v_offset, v_clearance, total_packet, time_out_sec;


	public void init()
	{
		String strWinLen, strPackWd, strPackHt, strHrOff, strVtOff, strVtClr, strTotPack, strTimeout;

		strWinLen = getParameter("window_length");  // Start collecting parameters
		strPackWd = getParameter("packet_width");
		strPackHt = getParameter("packet_height");
		strHrOff = getParameter("horizontal_offset");
		strVtOff = getParameter("vertical_offset");
		strVtClr = getParameter("vertical_clearance");
		strTotPack = getParameter("total_packets");
		strTimeout = getParameter("timer_time_out");

		// Get the values of the parameters into properties.

		try {
			if (strWinLen != null) window_len = Integer.parseInt(strWinLen);
			if (strPackWd != null) pack_width = Integer.parseInt(strPackWd);
			if (strPackHt != null) pack_height = Integer.parseInt(strPackHt);
			if (strHrOff != null) h_offset = Integer.parseInt(strHrOff);
			if (strVtOff != null) v_offset = Integer.parseInt(strVtOff);
			if (strVtClr != null) v_clearance = Integer.parseInt(strVtClr);
			if (strTotPack != null) total_packet = Integer.parseInt(strTotPack);
			if (strTimeout != null) time_out_sec = Integer.parseInt(strTimeout);
		} catch (Exception e) {}

		// If parameter is not found, use default values.

		window_len = (window_len > 0) ? window_len : window_len_def;
		pack_width = (pack_width > 0) ? pack_width : pack_width_def;
		pack_height = (pack_height > 0) ? pack_height : pack_height_def;
		h_offset = (h_offset > 0) ? h_offset : h_offset_def;
		v_offset = (v_offset > 0) ? v_offset : v_offset_def;
		v_clearance = (v_clearance > 0) ? v_clearance : v_clearance_def;
		total_packet = (total_packet > 0) ? total_packet : total_packet_def;
		time_out_sec = (time_out_sec > 0) ? time_out_sec : time_out_sec_def;

		base = 0;    // Defining base
		nextseq = 0;   // Defining Next sequence number.
		fps = 5;    // Defining default Frame per Second.

		sender = new packet[total_packet];
		statusMsg = "Ready to run. Press 'Send New' button to start.";
		strCurrentValues = "Window Base = "+ base +".   Next Sequence No. = "+ nextseq;

		// Defining the buttons

		send = new Button("Send New");
		send.setActionCommand("rdt");
		send.addActionListener(this);

		stop = new Button("Stop Animation");
		stop.setActionCommand("stopanim");
		stop.addActionListener(this);

		fast = new Button("Faster");
		fast.setActionCommand("fast");
		fast.addActionListener(this);

		slow = new Button("Slower");
		slow.setActionCommand("slow");
		slow.addActionListener(this);

		kill = new Button("Kill Packet");
		kill.setActionCommand("kl");
		kill.addActionListener(this);
		kill.setEnabled(false);

		reset = new Button("Reset");
		reset.setActionCommand("rst");
		reset.addActionListener(this);

		// Adding the buttons

		add(send);
		add(stop);
		add(fast);
		add(slow);
		add(kill);
		add(reset);
	}


	public void start()
	{
		if (gbnThread==null)     // Creating main thread and start it
			gbnThread = new Thread(this);
		gbnThread.start();
	}

	public void run()
	{
		Thread currenthread = Thread.currentThread();

		while (currenthread==gbnThread)   // While the animation is running
			if (onTheWay(sender))    // Checks if any of the packets are travelling
			{
				for (int i=0; i<total_packet; i++)
					if (sender[i]!= null)
						if (sender[i].on_way)     // If packet is roaming
							if (sender[i].packet_pos < (v_clearance-pack_height))
								sender[i].packet_pos+=5;  // Move packet
							else if (sender[i].packet_ack)  // If it is moving to destination
							{
								sender[i].reached_dest = true;
								if (check_upto_n(i))   // Send acknowledgement if all preceeding
								{       // packets are received.
									sender[i].packet_pos = pack_height+5;
									sender[i].packet_ack = false;
									statusMsg = "Packet "+i+" received. Acknowledge sent.";
								}
								else
								{
									sender[i].on_way = false;
									statusMsg = "Packet "+i+" received. No acknowledge sent.";
									if (i==selected)
									{
										selected = -1;
										kill.setEnabled(false);
									}
								}
							}
							else if (!sender[i].packet_ack)   // acknowledgement
							{
								statusMsg = "Packet "+ i +" acknowledge received.";
								sender[i].on_way = false;
								for (int n=0; n<=i; n++)
									sender[n].acknowledged = true;
								if (i==selected)
								{
									selected = -1;
									kill.setEnabled(false);
								}

								timerThread = null;    //resetting timer thread

								if (i+window_len<total_packet)
									base = i+1;
								if (nextseq < base+window_len) send.setEnabled(true);

								if (base != nextseq)
								{
									statusMsg += " Timer restarted.";
									timerThread = new Thread(this);
									timerSleep = true;
									timerThread.start();
								}
								else
									statusMsg += " Timer stopped.";
							}
				strCurrentValues = "Window Base = "+ base +".   Next Sequence No. = "+ nextseq;
				repaint();

				try {
					Thread.sleep(1000/fps);
				} catch (InterruptedException e)
				{
					System.out.println("Help");
				}
			}
			else
				gbnThread = null;


		while (currenthread == timerThread)
			if (timerSleep)
			{
				timerSleep=false;
				try {
					Thread.sleep(time_out_sec*1000);
				} catch (InterruptedException e)
				{
					System.out.println ("Timer interrupted.");
				}
			}
			else
			{
				for (int n=base; n<base+window_len; n++)
					if (sender[n] != null)
						if (!sender[n].acknowledged)
						{
							sender[n].on_way = true;
							sender[n].packet_ack = true;
							sender[n].packet_pos = pack_height+5;
						}
				timerSleep = true;
				if (gbnThread == null)
				{
					gbnThread = new Thread (this);
					gbnThread.start();
				}

				statusMsg = "Packets resent by timer. Timer restarted.";
			}
	}


	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();

		if (cmd == "rdt" && nextseq < base+window_len) // Send button is pressed
		{
			sender[nextseq] = new packet(true,pack_height+5);

			statusMsg = "Packet "+ nextseq +" sent.";

			if (base == nextseq)
			{    // i.e. the window is empty and a new data is getting in
				statusMsg += " Timer set for packet "+ base +".";
				if (timerThread == null)
					timerThread = new Thread(this);
				timerSleep = true;
				timerThread.start();
			}

			repaint();
			nextseq++;
			if (nextseq == base+window_len)
				send.setEnabled(false);
			start();
		}

		else if (cmd == "fast")    // Faster button pressed
		{
			fps+=2;
			statusMsg = "Simulation speed increased by 2 fps.";
		}

		else if (cmd == "slow" && fps>2)
		{
			fps-=2;
			statusMsg = "Simulation speed decreased by 2 fps.";
		}

		else if (cmd == "stopanim")
		{
			gbnThread = null;
			if (timerThread != null)
			{
				timerFlag = true;
				timerThread = null;   // added later
			}
			stop.setLabel("Resume");
			stop.setActionCommand("startanim");

			// disableing all the buttons
			send.setEnabled(false);
			slow.setEnabled(false);
			fast.setEnabled(false);
			kill.setEnabled(false);

			statusMsg = "Simulation paused.";
			repaint();
		}

		else if (cmd == "startanim")
		{
			statusMsg = "Simulation resumed.";
			stop.setLabel("Stop Animation");
			stop.setActionCommand("stopanim");
			if (timerFlag)
			{
				statusMsg += " Timer running.";
				timerThread = new Thread(this);
				timerSleep = true;
				timerThread.start();
			}

			// enabling the buttons
			send.setEnabled(true);
			slow.setEnabled(true);
			fast.setEnabled(true);
			kill.setEnabled(true);
			repaint();    // repainted to show new messages

			start();
		}

		else if (cmd == "kl")
		{
			if (sender[selected].packet_ack)
				statusMsg = "Packet "+ selected +" destroyed. Timer running for packet "+base+".";
			else
				statusMsg = "Acknowledgement of packet "+ selected +" destroyed. Timer running for packet "+base+".";

			sender[selected].on_way = false;
			kill.setEnabled(false);
			selected = -1;
			repaint();
		}

		else if (cmd == "rst")
			reset_app();
	}


	public boolean mouseDown(Event e, int x, int y)
	{
		int i, xpos, ypos;
		i = (x-h_offset)/(pack_width+7);
		if (sender[i]!= null)
		{
			xpos = h_offset+(pack_width+7)*i;
			ypos = sender[i].packet_pos;

			if (x>=xpos && x<= xpos+pack_width && sender[i].on_way)
			{
				if ((sender[i].packet_ack && y>=v_offset+ypos && y<=v_offset+ypos+pack_height) || ((!sender[i].packet_ack) && y>=v_offset+v_clearance-ypos && y<=v_offset+v_clearance-ypos+pack_height))
				{
					statusMsg = "Packet "+ i +" selected.";
					sender[i].selected = true;
					selected = i;
					kill.setEnabled(true);
				}
				else
					statusMsg = "Click on a moving packet to select.";
			}
			else
				statusMsg = "Click on a moving packet to select.";
		}
		return true;
	}


	public void paint(Graphics g)    // To eliminate flushing, update is overriden
	{
		update(g);
	}


	public void update(Graphics g)
	{
		Dimension d = size();

		//Create the offscreen graphics context, if no good one exists.
		if ((offGraphics == null) || (d.width != offDimension.width) || (d.height != offDimension.height))
		{
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
		}

		//Erase the previous image.
		offGraphics.setColor(Color.white);
		offGraphics.fillRect(0, 0, d.width, d.height);

		//drawing window


		offGraphics.setColor(Color.black);
		offGraphics.draw3DRect(h_offset+base*(pack_width+7)-4, v_offset-3, (window_len)*(pack_width+7)+1, pack_height+6,true);


		for (int i=0; i<total_packet; i++)
		{
			// drawing the sending row

			if (sender[i]==null)
			{
				offGraphics.setColor(Color.black);
				offGraphics.draw3DRect(h_offset+(pack_width+7)*i, v_offset, pack_width,pack_height,true);
				offGraphics.draw3DRect(h_offset+(pack_width+7)*i, v_offset+v_clearance, pack_width,pack_height,true);
			}
			else
			{
				if (sender[i].acknowledged)
					offGraphics.setColor(ack_color);
				else
					offGraphics.setColor(unack_color);
				offGraphics.fill3DRect (h_offset+(pack_width+7)*i, v_offset,pack_width,pack_height,true);


				// drawing the destination packets

				offGraphics.setColor (dest_color);
				if (sender[i].reached_dest)
					offGraphics.fill3DRect (h_offset+(pack_width+7)*i, v_offset+v_clearance,pack_width,pack_height,true);
				else
					offGraphics.draw3DRect (h_offset+(pack_width+7)*i, v_offset+v_clearance,pack_width,pack_height,true);

				// drawing the moving packets

				if (sender[i].on_way)
				{
					if (i==selected)
						offGraphics.setColor (sel_color);
					else if (sender[i].packet_ack)
						offGraphics.setColor (roam_pack_color);
					else
						offGraphics.setColor (roam_ack_color);

					if (sender[i].packet_ack)
						offGraphics.fill3DRect (h_offset+(pack_width+7)*i, v_offset+sender[i].packet_pos,pack_width,pack_height,true);
					else
						offGraphics.fill3DRect (h_offset+(pack_width+7)*i, v_offset+v_clearance-sender[i].packet_pos,pack_width,pack_height,true);
				}
			}
		}   // for loop ends

		// drawing message boxes

		offGraphics.setColor(Color.black);
		int newvOffset = v_offset+v_clearance+pack_height;
		int newHOffset = h_offset;

		offGraphics.drawString(statusMsg,newHOffset,newvOffset+25);
		//offGraphics.drawString(strCurrentValues,newHOffset,newvOffset+40);

		offGraphics.drawString("Packet",newHOffset+15,newvOffset+60);
		offGraphics.drawString("Acknowledge",newHOffset+85,newvOffset+60);
		offGraphics.drawString("Received Pack",newHOffset+185,newvOffset+60);
		offGraphics.drawString("Selected",newHOffset+295,newvOffset+60);

		offGraphics.drawString("Base = "+base,h_offset+(pack_width+7)*total_packet+10,v_offset+v_clearance/2);
		offGraphics.drawString("NextSeq = "+nextseq,h_offset+(pack_width+7)*total_packet+10,v_offset+v_clearance/2+20);

		offGraphics.setColor(Color.blue);
		offGraphics.drawString("Sender",h_offset+(pack_width+7)*total_packet+10,v_offset+12);
		offGraphics.drawString("Receiver",h_offset+(pack_width+7)*total_packet+10,v_offset+v_clearance+12);

		offGraphics.setColor(Color.gray);
		offGraphics.draw3DRect(newHOffset-10,newvOffset+42,360,25,true);

		offGraphics.setColor(Color.red);
		offGraphics.draw3DRect(h_offset+(pack_width+7)*total_packet+5,v_offset+v_clearance/2-15,80,40,true);

		offGraphics.setColor(roam_pack_color);
		offGraphics.fill3DRect(newHOffset, newvOffset+50,10,10,true);

		offGraphics.setColor(roam_ack_color);
		offGraphics.fill3DRect(newHOffset+70, newvOffset+50,10,10,true);

		offGraphics.setColor(dest_color);
		offGraphics.fill3DRect(newHOffset+170, newvOffset+50,10,10,true);

		offGraphics.setColor(sel_color);
		offGraphics.fill3DRect(newHOffset+280, newvOffset+50,10,10,true);


		g.drawImage(offImage, 0, 0, this);
	}    // method paint ends


	// checks out if an array is on the way to source or destination

	public boolean onTheWay(packet pac[])
	{
		for (int i=0; i<pac.length; i++)
			if (pac[i] == null)
				return false;
			else if (pac[i].on_way) return true;
		return false;
	}

	// checkes all the packets before packno. Returns false if any packet has
	// not reached destination and true if all the packets have reached destination.

	public boolean check_upto_n(int packno)
	{
		for (int i=0; i<packno; i++)
			if (!sender[i].reached_dest)
				return false;
		return true;
	}

	public void reset_app()
	{
		for (int i=0; i<total_packet; i++)
			if (sender[i] != null)
				sender[i] = null;
		base = 0;
		nextseq = 0;
		selected = -1;
		fps = 5;
		timerFlag = false;
		timerSleep = false;
		gbnThread = null;
		timerThread = null;
		if(stop.getActionCommand()=="startanim")  // in case of pause mode, enable all buttons
		{
			slow.setEnabled(true);
			fast.setEnabled(true);
		}

		send.setEnabled(true);
		kill.setEnabled(false);

		stop.setLabel("Stop Animation");
		stop.setActionCommand("stopanim");
		statusMsg = "Simulation restarted. Press 'Send New' to start.";
		repaint();
	}
}


class packet
{
	boolean on_way, reached_dest, acknowledged, packet_ack, selected;
	int packet_pos;

	packet()
	{
		on_way = false;
		selected = false;
		reached_dest = false;
		acknowledged = false;
		packet_ack = true;
		packet_pos = 0;
	}

	packet(boolean onway, int packetpos)
	{
		on_way = onway;
		selected = false;
		reached_dest = false;
		acknowledged = false;
		packet_ack = true;
		packet_pos = packetpos;
	}
}
 