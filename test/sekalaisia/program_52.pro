road(tampa, houston, 200).
road(gordon, tampa, 300).
road(houston, gordon, 100).
road(houston, kansas_city, 120).
road(gordon, kansas_city, 130).

route(Town1, Town2, Distance) :-
  road(Town1, Town2, Distance).
  
route(Town1, Town2, Distance) :-
  road(Town1, X, Dist1),
  route(X, Town2, Dist2),
  Distance = Dist1 + Dist2, !.

% :- route(tampa, kansas_city, X), writeln(X).