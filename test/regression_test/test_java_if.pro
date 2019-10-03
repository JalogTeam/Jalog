% integer
test_int(5, 0, "NOK?"). % open?
test_int(7, 9, "OK").
test_int(X, 0, "NOK").

% symbol
test_symbol(trap, is_open, "NOK?").
test_symbol(test, good, "OK").
test_symbol(X, ugly, "NOK").

% real
test_real(2.718, 0.0, "NOK?"). % open?
test_real(3.142, 1.414, "OK").
test_real(X, 0.0, "NOK").

% character
test_char('x', '@', "NOK?").
test_char('y', 'z', "OK").
test_char(X, '#', "NOK").


% string
test_string("whatever", "is open", "NOK?").
test_string("template", "match", "OK").
test_string(X, "fallback", "NOK").

% list
test_list([3, 4], [1, 2], "Virhe!").
test_list([3], [2, 9], "Virhe2!").
test_list([3, 5, 11], [3, 9], "Virhe3!").
test_list([3, 5], [7, 9], "Jep").
test_list(X, [100], "Catch").

% compound
test_compound(a(3, 4), c(1, 2), "Virhe!").
test_compound(a(3), d(2, 9), "Virhe2!").
test_compound(a(3, 5, 11), e(3, 9), "Virhe3!").
test_compound(a(3, 5), b(7, 9), "Jep").
test_compound(X, f(100), "Catch").

% open
test_open(X, Y, "open") :- free(X),!.
test_open(X, 1, "bound").

% exit
test_exit :- exit(42).

% command line arguments 
test_command_line_arguments :-
  comline_arg(N, Label, Value),
  writeln("comline_arg(", N, ", \"", Label, "\", \"", Value, "\")."),
  fail.
test_command_line_arguments.
  