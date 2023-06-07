% dynamic - test

:- writeln("A "), a. % should give an error message
:- writeln("B "), dynamic("a/0").
:- writeln("C "), a.
:- writeln("D "), dynamic("b/1").
:- writeln("E "), b(X). % should fail without error message
:- writeln("F "), b(3, "d"). % error
:- writeln("G "), b. % error
b.
:- writeln("b stored").
:- writeln("H "), b.
b("heippa").
:- writeln("I "), b(X), writeln("J "), writeln(X), fail. % should write "heippa"

:- writeln("K Rule cannot be made dynamic.").
c :- writeln("K Hei!").
:- dynamic("c/0"), writeln("ERROR dynamic rule shouldn't exist").
:- writeln("K done").

:- writeln("L Dynamic predicate cannot accept a rule.").
b("jee") :- writeln("L Hei, hei!").
:- writeln("L "), b(X), writeln(X), fail.
:- writeln("L done").



