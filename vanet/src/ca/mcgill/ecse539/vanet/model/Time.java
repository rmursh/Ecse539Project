/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse539.vanet.model;
import java.util.*;

// line 9 "../../../../../model.ump"
public class Time
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Time Attributes
  private int timeframe;

  //Time Associations
  private VANET vANET;
  private List<Node> nodes;
  private List<Cluster> clusters;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Time(int aTimeframe, VANET aVANET)
  {
    timeframe = aTimeframe;
    boolean didAddVANET = setVANET(aVANET);
    if (!didAddVANET)
    {
      throw new RuntimeException("Unable to create time due to vANET");
    }
    nodes = new ArrayList<Node>();
    clusters = new ArrayList<Cluster>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTimeframe(int aTimeframe)
  {
    boolean wasSet = false;
    timeframe = aTimeframe;
    wasSet = true;
    return wasSet;
  }

  public int getTimeframe()
  {
    return timeframe;
  }

  public VANET getVANET()
  {
    return vANET;
  }

  public Node getNode(int index)
  {
    Node aNode = nodes.get(index);
    return aNode;
  }

  public List<Node> getNodes()
  {
    List<Node> newNodes = Collections.unmodifiableList(nodes);
    return newNodes;
  }

  public int numberOfNodes()
  {
    int number = nodes.size();
    return number;
  }

  public boolean hasNodes()
  {
    boolean has = nodes.size() > 0;
    return has;
  }

  public int indexOfNode(Node aNode)
  {
    int index = nodes.indexOf(aNode);
    return index;
  }

  public Cluster getCluster(int index)
  {
    Cluster aCluster = clusters.get(index);
    return aCluster;
  }

  public List<Cluster> getClusters()
  {
    List<Cluster> newClusters = Collections.unmodifiableList(clusters);
    return newClusters;
  }

  public int numberOfClusters()
  {
    int number = clusters.size();
    return number;
  }

  public boolean hasClusters()
  {
    boolean has = clusters.size() > 0;
    return has;
  }

  public int indexOfCluster(Cluster aCluster)
  {
    int index = clusters.indexOf(aCluster);
    return index;
  }

  public boolean setVANET(VANET aVANET)
  {
    boolean wasSet = false;
    if (aVANET == null)
    {
      return wasSet;
    }

    VANET existingVANET = vANET;
    vANET = aVANET;
    if (existingVANET != null && !existingVANET.equals(aVANET))
    {
      existingVANET.removeTime(this);
    }
    vANET.addTime(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfNodes()
  {
    return 0;
  }

  public Node addNode(int aId, int aPositionX, int aPositionY, float aSpeed, float aDirection, VANET aVANET)
  {
    return new Node(aId, aPositionX, aPositionY, aSpeed, aDirection, aVANET, this);
  }

  public boolean addNode(Node aNode)
  {
    boolean wasAdded = false;
    if (nodes.contains(aNode)) { return false; }
    Time existingTime = aNode.getTime();
    boolean isNewTime = existingTime != null && !this.equals(existingTime);
    if (isNewTime)
    {
      aNode.setTime(this);
    }
    else
    {
      nodes.add(aNode);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeNode(Node aNode)
  {
    boolean wasRemoved = false;
    //Unable to remove aNode, as it must always have a time
    if (!this.equals(aNode.getTime()))
    {
      nodes.remove(aNode);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addNodeAt(Node aNode, int index)
  {  
    boolean wasAdded = false;
    if(addNode(aNode))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNodes()) { index = numberOfNodes() - 1; }
      nodes.remove(aNode);
      nodes.add(index, aNode);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveNodeAt(Node aNode, int index)
  {
    boolean wasAdded = false;
    if(nodes.contains(aNode))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNodes()) { index = numberOfNodes() - 1; }
      nodes.remove(aNode);
      nodes.add(index, aNode);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addNodeAt(aNode, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfClusters()
  {
    return 0;
  }

  public Cluster addCluster(int aVoting_ID, VANET aVANET)
  {
    return new Cluster(aVoting_ID, aVANET, this);
  }

  public boolean addCluster(Cluster aCluster)
  {
    boolean wasAdded = false;
    if (clusters.contains(aCluster)) { return false; }
    Time existingTime = aCluster.getTime();
    boolean isNewTime = existingTime != null && !this.equals(existingTime);
    if (isNewTime)
    {
      aCluster.setTime(this);
    }
    else
    {
      clusters.add(aCluster);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCluster(Cluster aCluster)
  {
    boolean wasRemoved = false;
    //Unable to remove aCluster, as it must always have a time
    if (!this.equals(aCluster.getTime()))
    {
      clusters.remove(aCluster);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addClusterAt(Cluster aCluster, int index)
  {  
    boolean wasAdded = false;
    if(addCluster(aCluster))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClusters()) { index = numberOfClusters() - 1; }
      clusters.remove(aCluster);
      clusters.add(index, aCluster);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveClusterAt(Cluster aCluster, int index)
  {
    boolean wasAdded = false;
    if(clusters.contains(aCluster))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClusters()) { index = numberOfClusters() - 1; }
      clusters.remove(aCluster);
      clusters.add(index, aCluster);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addClusterAt(aCluster, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    VANET placeholderVANET = vANET;
    this.vANET = null;
    placeholderVANET.removeTime(this);
    for(int i=nodes.size(); i > 0; i--)
    {
      Node aNode = nodes.get(i - 1);
      aNode.delete();
    }
    for(int i=clusters.size(); i > 0; i--)
    {
      Cluster aCluster = clusters.get(i - 1);
      aCluster.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "timeframe" + ":" + getTimeframe()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "vANET = "+(getVANET()!=null?Integer.toHexString(System.identityHashCode(getVANET())):"null")
     + outputString;
  }
}