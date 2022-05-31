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
    int min_arity;
    int max_arity;
    
    Name_Class(String name, Class pred_class, int min_arity, int max_arity) {
      this.name = name;
      this.pred_class = pred_class;
      this.min_arity = min_arity;
      this.max_arity = max_arity;
    }
    
    Name_Class(String name, Class pred_class, int arity) {
      this(name, pred_class, arity, arity);
    }
    
    Name_Class(String name, Class pred_class) {
      this(name, pred_class, 0, Integer.MAX_VALUE);
    }
  };

  static Name_Class[] built_in_preds = {
    new Name_Class("foreach_", Pred_foreach_.class, 2),
    new Name_Class("exit", Pred_exit.class, 0, 1),
    new Name_Class("write", Pred_write.class),
    new Name_Class("writeln", Pred_writeln.class),
    new Name_Class("writeq", Pred_writeq.class),
    new Name_Class("cut_", Pred_cut_.class, 0),
    new Name_Class("nl", Pred_writeln.class, 0),
    new Name_Class("fail", Pred_fail.class, 0),
    new Name_Class("consult", Pred_consult.class, 1),
    new Name_Class("consult_dir", Pred_consult_dir.class, 1),
    new Name_Class("dump_", Pred_dump_.class, 1),
    new Name_Class("assertz", Pred_assertz.class, 1),
    new Name_Class("assert", Pred_assertz.class, 1),
    new Name_Class("not", Pred_not.class, 1),
    new Name_Class("bound", Pred_bound.class, 1),
    new Name_Class("free", Pred_free.class, 1),
    new Name_Class("is_integer", Pred_is_integer.class, 1),
    new Name_Class("is_real", Pred_is_real.class, 1),
    new Name_Class("is_char", Pred_is_char.class, 1),
    new Name_Class("is_string", Pred_is_string.class, 1),
    new Name_Class("is_compound", Pred_is_compound.class, 1),
    new Name_Class("is_list", Pred_is_list.class, 1),
    new Name_Class("dynamic", Pred_dynamic.class, 1),
    new Name_Class("=", Pred__eq_.class, 2),
    new Name_Class(">", Pred__cmpr_.class, 2),
    new Name_Class("<", Pred__cmpr_.class, 2),
    new Name_Class(">=", Pred__cmpr_.class, 2),
    new Name_Class("<=", Pred__cmpr_.class, 2),
    new Name_Class("!=", Pred__cmpr_.class, 2),
    new Name_Class("consult_data", Pred_consult_data.class, 2),
    new Name_Class("concat", Pred_concat.class, 3),
    new Name_Class("trap", Pred_trap.class, 3),
    new Name_Class("substring", Pred_substring.class, 4),
  };

  private static class BuiltInInfo {
    Method make_method;
    int min_arity;
    int max_arity;
  }

  static BuiltInInfo built_in_info;
  static Hashtable<String, BuiltInInfo> builtIns = 
      new Hashtable<String, BuiltInInfo>(100);
  static {
    try {
      for (int i = 0; i < built_in_preds.length; i++) {
        Name_Class built_in_pred = built_in_preds[i];
        built_in_info = new BuiltInInfo();
        built_in_info.make_method = built_in_pred.pred_class.
            getMethod("first_call", Pro_TermData_Compound.class);
        built_in_info.min_arity = built_in_pred.min_arity;
        built_in_info.max_arity = built_in_pred.max_arity;
        
        
        builtIns.put(built_in_pred.name, built_in_info);
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
    Pred.op_found = false;

    
    Method cur_pred_make_method;
    
    if(!(data instanceof Pro_TermData_Compound)){
      Pred.forward = false;
// Debug_times.leave(3);
      return result;
    }

// System.out.println("\n--Ops.call_forward: " + name + "/" + arity);

    built_in_info = builtIns.get(name);
    if (built_in_info != null) {
      if ((arity >= built_in_info.min_arity) && (arity <= built_in_info.max_arity)) {
        cur_pred_make_method = built_in_info.make_method;
        Pred.op_found = true;
// System.out.println("*** cur_pred_make_method got");
        try {
          result = (Pred)cur_pred_make_method.invoke(null, data);
// System.out.println("*** cur_pred_make_method result " + result);
        } catch (Exception e) {
// System.out.println("*** cur_pred_make_method exception: " + e);
        }
      }

    }
    
    if (!Pred.op_found) {
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
