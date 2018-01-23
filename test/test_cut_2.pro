koe :- write("[1:"),foreach_(A,[1,3,5]),!,foreach_(B,[7,8]),koe2,write(A,B).
koe :- write("[2:").

koe2 :- write("<1:"), !, foreach_(A,[2,4,6]), write(A).
koe2 :- write("<2:").

:-koe, fail.

:-dump("testiout.dump").

