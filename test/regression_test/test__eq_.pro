% = - test

% char
% compound
% integer
% list
% real
% string
% open variable


% koe1 expected: 


koe1a :- write("koe1a: "), 'a' = 'a', write('|'),nl.
koe1b :- write("koe1b: "), 'a' = 'b', write('|'),nl. % should fail
koe1b :- write('-'), nl.
koe1c :- write("koe1c: "), A = 'a', write(A), write('|'),nl.

koe2a :- write("koe2a: "), a('x') = a('x'), write('|'),nl.
koe2b :- write("koe2b: "), a('x') = a('y'), write('|'),nl. % should fail
koe2b :- write('-'), nl.
koe2c :- write("koe2c: "), a('x') = b('x'), write('|'),nl. % should fail
koe2c :- write('-'), nl.
koe2d :- write("koe2d: "), A = a('x'), write(A), write('|'),nl.
koe2e :- write("koe2e: "), A = a(B), A = a('x'), write(A, ' ', B), 
    write('|'),nl.

koe3a :- write("koe3a: "), 1 = 1, write('|'),nl.
koe3b :- write("koe3b: "), 1 = 2, write('|'),nl. % should fail
koe3b :- write('-'), nl.
koe3c :- write("koe3c: "), A = 1, write(A), write('|'),nl.

koe4a :- write("koe4a: "), ['a','x'] = ['a','x'], write('|'),nl.
koe4b :- write("koe4b: "), ['a','x'] = ['a','y'], write('|'),nl. % should fail
koe4b :- write('-'), nl.
koe4c :- write("koe4c: "), ['a','x'] = ['b','x'], write('|'),nl. % should fail
koe4c :- write('-'), nl.
koe4d :- write("koe4d: "), A = ['a','x'], write(A), write('|'),nl.
koe4e :- write("koe4e: "), A = ['a',B], A = ['a','x'], write(A, ' ', B), 
    write('|'),nl.

koe5a :- write("koe5a: "), 3.141 = 3.141, write('|'),nl.
koe5b :- write("koe5b: "), 3.141 = 2.8, write('|'),nl. % should fail
koe5b :- write('-'), nl.
koe5c :- write("koe5c: "), A = 3.141, write(A), write('|'),nl.
koe5d :- write("koe5d: "), 2 = 2.0, write('|'),nl.
koe5e :- write("koe5e: "), 2.0 = 2, write('|'),nl.

koe6a :- write("koe6a: "), "a" = "a", write('|'),nl.
koe6b :- write("koe6b: "), "a" = "b", write('|'),nl. % should fail
koe6b :- write('-'), nl.
koe6c :- write("koe6c: "), A = "a", write(A), write('|'),nl.

koe7a :- write("koe7a: "), A = A, write('|'),nl.
koe7b :- write("koe7b: "), A = B, write('|'),nl.
koe7c :- write("koe7c: "), A = B, A = 'z', write(B), write('|'),nl.

% Test main

:- koe1a.
:- koe1b. 
:- koe1c. 
 
:- koe2a. 
:- koe2b. 
:- koe2c. 
:- koe2d. 
:- koe2e. 

:- koe3a. 
:- koe3b. 
:- koe3c. 
   
:- koe4a. 
:- koe4b. 
:- koe4c. 
:- koe4d. 
:- koe4e. 
   
:- koe5a. 
:- koe5b. 
:- koe5c. 
:- koe5d. 
:- koe5e. 
   
:- koe6a. 
:- koe6b. 
:- koe6c. 
   
:- koe7a. 
:- koe7b. 
:- koe7c.
 