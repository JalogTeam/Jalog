// Pro_TermData_Compound.java

package io.github.JalogTeam.jalog;

// ToDo: toString needs a proper loop detection algorithm!

import java.util.Hashtable;

public class Pro_TermData_Compound extends Pro_TermData
{
  public String name;
  public byte arity;
  public Pro_Term[] subterm;
  
  static final int MAX_DEPTH = 1000;
  
  private final Pro_Term[] no_subterms = {};
  
  public Pro_TermData_Compound(String iniName)
  {
    name = iniName;
    arity = 0;
    typename = Typenames.SYMBOL;
    subterm = no_subterms;
  }

  public Pro_TermData_Compound(String iniName, Pro_Term[] iniSubterm)
  {
    Pro_TermData data;
    Pro_Term subterm_i;
    
    name = iniName;
    
    if (iniSubterm != null) {
      arity = (byte)(iniSubterm.length);
      
      if (Typenames.identifyOperator(iniName, arity) != ' ') {
        isExpression = true;
      }
      for (int i = 0; (i < arity) && !isExpression; i++) {
        subterm_i = iniSubterm[i];
        if (subterm_i != null) {        
          data = subterm_i.getData();
          if (data != null) {
            isExpression = data.isExpression;
          }
        }
      }
      
      typename = (arity == 0 ? Typenames.SYMBOL : Typenames.COMPOUND);
      subterm = iniSubterm;
    } else {
      arity = 0;
      typename = Typenames.SYMBOL;
      subterm = no_subterms;
    }
  }

  public Pro_TermData_Compound(String iniName, Pro_Term sub)
  {
    Pro_TermData data;
    Pro_Term[] iniSubterm = {sub};
    
    name = iniName;
    arity = (byte)1;
    
    if (Typenames.identifyOperator(iniName, arity) != ' ') {
      isExpression = true;
    }
    if (sub != null) {        
      data = sub.getData();
      if (data != null) {
        isExpression = data.isExpression;
      }
    }
    
    typename = Typenames.COMPOUND;
    
    subterm = iniSubterm;
  }

  public Pro_TermData_Compound(String iniName, Pro_Term left, Pro_Term right)
  {
    Pro_TermData data;
    Pro_Term[] iniSubterm = {left, right};
    
    name = iniName;
    arity = (byte)2;

    if (Typenames.identifyOperator(iniName, arity) != ' ') {
      isExpression = true;
    }
    if (left != null) {        
      data = left.getData();
      if (data != null) {
        isExpression = data.isExpression;
      }
    }
    if (right != null) {        
      data = right.getData();
      if (data != null) {
        isExpression = data.isExpression;
      }
    }

    typename = Typenames.COMPOUND;
    
    subterm = iniSubterm;
  }

// to detect loops
static int depth = 0;
  
  public String getIndicator()
  {
    return name + "/" + arity;
  }

  public String toString()
  {
depth ++;
    if(arity == 0)
    {
depth --;
//      return name+"()";
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
if(depth > MAX_DEPTH) {
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

