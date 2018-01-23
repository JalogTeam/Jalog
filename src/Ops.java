// Primitive_Ops.java
import java.io.*;
import java.util.Stack;

// NOTE: Consider identifying predefined predicates in parser.


public class Ops
{
  static Stack ConsultingFiles = new Stack();



  static Pred first_call(Pro_Term pred_call)
  { // Entered always forward==true
Debug_times.enter(3);
    Pred result = null;
if(!Pred.forward) System.out.println("*** Ops.call, forward == false");

    Pro_TermData_Compound data = (Pro_TermData_Compound) pred_call.getData();

    if(!(data instanceof Pro_TermData_Compound)){
      Pred.forward = false;
Debug_times.leave(3);
      return result;
    }

// System.out.println("\n--Ops.call_forward: " + data.name + "/" + data.arity);

    // cut_/0

    if(data.name.equals("cut_") && (data.arity == 0)){ 
// System.out.println("\n--Ops.first_call: cut");
Debug_times.enter(2);
      result = new Pred_cut_();
Debug_times.leave(2);

    // write/*

    } else if(data.name.equals("write")){ // handle all arities
      for(int i = 0; i < data.arity; i++){
        System.out.print(data.subterm[i].image());
      }

    // writeln/*

    } else if(data.name.equals("writeln")){ // handle all arities
      for(int i = 0; i < data.arity; i++){
        System.out.print(data.subterm[i].image());
      }
      System.out.println("");

    // writeq/*

    } else if(data.name.equals("writeq")){ // handle all arities
      for(int i = 0; i < data.arity; i++){
        System.out.print(data.subterm[i].toString());
      }

    // nl/0

    } else if(data.name.equals("nl") && (data.arity == 0)){ 
      System.out.println("");

    // =/2

    } else if(data.name.equals("=") && (data.arity == 2)){
Debug_times.enter(2);
      result = new Pred__eq_(data);
Debug_times.leave(2);
      result.call();
   
    // foreach_/2

    } else if(data.name.equals("foreach_") && (data.arity == 2)){ // foreach_(Variable, List) - (o,i)
Debug_times.enter(2);
      result = new Pred_foreach_(data);
Debug_times.leave(2);
      result.call();

   // consult/1

    } else if(data.name.equals("consult") && (data.arity == 1)){ // consult(String Filename) - (i)
      String Filename = data.subterm[0].image();
// System.out.print("\n--Consulting \"" + Filename + "\"--");
      int size = ConsultingFiles.size();
      boolean found = false;
      for(int i = 0; (i < size) && !found; i++){
        found = Filename.equals((String)ConsultingFiles.elementAt(i));
      }
      if(!found) {
// System.out.print(" starting.\n");
        ConsultingFiles.push(Filename);
        Consult.run(Filename);
        if(Consult.exit_value != null) { // bad file
          Pred.exception = true;
          Pred.exit_value = Consult.exit_value;
        }
        ConsultingFiles.pop();
// System.out.print("\n--Consulting \"" + Filename + "\"-- Finished\n");
      } else {
// System.out.print("\n--Consulting \"" + Filename + "\"-- Loop: Rejected!\n");
        Pred.forward = false;
      }

    // fail/0

    } else if(data.name.equals("fail") && (data.arity == 0)){
      Pred.forward = false;

    // dump/1

    } else if(data.name.equals("dump") && (data.arity == 1)){
      Pro_TermData data0 = data.subterm[0].getData();
      if(data0 instanceof Pro_TermData_String) {
        String filename = ((Pro_TermData_String)data0).value;
        Database.dump(filename);
      } else {
        Pred.forward = false;
      }

    // assertz/1

    } else if(data.name.equals("assertz") && (data.arity == 1)){
      Pro_Term term0 = data.subterm[0];
      if(term0.getData() instanceof Pro_TermData_Compound) {
        Database.assertz(term0);
      } else {
        Pred.forward = false;
      }

    // not/1

    } else if(data.name.equals("not") && (data.arity == 1)){
      Pro_Term[] items = {data.subterm[0]};
Debug_times.enter(2);
      result = new Pred_not();
Debug_times.leave(2);

      result.called_body = Pro_Term.m_list(items);

// TEMPORARY!
    // call/1

    } else if(data.name.equals("call") && (data.arity == 1)){
      Pro_Term[] items = {data.subterm[0]};
      result = new Pred();

      result.called_body = Pro_Term.m_list(items);

    } else if(data.name.equals("z_") && (data.arity == 0)){

      Pred.z_request = true;
      

// END TEMPORARY

    // bound/1

    } else if(data.name.equals("bound") && (data.arity == 1)){
      if(data.subterm[0].getData() != null) {
      } else {
        Pred.forward = false;
      }

    // ">"/2

    } else if(data.name.equals(">") && (data.arity == 2)){
Debug_times.enter(2);
      Pro_Term tmp1 = new Pro_Term();
      Pro_Term tmp2 = new Pro_Term();
Debug_times.leave(2);
      tmp1.compval(data.subterm[0]);
      tmp2.compval(data.subterm[1]);

      Pro_TermData data1 = tmp1.getData();
      Pro_TermData data2 = tmp2.getData();

      if((data1 instanceof Pro_TermData_Integer) &&
          (data2 instanceof Pro_TermData_Integer) &&
          (((Pro_TermData_Integer)data1).value >
          ((Pro_TermData_Integer)data2).value)){
      } else {
        Pred.forward = false;
      }

    // trap/3

    } else if(data.name.equals("trap") && (data.arity == 3)){
      Pro_Term[] items = {data.subterm[0]};
      Pro_Term[] catch_items = {data.subterm[2]};
Debug_times.enter(2);
      result = new Pred_trap();
Debug_times.leave(2);

      result.called_body = Pro_Term.m_list(items);
      ((Pred_trap)result).exit_var = data.subterm[1];
      ((Pred_trap)result).catch_body = Pro_Term.m_list(catch_items);

    // exit/1

    } else if(data.name.equals("exit") ){
      Pred.exception = true;
      Pred.exit_value = null;
      if(data.arity > 0) {
        Pro_TermData data1 = data.subterm[0].getData();
        if((data1 != null) && (data1 instanceof Pro_TermData_Integer)) {
          Pred.exit_value = data.subterm[0].getRealNode();
        }
      }
      if(Pred.exit_value == null) {
        Pred.exit_value = Pro_Term.m_integer(0);
      }


    // everything else

    } else {
// System.out.println("  Ops.call_forward data.name: " + data.name);
Debug_times.enter(2);
      result = new Pred_fetch_(pred_call);
Debug_times.leave(2);
// System.out.println("  Ops.call_forward 2 " + result);
//      result.call();
// System.out.println("  Ops.call_forward 3");
    }
// System.out.println("  Ops.call_forward EXIT");
Debug_times.leave(3);
    return result;
  }
}
