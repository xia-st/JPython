package pers.xia.jpython.grammar.pgen;

// 表示 O->O 这种格式的FA单元
class DoubleS
{
    _State start;
    _State end;
    public DoubleS()
    {
        this.start = new _State();
        this.end = new _State();
    }
    
    public DoubleS(_State start, _State end)
    {
        this.start = start;
        this.end = end;
    }
    
    public DoubleS(_Arc arc)
    {
        start = new _State();
        start.addArc(arc);
        end = arc.nextState;
    }

    public DoubleS(_Label label)
    {
        start = new _State();
        end = new _State();
        _Arc arc = new _Arc(label, end);
        start.addArc(arc);
    }
}
    