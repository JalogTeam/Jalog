:- dynamic("case/1").

case1 :- 
  consult_data("res:resources/sub/testdata.pro", ["case/1"]),
  case(X),
  writeln(X),
  fail.
  
case1.

:- case1.
