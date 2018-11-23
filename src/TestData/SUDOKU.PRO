
matrix_all_bound([]) :- !.

matrix_all_bound([List|More]) :-
  list_all_bound(List),
  matrix_all_bound(More).
  

list_all_bound([]) :- !.

list_all_bound([H|T]) :-
  bound(H),
  list_all_bound(T).

pick_one([I|_], I).

pick_one([_|Rest], I) :-
  pick_one(Rest, I).

len([], 0) :- !.

len([_|Rest], I1) :-
  len(Rest, I),
  I1 = I + 1.

sudoku1(Rows):-
  make_links(Rows, Cols, SubSquares),
  write("links done"),nl,
  xxxy(Rows, Cols, SubSquares, 1, 1, 1, 0),
  write("Rows\n", Rows),nl,nl,
  write("Columns\n", Cols),nl,nl,
  write("Squares\n", SubSquares),nl.

  
  
/*make_links([
    [A1, A2, A3, A4, A5, A6, A7, A8, A9],
    [B1, B2, B3, B4, B5, B6, B7, B8, B9],
    [C1, C2, C3, C4, C5, C6, C7, C8, C9],
    [D1, D2, D3, D4, D5, D6, D7, D8, D9],
    [E1, E2, E3, E4, E5, E6, E7, E8, E9],
    [F1, F2, F3, F4, F5, F6, F7, F8, F9],
    [G1, G2, G3, G4, G5, G6, G7, G8, G9],
    [H1, H2, H3, H4, H5, H6, H7, H8, H9],
    [I1, I2, I3, I4, I5, I6, I7, I8, I9]
    ], [
    [A1, B1, C1, D1, E1, F1, G1, H1, I1],
    [A2, B2, C2, D2, E2, F2, G2, H2, I2],
    [A3, B3, C3, D3, E3, F3, G3, H3, I3],
    [A4, B4, C4, D4, E4, F4, G4, H4, I4],
    [A5, B5, C5, D5, E5, F5, G5, H5, I5],
    [A6, B6, C6, D6, E6, F6, G6, H6, I6],
    [A7, B7, C7, D7, E7, F7, G7, H7, I7],
    [A8, B8, C8, D8, E8, F8, G8, H8, I8],
    [A9, B9, C9, D9, E9, F9, G9, H9, I9]
    ], [
    [A1, A2, A3, B1, B2, B3, C1, C2, C3],
    [A4, A5, A6, B4, B5, B6, C4, C5, C6],
    [A7, A8, A9, B7, B8, B9, C7, C8, C9],
    [D1, D2, D3, E1, E2, E3, F1, F2, F3],
    [D4, D5, D6, E4, E5, E6, F4, F5, F6],
    [D7, D8, D9, E7, E8, E9, F7, F8, F9],
    [G1, G2, G3, H1, H2, H3, I1, I2, I3],
    [G4, G5, G6, H4, H5, H6, I4, I5, I6],
    [G7, G8, G9, H7, H8, H9, I7, I8, I9]]). */
    
make_links(Rows, Cols, SubSquares) :-
  make_open_matrix(Cols, 9, 9),
  make_open_matrix(SubSquares, 9, 9),
  make_link(Rows, Cols, SubSquares, 1, 1).
  

make_link(_, _, _, 10, _) :- !.

make_link(Rows, Cols, SubSquares, IRow, 10) :-
  !,
  IRow_1 = IRow + 1,
  make_link(Rows, Cols, SubSquares, IRow_1, 1). 

make_link(Rows, Cols, SubSquares, IRow, ICol) :-
  nth_element(Rows, IRow, Row),
  nth_element(Row, ICol, TheElement),
  nth_element(Cols, ICol, Col),
  nth_element(Col, IRow, TheElement),
  sub_square_index(IRow, ICol, ISSRow),
  sub_square_elem_index(IRow, ICol, ISSCol),
  nth_element(SubSquares, ISSRow, SubSquare),
  nth_element(SubSquare, ISSCol, TheElement),
  ICol_1 = ICol + 1,
  make_link(Rows, Cols, SubSquares, IRow, ICol_1). 
    

max(A, B, A) :-
  A > B,
  !.
  
max(_, B, B).


bound_member(I, [I1|_]) :-
  bound(I1),
  I = I1,
  !.
  
bound_member(I, [_|Rest]) :-
  bound_member(I, Rest).        
    
    
find_choices(Row, Col, SubSquare, Choices) :-
  find_rest_choices(Row, Col, SubSquare, 1, Choices).

find_rest_choices(_, _, _, I, []) :-
  I > 9,
  !.

