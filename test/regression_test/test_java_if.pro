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

% compound
  
  