% bound - test

koe1 :- write("koe1: "), bound(X), write(" ERROR"), fail.
koe1 :- writeln(';').
koe2 :- write("koe2: "), X = 1, bound(X), write(" OK"), fail.
koe2 :- writeln(';').

:- koe1.
:- koe2.
