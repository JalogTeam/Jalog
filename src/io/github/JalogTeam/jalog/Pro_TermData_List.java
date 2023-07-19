// Pro_TermData_List.java

package io.github.JalogTeam.jalog;

import java.util.Hashtable;

public class Pro_TermData_List extends Pro_TermData
{
  public Pro_Term t1;
  public Pro_Term t2;
  
  public static final Pro_TermData_List EMPTY;
  static { EMPTY = new Pro_TermData_List(null,null);}

  public Pro_TermData_List(Pro_Term head, Pro_Term tail)
  {
    typename = Typenames.LIST;
    t1 = head;
    t2 = tail;
  }

  public static Pro_TermData_List make(Pro_Term[] iniVal, Pro_Term tail)
  {
    int len = iniVal.length;
    Pro_Term rest;
    
    rest = tail;
    while(len > 0)
    {
      len = len -1;
      rest = new Pro_Term(new Pro_TermData_List(iniVal[len], rest));
    }
    return (Pro_TermData_List)(rest.getData());
  }
  
  public String toString()
  {
    if(this == EMPTY)
    {
      return "[]";
    }
    else
    {
      if(Pro_Term.debug > 0)
      {
        return "[" + t1 + "|" + t2 + "]";
      }
      else
      {
        Pro_Term next = t2.getRealNode();
        String results = t1.toString();
         
        while((next != null) && (next.getData() instanceof Pro_TermData_List))
        {
          Pro_TermData_List next_data = (Pro_TermData_List)(next.getData());
          if(next_data != EMPTY)
          {
            if(next_data.t1 != null) {
              results += ("," + next_data.t1.toString());
            } else {
              results += ", #null#";
            }
            if(next_data.t2 != null) {            
              next = next_data.t2.getRealNode();
            } else {
              next = null;
              results += "| #null#";
            }
          }
          else
          {
            next = null;
          }
          
        }
        
        if(next != null)
        {
          results += ("|" + next.toString());
        }
        
        return "[" + results + "]";
      }
    }
  }


  public String image()
  {
    if(this == EMPTY)
    {
      return "[]";
    }
    else
    {
        Pro_Term next = t2.getRealNode();
        
        String results = t1.image();
        while((next != null) && (next.getData() instanceof Pro_TermData_List))
        {
          Pro_TermData_List next_data = (Pro_TermData_List)(next.getData());
          if(next_data != EMPTY)
          {
            results += ("," + next_data.t1.image());
            next = next_data.t2.getRealNode();
          }
          else
          {
            next = null;
          }
          
        }
        
        if(next != null)
        {
          results += ("|" + next.image());
        }
        
        return "[" + results + "]";
      
    }
  }

  public Pro_TermData copy(Hashtable variable_map)
  {
    Pro_Term new_t1 = null;
    Pro_Term new_t2 = null;

    if (t1 != null) {
      new_t1 = t1.copy(variable_map);
    }

    if (t2 != null) {
      new_t2 = t2.copy(variable_map);
    }

    if (new_t1 == t1 && new_t2 == t2) {
//System.out.println("case L1 ****");
      return this;
    } else {
//System.out.println("case L2 ****");
      return new Pro_TermData_List(new_t1, new_t2);
    }
  }

  public String[] toStringList()
  {
    int n = 0;
    String[] result;
    
    if(this == EMPTY)
    {
      result = new String[0];
    }
    else
    {
      Pro_Term next = t2.getRealNode();
      n = 1;
       
      while((next != null) && (next.getData() instanceof Pro_TermData_List))
      {
        Pro_TermData_List next_data = (Pro_TermData_List)(next.getData());
        if(next_data != EMPTY)
        {
          n = n + 1;
          next = next_data.t2.getRealNode();
        }
        else
        {
          next = null;
        }
        
      }
      
      if(next != null)
      {
        n = n + 1;
      }
      
      result = new String[n];
      
      next = t2.getRealNode();
      result[0] = t1.image();
      n = 1;
      while((next != null) && (next.getData() instanceof Pro_TermData_List))
      {
        Pro_TermData_List next_data = (Pro_TermData_List)(next.getData());
        if(next_data != EMPTY)
        {
          result[n] = next_data.t1.image();
          n = n + 1;
          next = next_data.t2.getRealNode();
        }
        else
        {
          next = null;
        }
        
      }
      
      if(next != null)
      {
        result[n] = next.image();
      }
      
    }
    return result;
  }
  
} // end class Pro_TermData_List

