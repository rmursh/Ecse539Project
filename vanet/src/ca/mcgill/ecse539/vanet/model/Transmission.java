/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse539.vanet.model;

// line 40 "../../../../../model.ump"
public class Transmission
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Transmission Attributes
  private int hopCount;

  //Transmission Associations
  private VANET vANET;
  private Node source;
  private Node dest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transmission(int aHopCount, VANET aVANET, Node aSource, Node aDest)
  {
    hopCount = aHopCount;
    boolean didAddVANET = setVANET(aVANET);
    if (!didAddVANET)
    {
      throw new RuntimeException("Unable to create transmission due to vANET");
    }
    boolean didAddSource = setSource(aSource);
    if (!didAddSource)
    {
      throw new RuntimeException("Unable to create t due to source");
    }
    boolean didAddDest = setDest(aDest);
    if (!didAddDest)
    {
      throw new RuntimeException("Unable to create td due to dest");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHopCount(int aHopCount)
  {
    boolean wasSet = false;
    hopCount = aHopCount;
    wasSet = true;
    return wasSet;
  }

  public int getHopCount()
  {
    return hopCount;
  }

  public VANET getVANET()
  {
    return vANET;
  }

  public Node getSource()
  {
    return source;
  }

  public Node getDest()
  {
    return dest;
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
      existingVANET.removeTransmission(this);
    }
    vANET.addTransmission(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setSource(Node aSource)
  {
    boolean wasSet = false;
    if (aSource == null)
    {
      return wasSet;
    }

    Node existingSource = source;
    source = aSource;
    if (existingSource != null && !existingSource.equals(aSource))
    {
      existingSource.removeT(this);
    }
    source.addT(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setDest(Node aDest)
  {
    boolean wasSet = false;
    if (aDest == null)
    {
      return wasSet;
    }

    Node existingDest = dest;
    dest = aDest;
    if (existingDest != null && !existingDest.equals(aDest))
    {
      existingDest.removeTd(this);
    }
    dest.addTd(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    VANET placeholderVANET = vANET;
    this.vANET = null;
    placeholderVANET.removeTransmission(this);
    Node placeholderSource = source;
    this.source = null;
    placeholderSource.removeT(this);
    Node placeholderDest = dest;
    this.dest = null;
    placeholderDest.removeTd(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "hopCount" + ":" + getHopCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "vANET = "+(getVANET()!=null?Integer.toHexString(System.identityHashCode(getVANET())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "source = "+(getSource()!=null?Integer.toHexString(System.identityHashCode(getSource())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dest = "+(getDest()!=null?Integer.toHexString(System.identityHashCode(getDest())):"null")
     + outputString;
  }
}