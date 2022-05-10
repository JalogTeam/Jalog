// Ops.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;
import java.lang.reflect.Method;


// NOTE: Consider identifying predefined predicates in parser.


public class Ops
{
  private static class Name_Class {
    String name;
    Class pred_class;
    
    Name_Class(String name, Class pred_class) {
      this.name = name;
      this.pred_class = pred_class;
    }
  };

  static Name_Class[] built_in_preds = {
    new Name_Class("foreach_", Pred_foreach_.class),
    new Name_Class("exit", Pred_exit.class),
    new Name_Class("write", Pred_write.class),
    new Name_Class("writeln", Pred_writeln.class),
    new Name_Class("writeq", Pred_writeq.class),
    new Name_Class("cut_", Pred_cut_.class),
    new Name_Class("nl", Pred_writeln.class),
    new Name_Class("fail", Pred_fail.class),
    new Name_Class("consult", Pred_consult.class),
    new Name_Class("consult_dir", Pred_consult_dir.class),
    new Name_Class("dump_", Pred_dump_.class),
    new Name_Class("assertz", Pred_assertz.class),
    new Name_Class("not", Pred_not.class),
    new Name_Class("bound", Pred_bound.class),
    new Name_Class("free", Pred_free.class),
    new Name_Class("is_integer", Pred_is_integer.class),
    new Name_Class("is_real", Pred_is_real.class),
    new Name_Class("is_char", Pred_is_char.class),
    new Name_Class("is_string", Pred_is_string.class),
    new Name_Class("is_compound", Pred_is_compound.class),
    new Name_Class("is_list", Pred_is_list.class),
    new Name_Class("dynamic", Pred_dynamic.class),
    new Name_Class("=", Pred__eq_.class),
    new Name_Class(">", Pred__cmpr_.class),
    new Name_Class("<", Pred__cmpr_.class),
    new Name_Class(">=", Pred__cmpr_.class),
    new Name_Class("<=", Pred__cmpr_.class),
    new Name_Class("!=", Pred__cmpr_.class),
    new Name_Class("consult_data", Pred_consult_data.class),
    new Name_Class("concat", Pred_concat.class),
    new Name_Class("trap", Pred_trap.class),
    new Name_Class("substring", Pred_substring.class),
  };

  static Hashtable<String, Method> builtIns = 
      new Hashtable<String, Method>(100);
  static {
    try {
      for (int i = 0; i < built_in_preds.length; i++) {
        Name_Class built_in_pred = built_in_preds[i];
        builtIns.put(built_in_pred.name, built_in_pred.pred_class.
            getMethod("first_call", Pro_TermData_Compound.class));
      }
    } catch (Exception e) {
    }
  }
  
  static Pred first_call(Pro_Term pred_call)
  { // Entered always forward==true
// Debug_times.enter(3);
    Pred result = null;
if(!Pred.forward) System.out.println("*** Internal error: Ops.call, forward == false");

    Pro_TermData_Compound data = (Pro_TermData_Compound) pred_call.getData();
    String name = data.name;
    int arity = data.arity;
    boolean op_found = false;

    Method cur_pred_make_method;
    
    if(!(data instanceof Pro_TermData_Compound)){
      Pred.forward = false;
// Debug_times.leave(3);
      return result;
    }

// System.out.println("\n--Ops.call_forward: " + name + "/" + arity);

    cur_pred_make_method = builtIns.get(name);
    if (cur_pred_make_method != null) {
      op_found = true;
// System.out.println("*** cur_pred_make_method got");
      try {
        result = (Pred)cur_pred_make_method.invoke(null, data);
// System.out.println("*** cur_pred_make_method result " + result);
      } catch (Exception e) {
// System.out.println("*** cur_pred_make_method exception: " + e);
      }

    }
    
    if (!op_found) {
    // everything else

// System.out.println("  Ops.call_forward name: " + name);
// Debug_times.enter(2);
      result = new Pred_fetch_(pred_call);
// Debug_times.leave(2);
// System.out.println("  Ops.call_forward 3");
// System.out.println("  Ops.call_forward EXIT");
// Debug_times.leave(3);
      
    }

    return result;
  }
}
