/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse539.vanet.model;
import java.util.*;

// line 16 "../../../../../model.ump"
public class Node
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Node Attributes
  private int id;
  private int positionX;
  private int positionY;
  private float speed;
  private float direction;
  private int qos;
  private int remaining_d;

  //Node Associations
  private VANET vANET;
  private List<Node> neighbors;
  private Cluster custer;
  private Time time;
  private Node node;
  private List<Transmission> ts;
  private List<Transmission> td;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Node(int aId, int aPositionX, int aPositionY, float aSpeed, float aDirection, VANET aVANET, Time aTime)
  {
    id = aId;
    positionX = aPositionX;
    positionY = aPositionY;
    speed = aSpeed;
    direction = aDirection;
    qos = 0;
    remaining_d = 0;
    boolean didAddVANET = setVANET(aVANET);
    if (!didAddVANET)
    {
      throw new RuntimeException("Unable to create node due to vANET");
    }
    neighbors = new ArrayList<Node>();
    boolean didAddTime = setTime(aTime);
    if (!didAddTime)
    {
      throw new RuntimeException("Unable to create node due to time");
    }
    ts = new ArrayList<Transmission>();
    td = new ArrayList<Transmission>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionX(int aPositionX)
  {
    boolean wasSet = false;
    positionX = aPositionX;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionY(int aPositionY)
  {
    boolean wasSet = false;
    positionY = aPositionY;
    wasSet = true;
    return wasSet;
  }

  public boolean setSpeed(float aSpeed)
  {
    boolean wasSet = false;
    speed = aSpeed;
    wasSet = true;
    return wasSet;
  }

  public boolean setDirection(float aDirection)
  {
    boolean wasSet = false;
    direction = aDirection;
    wasSet = true;
    return wasSet;
  }

  public boolean setQos(int aQos)
  {
    boolean wasSet = false;
    qos = aQos;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemaining_d(int aRemaining_d)
  {
    boolean wasSet = false;
    remaining_d = aRemaining_d;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getPositionX()
  {
    return positionX;
  }

  public int getPositionY()
  {
    return positionY;
  }

  public float getSpeed()
  {
    return speed;
  }

  public float getDirection()
  {
    return direction;
  }

  public int getQos()
  {
    return qos;
  }

  public int getRemaining_d()
  {
    return remaining_d;
  }

  public VANET getVANET()
  {
    return vANET;
  }

  public Node getNeighbor(int index)
  {
    Node aNeighbor = neighbors.get(index);
    return aNeighbor;
  }

  public List<Node> getNeighbors()
  {
    List<Node> newNeighbors = Collections.unmodifiableList(neighbors);
    return newNeighbors;
  }

  public int numberOfNeighbors()
  {
    int number = neighbors.size();
    return number;
  }

  public boolean hasNeighbors()
  {
    boolean has = neighbors.size() > 0;
    return has;
  }

  public int indexOfNeighbor(Node aNeighbor)
  {
    int index = neighbors.indexOf(aNeighbor);
    return index;
  }

  public Cluster getCuster()
  {
    return custer;
  }

  public boolean hasCuster()
  {
    boolean has = custer != null;
    return has;
  }

  public Time getTime()
  {
    return time;
  }

  public Node getNode()
  {
    return node;
  }

  public boolean hasNode()
  {
    boolean has = node != null;
    return has;
  }

  public Transmission getT(int index)
  {
    Transmission aT = ts.get(index);
    return aT;
  }

  public List<Transmission> getTs()
  {
    List<Transmission> newTs = Collections.unmodifiableList(ts);
    return newTs;
  }

  public int numberOfTs()
  {
    int number = ts.size();
    return number;
  }

  public boolean hasTs()
  {
    boolean has = ts.size() > 0;
    return has;
  }

  public int indexOfT(Transmission aT)
  {
    int index = ts.indexOf(aT);
    return index;
  }

  public Transmission getTd(int index)
  {
    Transmission aTd = td.get(index);
    return aTd;
  }

  public List<Transmission> getTd()
  {
    List<Transmission> newTd = Collections.unmodifiableList(td);
    return newTd;
  }

  public int numberOfTd()
  {
    int number = td.size();
    return number;
  }

  public boolean hasTd()
  {
    boolean has = td.size() > 0;
    return has;
  }

  public int indexOfTd(Transmission aTd)
  {
    int index = td.indexOf(aTd);
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
      existingVANET.removeNode(this);
    }
    vANET.addNode(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfNeighbors()
  {
    return 0;
  }

  public boolean addNeighbor(Node aNeighbor)
  {
    boolean wasAdded = false;
    if (neighbors.contains(aNeighbor)) { return false; }
    Node existingNode = aNeighbor.getNode();
    if (existingNode == null)
    {
      aNeighbor.setNode(this);
    }
    else if (!this.equals(existingNode))
    {
      existingNode.removeNeighbor(aNeighbor);
      addNeighbor(aNeighbor);
    }
    else
    {
      neighbors.add(aNeighbor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeNeighbor(Node aNeighbor)
  {
    boolean wasRemoved = false;
    if (neighbors.contains(aNeighbor))
    {
      neighbors.remove(aNeighbor);
      aNeighbor.setNode(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addNeighborAt(Node aNeighbor, int index)
  {  
    boolean wasAdded = false;
    if(addNeighbor(aNeighbor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNeighbors()) { index = numberOfNeighbors() - 1; }
      neighbors.remove(aNeighbor);
      neighbors.add(index, aNeighbor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveNeighborAt(Node aNeighbor, int index)
  {
    boolean wasAdded = false;
    if(neighbors.contains(aNeighbor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNeighbors()) { index = numberOfNeighbors() - 1; }
      neighbors.remove(aNeighbor);
      neighbors.add(index, aNeighbor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addNeighborAt(aNeighbor, index);
    }
    return wasAdded;
  }

  public boolean setCuster(Cluster aCuster)
  {
    boolean wasSet = false;
    Cluster existingCuster = custer;
    custer = aCuster;
    if (existingCuster != null && !existingCuster.equals(aCuster))
    {
      existingCuster.removeClustermember(this);
    }
    if (aCuster != null)
    {
      aCuster.addClustermember(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    if (aTime == null)
    {
      return wasSet;
    }

    Time existingTime = time;
    time = aTime;
    if (existingTime != null && !existingTime.equals(aTime))
    {
      existingTime.removeNode(this);
    }
    time.addNode(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setNode(Node aNode)
  {
    boolean wasSet = false;
    Node existingNode = node;
    node = aNode;
    if (existingNode != null && !existingNode.equals(aNode))
    {
      existingNode.removeNeighbor(this);
    }
    if (aNode != null)
    {
      aNode.addNeighbor(this);
    }
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfTs()
  {
    return 0;
  }

  public Transmission addT(int aHopCount, VANET aVANET, Node aDest)
  {
    return new Transmission(aHopCount, aVANET, this, aDest);
  }

  public boolean addT(Transmission aT)
  {
    boolean wasAdded = false;
    if (ts.contains(aT)) { return false; }
    Node existingSource = aT.getSource();
    boolean isNewSource = existingSource != null && !this.equals(existingSource);
    if (isNewSource)
    {
      aT.setSource(this);
    }
    else
    {
      ts.add(aT);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeT(Transmission aT)
  {
    boolean wasRemoved = false;
    //Unable to remove aT, as it must always have a source
    if (!this.equals(aT.getSource()))
    {
      ts.remove(aT);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTAt(Transmission aT, int index)
  {  
    boolean wasAdded = false;
    if(addT(aT))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTs()) { index = numberOfTs() - 1; }
      ts.remove(aT);
      ts.add(index, aT);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTAt(Transmission aT, int index)
  {
    boolean wasAdded = false;
    if(ts.contains(aT))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTs()) { index = numberOfTs() - 1; }
      ts.remove(aT);
      ts.add(index, aT);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTAt(aT, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfTd()
  {
    return 0;
  }

  public Transmission addTd(int aHopCount, VANET aVANET, Node aSource)
  {
    return new Transmission(aHopCount, aVANET, aSource, this);
  }

  public boolean addTd(Transmission aTd)
  {
    boolean wasAdded = false;
    if (td.contains(aTd)) { return false; }
    Node existingDest = aTd.getDest();
    boolean isNewDest = existingDest != null && !this.equals(existingDest);
    if (isNewDest)
    {
      aTd.setDest(this);
    }
    else
    {
      td.add(aTd);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTd(Transmission aTd)
  {
    boolean wasRemoved = false;
    //Unable to remove aTd, as it must always have a dest
    if (!this.equals(aTd.getDest()))
    {
      td.remove(aTd);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTdAt(Transmission aTd, int index)
  {  
    boolean wasAdded = false;
    if(addTd(aTd))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTd()) { index = numberOfTd() - 1; }
      td.remove(aTd);
      td.add(index, aTd);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTdAt(Transmission aTd, int index)
  {
    boolean wasAdded = false;
    if(td.contains(aTd))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTd()) { index = numberOfTd() - 1; }
      td.remove(aTd);
      td.add(index, aTd);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTdAt(aTd, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    VANET placeholderVANET = vANET;
    this.vANET = null;
    placeholderVANET.removeNode(this);
    while( !neighbors.isEmpty() )
    {
      neighbors.get(0).setNode(null);
    }
    if (custer != null)
    {
      Cluster placeholderCuster = custer;
      this.custer = null;
      placeholderCuster.removeClustermember(this);
    }
    Time placeholderTime = time;
    this.time = null;
    placeholderTime.removeNode(this);
    if (node != null)
    {
      Node placeholderNode = node;
      this.node = null;
      placeholderNode.removeNeighbor(this);
    }
    for(int i=ts.size(); i > 0; i--)
    {
      Transmission aT = ts.get(i - 1);
      aT.delete();
    }
    for(int i=td.size(); i > 0; i--)
    {
      Transmission aTd = td.get(i - 1);
      aTd.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "positionX" + ":" + getPositionX()+ "," +
            "positionY" + ":" + getPositionY()+ "," +
            "speed" + ":" + getSpeed()+ "," +
            "direction" + ":" + getDirection()+ "," +
            "qos" + ":" + getQos()+ "," +
            "remaining_d" + ":" + getRemaining_d()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "vANET = "+(getVANET()!=null?Integer.toHexString(System.identityHashCode(getVANET())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "custer = "+(getCuster()!=null?Integer.toHexString(System.identityHashCode(getCuster())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "time = "+(getTime()!=null?Integer.toHexString(System.identityHashCode(getTime())):"null")
     + outputString;
  }
}