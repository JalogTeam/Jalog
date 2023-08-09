% str_int - test

test_io(S, I) :- writeq(S), str_int(S, I), !, writeln(" -> ", I).
test_io(_, _) :- writeln(" is not integer.").

test_oi(S, I) :- write(I), str_int(S, I), !, write(" -> "), writeq(S), nl.
test_oi(S, I) :- writeln(" not converted").

test_ii(S, I) :- writeq(S), write(" ? ", I), str_int(S, I), !, writeln(" YES").
test_ii(S, I) :- writeln(" NO").


:- test_io("", _).

:- test_io("-", _).

:- test_io("+", _).

:- test_io(" ", _).

:- test_io("0", _).

:- test_io("5", _).

:- test_io("+5", _).

:- test_io("-5", _).

:- test_io("05", _).

:- test_io("9223372036854775807", _).

:- test_io("9223372036854775808", _).

:- test_io("-9223372036854775807", _).

:- test_io("-9223372036854775808", _).

:- test_io("-9223372036854775809", _).

:- test_io("x56", _).

:- test_io("&56", _).

:- test_io("56x", _).

:- test_io("56&", _).

:- test_oi(_, 0).

:- test_oi(_, 5).

:- test_oi(_, -5).

:- test_oi(_, 9223372036854775807).

:- test_oi(_, -9223372036854775807).

:- N = -9223372036854775807-1, test_oi(_, N).

:- test_ii("5", 5).

:- test_ii("5", 6).

:- test_ii("05", 5).

:- test_ii("-5", -5).

:- test_ii("-05", -5).

:- test_ii("-5", -6).