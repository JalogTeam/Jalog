// Pred__eq_.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred__eq_ extends Pred
{
  public Pro_Term Left;
  public Pro_Term Right;

  Pred__eq_(Pro_TermData_Compound data)
  {
 
    Left = data.subterm[0];

    Right = data.subterm[1];
// System.out.println("Pred__eq_ construct " + Left + " = " + Right);
  }

  public void call()
  {
    
    if(!forward){
      trail.backtrack(Mark);
// System.out.println("Pred__eq_ call backtrack");
    } else if(Left.unify(Right, trail, Mark)) { 
      forward = true;
// System.out.println("Pred__eq_ call success");
    } else {
      forward = false;
// System.out.println("Pred__eq_ call fail");
    }
  }
}
