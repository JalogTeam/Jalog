% findall - test

a(1).
a(3).


:- dynamic("b/1").

c(1, 2).
c(1, 3).
c(5, 7).
c(5, 8).
c(5, 9).

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

d(1).
d(3).
d(5).

% e(L) :- write("e(L) "), d(A), findall(B, c(A, B), L),
%         write("\nd: ", A," , L=", L), nl.

e(L) :- write("e(L) "), d(A), findall(B, c(A, B), M), L = l(A, M),
        write("\nd: ", A," , L=", L), nl.

koe6 :- write("koe6: "), findall(L, e(L), LL), 
        write("\nend koe6: LL=", LL, "\n"). 

koe7 :- write("koe7: "), findall(r(I, J), c(I, J), L), !, writeln(' ', L), fail.
koe7 :- writeln("Ok").

:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe7.
