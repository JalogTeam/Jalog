// Pro_TermData.java

package io.github.JalogTeam.jalog;

import java.util.Hashtable;

public abstract class Pro_TermData
{
  public String typename;
  
  public String image()
  {
    return toString();
  }

  public Pro_TermData copy(Hashtable variable_map)
  {
    return this;
  }

} // end class Pro_TermData

