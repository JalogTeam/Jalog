% retract - test

:- dynamic("clause/0").
:- dynamic("data/1").

koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), 
  assertz(data(10)),
  assertz(data(20)),
  assertz(data(40)),
  writeln(';'), fail.
koe1 :- write("koe1 c: "), retract(data(X)), writeln("C ", X),
    write("koe1 d: "), retract(data(Y)), write("D ", Y),
    writeln(';'), writeln("koe1 end").

koe2 :- write("koe2 a: "), 
  assertz(data(100)),
  assertz(data(110)),
  assertz(data(120)),
  writeln(';'), fail.
koe2 :- writeln("koe2 b: "),
  retract(data(X)), writeln("  ", X), fail.
koe2 :- writeln("koe2 end").  


:- koe1.
:- koe2.