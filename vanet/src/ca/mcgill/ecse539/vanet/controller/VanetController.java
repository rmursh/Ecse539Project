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
	private static final double VIEW_LENGTH = 1000;
	private static VANET vnt = VANET.getInstance();
	ArrayList<Integer> Chlist = new ArrayList<>();
	private static List<Node> nodes=null;
	private static List<Cluster> clusters=null;
	private static List<Node> clusterHeads=null;
    private static int clusterCount = 1;
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
            		XYDataset data= createDataset(temp);
    	        	JFreeChart chartnew = ChartFactory.createScatterPlot(
    	                "Car Positions at Time " + currentFrame, // chart title
    	                "X", // x axis label
    	                "Y", // y axis label
    	                data, // data  ***-----PROBLEM------***
    	                PlotOrientation.VERTICAL,
    	                true, // include legend
    	                true, // tooltips
    	                false // urls
    	                );
    	
    	        	XYPlot plot = (XYPlot) chartnew.getPlot();
    	        	XYItemRenderer renderer  = plot.getRenderer(0);
    	        	for(int i = 0 ; i < plot.getSeriesCount() - 1; i++){
    	        		if(i%3 ==0){
            	        	renderer.setSeriesShape(i, circle);
            	        	renderer.setSeriesPaint(i,Color.BLUE);
    	        		}
    	        		else if(i%3 ==1){
    	        			renderer.setSeriesShape(i, circle);
            	        	renderer.setSeriesPaint(i,Color.RED);
    	        		}
    	        		else{
    	        			renderer.setSeriesShape(i, circle);
            	        	renderer.setSeriesPaint(i,Color.GREEN);
    	        		}

    	        	}
//    	        	renderer.setSeriesShape(0, circle);
//    	        	renderer.setSeriesPaint(0,Color.BLUE);
    	        	renderer.setSeriesShape(plot.getSeriesCount() - 2, circle);
    	        	renderer.setSeriesPaint(plot.getSeriesCount() - 2,Color.MAGENTA);
    	        	renderer.setSeriesShape(plot.getSeriesCount() - 1, ShapeUtilities.createDiagonalCross(3, 1));
    	        	renderer.setSeriesPaint(plot.getSeriesCount() - 1,Color.BLACK);
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
    	            System.out.println("Clusterhead : " + nodes.get(0).getCuster().getCH().getId()+ " Cluster: " + nodes.get(0).getCuster().getVoting_ID()+ " " + nodes.get(0).getTime().getTimeframe());
    	    	    //System.out.println(CreateTransmission(1,15,temp.getTimeframe()));
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
	    int numClusters = (int)(VIEW_LENGTH/300);
	    List<XYSeries> xyList1 = new ArrayList<XYSeries>();
	    List<XYSeries> xyList2 = new ArrayList<XYSeries>();
	    for(int i =0 ;  i < numClusters; i++){
	    	xyList1.add(new XYSeries("Cluster " + i));
	    }
	    XYSeries clusterHeads = new XYSeries("ClusterHead");
	    XYSeries leftovers = new XYSeries("Cluster" + numClusters);

	    List<Node> ch = CalculateVariables(t);

	    for(Node n : nodes){
	    	if(!ch.contains(n))
	    	{

	    		for(int i =0; i < numClusters; i++){
	    			if(n.getPositionX() < 300){
				        double x = n.getPositionX();
				        double y = n.getPositionY();
				        xyList1.get(i).add(x, y);
		    		}
	    			else if(n.getPositionX() >= i*300 && n.getPositionX() < (i+1)*300){
				        double x = n.getPositionX();
				        double y = n.getPositionY();
				        xyList1.get(i).add(x, y);
		    		}

	    		}
    			if(n.getPositionX() >= numClusters*300){
			        double x = n.getPositionX();
			        double y = n.getPositionY();
			        leftovers.add(x, y);
	    		}
         
	    	}
	    	else{
	    		
	    		double x = n.getPositionX();
		        double y = n.getPositionY();
		        clusterHeads.add(x, y);

	    	}
	    }

	    XYSeries series2 = new XYSeries("ClusterHead");
	    ArrayList<XYSeries> xyList = new ArrayList<XYSeries>();
	    int i = 0;
	    for(Cluster cluster : vnt.getClusters()){
	    	int timeFrame = cluster.getCH().getTime().getTimeframe(); 
	        XYSeries xy = new XYSeries("Normal Nodes " + i);
            
	    	if(timeFrame == t.getTimeframe()){
                i++;
	    		for(Node n :cluster.getClustermembers()){
	    			if(!n.hasIs_CH()){
	    				double x = n.getPositionX();
				        double y = n.getPositionY();
				        xy.add(x, y);
				        
	    			}
	    	    	else{
			    		double x = n.getPositionX();
				        double y = n.getPositionY();
				        series2.add(x, y);
	    		    }
	    		}
	    	   
                xyList.add(xy);
	    		//result.addSeries(xy);
	    		//result.addSeries(series2);
	    	}
	    }

        for(XYSeries xy : xyList1){
    	    result.addSeries(xy);
        }
        result.addSeries(leftovers);
	    result.addSeries(clusterHeads);

	    return result;
	}


	public VanetController(String path, double x, double y) {

		xRange = x;
		yRange = y;
		doCalculationsAndPlot(path);
		
	}

	public static int CreateTransmission(int Source, int Destination, int TimeFrame) {
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
			for (Node nighbour : src_ch.getNeighbors()) 
			{
				if (!nighbour.getCuster().getCH().equals(src_ch))
				{
					if(nighbour.getCuster().getCH().equals(dst_ch))
					{
					exit = true;
					hopcount++;
					break;
					}
				 
				else
				{
					src_ch = nighbour.getCuster().getCH();
					hopcount++;
					break;
				}
					
				} // outer if end

			}// for end
			
		}// while end
//		try {
//			Transmission TR = new Transmission(hopcount, vnt, src, dst);
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}

		return hopcount;

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
