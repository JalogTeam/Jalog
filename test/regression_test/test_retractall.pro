% retractall - test

:- dynamic("data/1").
:- dynamic("dataz/0").

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


:- koe1.
:- koe2.
:- koe3.
:- koe4.
:- koe5.
:- koe6.
