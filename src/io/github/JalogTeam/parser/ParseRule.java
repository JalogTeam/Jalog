/* ParseRule.java */

package io.github.JalogTeam.parser;

public class ParseRule
{
  public final int state;
  public final String tokenType;
  public final String preAction;
  public final int subState;
  public final String postAction;
  public final int nextState;
  
  public ParseRule(int state, String tokenType, String preAction, int subState, 
      String postAction, int nextState)
  {
    this.state = state;
    this.tokenType = tokenType;
    this.preAction = preAction;
    this.subState = subState;
    this.postAction = postAction;
    this.nextState = nextState;
  }

}
