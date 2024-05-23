% term_str - test

:- writeln("alku").
:- debug_(1).
:- A = 5, B = 4, writeln([A+B, A-1, 1+2]).

writex(X) :- writeq(X).

:- A = 5, B = 4, writex([A+B, A-1, 1+2]), nl.

test_x :- B = -5, write(" | "), writeq(B), nl, 
    A = +5, write(" | "), writeq(A), nl, write(" | "), writex(+5-2).

test_io(T, V, S) :- write("io ", T, " "), writeq(V), term_str(T, V, S), !, 
    write(" -> "), writeq(S), nl.
test_io(_, _, _) :- writeln(" conversion failed.").

test_oi(T, V, S) :- write("oi ", T, " "), term_str(T, V, S), !, writeq(V), 
    write(" <- "), writeq(S), nl.
test_oi(_,_, _) :- writeln(" conversion failed.").

test_ii(T, V, S) :- write("ii ", T, " "), writeq(V), term_str(T, V, S), !, 
    write(" <-> "), writeq(S), writeln(" true").
test_ii(_,_, _) :- writeln(" false.").

:- writeq(-5).

:- test_x.
:- debug_(0).
:- writeq(+5).

:- test_io(string, "", _).

:- test_io(string, "-", _).

:- test_io(string, "+", _).

:- test_io(string, " ", _).

:- test_io(integer, 0, _).

:- test_io(integer, 5, _).

% :- test_io(integer, +5, _).

:- test_io(integer, -5, _).

:- test_io(struct, a(3,"k"), _).

:- test_io(list, [3,2,4], _).

:- test_io(char, 'c', _).

:- test_io(real, 3.141, _).


:- test_oi(string, _, "\"\"").

:- test_oi(string, _, "\"-\"").

:- test_oi(string, _, "\"+\"").

:- test_oi(string, _, "\" \"").

:- test_oi(integer, _, "0").

:- test_oi(integer, _, "5").

:- test_oi(integer, _, "+5").

:- test_oi(integer, _, "-5").

:- test_oi(struct, _, "a(3,\"k\")").

:- test_oi(list, _, "[3,2,4]").

:- test_oi(char, _, "'c'").

:- test_oi(real, _, "3.141").


:- test_ii(string, "", "\"\"").

:- test_ii(string, "-", "\"-\"").

:- test_ii(string, "+", "\"+\"").

:- test_ii(string, " ", "\" \"").

:- test_ii(integer, 0, "0").

:- test_ii(integer, 5, "5").

% :- test_ii(integer, +5, "+5").

:- test_ii(integer, -5, "+5").

:- test_ii(struct, a(3,"k"), "a(3,\"k\")").

:- test_ii(list, [3,2,4], "[3,2,4]").

:- test_ii(char, 'c', "'c'").

:- test_ii(real, 3.141, "3.141").


