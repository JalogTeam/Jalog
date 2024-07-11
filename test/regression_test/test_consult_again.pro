% consult_again - test

:- dynamic("data/1").
:- dynamic("parent_dir/1").

koe1 :- writeln("consult_again test"), fail.
koe1 :- consult_dir(Dir), assertz(parent_dir(Dir)), writeln("koe1 Dir: ", Dir),
        fail.
koe1 :- consult_dir("test_dir"), fail.
koe1 :- consult_dir(Dir), writeln("koe1 changed Dir: ", Dir), fail.
koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), consult("testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
% Ensure that double consult does not work
koe1 :- write("koe1 d: "), consult("testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 e: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
% Try consulting the same from upper default dir
koe1 :- parent_dir(Dir), consult_dir(Dir), fail.
koe1 :- write("koe1 f: "), consult("test_dir/testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 g: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
% Try consulting another file with the same name in the current directory
koe1 :- write("koe1 h: "), consult("testdata_consult_1.pro"),
        writeln(';'), fail.
koe1 :- write("koe1 i: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.

koe1 :- consult_dir(Dir), writeln("koe1 Dir: ", Dir), fail.
koe1 :- writeln(';').

:- koe1.

