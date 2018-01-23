// Pro_Term.java
import java.io.*;
import java.util.Hashtable;

public class Pro_Term 
{
  static int debug = 1;
  static int nextId = 1;
  
  private Pro_TermData data;
//  public Pro_TermData data;
  public int Id;
  
  
  Pro_Term()
  {
    Id = nextId;
    nextId++;
  }

  public boolean unify(Pro_Term pn2, Pro_Trail pBack, Pro_TrailMark Mark)
  {
    return unify2(pn2, pBack, Mark);
  }
  
  public boolean match(Pro_Term pn2)
  {
    return unify2(pn2, null, null);
  }
  
  boolean unify2(Pro_Term pn2, Pro_Trail pBack, Pro_TrailMark Mark)
  {
    Pro_Term pn1;
    Pro_Term tmp1 = new Pro_Term();
    Pro_Term tmp2 = new Pro_Term();
    Pro_TrailMark garbage = new Pro_TrailMark();
    Pro_TrailMark first_backtop = new Pro_TrailMark();
    boolean success = false;
    
    if(pBack != null)
    {
      pBack.mark(Mark);// = pBack.top;
      pBack.mark(first_backtop);
    }

    pn1 = getRealNode();
    pn2 = pn2.getRealNode();
    
    if (pn1 == pn2)
    {
      success = true;
    }
    else if (pn2.data == null)
    {
      if(pBack != null)
      {
        pn2.compval(pn1);  // Unifying!
        pBack.append(pn2);
      }
      success = true;
    }
    else 
    {
      if (pn1.data instanceof Pro_TermData_Compound)
      {
        tmp1.compval(pn1);
        if (!(tmp1.data instanceof Pro_TermData_Unified))
        {
          pn1 = tmp1;
        }
      }
      if (pn1.data == null)
      {
        if(pBack != null)
        {
          pn1.compval(pn2);  // Unifying!
          pBack.append(pn1);
        }
        success = true;
      }
      else if (pn1.data instanceof Pro_TermData_Unified)
      {
        success = false;
      }
      else if (pn1.data instanceof Pro_TermData_Integer)
      {
        tmp2.compval(pn2);
        success = ((tmp2.data instanceof Pro_TermData_Integer)&&
                        (((Pro_TermData_Integer)pn1.data).value ==
                         ((Pro_TermData_Integer)tmp2.data).value));
      }
      else if (pn1.data instanceof Pro_TermData_Char)
      {
        tmp2.compval(pn2);
        success = ((tmp2.data instanceof Pro_TermData_Char)&&
                        (((Pro_TermData_Char)pn1.data).value ==
                         ((Pro_TermData_Char)tmp2.data).value));
      }
/*      else if (pn1.data instanceof Pro_TermData_Real)
      {
        tmp2.compval(pn2);
        success = ((tmp2.data instanceof Pro_TermData_Real)&&
                        (((Pro_TermData_Real)pn1.data).value ==
                         ((Pro_TermData_Real)tmp2.data).value));
      }
*/
      else if (pn1.data instanceof Pro_TermData_Compound)
      {
        if(pn2.data instanceof Pro_TermData_Compound)
        {
          Pro_TermData_Compound pt1 = ((Pro_TermData_Compound)pn1.data);
          Pro_TermData_Compound pt2 = ((Pro_TermData_Compound)pn2.data);
// System.out.println("\n1 Comparing: " + pt1 + " and " + pt2);
          if (pt1 == pt2)
          {
            success = true;
// System.out.println("\n1.1  Unexpected success");
          } else if((pt1.name.equals(pt2.name)) &&
                 (pt1.arity == pt2.arity))
          {
            int i;
// System.out.println("\n1.2  Names and arities Ok");
            
            success = true;
            i = 0;
            while(success && (i < pt1.arity))
            {
// System.out.println("\n2 Comparing: " + pt1.subterm[i] + " and " + pt2.subterm[i]);
              success = pt1.subterm[i].unify2(
                      pt2.subterm[i],pBack,garbage);
// System.out.println("\n3   Result: " + success);
              i++;
            }
          } 
          else 
          {
// System.out.println("\n4  Names and arities differ");
// System.out.println("  pt1:" + pt1.name + "/" + pt1.arity);
// System.out.println("  pt2:" + pt2.name + "/" + pt2.arity);
            success = false;
          }
        } 
        else 
        {
// System.out.println("\n5  Other not compound");
          success = false;
        }
      }
      else if (pn1.data instanceof Pro_TermData_String)
      {
        success = ((tmp2.data instanceof Pro_TermData_String)&&
                        (((Pro_TermData_String)pn1.data).value.equals(
                         ((Pro_TermData_String)tmp2.data).value)));
      }
      else if (pn1.data instanceof Pro_TermData_List)
      {
        if(pn2.data instanceof Pro_TermData_List)
        {
          boolean go;
          
          Pro_TermData_List pl1 = (Pro_TermData_List)pn1.data;
          Pro_TermData_List pl2 = (Pro_TermData_List)pn2.data;
          success = true;
          go = true;
          while(go)
          {
            if((pl1 == pl2) /*|| ((pl1.t1 == null) && (pl2.t1 == null))*/)
            {
              /* success, references to the same list */
              go = false;
            }
            else if((pl1 == Pro_TermData_List.EMPTY)||
                (pl2 == Pro_TermData_List.EMPTY))
            {  // one is empty
              success = false;
              go = false;
            }
            else
            {
              success = pl1.t1.unify2(
                      pl2.t1,pBack,garbage);
              if(success)
              {
                pn1 = pl1.t2.getRealNode();
                pn2 = pl2.t2.getRealNode();
                if((pn1.data instanceof Pro_TermData_List)&&
                    (pn2.data instanceof Pro_TermData_List))
                {
                  pl1 = (Pro_TermData_List)pn1.data;
                  pl2 = (Pro_TermData_List)pn2.data;
                }
                else
                {
                  success = pl1.t2.unify2(
                      pl2.t2,pBack,garbage);
                  go = false;
                }
              } 
              else 
              {
                go = false;
              }
            }
          }
        } 
        else 
        {
          success = false;
        }
      }
      if(!success)
      {
        if(pBack != null)
        {
          pBack.backtrack(first_backtop);
        }
      }
    }
    return success;
  } // end unify
  

