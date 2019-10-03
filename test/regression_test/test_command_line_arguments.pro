% command line arguments 
test_command_line_arguments :-
  comline_arg(N, Label, Value),
  writeln("comline_arg(", N, ", \"", Label, "\", \"", Value, "\")."),
  fail.
test_command_line_arguments.

:- writeln("test_command_line_arguments"),
  test_command_line_arguments,
  writeln("done").
