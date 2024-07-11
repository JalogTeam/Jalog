// Pred_free.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_debug_ extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {
    Pro_Term debug_mode = data.subterm[0].getRealNode();
    
    if (debug_mode.getType() == Typenames.INTEGER) {
      Pro_Term.debug = 
          (int)((Pro_TermData_Integer)(debug_mode.data)).value;
    } 
    return null;
  }
}
