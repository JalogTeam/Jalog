// Pred_cut_.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_cut_ extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {
    return new Pred_cut_();
  }

  Pred_cut_(){
    cut = true;
  }

  public void call()
  {
    if(!forward) {
// System.out.println("\n Pred_cut_: cutting = true");
      cutting = true;
    }
  }
}
