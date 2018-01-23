public class ParseTableEntry
{
  public int state;
  public int tokenType;
  public int subState;
  public int nextState;
  public int action;
  
  public ParseTableEntry(int State, int TokenType, int SubState, int NextState, int Action)
  {
    state = State;
    tokenType = TokenType;
    subState = SubState;
    nextState = NextState;
    action = Action;
  }
}