find_rest_choices(Row, Col, SubSquare, I, [I|Rest]) :-
  not(bound_member(I, Row)),
  not(bound_member(I, Col)),
  not(bound_member(I, SubSquare)),
  !,
  I1 = I + 1,
  find_rest_choices(Row, Col, SubSquare, I1, Rest).
    
find_rest_choices(Row, Col, SubSquare, I, Rest) :-
  !,
  I1 = I + 1,
  find_rest_choices(Row, Col, SubSquare, I1, Rest).
    

nth_element([Elem|_], 1, Elem) :- !.

nth_element([_|Rest], N, Elem) :-
  Nm1 = N - 1,
  nth_element(Rest, Nm1, Elem).
  
  
sub_square_index(IRow, ICol, ISubSquare) :-
  ISubSquare = 1 + ((IRow - 1) div 3) * 3 + (ICol - 1) div 3 .

/*
    [A1, A2, A3, A4, A5, A6, A7, A8, A9],
    [B1, B2, B3, B4, B5, B6, B7, B8, B9],
    [C1, C2, C3, C4, C5, C6, C7, C8, C9],
    [D1, D2, D3, D4, D5, D6, D7, D8, D9],
    [E1, E2, E3, E4, E5, E6, E7, E8, E9],
    [F1, F2, F3, F4, F5, F6, F7, F8, F9],
    [G1, G2, G3, G4, G5, G6, G7, G8, G9],
    [H1, H2, H3, H4, H5, H6, H7, H8, H9],
    [I1, I2, I3, I4, I5, I6, I7, I8, I9]
*/
sub_square_elem_index(IRow, ICol, ISSE) :-
  ISSE = 1 + ((IRow - 1) mod 3) * 3 + (ICol - 1) mod 3 . 
  
xxxy(Rows, _Cols, _SubSquares, 10, _ICol, _IChoices, 0) :-
  matrix_all_bound(Rows),
  !,
  write("Found"). % Solution found!
  
xxxy(_Rows, _Cols, _SubSquares, 10, _ICol, _IChoices, 0) :-
  !,
  fail.


xxxy(_Rows, _Cols, _SubSquares, 10, _ICol, IChoices, IChoices) :-
  !, % No solution for this branch
  fail.

xxxy(Rows, Cols, SubSquares, IRow, _ICol, IChoices, Max) :-
  IRow > 9,
  IChoices_1 = IChoices + 1,
  !,
  xxxy(Rows, Cols, SubSquares, 1, 1, IChoices_1, Max).
  

xxxy(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max) :-
  ICol > 9,
  !,
  IRow_1 = IRow + 1,
  !,
  write(".",IRow),
  xxxy(Rows, Cols, SubSquares, IRow_1, 1, IChoices, Max).
  
xxxy(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max) :-
  nth_element(Rows, IRow, Row),
  nth_element(Row, ICol, ThisElement),
  not(bound(ThisElement)),
  nth_element(Cols, ICol, Col),
  sub_square_index(IRow, ICol, ISubSquare),
  nth_element(SubSquares, ISubSquare, SubSquare),
  find_choices(Row, Col, SubSquare, Choices),
  len(Choices, IChoicesActual),
  !,
  xxxy_1(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max, Choices, IChoicesActual, ThisElement).

xxxy(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max) :-
  ICol_1 = ICol + 1,
  !,
  xxxy(Rows, Cols, SubSquares, IRow, ICol_1, IChoices, Max).

xxxy_1(Rows, Cols, SubSquares, _IRow, _ICol, IChoices, _Max, Choices, IChoicesActual, ThisElement) :-
  IChoicesActual = Ichoices,
  !,
  pick_one(Choices, Choice),
  ThisElement = Choice,
  xxxy(Rows, Cols, SubSquares, 1, 1, 1, 0),
  !.
  
xxxy_1(Rows, Cols, SubSquares, IRow, ICol, IChoices, Max, _Choices, IChoicesActual, _) :-
  ICol_1 = ICol + 1,
  max(Max, IChoicesActual, MaxNew),
  !,
  xxxy(Rows, Cols, SubSquares, IRow, ICol_1, IChoices, MaxNew).
   

make_open_matrix([],0,_) :- 
  !.

make_open_matrix([List|Matrix], NRow, NCol) :-
  make_open_list(List, NCol),
  N1Row = NRow - 1,
  make_open_matrix(Matrix, N1Row, NCol).
  
  
make_open_list([],0) :-  
  !.

