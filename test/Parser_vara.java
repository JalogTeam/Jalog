import java.io.*;
import java.util.Stack;

public class Parser
{

  private static final int NOT_FOUND = 0;
  private static final int TOKEN_FOUND = 1;
  private static final int CATCHALL_FOUND = 2;
  private static final int SUBSTATE_FOUND = 3;



  private int state;
  private Scanner sc;
  
// ACTION CODES
//  private static final int _action = ;
  private static final int int_term_action = 1;
  private static final int pop_term1_action = 2;
  





  private ParseTableEntry ParseTable[] = {
// state 0: end
    
// expr = expr1
      new ParseTableEntry(100,-1,200,101,0),
      new ParseTableEntry(101,-1,500,0,0),

// expr2
    new ParseTableEntry(200,-1,300,201,0),
    new ParseTableEntry(201,-1,600,0,0),

// expr3
    new ParseTableEntry(300,-1,400,301,0),
    new ParseTableEntry(301,-1,700,0,0),
    
// expr4
    new ParseTableEntry(400,Token.NAME,0,401,0),
    new ParseTableEntry(401,-1,1000,0,0), // !        // s_arglist = 1000 
    new ParseTableEntry(400,Token.MINUS,0,400,0), // !
    new ParseTableEntry(400,Token.LPAR,0,410,0),
    new ParseTableEntry(410,-1,100,411,0),
    new ParseTableEntry(411,Token.RPAR,0,0,0),
    new ParseTableEntry(400,Token.INT,0,0,int_term_action),
    new ParseTableEntry(400,Token.REAL,0,0,0),
    new ParseTableEntry(400,Token.CHAR,0,0,0),
    new ParseTableEntry(400,Token.LBRAK,0,420,0),
    new ParseTableEntry(420,-1,2000,421,0),  // exprlist = 2000
    new ParseTableEntry(421,-1,3000,422,0),    // tail = 3000
    new ParseTableEntry(422,Token.RBRAK,0,0,0),
    new ParseTableEntry(400,Token.CUT,0,0,0),
    new ParseTableEntry(400,Token.STRING,0,0,0),
    // 400 -> syntax error

// expr5
    new ParseTableEntry(500,Token.EQ,0,501,0),
    new ParseTableEntry(500,Token.NE,0,501,0),
    new ParseTableEntry(500,Token.GT,0,501,0),
    new ParseTableEntry(500,Token.GE,0,501,0),
    new ParseTableEntry(500,Token.LT,0,501,0),
    new ParseTableEntry(500,Token.LE,0,501,0),
    new ParseTableEntry(500,-1,0,0,0),
    new ParseTableEntry(501,-1,200,502,0),
    new ParseTableEntry(502,-1,500,0,0),
    
// expr6
    new ParseTableEntry(600,Token.PLUS,0,601,0),
    new ParseTableEntry(600,Token.MINUS,0,601,0),
    new ParseTableEntry(600,-1,0,0,0),
    new ParseTableEntry(601,-1,300,602,0),
    new ParseTableEntry(602,-1,600,0,0),
    
// expr7
    new ParseTableEntry(700,Token.STAR,0,701,0),
    new ParseTableEntry(700,Token.SLASH,0,701,0),
    new ParseTableEntry(700,Token.DIV_,0,701,0),
    new ParseTableEntry(700,Token.MOD_,0,701,0),
    new ParseTableEntry(700,-1,0,0,0),
    new ParseTableEntry(701,-1,400,702,0),
    new ParseTableEntry(702,-1,700,0,0),
    
// arglist
    new ParseTableEntry(1000,Token.LPAR,0,1001,0),
    new ParseTableEntry(1000,-1,0,0,0),        // catchall
    new ParseTableEntry(1001,-1,2000,1002,0),
    new ParseTableEntry(1002,Token.RPAR,0,0,0),  // expect RPAR

// exprlist
      new ParseTableEntry(2000,-1,100,2001,0),
      new ParseTableEntry(2000,-1,0,0,0),
      new ParseTableEntry(2001,-1,2100,0,0),
      
// exprlist1
      new ParseTableEntry(2100,Token.COMMA,0,2101,0),
      new ParseTableEntry(2100,-1,0,0,0),
      new ParseTableEntry(2101,-1,2000,0,0),
      
// tail
      new ParseTableEntry(3000,Token.VBAR,0,3001,0),
      new ParseTableEntry(3000,-1,0,0,0),
      new ParseTableEntry(3001,-1,100,0,0),
//    new ParseTableEntry(1,Token.,1,0,0),
//    new ParseTableEntry(1,Token.,1,0,0),
//    new ParseTableEntry(1,Token.,1,0,0),
  };
  
  public Pro_Term NextTerm()
  {
    Token T;
    Pro_Term result = null;
    StackItem currentItem = new StackItem();
    int found;
    
    if (sc != null)
    {
      Stack stack = new Stack();
      int nextI = 0;
      
      state = 100; // start of term
      T = sc.NextToken();              // NEXT TOKEN ****************
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
        
        if (found == TOKEN_FOUND || found == CATCHALL_FOUND)
        {
          // ACTION for token and catchall
          
          switch (ParseTable[nextI].action)
          {
            case int_term_action: 
              {
                currentItem.term = new Pro_TermData_Integer(T.iValue);
              } break;
          }
          //
          state = ParseTable[nextI].nextState;
          if (found == TOKEN_FOUND)
          {
            T = sc.NextToken();              // NEXT TOKEN ****************
          }
          while (state == 0 && !stack.empty())
          {

            StackItem stackItem = (StackItem)stack.pop();
            int i = stackItem.i;
            
            // ACTION for pop
          
            switch (ParseTable[i].action)
            {
              case pop_term1_action: 
                {
                  stackItem.term1 = currentItem.term_out;
                  
                } break;
            }
            currentItem = stackItem;
            
          
            state = ParseTable[i].nextState;
            System.out.println("Pop (" + i + ") SUCCESS ->" + state  );
            
          }
          if (state == 0)
          {
            result = Pro_Term.EMPTY_LIST;
          }
          nextI = 0;
        }
        else if (found == SUBSTATE_FOUND)
        {


          System.out.println("Push (" + nextI+ ") " + state + "->" + ParseTable[nextI].subState);


            // ACTION for push
          
            switch (ParseTable[nextI].action)
            {
              case push_term1_and_pop_term_out_action: 
                {
                  stackItem.term1 = currentItem.term_out;
                  
                } break;
            }
            currentItem = stackItem;




//          stack.push(new StackItem(nextI));
          currentItem.i = nextI;
          stack.push(currentItem);
          currentItem = new StackItem();
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
            System.out.println("Pop (" + nextI + ") FAIL ->" + state  );
            nextI += 1;
            
          }
          else
          {
            state = 0;  // exit
            result = null;
          }
          
        }
        
            
      
      } while(state != 0 );
    }
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
