a(0,[]).
a(A,['x'|B]) :-
  A1=A-1,
  a(A1,B).
:-write("--"),a(10,A),write(A,"--").
:-write("(",A,")").
:-dump("testiout.dump").
:-foreach_(B,[1,2,3,5,7,11]),C=B,C+2 = A,write(A," "),fail.
:-A=3*5,writeln(A, " =15?").
:-A=7-2,writeln(A, " =5?").
:-A=66/8,writeln(A, " =8.25?").
:-A=-9, writeln(A, " =-9?").
:-A=2+3*5, writeln(A, " =17?").
:-A=3*5+2, writeln(A, " =17?").
:-A=66 div 8, writeln(A, " =8?").
:-A=66 mod 8, writeln(A, " =2?").
:-writeln("").
:-A=3.5*5,writeln(A, " =17.5?").
:-A=7.8-2.2,writeln(A, " =5.6?").
:-A=14.3/5.5,writeln(A, " =2.6?").
:-A=-9.4, writeln(A, " =-9.4?").
:-A=2+3*5.5, writeln(A, " =18.5?").
:-A=3*5+2.4, writeln(A, " =17.4?").
:-A=66.4 div 8, writeln(A, " lauseke?").
:-A=66 mod 8.7, writeln(A, " lauseke?").


