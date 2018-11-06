// Pred__eq_.java
import java.io.*;

public class Pred__eq_ extends Pred
{
  public Pro_Term Left;
  public Pro_Term Right;

  Pred__eq_(Pro_TermData_Compound data)
  {
 
    Left = data.subterm[0];

    Right = data.subterm[1];

  }

  public void call()
  {
    
    if(!forward){
      trail.backtrack(Mark);
    } else if(Left.unify(Right, trail, Mark)) { 
      forward = true;
    } else {
      forward = false;
    }
  }
}
