// Jalog.java
import java.io.*;

public class Jalog
{
  static final String id_string="Jalog 0.3 by Ari Okkonen & Mikko Levanto 2019-04-21";
  
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
  };


  // typenames
  
  static final String OPEN = "open";
  static final String INTEGER = "integer";
  static final String SYMBOL = "symbol";
  static final String REAL = "real";
  static final String CHAR = "char";
  static final String STRING = "String";
  static final String LIST = "list";
  static final String COMPOUND = "compound";
  
  public static Term symbol(String name) {
 
    return new Term(Pro_Term.m_compound(name, new Pro_Term[0]));
  }

  public static Term open() {
    return new Term(Pro_Term.m_open());
  }




  static public class Exit extends Exception {
    public Exit(long status) {
        super("Exit");
        this.status = status;
    }
    long status;
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
