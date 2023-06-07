// Pred_dynamic.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_dynamic extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
    Database_Table factClass;

    if(data1 instanceof Pro_TermData_String) {
      String key = ((Pro_TermData_String)data1).image();
      factClass = Database.define_by_string(key);
      factClass.dynamic = true;
    } else {
      System.err.println("*** Error: dynamic: Argument must be a " +
          "string containing functor/arity");
      Pred.forward = false;
    }

    return null;
  }
}
