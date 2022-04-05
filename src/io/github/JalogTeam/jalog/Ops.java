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
  };

//  static private Stack ConsultedFiles = new Stack();
  static Hashtable ConsultedFiles = new Hashtable(100);

  static Hashtable<String, Method> builtIns = 
      new Hashtable<String, Method>(100);
  static {
    try {
      for (int i = 0; i < built_in_preds.length; i++) {
        Name_Class built_in_pred = built_in_preds[i];
        builtIns.put(built_in_pred.name, built_in_pred.pred_class.
            getMethod("make", Pro_TermData_Compound.class));
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

    // write/*

    } else if(name.equals("write")){ // handle all arities
      for(int i = 0; i < arity; i++){
//        System.out.print(data.subterm[i].image());
        Jalog.out.print(data.subterm[i].image());
      }
      // result = new Pred(); // **

    // writeln/*

    } else if(name.equals("writeln")){ // handle all arities
      for(int i = 0; i < arity; i++){
//        System.out.print(data.subterm[i].image());
        Jalog.out.print(data.subterm[i].image());
      }
//      System.out.println("");
      Jalog.out.println("");
      // result = new Pred(); // **

    // writeq/*

    } else if(name.equals("writeq")){ // handle all arities
      for(int i = 0; i < arity; i++){
//        System.out.print(data.subterm[i].toString());
        Jalog.out.print(data.subterm[i].toString());
      }
      // result = new Pred(); // **

    // exit/0, exit/1

    } else if(name.equals("exit") ){
      Pred.exception = true;
      Pred.exit_value = null;
      if(arity > 0) {
        Pred.exit_value = Pro_Term.m_integer(
            Pro_Term.eval_integer(data.subterm[0]));
      } else {
        Pred.exit_value = Pro_Term.m_integer(0);
      }
      
      // end of multi argument operations
      
    } else {
      
      switch (arity) {

        case 0: {

          // cut_/0

          if(name.equals("cut_") && (arity == 0)){ 
// System.out.println("\n--Ops.first_call: cut");
// Debug_times.enter(2);
            result = new Pred_cut_();
// Debug_times.leave(2);
          
          // nl/0

          } else if(name.equals("nl")){ 
//            System.out.println("");
            Jalog.out.println("");
            // result = new Pred(); // **
            
          // fail/0

          } else if(name.equals("fail")){
            Pred.forward = false;

          } else {
            op_found = false;
          }
          
        } break;
        case 1: {
          
          data1 = data.subterm[0].getData();
          
         // consult/1

          if(name.equals("consult")){ 
              // consult(String filename) - (i)
            filename = Consult.identify(data.subterm[0].image());
// System.out.print("\n--Consulting \"" + data.subterm[0].image() + " -> " + filename + "\"--");

            found = (ConsultedFiles.get(filename) != null);
/*
            int size = ConsultedFiles.size();
            boolean found = false;
            for(int i = 0; (i < size) && !found; i++){
              found = filename.equals((String)ConsultedFiles.elementAt(i));
            }
*/
            if(!found) {
// System.out.print(" starting.\n");
/*
              ConsultedFiles.push(filename);
*/
              ConsultedFiles.put(filename, "");
              Consult.consult_file(filename, null);
              if(Consult.exit_value != null) { // bad file
                Pred.exception = true;
                Pred.exit_value = Consult.exit_value;
              }
//              ConsultedFiles.pop(); No double consulting
// System.out.print("\n--Consulting \"" + filename + "\"-- Finished\n");
            }
            // result = new Pred(); // **
            
          // consult_dir/1

          } else if(name.equals("consult_dir")){
            if(data1 instanceof Pro_TermData_String_simple) {
              filename = ((Pro_TermData_String_simple)data1).value;
              Consult.set_consult_dir(filename);
            } else if(data1 == null) {
              filename = Consult.get_consult_dir(); 
              Pro_TrailMark mark = new Pro_TrailMark();
              data.subterm[0].unify(Pro_Term.m_string(filename), Pred.trail, mark);
            } else {
              Pred.forward = false;
            }

          // dump/1

          } else if(name.equals("dump")){
            if(data1 instanceof Pro_TermData_String_simple) {
              filename = ((Pro_TermData_String_simple)data1).value;
              Database.dump(filename);
            } else {
              Pred.forward = false;
            }
            // result = new Pred(); // **

          // assertz/1

          } else if(name.equals("assertz")){
            Pro_Term term0 = data.subterm[0];
            if(term0.getData() instanceof Pro_TermData_Compound) {
              Database.assertz(term0);
            } else {
              Pred.forward = false;
            }
            // result = new Pred(); // **

          // not/1

          } else if(name.equals("not")){
            Pro_Term[] items = {data.subterm[0]};
// Debug_times.enter(2);
            result = new Pred_not();
// Debug_times.leave(2);

            result.called_body = Pro_Term.m_list(items);

// TEMPORARY!
          // call/1

          } else if(name.equals("call")){
            Pro_Term[] items = {data.subterm[0]};
            result = new Pred();

            result.called_body = Pro_Term.m_list(items);

          } else if(name.equals("z_")){

            Pred.z_request = true;
            // result = new Pred(); // **
            

// END TEMPORARY

          // bound/1

          } else if(name.equals("bound")){
            if(data1 != null) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // free/1

          } else if(name.equals("free")){
            if(data1 == null) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }


          // is_integer/1

          } else if(name.equals("is_integer")){
            if((data1 != null) && (data1 instanceof Pro_TermData_Integer)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // is_real/1

          } else if(name.equals("is_real")){
            if((data1 != null) && (data1 instanceof Pro_TermData_Real)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // is_char/1

          } else if(name.equals("is_char")){
            if((data1 != null) && (data1 instanceof Pro_TermData_Char)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // is_string/1

          } else if(name.equals("is_string")){
            if((data1 != null) && (data1 instanceof Pro_TermData_String_simple)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // is_compound/1

          } else if(name.equals("is_compound")){
            if((data1 != null) && (data1 instanceof Pro_TermData_Compound)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // is_list/1

          } else if(name.equals("is_list")){
            if((data1 != null) && (data1 instanceof Pro_TermData_List)) {
              // result = new Pred(); // **
            } else {
              Pred.forward = false;
            }

          // dynamic/1

          } else if(name.equals("dynamic")){
            Pro_Term term0 = data.subterm[0];
            Pro_TermData arg = term0.getData();
            if(arg instanceof Pro_TermData_String_simple) {
              String key = ((Pro_TermData_String_simple)arg).value;
              Database.define_by_string(key);      
            } else {
              System.out.println("*** Error: dynamic: Argument must be a " +
                  "string containing functor/arity");
              Pred.forward = false;
            }
            // result = new Pred(); // **

          } else {
            op_found = false;
          }
          
        } break;
        case 2: {

          // =/2

          if(name.equals("=")){
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
            
          // ">"/2

          } else {
            Op = ' ';
            if(name.equals(">")) { Op = '>'; }
            if(name.equals("<")) { Op = '<'; }
            if(name.equals(">=")) { Op = 'G'; }
            if(name.equals("<=")) { Op = 'L'; }
            if(name.equals("!=")) { Op = 'N'; }
            
            if (Op != ' ') {
  // Debug_times.enter(2);
              Pro_Term tmp1 = new Pro_Term();
              Pro_Term tmp2 = new Pro_Term();
  // Debug_times.leave(2);
              tmp1.compval(data.subterm[0]);
              tmp2.compval(data.subterm[1]);

              data1 = tmp1.getData();
              data2 = tmp2.getData();

              T1 = ' ';
              T2 = ' ';
              Tv = ' ';
              if(data1 instanceof Pro_TermData_Integer){
                T1 = 'i';
                I1 = ((Pro_TermData_Integer)data1).value;
                Tv = 'i';
              } else if(data1 instanceof Pro_TermData_Real) {
                T1 = 'r';
                R1 = ((Pro_TermData_Real)data1).value;
                Tv = 'r';
              } else if(data1 instanceof Pro_TermData_Char) {
                T1 = 'c';
                C1 = ((Pro_TermData_Char)data1).value;
                Tv = 'c';
              } else if(data1 instanceof Pro_TermData_String) {
                T1 = 's';
                S1 = (Pro_TermData_String)data1;
                Tv = 's';
              } else if((data1 instanceof Pro_TermData_Compound) && 
                  (((Pro_TermData_Compound)data1).arity == 0)) {
                T1 = 'y';
                N1 = ((Pro_TermData_Compound)data1).name;
                Tv = 'y';
              }
              if(Tv != ' ') {
                if(data2 instanceof Pro_TermData_Integer){
                  T2 = 'i';
                  I2 = ((Pro_TermData_Integer)data2).value;
                  Tv = (T1 == 'i' ? 'i': T1 == 'r' ? 'r' : ' ');
                } else if(data2 instanceof Pro_TermData_Real) {
                  T2 = 'r';
                  R2 = ((Pro_TermData_Real)data2).value;
                  Tv = (T1 == 'i' ? 'r': T1 == 'r' ? 'r' : ' ');
                } else if(data2 instanceof Pro_TermData_Char) {
                  T2 = 'c';
                  C2 = ((Pro_TermData_Char)data2).value;
                  Tv = (T1 == 'c' ? 'c': ' ');
                } else if(data2 instanceof Pro_TermData_String) {
                  T2 = 's';
                  S2 = (Pro_TermData_String)data2;
                  Tv = (T1 == 's' ? 's': ' ');
                } else if(data2 instanceof Pro_TermData_Compound && 
                    (((Pro_TermData_Compound)data2).arity == 0)) {
                  T2 = 'y';
                  N2 = ((Pro_TermData_Compound)data2).name;
//                  Tv = (T1 == 'y' ? 's': ' ');
                  Tv = (T1 == 'y' ? 'y': ' ');
                } else {
                  Tv = ' ';
                }
                if(Tv != ' ') {
                  if(Tv == 'r') { // real comparison
                    if(T1 == 'i') {
                      R1 = I1;
                    }
                    if(T2 == 'i') {
                      R2 = I2;
                    }
                    switch(Op) {
                      case '>': Bv = (R1 > R2); break;
                      case '<': Bv = (R1 < R2); break;
                      case 'G': Bv = (R1 >= R2); break;
                      case 'L': Bv = (R1 <= R2); break;
                      case 'N': Bv = (R1 != R2); break;
                      default: Tv = ' '; // No result for others
                    }
                  } else if(Tv == 'i') { // integer comparison
                    switch(Op) {
                      case '>': Bv = (I1 > I2); break;
                      case '<': Bv = (I1 < I2); break;
                      case 'G': Bv = (I1 >= I2); break;
                      case 'L': Bv = (I1 <= I2); break;
                      case 'N': Bv = (I1 != I2); break;
                      default: Tv = ' '; // No result for others
                    }
                  } else if(Tv == 'c') { // character comparison
                    switch(Op) {
                      case '>': Bv = (C1 > C2); break;
                      case '<': Bv = (C1 < C2); break;
                      case 'G': Bv = (C1 >= C2); break;
                      case 'L': Bv = (C1 <= C2); break;
                      case 'N': Bv = (C1 != C2); break;
                      default: Tv = ' '; // No result for others
                    }
                  } else if(Tv == 's') { // string comparison
//                    I1 = S1.compareTo(S2);
                    I1 = Pro_TermData_String.compare_strings(S1, S2);
                    switch(Op) {
                      case '>': Bv = (I1 > 0); break;
                      case '<': Bv = (I1 < 0); break;
                      case 'G': Bv = (I1 >= 0); break;
                      case 'L': Bv = (I1 <= 0); break;
                      case 'N': Bv = (I1 != 0); break;
                      default: Tv = ' '; // No result for others
                    }
                  } else if(Tv == 'y') { // string comparison
                    I1 = N1.compareTo(N2);
                    switch(Op) {
                      case '>': Bv = (I1 > 0); break;
                      case '<': Bv = (I1 < 0); break;
                      case 'G': Bv = (I1 >= 0); break;
                      case 'L': Bv = (I1 <= 0); break;
                      case 'N': Bv = (I1 != 0); break;
                      default: Tv = ' '; // No result for others
                    }
                  }
                }
                
              }
              if (Tv != ' ') {
                Pred.forward = Bv;
              } else {
                Pred.forward = false;
              }
              if (Pred.forward) {
                // result = new Pred(); 
              }
            } else {
              op_found = false;
            }
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
