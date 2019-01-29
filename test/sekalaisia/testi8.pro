append([H|T],A,[H|T2]) :- 
  !,
  append(T,A,T2).
append([],A,[A]).
% kokorivi
member([A|_],A). %rivinloppu
member([_|T],A) :- %rivinloppu
  member(/* pikkukom*mentti */T,A) /* monirivinen
pitka
kommentti */.

:- dump("testiout.dump").

