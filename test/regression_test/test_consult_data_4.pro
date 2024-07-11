% consult_data_4 - test
koe1 :- write("koe1 a: "), data(X), write(" data:", X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 a2: "), data2(X), write(" data2:", X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), consult_data("file:testdata_consult_data_1.pro"), 
            writeln("consult_data OK;"), fail.

:- dynamic("data/1").
:- dynamic("data/2", x).
:- dynamic("xyz/1", x).
:- dynamic("data2/1", g).

koe1 :- write("koe1 c: "), data(X), write(" data:", X), fail.
koe1 :- writeln(';'),fail.
koe1 :- write("koe1 c2: "), data(X, Y), write(" data:", X, " ", Y), fail.
koe1 :- writeln(';'),fail.
koe1 :- write("koe1 c3: "), xyz(X), write(" xyz:", X), fail.
koe1 :- writeln(';'),fail.
koe1 :- write("koe1 c4: "), data2(X), write(" data2:", X), fail.
koe1 :- writeln(';'),fail.
koe1.

:- koe1.
