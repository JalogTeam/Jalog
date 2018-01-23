public class ScanTableEntry
{
  public int state;
  public char fst;
  public char lst;
  public int tokenType;
  public int nextState;
  public int action;
  
  public ScanTableEntry(int State, char Fst, char Lst, int TokenType, int NextState, int Action)
  {
    state = State;
    fst = Fst;
    lst = Lst;
    tokenType = TokenType;
    nextState = NextState;
    action = Action;
  }
}
