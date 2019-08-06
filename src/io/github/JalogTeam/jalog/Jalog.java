// Jalog.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Jalog
{
  static final String id_string="Jalog 0.4 by Ari Okkonen & Mikko Levanto 2019-08-06";
  
  private static int instance_count = 0;
  
  public Jalog() {
    if (instance_count > 0) {
      throw new Error("Multiple Jalog instances not supported.");
    } else {
      instance_count ++;
    }
  }
  
  public static void dispose() {
    
    Database.db.clear();
    
    instance_count = 0;
    
  }
  
  public static void main(String args[])
  { 
    int i;
    boolean show_help = true;

//    System.err.println(id_string);
/*
    for(i=0;i<args.length;i++){
      System.out.println("" + i + ": '" + args[i] + "'");
    }
*/
    
    Command_Line.set(args);
 
/*

    for(i=0;i<Command_Line.env_labels.length;i++){
      System.out.println("env " + i + ": '" + Command_Line.env_labels[i] + "'='"+Command_Line.env_values[i]+"'");
    }
    System.out.println("Program name: '" + Command_Line.program_name + "'");
    for(i=0;i<Command_Line.appl_labels.length;i++){
      System.out.println("appl " + i + ": '" + Command_Line.appl_labels[i] + "'='"+Command_Line.appl_values[i]+"'");
    }
*/

/* Settings based on command line options */
    for(i=0;i<Command_Line.env_labels.length;i++){
      String Label = Command_Line.env_labels[i];
      if(Label.equals("v")) {
        System.out.println(id_string);
        show_help = false; 
      }
    }

    if(Command_Line.program_name != null) {
      Consult.run(Command_Line.program_name);
//System.out.println("created terms total: " + Pro_Term.lastId);
//System.out.println("max trail: " + Pro_Trail.maxnum);
//System.out.println("last trail: " + Pro_Trail.currentnum);
      if(Consult.exit_value != null) {
        /* We got exceptional exit */
//        Pro_TermData exit_data = Consult.exit_value.data;
        Pro_TermData exit_data = Consult.exit_value.getData();
        if(exit_data instanceof Pro_TermData_Integer) {
          int exit_code = (int)((Pro_TermData_Integer)exit_data).value;
          System.exit(exit_code);
        } else {
          System.out.println("Abnormal termination: " + exit_data);
        }
      }
    } else if(show_help){
      System.out.println("Parameters: <compiler_options> <program_name> <program_arguments>");
      System.out.println("  <compiler_options>");
      System.out.println("      -v Show version information");
      System.out.println("  <program_name> - complete file name - no default extensions");
      System.out.println("  <program_arguments> - as the program needs them");
    }
      
    
  }
  
  // Java calling interface
  // ======================
  
  public static class Term extends Pro_Term{
    Term(Pro_Term t) {
      Id = t.Id;
      data = t.data;
    }

    private Term() {
      // Not to be used
    }
    // How to get rid of Pro_Term.getType? 
    
    public String getType()
    {
      Pro_TermData data = getRealNode().data;
      
      return (data == null ? Jalog.OPEN : data.typename);
    }

    public long getIntegerValue() {
      String type = getType();
      
      if(type == Jalog.INTEGER) {
        return ((Pro_TermData_Integer)getData()).value;
      } else if(type == Jalog.REAL) {
        return (long)(((Pro_TermData_Real)getData()).value);
      } else {
        return 0;
      }
    }

    public String getSymbolValue() {
      String type = getType();
      
      if(type == Jalog.SYMBOL) {
        return ((Pro_TermData_Compound)getData()).name;
      } else {
        return null;
      }
    }

    public double getRealValue() {
      String type = getType();
      
      if(type == Jalog.INTEGER) {
        return (double)(((Pro_TermData_Integer)getData()).value);
      } else if(type == Jalog.REAL) {
        return ((Pro_TermData_Real)getData()).value;
      } else {
        return 0.0;
      }
    }

    public char getCharacterValue() {
      String type = getType();
      
      if(type == Jalog.CHARACTER) {
        return ((Pro_TermData_Char)getData()).value;
      } else {
        return '\uFFFF';
      }
    }

    public String getStringValue() {
      String type = getType();
      
      if(type == Jalog.STRING) {
        return ((Pro_TermData_String)getData()).value;
      } else {
        return null;
      }
    }

    public Term[] getElements() {
      Term[] result;
      Pro_Term current, head;
      int n, i;
      
      if (getType() == Jalog.LIST) {
        current = this;
        n = 0;
        while ((current != null) && (current.getType() == Jalog.LIST)) {
          current = ((Pro_TermData_List)current.getData()).t2;
          if (current != null) n++;
//          n++;
        }
        if (current != null) n++;
// System.out.println("*** getElements, n=" + n);        
        result = new Term[n];
        current = this;
        n = 0;
        while (/*(current != null) && (current.getType() == Jalog.LIST) && */
            n < result.length) {
          head = ((Pro_TermData_List)current.getData()).t1;
          if (head != null) {
            result[n] = new Jalog.Term(head);
// System.out.println("  * getElements, ["+n+"] = "
// +(result[n]!=null ? result[n].getType() : "null"));
          } else {
            result[n] = null;
// System.out.println("  * getElements, ["+n+"] = "
// +(result[n]!=null ? result[n].getType() : "null"));
          }
          current = ((Pro_TermData_List)current.getData()).t2;
            if (current != null) n++;
//          n++;
        }
        /* Here for tail - not processed
        if (current != null){
          result[n] = new Jalog.Term(current);
System.out.println("  * getElements, ["+n+"] = "
+(result[n]!=null ? result[n].getType() : "null"));
          if (current != null) n++;
//          n++;
        }
        */
      } else {
        result = null;
      }
      return result;
    }

    public String getFunctor() {
      String type = getType();
      if ((type == Jalog.COMPOUND) || (type == Jalog.SYMBOL)) {
        return ((Pro_TermData_Compound)getData()).name;
      } else {
        return null;
      }
    }

    public Term[]  getSubTerms() {
      String type = getType();
      Term[] result = null;
      int i;
      int arity;
      Pro_TermData_Compound data;
      
      if ((type == Jalog.COMPOUND) || (type == Jalog.SYMBOL)) {
        data = (Pro_TermData_Compound)getData();
        arity = data.arity;
        result = new Term[arity];
        for(i = 0; i < arity; i ++)
        {
          result[i] = new Term(data.subterm[i]);
        }
      }
      return result;
    }

  };


  // typenames
  
  public static final String OPEN = "open";
  public static final String INTEGER = "integer";
  public static final String SYMBOL = "symbol";
  public static final String REAL = "real";
  public static final String CHARACTER = "character";
  public static final String STRING = "string";
  public static final String LIST = "list";
  public static final String COMPOUND = "compound";
  
  public static Term open() {
    return new Term(Pro_Term.m_open());
  }

  public static Term integer(long i) {
    return new Term(Pro_Term.m_integer(i));
  }

  public static Term symbol(String name) {
    return new Term(Pro_Term.m_compound(name, new Pro_Term[0]));
  }

  public static Term real(double r) {
    return new Term(Pro_Term.m_real(r));
  }

  public static Term character(char c) {
    return new Term(Pro_Term.m_char(c));
  }

  public static Term string(String s) {
    return new Term(Pro_Term.m_string(s));
  }

  public static Term list(Term[] elements) {
    return new Term(Pro_Term.m_list(elements));
  }

  public static Term compound(String name, Term[] arguments) {
    return new Term(Pro_Term.m_compound(name, arguments));
  }




  static public class Exit extends Exception {
    public Exit(long status) {
        super("Exit");
        this.status = status;
    }
    public long status;
  }
  
  static public void consult_file(String filename) {
    Consult.run(filename);
  }
  
  static public boolean call(String predname, Pro_Term ... args) throws Jalog.Exit {
    boolean retval;
    long exit_status = -1;
    
    Inference I = new Inference();

    Pro_Term predcall[] = {Pro_Term.m_compound(predname, args)};
    
    Pro_Term body = Pro_Term.m_list(predcall);
    
    Pred.forward = true;
    I.run_body(body);
    
    // Get result values
    
    if(I.exit_value == null)
    {
      if(Pred.forward){
        retval = true;
      } else {
        retval = false;
      }
    } else {
      /* Exception! */
      Pred.trail.backtrack(I.Mark); // clear variables
      
      Pro_TermData exit_td = I.exit_value.getData();
      if (exit_td instanceof Pro_TermData_Integer) {
        exit_status = ((Pro_TermData_Integer)exit_td).value;
      }
      
      throw new Exit(exit_status);
    }
//    Pred.trail.backtrack(I.Mark); // clear variables
    return retval;
  }
}
