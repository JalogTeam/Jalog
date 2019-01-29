p1 :-
  write("<p1.1>"), 
  p2,
  p3,
  write("</p1.1>").

p1 :-
  write("<p1.2>"), 
  p2,
  p3,
  write("</p1.2>").

p2 :-
  write("<p2.1/>").

p2 :-
  write("<p2.2/>").

p3 :-
  write("<p3.1/>").

p3 :-
  write("<p3.2/>").

:- write("<.1>"), p1, write("</.1>"), fail.
:- write("<.2/>").
  
