// Pred_member.java
// member(generic, generic*) - (i,o) (i,i) (o,i) (o,o)

package io.github.JalogTeam.jalog;

public class Pred_member extends Pred
{
  
  Pro_TermData_List current_list;
  Pro_Term elem;
  
  
  
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term elem_param = params.subterm[0].getRealNode();
    Pro_Term list_param = params.subterm[1].getRealNode();

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    if (list_param.data == null) { // (i,o)
      Pro_Term[] e_array = {elem_param};
      Pro_Term.m_list(e_array);
      forward = Pro_Term.m_list(e_array).unify(list_param, trail);
    } else { // (i,i)
      if (list_param.data.typename == Jalog.LIST) {
        current_list = (Pro_TermData_List)list_param.data;
        elem = elem_param;
        
        call();
      }
    }
        
        
        
      // check type
      if (elem_param.data.typename == Jalog.STRING) {
        Pro_TermData_String s1 = (Pro_TermData_String)elem_param.data;
        if (s1.len == 1) { // only length 1 is possible
          // compute result_value for list_param
          c = s1.substring(0, 1).charAt(0);

          Pro_Term result_value = Pro_Term.m_char(c);
          
          // Unify result_value and list_param
          forward = result_value.unify(list_param, trail);
        }
      }

    } else if (list_param.data != null) {
      // elem_param is open and list_param is bound
      // check type
      if (list_param.data.typename == Jalog.CHARACTER) {
        
        // compute result_value for elem_param
        s = Character.toString(((Pro_TermData_Char)list_param.data).value);
        Pro_Term result_value = Pro_Term.m_string(s);
        
        // Unify result_value and elem_param
        forward = result_value.unify(elem_param, trail);
      }
    
    }
    
    return null;
    
  }
  
  public void call()
  {

    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    
    while ((cur_pos < end_pos) && !forward) {
      forward = Pro_TermData_String.contains_at(Str_in, cur_pos, Str_out);
      cur_pos++;      
    }

    if (forward) {
      forward = Pos.unify(Pro_Term.m_integer(cur_pos - 1), trail, Mark);
    }
     
  }

}


    public class Pred_<i>example</i> extends Pred
    {
      Pro_Term <i>param_1</i>;
      Pro_Term <i>param_2</i>;     
      ... // more declarations as needed
      
      public static Pred first_call(Pro_TermData_Compound params) {

        Pred_<i>example</i> state = null;
    /*
        You may want to take parameters to local variables:
    */    
        Pro_Term <i>first_param</i> = params.subterm[0].getRealNode();
        Pro_Term <i>second_param</i> = params.subterm[1].getRealNode();
        ... // more parameters as needed

        ... // other declarations as needed

    /*
        Identify flow pattern and act accordingly.
        <i>_param</i>.data == null means an open variable.
    */    
        if (<i>first_param</i>.data != null) 
        {  // <i>first_param</i> is bound
          // compute <i>result_value</i>
          Pro_Term <i>result_value</i> = Pro_Term.m_<i>type</i>(...);
          
          // Unify <i>result_value</i> and <i>second_param</i>
          forward = <i>result_value</i>.unify(<i>second_param</i>, trail);

        } else if (<i>second_param</i>.data != null) {
          Pro_TrailMark newMark;
          // compute <i>result_value</i> for <i>first_param</i>
          Pro_Term <i>result_value</i> = Pro_Term.m_<i>type</i>(...);
          
          // Unify <i>result_value</i> and <i>first_param</i>
          forward = <i>result_value</i>.unify(<i>first_param</i>, trail, newMark);
        
          if (forward) { 
            // this is needed if backtracking can produce more results
            state = new Pred_<i>example</i>();
            state.Mark = newMark;
            state.<i>param_1</i> = <i>first_param</i>;
            state.<i>param_2</i> = <i>second_param</i>;
            ... // Set other state fields as needed.
          }
          
        } else { 
          // In this example, both open parameters -> invalid call
          forward = false; 
           
          // op_found = false; // if an error message wanted
          
          // exception = true; // if an exception is wanted
          // exit_value = Pro_Term.m_integer(<i>error_code</i>);
           
        }
        
        return state;
        
      }
 
      public void call()
      {
        // This is called on backtrack


        if(!forward){
          trail.backtrack(Mark); // cancels unifications
        }
        forward = false;
            
        // Compute another result 
        Pro_Term <i>result_value</i> = Pro_Term.m_<i>type</i>(...);
        
        // Unify <i>result_value</i> and <i>param_1</i>
        forward = <i>result_value</i>.unify(<i>param_1</i>, trail, Mark);

      }

      // post_call is needed, if Prolog code is executed within the predicate
      public void post_call()
      {

        called_body = null;
      }

    }
