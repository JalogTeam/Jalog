// Pred.java
import java.io.*;

public class Pred
{
  static boolean forward;
  static boolean cutting;
  static boolean exception;
  static Pro_Term exit_value;

// TEMPORARY!
  static boolean z_request;
  

  public static Pro_Trail trail;

  Pred prev;
  Pro_Term body_item; // this and rest in list
//  boolean primitive; // is primitive predicate
  boolean cut;       // is cut primitive

  Pro_TrailMark Mark;

// called predicate control
  Activation sub_activation;
  Pro_Term called_body /*_item*/; // Kirjoitetaan kun tiedetaan

  static {
    forward = true;
    cutting = false;
    exception = false;
    exit_value = null;
    trail = new Pro_Trail();
// TEMPORARY!
    z_request = false;
  }

  Pred(){
    Mark = new Pro_TrailMark();
    prev = null;
//    primitive = true;
    cut = false;
    sub_activation = null;
    called_body = null;
  }

  public void call()
  {
  }

  public void post_call()
  {
    called_body = null;
  }

  public String toString()
  {
    return "{sub_activation:" + sub_activation + ",called_body:"+ called_body + "}";
  }


}
