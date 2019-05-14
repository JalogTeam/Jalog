// test52.java
import java.io.*;

public class Test52
{
  static final Pro_Term[] empty_pro_term_array = new Pro_Term[0];

  public static void main(String args[])
  { 
     long[] X = new long[1];
     String from = "tampa";
     String to = "kansas_city";
     
     Jalog.consult("D:\\wa\\Jalog\\test\\sekalaisia\\program_52.pro");
     find_route(from, to, X);
     
     System.out.println("From " + from + " to " + to + " " + X[0] + " miles");
  }

  public static void find_route(String from, String to, long[] length_ret) {
    long length;
  
    Pro_Term from_v = Pro_Term.m_compound(from, empty_pro_term_array);
    Pro_Term to_v = Pro_Term.m_compound(to, empty_pro_term_array);
    Pro_Term len_v = Pro_Term.m_open();
    length_ret[0] = 0;
    try {
      if (Jalog.call("route", from_v, to_v, len_v))
        {
          Pro_TermData len_td = len_v.getData();
          if(len_td != null) {
            System.out.println("The class " +
                            " is " + len_td.getClass().getName());
          } else {
            System.out.println(" *** NULL *** ");
          }
          if (len_td instanceof Pro_TermData_Integer) {
            length = ((Pro_TermData_Integer)len_td).value;
            System.out.println("Success " + ((Pro_TermData_Integer)len_td).value);
            length_ret[0] = length;
          } else {
            System.out.println("Success, but wrong type");
          }
        } else {
          System.out.println("Fail");
        }
    } catch (Jalog.Exit e) {
      System.out.println("Error " + e.status);
    }
    
    
  }
  
}
