road(T1, T2, _) :- 
  writeln("road(", T1, ", ", T2, ", _)"), fail.
road(joensuu, kajaani, 230). % 230 km road connection from Joensuu to Kajaani
road(kajaani, oulu, 183). % 183 km road connection from Kajaani to Oulu
road(T1, T2, _) :- 
  writeln("  road(", T1, ", ", T2, ", _) FAILED"), fail.

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

route(T1, T2, Prev, _, _) :- 
  writeln("route(", T1, ", ", T2, ", ", Prev, ", _, _)"), fail.
route(Town, Town, _, [Town], 0).
route(Town1, Town2, ToTown1, [Town1|FromTown1], Length) :- 
   ToTownX = [Town1|ToTown1],
   connected(Town1, TownX, Length1), 
   not(member(TownX, ToTownX)),   
   route(TownX, Town2, ToTownX, FromTown1, Length2), 
   Length = Length1 + Length2.
route(T1, T2, Prev, _, _) :- 
  writeln("  route(", T1, ", ", T2, ", ", Prev, ", _, _) FAILED"), fail.
   
:- route(oulu, joensuu, [], Route, Len),  
  nl, writeln(Route, ", total length ", Len).