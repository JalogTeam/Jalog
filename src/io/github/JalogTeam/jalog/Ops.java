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
    boolean op_found = true;
    
    Pro_TermData data1, data2, data3, data4;

    char Op;
    double R1 = 0.0, R2 = 0.0;
    long I1 = 0, I2 = 0;
    char T1, T2, Tv, C1 = ' ', C2 = ' ';
    String N1 = "", N2 = "";
    Pro_TermData_String S1 = null, S2 = null;
    boolean Bv = false;
    String filename;
    boolean found = false;
    Method cur_pred_make_method;
    
    if(!(data instanceof Pro_TermData_Compound)){
      Pred.forward = false;
// Debug_times.leave(3);
      return result;
    }

// System.out.println("\n--Ops.call_forward: " + name + "/" + arity);

    cur_pred_make_method = builtIns.get(name);
    if (cur_pred_make_method != null) {
// System.out.println("*** cur_pred_make_method got");
      try {
        result = (Pred)cur_pred_make_method.invoke(null, data);
// System.out.println("*** cur_pred_make_method result " + result);
      } catch (Exception e) {
// System.out.println("*** cur_pred_make_method exception: " + e);
      }

    } else {
      
      switch (arity) {

        case 1: {
          
          data1 = data.subterm[0].getData();

// BEGIN TEMPORARY

          // z_/1          
          if(name.equals("z_")){

            Pred.z_request = true;
            // result = new Pred(); // **
            

// END TEMPORARY

          } else {
            op_found = false;
          }
          
        } break;
        case 2: {

          // =/2

          if(name.equals("=")){
System.out.println("*** old =");
// Debug_times.enter(2);
            result = new Pred__eq_(data);
// Debug_times.leave(2);
            result.call();
         

          // consult_data/2

          } else if(name.equals("consult_data")){ 
              // consult(String filename) - (i)
            filename = data.subterm[0].image();
            Pro_Term filter_list = data.subterm[1];
            String[] filter = ((Pro_TermData_List)filter_list.data). 
                toStringList();
// System.out.println("\n--Consulting data \"" + filename + "\" filter[0]: " + filter[0]);
            Consult.consult_file(filename, filter);
//System.out.println("\n--Consulted data \"" + filename + "\"--");
            if(Consult.exit_value != null) { // bad file
              Pred.exception = true;
              Pred.exit_value = Consult.exit_value;
            }
            

          } else {
            op_found = false;
          }
        } break;
        case 3: {
          
          // trap/3

          if(name.equals("trap")){
            Pro_Term[] items = {data.subterm[0]};
            Pro_Term[] catch_items = {data.subterm[2]};
// Debug_times.enter(2);
            result = new Pred_trap();
// Debug_times.leave(2);

            result.called_body = Pro_Term.m_list(items);
            ((Pred_trap)result).exit_var = data.subterm[1];
            ((Pred_trap)result).catch_body = Pro_Term.m_list(catch_items);
          
          // concat/3

          } else if (name.equals("concat")){
            Pro_Term left_term = data.subterm[0].getRealNode();
            Pro_Term right_term = data.subterm[1].getRealNode();
            Pro_Term result_term = data.subterm[2].getRealNode();

            long len_left, len_right, len;
            Pro_TermData_Compound compare_data;
            Pro_Term right_part, left_part;
            
            if (left_term.data != null && right_term.data != null) {
              Pro_Term so = Pro_Term.m_string_concat(
                  (Pro_TermData_String)left_term.data, 
                  (Pro_TermData_String)right_term.data);
            
Pro_Term.debug = 0;
              result = new Pred__eq_(
                  new Pro_TermData_Compound("=", so, result_term));
              result.call();
Pro_Term.debug = 0;
            } else if (left_term.data != null && result_term.data != null) {
              len_left = ((Pro_TermData_String)left_term.data).len;
              len = ((Pro_TermData_String)result_term.data).len;
              left_part = Pro_Term.m_string_substring(
                  (Pro_TermData_String)result_term.data, 0, len_left);
              result = new Pred__eq_(
                  new Pro_TermData_Compound("=", left_term, left_part));
              result.call();
              if (Pred.forward) {
                right_part = Pro_Term.m_string_substring(
                    (Pro_TermData_String)result_term.data, len_left, 
                    len - len_left);
                result = new Pred__eq_(
                    new Pro_TermData_Compound("=", right_term, right_part));
                result.call();
              }
            } else if (right_term.data != null && result_term.data != null) {
              len_right = ((Pro_TermData_String)right_term.data).len;
              len = ((Pro_TermData_String)result_term.data).len;
              right_part = Pro_Term.m_string_substring(
                  (Pro_TermData_String)result_term.data, len - len_right, 
                  len_right);
              result = new Pred__eq_(
                  new Pro_TermData_Compound("=", right_term, right_part));
              result.call();
              if (Pred.forward) {
                left_part = Pro_Term.m_string_substring(
                    (Pro_TermData_String)result_term.data, 0, len - len_right);
                result = new Pred__eq_(
                    new Pro_TermData_Compound("=", left_term, left_part));
                result.call();
              }
            } else {
              Pred.forward = false;  // invalid flow pattern (o,o,o) (i,o,o),(o,i,o),(o,o,i)
            }
          } else {
            op_found = false;
          }
          
        } break;
        case 4: {

          data1 = data.subterm[0].getData();
          data2 = data.subterm[1].getData();
          data3 = data.subterm[2].getData();
          data4 = data.subterm[3].getData();
 
          // substring/4

          if(name.equals("substring")){
            // substring(Str_in,Pos,Len,Str_out)
            
// data1 must be string!
// ---------------------
/*            
System.out.println("** substring: " + 
"\n   data1=" + data1 + 
"\n   data2=" + data2 + 
"\n   data3=" + data3 + 
"\n   data4=" + data4); 
*/            
            if ( (data1 == null) || (data1.typename != Jalog.STRING) ) {
              Pred.forward = false;
            } else if ( data2 != null) {
            
              
              long pos = Pro_Term.eval_integer(data.subterm[1]);
              long len = Pro_Term.eval_integer(data.subterm[2]);
              
//            Pro_Term pos = data.subterm[1];
//            Pro_Term len = data.subterm[2];
              Pro_Term str_out = data.subterm[3];
   
              Pro_Term so = Pro_Term.m_string_substring(
                  (Pro_TermData_String)data1,
                  pos, len);
//                ((Pro_TermData_Integer)pos.data).value,
//                ((Pro_TermData_Integer)len.data).value);
              
              Pro_Term[] to_be_compared = {so, str_out};            
              Pro_TermData_Compound compare_data = 
                  new Pro_TermData_Compound("=", to_be_compared);
Pro_Term.debug = 0;
              result = new Pred__eq_(compare_data);
              result.call();
Pro_Term.debug = 0;

            } else {
              if (data4 instanceof Pro_TermData_String) {
                result = new Pred_substring(data);
                if(Pred.forward) result.call();
              }
            }
          } else {
             op_found = false;
          }
               
        } break;
        
        
        
        default: {
          op_found = false;
        } break;
        
      }
      
    }
    
    if (!op_found) {
    // everything else

// System.out.println("  Ops.call_forward name: " + name);
// Debug_times.enter(2);
      result = new Pred_fetch_(pred_call);
// Debug_times.leave(2);
// System.out.println("  Ops.call_forward 2 " + result);
//      result.call();
// System.out.println("  Ops.call_forward 3");
// System.out.println("  Ops.call_forward EXIT");
// Debug_times.leave(3);
      
    }

    return result;
  }
}
