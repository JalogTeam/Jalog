// Database.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;


public class Database
{
static int debug = 0;
  static int exit_value = 0;
  static class Fact_Chain_Item extends Chain_Item
  {
  //  Pro_Term term;
    Pro_TermData_Compound data;
  }

  static Hashtable<String, Database_Table> db;
  
  static final int SUCCEEDED = 1;
  static final int FAILED = 2;
  static final int NOT_DYNAMIC = 3;
  static final String DEFAULTDB = "dbasedom";
  
  static{
    db = new Hashtable(100);  
  }
  
  private static void asserty(Pro_Term x, boolean last, String databaseName)
  {
    boolean is_rule = false;
    Database_Table factClass;
    Pro_TermData data = x.getData();
    
    exit_value = 0;
    if(data instanceof Pro_TermData_Compound) {
      Pro_TermData_Compound compo = (Pro_TermData_Compound)data;
      String name = compo.name;
      byte arity = compo.arity;
      if((name.equals("if_") || name.equals(":-")) && arity == 2) {
        exit_value = 9998;
      }
      String key = name + "/" + Integer.toString(arity);

      factClass = find_by_string(key, databaseName);
      if ((factClass != null) && factClass.dynamic) {
        Fact_Chain_Item item = new Fact_Chain_Item();
   
        Pro_Term saveterm = x.copy();
        item.data = (Pro_TermData_Compound) saveterm.getData();
        if(last) {
          factClass.facts.addLast(item);
        } else {
          factClass.facts.addFirst(item);
        }
      } else {
        exit_value = 1407; // The functor does not belong to the domain
      }
    } else {
      exit_value = 1407; // The functor does not belong to the domain
    } 
        
  }

  public static void assert_rule(Pro_Term x)
  {
    boolean last = true;
    String databaseName = null;
    boolean is_rule = false;
    Database_Table factClass;
    Pro_TermData data = x.getData();
    
    if(data instanceof Pro_TermData_Compound) {
      Pro_TermData_Compound compo = (Pro_TermData_Compound)data;
      String name = compo.name;
      byte arity = compo.arity;
      if((name.equals("if_") || name.equals(":-")) && arity == 2) {
        Pro_Term head = compo.subterm[0];
        Pro_Term body = compo.subterm[1];
        is_rule = true;  // not to be stored as dynamic

        Pro_TermData headdata = head.getData();
        if(headdata instanceof Pro_TermData_Compound) {
          Pro_TermData_Compound headcompo = (Pro_TermData_Compound)headdata; 
          name = headcompo.name;
          arity = headcompo.arity;
        } else {
          exit_value = 9997; // Invalid head of rule
          System.err.println("\n*** Error: assert: wrong head Pro_TermData class: " + 
              headdata.getClass().getName());
        }

        Pro_TermData bodydata = body.getData();
        if(!(bodydata instanceof Pro_TermData_List)) {
          exit_value = 9997; // Invalid head of rule
          System.err.println("\n*** Error: assert: wrong body Pro_TermData class: " + 
              bodydata.getClass().getName());
        }
      }
      String key = name + "/" + Integer.toString(arity);

//      factClass = (Database_Table) db.get(key);
      factClass = define_by_string(key, databaseName);
/*
      if(factClass == null) {
        factClass = new Database_Table();
        factClass.databaseName = databaseName;
        db.put(key,factClass);
      } else {
        if ((databaseName != "") && (databaseName != factClass.databaseName)) {
          System.err.println("\n*** Error: assert: databaseName conflict: " + 
              "fact: " + key + ", database attempted: " + databaseName + 
              ", should be: " + factClass.databaseName);
          factClass = null; // Error
        }
      }
*/
      if (factClass != null) { 
        // factClass Ok
        if (!(is_rule && factClass.dynamic)) {
          factClass.has_rules |= is_rule;
          Fact_Chain_Item item = new Fact_Chain_Item();
     
          Pro_Term saveterm = x.copy();
          item.data = (Pro_TermData_Compound) saveterm.getData();
          if(last) {
            factClass.facts.addLast(item);
          } else {
            factClass.facts.addFirst(item);
          }
        } else {
          exit_value = 9999; // Rule cannot be dynamic
          System.err.println("\n*** Error: assert: Dynamic rule not allowed: " + key);
        }
      }
    } else {
      exit_value = 9996; // Not a clause
      System.err.println("\n*** Error: assert: wrong Pro_TermData class: " + data.getClass().getName());
    } 
        
  }

  static void make_dynamic(String key, String databaseName) {
    Database_Table factClass = define_by_string(key, databaseName);
    if (!factClass.has_rules) factClass.dynamic = true;
  }
  
