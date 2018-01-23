// Inference.java
import java.io.*;

public class Inference // extends Pred
{
  static int Debug = 0;
  static int level = 0;
   
  public Pro_Term exit_value = null;

    Activation current;
    Pro_TrailMark Mark;

  Inference()
  {
      Mark = new Pro_TrailMark();
  }

  public void run_body(Pro_Term body)
  {
Debug_times.enter(1); 
if(Debug > 0) System.out.println("*** BEGIN Inference: body=" + body);
//    if(level > 0) System.out.println("** BEGIN Inference: recursion level=" + level);
    level ++;
    Pro_Term new_body_item;
    Pred new_pred;
    boolean ready = false;
if(Debug > 0) System.out.println("\n Inference (i): ready = " + ready);
    Activation trap_activation = null;

    exit_value = null;

// * set trail mark
    if(Pred.forward) {
      Pred.trail.mark(Mark); 
        current = new Activation();
if(Debug > 0) System.out.println("\n Inference 1: new Activation " + current);
        current.next = body;
        
    } else {
//      System.out.println("\n Inference: Recall");
    }

// * kunnes body loppuu tai ensimmainen feilaa
    while(current != null) {
if(Debug > 0) System.out.println("\nInference while: current = " + current);

      if(current.pcall != null) {
if(Debug > 0)        System.out.println("\nInference A: " + Pred.forward +", "+ 
            ((Pro_TermData_List)current.pcall.body_item.getData()).t1);
      } else {
if(Debug > 0)        System.out.println("\nInference A: " + Pred.forward +", (null)");
      }
      Pro_TermData_List new_body_item_data; 
      ready = false;
if(Debug > 0) System.out.println("\n Inference (j): ready = " + ready);
Debug_times.enter(4); 

      if(Pred.forward) {
        Pred.cutting = false;
        new_body_item = current.next;
if(Debug > 0) System.out.println("\nInference: new_body_item = " + new_body_item + " cutting = false");
// At end of clause we have new_body_item = empty list
        if(new_body_item != null) {
          new_body_item_data = (Pro_TermData_List) new_body_item.getData();
          if((new_body_item_data != null) && 
              (new_body_item_data.t1 != null)) {
if(Debug > 0) System.out.println("\nInference B: first_call " + new_body_item_data.t1);

            new_pred = Ops.first_call(new_body_item_data.t1);
// TEMPORARY!
            if(Pred.z_request) {
              Pred.z_request = false;
              System.out.print("<" + current + ">");
            }
            if(new_pred != null) {  // **
              new_pred.body_item = new_body_item;
              new_pred.prev = current.pcall;
            }

            if(Pred.forward) {
              current.next = new_body_item_data.t2; // rest of clause
if(Debug > 0)        System.out.println("\n Inference (c): " + current + ".next = " + current.next);
              if(new_pred != null) {
                current.pcall = new_pred;
if(Debug > 0)        System.out.println("\n Inference (b): " + current + ".pcall = " + current.pcall);
                if(new_pred instanceof Pred_trap) {
                  ((Pred_trap)new_pred).prev_trap_activation = trap_activation;
                  trap_activation = current;
                }
              }
            }
          } else { // end of clause
            ready = true;
if(Debug > 0) System.out.println("\n Inference (k): ready = " + ready);
          }
        } else { // end of clause
          ready = true;
if(Debug > 0) System.out.println("\n Inference (l): ready = " + ready);
        }
      } else { // backward
if(Debug > 0)        System.out.println("\n Inference: backtracking");
        if(current.pcall != null) {
if(Debug > 0) System.out.println("\nInference C: ");
          // We can recall to previous body item
          if(current.pcall.sub_activation != null) {
if(Debug > 0) System.out.println("\nInference D: ");

            current = current.pcall.sub_activation;
if(Debug > 0) System.out.println("\n Inference 2: current = " + current);
            
          } else {
if(Debug > 0) System.out.println("\nInference E: ");
          
            current.pcall.call(); // recall, forward == false
            if(!Pred.forward) {
              current.next = current.pcall.body_item;
              current.pcall = current.pcall.prev;
if(Debug > 0)        System.out.println("\n Inference (d): " + current + ".next = " + current.next);
if(Debug > 0)        System.out.println("\n Inference (d): " + current + ".pcall = " + current.pcall);
            }
          }

        } else { // begin of clause
          ready = true;
if(Debug > 0) System.out.println("\n Inference (m): ready = " + ready);
        }
      }
Debug_times.leave(4); 

      if (Pred.exception) {
        Pred.forward = false;
        ready = true;

if(Debug > 0) System.out.println("\n Inference (n): ready = " + ready);
      }
if(Debug > 0)        System.out.println("\n Inference (e): " + current + ".next = " + current.next);
if(Debug > 0)        System.out.println("\n Inference (e): " + current + ".pcall = " + current.pcall);
Debug_times.enter(5); 

      if ( Pred.forward) { // succeeded
Debug_times.enter(6); 
        if ( ready ) {
          current = current.up;
if(Debug > 0) System.out.println("\n Inference 3: current = " + current);
          if(current != null) {
if(Debug > 0) System.out.println("\n Inference: post_call " + 
((Pro_TermData_List)current.pcall.body_item.getData()).t1);
            current.pcall.post_call();
          }

        } else {
if(Debug > 0)
{
System.out.println("\nInference (a) current = " + current);
if(current.pcall == null) System.out.println("\nInference (a) current.pcall == null, next=" + current.next);
}
          if((current.pcall == null) || (current.pcall.called_body == null)) { /* Here crashes ****/
            if((current.pcall != null) && (current.pcall.cut)) {
if(Debug > 0) System.out.println("\n Inference: cut passed");
              current.pcall.prev = null; // no backtracking behind this point
              // Pred.cutting is assumed to have been set
              // Can be optimized, specially tail recursion

            } else {          // normal primitive
              // should be ready
if(Debug > 0)              System.out.println("\n Inference: primitive called");
            }

          } else {               // constructed
Debug_times.enter(2);
            Activation new_activation = new Activation();
Debug_times.leave(2);
if(Debug > 0)              System.out.println("\n Inference: constructed called");
            current.pcall.sub_activation = new_activation;
            new_activation.up = current;
            new_activation.next = current.pcall.called_body;
            current = new_activation;
if(Debug > 0) System.out.println("\n Inference 4: current = " + current);
            
          }  
        }
Debug_times.leave(6); 
      } else { // failed
Debug_times.enter(7); 
        if ( ready ) {
          if(!Pred.exception) {
            current = current.up;
if(Debug > 0)        System.out.println("\n Inference (g): " + current + ".next = " + current.next);
if(Debug > 0)        System.out.println("\n Inference (g): " + current + ".pcall = " + current.pcall);
          } else {
            current = trap_activation;
if(Debug > 0)        System.out.println("\n Inference (h): " + current );
            if(current != null) {
if(Debug > 0)        System.out.println("\n Inference (h): " + current + ".next = " + current.next);
if(Debug > 0)        System.out.println("\n Inference (h): " + current + ".pcall = " + current.pcall);
              trap_activation = ((Pred_trap)current.pcall).prev_trap_activation;
            } else {
//              System.out.println("\nUnhandled exception " + Pred.exit_value.image());
              exit_value = Pred.exit_value;
            }
          }
if(Debug > 0) System.out.println("\n Inference 5: current = " + current);
          if(current != null) {
if(Debug > 0) System.out.println("\n Inference: post_call " + 
((Pro_TermData_List)current.pcall.body_item.getData()).t1);
Debug_times.enter(10); 
            current.pcall.post_call();
Debug_times.leave(10); 
            if(Pred.forward) {
Debug_times.enter(8); 
              if(current.pcall.called_body != null ) {
Debug_times.enter(2);
                Activation new_activation = new Activation();
Debug_times.leave(2);
if(Debug > 0)                System.out.println("\n Inference: constructed called 2 ");
                current.pcall.sub_activation = new_activation;
                new_activation.up = current;
                new_activation.next = current.pcall.called_body;
                current = new_activation;
if(Debug > 0) System.out.println("\n Inference 6: current = " + current);
              
              }
Debug_times.leave(8); 

            } else {
Debug_times.enter(9); 
              if(current.pcall.called_body != null ) {
Debug_times.enter(2);
                Activation new_activation = new Activation();
Debug_times.leave(2);
if(Debug > 0)                System.out.println("\n Inference: constructed called 3 ");
                current.pcall.sub_activation = new_activation;
                new_activation.up = current;
                new_activation.next = current.pcall.called_body;
                current = new_activation;
if(Debug > 0) System.out.println("\n Inference 7: current = " + current);
              

              } else {
                current.next = current.pcall.body_item;
                current.pcall = current.pcall.prev;
if(Debug > 0)        System.out.println("\n Inference (f): " + current + ".next = " + current.next);
if(Debug > 0)        System.out.println("\n Inference (f): " + current + ".pcall = " + current.pcall);
              }
Debug_times.leave(9); 
            }

          }
        }
Debug_times.leave(7); 

      }
Debug_times.leave(5); 

    }
    level --;
//    if(level > 0) System.out.println("** END Inference: recursion level=" + level);
Debug_times.leave(1);
Debug_times.report();
  }
}

