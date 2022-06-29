// ClassInfo.java

package io.github.JalogTeam.jalog;

public class ClassInfo {
  String name;
  Class pred_class;
  int min_arity;
  int max_arity;
  
  ClassInfo(String name, Class pred_class, int min_arity, int max_arity) {
    this.name = name;
    this.pred_class = pred_class;
    this.min_arity = min_arity;
    this.max_arity = max_arity;
  }
  
  ClassInfo(String name, Class pred_class, int arity) {
    this(name, pred_class, arity, arity);
  }
  
  ClassInfo(String name, Class pred_class) {
    this(name, pred_class, 0, Integer.MAX_VALUE);
  }
}