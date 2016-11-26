package ca.mcgill.ecse539.vanet.controller;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse539.vanet.model.*;
import java.util.Scanner;

import javax.swing.Timer;

import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class VanetController {

	private double xRange = 1000;
	private double yRange = 100;
	private static final int DELAY = 1000;
	private static VANET vnt = VANET.getInstance();
	ArrayList<Integer> Chlist = new ArrayList<>();
	private static List<Node> nodes=null;
	private static List<Cluster> clusters=null;
	private static List<Node> clusterHeads=null;

	private JFreeChart chart = null;
	private Shape circle = new Ellipse2D.Double(-2.0, -2.0, 6.0, 6.0);
	private Shape square = new Rectangle2D.Double(-2.0, -2.0, 6.0, 6.0);

	public void setXRange(double x){
		xRange = x;
	}
	
	public void setYRange(double y){
		yRange = y;
	}
	public void doCalculationsAndPlot(String fp) {
		ReadFile(fp);
		// Initialization of the timer. 1 second delay and this class as ActionListener
		ChartFrame frame = new ChartFrame("Car Positions", chart);
		Iterator t = vnt.getTimes().iterator();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	try{
            		  //...Perform a task...
            		Time temp = (Time) t.next();
                	int currentFrame = (temp.getTimeframe());
            		nodes= getnodesbyFrameNumber(currentFrame);  
    	        	JFreeChart chartnew = ChartFactory.createScatterPlot(
    	                "Car Positions at Time " + currentFrame, // chart title
    	                "X", // x axis label
    	                "Y", // y axis label
    	                createDataset(temp), // data  ***-----PROBLEM------***
    	                PlotOrientation.VERTICAL,
    	                true, // include legend
    	                true, // tooltips
    	                false // urls
    	                );
    	
    	        	XYPlot plot = (XYPlot) chartnew.getPlot();
    	        	XYItemRenderer renderer  = plot.getRenderer(0);
    	        	renderer.setSeriesShape(0, circle);
    	        	renderer.setSeriesPaint(0,Color.BLUE);
    	        	renderer.setSeriesShape(1, ShapeUtilities.createDiagonalCross(3, 1));
    	        	renderer.setSeriesPaint(1,Color.RED);
    	        	plot.setBackgroundPaint(Color.white);
    	            plot.setRangeGridlinePaint(Color.black);
    	            if(xRange != -1 && yRange != -1){
        	            ValueAxis yAxis = plot.getRangeAxis();
        	            yAxis.setRange(-10.0, yRange);
        	            plot.getDomainAxis().setRange(0.00, xRange);	
    	            }
    	            // create and display a frame...
    	            ChartPanel panel = frame.getChartPanel();
    	            panel.setChart(chartnew);
    	            frame.pack();
    	            frame.setVisible(true);
            	}
            	catch(Exception e){
            		
            	}
            
            }
        };
        Timer timer = new Timer(DELAY ,taskPerformer);

        timer.start();

    }
	
	private static XYDataset createDataset(Time t) {
//        vnt.getClusters().clear();
//        System.out.println(vnt.getClusters());
	    XYSeriesCollection result = new XYSeriesCollection();
	    XYSeries series1 = new XYSeries("Node");
	    XYSeries series2 = new XYSeries("ClusterHead");
	    List<Node> ch = CalculateVariables(t);
        int clusterCount = 1;
	    for(Node n : nodes){
	    	if(ch.contains(n))
	    	{
	    		Cluster cluster = new Cluster(clusterCount, vnt,n);
	    		for(Node m : n.getNeighbors()){
	    			if(!cluster.getClustermembers().contains(m)){
	    				cluster.addClustermember(m);
	    			}
	    		}
	    		clusterCount++;
	    	}
	    }
        for (Node n : nodes) {
	    	if(!n.hasIs_CH())
	    	{
		        double x = n.getPositionX();
		        double y = n.getPositionY();
		        series1.add(x, y);
	    	}
	    	else{

	    		double x = n.getPositionX();
		        double y = n.getPositionY();
		        series2.add(x, y);
	    	}

	    }
	    result.addSeries(series1);
	    result.addSeries(series2);
	    return result;
	}


	public VanetController(String path, double x, double y) {

		xRange = x;
		yRange = y;
		doCalculationsAndPlot(path);
		
	}

	public String CreateTransimission(int Source, int Destination, int TimeFrame) {
		Time TF = null;
		Node src = null;
		Node dst = null;
		Node src_ch = null;
		Node dst_ch = null;
		int hopcount = 2;
		boolean exit = false;
		for (Time t : vnt.getTimes()) {
			if (t.getTimeframe() == TimeFrame) {
				TF = t;
				break;
			}
		}
		for (Node n : TF.getNodes()) {
			if (n.getId() == Source)
				src = n;
			if (n.getId() == Destination)
				dst = n;
		}
		src_ch = src.getCuster().getCH();
		dst_ch = dst.getCuster().getCH();
		while (!exit) {
			for (Node nighbour : src_ch.getNeighbors()) {
				if (nighbour == dst_ch) {
					exit = true;
					break;
				} else if (nighbour.hasIs_CH() == true) {
					src_ch = nighbour;
				}

			}
			hopcount++;
		}
		try {
			Transmission TR = new Transmission(hopcount, vnt, src, dst);
		} catch (Exception e) {
			return e.toString();
		}

		return "";

	}

	public static List<Node> CalculateVariables(Time t) {
		// Node n= new Node(0, 0, 0, 0, 0, vnt);// this how you will create
		// nodes and by passing vnt parameter in the constructor, so the node
		// will be automatically added to this instance of VANET, therefore you
		// don't need to do vnt.addNode
		double difference;
		double speeda;
		double speedb;
		double distancea = 0;
		double distanceb;
		int directiona;
		int directionb;
		int neighbor_size;
		double qos;
		double max=0;
		List <Node>list_neighbors= new ArrayList<Node>();
		List <Node>cluster_head= new ArrayList<Node>();
		Node current_head;
		Node current_neighbors;
		Node temp_head = null;
		double current_qos;  
		int time = t.getTimeframe();
		int nodesize = nodes.size();
			// outer loop			
			for (int i = 0; i <nodesize; i++) {
				//get the required data from the class
				double x1 = nodes.get(i).getPositionX();
				double y1 = nodes.get(i).getPositionY();
				directiona=(int) nodes.get(i).getDirection();
				// System.out.println(distancea);
				// inner loop
				// determine neighbor nodes (clusters)
				for (int j = 0; j < nodesize; j++) {
					if (i != j) 
					{
						speedb = nodes.get(j).getSpeed();
						double x2 = nodes.get(j).getPositionX();
						double y2 = nodes.get(j).getPositionY();
						directionb=(int) nodes.get(j).getDirection();
						difference = Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2) + Math.abs(y1-y2)*Math.abs(y1-y2));
						if ((difference<=300) && (directiona==directionb)){ // choosing neighboring criteria
							nodes.get(i).addNeighbor(nodes.get(j));
						}
						//{		
					}
					
				} // end inner loop
			} // end outer loop
			//testing data:
		
			// Calculate QoS value for each node and send it to the class Node
			for (int i = 0; i <nodesize; i++) {
				speeda = nodes.get(i).getSpeed();
                distancea = speeda*time;
				neighbor_size=nodes.get(i).getNeighbors().size();
				if (speeda!=0){
					qos=((1/speeda)*distancea*neighbor_size);
				}
				else{
					qos=0.0;
				}
				nodes.get(i).setQos((int)qos);

		    }
			
			// Vote for the node with highest QoS among neighbors to be the CH 
			List<Node> list_head=new ArrayList<Node>();
			for (int i=0;i<nodesize;i++)
			{
	           // list_head=new ArrayList();
				max=0;
				list_neighbors=nodes.get(i).getNeighbors();
				for (int j=0;j<list_neighbors.size();j++)
				{
					current_neighbors= list_neighbors.get(j);
					current_qos=current_neighbors.getQos();
					if (current_qos>max)
					{
						max=current_qos;
						temp_head=current_neighbors;
					}
				}
				list_head.add(temp_head);
			}

			//List<Integer> list_head=new ArrayList();
			for (Node k : list_head)
			{
				if (cluster_head.isEmpty())
				{
					cluster_head.add(list_head.get(0));
				}
				else{
					current_head=k;
					if (!cluster_head.contains(current_head)){
					cluster_head.add(current_head);
					}
					
				}
			}

			return cluster_head;

			//System.out.println( "time " + time +"  list_head " + list_head);

			

	}

	public static String ReadFile(String path) {
		Scanner read = null;
		int X, Y, ID, TR;
		float D, S;
		try {
			read = new Scanner(new BufferedReader(new FileReader(path)));
		} catch (Exception e) {
			return e.toString();
		}

		while (read.hasNext()) {
			TR = read.nextInt();
			ID = read.nextInt();
			X = read.nextInt();
			Y = read.nextInt();
			S = read.nextFloat();
			D = read.nextFloat();
			Time T = null;
			Node n = null;
			boolean exist = false;
			for (Time t : vnt.getTimes()) {
				if (t.getTimeframe() == TR) {
					exist = true;
					T = t;
					break;
				}
			}
			if (!exist) {
				try {
					T = new Time(TR, vnt);
				} catch (Exception e) {
					read.close();
					return e.toString();
				}
			}
			try {
				n = new Node(ID, X, Y, S, D, vnt, T);
			} catch (Exception e) {
				read.close();
				return e.toString();
			}
			// System.out.println(n.getId()+ " " + n.getPositionX()+ " " +
			// n.getPositionY() + " " + n.getSpeed() + " " + n.getDirection() +"
			// " + n.getTime().toString()+ "\n");
		}
		read.close();
		return "";
	}

	public static List<Node> getnodesbyFrameNumber(Integer F) {
		Time TF = null;
		boolean found = false;

		for (Time t : vnt.getTimes()) {
			if (t.getTimeframe() == F) {
				TF = t;
				found = true;
				break;
			}
		}
		if (found)

			return TF.getNodes();
		else
			return null;
	}

}
