// Ops.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;
import java.lang.reflect.Method;


// NOTE: Consider identifying predefined predicates in parser.


public class Ops
{
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
      for (int i = 0; i < BuiltIns.list.length; i++) {
        ClassInfo built_in_pred = BuiltIns.list[i];
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
