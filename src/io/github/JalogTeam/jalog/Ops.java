// Primitive_Ops.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.Stack;

// NOTE: Consider identifying predefined predicates in parser.


public class Ops
{
  static Stack ConsultingFiles = new Stack();



  static Pred first_call(Pro_Term pred_call)
  { // Entered always forward==true
// Debug_times.enter(3);
    Pred result = null;
if(!Pred.forward) System.out.println("*** Internal error: Ops.call, forward == false");

    Pro_TermData_Compound data = (Pro_TermData_Compound) pred_call.getData();
    String name = data.name;
    int arity = data.arity;
    boolean op_found = true;
    
    Pro_TermData data1, data2, data3;

    char Op;
    double R1 = 0.0, R2 = 0.0;
    long I1 = 0, I2 = 0;
    char T1, T2, Tv, C1 = ' ', C2 = ' ';
    String S1 = "", S2 = "";
    boolean Bv = false;
    String filename;
    
    if(!(data instanceof Pro_TermData_Compound)){
      Pred.forward = false;
// Debug_times.leave(3);
      return result;
    }

// System.out.println("\n--Ops.call_forward: " + name + "/" + arity);

    // write/*

    if(name.equals("write")){ // handle all arities
      for(int i = 0; i < arity; i++){
        System.out.print(data.subterm[i].image());
      }
      // result = new Pred(); // **

    // writeln/*

    } else if(name.equals("writeln")){ // handle all arities
      for(int i = 0; i < arity; i++){
        System.out.print(data.subterm[i].image());
      }
      System.out.println("");
      // result = new Pred(); // **

    // writeq/*

    } else if(name.equals("writeq")){ // handle all arities
      for(int i = 0; i < arity; i++){
        System.out.print(data.subterm[i].toString());
      }
      // result = new Pred(); // **

    // exit/0, exit/1

    } else if(name.equals("exit") ){
      Pred.exception = true;
      Pred.exit_value = null;
      if(arity > 0) {
        data1 = data.subterm[0].getData();
        if((data1 != null) && (data1 instanceof Pro_TermData_Integer)) {
          Pred.exit_value = data.subterm[0].getRealNode();
        }
      }
      if(Pred.exit_value == null) {
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
            System.out.println("");
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
            filename = data.subterm[0].image();
// System.out.print("\n--Consulting \"" + filename + "\"--");
            int size = ConsultingFiles.size();
            boolean found = false;
            for(int i = 0; (i < size) && !found; i++){
              found = filename.equals((String)ConsultingFiles.elementAt(i));
            }
            if(!found) {
// System.out.print(" starting.\n");
              ConsultingFiles.push(filename);
              Consult.run(filename);
              if(Consult.exit_value != null) { // bad file
                Pred.exception = true;
                Pred.exit_value = Consult.exit_value;
              }
              ConsultingFiles.pop();
// System.out.print("\n--Consulting \"" + filename + "\"-- Finished\n");
            } else {
// System.out.print("\n--Consulting \"" + filename + "\"-- Loop: Rejected!\n");
              Pred.forward = false;
            }
            // result = new Pred(); // **
            
          // dump/1

          } else if(name.equals("dump")){
            if(data1 instanceof Pro_TermData_String) {
              filename = ((Pro_TermData_String)data1).value;
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
            if((data1 != null) && (data1 instanceof Pro_TermData_String)) {
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
            if(arg instanceof Pro_TermData_String) {
              String key = ((Pro_TermData_String)arg).value;
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
         
          // foreach_/2

          } else if(name.equals("foreach_")){
              // foreach_(Variable, List) - (o,i)
// Debug_times.enter(2);
            result = new Pred_foreach_(data);
// Debug_times.leave(2);
            result.call();
            
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
                S1 = ((Pro_TermData_String)data1).value;
                Tv = 's';
              } else if((data1 instanceof Pro_TermData_Compound) && 
                  (((Pro_TermData_Compound)data1).arity == 0)) {
                T1 = 'y';
                S1 = ((Pro_TermData_Compound)data1).name;
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
                  S2 = ((Pro_TermData_String)data2).value;
                  Tv = (T1 == 's' ? 's': ' ');
                } else if(data2 instanceof Pro_TermData_Compound && 
                    (((Pro_TermData_Compound)data2).arity == 0)) {
                  T2 = 'y';
                  S2 = ((Pro_TermData_Compound)data2).name;
                  Tv = (T1 == 'y' ? 's': ' ');
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
                    I1 = S1.compareTo(S2);
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
