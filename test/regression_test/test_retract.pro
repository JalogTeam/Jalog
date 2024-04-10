% retract - test

:- dynamic("data/1").
:- dynamic("dataz/0").

:- dynamic("indent/1", mac).
:- dynamic("indentz/1", mac).
:- dynamic("blank_line/1", mac).

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
koe3 :- writeln("  retracting clause"), 
  trap(retract(clause), Ecode, writeln("  Error: ", Ecode)),
  writeln(" retracted"), fail.
koe3 :- writeln("koe3: searching clause"), 
  clause, writeln(" clause found again, OK"), fail.
koe3 :- writeln("koe3 end").
  
koe4 :- writeln("koe4: nondynamic fact cannot be retracted"), 
  fact, writeln(" fact found"), fail.
koe4 :- writeln("  retracting fact"), 
  trap(retract(fact), Ecode, writeln("  Error: ", Ecode)), 
  writeln(" retracted"), fail.
koe4 :- writeln("koe4: searching fact"), 
  fact, writeln(" fact found again, OK"), fail.
koe4 :- writeln("koe4 end").
  
koe5 :- writeln("koe5: "), 
  trap(retract(datay(_)), Ecode, writeln("  Error: ", Ecode)), 
  writeln("  retracted"),
  datay(X), writeln("  ", X), fail.
koe5 :- writeln("koe5 end").

koe6 :- writeln("koe6: "), retract(dataz), writeln("  retracted"),
  dataz, writeln("  <found>"), fail.
koe6 :- writeln("koe6 end").

koe11 :- write("koe11 a: "), 
  assertz(data(100)),
  assertz(data(110)),
  assertz(data(120)),
  writeln(';'), fail.
koe11 :- writeln("koe11 b: "),
  retract(data(X), dbasedom), writeln("  ", X), fail.
koe11 :- writeln("koe11 c: "),
  retract(data(X)), writeln(" Bad success! ", X), fail.
koe11 :- writeln("koe11 end").  


koe12 :- write("koe12 a: "), 
  assertz(indent(100)),
  assertz(indent(110)),
  assertz(indent(120)),
  writeln(';'), fail.
koe12 :- writeln("koe12 b: "),
  retract(indent(X), mac), writeln("  ", X), fail.
koe12 :- writeln("koe12 c: "),
  retract(indent(X), mac), writeln(" Bad success! ", X), fail.
koe12 :- writeln("koe12 d: "),
  retract(indent(X)), writeln(" Bad success! ", X), fail.
koe12 :- writeln("koe12 end").  

koe13 :- write("koe13 a: "), 
  assertz(indent(10)),
  assertz(data(120)),
  writeln(';'), fail.
koe13 :- writeln("koe13 b: "),
  trap(retract(indent(X), dbasedom), Ecode, writeln("  Error: ", Ecode)),
  writeln("  ", X), fail.
koe13 :- writeln("koe13 c: "),
  trap(retract(data(X), mac), Ecode, writeln("  Error: ", Ecode)),
  writeln(" Bad success! ", X), fail.
koe13 :- writeln("koe13 d: "),
  retract(indent(X)), writeln(" indent: ", X), fail.
koe13 :- writeln("koe13 e: "),
  retract(data(X)), writeln(" data: ", X), fail.
koe13 :- writeln("koe13 end").  

koe14 :- write("koe14 a: "), 
  assertz(indent(10)),
  assertz(indent(11)),
  assertz(indentz(12)),
  assertz(data(120)),
  assertz(data(121)),
  writeln(';'), fail.
koe14 :- writeln("koe14 b: "),
  trap(retract(A, mac), Ecode, writeln("  Error: ", Ecode)),
  writeln("  got ", A),
  findall(X, indent(X), List), writeln("  indents found: ", List),
  findall(X2, indentz(X2), List2), writeln("  indentzs found: ", List2),
  findall(X3, data(X3), List3), writeln("  datas found: ", List3),
  fail.
koe14 :- writeln("koe14 end").
  
:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe11.
:- koe12.
:- koe13.
:- koe14.