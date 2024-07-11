% retractall - test

:- dynamic("data/1").
:- dynamic("dataz/0").
:- dynamic("indent/1", mac).
:- dynamic("indentz/1", mac).


data(10).
data(20).
data(30).

datax(5).

koe1 :- writeln("koe1: "), retractall(data(20)), writeln("  retracted"),
  data(X), writeln("  ", X), fail.
koe1 :- writeln("koe1 end").

koe2 :- writeln("koe2: "), retractall(data(_)), writeln("  retracted"),
  data(X), writeln("  ", X), fail.
koe2 :- writeln("koe2 end").

koe3 :- writeln("koe3: "), retractall(data(_)), writeln("  retracted"),
  data(X), writeln("  ", X), fail.
koe3 :- writeln("koe3 end").

koe4 :- writeln("koe4: "), 
  trap(retractall(datax(_)), Ecode, writeln("  error: ", Ecode)),
  writeln("  retracted"), fail.
koe4 :- datax(X), writeln("  ", X), fail.
koe4 :- writeln("koe4 end").

koe5 :- writeln("koe5: "), 
  trap(retractall(datay(_)), Ecode, writeln("  error: ", Ecode)),
  writeln("  retracted"), fail.
koe5 :- datay(X), writeln("  ", X), fail.
koe5 :- writeln("koe5 end").

koe6 :- writeln("koe6: "), retractall(dataz), writeln("  retracted"),
  dataz, writeln("  <found>"), fail.
koe6 :- writeln("koe6 end").


koe13 :- write("koe13 a: "), 
  assertz(indent(10)),
  assertz(indent(11)),
  assertz(indentz(12)),
  assertz(data(120)),
  assertz(data(121)),
  writeln(';'), fail.
koe13 :- writeln("koe13 b: "), % should get error
  trap(retractall(indent(X), dbasedom), Ecode, writeln("  Error: ", Ecode)),
  writeln("  erroneous success"), fail.
koe13 :- writeln("koe13 c: "), % should get error
  trap(retractall(data(X), mac), Ecode, writeln("  Error: ", Ecode)),
  writeln("  erroneous success"), fail.
koe13 :- writeln("koe13 d: "), % should succeed
  trap(retractall(indent(X)), Ecode, writeln("  Error: ", Ecode)),
  findall(X, indent(X), List), writeln("  indents found: ", List),
  findall(X2, indentz(X2), List2), writeln("  indentzs found: ", List2).

koe14 :- write("koe14 a: "), 
  assertz(indent(10)),
  assertz(indent(11)),
  assertz(indentz(12)),
  assertz(data(120)),
  assertz(data(121)),
  writeln(';'), fail.
koe14 :- writeln("koe14 b: "),
  trap(retractall(_, mac), Ecode, writeln("  Error: ", Ecode)),
  findall(X, indent(X), List), writeln("  indents found: ", List),
  findall(X2, indentz(X2), List2), writeln("  indentzs found: ", List2),
  findall(X3, data(X3), List3), writeln("  datas found: ", List3).
 
:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
:- koe13.
:- koe14.