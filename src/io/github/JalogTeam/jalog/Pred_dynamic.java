// Pred_dynamic.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_dynamic extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
    Pro_TermData data2;
    String databaseName = null;
    Database_Table factClass;

    if (data.subterm.length > 1) {
      data2 = data.subterm[1].getData();
      if(data2.typename == Typenames.SYMBOL) {
        databaseName = ((Pro_TermData_Compound)data2).name;
      } else {
        System.err.println("*** Error: dynamic: database name must be symbol");
        Pred.forward = false;
      }
    } else {
      databaseName = "";
    }
    
    if (Pred.forward) {    
      if(data1 instanceof Pro_TermData_String) {
        String key = ((Pro_TermData_String)data1).image();
// System.out.println("Pred_dynamic: key: " + key + ", databaseName: " + databaseName);
        factClass = Database.define_by_string(key, databaseName);
/*
if (factClass != null) {
System.out.println("Pred_dynamic: factClass.databaseName: " +  factClass.databaseName);
} else {
System.out.println("Pred_dynamic: factClass: null");
} 
*/
        if (factClass.has_rules) {
          System.err.println("*** Error: " + key + 
              " cannot be dynamic because it has rules.");
        } else {
          factClass.dynamic = true;
        }
      } else {
        System.err.println("*** Error: dynamic: Argument must be a " +
            "string containing functor/arity");
        Pred.forward = false;
      }
    }

    return null;
  }
}
