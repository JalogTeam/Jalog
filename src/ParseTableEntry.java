public class ParseTableEntry
{
  public int state;
  public int tokenType;
  public int subState;
  public int nextState;
  public int preAction;
  public int postAction;
  
  public ParseTableEntry(int State, int TokenType, int PreAction, int SubState, 
      int PostAction, int NextState)
  {
    state = State;
    tokenType = TokenType;
    subState = SubState;
    nextState = NextState;
    preAction = PreAction;
    postAction = PostAction;
  }
}
