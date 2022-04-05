// Pro_Term.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.Hashtable;

public class Pro_Term 
{
  static int debug = 0;
  static long lastId = 0;
  static long lastPrintId = 0;
  
  protected Pro_TermData data;
//  public Pro_TermData data;
  public long Id, printId;
  
  
  Pro_Term()
  {
    lastId++;
    Id = lastId;
    printId = 0; // until printed
  }

  Pro_Term(Pro_TermData data)
  {
    lastId++;
    Id = lastId;
    this.data = data;
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
    double rvalue;

if(debug>0) System.out.println("* unify2: begin");
    if(pBack != null)
    {
      pBack.mark(Mark);// = pBack.top;
      pBack.mark(first_backtop);
    }

    pn1 = getRealNode();
    pn2 = pn2.getRealNode();
if(debug>0) System.out.println("  1: " + (pn1/*.data*/));
if(debug>0) System.out.println("  2: " + (pn2/*.data*/));
    
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
        if(tmp2.data instanceof Pro_TermData_Integer){
          success = (((Pro_TermData_Integer)pn1.data).value ==
                         ((Pro_TermData_Integer)tmp2.data).value);
        } else if(tmp2.data instanceof Pro_TermData_Real){
          rvalue = ((Pro_TermData_Integer)pn1.data).value;
          success = (rvalue == ((Pro_TermData_Real)tmp2.data).value);
        }
        
      }
      else if (pn1.data instanceof Pro_TermData_Char)
      {
        tmp2.compval(pn2);
        success = ((tmp2.data instanceof Pro_TermData_Char)&&
                        (((Pro_TermData_Char)pn1.data).value ==
                         ((Pro_TermData_Char)tmp2.data).value));
      }
      else if (pn1.data instanceof Pro_TermData_Real)
      {
        tmp2.compval(pn2);
        if(tmp2.data instanceof Pro_TermData_Real){
          success = (((Pro_TermData_Real)pn1.data).value ==
                         ((Pro_TermData_Real)tmp2.data).value);
        } else if (tmp2.data instanceof Pro_TermData_Integer){
          rvalue = ((Pro_TermData_Integer)tmp2.data).value;
          success = (((Pro_TermData_Real)pn1.data).value == rvalue);
        }
      }

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
// System.out.println("A");
        if (pn2.data instanceof Pro_TermData_String)
        {
// System.out.println("B");
          success = Pro_TermData_String.compare_strings(
              (Pro_TermData_String)pn1.data, 
              (Pro_TermData_String)pn2.data) == 0;
// System.out.println("C success=" + success);
        }
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
if(debug>0) System.out.println("* unify2: end " + success);
 
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
  
  public String getType()
  {
    Pro_TermData data = getRealNode().data;
    
    return (data == null ? Jalog.OPEN : data.typename);
  }
/*
  public long getIntegerValue() {
    String type = getType();
    
    if(type == Jalog.INTEGER) {
      return ((Pro_TermData_Integer)getData()).value;
    } else {
      return 0;
    }
  }
*/
  
  public void clearData()
  {
    data = null;
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
    a.data = new Pro_TermData_String_simple(iniVal);
    return a;
  }
  

