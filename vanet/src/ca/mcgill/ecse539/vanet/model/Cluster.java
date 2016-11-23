/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse539.vanet.model;
import java.util.*;

// line 33 "../../../../../model.ump"
public class Cluster
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cluster Attributes
  private int voting_ID;

  //Cluster Associations
  private VANET vANET;
  private Node CH;
  private List<Node> clustermembers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cluster(int aVoting_ID, VANET aVANET, Node aCH)
  {
    voting_ID = aVoting_ID;
    boolean didAddVANET = setVANET(aVANET);
    if (!didAddVANET)
    {
      throw new RuntimeException("Unable to create cluster due to vANET");
    }
    boolean didAddCH = setCH(aCH);
    if (!didAddCH)
    {
      throw new RuntimeException("Unable to create is_CH due to CH");
    }
    clustermembers = new ArrayList<Node>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setVoting_ID(int aVoting_ID)
  {
    boolean wasSet = false;
    voting_ID = aVoting_ID;
    wasSet = true;
    return wasSet;
  }

  public int getVoting_ID()
  {
    return voting_ID;
  }

  public VANET getVANET()
  {
    return vANET;
  }

  public Node getCH()
  {
    return CH;
  }

  public Node getClustermember(int index)
  {
    Node aClustermember = clustermembers.get(index);
    return aClustermember;
  }

  public List<Node> getClustermembers()
  {
    List<Node> newClustermembers = Collections.unmodifiableList(clustermembers);
    return newClustermembers;
  }

  public int numberOfClustermembers()
  {
    int number = clustermembers.size();
    return number;
  }

  public boolean hasClustermembers()
  {
    boolean has = clustermembers.size() > 0;
    return has;
  }

  public int indexOfClustermember(Node aClustermember)
  {
    int index = clustermembers.indexOf(aClustermember);
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
      existingVANET.removeCluster(this);
    }
    vANET.addCluster(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setCH(Node aNewCH)
  {
    boolean wasSet = false;
    if (aNewCH == null)
    {
      //Unable to setCH to null, as is_CH must always be associated to a CH
      return wasSet;
    }
    
    Cluster existingIs_CH = aNewCH.getIs_CH();
    if (existingIs_CH != null && !equals(existingIs_CH))
    {
      //Unable to setCH, the current CH already has a is_CH, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Node anOldCH = CH;
    CH = aNewCH;
    CH.setIs_CH(this);

    if (anOldCH != null)
    {
      anOldCH.setIs_CH(null);
    }
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfClustermembers()
  {
    return 0;
  }

  public boolean addClustermember(Node aClustermember)
  {
    boolean wasAdded = false;
    if (clustermembers.contains(aClustermember)) { return false; }
    Cluster existingCuster = aClustermember.getCuster();
    if (existingCuster == null)
    {
      aClustermember.setCuster(this);
    }
    else if (!this.equals(existingCuster))
    {
      existingCuster.removeClustermember(aClustermember);
      addClustermember(aClustermember);
    }
    else
    {
      clustermembers.add(aClustermember);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeClustermember(Node aClustermember)
  {
    boolean wasRemoved = false;
    if (clustermembers.contains(aClustermember))
    {
      clustermembers.remove(aClustermember);
      aClustermember.setCuster(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addClustermemberAt(Node aClustermember, int index)
  {  
    boolean wasAdded = false;
    if(addClustermember(aClustermember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClustermembers()) { index = numberOfClustermembers() - 1; }
      clustermembers.remove(aClustermember);
      clustermembers.add(index, aClustermember);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveClustermemberAt(Node aClustermember, int index)
  {
    boolean wasAdded = false;
    if(clustermembers.contains(aClustermember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClustermembers()) { index = numberOfClustermembers() - 1; }
      clustermembers.remove(aClustermember);
      clustermembers.add(index, aClustermember);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addClustermemberAt(aClustermember, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    VANET placeholderVANET = vANET;
    this.vANET = null;
    placeholderVANET.removeCluster(this);
    Node existingCH = CH;
    CH = null;
    if (existingCH != null)
    {
      existingCH.setIs_CH(null);
    }
    while( !clustermembers.isEmpty() )
    {
      clustermembers.get(0).setCuster(null);
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "voting_ID" + ":" + getVoting_ID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "vANET = "+(getVANET()!=null?Integer.toHexString(System.identityHashCode(getVANET())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "CH = "+(getCH()!=null?Integer.toHexString(System.identityHashCode(getCH())):"null")
     + outputString;
  }
}