  public Pro_Term getRealNode()
  {
    Pro_Term current = this;
    
    while( (current.data != null) && 
        (current.data instanceof Pro_TermData_Unified)) 
    {
      current = ((Pro_TermData_Unified)(current.data)).pValue;
    }
    return current;
  }

  public Pro_TermData getData()
  {
    return getRealNode().data;
  }

  public void setData(Pro_TermData data)
  {
    this.data = data;
  }  
  
  static public Pro_Term m_integer(long iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_Integer(iniVal);
    return a;
  }
  

  static public Pro_Term m_real(double iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_Real(iniVal);
    return a;
  }
  

  static public Pro_Term m_char(char iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_Char(iniVal);
    return a;
  }
  

  static public Pro_Term m_string(String iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_String(iniVal);
    return a;
  }
  

  static public Pro_Term m_open()
  {
    Pro_Term a = new Pro_Term();
    a.data = null;
    return a;
  }
  

  static public Pro_Term m_unified(Pro_Term iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_Unified(iniVal);
    return a;
  }
  

  static public Pro_Term m_list(Pro_Term[] iniVal, Pro_Term tail)
  {
    if (iniVal.length > 0)
    {
      Pro_Term a = new Pro_Term();
      a.data = Pro_TermData_List.make(iniVal,tail);
      return a;
    }
    else
    {
      return tail;
    }
  }
  

  public static final Pro_Term EMPTY_LIST;
  static { EMPTY_LIST = new Pro_Term();
    EMPTY_LIST.data = Pro_TermData_List.EMPTY;}

  static public Pro_Term m_list(Pro_Term[] iniVal)
  {
    Pro_Term a = new Pro_Term();
    a.data = Pro_TermData_List.make(iniVal,Pro_Term.EMPTY_LIST);
    return a;
  }
  

