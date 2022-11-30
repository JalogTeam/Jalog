% bound - test

koe1 :- write("koe1: "), bound(X), write(" ERROR"), fail.
koe1 :- writeln(';').
koe2 :- write("koe2: "), X = 1, bound(X), write(" OK"), fail.
koe2 :- writeln(';').
koe3 :- write("koe3: "), X = Y, not(bound(X)), not(bound(Y)), write(" OK"), 
        fail.
koe3 :- writeln(';').
koe4 :- write("koe4: "), X = Y, not(bound(X)), not(bound(Y)), X = 1, bound(Y), 
        write(" OK"), fail.
koe4 :- writeln(';').

:- koe1.
:- koe2.
:- koe3.
:- koe4.
