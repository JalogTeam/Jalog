% findall - test

a(1).
a(3).
:- dynamic("b/1").

koe1 :- write("koe1: "), findall(X, a(X), L), write(' ', L), fail.
koe1 :- writeln(';').

koe2 :- write("koe2: "), findall(X, b(X), L), write(' ', L), fail.
koe2 :- writeln(';').

koe3 :- write("koe3: "), findall(X, a(X), [1, 3]), write(" Ok"), fail.
koe3 :- writeln(';').

koe4 :- write("koe4: "), findall(X, a(X), [1, Y]), write(' ', Y), fail.
koe4 :- writeln(';').

koe5 :- write("koe5: "), findall(X, a(X), [Y]), write(" ERROR ", Y), fail.
koe5 :- writeln(';').


:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
