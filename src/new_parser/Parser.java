/* Parser.java */

public abstract class Parser
{
  /* basic usage:
        Parser p = new A_subclass_of_Parser(args_if_needed);
        while (p.action != Syntax.EOF && not all parsed) {
          if (p.tokenType == Syntax.EOL) {
            String line = next_input_line_or_null_if_no_more_lines(from_source);
            p.setLine(line);
          }
          p.advance();
          process_token_according_to(p.tokenType, other_variables);
        }
     this pattern can be varied according to particular needs;
     e.g., it is possible to terminate when p.action == Syntax.ERR or
     an unexpected action for current situation;
  */

  public String action = Syntax.EOL;

  public abstract void setLine(String line);
      // call with null when no more lines

  public abstract void moveTo(int nextPos);

  public abstract void advance();

}
