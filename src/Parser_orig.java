//T: import java.io.*;
import java.util.Stack;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Locale;

public class Parser
{
  public static final int CLAUSE = 4000;
  public static final int TERM = 100;

  private static final int NOT_FOUND = 0;
  private static final int TOKEN_FOUND = 1;
  private static final int CATCHALL_FOUND = 2;
  private static final int SUBSTATE_FOUND = 3;



  private int state;
  private Scanner sc;
  
// ACTION CODES
//  private static final int _action = ;
  private static final int int_term_action = 1;
  private static final int pop_to_ti_action = 2;
  private static final int push_ti_ti_action = 3;
  private static final int pop_to_to_action = 4;
  private static final int plus_binary_action = 5;
  private static final int pop_to_op2_action = 6;
  private static final int push_t1_ti_action = 7;
  private static final int ti_to_action = 8;
  private static final int minus_binary_action = 9;
  private static final int mult_binary_action = 10;
  private static final int div_binary_action = 11;
  private static final int idiv_binary_action = 12;
  private static final int imod_binary_action = 13;
  private static final int empty_xlist_action = 14;
  private static final int pop_list_prepend_action = 15;
  private static final int pop_list_action = 16;
  private static final int pop_to_t1_action = 17;
  private static final int name_term_action = 18;
  private static final int pop_list_args_action = 19;
  private static final int char_term_action = 20;
  private static final int str_term_action = 21;
  private static final int list_action = 22;
  private static final int empty_list_action = 23;
  private static final int real_term_action = 24;
  private static final int varname_term_action = 25;
  private static final int binary_action = 26;
  private static final int clause_action = 27;
  private static final int empty_list_t1_action = 28;



