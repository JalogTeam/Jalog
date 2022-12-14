% assertz - test

:- dynamic("clause/0").
:- dynamic("data/1").
:- dynamic("data3/1").
:- dynamic("data5/2").

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

for(X, [X|_]).
for(X, [_|T]) :- for(X,T).


koe5 :- write("koe5 a: "), data5(X, Y), write(" (", X, ",", Y, ")"), fail.
koe5 :- writeln(';'), fail.
koe5 :- write("koe5 b: "), 
  for(X, [1,4]),
  for(Z, [2, 15]),
  assert(data5(X, k(Z))),
  nl,
  data5(Y, R), write(" (", Y, ",", R, ")"),
  fail.
koe5 :- nl, write("koe5 c: "), data5(Y, R), write(" (", Y, ",", R, ")"), fail.
koe5 :- writeln(';').

:- koe5.

koe6 :- writeln("koe6 alku"),fail.
koe6 :- assert(data5(X, k(X))),
    writeln("assertoitu"), fail.
koe6 :- nl, write("koe6 a: "), data5(Y, R), write(" (", Y, ",", R, ")"), fail.
koe6 :- writeln(';'), fail.
koe6 :- nl, write("koe6 b: "), Y = 4, data5(Y, R), write(" (", Y, ",", R, ")"),
    fail.
koe6 :- writeln(';'), fail.
koe6 :- nl, write("koe6 c: "), Y = 3, data5(Y, R), write(" (", Y, ",", R, ")"),
    fail.
koe6 :- writeln(';').

:- koe6.

