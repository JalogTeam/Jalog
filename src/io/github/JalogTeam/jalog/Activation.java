// Activation.java

package io.github.JalogTeam.jalog;

import java.io.*;

/* Activation record for running predicates */

public class Activation
{
  static int nextId = 1;

  public int Id; 
  Pred pcall;      // Predicate under execution
//  Activation down; // Activation record for called predicate or null
  Activation up;   // Activation record for calling predicate or null
  Pro_Term next;   // Rest of current clause (tail)

  Activation()
  {
    Id = nextId;
    nextId++;
  }

  public String toString()
  {
    return "<" + Id + ",up:"+ (up != null ? "" + up.Id : "null")+
        ",pcall:" + pcall +
        ",next:" + next + ">";
  }
  
}
