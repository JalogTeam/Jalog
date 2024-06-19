// Pred_consult_data.java
      // consult_data(String filename) - (i)

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_consult_data extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
    Pro_TermData data2 = (data.subterm.length > 1 ? data.subterm[1].getData() :
        null);
  
    String filename;
    String[] filter = null;
    String domain = null;
    
    filename = FileManager.identify(data.subterm[0].image(), "", false);
// System.out.print("\n--Consulting \"" + data.subterm[0].image() + " -> " + filename + "\"--");
    if (data2 == null) { // default database
      domain = Database.DEFAULTDB;
    } else if (data2.typename == Typenames.SYMBOL) { // databasename
      domain = data2.toString();
    } else if (data2.typename == Typenames.LIST) { // Should be filter list
      filter = ((Pro_TermData_List)data2).toStringList();
    }
   
// System.out.println("\n--Consulting data \"" + filename + "\"" +
// (filter != null ? " filter[0]: " + filter[0] : " filter: null " ) +
// " domain = " + domain);

    Consult.consult_file(filename, filter, domain);

    if(Consult.exit_value != null) { // bad file
//      Pred.exception = true;
      Pred.exit_value = Consult.exit_value;
    }
//              ConsultedFiles.pop(); No double consulting
// System.out.print("\n--Consulting \"" + filename + "\"-- Finished\n");
    // result = new Pred(); // **
    return null;
  }

}