  static Database_Table define_by_string(String key, String databaseName) {
    Database_Table factClass;

// System.out.println("# define_by_string(" + key + ", " + databaseName + ")");
    factClass = find_by_string(key, databaseName);
    if(factClass == null) {
      factClass = new Database_Table();
      if (databaseName != null) factClass.setName(databaseName);
      db.put(key,factClass);
    } else {
      if (databaseName != null) { 
        if (factClass.databaseName == null) {
          factClass.setName(databaseName);
        } else if (!factClass.checkName(databaseName)) {
          exit_value = 9995; // Fact belongs to another database
          System.err.println("\n*** Error: databaseName conflict: " + 
              "fact: " + key + ", database attempted: " + databaseName + 
              ", should be: " + factClass.databaseName);
          factClass = null; // Error
        }
      }
    }
// System.out.println("# define_by_string -> " + factClass + " - " + 
// (factClass != null ? factClass.databaseName : "null"));

    return factClass;
  }
  
  static Database_Table find_by_string(String key, String databaseName) { 
    Database_Table factClass;

// System.out.println("# find_by_string(" + key + ", " + databaseName + ")");
    factClass = (Database_Table) db.get(key);
    if(factClass == null) {
      factClass = null; // Error
    } else {
      if (databaseName != null) { 
        if (factClass.databaseName == null) {
//          factClass.setName(databaseName);
        } else if (!factClass.checkName(databaseName)) {
          factClass = null; // Error
        }
      }
    }

    return factClass;
  }

  static Vector<Database_Table> find_dynamicFactClasses(String databaseName) {
    Vector<Database_Table> ans = new Vector<Database_Table>();
    Database_Table factClass;
    Enumeration<Database_Table> elements = db.elements();

    Fact_Chain_Item currentItem;

    while (elements.hasMoreElements()) {
      factClass = elements.nextElement();
if (debug > 0) System.err.println(" Database.find_dynamicFactClasses factClass."
+ "databaseName = " + factClass.databaseName);
      if (factClass.dynamic) {
        if ((databaseName == null) || 
            (factClass.databaseName.equals( databaseName)))
        {
          ans.add(factClass);
        }
      }
    }
    
    return ans;    
  }
  
  static void asserta(Pro_Term x)
  {
    asserty(x, false, null);
  }
  
  static void assertz(Pro_Term x)
  {
    asserty(x, true, null);
  }
  
  static void asserta(Pro_Term x, String databaseName)
  {
    asserty(x, false, databaseName);
  }
  
  static void assertz(Pro_Term x, String databaseName)
  {
    asserty(x, true, databaseName);
  }
  
  static int retract(Pro_Term filter, Pro_TrailMark Mark)
  {
//    return null;
    int status;
    Pro_Term unified_clause;
    DB_Cursor prev_item;
    Database_Table factClass;

    Pro_TermData_Compound data = (Pro_TermData_Compound)filter.getData();
 
    String name = data.name;
    byte arity = data.arity;
 
    String key = name + "/" + Integer.toString(arity);
    factClass = (Database_Table) Database.db.get(key);
    if((factClass != null) && factClass.dynamic) {
      prev_item = new DB_Cursor();
      Pred.trail.mark(Mark);
      unified_clause = Database.fetch(factClass, prev_item, filter, Mark, true);

      if(unified_clause != null) {
        factClass.facts.remove(prev_item.current_item);
        status = SUCCEEDED;
      } else {
        status = FAILED;
      }
    } else { // not dynamic
      status = NOT_DYNAMIC;
    }

    return status;

  }

  static Pro_Term fetch(Database_Table factClass, DB_Cursor prev_item,
      Pro_Term filter, Pro_TrailMark Mark, boolean fact_only)
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
      } else if (!prev_item.current_item.deleted) {
        // New term and copy data to it
        Pro_TermData_Compound compo = (Pro_TermData_Compound)
                (((Fact_Chain_Item) prev_item.current_item).data);
        if (compo != null) {
          
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
            if(filter.unify(head, Pred.trail, Mark)) {
  // System.out.println("Database.fetch: " + temp_term);

              return temp_term;
            }
          }
        }
      }
    } // end while
  }

  static public void dump(String filename)
  {
    try
    {
      Database_Table factClass;
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
        factClass = (Database_Table) db.get(key);
        out.write("  has_rules: " + factClass.has_rules);
        out.newLine();
        out.write("  dynamic: " + factClass.dynamic);
        out.newLine();
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
      System.err.println("\n*** Error: dump: IOException " + e);
    } 
  }
  
}
