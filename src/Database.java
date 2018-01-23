// Database.java
import java.io.*;
import java.util.*;

class Database_FactClass // Name/Arity
{
  DB_Cursor last_cursor;
  Chain facts;

  Database_FactClass()
  {
    last_cursor = null;
    facts = new Chain();
  }
}

class Fact_Chain_Item extends Chain_Item
{
//  Pro_Term term;
  Pro_TermData_Compound data;
}

public class Database
{
  static Hashtable db;
  
  static{
    db = new Hashtable(100);  
  }
  
  static void asserty(Pro_Term x, boolean last)
  {
    Database_FactClass factClass;
    Pro_TermData data = x.getData();
    if(data instanceof Pro_TermData_Compound) {
      Pro_TermData_Compound compo = (Pro_TermData_Compound)data;
      String name = compo.name;
      byte arity = compo.arity;
      if((name.equals("if_") || name.equals(":-")) && arity == 2) {
        Pro_Term head = compo.subterm[0];
        Pro_Term body = compo.subterm[1];

        Pro_TermData headdata = head.getData();
        if(headdata instanceof Pro_TermData_Compound) {
          Pro_TermData_Compound headcompo = (Pro_TermData_Compound)headdata;
          name = headcompo.name;
          arity = headcompo.arity;
        } else {
          System.out.println("*** Error: assert: wrong head Pro_TermData class: " + 
              headdata.getClass().getName());
        }

        Pro_TermData bodydata = body.getData();
        if(!(bodydata instanceof Pro_TermData_List)) {
          System.out.println("*** Error: assert: wrong body Pro_TermData class: " + 
              bodydata.getClass().getName());
        }
      }
      String key = name + "/" + Integer.toString(arity);

      factClass = (Database_FactClass) db.get(key);
      if(factClass == null) {
        factClass = new Database_FactClass();
        db.put(key,factClass);
      } 
      // factClass Ok
      
      Fact_Chain_Item item = new Fact_Chain_Item();
 
      Pro_Term saveterm = x.copy();
      item.data = (Pro_TermData_Compound) saveterm.getData();
      if(last) {
        factClass.facts.addLast(item);
      } else {
        factClass.facts.addFirst(item);
      }

    } else {
      System.out.println("*** Error: assert: wrong Pro_TermData class: " + data.getClass().getName());
    } 
        
  }

  
  static void asserta(Pro_Term x)
  {
    asserty(x, false);
  }
  
  static void assertz(Pro_Term x)
  {
    asserty(x, true);
  }
  
  static Pro_Term retract(Pro_Term x)
  {
    return null;
  }

/* MALLI  
  static Pro_Term fetch(Database_FactClass factClass, DB_Cursor prev_item, Pro_Term filter)
  {
    if (prev_item.current_item == null) {
      prev_item.current_item = factClass.facts.first;
    } else {
      prev_item.current_item = prev_item.current_item.next;
    }
    if (prev_item.current_item == null) {
      return null;
    } else {
      // New term and copy data to it
      Pro_Term temp_term = new Pro_Term();
      temp_term.data = prev_item.current_item.data;
      return temp_term.copy();
    }
  }
*/

  static Pro_Term fetch(Database_FactClass factClass, DB_Cursor prev_item,
      Pro_Term filter, Pro_TrailMark Mark)
  {
//System.out.println("Database.fetch(" + factClass+ "," + prev_item+ "," + filter+ "," +  Mark+ ")");
    if (prev_item == null) {
// System.out.println("Database.fetch: prev_item==null");
      return null;
    }
    while(true) // returns from inside when terminating condition (end of list or found) met
    {
      boolean is_rule;
      if (prev_item.current_item == null) {
        prev_item.current_item = factClass.facts.first;
      } else {
        prev_item.current_item = ((Fact_Chain_Item)(prev_item.current_item)).next;
      }
//System.out.println("Database.fetch: prev_item.current_item: " + prev_item.current_item);
       if (prev_item.current_item == null) {
// System.out.println("Database.fetch: *null*");
        return null;
      } else {
        // New term and copy data to it
        Pro_TermData_Compound compo = (Pro_TermData_Compound)
                (((Fact_Chain_Item) prev_item.current_item).data);
        Pro_Term temp_term = new Pro_Term(compo);
        Pro_Term head = temp_term;

        String name = compo.name;
        byte arity = compo.arity;
        is_rule = false;
        if((name.equals("if_") || name.equals(":-")) && arity == 2) {
          head = compo.subterm[0];
          is_rule = true;
        }
        if(filter.match(head)){
          temp_term = temp_term.copy(); // copy result for returning
          head = temp_term;
          if(is_rule)
          {
            head = ((Pro_TermData_Compound)(temp_term.getData())).subterm[0];
          }
          if(head.unify(filter, Pred.trail, Mark)) {
// System.out.println("Database.fetch: " + temp_term);

            return temp_term;
          }
        }
      }
    } // end while
  }

  static void dump(String filename)
  {
    try
    {
      Database_FactClass factClass;
      Fact_Chain_Item currentItem;

      BufferedWriter out = new BufferedWriter(new FileWriter(filename));
      Enumeration keys = db.keys();
      String key;

      out.write("--- Begin dump ---");
      out.newLine();

      while (keys.hasMoreElements()) {
        key = (String) keys.nextElement();
        out.write(key);
        out.newLine();
        factClass = (Database_FactClass) db.get(key);
        for(currentItem = (Fact_Chain_Item)factClass.facts.first; 
            currentItem != null; currentItem = (Fact_Chain_Item)currentItem.next) {
          out.write("  " + currentItem.data.image());
          out.newLine();
        }
      }

      out.write("--- End dump ---");
      out.newLine();

      out.close();
    } catch (IOException e) {
      System.out.println("*** Error: dump: IOException " + e);
    } 
  }
  
}
