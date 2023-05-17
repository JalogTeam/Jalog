% asserta - test

:- dynamic("clause/0").
:- dynamic("data/1").

koe1 :- writeln("asserta test"), fail.
koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), 
  asserta(data(10)),
  asserta(data(20)),
  asserta(data(40)),
  writeln(';'), fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), writeln("asserta test end").

:- koe1.
 
% OHO!
% test that rules cannot be asserted
koe2 :- writeln("koe2 alku"),fail.
koe2 :- asserta((clause :- writeln("  oho!"), writeln("  Wow!"))),
    writeln("assertoitu"), fail.
koe2 :- clause, writeln("löytyi"), !.

koe2 :- writeln("ei löytynyt").

:- koe2.
 
