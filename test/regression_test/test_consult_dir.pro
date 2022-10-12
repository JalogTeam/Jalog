% consult_dir - test

:- dynamic("data/1").

koe1 :- writeln("consult_dir test"), fail.
koe1 :- consult_dir(Dir), assertz(parent_dir(Dir)), writeln("koe1 Dir: ", Dir),
        fail.
koe1 :- concat("test", "_dir", TestName), consult_dir(TestName), fail.
koe1 :- consult_dir(Dir), writeln("koe1 changed Dir: ", Dir), fail.
koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), consult("testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- parent_dir(Dir), consult_dir(Dir), fail.
koe1 :- consult_dir(Dir), writeln("koe1 Dir: ", Dir), fail.
koe1 :- writeln(';').

:- koe1.

