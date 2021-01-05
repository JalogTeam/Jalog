case1 :- 
  consult_data("res:resources/testdata.pro", ["case/1"]),
  case(X),
  writeln(X),
  fail.
  
case1 :- 
  writeln("---"),
  consult("res:resources/testdata.pro"),
  case(X),
  writeln(X),
  fail.

case1.

:- case1.