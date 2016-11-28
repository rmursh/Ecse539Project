/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse539.vanet.model;
import java.util.*;

// line 2 "../../../../../model.ump"
public class VANET
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static VANET theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VANET Attributes
  private int area;
  private int range;
  private int path_length;

  //VANET Associations
  private List<Time> times;
  private List<Node> nodes;
  private List<Cluster> clusters;
  private List<Transmission> transmissions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private VANET()
  {
    area = 0;
    range = 0;
    path_length = 0;
    times = new ArrayList<Time>();
    nodes = new ArrayList<Node>();
    clusters = new ArrayList<Cluster>();
    transmissions = new ArrayList<Transmission>();
  }

  public static VANET getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new VANET();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setArea(int aArea)
  {
    boolean wasSet = false;
    area = aArea;
    wasSet = true;
    return wasSet;
  }

  public boolean setRange(int aRange)
  {
    boolean wasSet = false;
    range = aRange;
    wasSet = true;
    return wasSet;
  }

  public boolean setPath_length(int aPath_length)
  {
    boolean wasSet = false;
    path_length = aPath_length;
    wasSet = true;
    return wasSet;
  }

  public int getArea()
  {
    return area;
  }

  public int getRange()
  {
    return range;
  }

  public int getPath_length()
  {
    return path_length;
  }

  public Time getTime(int index)
  {
    Time aTime = times.get(index);
    return aTime;
  }

  public List<Time> getTimes()
  {
    List<Time> newTimes = Collections.unmodifiableList(times);
    return newTimes;
  }

  public int numberOfTimes()
  {
    int number = times.size();
    return number;
  }

  public boolean hasTimes()
  {
    boolean has = times.size() > 0;
    return has;
  }

  public int indexOfTime(Time aTime)
  {
    int index = times.indexOf(aTime);
    return index;
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

  public Transmission getTransmission(int index)
  {
    Transmission aTransmission = transmissions.get(index);
    return aTransmission;
  }

  public List<Transmission> getTransmissions()
  {
    List<Transmission> newTransmissions = Collections.unmodifiableList(transmissions);
    return newTransmissions;
  }

  public int numberOfTransmissions()
  {
    int number = transmissions.size();
    return number;
  }

  public boolean hasTransmissions()
  {
    boolean has = transmissions.size() > 0;
    return has;
  }

  public int indexOfTransmission(Transmission aTransmission)
  {
    int index = transmissions.indexOf(aTransmission);
    return index;
  }

  public static int minimumNumberOfTimes()
  {
    return 0;
  }

  public Time addTime(int aTimeframe)
  {
    return new Time(aTimeframe, this);
  }

  public boolean addTime(Time aTime)
  {
    boolean wasAdded = false;
    if (times.contains(aTime)) { return false; }
    VANET existingVANET = aTime.getVANET();
    boolean isNewVANET = existingVANET != null && !this.equals(existingVANET);
    if (isNewVANET)
    {
      aTime.setVANET(this);
    }
    else
    {
      times.add(aTime);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTime(Time aTime)
  {
    boolean wasRemoved = false;
    //Unable to remove aTime, as it must always have a vANET
    if (!this.equals(aTime.getVANET()))
    {
      times.remove(aTime);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTimeAt(Time aTime, int index)
  {  
    boolean wasAdded = false;
    if(addTime(aTime))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimes()) { index = numberOfTimes() - 1; }
      times.remove(aTime);
      times.add(index, aTime);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeAt(Time aTime, int index)
  {
    boolean wasAdded = false;
    if(times.contains(aTime))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimes()) { index = numberOfTimes() - 1; }
      times.remove(aTime);
      times.add(index, aTime);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeAt(aTime, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfNodes()
  {
    return 0;
  }

  public Node addNode(int aId, int aPositionX, int aPositionY, float aSpeed, float aDirection, Time aTime)
  {
    return new Node(aId, aPositionX, aPositionY, aSpeed, aDirection, this, aTime);
  }

  public boolean addNode(Node aNode)
  {
    boolean wasAdded = false;
    if (nodes.contains(aNode)) { return false; }
    VANET existingVANET = aNode.getVANET();
    boolean isNewVANET = existingVANET != null && !this.equals(existingVANET);
    if (isNewVANET)
    {
      aNode.setVANET(this);
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
    //Unable to remove aNode, as it must always have a vANET
    if (!this.equals(aNode.getVANET()))
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

  public Cluster addCluster(int aVoting_ID)
  {
    return new Cluster(aVoting_ID, this);
  }

  public boolean addCluster(Cluster aCluster)
  {
    boolean wasAdded = false;
    if (clusters.contains(aCluster)) { return false; }
    VANET existingVANET = aCluster.getVANET();
    boolean isNewVANET = existingVANET != null && !this.equals(existingVANET);
    if (isNewVANET)
    {
      aCluster.setVANET(this);
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
    //Unable to remove aCluster, as it must always have a vANET
    if (!this.equals(aCluster.getVANET()))
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

  public static int minimumNumberOfTransmissions()
  {
    return 0;
  }

  public Transmission addTransmission(int aHopCount, Node aSource, Node aDest)
  {
    return new Transmission(aHopCount, this, aSource, aDest);
  }

  public boolean addTransmission(Transmission aTransmission)
  {
    boolean wasAdded = false;
    if (transmissions.contains(aTransmission)) { return false; }
    VANET existingVANET = aTransmission.getVANET();
    boolean isNewVANET = existingVANET != null && !this.equals(existingVANET);
    if (isNewVANET)
    {
      aTransmission.setVANET(this);
    }
    else
    {
      transmissions.add(aTransmission);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTransmission(Transmission aTransmission)
  {
    boolean wasRemoved = false;
    //Unable to remove aTransmission, as it must always have a vANET
    if (!this.equals(aTransmission.getVANET()))
    {
      transmissions.remove(aTransmission);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTransmissionAt(Transmission aTransmission, int index)
  {  
    boolean wasAdded = false;
    if(addTransmission(aTransmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTransmissions()) { index = numberOfTransmissions() - 1; }
      transmissions.remove(aTransmission);
      transmissions.add(index, aTransmission);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTransmissionAt(Transmission aTransmission, int index)
  {
    boolean wasAdded = false;
    if(transmissions.contains(aTransmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTransmissions()) { index = numberOfTransmissions() - 1; }
      transmissions.remove(aTransmission);
      transmissions.add(index, aTransmission);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTransmissionAt(aTransmission, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (times.size() > 0)
    {
      Time aTime = times.get(times.size() - 1);
      aTime.delete();
      times.remove(aTime);
    }
    
    while (nodes.size() > 0)
    {
      Node aNode = nodes.get(nodes.size() - 1);
      aNode.delete();
      nodes.remove(aNode);
    }
    
    while (clusters.size() > 0)
    {
      Cluster aCluster = clusters.get(clusters.size() - 1);
      aCluster.delete();
      clusters.remove(aCluster);
    }
    
    while (transmissions.size() > 0)
    {
      Transmission aTransmission = transmissions.get(transmissions.size() - 1);
      aTransmission.delete();
      transmissions.remove(aTransmission);
    }
    
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "area" + ":" + getArea()+ "," +
            "range" + ":" + getRange()+ "," +
            "path_length" + ":" + getPath_length()+ "]"
     + outputString;
  }
}