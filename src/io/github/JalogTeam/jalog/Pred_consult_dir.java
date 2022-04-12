// Pred_consult_dir.java
      // consult(String filename) - (i)

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_consult_dir extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();
    String filename;
    
    if(data1 instanceof Pro_TermData_String) {
      filename = ((Pro_TermData_String_simple)data1).image();
      Consult.set_consult_dir(filename);
    } else if(data1 == null) {
      filename = Consult.get_consult_dir(); 
      Pro_TrailMark mark = new Pro_TrailMark();
      data.subterm[0].unify(Pro_Term.m_string(filename), Pred.trail, mark);
    } else {
      Pred.forward = false;
    }
    
    return null;
  }

}
