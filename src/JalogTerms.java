import java.util.Vector;
import java.util.Stack;
import java.util.Hashtable;

public class JalogTerms
{
  public int Error = 0;
  public int ErrorPos = 0; 
  public static final int ERROR_INTERNAL = 1;
  public static final int ERROR_SYNTAX = 2;
  public static final int ERROR_OVERFLOW = 3;
  public static final int CLAUSE = JalogSyntax.CLAUSE;
  public static final int TERM = JalogSyntax.TERM;

  private static Pro_Term[] Pro_Term_empty = new Pro_Term[0];
  
  private int iState;
  private JalogParser Pr1;
  private JalogTerms(){}
  // Laita privateiksi
    String action;
    Pro_Term T, term = null;
    Vector<Pro_Term> termList = new Vector<Pro_Term>();
    Stack<Vector<Pro_Term>> termList_stack = new Stack<Vector<Pro_Term>>();
    Stack<Pro_Term> term_stack = new Stack<Pro_Term>();
    Hashtable varSymTab = new Hashtable();
    Pro_TermData_Compound tC;
    Pro_TermData data;
    Pro_Term[] operands; 
    Pro_TermData_List new_last_data;
    Pro_Term old_last, new_last;
    Pro_Term tail = Pro_Term.EMPTY_LIST;
    String varname;
    String name;
    long ivalue;
    double rvalue;
    String svalue;
    boolean EOF = false;
    char ch;
  
  public JalogTerms(int initState)
  {
    iState = initState;
    
    Pr1 = new JalogParser(initState);
  }
 
