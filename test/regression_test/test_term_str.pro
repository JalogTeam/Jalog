% term_str - test

:- writeln("alku").
% :- debug_(1).

test_io(T, V, S) :- write("io ", T, " "), writeq(V), term_str(T, V, S), !, 
    write(" -> "), writeq(S), nl.
test_io(_, _, _) :- writeln(" conversion failed.").

test_oi(T, V, S) :- write("oi ", T, " "), term_str(T, V, S), !, writeq(V), 
    write(" <- "), writeq(S), nl.
test_oi(_,_, S) :- write(" ? <- "), writeq(S), writeln(" conversion failed.").

test_ii(T, V, S) :- write("ii ", T, " "), writeq(V), write(" <-> "), writeq(S), 
    term_str(T, V, S), !, writeln(" true").
test_ii(_,_, _) :- writeln(" false.").

% :- debug_(0).
:- test_io(string, "", _).

:- test_io(string, "-", _).

:- test_io(string, "+", _).

:- test_io(string, " ", _).

:- test_io(integer, 0, _).

:- test_io(integer, 5, _).

:- test_io(integer, +5, _).

:- test_io(integer, -5, _).

:- test_io(struct, a(3,"k"), _).

:- test_io(list, [3,2,4], _).

:- test_io(char, 'c', _).

:- test_io(real, 3.141, _).

:- test_io(symbol, abc, _).

:- test_io(symbol, abc(), _).


:- test_oi(string, _, "\"\""). %

:- test_oi(string, _, "\"-\""). %

:- test_oi(string, _, "\"+\""). %

:- test_oi(string, _, "\" \""). %

:- test_oi(integer, _, "0").

:- test_oi(integer, _, "5").

:- test_oi(integer, _, "+5"). %

:- test_oi(integer, _, "-5"). %

:- test_oi(struct, _, "a(3,\"k\")").

:- test_oi(list, _, "[3,2,4]").

:- test_oi(char, _, "'c'").

:- test_oi(real, _, "3.141").

:- test_oi(symbol, _, "abc").

:- test_oi(symbol, _, "abc()").


:- test_ii(string, "", "\"\"").

:- test_ii(string, "-", "\"-\"").

:- test_ii(string, "+", "\"+\"").

:- test_ii(string, " ", "\" \"").

:- test_ii(string, "x", "\"y\""). % sb false

:- test_ii(integer, 0, "0").

:- test_ii(integer, 5, "5").

:- test_ii(integer, +5, "+5"). 

:- test_ii(integer, -5, "+5"). % sb false

:- test_ii(integer, 5, "6"). % sb false

:- test_ii(struct, a(3,"k"), "a(3,\"k\")").

:- test_ii(struct, a(3,"k"), "a(3,\"h\")"). % sb false

:- test_ii(list, [3,2,4], "[3,2,4]").

:- test_ii(list, [3,2,4], "[3,5,4]"). % sb false

:- test_ii(char, 'c', "'c'").

:- test_ii(char, 'c', "'d'"). % sb false

:- test_ii(real, 3.141, "3.141").

:- test_ii(real, 3.141, "2.7"). % sb false

:- test_ii(symbol, abc, "abc").

:- test_ii(symbol, abc, "abc()").

:- test_ii(symbol, abc, "xyz").


