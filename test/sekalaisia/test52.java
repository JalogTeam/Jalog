// test52.java
import java.io.*;

public class Test52
{
  static Jalog j = new Jalog();

  public static void main(String args[])
  { 
     long[] X = new long[1];
     String from = "tampa";
     String to = "kansas_city";
     
     j.consult_file("D:\\wa\\Jalog\\test\\sekalaisia\\program_52.pro");
     find_route(from, to, X);
     
     System.out.println("From " + from + " to " + to + " " + X[0] + " miles");
     
     j.dispose();
  }

  public static void find_route(String from, String to, long[] length_ret) {
    long length;
  
    Jalog.Term from_v = Jalog.symbol(from);
    Jalog.Term to_v = Jalog.symbol(to);
    Jalog.Term len_v = Jalog.open();
    String len_type;
    
    length_ret[0] = 0;
    try {
      if (j.call("route", from_v, to_v, len_v))
        {
          len_type = len_v.getType();
          if (len_type == Jalog.INTEGER) {
            length = len_v.getIntegerValue();
            System.out.println("Success " + length);
            length_ret[0] = length;
          } else {
            System.out.println("Success, but wrong type: " + len_type);
          }
        } else {
          System.out.println("Fail");
        }
    } catch (Jalog.Exit e) {
      System.out.println("Error " + e.status);
    }
    
    
  }
  
}
