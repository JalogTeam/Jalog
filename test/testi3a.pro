append([H|T],A,[H|T2]) :- append(T,A,T2).
append([],A,[A]).

member([A|_],A).
member([_|T],A) :- 
  member(T,A).

t([A|B]) :- write(A,B).
:- writeln("koe3: "),member([1,2,3,5,7],A), write("loytyi ",A,", "), fail. 

