// Pred_str_int.java
//
// str_int(string StringParam, integer I) - (i,o), (i,i), (o,i)

package io.github.JalogTeam.jalog;

public class Pred_str_int extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term string_param_param = params.subterm[0].getRealNode();
    Pro_Term int_param_param = params.subterm[1].getRealNode();

    Pro_TermData_String str;
    char c;
    String s;
    
    String string_param_param_type = string_param_param.getType();
    String int_param_param_type = int_param_param.getType();

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    // check types
    if (string_param_param_type == Typenames.STRING) {
// System.err.println("\n*** 8 *** " );

      //  (i)
      // string_param_param is bound
      Pro_TermData_String s1 = 
          (Pro_TermData_String)string_param_param.getData();
      long len = s1.len;
      
      if (len < 21) {
// System.err.println("\n*** 9 *** len = " + len);
//        s = s1.toString();
        s = s1.image();
        try {
// System.err.println("\n*** 10 *** s = |" + s + "|");
          long value = Long.parseLong(s);
// System.err.println("\n*** 11 *** s = |" + s + "| value = " + value);
          Pro_Term value_term = Pro_Term.m_integer(value);

// System.err.println("\n*** 12 *** value_term = " + value_term + 
// "| int_param_param = " + int_param_param);
          
          forward = int_param_param.unify(value_term, trail);
// System.err.println("\n*** 13 *** forward = " + forward + " value_term = " + value_term + 
// "| int_param_param = " + int_param_param);
        }
        catch (Exception e) {
        }
      }
    } else if (int_param_param_type == Typenames.INTEGER) {
      long i = ((Pro_TermData_Integer)(int_param_param.data)).value;

      s = Long.toString(i);
      Pro_Term s_term = Pro_Term.m_string(s);
      forward = string_param_param.unify(s_term, trail);
    }
    
    return null;
    
  }

}
