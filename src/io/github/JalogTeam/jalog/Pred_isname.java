// Pred_isname.java
//
// isname(string StringParam) - (i)

package io.github.JalogTeam.jalog;

public class Pred_isname extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term string_param_param = params.subterm[0].getRealNode();

    Pro_TermData_String str;
    char c;
    String s;
    
    String string_param_param_type = string_param_param.getType();

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    // check types
    if (string_param_param_type == Typenames.STRING) {
      //  (i)
      // string_param_param is bound
      Pro_TermData_String s1 = 
          (Pro_TermData_String)string_param_param.getData();
      long len = s1.len;
      
      if (len > 0) {
        c = s1.charAt(0);
        
        forward = ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))
                  || (c == '_');
        
        for (long i = 1; (i < len) && forward; i++) {
          c = s1.charAt(i);
          forward = ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))
                  || ((c >= '0') && (c <= '9')) || (c == '_'); 
        }
      }
    }
    
    return null;
    
  }

}
