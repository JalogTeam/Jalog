% findall - test

a(1).
a(3).


:- dynamic("b/1").

c(1, 2).
c(1, 3).
c(5, 7).
c(5, 9).

d(1, L) :- findall(I, c(1, I), L), write("\nd: 1, L=", L).
d(5, L) :- findall(I, c(5, I), L), write("\nd: 5, L=", L).

koe6  :- findall(x(M, N), d(M, N), L), write("\nL=", L, "\n"). 

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
:- koe6.
