case1 :- 
  consult_dir("res:resources/sub"),
  consult_data("testdata.pro", ["case/1"]),
  case(X),
  writeln(X),
  fail.
  
case1.

:- case1.
