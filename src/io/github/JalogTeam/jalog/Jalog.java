// Jalog.java
/*
  An interpreter for a Prolog-like language with Java-like arithmetic 
  written in Java
  
  Jalog is distributed under The MIT License
  ==========================================
  
    Copyright (c) 2019 JalogTeam: Mikko Levanto, Ari Okkonen

    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be included
    in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
    CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
    TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
  
  https://opensource.org/licenses/MIT
*/
package io.github.JalogTeam.jalog;

import java.io.*;

public class Jalog
{
  static final String id_string =
/*      "Jalog 1.4.0 by Ari Okkonen & Mikko Levanto 2022-01-11";
*/ 
      Version.id_string;
      
  private static int instance_count = 0;
  private static int arg_index = 1;

  public static void main(String args[])
  { 
    int i;
    boolean show_help = true;
    Jalog myJalog;

    
    Command_Line.set(args);
 

/* Settings based on command line options */
    for(i=0;i<Command_Line.env_labels.length;i++){
      String Label = Command_Line.env_labels[i];
      if(Label.equals("v")) {
        System.out.println(id_string);
        show_help = false; 
      }
    }

    if(Command_Line.program_name != null) {
      
      myJalog = new Jalog();
      
      /* Command line options for the program */

      Database.define_by_string("comline_arg/3"); // Avoid error messages
                                     // if no command line arguments      
      for(i=0;i<Command_Line.appl_labels.length;i++){
        set_comline_arg(Command_Line.appl_labels[i],
            Command_Line.appl_values[i]);
      }
      
      Consult.consult_file(Command_Line.program_name, null);
      if(Consult.exit_value != null) {
        /* We got exceptional exit */
//        Pro_TermData exit_data = Consult.exit_value.data;
        Pro_TermData exit_data = Consult.exit_value.getData();
        if(exit_data instanceof Pro_TermData_Integer) {
          int exit_code = (int)((Pro_TermData_Integer)exit_data).value;
          System.exit(exit_code);
        } else {
          System.err.println("Abnormal termination: " + exit_data);
        }
      }
    } else if(show_help){
      System.err.println("Parameters: <compiler_options> <program_name> <program_arguments>");
      System.err.println("  <compiler_options>");
      System.err.println("      -v Show version information");
      System.err.println("  <program_name> - complete file name - no default extensions");
      System.err.println("  <program_arguments> - as the program needs them");
    }
      
    
  }
  
// Java calling interface
// ======================

// Constructor

  // Constructs a Jalog inference engine. Limitation: Only one engine can exist
  // at any time. After usage it must be disposed of using the dispose method.
  
  public Jalog() {
// System.out.println("* Jalog constructor");
    if (instance_count > 0) {
      throw new Error("Multiple Jalog instances not supported.");
    } else {
      instance_count ++;
    }
    arg_index = 1;
    rm = new ResourceManager();
//System.out.println("*  rm=" + (rm==null?"null":"not null"));
    
  }

// Nested classes

  public static class ResourceManager {
    public InputStream getResourceAsStream(String fileName) throws IOException {
// System.out.println("* Jalog.ResourceManager.getResourceAsStream(" + fileName + ")");
      return Jalog.class.getClassLoader().getResourceAsStream(fileName);
    }
  }
  
  public static class Output {
    public void print(String s) {
      System.out.print(s);
    }
    
    public void println(String s) {
      print(s);
      print("\n");
    }

    public void println() {
      print("\n");
    }
  }
  
  public static class OutputErr extends Output {
    public void print(String s) {
      System.err.print(s);
    }
  }
  
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
        return image();
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
        //Tail not processed!
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

  static public class Exit extends Exception {
    public Exit(long status) {
        super("Exit");
        this.status = status;
    }
    public long status;
  }

// Resource manager replacement

  static ResourceManager rm = null;
  
  public static void setResourceManager(ResourceManager rm) {
    Jalog.rm = rm;
  }
  
  
// Output redirect

  public static Output out = new Output();
  public static Output err = new OutputErr();
  
  public static void setOut(Output o) {
    out = o;  
  }
  
  public static void setErr(Output o) {
    err = o;  
  }
  
// Fields: typenames
  
  public static final String OPEN = "open";
  public static final String INTEGER = "integer";
  public static final String SYMBOL = "symbol";
  public static final String REAL = "real";
  public static final String CHARACTER = "character";
  public static final String STRING = "string";
  public static final String LIST = "list";
  public static final String COMPOUND = "compound";

// Methods
  public static InputStream getResourceAsStream(String fileName) throws IOException {
// System.out.println("* Jalog.getResourceAsStream(" + fileName + ")");
// System.out.println("*   rm=" + (rm==null?"null":"not null"));
    return rm.getResourceAsStream(fileName);

  }
  
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

  static public void consult_file(String filename) {
    Consult.consult_file(filename, null);
  }

  static public void consult_stringlist(String[] lines, 
      String name) {
    Consult.consult_stringlist(lines, null, name);
  }

  static public void consult_data_file(String filename, String[] filter) {
    Consult.consult_file(filename, filter);
  }

  static public void consult_data_stringlist(String[] lines, String[] filter, 
      String name) {
    Consult.consult_stringlist(lines, filter, name);
  }

  static public void set_consult_dir(String dirname) {
    Consult.set_consult_dir(dirname);
  }
  
  static public String get_consult_dir() {
    return Consult.get_consult_dir();
  }
  
  static public void set_comline_arg(String label, String value) {
    Jalog.Term[] in_list_content;
    
    in_list_content = new Jalog.Term[3];
    
    in_list_content[0] = Jalog.integer(arg_index++);
    in_list_content[1] = Jalog.string(label);
    in_list_content[2] = Jalog.string(value);

    Database.assertz(Jalog.compound("comline_arg", in_list_content));
  }

  public static void dispose() {
    Database.db.clear();
    instance_count = 0;
  }
  
 
  static public boolean call(String predname, Pro_Term ... args) throws Jalog.Exit {
    boolean retval;
    long exit_status = -1;
    
    Inference I = new Inference();

    Pro_Term predcall[] = {Pro_Term.m_compound(predname, args)};
    
    Pro_Term body = Pro_Term.m_list(predcall);
    
    Pred.forward = true;
    Pred.exception = false;
    
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
    return retval;
  }
}
