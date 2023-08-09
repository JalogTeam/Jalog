% isname - test

test(S) :- writeq(S), isname(S), !, writeln(" is name.").
test(_) :- writeln(" is not name.").

:- test("").

:- test("_").

:- test(":").

:- test("A").

:- test("@").

:- test("~").

:- test("/").

:- test(":").

:- test("[").

:- test("^").

:- test("`").

:- test("{").

:- test("0").

:- test("9").

:- test("Z").

:- test("a").

:- test("z").


:- test("_x").

:- test(":x").

:- test("Ax").

:- test("@x").

:- test("~x").

:- test("/x").

:- test(":x").

:- test("[x").

:- test("^x").

:- test("`x").

:- test("{x").

:- test("0x").

:- test("9x").

:- test("Zx").

:- test("ax").

:- test("zx").


:- test("x_").

:- test("x:").

:- test("xA").

:- test("x@").

:- test("x~").

:- test("x/").

:- test("x:").

:- test("x[").

:- test("x^").

:- test("x`").

:- test("x{").

:- test("x0").

:- test("x9").

:- test("xZ").

:- test("xa").

:- test("xz").


:- test("x_y").

:- test("x:y").

:- test("xAy").

:- test("x@y").

:- test("x~y").

:- test("x/y").

:- test("x:y").

:- test("x[y").

:- test("x^y").

:- test("x`y").

:- test("x{y").

:- test("x0y").

:- test("x9y").

:- test("xZy").

:- test("xay").

:- test("xzy").


:- test("Variable#1").

:- test("Variable_$").

:- test("Variable_1").


