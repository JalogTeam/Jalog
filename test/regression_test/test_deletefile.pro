err(Err) :- writeln("  ERROR: ", Err).

koe1(Name) :- 
  writeln("koe1(", Name, ")"),
  trap(deletefile(Name), Err, err(Err)),
  !,
  writeln("  - found").
koe1(_) :-
  writeln("  - not found").

:- koe1("test_dir/deletetest.txt"). % exists
:- koe1("deletetest.txt"). % exists but forbidden
:- koe1("res:/testeioo.pro"). % does not exist
:- koe1("test_dir/soopaa.txt"). % does not exist
