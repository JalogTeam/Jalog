// Consult.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Consult
{
  static Pro_Term exit_value = null;
  static private String consult_dirname = null;
  static private File consult_dir = null;
  static private String include_dirname = null;
  static private File include_dir = null;
  

  static private Object identify(String name, String[] filter) {
    String resource_name = null;
    File infile = null;
    
    int root_type = 0;
    int name_start_pos = 0;
    
    if (name.startsWith("res:")) {
      root_type = 2; // resource
      name_start_pos = 4;
    } else if (name.startsWith("file:")) {
      root_type = 1; // file
      name_start_pos = 5;
    } else {
      infile = new File(name);
      if (infile.isAbsolute()) {
        root_type = 1; // file
        name_start_pos = 0; 
      } else {
        // name is relative
      }
    }
    
    if (root_type == 1) {
      return infile;
    } else {
      return resource_name;
    }
  }


/*
  static public void set_consult_dir(String dirname) {
    consult_dirname = dirname;
    Object r = identify(dirname, 'x');
    if (r == null) {
      
    } else if (r isinstanceof File) {
      consult_dir = (File)r;
      consult_dirname = consult_dir.toString();
    } else {
      consult_dir = null;
      consult_dirname = (String)r;
    } 
  }

  static void consult_file(String fileName, char mode)
  {
    File infile = null;
    String resource_name = null;
    
    Object r = identify(fileName, 'x');
    if (r == null) { // relative
      if (consult_dirname != null) {
        if (consult_dir != null) { // file
          infile = new File(consult_dir, fileName);
        } else { // resource
          resource_name = 
        }
      }
    }


  }
*/

  static public void set_include_dir(String dirname) {
    include_dirname = dirname;
  }

  static void consult_file(String fileName, String[] filter)
  {
// System.out.println("Consult.consult_file: filter: '" + filter + "', fileName: \"" + fileName + "\"");
    int root_type = 0; // 1-file, 2-resource
    int name_start_pos = 0;  
    File infile = null;    
    
    Reader input = null;
    
    if (fileName.startsWith("res:")) {
      root_type = 2; // resource
      name_start_pos = 4;
      
      try {
// System.out.println("A");
        InputStream is = Consult.class.getClassLoader().
          getResourceAsStream(fileName.substring(name_start_pos));

        input =
          new InputStreamReader(is, "UTF-8");
      } catch (Exception e) {
        System.out.println("*** Error: " + e);
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
        System.out.println("*** Error: " + e);
        input = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      
    } else {
      infile = new File(fileName);
      if (infile.isAbsolute()) {
        // VALMIS
// System.out.println("VALMIS");
      
        try {
          input = new FileReader(fileName);
        } catch (Exception e) {
          System.out.println("*** Error: " + e);
          input = null;
          exit_value = Pro_Term.m_integer(1); // File not found
        }
        
      } else {
        if (filter == null) {
          if (consult_dirname != null) {
            // FILE NOT FOUND
// System.out.println("FILE NOT FOUND");
          } else if (consult_dir != null) {
            // resource
// System.out.println("resource");
      
            try {
              input = new FileReader(fileName);
            } catch (Exception e) {
              System.out.println("*** Error: " + e);
              input = null;
              exit_value = Pro_Term.m_integer(1); // File not found
            }
            
          } else {
            infile = new File(consult_dir, fileName.substring(name_start_pos));
            root_type = 1; // file
      
            try {
              input = new FileReader(fileName);
            } catch (Exception e) {
              System.out.println("*** Error: " + e);
              input = null;
              exit_value = Pro_Term.m_integer(1); // File not found
            }
            
          }
        }
      }
    }
    
/*
System.out.println();
System.out.println("root_type = " + root_type);
System.out.println("name_start_pos = " + name_start_pos); 
System.out.println("infile = " + infile); 
System.out.println();
*/
    
    
/*   
//      System.out.println("Consulting " + FileName);
    try {
//        file1 = new BufferedReader(new FileReader(FileName));
      input = new FileReader(fileName);
    } catch (Exception e) {
      System.out.println("*** Error: " + e);
      input = null;
      exit_value = Pro_Term.m_integer(1); // File not found
    }
*/    
    if(input != null) run(input, filter, fileName);
    
  }

  static void consult_stringlist(String[] lines, String name) { 
    // name for error messages only
    StringArrayReader input = new StringArrayReader(lines);
    
    run(input, null, name);
    
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
        System.out.println("*** Error: " + e);
        file1 = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      if(file1 != null) {
        do {
          try {
            line = file1.readLine();
            LineNmbr = LineNmbr + 1;
          } catch (Exception e) {
            System.out.println("*** Error: " + e);
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
                System.err.print("*** Internal error when processing file ");
                
              } else {
                System.err.print("*** Error in file ");
              }
              System.err.print(filename + " Line: " + LineNmbr);
              if (JT.ErrorPos > 0) {
                System.err.print(" Pos: " + 
                  JT.ErrorPos);
              }
              System.err.println();
              System.err.println("    " + line);
              if (JT.ErrorPos > 0) {
                for(int i = -3; i < JT.ErrorPos; i++) {
                  System.err.print(" ");
                }
                System.err.println("^");
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
              System.out.println("*Yes*");
            } else {
              System.out.println("*No*");
            }
          } else {
            /* Exception! */
            exit_value = I.exit_value;
          }
          Pred.trail.backtrack(I.Mark); // clear variables
        } else {
          if(data.subterm[0] != null) {
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