  private ParseTableEntry ParseTable[] = {
// state 0: end
    
// expr = expr1
      new ParseTableEntry(100,-1,0,200,pop_to_ti_action,101),
      new ParseTableEntry(101,-1,push_ti_ti_action,500,pop_to_to_action,0),

// expr2
    new ParseTableEntry(200,-1,0,300,pop_to_ti_action,201),
    new ParseTableEntry(201,-1,push_ti_ti_action,600,pop_to_to_action,0),

// expr3
    new ParseTableEntry(300,-1,0,400,pop_to_ti_action,301),
    new ParseTableEntry(301,-1,push_ti_ti_action,700,pop_to_to_action,0),
    
// expr4
    new ParseTableEntry(400,Token.NAME,name_term_action,0,0,401),
    new ParseTableEntry(400,Token.VARNAME,varname_term_action,0,0,0),
    new ParseTableEntry(401,-1,push_t1_ti_action,1000,pop_to_to_action,0), // !        // s_arglist = 1000 
    new ParseTableEntry(400,Token.MINUS,0,0,0,400), // !
    new ParseTableEntry(400,Token.LPAR,0,0,0,410),
    new ParseTableEntry(410,-1,0,100,pop_to_to_action,411),
    new ParseTableEntry(411,Token.RPAR,0,0,0,0),
    new ParseTableEntry(400,Token.INT,int_term_action,0,0,0),
    new ParseTableEntry(400,Token.REAL,real_term_action,0,0,0),
    new ParseTableEntry(400,Token.CHAR,char_term_action,0,0,0),
    new ParseTableEntry(400,Token.LBRAK,0,0,0,420),
    new ParseTableEntry(420,-1,0,2000,pop_list_action,421),  // exprlist = 2000
    new ParseTableEntry(421,-1,0,3000,pop_to_t1_action,422),    // tail = 3000
    new ParseTableEntry(422,Token.RBRAK,list_action,0,0,0),
    new ParseTableEntry(400,Token.CUT,0,0,0,0),
    new ParseTableEntry(400,Token.STRING,str_term_action,0,0,0),
    // 400 -> syntax error

// expr5
    new ParseTableEntry(500,Token.EQ,binary_action,0,0,501),
    new ParseTableEntry(500,Token.NE,binary_action,0,0,501),
    new ParseTableEntry(500,Token.GT,binary_action,0,0,501),
    new ParseTableEntry(500,Token.GE,binary_action,0,0,501),
    new ParseTableEntry(500,Token.LT,binary_action,0,0,501),
    new ParseTableEntry(500,Token.LE,binary_action,0,0,501),
    new ParseTableEntry(500,-1,ti_to_action,0,0,0),
    new ParseTableEntry(501,-1,0,200,pop_to_op2_action,502),
    new ParseTableEntry(502,-1,push_t1_ti_action,500,pop_to_to_action,0),
    
// expr6
//    new ParseTableEntry(600,Token.PLUS,plus_binary_action,0,0,601),
    new ParseTableEntry(600,Token.PLUS,binary_action,0,0,601),
    new ParseTableEntry(600,Token.MINUS,minus_binary_action,0,0,601),
    new ParseTableEntry(600,-1,ti_to_action,0,0,0),
    new ParseTableEntry(601,-1,0,300,pop_to_op2_action,602),
    new ParseTableEntry(602,-1,push_t1_ti_action,600,pop_to_to_action,0),
    
// expr7
    new ParseTableEntry(700,Token.STAR,mult_binary_action,0,0,701),
    new ParseTableEntry(700,Token.SLASH,div_binary_action,0,0,701),
    new ParseTableEntry(700,Token.DIV_,idiv_binary_action,0,0,701),
    new ParseTableEntry(700,Token.MOD_,imod_binary_action,0,0,701),
    new ParseTableEntry(700,-1,ti_to_action,0,0,0),        
    new ParseTableEntry(701,-1,0,400,pop_to_op2_action,702),
    new ParseTableEntry(702,-1,push_t1_ti_action,700,pop_to_to_action,0),
    
// arglist
    new ParseTableEntry(1000,Token.LPAR,0,0,0,1001),
    new ParseTableEntry(1000,-1,ti_to_action,0,0,0),        // catchall
    new ParseTableEntry(1001,-1,0,2000,pop_list_args_action,1002),
    new ParseTableEntry(1002,Token.RPAR,0,0,0,0),  // expect RPAR

// exprlist
    new ParseTableEntry(2000,-1,0,100,pop_to_t1_action,2001),
    new ParseTableEntry(2000,-1,empty_xlist_action,0,0,0),
    new ParseTableEntry(2001,-1,0,2100,pop_list_prepend_action,0),
      
// exprlist1
    new ParseTableEntry(2100,Token.COMMA,0,0,0,2101),
    new ParseTableEntry(2100,-1,empty_xlist_action,0,0,0),
    new ParseTableEntry(2101,-1,0,2000,pop_list_action,0),
      
// tail
    new ParseTableEntry(3000,Token.VBAR,0,0,0,3001),
    new ParseTableEntry(3000,-1,empty_list_action,0,0,0),
    new ParseTableEntry(3001,-1,0,100,pop_to_to_action,0),
//    new ParseTableEntry(1,Token.,1,0,0),
//    new ParseTableEntry(1,Token.,1,0),
//    new ParseTableEntry(1,Token.,1,0,0),

// clause
//    new ParseTableEntry(4000,Token.IFSYM,0,4100,pop_to_to_action,4004),  
    new ParseTableEntry(4000,Token.IFSYM,0,0,0,4005),  
    new ParseTableEntry(4005,-1,0,4100,pop_to_to_action,4004),  
    new ParseTableEntry(4000,-1,0,100,pop_to_t1_action,4001),  // expr = 100 for head
    new ParseTableEntry(4001,Token.IFSYM,0,0,0,4005),  
//    new ParseTableEntry(4001,Token.IFSYM,0,4100,pop_to_to_action,4004),
    new ParseTableEntry(4001,Token.POINT,empty_list_action,0,0,4004), 
//    new ParseTableEntry(4002,-1,0,2000,pop_list_action,4003),  // exprlist = 2000 for body
//    new ParseTableEntry(4003,Token.POINT,list_action,0,0,4004), 
    new ParseTableEntry(4004,-1,clause_action,0,0,0), 

// clause1
    new ParseTableEntry(4100,-1,0,2000,pop_list_action,4102),  // exprlist = 2000 for body
    new ParseTableEntry(4102,Token.POINT,empty_list_t1_action,0,0,4103), 
    new ParseTableEntry(4103,-1,list_action,0,0,0), 

    

  };
  
