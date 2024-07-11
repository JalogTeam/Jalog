// Pred.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred
{
  public static boolean cutting = false;
//  public static boolean exception;
  public static Pro_Term exit_value = null;
  public static boolean forward = true;
  public static boolean op_found = false;
  public static Pro_Trail trail =  new Pro_Trail();
// TEMPORARY!
//  static boolean z_request;
/* #arity_info_test           
  public static final int z_number = 1;
*/  
/*
  static {
    forward = true;
    cutting = false;
//    exception = false;
    exit_value = null;
    trail = new Pro_Trail();
// TEMPORARY!
//    z_request = false;
  }
*/

  
  
  public Pro_Term called_body /*_item*/; // Kirjoitetaan kun 
  public boolean cut;       // is cut primitive
  public Pro_TrailMark Mark;
                                                             


  Pred prev;
  Pro_Term body_item; // this and rest in list
//  boolean primitive; // is primitive predicate


// called predicate control
  public Activation sub_activation;

  protected Pred(){
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
