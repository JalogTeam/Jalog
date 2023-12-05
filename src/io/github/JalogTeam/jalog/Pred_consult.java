// Pred_consult.java
      // consult(String filename) - (i)

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_consult extends Pred
{
  static Hashtable ConsultedFiles = new Hashtable(100);

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
  
    String filename;
    boolean found;
    
    filename = Consult.identify(data.subterm[0].image());
// System.out.print("\n--Consulting \"" + data.subterm[0].image() + " -> " + filename + "\"--");

    found = (ConsultedFiles.get(filename) != null);
/*
    int size = ConsultedFiles.size();
    boolean found = false;
    for(int i = 0; (i < size) && !found; i++){
      found = filename.equals((String)ConsultedFiles.elementAt(i));
    }
*/
    if(!found) {
// System.out.print(" starting.\n");
/*
      ConsultedFiles.push(filename);
*/
      ConsultedFiles.put(filename, "");
      Consult.consult_file(filename, null);
      if(Consult.exit_value != null) { // bad file
//        Pred.exception = true;
        Pred.exit_value = Consult.exit_value;
      }
//              ConsultedFiles.pop(); No double consulting
// System.out.print("\n--Consulting \"" + filename + "\"-- Finished\n");
    }
    // result = new Pred(); // **
    return null;
  }

}
