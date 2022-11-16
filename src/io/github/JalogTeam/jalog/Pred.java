// Pred.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred
{
  public Pro_Term called_body /*_item*/; // Kirjoitetaan kun 
  public boolean cut;       // is cut primitive
  public static boolean cutting;
  public static boolean exception;
  public static Pro_Term exit_value;
  public static boolean forward;
  public Pro_TrailMark Mark;
  public static boolean op_found;
  public static Pro_Trail trail;
                                                             
// TEMPORARY!
  static boolean z_request;
/* #arity_info_test           
  public static final int z_number = 1;
*/  


  Pred prev;
  Pro_Term body_item; // this and rest in list
//  boolean primitive; // is primitive predicate


// called predicate control
  Activation sub_activation;

  static {
    forward = true;
    cutting = false;
    exception = false;
    exit_value = null;
    trail = new Pro_Trail();
// TEMPORARY!
    z_request = false;
  }

  public Pred(){
    Mark = new Pro_TrailMark();
    prev = null;
//    primitive = true;
    cut = false;
    sub_activation = null;
    called_body = null;
  }

  public static Pred first_call(Pro_TermData_Compound data) {
    return null;
  }
  
  public void call()
  {
// System.out.println("Pred.call");
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
