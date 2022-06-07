// Pred_findall.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_findall extends Pred
{
  private Pro_TermData_List work_list_first;
  private Pro_TermData_List work_list_last;
  private Pro_Term variable_term;
  private Pro_Term result_list; 
  
  public static Pred first_call(Pro_TermData_Compound data) {

    Pred_findall result = new Pred_findall();
    
    result.variable_term = data.subterm[0].getRealNode();
    Pro_Term[] called_body_content = {data.subterm[1]};
    result.result_list = data.subterm[2].getRealNode();
    
//  Illegal list item as a dummy first:
    result.work_list_first = new Pro_TermData_List(null, Pro_Term.EMPTY_LIST);
    result.work_list_last = result.work_list_first;
    
 
    result.called_body = Pro_Term.m_list(called_body_content);

    return result;
  }

  public void post_call()
  {
// System.out.println("findall post_call forward = " + forward);    
    if(forward) { // call succeeded, add new item
      
      work_list_last.t2 = new Pro_Term(new Pro_TermData_List(
          new Pro_Term(variable_term.getData()), Pro_Term.EMPTY_LIST));
      work_list_last = (Pro_TermData_List)work_list_last.t2.data;
            
      forward = false;
    } else { // call failed, return result list
       
      forward = work_list_first.t2.unify(result_list, trail);
// System.out.println("- forward = " + forward);
// System.out.println("- result_list = " + result_list);
    
      sub_activation = null;
      called_body = null;

     
    }
    
  }
}
