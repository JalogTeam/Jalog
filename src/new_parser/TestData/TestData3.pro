test.toinen.% Jalog syntax test case
fact(yksi).
head :- body.
y:-assert(head :- body).
pred(X) :- X <> 1 - 2 * (3 + 4) + 5.
:- koe(1,-2,'3',[],[a],[b|[]],[c|x],[d|[e]],[f,g],[h,i|y]).
short("string").
long("monta"
" riviä"
" pitkä"
" string").
