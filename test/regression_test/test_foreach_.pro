% foreach_ - test


koe1 :- write("koe1: "), foreach_(X, [1, 2, 3]), write(' ', X), fail.
koe1 :- writeln(';').

koe2 :- write("koe2: "), foreach_(X, []), write(' ', X), fail.
koe2 :- writeln(';').

:- koe1.
:- koe2.