// Jalog.java
import java.io.*;

public class Jalog
{
  static final String id_string="Jalog 0.1 by Ari Okkonen & Mikko Levanto 2012-02-17";
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
System.out.println("max trail: " + Pro_Trail.maxnum);
System.out.println("last trail: " + Pro_Trail.currentnum);
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
}
