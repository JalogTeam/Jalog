road(T1, T2, _) :- 
  writeln("road(", T1, ", ", T2, ", _)"), fail.
road(joensuu, kajaani, 230). % 230 km road connection from Joensuu to Kajaani
road(kajaani, oulu, 183). % 183 km road connection from Kajaani to Oulu
road(T1, T2, _) :- 
  writeln("  road(", T1, ", ", T2, ", _) FAILED"), fail.

add_unique_to_open_list(M, L) :-
  writeln("add_unique_to_open_list(", M, ", ", L, ")"), fail.
add_unique_to_open_list(M, [Mx|T]) :-
  bound(Mx),
  M = Mx,
  !,
  writeln("  add_unique_to_open_list(", M, ", ", [Mx|T], ") FAILED" ), fail.
  fail.
add_unique_to_open_list(M, [M|_]) :- !.
add_unique_to_open_list(M, [_|T]) :-
  add_unique_to_open_list(M, T).
add_unique_to_open_list(T1, T2) :- 
  writeln("  add_unique_to_open_list(", T1, ", ", T2, ") FAILED" ), fail.
  
close_list(T1) :- 
  writeln("close_list(", T1, ")"), fail.
close_list([]).
close_list([_|T]) :-
  close_list(T).
close_list(T1) :- 
  writeln("  close_list(", T1, ") FAILED"), fail.
  
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
  add_unique_to_open_list(Town, Path),
  close_list(Path).
route(Town1, Town2, Path, Length) :- 
   add_unique_to_open_list(Town1, Path),
   connected(Town1, TownX, Length1), 
   route(TownX, Town2, Path, Length2), 
   Length = Length1 + Length2.
route(T1, T2, Path, _, _) :- 
  writeln("  route(", T1, ", ", T2, ", ", Path, ", _, _) FAILED"), fail.
   
:- route(oulu, joensuu, Route, Len),  
  nl, writeln(Route, ", total length ", Len).