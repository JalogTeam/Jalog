% assertz - test

:- dynamic("clause/0").
:- dynamic("data/1").
:- dynamic("data3/1").

koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), 
  assertz(data(10)),
  assertz(data(20)),
  assertz(data(40)),
  writeln(';'), fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';').

:- koe1.

% OHO!
koe2 :- writeln("koe2 alku"),fail.
koe2 :- assertz((clause :- writeln("  oho!"), writeln("  Wow!"))),
    writeln("assertoitu"), fail.
koe2 :- clause, writeln("löytyi"), !.

koe2 :- writeln("ei löytynyt").

:- koe2.

% assert - test

koe3 :- write("koe3 a: "), data3(X), write(' ', X), fail.
koe3 :- writeln(';'), fail.
koe3 :- write("koe3 b: "), 
  assert(data3(10)),
  assert(data3(20)),
  assert(data3(40)),
  writeln(';'), fail.
koe3 :- write("koe3 c: "), data3(X), write(' ', X), fail.
koe3 :- writeln(';').

:- koe3.

% OHO!
koe4 :- writeln("koe4 alku"),fail.
koe4 :- assert((clause :- writeln("  oho!"), writeln("  Wow!"))),
    writeln("assertoitu"), fail.
koe4 :- clause, writeln("löytyi"), !.

koe4 :- writeln("ei löytynyt").


:- koe4.