  private int currentState;
  private Stack stack;
  private StackItem currentItem;
  private StackItem subItem;
  private Hashtable symTab;

  Parser(int startState)
  {
    currentState = startState;
    stack = new Stack();
    currentItem = new StackItem();
    subItem = new StackItem();
    symTab = new Hashtable();
  }
 
  public Pro_Term NextPart()
  {
    Token T;
    Pro_Term result = null;
    int found;
    int startState = currentState;
    if (sc != null)
    {
      int nextI = 0;
      
      state = startState; // start of part
      T = sc.NextToken();              // NEXT TOKEN ****************
   //T: System.out.println("T: " + T);
      nextI = 0;
      do // base level
      {
        
        found = NOT_FOUND;
        for (int i = nextI ; i < ParseTable.length && found == NOT_FOUND; i++)
        {
          if(state == ParseTable[i].state)
          {

            if (T.tokenType == ParseTable[i].tokenType)
            {                                       //  token found
              found = TOKEN_FOUND;
              nextI = i;
              
            }
            else if ((ParseTable[i].tokenType == -1) &&
                (ParseTable[i].subState == 0))
            {                                       // catchall
              found = CATCHALL_FOUND;
              nextI = i;
              
            }
            else if (ParseTable[i].subState != 0)
            {                                       // substate
              found = SUBSTATE_FOUND;
              nextI = i;
              
            }
            
          }
        }
        //T: System.out.println("nextI: " + nextI);
        
        if (found != NOT_FOUND)
        {
          // PRE ACTION
        //T: System.out.println("ParseTable[nextI].state: " + ParseTable[nextI].state);
        //T: System.out.println("ParseTable[nextI].preAction: " + ParseTable[nextI].preAction);
          
          switch (ParseTable[nextI].preAction)
          {
            case int_term_action: 
              {
                currentItem.term_out = Pro_Term.m_integer(T.iValue);
//                //T: System.out.println("PRE int_term_action: " + currentItem.term_out);
              } break;
            case real_term_action: 
              {
                currentItem.term_out = Pro_Term.m_real(T.rValue);
//                //T: System.out.println("PRE real_term_action: " + currentItem.term_out);
              } break;
            case char_term_action: 
              {
                currentItem.term_out = Pro_Term.m_char(T.sValue.charAt(0));
//                //T: System.out.println("PRE int_char_action: " + currentItem.term_out);
              } break;
            case str_term_action: 
              {
                currentItem.term_out = Pro_Term.m_string(T.sValue);
//                //T: System.out.println("PRE str_term_action: " + currentItem.term_out);
              } break;
            case push_ti_ti_action:
              {
                subItem.term_in = currentItem.term_in;
                //T: System.out.println("PRE push_ti_ti_action: " + currentItem.term_in);
              } break;
            case plus_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("+",operands);
//                //T: System.out.println("PRE plus_binary_action: " + currentItem.term1);
              } break;
            case binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound(T.sValue,operands);
                //T: System.out.println("PRE binary_action: " + T.sValue + " " + currentItem.term1);
              } break;
            case push_t1_ti_action:
              {
                subItem.term_in = currentItem.term1;
                //T: System.out.println("PRE push_t1_ti_action: " + currentItem.term1);
              } break;
            case ti_to_action:
              {
                currentItem.term_out = currentItem.term_in;
                //T: System.out.println("PRE ti_to_action: " + currentItem.term_in);
              } break;
            case minus_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("-",operands);
//                //T: System.out.println("PRE minus_binary_action: " + currentItem.term1);
              } break;
            case mult_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("*",operands);
//                //T: System.out.println("PRE mult_binary_action: " + currentItem.term1);
              } break;
            case div_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("/",operands);
//                //T: System.out.println("PRE div_binary_action: " + currentItem.term1);
              } break;
            case idiv_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("div",operands);
//                //T: System.out.println("PRE idiv_binary_action: " + currentItem.term1);
              } break;
            case imod_binary_action:
              {
                Pro_Term[] operands = {currentItem.term_in,null};
                currentItem.term1 = Pro_Term.m_compound("mod",operands);
//                //T: System.out.println("PRE imod_binary_action: " + currentItem.term1);
              } break;
            case empty_xlist_action:
              {
                currentItem.termList = new Vector();
                //T: System.out.println("PRE empty_xlist_action");
              } break;
            case name_term_action:
              {
                currentItem.term1 = Pro_Term.m_compound(T.sValue,new Pro_Term[0]);
                //T: System.out.println("PRE name_term_action: " + currentItem.term1);
              } break;
            case list_action:
              {
                Pro_Term[] items;
                int arity;
                
                arity = currentItem.termList.size();
//T: System.out.println("PRE list_action 1");
                items = new Pro_Term[arity];
//T: System.out.println("PRE list_action 2");
                for (int k = 0; k < arity; k++)
                {
//T: System.out.println("PRE list_action 3." + k);
                  items[k] = (Pro_Term)currentItem.termList.elementAt(k);
                }
//T: System.out.println("PRE list_action 4");
//T: System.out.println("currentItem.term1= " + currentItem.term1);
//T: System.out.println("items.length= " + items.length);
                currentItem.term_out = Pro_Term.m_list(items, currentItem.term1);
//T: System.out.println("PRE list_action 5");
                //T: System.out.println("PRE list_action: " + currentItem.term_out);
              } break;
            case empty_list_action:
              {
                currentItem.term_out = Pro_Term.EMPTY_LIST;
                //T: System.out.println("PRE empty_list_action: " + currentItem.term_out);
              } break;
            case empty_list_t1_action:
              {
                currentItem.term1 = Pro_Term.EMPTY_LIST;
                //T: System.out.println("PRE empty_list_t1_action: " + currentItem.term1);
              } break;
            case varname_term_action: 
              {
                //T: System.out.println("PRE varname_term_action: " + T.sValue);
                if (T.sValue.equals("_"))
                {
                  //T: System.out.println("... varname_term_action: Open");
                  currentItem.term_out = Pro_Term.m_open();
                }
                else
                {
                  String normName = T.sValue.toLowerCase(Locale.US);
                  currentItem.term_out = (Pro_Term)symTab.get(normName);
                  //T: System.out.println("... varname_term_action: Variable");
                
                  if (currentItem.term_out == null) 
                  {
                    currentItem.term_out = Pro_Term.m_open();
                    symTab.put(normName, currentItem.term_out);
                  }
                }
                //T: System.out.println("PRE varname_term_action: " + currentItem.term_out);
              } break;
            case clause_action:
              {
                //T: System.out.println("PRE clause_action BEGIN: ");
                //T: System.out.println("currentItem: " + currentItem);
                Pro_Term[] operands = {currentItem.term1,currentItem.term_out};
                //T: System.out.println("currentItem.term1: " + currentItem.term1);
                //T: System.out.println("currentItem.term_out: " + currentItem.term_out);
               // //T: System.out.println("operands: " + operands);
                
                currentItem.term_out = Pro_Term.m_compound(":-",operands);
//T: System.out.println("   ----x");
//T: if(currentItem == null) {System.out.println("currentItem == null");}
//T: else if(currentItem.term_out == null) {System.out.println("currentItem.term_out == null");}
//T: System.out.println("Class of currentItem.term_out.data: " + currentItem.term_out.data.getClass().getName());
                //T: System.out.println("PRE clause_action: " + currentItem.term_out);
              } break;
      }
          
          //
          
        }
        
        if (found == TOKEN_FOUND || found == CATCHALL_FOUND)
        {
          // ACTION for token and catchall
          //

          state = ParseTable[nextI].nextState;
          //T: System.out.println("state = " + state);
          if (found == TOKEN_FOUND)
          {
            T = sc.NextToken();              // NEXT TOKEN ****************
            //T: System.out.println("T = " + T);
          }
          while (state == 0 && !stack.empty())
          {
            subItem = currentItem;
            currentItem = (StackItem)stack.pop();
            int i = currentItem.i;
            
            // ACTION for pop
          
            switch (ParseTable[i].postAction)
            {
              case pop_to_ti_action: 
                {
                  currentItem.term_in = subItem.term_out;
                  //T: System.out.println("POST pop_to_ti_action: " + currentItem.term_in);
                } break;
              case pop_to_to_action: 
                {
                  currentItem.term_out = subItem.term_out;
                  //T: System.out.println("POST pop_to_to_action: " + currentItem.term_out);
                } break;
              case pop_to_op2_action:
                {
                  ((Pro_TermData_Compound)currentItem.term1.data).subterm[1] = subItem.term_out;
                  //T: System.out.println("POST pop_to_op2_action: " + currentItem.term1);
                } break;
              case pop_list_prepend_action:
                {
                  currentItem.termList = subItem.termList;
                  currentItem.termList.add(0,currentItem.term1);
                  //T: System.out.println("POST pop_list_prepend_action: " + currentItem.termList.size());
                } break;
              case pop_list_action:
                {
                  currentItem.termList = subItem.termList;
                  //T: System.out.println("POST pop_list_action: " + currentItem.termList.size());
                } break;
              case pop_to_t1_action:
                {
                  currentItem.term1 = subItem.term_out;
                  //T: System.out.println("POST pop_to_t1_action: " + currentItem.term1);
                } break;

              case pop_list_args_action:
                {
                  Pro_TermData_Compound tC = (Pro_TermData_Compound)currentItem.term_in.data;
                  if(subItem.termList.size() > 255)
                  {
                    //T: System.out.println("*** Error: arity > 255");
                  }
                  tC.arity = (byte)subItem.termList.size();
                  tC.subterm = new Pro_Term[tC.arity];
                  for (int k = 0; k < tC.arity; k++)
                  {
                    tC.subterm[k] = (Pro_Term)subItem.termList.elementAt(k);
                  }
                  currentItem.term_out = currentItem.term_in;
                  //T: System.out.println("POST pop_list_args_action: " + currentItem.term_out);
                } break;
              }
            
          
            state = ParseTable[i].nextState;
            //T: System.out.println("Pop (" + i + ") SUCCESS ->" + state  );
            
          }
          if (state == 0)
          {
            //T: System.out.println("EXIT: " + currentItem.term_out + " " +
            //T:     subItem.term_out);
            result = currentItem.term_out;
          }
          nextI = 0;
        }
        else if (found == SUBSTATE_FOUND)
        {
          
          //T: System.out.println("Push (" + nextI+ ") " + state + "->" + ParseTable[nextI].subState);

          currentItem.i = nextI;
          stack.push(currentItem);
          currentItem = subItem;
          subItem = new StackItem();
          
          state = ParseTable[nextI].subState;
          nextI = 0;
     
        }
        else
        {                       // fail
          
          if(!stack.empty())
          {
            StackItem stackItem = (StackItem)stack.pop();
            nextI = stackItem.i;
            state = ParseTable[nextI].state;
            //T: System.out.println("Pop (" + nextI + ") FAIL ->" + state  );
            nextI += 1;
            
          }
          else
          {
            state = 0;  // exit
            //T: System.out.println("FAIL: " + currentItem.term_out + " " +
            //T:     subItem.term_out);
            
            result = null;
          }
          
        }
        
            
      
      } while(state != 0 );
    }
    currentState = startState;
    return result;
  }
 
  public void SetString(String str)
  {
    sc = new Scanner();
    sc.SetString(str);
  }
  
  public void skipWhitespace()
  {
  }
}
