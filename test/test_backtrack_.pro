% Backtrack-testi

pred1(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max) :-
  writeln("1"),
  pred2(B),
  writeln("1: ", B),
  not(bound(B)),
  writeln("1: ***sotkee"),

  fail.

pred1(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max) :-
  writeln("2").


pred2(A) :-
  A = 1,!,
  writeln("pred2").
  
:- writeln("Begin"),pred1(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max),writeln("End").


