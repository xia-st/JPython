package pers.xia.jpython.grammar;

public enum DFAType
{
    single_input,
    file_input,
    eval_input,
    decorator,
    decorators,
    decorated,
    async_funcdef,
    funcdef,
    parameters,
    typedargslist,
    tfpdef,
    varargslist,
    vfpdef,
    stmt,
    simple_stmt,
    small_stmt,
    expr_stmt,
    testlist_star_expr,
    augassign,
    del_stmt,
    pass_stmt,
    flow_stmt,
    break_stmt,
    continue_stmt,
    return_stmt,
    yield_stmt,
    raise_stmt,
    import_stmt,
    import_name,
    import_from,
    import_as_name,
    dotted_as_name,
    import_as_names,
    dotted_as_names,
    dotted_name,
    global_stmt,
    nonlocal_stmt,
    assert_stmt,
    compound_stmt,
    async_stmt,
    if_stmt,
    while_stmt,
    for_stmt,
    try_stmt,
    with_stmt,
    with_item,
    except_clause,
    suite,
    test,
    test_nocond,
    lambdef,
    lambdef_nocond,
    or_test,
    and_test,
    not_test,
    comparison,
    comp_op,
    star_expr,
    expr,
    xor_expr,
    and_expr,
    shift_expr,
    arith_expr,
    term,
    factor,
    power,
    atom_expr,
    atom,
    testlist_comp,
    trailer,
    subscriptlist,
    subscript,
    sliceop,
    exprlist,
    testlist,
    dictorsetmaker,
    classdef,
    arglist,
    argument,
    comp_iter,
    comp_for,
    comp_if,
    encoding_decl,
    yield_expr,
    yield_arg,

/* The follow dfa type are copyed from TokState.java */
/* The follow type a used to make analyze easier */

    ENDMARKER,
    NAME,
    NUMBER,
    STRING,
    NEWLINE,
    INDENT,
    DEDENT,
    LPAR,   // (
    RPAR,   // )
    LSQB,   // [
    RSQB,   // ]
    COLON,  // :
    COMMA,  // ,
    SEMI,   // ;
    PLUS,   // +
    MINUS,  // -
    STAR,   // *
    SLASH,  // /
    VBAR,   // |
    AMPER,  // &
    LESS,   // <
    GREATER,    // >
    EQUAL,  // =
    DOT,    // .
    PERCENT,    // %
    BACKQUOTE,
    LBRACE, // {
    RBRACE, // }
    EQEQUAL,    // ==
    NOTEQUAL,   // !=
    LESSEQUAL,  // <=
    GREATEREQUAL,   // >=
    TILDE,  // ~
    CIRCUMFLEX, // ^
    LEFTSHIFT,  // <<
    RIGHTSHIFT, // >>
    DOUBLESTAR, // **
    PLUSEQUAL,  // +=
    MINEQUAL,   // -=
    STAREQUAL,  // *=
    SLASHEQUAL, // /=
    PERCENTEQUAL,   // %=
    AMPEREQUAL, // &=
    VBAREQUAL,  // |=
    CIRCUMFLEXEQUAL,    // ^=
    LEFTSHIFTEQUAL, // <<=
    RIGHTSHIFTEQUAL,    // >>=
    DOUBLESTAREQUAL,    // **=
    DOUBLESLASH,    // \\
    DOUBLESLASHEQUAL,   // \\=
    AT, // @
    ATEQUAL,    // @=
    RARROW, // ->
    ELLIPSIS,   // ...
    OP,
    AWAIT,  // await
    ASYNC,  // async
    ERRORTOKEN,
    N_TOKENS,
}