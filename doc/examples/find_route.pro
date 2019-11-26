road(joensuu, kajaani, 230). % 230 km road connection from Joensuu to Kajaani
road(kajaani, oulu, 183). % 183 km road connection from Kajaani to Oulu

member(H, [H|_]).
member(X, [_|T]) :-
  member(X, T).
  
connected(Town1, Town2, Length) :- 
  road(Town1, Town2, Length). 
connected(Town1, Town2, Length) :- 
  road(Town2, Town1, Length). 

route(Town, Town, _, [Town], 0).
route(Town1, Town2, ToTown1, [Town1|FromTown1], Length) :- 
   ToTownX = [Town1|ToTown1],
   connected(Town1, TownX, Length1), 
   not(member(TownX, ToTownX)),   
   route(TownX, Town2, ToTownX, FromTown1, Length2), 
   Length = Length1 + Length2.
   
:- route(oulu, joensuu, [], Route, Len),  
  writeln(Route, ", total length ", Len).