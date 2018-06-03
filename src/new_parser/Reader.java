// Reader.java
import java.io.*;
import java.util.Stack;
import java.util.Vector;
import java.util.Hashtable;

public class Reader
{
  static Pro_Term exit_value = null;
  static Pro_Term[] Pro_Term_empty = new Pro_Term[0];

  static void run(String FileName)
  {
    String action;
    Stack<Pro_Term> term_stack = new Stack<Pro_Term>();
    Pro_Term T, term = null, term_out;
    Stack<Vector<Pro_Term>> termList_stack = new Stack<Vector<Pro_Term>>();
    Vector<Pro_Term> termList = new Vector<Pro_Term>();
    Hashtable varSymTab = new Hashtable();
    String varname;
    Pro_Term[] Apu = new Pro_Term[10];
    Pro_Term[] operands; 
    Pro_Term old_last, new_last;
    Pro_TermData_List new_last_data;
    long ivalue;

    exit_value = null;
    JalogParser Pr1 = new JalogParser(JalogSyntax.CLAUSES);
    int ApuCnt = 0;
    String line;
    RandomAccessFile file1;
    int LineNmbr = 0;
    Pro_TermData_Compound tC;

    /*try*/ {
    
//      System.out.println("Consulting " + FileName);
      try {
        file1 = new RandomAccessFile(FileName,"r");
      } catch (Exception e) {
        System.out.println("*** Error: " + e);
        file1 = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      if(file1 != null) {
        do {
          try {
            line = file1.readLine();
            LineNmbr = LineNmbr + 1;
          } catch (Exception e) {
            System.out.println("*** Error: " + e);
            line = null;
          }
  // System.out.println("");
   System.out.println("Line: " + line);
          Pr1.setLine(line);
          
          do
          {
  // System.out.println("   ---");
            Pr1.advance();
            action = Pr1.action;
     System.out.println("action: " + action);
     
            if (action == JalogSyntax.SYM) {
              term = 
                  Pro_Term.m_compound(Pr1.sValue(),Pro_Term_empty);
     System.out.println("term: " + term);
            } else if (action == JalogSyntax.BGN_STRUCT) {
              term = 
                  Pro_Term.m_compound(Pr1.sValue(),Pro_Term_empty);
              termList_stack.push(termList);
System.out.println("Push termList:" + termList.size());
              termList = new Vector();
              term_stack.push(term);
System.out.println("Push term:" + term.Id);
              term = null;
            } else if (action == JalogSyntax.END_STRUCT) {
              term = term_stack.pop();
System.out.println("Pop term:" + term.Id);
              tC = (Pro_TermData_Compound)term.getData();
              tC.subterm = termList.toArray(Pro_Term_empty);
              tC.arity = (byte)tC.subterm.length;
              termList = termList_stack.pop();
System.out.println("Pop termList:" + termList.size());
            } else if (action == JalogSyntax.BGN_ARG) {
              
            } else if (action == JalogSyntax.END_ARG) {
              termList.add(term);

            } else if (action == JalogSyntax.BGN_CLAUSE) {
              varSymTab.clear();
            } else if (action == JalogSyntax.END_CLAUSE) {
              operands = new Pro_Term[2];
              operands[0] = term;
              operands[1] = Pro_Term.EMPTY_LIST;
              term_out = Pro_Term.m_compound(":-",operands);
     System.out.println("term: " + term_out);

     
            } else if (action == JalogSyntax.BGN_BODY) {
              // dummy for begin of subgoal list
              new_last_data = new Pro_TermData_List(null, null);
              new_last = new Pro_Term(new_last_data);              
              term_stack.push(new_last); // subgoal list begin pointer
              term_stack.push(new_last); // subgoal list end pointer
            
              
            } else if (action == JalogSyntax.END_BODY) {
              // pop old last 
              old_last = term_stack.pop();
              // link empty to old
              ((Pro_TermData_List)old_last.getData()).t2 = Pro_Term.EMPTY_LIST;
              // pop begin of list
              term = ((Pro_TermData_List)term_stack.pop().getData()).t2;
/*
            operands = new Pro_Term[2];
              operands[0] = term;
              operands[1] = Pro_Term.EMPTY_LIST;
              term_out = Pro_Term.m_compound(":-",operands);
*/
     System.out.println("term: " + term);

            } else if (action == JalogSyntax.BGN_ITEM) {
              
            } else if (action == JalogSyntax.END_ITEM) {
              // create new list term item
              new_last_data = new Pro_TermData_List(term, null);
              new_last = new Pro_Term(new_last_data);
              // pop old last 
              old_last = term_stack.pop();
              // link new to old
              ((Pro_TermData_List)old_last.getData()).t2 = new_last;
              // push new last
              term_stack.push(new_last);
            } else if (action == JalogSyntax.VARIABLE) {
              varname = Pr1.sValue();
              if (varname.equals("_")) {
                term = Pro_Term.m_open();
              } else {
                term = (Pro_Term)varSymTab.get(varname);
                if (term == null) 
                {
                  term = Pro_Term.m_open();
                  varSymTab.put(varname, term);
                }
                
              }
              
              
              
            } else if (action == JalogSyntax.INT) {
              ivalue = Pr1.iValue();
              if (ivalue < 0) {
                System.out.println( "ERROR: negative iValue");
              }
              term = Pro_Term.m_integer(ivalue);
System.out.println("Integer: " + term.Id + " " + ivalue);         
            } else if (action == JalogSyntax.BGN_BINOP){
              operands = new Pro_Term[2];
              operands[0] = term;
              term = 
                  Pro_Term.m_compound(Pr1.sValue(),operands);
              term_stack.push(term);
System.out.println("Push term:" + term.Id + ": " + operands[0].Id + " " + 
    Pr1.sValue());
              term = null;
            } else if (action == JalogSyntax.END_BINOP){
              T = term;
              term = term_stack.pop();
System.out.println("Pop term:" + term.Id);
              
              tC = (Pro_TermData_Compound)term.getData();
              tC.subterm[1] = T;
            } else if (action == JalogSyntax.BGN_UNOP) {
              operands = new Pro_Term[1];
              term = 
                  Pro_Term.m_compound(Pr1.sValue(),operands);
              term_stack.push(term);
System.out.println("Push term:" + term.Id + ": " + 
    Pr1.sValue());
              term = null;
            } else if (action == JalogSyntax.END_UNOP) {
              T = term;
              term = term_stack.pop();
System.out.println("Pop term:" + term.Id);
              
              tC = (Pro_TermData_Compound)term.getData();
              tC.subterm[0] = T;
            }

  /*
            T = Pr1.NextPart();
            if(Pr1.Error != 0)
            {
              exit_value = Pro_Term.m_integer(1); // Syntax error
              System.err.println("*** Error in file " + FileName + " Line: " + LineNmbr + " Pos: " + Pr1.ErrorPos);
              System.err.println("    " + line);
              for(int i = -3; i < Pr1.ErrorPos; i++) {
                System.err.print(" ");
              }
              System.err.println("^");

              T = null;
              line = null;
            } else {
//System.out.println("   Term: " + T);
//System.out.println("");
              process_clause(T);
              if(exit_value != null) {
                T = null;
                line = null;
              }
            }
            if (ApuCnt < 9) {
              Apu[ApuCnt] = T;
              ApuCnt++;
            }
*/            
          } while(action != Syntax.EOL && action != Syntax.COMPLETE);
        } while (line != null);
      }
//System.out.println("Consulted");
    
/*    } catch (Exception e) {
      System.out.println(e); */
    }
/*
    int I;
    for (I = 0; I < ApuCnt; I++) {
      System.out.println("Term[" + I + "]:" + Apu[I]);
    }
*/
  }

  private static void process_clause(Pro_Term T){
    
System.out.println("\n--Reader: process_clause:" + T );
  }
}
