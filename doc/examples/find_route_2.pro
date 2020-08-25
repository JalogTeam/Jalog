road(T1, T2, _) :- 
  writeln("road(", T1, ", ", T2, ", _)"), fail.
road(joensuu, kajaani, 230). % 230 km road connection from Joensuu to Kajaani
road(kajaani, oulu, 183). % 183 km road connection from Kajaani to Oulu
road(T1, T2, _) :- 
  writeln("  road(", T1, ", ", T2, ", _) FAILED"), fail.

bound_member(T1, T2) :- 
  writeln("bound_member(", T1, ", ", T2, ")"), fail.
bound_member(_, [Hx|_]) :-
  not(bound(Hx)), 
  !,
  fail.
bound_member(X, [X|_]).
bound_member(X, [_|T]) :-
  bound_member(X, T).
bound_member(T1, T2) :- 
  writeln("  bound_member(", T1, ", ", T2, ") FAILED"), fail.
  
close_list(T1, T2) :- 
  writeln("close_list(", T1, ")"), fail.
close_list([]).
close_list([_|T]) :-
  close_list(T).
close_list(T1) :- 
  writeln("  close_list(", T1, ") FAILED"), fail.


member(T1, T2) :- 
  writeln("member(", T1, ", ", T2, ")"), fail.
member(H, [H|_]).
member(X, [_|T]) :-
  member(X, T).
member(T1, T2) :- 
  writeln("  member(", T1, ", ", T2, ") FAILED"), fail.
  
connected(T1, T2, _) :- 
  writeln("connected(", T1, ", ", T2, ", _)"), fail.
connected(Town1, Town2, Length) :- 
  road(Town1, Town2, Length). 
connected(Town1, Town2, Length) :- 
  road(Town2, Town1, Length). 
connected(T1, T2, _) :- 
  writeln("  connected(", T1, ", ", T2, ", _) FAILED"), fail.

route(T1, T2, Path, _) :- 
  writeln("route(", T1, ", ", T2, ", ", Path, ", _, _)"), fail.
route(Town, Town, Path, 0) :-
  close_list(Path).
route(Town1, Town2, Path, Length) :- 
   not(bound_member(Town2, Path)),
   member(Town2, Path),
   connected(Town1, TownX, Length1), 
   route(TownX, Town2, Path, Length2), 
   Length = Length1 + Length2.
route(T1, T2, Path, _, _) :- 
  writeln("  route(", T1, ", ", T2, ", ", Path, ", _, _) FAILED"), fail.
   
:- route(oulu, joensuu, Route, Len),  
  nl, writeln(Route, ", total length ", Len).