make_open_list([_Item|List], NCol) :-
  N1Col = NCol - 1,
  make_open_list(List,N1Col).
  

   
  

%goal
/* Kaleva nnnn */
/*
  sudoku1([
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, _, _, _, _, _, _, _, _]
    ]).
*/

/* Mikon helppo */
/*
:-  sudoku1([
    [ 6, 1, _, 4, 8, 9, 7, _, 2],
    [ 8, 9, 2, _, 7, _, 6, 1, 4],
    [ 7, _, 4, _, 1, 2, 8, 9, _],
    [ 9, 6, _, 7, _, 4, _, 5, 8],
    [ _, 2, _, 3, _, _, _, _, 1],
    [ _, 5, 8, 9, 6, _, 4, _, 7],
    [ _, _, 9, _, 4, 6, 5, 8, _],
    [ 2, _, 3, 8, _, 5, _, 7, _],
    [ _, _, _, _, _, 7, 2, 4, 9]
    ]).
*/
/*
:-  sudoku1([[6,1,5,4,8,9,7,3,2],
[8,9,2,5,7,3,6,1,4],
[7,3,4,6,1,2,8,9,5],
[9,6,1,7,2,4,3,5,8],
[4,2,7,3,5,8,9,6,1],
[3,5,8,9,6,1,4,2,7],
[1,7,9,2,4,6,5,8,3],
[2,4,3,8,9,5,1,7,6],
[5,8,6,1,3,7,2,4,9]]).
*/
/*
:-  sudoku1([[6,1,5,4,8,9,7,3,2],
[8,9,2,5,7,3,6,1,4],
[7,3,4,6,1,2,8,9,5],
[9,_,1,7,_,4,3,5,8],
[4,2,7,3,5,8,9,6,1],
[3,5,8,9,6,1,4,2,7],
[1,_,9,2,_,6,5,8,3],
[2,4,3,8,9,5,1,7,6],
[5,8,6,1,3,7,2,4,9]]).
*/
/* Kaleva 2581 */
/*
:-  sudoku1([
    [ 8, _, _, _, 2, _, _, 3, _],
    [ 9, _, 4, 6, _, _, _, 1, 2],
    [ _, 5, _, _, 7, 1, _, _, 6],
    [ _, _, _, _, _, _, _, 7, _],
    [ 6, _, 2, _, 3, _, _, 8, _],
    [ 4,  8, _, _, 1, 9, _, 6, 5],
    [ _,  6, 8, 7, 5, _, 9, 4, _],
    [ 1, 2, _, _, _, 4, 7, _, _],
    [ _, _, _, 1, _, 8, 6, _, _]
    ]).
 
*/
/* Kaleva 2582 */
/*
:-  sudoku1([
    [ _, 9, 5, _, _, 7, _, 8, _],
    [ _, _, 6, 8, _, _, 3, _, _],
    [ _, _, _, _, _, 4, _, _, _],
    [ 5, _, _, _, _, _, _, _, 3],
    [ _, 1, _, _, _, _, _, 5, _],
    [ _, _, 8, 7, _, _, 1, _, _],
    [ 2, _, 4, _, 5, _, _, _, 1],
    [ _, _, _, _, _, _, _, _, _],
    [ _, 5, 3, 4, 7, 9, _, _, 2]
    ]).
*/
/* Kaleva 2583 */
/*
*/
:-  sudoku1([
    [ 7, _, _, _, _, _, 9, _, 6],
    [ _, _, 1, 6, _, 2, _, _, _],
    [ _, 4, 6, _, _, 8, _, 5, _],
    [ 5, _, _, _, 4, 6, _, _, _],
    [ _, _, _, _, _, _, _, 3, _],
    [ _, _, _, _, 7, _, _, 2, _],
    [ _, 1, _, _, _, 5, _, 8, _],
    [ _, _, _, _, _, _, _, _, _],
    [ _, 2, _, 3, _, _, 5, 9, _]
    ]).
    
/* "Maailman vaikein" */
/* Takes currently several weeks to run

:-  sudoku1([
    [ 8, 5, _, _, _, 2, 4, _, _],
    [ 7, 2, _, _, _, _, _, _, 9],
    [ _, _, 4, _, _, _, _, _, _],
    [ _, _, _, 1, _, 7, _, _, 2],
    [ 3, _, 5, _, _, _, 9, _, _],
    [ _, 4, _, _, _, _, _, _, _],
    [ _, _, _, _, 8, _, _, 7, _],
    [ _, 1, 7, _, _, _, _, _, _],
    [ _, _, _, _, 3, 6, _, 4, _]
    ]).

*/

    
