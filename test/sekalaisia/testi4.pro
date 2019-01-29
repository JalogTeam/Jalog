%:- write("joo"),writeln("pa joo"),fail, write("hupsis").
%:- writeln("toinen rivi").
:- write("-1-"), foreach_(A, []), write(A), write("-2-").
:- writeln("-3-").
:- write("->"),A=B, A=1, write(A,",",B),write("<-").
%:- write("->"),write(A=B),write("<-").
:- foreach_(I,["A","B","C"]), write(I,":"),foreach_(J,["X",I,"Y"]), write("(",I,J,")"), fail.
%:- write("->"), write(A,",",B,"..."),A=B, write(A,",",B),write("<-").

