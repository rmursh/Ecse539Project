package ca.mcgill.ecse539.vanet.controller;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse539.vanet.model.*;
import java.util.Scanner;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class VanetController {

	private static VANET vnt = VANET.getInstance();
	ArrayList<Integer> Chlist = new ArrayList<>();
	private static List<Node> nodes=null;
	public static void main(String[] args) {
    ReadFile();
    	
    	for (Time t : vnt.getTimes())
    	{
    		nodes= getnodesbyFrameNumber(t.getTimeframe());
    		for (Node n : nodes)
    		  System.out.println(n.getId()+ " " + n.getPositionX()+ " " + n.getPositionY() + " " + n.getSpeed() + " " + n.getDirection() +" " + n.getTime().toString()+ "\n");
    	 
    	}
    
    	JFreeChart chart = ChartFactory.createScatterPlot(
            "Scatter Plot", // chart title
            "X", // x axis label
            "Y", // y axis label
            createDataset(), // data  ***-----PROBLEM------***
            PlotOrientation.VERTICAL,
            true, // include legend
            true, // tooltips
            false // urls
            );

    	Shape circle = new Ellipse2D.Double(-2.0, -2.0, 6.0, 6.0);
    	XYPlot plot = (XYPlot) chart.getPlot();
    	XYItemRenderer renderer  = plot.getRenderer();
    	renderer.setSeriesShape(0, circle);
    	renderer.setSeriesPaint(0,Color.BLUE);
    	plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);
        // create and display a frame...
        ChartFrame frame = new ChartFrame("Car Positions", chart);
        frame.pack();
        frame.setVisible(true);
    
    }
	
	private static XYDataset createDataset() {
	    XYSeriesCollection result = new XYSeriesCollection();
	    XYSeries series = new XYSeries("Node");
	    for (Node n : nodes) {
	        double x = n.getPositionX();
	        double y = n.getPositionY();
	        series.add(x, y);
	    }
	    result.addSeries(series);
	    return result;
	}

	public VanetController() {

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

	public void CalculateVariables() {
		// Node n= new Node(0, 0, 0, 0, 0, vnt);// this how you will create
		// nodes and by passing vnt parameter in the constructor, so the node
		// will be automatically added to this instance of VANET, therefore you
		// don't need to do vnt.addNode

	}

	public static String ReadFile() {
		Scanner read = null;
		int X, Y, ID, TR;
		float D, S;
		try {
			read = new Scanner(new BufferedReader(new FileReader("d.txt")));
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
