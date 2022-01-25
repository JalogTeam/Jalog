package io.github.JalogTeam.jalog;

import java.io.*;

public class Test_String {
  
  public static void main(String args[]) {
    System.out.println("BEGIN String test");  
    Pro_TermData_String s = test_String_simple("jotakin pienta");
    System.out.println();  
    test_String_substring(s, 1, 12);
    System.out.println(); 
    Pro_TermData_String s1 = 
        new Pro_TermData_String_simple("jotakin pienta ");
    Pro_TermData_String s2 = 
        new Pro_TermData_String_simple("lisaa sellaista");
    
    test_String_concat(s1, s2);
    System.out.println("END String test");  
  }
  
  public static Pro_TermData_String test_String_simple(String img) {
    
    Pro_TermData_String_simple s;
    StringBuilder buffer;

    System.out.println("BEGIN String_simple test");  

    s = new Pro_TermData_String_simple(img);
    System.out.println("value = |" + s.value + "|");
    System.out.println("typename = " + s.typename);
    System.out.println("tag = " + s.tag);
    System.out.println("len = " + s.len);
    System.out.println("toString() = |" + s.toString() + "|");
    System.out.println("image() = |" + s.image() + "|");
    System.out.println("substring(1,3) = |" + s.substring(1,3) + "|");

    buffer = new StringBuilder(20);
    s.appendSubstring(buffer, 8, 4); // "pien"
    System.out.println("appendSubstring(buffer,8,4) -> |" + buffer.toString() + "|");
    s.appendSubstring(buffer, 4, 3); // "kin"
    System.out.println("appendSubstring(buffer,4,3) -> |" + buffer.toString() + "|");
    
    System.out.println("END String_simple test");  
    
    return s;
  }

  public static Pro_TermData_String test_String_substring(Pro_TermData_String s, int start, 
      int len) {
    StringBuilder buffer;
    Pro_TermData_String_substring ss;

    System.out.println("BEGIN String_substring test");  
    ss = new Pro_TermData_String_substring(s, start, len);
    System.out.println("base_string = |" + ss.base_string + "|");
    System.out.println("start = " + ss.start);
    System.out.println("typename = " + ss.typename);
    System.out.println("tag = " + ss.tag);
    System.out.println("len = " + ss.len);
    System.out.println("toString() = |" + ss.toString() + "|");
    System.out.println("image() = |" + ss.image() + "|");
    System.out.println("substring(" + start + ", " + len + ") = |" + 
        ss.substring(start,len) + "|");

    buffer = new StringBuilder(20);
    ss.appendSubstring(buffer, 7, 4); // "pien"
    System.out.println("appendSubstring(buffer,7,4) -> |" + 
        buffer.toString() + "|");
    ss.appendSubstring(buffer, 3, 3); // "kin"
    System.out.println("appendSubstring(buffer,3,3) -> |" + 
        buffer.toString() + "|");
    
    System.out.println("END String_substring test");  
    
    return ss;
  }

   
  public static Pro_TermData_String test_String_concat(Pro_TermData_String s1, 
      Pro_TermData_String s2) {
    StringBuilder buffer;
    Pro_TermData_String_concat sc;

    System.out.println("BEGIN String_concat test");  
    sc = new Pro_TermData_String_concat(s1, s2);
    System.out.println("left = |" + sc.left + "|");
    System.out.println("right = |" + sc.right + "|");
    System.out.println("typename = " + sc.typename);
    System.out.println("tag = " + sc.tag);
    System.out.println("len = " + sc.len);
    System.out.println("toString() = |" + sc.toString() + "|");
    System.out.println("image() = |" + sc.image() + "|");
    System.out.println("substring(8,12) = |" + sc.substring(8,12) + "|");

    buffer = new StringBuilder(20);
    sc.appendSubstring(buffer, 8, 12); // "pienta lisaa"
    System.out.println("appendSubstring(buffer,8, 12) -> |" + buffer.toString() + "|");
    sc.appendSubstring(buffer, 4, 3); // "kin"
    System.out.println("appendSubstring(buffer,4,3) -> |" + buffer.toString() + "|");
    
    System.out.println("END String_concat test");  
    
    return sc;
  }
}