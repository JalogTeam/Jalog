// Pred_findall.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_findall extends Pred
{
  private Pro_TermData_List work_list_first;
  private Pro_TermData_List work_list_last;
  private Pro_Term variable_term;
  private Pro_Term result_list; 
// static int next_id = 0;
// int id;
  
  
  public static Pred first_call(Pro_TermData_Compound data) {
// System.out.println("\nfindall " + next_id + " first_call BEGIN forward = " + forward);
// trail.dump("  findall first_call BEGIN"); 

    Pred_findall result = new Pred_findall();
// result.id = next_id++;
    
    result.variable_term = data.subterm[0].getRealNode();
// System.out.println("  findall first_call args: " + data.subterm[0].getRealNode() + 
// ", " + data.subterm[1].getRealNode() + ", " + data.subterm[2].getRealNode());
    
    if(result.variable_term.data != null) {
      Pred.forward = false;
      return null;
    }
    
    Pro_Term[] called_body_content = {data.subterm[1]};
    result.result_list = data.subterm[2].getRealNode();
    
//  Illegal list item as a dummy first:
    result.work_list_first = new Pro_TermData_List(null, Pro_Term.EMPTY_LIST);
    result.work_list_last = result.work_list_first;
    
 
    result.called_body = Pro_Term.m_list(called_body_content);
// trail.dump("  findall first_call END"); 
// System.out.println("findall " + result.id + " first_call END forward = " + forward);    

    return result;
  }

  public void post_call()
  {
// System.out.println("\nfindall " + id + " post_call BEGIN forward = " + forward);
// trail.dump("  findall post_call BEGIN"); 
// System.out.println("  findall post_call args: " + variable_term + 
// ", " + called_body + ", " + result_list);
// System.out.println("  findall post_call work_list_first.t2=" + work_list_first.t2);  
// System.out.println("* Z1");     
    if(forward) { // call succeeded, add new item
// System.out.println("* A1");     
// System.out.println("  findall post_call work_list_last.t2=" + work_list_last.t2);  
//      work_list_last.t2 = new Pro_Term(new Pro_TermData_List(
//          new Pro_Term(variable_term.getData()), Pro_Term.EMPTY_LIST));
      work_list_last.t2 = new Pro_Term(new Pro_TermData_List(
          new Pro_Term(variable_term.copy().getData()), Pro_Term.EMPTY_LIST));
// System.out.println("* A2");     
// System.out.println("  findall post_call work_list_last.t2=" + work_list_last.t2);  
      work_list_last = (Pro_TermData_List)work_list_last.t2.data;
      
// System.out.println("* A3");     
// System.out.println("  findall post_call work_list_last.t2=" + work_list_last.t2);  
            
      forward = false;
// System.out.println("* A4");     
    } else { // call failed, return result list
// System.out.println("* B1");     
// System.out.println("- result_list b = " + result_list);
       
      forward = work_list_first.t2.unify(result_list, trail);
// System.out.println("- forward = " + forward);
// System.out.println("- result_list a = " + result_list);

// System.out.println("* B2");     
    
      sub_activation = null;
// System.out.println("* B3");     
      called_body = null;
// System.out.println("* B4");     

     
    }
// System.out.println("  findall post_call work_list_first.t2=" + work_list_first.t2);  
// System.out.println("  findall post_call args: " + variable_term + 
// ", " + called_body + ", " + result_list);
// trail.dump("  findall post_call END"); 
// System.out.println("* Z2");  
// System.out.flush();   
// System.out.println("findall " + id + " post_call END forward = " + forward);    
  }
}
