// Pred_fetch_.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_fetch_ extends Pred
{
  Database_Table factClass;
  DB_Cursor prev_item;
  Pro_Term calling_term;
  Inference I;
  Pro_Term unified_clause;

  Pred_fetch_(Pro_Term T)
  {

    calling_term = T;
    Pro_TermData_Compound data = (Pro_TermData_Compound)T.getData();
 
    String name = data.name;
    byte arity = data.arity;
 
    String key = name + "/" + Integer.toString(arity);
    factClass = (Database_Table) Database.db.get(key);
    if(factClass != null) {
      prev_item = new DB_Cursor();
      trail.mark(Mark);
      unified_clause = Database.fetch(factClass, prev_item, calling_term, Mark,
          false);
// System.out.println("Pred_fetch_ unified_clause = " + unified_clause);


      if(unified_clause == null){
        forward = false;
      } else {
        data = (Pro_TermData_Compound) unified_clause.getData();
        if(data instanceof Pro_TermData_Compound){

          if((data.name.equals("if_") || data.name.equals(":-")) && (data.arity == 2) 
              && (data.subterm[1] != null)){

              // Tassa pitaa suorittaa body. Voi tulla true tai false.
// hoida joskus? (hoidettu Inferencessa)                cutting = false;
            called_body = data.subterm[1];
// System.out.println("--  Pred_fetch_.call, after body: unified_clause=" + unified_clause + ", calling: " + calling_term);
          } else {
            called_body = Pro_Term.EMPTY_LIST;   
          }
        } else { // if not compound - then fail
          forward = false;
          factClass = null; // no more
        }
      }
    } else { // nothing in database, fail
      forward = false;
//      if(!key.equals("comline_arg/3")) { // TODO: Skip, if dynamic
        System.out.println("*** Unknown predicate: " + key);
//      }
    }
  } // Pred_fetch_






  public void call()
  {
    if(!forward){
      trail.backtrack(Mark);
// System.out.println("Pred_fetch_.call, backtrack " + calling_term);
    } else {
 System.out.println("*** ERROR: Pred_fetch_.call, forward == true !");
    }
// ???    cutting = false;
  }


  public void post_call()
  {
    Pro_TermData_Compound data;
// Debug_times.enter(11); 

    called_body = null;

    if(prev_item == null) {
      System.out.println("*** ERROR: Pred_fetch_.post_call, EXTRA CALL ! ***");
      throw new Error(" Boo ");
    }
    if(forward){
      // be happy! - so far

    } else { // failed, get next clause
// Debug_times.enter(13); 
      trail.backtrack(Mark);
// Debug_times.leave(13); 
      forward = true;
      if(cutting) {
        unified_clause = null; // no other choices considered
        cutting = false;
// System.out.println("Pred_fetch_.post_call cutting");
      } else {
// Debug_times.enter(12); 
        unified_clause = Database.fetch(factClass, prev_item, calling_term, 
            Mark, false);
// Debug_times.leave(12); 
// System.out.println("Pred_fetch_.post_call unified_clause = " + unified_clause);
      }

 
      if(unified_clause == null){
        forward = false;
        prev_item = null; // to break on surplus call
        factClass = null;

      } else {
        data = (Pro_TermData_Compound) unified_clause.getData();
        if(data instanceof Pro_TermData_Compound){

          if((data.name.equals("if_") || data.name.equals(":-")) && (data.arity == 2)) 
              { // is clause
            if(data.subterm[1] != null){

              // Tassa pitaa suorittaa body. Voi tulla true tai false.
// hoida joskus!                cutting = false;
              called_body = data.subterm[1];
            }
          }
        } else { // if not compound - then fail
          forward = false;
          factClass = null; // no more
        }
      }

    }
// Debug_times.leave(11); 
  }


}
