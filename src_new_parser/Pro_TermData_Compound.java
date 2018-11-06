// Pro_TermData_Compound.java

// ToDo: toString needs a proper loop detection algorithm!

import java.util.Hashtable;

public class Pro_TermData_Compound extends Pro_TermData
{
  public String name;
  public byte arity;
  public Pro_Term[] subterm;
  
  Pro_TermData_Compound(String iniName, Pro_Term[] iniSubterm)
  {
    name = iniName;
    arity = (byte)(iniSubterm.length);
    
    subterm = iniSubterm;
  }

// to detect loops
static int depth = 0;
  
  public String toString()
  {
depth ++;
    if(arity == 0)
    {
depth --;
      return name+"()";
    }
    else
    {
      String params = "";
      
      for(int i = 0; i < arity; i ++)
      {
        Pro_Term tt = subterm[i];
        if (i > 0)
        {
          params += ",";
        }
        if (tt == null) 
        {
          params += "null";
        }
        else
        {
if(depth > 10) {
          params += "...";
}else {
          params += subterm[i].toString();
}
        }
      }
depth --;
      return name + "(" + params + ")";
    }
  }
  
  public String image()
  {
    if(arity == 0)
    {
      return name;
    }
    else
    {
      String params = "";
      
      for(int i = 0; i < arity; i ++)
      {
        Pro_Term tt = subterm[i];
        if (i > 0)
        {
          params += ",";
        }
        if (tt == null) 
        {
          params += "null";
        }
        else
        {
          params += subterm[i].image();
        }
      }
      return name + "(" + params + ")";
    }
  }

  public Pro_TermData copy(Hashtable variable_map)
  {
    if(arity == 0)
    {
//System.out.println("case C1 ****");
      return this;
    }
    else
    {
      Pro_Term[] new_subterm = new Pro_Term[arity];
      boolean differs = false;
      
      for(int i = 0; i < arity; i ++)
      {
        if(subterm[i] != null) {
          new_subterm[i] = subterm[i].copy(variable_map);
          if(new_subterm[i] != subterm[i]) {
            differs = true;
          }
        } else {
          new_subterm[i] = null;
        } 
      }
      if(differs) {
//System.out.println("case C2 ****");
        return (Pro_TermData) new Pro_TermData_Compound(name, new_subterm);
      } else {
//System.out.println("case C3 ****");
        return this;
      }
    }
  }
  
} // end class Pro_TermData_Compound

