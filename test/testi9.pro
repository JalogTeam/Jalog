testi(A) :- write("<"),koe(A),write(">").
koe(A) :- write("("),write(A),write(")").
:- write("{"),testi(A), writeln("}").