  static public Pro_Term m_compound(String iniName, Pro_Term[] iniSubterm)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_Compound(iniName, iniSubterm);
    return a;
  }
  
  void compval(Pro_Term term)
  {
    double R1 = 0.0, R2 = 0.0, Rv = 0.0;
    long I1 = 0, I2 = 0, Iv = 0;
    char T1, T2, Tv;
    char Op;
    String OpName;
    
    term = term.getRealNode();
//          System.out.println("term='" + term.toString()+"'");        
    data = term.data; // does not compute yet
    if(term.data == null) {
      data = new Pro_TermData_Unified(term);
    } else {
      if (data instanceof Pro_TermData_Compound) {
        Pro_TermData_Compound compData = (Pro_TermData_Compound)data;

/* What about overflow? */

        if (compData.arity == 1) {
//          System.out.println("compData='" + compData.toString()+"'");

          OpName = compData.name;
          Op = ' ';
          if(OpName.equals("-")) { Op = '-'; }
          else if(OpName.equals("+")) { Op = '+'; }
          
          if(Op != ' ') {

            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            T1 = ' ';
            Tv = ' ';
            if(tmp1.data instanceof Pro_TermData_Integer){
              T1 = 'i';
              I1 = ((Pro_TermData_Integer)tmp1.data).value;
              Tv = 'i';
            } else if(tmp1.data instanceof Pro_TermData_Real) {
              T1 = 'r';
              R1 = ((Pro_TermData_Real)tmp1.data).value;
              Tv = 'r';
            }
            if(Tv == 'r') { // real arithmetics
              if(T1 == 'i') {
                R1 = I1;
              }
              switch(Op) {
                case '+': Rv = R1; break;
                case '-': Rv = -R1; break;
                default: Tv = ' '; // No result for others
              }
            } else if(Tv == 'i'){ // integer
              switch(Op) {
                case '+': Iv = I1; break;
                case '-': Iv = -I1; break;
                default: Tv = ' '; // No result for others
              }
            }
            if(Tv == 'i') {data = new Pro_TermData_Integer(Iv);}
            else if(Tv == 'r') {data = new Pro_TermData_Real(Rv);}
          }





/*
          if(compData.name.equals("-")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            if(tmp1.data instanceof Pro_TermData_Integer) {
              data = new Pro_TermData_Integer(
                  -((Pro_TermData_Integer)tmp1.data).value);
            }
          }
*/
//          System.out.println("data='" + data.toString()+"'");        
        } else if (compData.arity == 2) {
//          System.out.println("compData='" + compData.toString()+"'");

          OpName = compData.name;
          Op = ' ';
          if(OpName.equals("+")) { Op = '+'; }
          else if(OpName.equals("-")) { Op = '-'; }
          else if(OpName.equals("*")) { Op = '*'; }
          else if(OpName.equals("/")) { Op = '/'; }
          else if(OpName.equals("mod_")) { Op = 'm'; }
          else if(OpName.equals("div_")) { Op = 'd'; }
          
          if(Op != ' ') {

            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            T1 = ' ';
            T2 = ' ';
            Tv = ' ';
            if(tmp1.data instanceof Pro_TermData_Integer){
              T1 = 'i';
              I1 = ((Pro_TermData_Integer)tmp1.data).value;
              Tv = 'i';
            } else if(tmp1.data instanceof Pro_TermData_Real) {
              T1 = 'r';
              R1 = ((Pro_TermData_Real)tmp1.data).value;
              Tv = 'r';
            }
            if(Tv != ' ') {
              if(tmp2.data instanceof Pro_TermData_Integer){
                T2 = 'i';
                I2 = ((Pro_TermData_Integer)tmp2.data).value;
              } else if(tmp2.data instanceof Pro_TermData_Real) {
                T2 = 'r';
                R2 = ((Pro_TermData_Real)tmp2.data).value;
                Tv = 'r';
              } else {
                Tv = ' ';
              }
              if(Tv != ' ') {
                if(Tv == 'r') { // real arithmetics
                  if(T1 == 'i') {
                    R1 = I1;
                  }
                  if(T2 == 'i') {
                    R2 = I2;
                  }
                  switch(Op) {
                    case '+': Rv = R1 + R2; break;
                    case '-': Rv = R1 - R2; break;
                    case '*': Rv = R1 * R2; break;
                    case '/': Rv = R1 / R2; break;
                    default: Tv = ' '; // No result for others
                  }
                } else { // integer
                  switch(Op) {
                    case '+': Iv = I1 + I2; break;
                    case '-': Iv = I1 - I2; break;
                    case '*': Iv = I1 * I2; break;
                    case '/': Rv = I1 / (double)I2; Tv = 'r'; break;
                    case 'm': Iv = I1 % I2; break;
                    case 'd': Iv = I1 / I2; break;
                    default: Tv = ' '; // No result for others
                  }
                }
                if(Tv == 'i') {data = new Pro_TermData_Integer(Iv);}
                else if(Tv == 'r') {data = new Pro_TermData_Real(Rv);}
              }
              
            }
          }
/* 


          if(compData.name.equals("+")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value +
                  ((Pro_TermData_Integer)tmp2.data).value);
            } else {
              if (tmp1.data instanceof Pro_TermData_Integer) {
                A = ((Pro_TermData_Integer)tmp1.data).value;
              } else {

            }
          } else if(compData.name.equals("*")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value *
                  ((Pro_TermData_Integer)tmp2.data).value);
            }
          } else if(compData.name.equals("-")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value -
                  ((Pro_TermData_Integer)tmp2.data).value);
            }
          } else if(compData.name.equals("/")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value /
                  ((Pro_TermData_Integer)tmp2.data).value);
            }
          } else if(compData.name.equals("div")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value /
                  ((Pro_TermData_Integer)tmp2.data).value);
            }
          } else if(compData.name.equals("mod")) {
            Pro_Term tmp1 = new Pro_Term(); tmp1.compval(compData.subterm[0]);
            Pro_Term tmp2 = new Pro_Term(); tmp2.compval(compData.subterm[1]);
            if((tmp1.data instanceof Pro_TermData_Integer) &&
                (tmp2.data instanceof Pro_TermData_Integer)) {
              data = new Pro_TermData_Integer(
                  ((Pro_TermData_Integer)tmp1.data).value %
                  ((Pro_TermData_Integer)tmp2.data).value);
            }
          }
*/
//          System.out.println("data='" + data.toString()+"'");

        }
        

      }


    }
  }
  
  public String toString()
  {
    if(data == null)
    {
      return "_" + Id;
    }
    else
    {
      if(debug > 0)
      {
        return "(" + Id + ":" + data.toString() + ")";
      }
      else
      {
        return data.toString();
      }
    }
  }
  
  public String image()
  {
    if(data == null)
    {
      return "_" + Id;
    }
    else
    {
      return data.image();
    }
  }


  public Pro_Term copy()
  {
    Hashtable variable_map = new Hashtable(100);
//System.out.println("New db map " + toString() + " ****");
  
    return copy(variable_map);

  }
   
  public Pro_Term copy(Hashtable variable_map)
  {
    Pro_Term source = getRealNode();
    Pro_Term new_this = (Pro_Term) variable_map.get(source);
//System.out.println("copy: " + source + "->" + new_this + " ****");
    if(new_this == null) {
      if(source.data != null) {
        Pro_TermData new_data = source.data.copy(variable_map);
        { 
          new_this = new Pro_Term();
          new_this.data = new_data;
//System.out.println("case 4 ****");
        }  
      } else {
        // New open variable
        new_this = new Pro_Term();
        new_this.data = null;
      }
      variable_map.put(source, new_this);
//System.out.println("case 5 new mapping ****");
    }
//System.out.println("case 5 " + source.Id + "->" + new_this.Id + " ****");
    return new_this;
  }

} // end class Pro_Term

