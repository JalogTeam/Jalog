// Pred_member.java
// member(generic, generic*) - (i,o) (i,i) (o,i) (o,o)

package io.github.JalogTeam.jalog;

public class Pred_member extends Pred
{
  // This data is used when backtracking to enable finding the next solution.
  Pro_TermData current_data; // hopefully a list
  Pro_Term elem;
  int clause_number;
  
  public static Pred first_call(Pro_TermData_Compound params) {

    Pred_member state = null;

    // Parameters to local variables:
    Pro_Term elem_param = params.subterm[0].getRealNode();
    Pro_Term list_param = params.subterm[1].getRealNode();

    forward = false; // default fail

    // Identify flow pattern and data types, and act accordingly.
  
    String typename = list_param.getType();
    if (typename == Jalog.OPEN) { // (i,o)(o,o)
      // Backtrack cannot produce another result, state = null
    
      // generate result data
      Pro_Term[] e_array = {elem_param};
      Pro_Term lista = Pro_Term.m_list(e_array, Pro_Term.m_open());
      
      // unify result data to parameter
      forward = lista.unify(list_param, trail);
    } else { // (i,i) (o,i)
      if (typename == Jalog.LIST) {
        Pro_TermData_List list_data = 
            (Pro_TermData_List)list_param.getData();
        // Backtrack may produce another result, state = new object
        state = new Pred_member();
        state.current_data = list_data;
        state.elem = elem_param;
        state.clause_number = 1;

        trail.mark(state.Mark); // set backtrack point
        
        state.call(); // the first result is generated the same way as other
                      // results
      }
    }
    return state;
  }
  
  public void call()
  {
    Pro_Term current_head;
    Pro_Term current_tail;

    trail.backtrack(Mark);
    forward = false;
    
    while((!forward) && (current_data != null) && 
        (current_data != Pro_TermData_List.EMPTY) &&
        (current_data.typename == Jalog.LIST)) 
    {
      current_head = ((Pro_TermData_List)current_data).t1.getRealNode();
      current_tail = ((Pro_TermData_List)current_data).t2.getRealNode();
      switch (clause_number) {
        case 1: {
// Clause 1: member1(E, [E|_]).
          forward = current_head.unify(elem, trail, Mark);
          clause_number = 2;
        } break;
        case 2: {
// Clause 2: member1(E, [_|L]) :- bound(L), !, member1(E, L).
          if (current_tail.data != null) { // bound(L)
            current_data = current_tail.data;
            clause_number = 1;
          } else {
            clause_number = 3;
          }
        } break;
        default: {
// Clause 3: member1(E, [_|L]) :- L = [E|_].
          current_tail.data = new Pro_TermData_List(elem, Pro_Term.m_open());
          trail.append(current_tail);
          forward = true;
          current_data = null;
        }
      }
    }
  }

}
