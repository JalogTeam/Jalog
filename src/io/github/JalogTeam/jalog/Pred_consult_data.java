// Pred_consult_data.java
      // consult_data(String filename) - (i)

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_consult_data extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
  
    String filename;
    
    filename = data.subterm[0].image();
// System.out.print("\n--Consulting \"" + data.subterm[0].image() + " -> " + filename + "\"--");
    Pro_Term filter_list = data.subterm[1];
    String[] filter = ((Pro_TermData_List)filter_list.data). 
        toStringList();
// System.out.println("\n--Consulting data \"" + filename + "\" filter[0]: " + filter[0]);

/*
    ConsultedFiles.push(filename);
*/
    Consult.consult_file(filename, filter);
    if(Consult.exit_value != null) { // bad file
      Pred.exception = true;
      Pred.exit_value = Consult.exit_value;
    }
//              ConsultedFiles.pop(); No double consulting
// System.out.print("\n--Consulting \"" + filename + "\"-- Finished\n");
    // result = new Pred(); // **
    return null;
  }

}
