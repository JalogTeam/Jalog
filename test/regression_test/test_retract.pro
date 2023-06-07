% retract - test

:- dynamic("data/1").

clause :- writeln("clause").

fact.

koe1_c :- write("koe1 c: "), retract(data(X)), writeln("C ", X),
    write("koe1 d: "), retract(data(Y)), write("D ", Y),
    writeln(';'), !.

koe1 :- write("koe1 a: "), data(X), write(' ', X), fail.
koe1 :- writeln(';'), fail.
koe1 :- write("koe1 b: "), 
  assertz(data(10)),
  assertz(data(20)),
  assertz(data(40)),
  writeln(';'), fail.
koe1 :- koe1_c, fail.
koe1 :- data(20), writeln(" Bad success, koe1."), fail.
koe1 :- retract(data(40)), writeln(" 40 retracted successfully."), fail.
koe1 :- writeln("koe1 end").

koe2 :- write("koe2 a: "), 
  assertz(data(100)),
  assertz(data(110)),
  assertz(data(120)),
  writeln(';'), fail.
koe2 :- writeln("koe2 b: "),
  retract(data(X)), writeln("  ", X), fail.
koe2 :- writeln("koe2 c: "),
  retract(data(X)), writeln(" Bad success! ", X), fail.
koe2 :- writeln("koe2 end").  

koe3 :- writeln("koe3: rule cannot be retracted"), 
  clause, writeln(" clause found"), fail.
koe3 :- writeln("  retracting clause"), retract(clause), 
  writeln(" retracted"), fail.
koe3 :- writeln("koe3: searching clause"), 
  clause, writeln(" clause found again, OK"), fail.
koe3 :- writeln("koe3 end").
  
koe4 :- writeln("koe4: nondynamic fact cannot be retracted"), 
  fact, writeln(" fact found"), fail.
koe4 :- writeln("  retracting fact"), retract(fact), 
  writeln(" retracted"), fail.
koe4 :- writeln("koe4: searching fact"), 
  fact, writeln(" fact found again, OK"), fail.
koe4 :- writeln("koe4 end").
  
:- koe1.
:- koe2.
:- koe3.
:- koe4.