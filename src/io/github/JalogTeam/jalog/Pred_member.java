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
System.out.println("Pred_member.first_call A " + " " + elem_param + " " );  
      Pro_Term lista = Pro_Term.m_list(e_array, null);
System.out.println("Pred_member.first_call B " + " " + lista);  
      forward = lista.unify(list_param, trail);
System.out.println("Pred_member.first_call C " + " " + forward + " " +
lista + " " + list_param);  
    } else { // (i,i) (o,i)
      if (list_param.data.typename == Jalog.LIST) {
        
        Pro_TermData first_data = (Pro_TermData_List)list_param.getData();
        if (first_data.typename == Jalog.LIST) {
          state = new Pred_member();
          state.current_data = first_data;
          state.elem = elem_param;
          state.clause_number = 1;

          trail.mark(state.Mark);

          
          state.call();
        }
      }
    }
    
    return state;
    
  }
  
  public void call()
  {
    Pro_Term current_head;
    Pro_Term current_tail;

System.out.println("Pred_member.call A " + forward + " " + elem + " " +
current_data);  
    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    
    while((!forward) && (current_data != null) && (current_data != Pro_TermData_List.EMPTY) &&
        (current_data.typename == Jalog.LIST)) {
System.out.println("Pred_member.call B " + clause_number + " " + forward + " " + elem + " " +
current_data);  
      previous_data = current_data; 
      current_head = ((Pro_TermData_List)current_data).t1.getRealNode();
      current_tail = ((Pro_TermData_List)current_data).t2.getRealNode();
System.out.println("Pred_member.call C current_tail: " + current_tail);  
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
/*      
      forward = current_head.unify(elem, trail, Mark);

      
      if (forward) { // if success prepare for
        if (current_tail.data != null) { // bound(Tail)?
          current_data = current_tail.data;
        } else {
          
        }
      } else {
        if (current_tail.data != null) { // bound(Tail)?
          current_data = current_tail.data;
System.out.println("Pred_member.call D current_data: " + current_data);  
        } else {
          current_tail.data = new Pro_TermData_List(elem, Pro_Term.m_open());
          trail.append(current_tail);
          forward = true;
          current_data = Pro_TermData_List.EMPTY;
System.out.println("Pred_member.call E current_data: " + current_data);  
        }
        
      }

*/



/*

      current_tail.data = new Pro_TermData_List(elem, Pro_Term.m_open());



      if (current_tail.data != null) {
        current_data = current_tail.data;
      } else {
        complete = true;
        forward = true;
        current_tail.data = new Pro_TermData_List(elem, Pro_Term.m_open());
        current_data = Pro_TermData_List.EMPTY;
      }

      if (current_tail.data != null) {
        forward = ((Pro_TermData_List)current_data).t1.unify(elem, trail, Mark);
      
        current_data = current_tail.data;
      } else {
      
        forward = true;
        current_tail.data = new Pro_TermData_List(elem, Pro_Term.m_open());
        current_data = Pro_TermData_List.EMPTY;
      }
*/      
    }
System.out.println("Pred_member.call Z " + forward + " " + elem + " " +
current_data);  
  }

}
