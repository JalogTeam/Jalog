// Clause_Inference.java
import java.io.*;

public class Clause_Inference
{
//  Pro_Trail trail;
//  public boolean success;

  Pro_TrailMark mark;
  
  Clause_Inference next; // this level list
  Clause_Inference next_call;
  Clause_Inference previous_call;
  Pro_Term calling_term;

  Clause_Inference(Pro_Trail pTrail)
  {
    trail = pTrail;
    success = true;
  }

  public int infer_clause()
  {
    ///handle_heading - c-koodia
    if(*pforward){
		*p = stack_alloc(gstack,size);
		relocate(ptemplate,reloc_map,*p,size);
		*pforward = unify_term(param,(term*)*p,ptrail,pmark);
    }
    if(!*pforward)    {
        backtrack(ptrail,pmark);
		stack_release(gstack,*p);
    } else {
	}
   /// end c-koodia
  }

  public void run_body(Pro_Term body)
  {
    if(body.data instanceof Pro_TermData_List){
      Pro_TermData_List data = (Pro_TermData_List) body.data;
//      Pro_TermData_List calls_B = null; jos tarvitaan

      if(data.t1 != null){
        boolean done = false;
        while(!done){
          call_subgoal(data.t1); // t‰lle pit‰‰ olla tilamuuttuja
          if((!success) || (data.t2 == null)){
            done = true;
          } else {
            run_body(data.t2);
            if(success){
              done = true;
            }
          }
        }
      }
    }
  }

  public void call_subgoal(Pro_Term call)
  {
    int response;

    if(call.data instanceof Pro_TermData_Compound){
      Pro_TermData_Compound data = (Pro_TermData_Compound) call.data;

      response = Primitive_Ops.call(data, success);
      switch(response) {
        case 0: {  // fail
          success = false;
        } break;
        case 1: {  // success
          success = true;
        } break;
        case 2: {  // not primitive
          success = false; // joo, jatketaan, kun on jotain
        } break;
      }

    }
  }
}
