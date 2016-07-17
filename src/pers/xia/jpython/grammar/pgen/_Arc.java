package pers.xia.jpython.grammar.pgen;

/* 
 * 用于Pgen生成数据
 * 由于Label类被替代，故Arc类也必须有对应的替代。
 */
class _Arc
{
    public _Label label;
    public _State nextState;

    public _Arc(_Label label, _State nextState)
    {
        this.label = label;
        this.nextState = nextState;
    }
}
