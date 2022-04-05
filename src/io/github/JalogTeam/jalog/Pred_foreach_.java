// Pred_foreach_.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_foreach_ extends Pred
{
  public Pro_Term Var;
  public Pro_TermData_List RestList;

  public static Pred make(Pro_TermData_Compound data) {
    
    Pred_foreach_ result = new Pred_foreach_(data);
    result.call();
    
    return result;
  }

  Pred_foreach_(Pro_TermData_Compound data)
  {
    Pro_Term List;
    Pro_TermData ListData;

    Var = data.subterm[0];

    List = data.subterm[1];
    ListData = List.getData();
    if(ListData instanceof Pro_TermData_List){
      RestList = (Pro_TermData_List)ListData;
    } else {
      RestList = null;
    }
    if(Var == null){
      RestList = null;
    }

  }

  public void call()
  {
    Pro_Term head;
    Pro_Term List;
    Pro_TermData ListData;

    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    while ((!forward) && (RestList != null)) {
      head = RestList.t1;

      // Forward list
      List = RestList.t2;
      ListData = (List != null ? List.getData() : null);
      if(List != null && ListData instanceof Pro_TermData_List){
        RestList = (Pro_TermData_List)ListData;
      } else {
        RestList = null;
      }

      if(head != null){
        if(Var.unify(head, trail, Mark))
        { 
          forward = true;
        }
      }
    } // end while
     
  }
}
