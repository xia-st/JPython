package pers.xia.jpython.grammar.pgen;

import pers.xia.jpython.tokenizer.TokState;

/* 
 * 用于Pgen生成数据
 * Label类的替代。由于DFAName和DFA一开始没有定义，所以无法直接使用
 * 原来的Lable，故后面的操作会用这个_Label来代替。 
 */
class _Label
{
    String nextDfa;    //下一个DFA对象

    TokState tokState;
    String str;

    boolean isTerminal;

    public _Label(String nextDfa)
    {
        this.nextDfa = nextDfa;
        this.isTerminal = false;
    }

    public _Label(TokState tokState, String str)
    {
        this.tokState = tokState;
        this.str = str;
        this.isTerminal = true;
    }
    
    public boolean cmp(_Label label)
    {
        if(this.isTerminal != label.isTerminal) return false;
        if(this.isTerminal)
        {
            if(this.tokState == label.tokState)
            {
                //判断字符串是否相等，如果两个字符串都为null的话也应当被视作相等
                if(this.str == null && label.str == null) return true;
                if((this.str == null && label.str != null) || 
                        this.str != null & this.str == null)
                    return false;
                if(this.str.equals(label.str)) return true;
            }
        }
        else
        {
            if(this.nextDfa.equals(label.nextDfa)) return true;
            else return false;
        }
        return false;    // Make Compiler happy
    }
}
    