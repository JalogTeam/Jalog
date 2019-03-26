% consult - test

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
