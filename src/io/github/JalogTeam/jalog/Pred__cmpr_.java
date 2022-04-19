// Pred__cmpr_.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred__cmpr_ extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1;
    Pro_TermData data2;
    String name = data.name;
    
    char Op = ' ';
    double R1 = 0.0, R2 = 0.0;
    long I1 = 0, I2 = 0;
    char T1, T2, Tv, C1 = ' ', C2 = ' ';
    String N1 = "", N2 = "";
    Pro_TermData_String S1 = null, S2 = null;
    boolean Bv = false;
    
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
      Pred.forward = false;
      System.err.println("\n*** Internal error (cmpr)");
    }
    return null;
  }

}
