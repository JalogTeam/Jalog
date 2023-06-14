// BuiltIns.java

package io.github.JalogTeam.jalog;

public class BuiltIns
{
  static ClassInfo[] list = {
    new ClassInfo("!=", Pred__cmpr_.class, 2),
    new ClassInfo("<", Pred__cmpr_.class, 2),
    new ClassInfo("<=", Pred__cmpr_.class, 2),
    new ClassInfo("=", Pred__eq_.class, 2),
    new ClassInfo(">", Pred__cmpr_.class, 2),
    new ClassInfo(">=", Pred__cmpr_.class, 2),
    new ClassInfo("assert", Pred_assertz.class, 1),
    new ClassInfo("asserta", Pred_asserta.class, 1),
    new ClassInfo("assertz", Pred_assertz.class, 1),
    new ClassInfo("bound", Pred_bound.class, 1),
    new ClassInfo("concat", Pred_concat.class, 3),
    new ClassInfo("consult", Pred_consult.class, 1),
    new ClassInfo("consult_data", Pred_consult_data.class, 2),
    new ClassInfo("consult_dir", Pred_consult_dir.class, 1),
    new ClassInfo("cut_", Pred_cut_.class, 0),
    new ClassInfo("dump_", Pred_dump_.class, 1),
    new ClassInfo("dynamic", Pred_dynamic.class, 1),
    new ClassInfo("exit", Pred_exit.class, 0, 1),
    new ClassInfo("fail", Pred_fail.class, 0),
    new ClassInfo("findall", Pred_findall.class, 3),
    new ClassInfo("foreach_", Pred_foreach_.class, 2),
    new ClassInfo("free", Pred_free.class, 1),
    new ClassInfo("is_char", Pred_is_char.class, 1),
    new ClassInfo("is_compound", Pred_is_compound.class, 1),
    new ClassInfo("is_integer", Pred_is_integer.class, 1),
    new ClassInfo("is_list", Pred_is_list.class, 1),
    new ClassInfo("is_real", Pred_is_real.class, 1),
    new ClassInfo("is_string", Pred_is_string.class, 1),
    new ClassInfo("member", Pred_member.class, 2),
    new ClassInfo("nl", Pred_writeln.class, 0),
    new ClassInfo("not", Pred_not.class, 1),
    new ClassInfo("retract", Pred_retract.class, 1),
    new ClassInfo("retractall", Pred_retractall.class, 1),
    new ClassInfo("str_char", Pred_str_char.class, 2),
    new ClassInfo("substring", Pred_substring.class, 4),
    new ClassInfo("trap", Pred_trap.class, 3),
    new ClassInfo("write", Pred_write.class),
    new ClassInfo("writeln", Pred_writeln.class),
    new ClassInfo("writeq", Pred_writeq.class),

  };
}
