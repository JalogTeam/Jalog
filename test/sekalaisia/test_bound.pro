:- write("["),bound(1),write("]"),fail.
:- write("("),bound(A),write("****").
:- A = 1,write("["),bound(A),write("]"),fail.
:- A = B,write("("),bound(A),write("****").
:- A = B,write("("),bound(B),write("****").
:- A = 1, B = A, C = B,write("["),bound(C),write("]").