  static public Pro_Term m_string_substring(Pro_TermData_String base_string,
      long req_start, long req_len)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_String_substring(base_string, req_start, req_len);
    return a;
  }
  

  static public Pro_Term m_string_concat(Pro_TermData_String left, 
      Pro_TermData_String right)
  {
    Pro_Term a = new Pro_Term();
    a.data = new Pro_TermData_String_concat(left, right);
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
//          System.out.println("data='" + data.toString()+"'");

        }
        

      }


    }
  }
  
  public static long eval_integer(Pro_Term p) {
    Pro_Term v = null;
    long result = 0;
    
    if (p.data != null) {
      if (p.data instanceof Pro_TermData_Integer) {
        v = p;
      } else {
        v = new Pro_Term();
        v.compval(p);
      }
    }
    if ((v != null) && (v.data instanceof Pro_TermData_Integer)) {
      result = ((Pro_TermData_Integer)v.data).value;
    }
    
    return result;
  }
  
  public String toString()
  {
    if(data == null)
    {
      if (printId == 0) {
        lastPrintId++;
        printId = lastPrintId;
      }
      return "_" + printId;
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
      return this.toString();
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

/*
  private boolean compare_strings(Pro_TermData_String s1, 
      Pro_TermData_String s2) 
  {
    long p = 0;
    boolean result = true;
    String string1_found;
    long start_pos1, len1;
    int i;
    long max_len;
// System.out.println("____________");    
// System.out.println("CS:  s1.structure=" + s1.structure());
// System.out.println("CS:  s2.structure=" + s2.structure());

    if (s1.len == s2.len) {
      while (result && (p < s1.len)) {
// System.out.println("\nCS: p = " + p);
// System.out.println("CS: to get_string_part, p = " + p + ", s1 = \"" + s1 + "\"");
        get_string_part(p, s1);
// System.out.println("CS: from get_string_part, string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
        string1_found = string_found;
        start_pos1 = start_pos;
        len1 = len;
// System.out.println("\nCS: to get_string_part, p = " + p + ", s2 = \"" + s2 + "\"");
        get_string_part(p, s2);
// System.out.println("CS: from get_string_part, string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
        if (len > len1) {
          len = len1;
        }
        for ( i = 0; (i < len) && result; i++) {
          result = string1_found.charAt((int)start_pos1 + i) == 
                   string_found.charAt((int)start_pos + i);
        }                
        p = p + len;
// System.out.println("CS: result = " + result + ", p = " + p);
      }          
    } else {
      result =  false;
    }
// System.out.println("CS: result = " + result);    
// System.out.println("____________");    
    return result;      
  }

  
  String string_found;
  long start_pos, len;
String indent = "";

  private void get_string_part(long p, Pro_TermData_String s)
  {
    Pro_TermData_String_substring ss;
    Pro_TermData_String_concat cs;
// System.out.println(indent + "_____");
// System.out.println(indent + "GSP: p = " + p + ", s = " + s.structure());    
indent += "  ";

    switch (s.tag) {
      case Pro_TermData_String.SIMPLE : {
        string_found = ((Pro_TermData_String_simple)s).value;
        start_pos = p;
        len = s.len - start_pos;
// System.out.println(indent + "GSP/SIMPLE: string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
      }; break;
      case Pro_TermData_String.SUBSTRING : {
// System.out.println(indent + "GSP/SUBSTRING: ");
        ss = (Pro_TermData_String_substring)s;
// System.out.println(indent + "GSP/SUBSTRING ss: base_string = \"" + ss.base_string + "\", start = " + ss.start + ", len = " + ss.len);
        get_string_part(p + ss.start, ss.base_string);
// System.out.println(indent + "GSP/SUBSTRING: string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
//        if (len > ss.len - start_pos) len = s.len;
        if (len > ss.len - p) len = s.len - p;
// System.out.println(indent + "GSP/SUBSTRING: len = " + len);
        
      }; break;
      case Pro_TermData_String.CONCATENATED : {
        cs = (Pro_TermData_String_concat)s;
        if (p < cs.left.len) {
// System.out.println(indent + "GSP/CONCATENATED left: ");
          get_string_part(p, cs.left);
        } else {
// System.out.println(indent + "GSP/CONCATENATED right: ");
          get_string_part(p - cs.left.len, cs.right);
        }
      }; break;
    }

// System.out.println(indent + "GSP: string_found=|" + string_found + "|," +
// "start_pos=" + start_pos + ",len=" + len);
indent = indent.substring(2);
// System.out.println(indent + "_____");

  }
*/
/*
  public String typename() {
    Pro_TermData data = getData();
    
    if (data == null) {
      return "null";
    } else {
      return data.getClass().getName();
    }
  }
*/
} // end class Pro_Term

