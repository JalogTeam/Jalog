// Pred.java
import java.io.*;

public class Pred_cut_ extends Pred
{
  Pred_cut_(){
    super();
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
