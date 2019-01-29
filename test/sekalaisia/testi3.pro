append([H|T],A,[H|T2]) :- append(T,A,T2).
append([],A,[A]).

member([A|_],A).
member([_|T],A) :- 
  member(T,A).

t([A|B]) :- write(A,B).
%:- write("<"),t([]),write(">").
%:- dump("testiout.dump").
%:- append([1,2,3],4,A), writeln(A), fail.
%:- []=[A|B], write("poo",A,B).
%:- writeln("koe0: "),member([],3), write("loytyi "), fail. 
%:- writeln("koe1: "),member([1,2,3,5,7],3), write("loytyi "), fail. 
%:- writeln("koe2: "),member([1,2,3,5,7],4), write("loytyi "), fail. 
:- writeln("koe3: "),member([1,2,3,5,7],A), writeln("loytyi ",A,", "), fail. 

