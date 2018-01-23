koe :- write("[1:"),foreach_(A,[1,3,5]),!,write(A).
koe :- write("[2:").

:-write("<"),foreach_(A,[1,3,5]),!,write(A),fail.
:-write("<"),foreach_(A,[1,3,5]),!,write(A),foreach_(B,[2,4,6]),write(B),fail.

:-koe, fail.

:-dump("testiout.dump").