  public Pro_Term NextPart()
  {
    
    Pro_Term result = null;
    
    do
    {
// // System.out.println("   ---");
      Pr1.advance();
      action = Pr1.action;
// System.out.println("action: " + action);

      if (action == JalogSyntax.SYM) {
        name = Pr1.sValue();

        // Normalize case to accommodate Turbo Prolog convention
        name = name.toLowerCase();
        
        term = 
            Pro_Term.m_compound(name,Pro_Term_empty);
// System.out.println("term: " + term);
      } else if (action == JalogSyntax.BGN_STRUCT) {
        name = Pr1.sValue();

        // Normalize case to accommodate Turbo Prolog convention
        name = name.toLowerCase();
        
        term = 
            Pro_Term.m_compound(name,Pro_Term_empty);
        termList_stack.push(termList);
// System.out.println("Push termList:" + termList.size());
        termList = new Vector();
        term_stack.push(term);
// System.out.println("Push term:" + term.Id);
        term = null;
      } else if (action == JalogSyntax.END_STRUCT) {
        term = term_stack.pop();
// System.out.println("Pop term:" + term.Id);
        tC = (Pro_TermData_Compound)term.getData();
        tC.subterm = termList.toArray(Pro_Term_empty);
        tC.arity = (byte)tC.subterm.length;
        termList = termList_stack.pop();
// System.out.println("Pop termList:" + termList.size());
      } else if (action == JalogSyntax.BGN_ARG) {
        
      } else if (action == JalogSyntax.END_ARG) {
        termList.add(term);

      } else if (action == JalogSyntax.BGN_CLAUSE) {
        varSymTab.clear();
      } else if (action == JalogSyntax.END_CLAUSE) {
        data = term.getData();
        if (( data instanceof Pro_TermData_Compound ) && (":-".equals(((Pro_TermData_Compound)data).name))) {
           if (((Pro_TermData_Compound)data).arity == 1) {
            operands = new Pro_Term[2];
            operands[0] = null;
            operands[1] = ((Pro_TermData_Compound)data).subterm[0];
            ((Pro_TermData_Compound)data).arity = 2;
            ((Pro_TermData_Compound)data).subterm = operands;
          }
       } else {
          operands = new Pro_Term[2];
          operands[0] = term;
          operands[1] = Pro_Term.EMPTY_LIST;
          term = Pro_Term.m_compound(":-",operands);
        }
//      System.out.println("term: " + term_out);


      } else if ((action == JalogSyntax.BGN_BODY) || 
          (action == JalogSyntax.BGN_LIST)) {
        // dummy for begin of subgoal list
        new_last_data = new Pro_TermData_List(null, null);
        new_last = new Pro_Term(new_last_data);              
        term_stack.push(new_last); // subgoal list begin pointer
        term_stack.push(new_last); // subgoal list end pointer
      
        
      } else if ((action == JalogSyntax.END_BODY) ||
          (action == JalogSyntax.END_LIST)){
        // pop old last 
        old_last = term_stack.pop();
        // link empty to old
        ((Pro_TermData_List)old_last.getData()).t2 = tail;
        tail = Pro_Term.EMPTY_LIST;
        // pop begin of list
        term = ((Pro_TermData_List)term_stack.pop().getData()).t2;
// System.out.println("term: " + term);

      } else if (action == JalogSyntax.BGN_TAIL) {
        
      } else if (action == JalogSyntax.END_TAIL) {
        tail = term;
      } else if ((action == JalogSyntax.BGN_ITEM) || 
          (action == JalogSyntax.BGN_ELEM)){
        
      } else if ((action == JalogSyntax.END_ITEM) || 
          (action == JalogSyntax.END_ELEM)){
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
        
        // Normalize case to accommodate Turbo Prolog convention
        varname = varname.toUpperCase();

        // System.out.println("Variable: " + " " + varname);
        term = (Pro_Term)varSymTab.get(varname);
        if (term == null) 
        {
          term = Pro_Term.m_open();
          varSymTab.put(varname, term);
        }
      } else if (action == JalogSyntax.OPEN) {
// System.out.println("Open: " + " " + Pr1.sValue());
        term = Pro_Term.m_open();
        
      } else if (action == JalogSyntax.INT) {
        ivalue = Pr1.iValue();
        if (ivalue < 0) {
          Error = ERROR_OVERFLOW;
          ErrorPos = Pr1.tokenPos + 1;
// System.out.println( "ERROR: negative iValue");
        }
        term = Pro_Term.m_integer(ivalue);
// System.out.println("Integer: " + term.Id + " " + ivalue);         
      } else if (action == JalogSyntax.REAL) {
        rvalue = Pr1.rValue();
        term = Pro_Term.m_real(rvalue);
System.out.println("Real: " + term.Id + " " + rvalue);         
      } else if (action == JalogSyntax.EMPTY_LIST) {
        term = Pro_Term.EMPTY_LIST;
// System.out.println("Empty list");         
      } else if (action == JalogSyntax.CHAR) {
        ivalue = Pr1.iValue();
        svalue = Pr1.sValue();
        term = Pro_Term.m_char((char)ivalue);
// System.out.println("Character: " + term.Id + " '" + svalue + "'");         
      } else if (action == JalogSyntax.BGN_STRING) {
      } else if (action == JalogSyntax.END_STRING) {
        svalue = Pr1.sValue();
        term = Pro_Term.m_string(svalue);
// System.out.println("String: " + term.Id + " '" + svalue + "'");  
 
      } else if (action == JalogSyntax.BGN_BINOP){
        svalue = Pr1.sValue();
        ch = svalue.charAt(0);
        if ((ch >= 'a') && (ch <= 'z')) svalue = svalue + '_';
        operands = new Pro_Term[2];
        operands[0] = term;
        term = 
            Pro_Term.m_compound(svalue,operands);
        term_stack.push(term);
// System.out.println("Push term:" + term.Id + ": " + operands[0].Id + " " + 
//    Pr1.sValue());
        term = null;
      } else if (action == JalogSyntax.END_BINOP){
        T = term;
        term = term_stack.pop();
// System.out.println("Pop term:" + term.Id);
        
        tC = (Pro_TermData_Compound)term.getData();
        tC.subterm[1] = T;
      } else if (action == JalogSyntax.BGN_UNOP) {
        operands = new Pro_Term[1];
        term = 
            Pro_Term.m_compound(Pr1.sValue(),operands);
        term_stack.push(term);
// System.out.println("Push term:" + term.Id + ": " + 
//    Pr1.sValue());
        term = null;
      } else if (action == JalogSyntax.END_UNOP) {
        T = term;
        term = term_stack.pop();
// System.out.println("Pop term:" + term.Id);
        
        tC = (Pro_TermData_Compound)term.getData();
        tC.subterm[0] = T;
      } else if (action == JalogSyntax.CUT) {
        term = Pro_Term.m_compound("cut_",new Pro_Term[0]);

      } else if (action == JalogSyntax.EOL) {
        result = null;
      } else if (action == JalogSyntax.COMPLETE) {
        Pr1.reset(iState);
        result = term;
        term = null;
//      } else if (action == JalogSyntax.DONE) {
//        result = null;
      } else if (action == JalogSyntax.ERR) {
        Error = ERROR_SYNTAX;
      } else {
        
        Error = ERROR_INTERNAL;
System.out.println("*** Unknown action: " + action);             
      }
      if (Error != 0) {
        action = Syntax.COMPLETE;
        if (ErrorPos == 0) {
          ErrorPos = Pr1.errPos + 1;
        }
      }
    } while(action != Syntax.EOL && action != Syntax.COMPLETE);
// System.out.println("nextPart: " + result);
    return result;
  }
 
  public void SetLine(String line)
  {
    Error = 0;
    ErrorPos = 0;
    Pr1.setLine(line);
// System.out.println("SetLine: " + line);
    EOF = (line == null);
  }

/* Replaced by SetLine(null)

  public void SetEOF()
  {
    sc.SetEOF();
// System.out.println("SetEOF");
  }
*/
}
