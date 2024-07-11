err(Err) :- writeln("  ERROR: ", Err).

koe1(Name) :- 
  writeln("koe1(", Name, ")"),
  trap(existfile(Name), Err, err(Err)),
  !,
  writeln("  - found").
koe1(_) :-
  writeln("  - not found").

:- koe1("res:/test_write.pro"). % exists
:- koe1("res:/testeioo.pro"). % does not exist
:- koe1("file:test_dir/appendtest.ref"). % exists
:- koe1("file:test_dir/nonexistent.ref"). % does not exist
:- koe1("file:d:/x.pro"). % illegal to ask but exists
