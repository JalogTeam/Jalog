% assertz - test

:- dynamic("clause/0").
:- dynamic("data/1").
:- dynamic("data3/1").
:- dynamic("data5/2").

koe1 :- writeln("assertz test"), fail.
koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), 
  assertz(data(10)),
  assertz(data(20)),
  assertz(data(40)),
  writeln(';'), fail.
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';').
koe1 :- write("koe1 c: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), writeln("assertz test end").

:- koe1.

% OHO!
% test that rules cannot be asserted
koe2 :- writeln("koe2 alku"),fail.
koe2 :- trap(assertz((clause :- writeln("  oho!"), writeln("  Wow!"))),
              Err, writeln("koe2 trap: ", Err)),
    writeln("assertoitu"), fail.
koe2 :- clause, writeln("löytyi"), !.

koe2 :- writeln("ei löytynyt").

:- koe2.
 
:- dynamic("k3data/1", mac).

% assert - test

koe3 :- write("koe3 a: "), data3(X), write(' ', X), fail.
koe3 :- writeln(';'), fail.
koe3 :- write("koe3 b: "), 
  assertz(k3data(21)),
  assertz(k3data(41), mac),
  writeln(';'), fail.
koe3 :- write("koe3 c: "), k3data(X), write(' ', X), fail.
koe3 :- writeln(';'), writeln("koe3 test end").

:- koe3.

koe4 :- write("koe4 a: "), data(X), write(' ', X), fail.
koe4 :- writeln(';'), fail.
koe4 :- write("koe4 b: "), 
  trap(assertz(k3data(23), dbasedom), Err, writeln("Error: ", Err)),
  writeln("Bad success"), fail.
koe4 :- write("koe4 c: "), 
  trap(assertz(data(23), mac), Err, writeln("Error: ", Err)),
  writeln("Bad success"), fail.
koe4 :- write("koe4 d: k3data:"), k3data(X), write(' ', X), fail.
koe4 :- writeln(';'), write("koe4 e: data:"), data(X), write(' ', X), fail.
koe4 :- writeln(';'), writeln("koe4 test end").

:- koe4.

% assert - test

koe5 :- write("koe5 a: "), data3(X), write(' ', X), fail.
koe5 :- writeln(';'), fail.
koe5 :- write("koe5 b: "), 
  assert(data3(10)),
  assert(data3(20)),
  assert(data3(40)),
  writeln(';'), fail.
koe5 :- write("koe5 c: "), data3(X), write(' ', X), fail.
koe5 :- writeln(';').

:- koe5.

% OHO!
koe6 :- writeln("koe6 alku"),fail.
koe6 :- trap(assert((clause :- writeln("  oho!"), writeln("  Wow!"))), Err, writeln("Error: ", Err)),
    writeln("assertoitu"), fail.
koe6 :- clause, writeln("löytyi"), !.
koe6 :- writeln("ei löytynyt").

:- koe6.

for(X, [X|_]).
for(X, [_|T]) :- for(X,T).

koe7 :- write("koe7 a: "), data5(X, Y), write(" (", X, ",", Y, ")"), fail.
koe7 :- writeln(';'), fail.
koe7 :- write("koe7 b: "), 
  for(X, [1,4]),
  for(Z, [2, 15]),
  assert(data5(X, k(Z))),
  nl,
  data5(Y, R), write(" (", Y, ",", R, ")"),
  fail.
koe7 :- nl, write("koe7 c: "), data5(Y, R), write(" (", Y, ",", R, ")"), fail.
koe7 :- writeln(';').

:- koe7.

koe8 :- writeln("koe8 alku"),fail.
koe8 :- assert(data5(X, k(X))),
    writeln("assertoitu"), fail.
koe8 :- nl, write("koe8 a: "), data5(Y, R), write(" (", Y, ",", R, ")"), fail.
koe8 :- writeln(';'), fail.
koe8 :- nl, write("koe8 b: "), Y = 4, data5(Y, R), write(" (", Y, ",", R, ")"),
    fail.
koe8 :- writeln(';'), fail.
koe8 :- nl, write("koe8 c: "), Y = 3, data5(Y, R), write(" (", Y, ",", R, ")"),
    fail.
koe8 :- writeln(';').

:- koe8.