// Pred_member.java
// member(generic, generic*) - (i,o) (i,i) (o,i) (o,o)

package io.github.JalogTeam.jalog;

public class Pred_member extends Pred
{
  
  Pro_TermData current_data; // hopefully a list
  Pro_TermData previous_data; // a list
  Pro_Term elem;
  boolean complete;
  int clause_number;
  
  
  
  
  public static Pred first_call(Pro_TermData_Compound params) {

    Pred_member state = null;

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
    if (list_param.data == null) { // (i,o)(o,o)
      Pro_Term[] e_array = {elem_param};
      Pro_Term lista = Pro_Term.m_list(e_array, Pro_Term.m_open());
      forward = lista.unify(list_param, trail);
    } else { // (i,i) (o,i)
      Pro_TermData first_data = (Pro_TermData_List)list_param.getData();
      if (first_data.typename == Jalog.LIST) {
        forward = true; // no backtrack in first call
        
        state = new Pred_member();
        state.current_data = first_data;
        state.elem = elem_param;
        state.clause_number = 1;

        trail.mark(state.Mark);
        
        state.call();
      }
    }
    
    return state;
    
  }
  
  public void call()
  {
    Pro_Term current_head;
    Pro_Term current_tail;

    if(!forward) { // not executed in first call
      trail.backtrack(Mark);
    }
    forward = false;
    
    while((!forward) && (current_data != null) && (current_data != Pro_TermData_List.EMPTY) &&
        (current_data.typename == Jalog.LIST)) {
      previous_data = current_data; 
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
