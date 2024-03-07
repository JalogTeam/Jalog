import java.io.*;
import java.util.*;
import java.text.*;

public class Update_Version 
{
  static String version = "Jalog 1.4.1 by Ari Okkonen & Mikko Levanto ";
  
  public static void main(String args[]) {
    String filename = null;
    
    for (int i = 0; i < args.length; i++) {
      System.out.println("# " + i + ": |" + args[i] + "|");
      if ((args[i].charAt(0) != '-') && (filename == null)) {
        filename = args[i];
      }
    }
    System.out.println("# filename = " + filename);
    if (filename != null) {
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      System.out.println("# version = " + version + formatter.format(date));
            try
            {
                  
              BufferedWriter out = new BufferedWriter(new FileWriter(filename));

              out.write("// Generated code"); out.newLine();
              out.write("package io.github.JalogTeam.jalog;"); out.newLine();
              out.write(""); out.newLine();
              out.write("public class Version {"); out.newLine();
              out.write("  public static final String id_string ="); out.newLine();
              out.write("      \"" + version + formatter.format(date) + "\";"); 
                  out.newLine();
              out.write("}"); out.newLine();
              
              out.close();
            } catch (IOException e) {
              System.err.println("*** Error: IOException " + e);
              System.err.println("  - Cannot update version");
            } 
    }
  }
}
/*
package io.github.JalogTeam.jalog;

public class Version {
  public static final String id_string =
      "Jalog 1.4.0 by Ari Okkonen & Mikko Levanto 2022-02-01";
}
*/