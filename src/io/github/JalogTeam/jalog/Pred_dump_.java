// Pred_dump_.java
      // consult(String filename) - (i)

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_dump_ extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
    String filename;
    
    if(data1 instanceof Pro_TermData_String) {
      filename = ((Pro_TermData_String)data1).image();
      Database.dump(filename);
    } else {
      Pred.forward = false;
    }

    return null;
  }

}
