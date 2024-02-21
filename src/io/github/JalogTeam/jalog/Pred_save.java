// Pred_assertz.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_save extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    String databaseName = null;
    String fileName = null;
    boolean database_found = false;
    Database_Table t;
    Pro_Term term1 = data.subterm[0];

// System.out.println("Save predicate entered.");
    Pro_TermData data1 = term1.getData();
    Pro_TermData data2 = null;

    if (data.subterm.length > 1) {
      data2 = data.subterm[1].getData();
      if(data2.typename == Typenames.SYMBOL) {
        databaseName = ((Pro_TermData_Compound)data2).name;
      } else {
        System.err.println("*** Error: save: database name must be symbol");
        Pred.forward = false;
      }
    } else {
      databaseName = Database.DEFAULTDB;
    } // got databaseName if Pred.forward
    
    if (Pred.forward) {
      if(data1 instanceof Pro_TermData_String) {
        fileName = ((Pro_TermData_String)data1).image();
      } else {
        System.err.println("*** Error: save: filename must be string");
        Pred.forward = false;
      }
    }

    if (Pred.forward) {
      FileManager.openwrite("**1", fileName);
      
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      } else {
        Enumeration<Database_Table> e;
        String olddevice = FileManager.get_writedevice();
        FileManager.set_writedevice("**1");
        for (e = Database.db.elements() ; e.hasMoreElements() ;) {
          t = e.nextElement();
          if (databaseName.equals(t.databaseName)) {
            // Write this to file
            database_found = true;
            if (t.dynamic) {
              Database.Fact_Chain_Item item;
              for (item = (Database.Fact_Chain_Item)(t.facts.first); 
                  item != null; item = (Database.Fact_Chain_Item)(item.next)) 
              {
                String line = item.data.toString() + ".";
                FileManager.write(line);
                FileManager.nl();
              }
            }
          }
        }
        FileManager.set_writedevice(olddevice);
      }
      FileManager.closefile("**1");
    }
    return null;
  }
}
