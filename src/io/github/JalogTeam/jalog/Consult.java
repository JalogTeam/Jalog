// Consult.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Consult
{
  static Pro_Term exit_value = null;
  static boolean consult_use_res = false;
  static private String base_dirname = "file:" + 
      (new File("")).getAbsolutePath();
  static private String consult_dirname = base_dirname;

  
  // identify returns absolute path prefixed with 'file:' or 'res:'
  static public String identify(String name) {
    File infile = null;
    String result = name;
    boolean use_res;
    
    if (name.startsWith("res:")) {
      // Ok
//    } else if (name.startsWith("file:")) {
      // Ok
    } else {
      if (name.startsWith("file:")) {
        use_res = false;
        name = name.substring(5); // remove "file:" from beginning
      } else {
        use_res = consult_use_res;
      }
      if((!use_res) && (File.separatorChar != '/')) {
        name = name.replace('/', File.separatorChar);
      }
      infile = new File(name);
      if (infile.isAbsolute()) {
        result = "file:" + name;
      } else if (use_res){
        if (consult_dirname != null) {
          result = consult_dirname + "/" + name;
        } else {
          result = "res:" + name;
        }
      } else { // relative file path
        if (consult_dirname != null) {
          result = consult_dirname + File.separatorChar + name;
        } else {
          result = "file:" + name;
        }
      }
    }
    return result;
  }

  static public void set_consult_dir(String dirname) {
    if (dirname.startsWith("res:")) {
      consult_use_res = true;
      consult_dirname = dirname;
    } else if (dirname.startsWith("file:")) {
      consult_use_res = false;
      consult_dirname = dirname;
    } else {
      consult_dirname = identify(dirname);
      consult_use_res = dirname.startsWith("res:");
    }
  }
  
  static public String get_consult_dir() {
    return consult_dirname;
  }

  static void consult_file(String raw_fileName, String[] filter)
  {
// System.out.println("Consult.consult_file: filter: '" + filter + "', raw_fileName: \"" + raw_fileName + "\"");
    int root_type = 0; // 1-file, 2-resource
    int name_start_pos = 0;  
    File infile = null;    
    
    Reader input = null;
/*
    String fileName = identify(raw_fileName);
// System.out.println("Consult.consult_file: filter: '" + filter + "', fileName: \"" + fileName + "\"\n");
    
    if (fileName.startsWith("res:")) {
      root_type = 2; // resource
      name_start_pos = 4;
      
      try {
// System.out.println("A " + fileName);
        InputStream is = Jalog.getResourceAsStream(fileName.substring(name_start_pos));
// System.out.println("A0 " + (is==null?"is==null":"is/=null"));
        input =
          new InputStreamReader(is, "UTF-8");
      } catch (Exception e) {
        System.err.println("\n*** Error: " + e);
        input = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      
    } else if (fileName.startsWith("file:")) {
      root_type = 1; // file
      name_start_pos = 5;
      
      try {
// System.out.println("B");
        input = new FileReader(fileName.substring(name_start_pos));
      } catch (Exception e) {
        System.err.println("\n*** Error: " + e);
        input = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      
    }
    
*/
System.out.println("input: " + input);
System.out.println("Consult.consult_file 118: identify(raw_filename) = " + identify(raw_fileName));
FileManager.openread(raw_fileName, raw_fileName, consult_dirname, consult_use_res);
System.out.println("Consult.consult_file 120: raw_fileName: '" + raw_fileName + "'" + "exit_value: '" + FileManager.exit_value + "'");
System.out.println("Consult.consult_file 121: FileManager.open_files: '" + FileManager.open_files + "'");
System.out.println("Consult.consult_file 122: FileManager.open_files.get(raw_fileName): '" + FileManager.open_files.get(raw_fileName) + "'");

input = FileManager.open_files.get(raw_fileName).reader;
System.out.println("Consult.consult_file 125: input: '" + input + "'");

    if(input != null) run(input, filter, raw_fileName);
    
  }

  static void consult_stringlist(String[] lines, String[] filter, String name) { 
    // name for error messages only
    StringArrayReader input = new StringArrayReader(lines);
    
    run(input, filter, name);
    
  }
  
//  static void run(String FileName)
  static private void run(Reader input, String[] filter, String filename) {

// System.out.println("Consult.run: filter: '" + filter + "', filename: \"" + 
// filename + "\"");

    exit_value = null;
    JalogTerms JT = new JalogTerms(JalogTerms.CLAUSE);
    Pro_Term T;
    Pro_Term[] Apu = new Pro_Term[10];
    int ApuCnt = 0;
    String line;
    BufferedReader file1;
    int LineNmbr = 0;

    /*try*/ {
    
      try {
//        file1 = new BufferedReader(new FileReader(FileName));
        file1 = new BufferedReader(input);
      } catch (Exception e) {
        System.err.println("\n*** Error: " + e);
        file1 = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      if(file1 != null) {
        do {
          try {
            line = file1.readLine();
            LineNmbr = LineNmbr + 1;
          } catch (Exception e) {
            System.err.println("\n*** Error: " + e);
            line = null;
          }
  // System.out.println("");
  // System.out.println("Line: " + line);
          JT.SetLine(line);
          do
          {
  // System.out.println("   ---");
            T = JT.NextPart();
            if(JT.Error != 0)
            {
              exit_value = Pro_Term.m_integer(1); // Syntax error
              if (JT.Error == JT.ERROR_INTERNAL) {
                Jalog.err.print("*** Internal error when processing file ");
                
              } else {
                Jalog.err.print("*** Error in file ");
              }
              Jalog.err.print(filename + " Line: " + LineNmbr);
              if (JT.ErrorPos > 0) {
                Jalog.err.print(" Pos: " + 
                  JT.ErrorPos);
              }
              Jalog.err.println();
              Jalog.err.println("    " + line);
              if (JT.ErrorPos > 0) {
                for(int i = -3; i < JT.ErrorPos; i++) {
                  Jalog.err.print(" ");
                }
                Jalog.err.println("^");
              }

              T = null;
              line = null;
            } else {
//System.out.println("   Term: " + T);
//System.out.println("");
              if(filter == null) {
                process_clause(T);
              } else {
                process_data(T, filter);
              }
              if(exit_value != null) {
                T = null;
                line = null;
              }
            }
            if (ApuCnt < 9) {
              Apu[ApuCnt] = T;
              ApuCnt++;
            }
          } while(T != null);
        } while (line != null);
      }
//System.out.println("Consulted");
    
/*    } catch (Exception e) {
      System.out.println(e); */
    }
/*
    int I;
    for (I = 0; I < ApuCnt; I++) {
      System.out.println("Term[" + I + "]:" + Apu[I]);
    }
*/
  }

  private static void process_clause(Pro_Term T) {
//System.out.println("\n--Consult: process_clause:" + T );

    Pro_TermData_Compound data = 
        (T != null ? (Pro_TermData_Compound) T.getData() : null);

    if(data != null && data instanceof Pro_TermData_Compound){
      
      if(data.name.equals(":-") && (data.arity == 2)) { // is clause
        if((data.subterm[0] == null) && (data.subterm[1] != null)){
          // Directive to be executed immediately
          // :- <list of predicate calls>.
          Inference I = new Inference();

          Pred.forward = true;
          I.run_body(data.subterm[1]);
          if(I.exit_value == null)
          {
            if(Pred.forward){
              System.err.println("*Yes*");
            } else {
              System.err.println("*No*");
            }
          } else {
            /* Exception! */
            exit_value = I.exit_value;
          }
          Pred.trail.backtrack(I.Mark); // clear variables
        } else {
          if(data.subterm[0] != null) { // We have head!
            if(data.subterm[1].getData() == Pro_TermData_List.EMPTY) {
              Database.assertz(data.subterm[0]);
            } else {
              Database.assertz(T);
            }
          }

        }
      
      } 

    }
  }

  private static void process_data(Pro_Term T, String[] filter) {
    // filter: ["data/3", "x/2",...]
    
// System.out.println("\n--Consult: process_data:" + T );
    Pro_TermData_Compound data = 
        (T != null ? (Pro_TermData_Compound) T.getData() : null);

    if(data != null 
        && data.name.equals(":-") && (data.arity == 2) &&
        (data.subterm[0] != null) && 
        (data.subterm[1].getData() == Pro_TermData_List.EMPTY)){
      // genuine fact

      Pro_TermData_Compound fact = 
          (Pro_TermData_Compound)data.subterm[0].data;

      String name = fact.name;
      byte arity = fact.arity;
      int i;
      boolean found;
      String key = name + "/" + Integer.toString(arity);
      found = false;
// System.out.println("\n--Consult: process_data: key = \"" + key + "\"");
      for (i=0; (i < filter.length) && !found; i++) {
        found = key.equals(filter[i]);
// System.out.println("\n--Consult: process_data: filter = \"" + filter[i] + 
// "\", found = " + found);
      }
      if(found){
        Database.assertz(data.subterm[0]);
      }

    }
// System.out.println("\n--Consult: process_data: Done");
  }

}
