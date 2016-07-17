package pers.xia.jpython.grammar.pgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.tokenizer.TokState;

class Pgen
{
    _Grammar grammar;
    
    private int level;    //括号的等级
    private int cur;    //当前字符下标
    private String buf;    //每一行数据的缓存
    private BufferedReader br;    //文件缓存
    private boolean newLine;    //是否读取下一行数据

    public Pgen()
    {
        this.grammar = new _Grammar();
        this.level = 0;
        this.cur = 0;
        this.buf = null;
        this.newLine = false;
        
        //this.log = Logger.getLogger(Pgen.class);
    }
    
    public Pgen(File file)
    {
        this();
        try
        {
            this.br = new BufferedReader(
                    new FileReader(file));
        } catch (FileNotFoundException e)
        {
            //log.error("文件打开失败");
            throw new PyExceptions("Open file error");
        }
    }

    //读取下一行的grammar文件
    public boolean nextLine()
    {
        try
        {
            for(;;)
            {
                this.buf = this.br.readLine();
                if(this.buf == null) return false;
                this.buf = this.buf.trim();    //去掉首尾的非可打印字符(空格、制表符等)
                if(!this.buf.equals("")) break; //如果字符串中有数据的话跳出循环
            }
            this.cur = 0;
        } catch (IOException e)
        {
            throw new PyExceptions("buf read Error");
        }
        return true;
    }
    
    //获取下一个字符
    public char nextC()
    {
        char c;
        
        //如果newLine标记为true的话读取下一行数据
        if(this.newLine)
        {
            this.newLine = false;
            this.nextLine();
        }
        for(;;)
        {
            if(this.buf != null && this.cur < this.buf.length())
            {
                //fast page
                c = this.buf.charAt(this.cur++);
            }
            else if(this.buf == null)
            {
                //如果是刚开始没有数据的话则读取下一行。
                if(this.nextLine())
                {
                    c = this.buf.charAt(this.cur++);
                }
                else 
                {
                    return '\0';
                }
            }
            else if(this.level > 0)
            {
                /* 为了后面的方法能够保存这一行的数据，先不读取下
                 * 一行数据，而是设置一个标志，在下次运行本方法的
                 * 时候再读取，本次直接返回一个空格。
                 */
                this.newLine = true;
                return ' ';
            }
            else
            {
                return '\0';
            }
            
            //去掉注释
            if(c == '#')
            {
                this.nextLine();
                continue;
            }
            return c;
        }
    }
    
    //回退字符
    public void backup(char c)
    {
        if(c != '\0' && this.newLine == false)
        {
            if(--this.cur < 0)
            {
                throw new PyExceptions("token.backup: beginning of buffer");
            }
            if(this.buf.charAt(this.cur) != c)
            {
                throw new PyExceptions("token.backup: backup error");
            }
        }
    }
    
    public _NodeType oneChar(char c)
    {
        switch(c)
        {
        case '(': return _NodeType.LPAR;
        case ')': return _NodeType.RPAR;
        case '[': return _NodeType.LSQB;
        case ']': return _NodeType.RSQB;
        case '|': return _NodeType.VBAR;
        case '*': return _NodeType.STAR;
        case '+': return _NodeType.PLUS;
        }
        return null;
    }
    
    //获取下一个node
    public _Node nextNode()
    {
        _Node node = new _Node();
        char c;
        for(;;)
        {
            //跳过空白
            c = this.nextC();
            if(c == ' ' || c == '\t') continue;

            break;
        }
        
        //读取到一行的末尾
        if(c == '\0')    return null;
            
        //字符串
        if(c == '\'')
        {
            int start = this.cur;
            do
            {
                c = this.nextC();
                if(c == '\0')
                {
                    throw new PyExceptions("Can't find end quote");
                }
            }while(c != '\'');
            
            int end = this.cur - 1;
            node.type = _NodeType.STRING;
            node.value = this.buf.substring(start, end);
            
            return node;
        }
        
        //非终结符
        if(c >= 'A' && c <= 'Z')
        {
            int start = this.cur - 1;
            do
            {
                c = this.nextC();
            }while(c >= 'A' && c <= 'Z');
            
            this.backup(c);

            int end = this.cur;
            node.type = _NodeType.NAME;
            node.value = this.buf.substring(start, end);
            
            
            return node;
        }
        
        //DFA name
        if((c >= 'a' && c <= 'z') || c == '_')
        {
            int start = this.cur - 1;
            do
            {
                c = this.nextC();
            }while((c >= 'a' && c <= 'z') || c == '_');
            
            this.backup(c);
    
            int end = this.cur;
            node.type = _NodeType.DFANAME;
                        
            node.value = this.buf.substring(start, end);
            
            return node;
        }
        
        if(c == '(' || c == '[')
        {
            this.level++;
        }
        if(c == ')' || c == ']')
        {
            this.level--;
        }
        
        _NodeType type = this.oneChar(c);
        if(type != null)
        {
            node.type = type;
            node.value = Character.toString(c);
            return node;
        }
        throw new PyExceptions("Can't deal the character: " + c);
    }
    
    //获取每一行DFA的name
    public String getDFAName()
    {
        char c;
        for(;;)
        {
            c = this.nextC();
            
            if(c == ' ' || c == '\t')
            {
                continue;
            }
            break;
        }
        
        if(c == '\0')
        {
            throw new PyExceptions("Not a DFA name");
        }
        
        if((c < 'a' || c > 'z') && c != '_')
        {
            //log.error(Character.toString(c));
            throw new PyExceptions("Not a DFA name");
        }
        
        int start = this.cur - 1;
        do
        {
            c = this.nextC();
        }while((c >= 'a' && c <= 'z') || c == '_');
        
        int end = this.cur - 1;
        
        while(c == ' ' || c == '\t')
        {
            c = this.nextC();
        }
        if(c != ':')
        {
            throw new PyExceptions("Can't find char \":\"");
        }
        
        return this.buf.substring(start, end);
    }
        
    
    //下面代码开始创建语法树的代码。
    
    //收缩结点
    public _Node shrink(Stack<_Node> nodeS)
    {
        _Node orNode = new _Node();
        orNode.type = _NodeType.OR;
        _Node node = null;

        while(!nodeS.empty())
        {
            node = nodeS.pop();
            orNode.addFirstChild(node);

            if(nodeS.empty())
            {
                break;
            }

            node = nodeS.pop();

            if(node.type != _NodeType.VBAR)
            {
                throw new PyExceptions("nodeS Structure confusion");
            }
        }
        return orNode.childs.size() == 1 ?  orNode.childs.getLast() : orNode;
    }
    
    //以括号为判断标志收缩结点
    public _Node shrink(Stack<_Node> nodeS, _NodeType type)
    {
        _Node orNode = new _Node();
        orNode.type = _NodeType.OR;
        _Node node = null;

        while(!nodeS.empty())
        {
            node = nodeS.pop();
            
            if(node.type == type)
            {
                throw new PyExceptions("Empty node");
            }
            
            orNode.addFirstChild(node);

            if(nodeS.empty())
            {
                throw new PyExceptions("Can't find type: " + type);
            }
            node = nodeS.pop();
            
            if(node.type == type) break;
            
            if(node.type != _NodeType.VBAR)
            {
                throw new PyExceptions("nodeS Structure confusion");
            }
        }
        if(node.type == type)
        {
            return orNode.childs.size() == 1 ?  orNode.childs.getLast() : orNode;
        }
        throw new PyExceptions("Can't find " + type.toString());
    }
    
    public _Node createTree()
    {
        Stack<_Node> nodeS = new Stack<_Node>();
        _Node node = null;
        _Node nodeAhead = null;
        boolean hasAhead = false;
        
        for(;;)
        {
            node = hasAhead ? nodeAhead : this.nextNode();
            hasAhead = false;
            
            if(node == null) break;

            if(node.type == _NodeType.VBAR ||
                    node.type == _NodeType.LPAR ||
                    node.type == _NodeType.LSQB)
            {
                nodeS.push(node);
                continue;
            }
            else if(node.type == _NodeType.RPAR)
            {
                node = this.shrink(nodeS, _NodeType.LPAR);
            }
            else if(node.type == _NodeType.RSQB)
            {
                //把RSQB作为”选择“逻辑的标志
                _Node resultNode = this.shrink(nodeS, _NodeType.LSQB);
                node.addChild(resultNode);
            }
            else if(node.type != _NodeType.NAME &&
                    node.type != _NodeType.STRING &&
                    node.type != _NodeType.DFANAME)
            {
                //只有符合右边元素的才能进行接下来的操作。
                throw new PyExceptions("Unknow Node: " + node.toString());
            }
            
            //预读下一个Node
            nodeAhead = this.nextNode();
            if(nodeAhead != null)
            {
                if(nodeAhead.type == _NodeType.PLUS ||
                        nodeAhead.type == _NodeType.STAR)
                {
                    nodeAhead.addChild(node);
                    node = nodeAhead;
                }
                else
                {
                    hasAhead = true;
                }
            }
            
            if(nodeS.empty())
            {
                nodeS.push(node);
                continue;
            }        

            _Node preNode = nodeS.pop();

            if(preNode.type == _NodeType.AND)
            {
                /* 
                 * 前一个Node的Type为AND的话把该结点放入到前一个结点中，
                 */
                preNode.addChild(node);
                nodeS.push(preNode);
                continue;
            }
            
            if(preNode.type != _NodeType.VBAR &&
                    preNode.type != _NodeType.LPAR &&
                    preNode.type != _NodeType.LSQB)
            {
                /*
                 * 前一个Node的Type为这几者之一的话就用AND Type的结点将他们
                 * 连起来
                 */
                
                _Node optNode = new _Node();
                optNode.type = _NodeType.AND;
                optNode.addChild(preNode);
                optNode.addChild(node);
                nodeS.push(optNode);
                continue;
            }
            nodeS.push(preNode);
            nodeS.push(node);
        }
        node = this.shrink(nodeS);

        return node;
    }
    
    public void showTree(_Node tree)
    {
        System.out.println(tree);
        List<_Node> childs = tree.childs;
        List<_Node> childs2;
        
        while(!childs.isEmpty())
        {
            childs2 = new LinkedList<_Node>();
            for(_Node node : childs)
            {
                System.out.print(node + " ");
                childs2.addAll(node.childs);
            }
            System.out.println();
            childs = childs2;
        }
    }
    
    
    //下面代码开始创建FA

    //将字符串转换为对应的TokState
    private TokState findTokState(String name)
    {
        switch(name)
        {
        case "(": return TokState.LPAR;    // (
        case ")": return TokState.RPAR;    // )
        case "[": return TokState.LSQB;    // [
        case "]": return TokState.RSQB;    // ]
        case ":": return TokState.COLON;    // :
        case ",": return TokState.COMMA;    // ,
        case ";": return TokState.SEMI;    // ;
        case "+": return TokState.PLUS;    // +
        case "-": return TokState.MINUS;    // -
        case "*": return TokState.STAR;    // *
        case "/": return TokState.SLASH;    // /
        case "|": return TokState.VBAR;    // |
        case "&": return TokState.AMPER;    // &
        case "<": return TokState.LESS;    // <
        case ">": return TokState.GREATER;    // >
        case "=": return TokState.EQUAL;    // =
        case ".": return TokState.DOT;    // .
        case "%": return TokState.PERCENT;    // %
        case "{": return TokState.LBRACE;        // {
        case "}": return TokState.RBRACE;        // }
        case "==": return TokState.EQEQUAL;    // ==
        case "!=": return TokState.NOTEQUAL;    // !=
        case "<>": return TokState.NOTEQUAL;    // <>
        case "<=": return TokState.LESSEQUAL;    // <=
        case ">=": return TokState.GREATEREQUAL;    // >=
        case "~": return TokState.TILDE;    // ~
        case "^": return TokState.CIRCUMFLEX;    // ^
        case "<<": return TokState.LEFTSHIFT;        // <<
        case ">>": return TokState.RIGHTSHIFT;    // >>
        case "**": return TokState.DOUBLESTAR;    // **
        case "+=": return TokState.PLUSEQUAL;    // +=
        case "-=": return TokState.MINEQUAL;    // -=
        case "*=": return TokState.STAREQUAL;    // *=
        case "/=": return TokState.SLASHEQUAL;    // /=
        case "%=": return TokState.PERCENTEQUAL;    // %=
        case "&=": return TokState.AMPEREQUAL;    // &=
        case "|=": return TokState.VBAREQUAL;    // |=
        case "^=": return TokState.CIRCUMFLEXEQUAL;    // ^=
        case "<<=": return TokState.LEFTSHIFTEQUAL;    // <<=
        case ">>=": return TokState.RIGHTSHIFTEQUAL;    // >>=
        case "**=": return TokState.DOUBLESTAREQUAL;    // **=
        case "//": return TokState.DOUBLESLASH;    // \\
        case "//=": return TokState.DOUBLESLASHEQUAL;    // \\=
        case "@": return TokState.AT;    // @
        case "@=": return TokState.ATEQUAL; // @=
        case "->": return TokState.RARROW;        // ->
        case "...": return TokState.ELLIPSIS;    // ...
        case "await": return TokState.AWAIT;    // await
        case "async": return TokState.ASYNC;    // async
        }
        return null;
    }
    
    //连接子结点。该函数为递归调用
    private DoubleS connChild(_Node node)
    {
        if(node.childs.isEmpty())
        {
            _Label label = null;
            if(node.type == _NodeType.NAME)
            {
                label = new _Label(TokState.valueOf(node.value), null);
            }
            else if(node.type == _NodeType.STRING)
            {
                TokState tokState = this.findTokState(node.value);
                if(tokState != null)
                {
                    label = new _Label(tokState, null);
                }
                else
                {
                    label = new _Label(TokState.NAME, node.value);
                }
            }
            else if(node.type == _NodeType.DFANAME)
            {
                label = new _Label(node.value);
            }
            else
            {
                throw new PyExceptions("Node Error: " + node.type);
            }
            label = this.grammar.setLabel(label);
            return new DoubleS(label);
        }
        
        if(node.type == _NodeType.AND)
        {
            _State start = new _State();
            _State end = start;
            DoubleS ds = null;
            for(_Node cNode : node.childs)
            {
                ds = this.connChild(cNode);
                _Arc arc = new _Arc(null, ds.start);    //把null作为Label的参数，说明这个Arc是空指向
                end.addArc(arc);
                end = ds.end;
            }
            return new DoubleS(start, end);
        }
        
        if(node.type == _NodeType.OR)
        {
            DoubleS ds = new DoubleS();
            DoubleS ds2 = null;
            for(_Node cNode : node.childs)
            {
                ds2 = this.connChild(cNode);
                _Arc arc1 = new _Arc(null, ds2.start);    //从ds.start指向ds2.start的Arc
                _Arc arc2 = new _Arc(null, ds.end);    //从ds2.end指向ds.end的Arc
                
                ds.start.addArc(arc1);
                ds2.end.addArc(arc2);
            }
            return ds;
        }
        
        if(node.type == _NodeType.RSQB)
        {
            if(node.childs.size() != 1)
            {
                throw new PyExceptions("node's childs is more then one");
            }
            DoubleS ds = this.connChild(node.childs.get(0));
            _Arc arc = new _Arc(null, ds.end);
            ds.start.addArc(arc);
            return ds;
        }
        
        if(node.type == _NodeType.PLUS)
        {
            if(node.childs.size() != 1)
            {
                throw new PyExceptions("node's childs is more then one");
            }
            DoubleS ds1 = this.connChild(node.childs.get(0));
            DoubleS ds2 = this.connChild(node.childs.get(0));
            
            _Arc arc1 = new _Arc(null, ds2.start);
            _Arc arc2 = new _Arc(null, ds1.end);
            
            ds1.end.addArc(arc1);
            ds2.end.addArc(arc2);
            
            return ds1;
        }
        
        if(node.type == _NodeType.STAR)
        {
            if(node.childs.size() != 1)
            {
                throw new PyExceptions("node's childs is more then one");
            }
            DoubleS ds1 = this.connChild(node.childs.get(0));
            DoubleS ds2 = this.connChild(node.childs.get(0));
            
            _Arc arc1 = new _Arc(null, ds2.start);
            _Arc arc2 = new _Arc(null, ds1.end);
            _Arc arc3 = new _Arc(null, ds1.end);
            
            ds1.end.addArc(arc1);
            ds2.end.addArc(arc2);
            ds1.start.addArc(arc3);
            
            return ds1;
        }
        throw new PyExceptions("Node Error: " + node.type);
    }
    
    private DoubleS createFA(_Node tree)
    {
        //添加EMPTY到grammar中
        _Label label = new _Label("EMPTY");
        label = this.grammar.setLabel(label);
        
        DoubleS ds = connChild(tree);
        
        //添加最后EMPTY标志
        _Arc arc = new _Arc(label, null);
        ds.end.addArc(arc);
        
        return ds;
    }
    
    private void removeNonArc(_State startS)
    {
        Stack<_State> stateStack = new Stack<_State>();        //保存经过的结点
        Set<_State> stateSet = new HashSet<_State>();    //保存已经处理过的结点
        
        Map<_State, Stack<_Arc>> map = new HashMap<_State, Stack<_Arc>>();    //保存每个state与它需要添加的arc
        
        _State state = null; 
        
        stateStack.push(startS);
        while(!stateStack.empty())
        {
            state = stateStack.pop();
            if(stateSet.contains(state))
            {
                continue;
            }
            stateSet.add(state);
                        
            Stack<_Arc> lastedArcs = new Stack<_Arc>();    // Saved arc where lasted pass through
            Stack<_Arc> nonArcs = new Stack<_Arc>();    //Saved non arcs;
            Set<_Arc> checkedArcs = new HashSet<_Arc>();    // Saved checked arcs
            
            // Collect all Non Arc from state
            for(int i = 0; i < state.narcs; i++)
            {
                _Arc arc = state.arcs[i];
                if(arc.label == null)
                {
                    nonArcs.push(arc);
                }
                else
                {
                    if(arc.nextState != null) 
                        stateStack.push(arc.nextState);
                }
            }
            if(nonArcs.empty()) continue; // If the state don't have any Non Arc, then passed the state
            
            //Find all last Arcs.
            while(!nonArcs.empty())
            {
                _Arc nonArc = nonArcs.pop();
                if(checkedArcs.contains(nonArc))
                {
                    continue;
                }
                checkedArcs.add(nonArc);
                
                _State state2 = nonArc.nextState;
                stateStack.push(state2);
                
                for(int i = 0; i < state2.narcs; i++)
                {
                    _Arc arc = state2.arcs[i];
                    if(arc.label != null)
                    {
                        lastedArcs.add(arc);
                        continue;
                    }
                    nonArcs.push(arc);
                }
            }
            map.put(state, lastedArcs);
        }
        
        //Connect states
        for(Map.Entry<_State, Stack<_Arc>> sa : map.entrySet())
        {
            _State state2 = sa.getKey(); 
            state2.removeAllArc(null);
            List<_Arc> arcList = new ArrayList<_Arc>();
            for(_Arc arc : sa.getValue())
            {
                //Judge the arc is or not repeated
                int i = 0;
                for(;i < arcList.size(); i++)
                {
                    if(arcList.get(i).label == arc.label &&
                            arcList.get(i).nextState == arc.nextState)
                    {
                        break;
                    }
                }
                if(i < arcList.size()) continue;
                
                _Arc newArc = new _Arc(arc.label, arc.nextState);
                arcList.add(newArc);
                
                state2.addArc(newArc);
            }
        }
    }
    
    private boolean cmpStates(Set<_State> ss1, Set<_State> ss2)
    {
        if(ss1.size() != ss2.size()) return false;
        if(!ss1.containsAll(ss2)) return false;
        return true;
    }
    
    private boolean addEMPTYArc(_State state, _Label empty)
    {
            int i = 0;
            for(; i < state.narcs; i++)
            {
                _Arc arc2 = state.arcs[i]; 
                if(arc2.label == empty)
                {
                    break;
                }
            }
            if(i >= state.narcs)
            {
                _Arc arc2 = new _Arc(empty, null);
                state.addArc(arc2);
            }
        return true;
    }
    
    public _State modifyToDFA(_State startS)
    {
        Map<Set<_State>, _State> stateMap = new HashMap<Set<_State>, _State>();    //save the Mapping about new state and collection
        Set<_State> curStateS = new HashSet<_State>();    //current collection
        // Set<Set<_State>> sss = new HashSet<Set<_State>>();
        Stack<Set<_State>> stateStack = new Stack<Set<_State>>(); // Created but not be dealed collection
        
        _State state = new _State();
        
        //put initial date to map
        curStateS.add(startS);
        stateMap.put(curStateS, state);
        stateStack.push(curStateS);
        
        startS = state;    // set new start state
        
        while(!stateStack.empty())
        {
            curStateS = stateStack.pop();
            
            /*
            if(sss.contains(curStateS))
                continue;
            sss.add(curStateS);
            */

            if(curStateS.isEmpty()) continue;
            List<_Arc> arcList = new LinkedList<_Arc>();
            
            //get all arc from curStateS
            for(_State s: curStateS)
            {
                for(int i = 0; i < s.narcs; i++)
                {
                    arcList.add(s.arcs[i]);
                }
            }
            
            
            //Arc's where Same label can towards
            Map<_Label, Set<_State>> laMap = new HashMap<_Label, Set<_State>>(); 
            
            _Label empty = null;

            for(_Arc arc : arcList)
            {
                if(!arc.label.isTerminal &&
                        arc.label.nextDfa.equals("EMPTY"))
                {
                    empty = arc.label;
                    continue;
                }
                if(laMap.containsKey(arc.label))
                {
                    laMap.get(arc.label).add(arc.nextState);
                    continue;
                }
                Set<_State> set = new HashSet<_State>();
                set.add(arc.nextState);
                laMap.put(arc.label, set);
                
                // laMap.put(arc.label, new Set<_Arc>(){{add(arc);}}); // compiler will unhappy
            }
            
            // check and add EMPTY arc to current state
            if(empty != null)
            {
                this.addEMPTYArc(stateMap.get(curStateS), empty);
            }
            
            /* 
             * Check collection, if exists, link current state to exist state, else
             * create new state in stateMap
             */
            
            for(Map.Entry<_Label, Set<_State>> ss : laMap.entrySet())
            {
                boolean flag = false;    //whether or not find
                for(Set<_State> sk : stateMap.keySet())
                {
                    // if exists
                    if(this.cmpStates(ss.getValue(), sk))
                    {
                        flag = true;

                        //connect curState to found state;
                        _Arc arc = new _Arc(ss.getKey(), stateMap.get(sk));
                        stateMap.get(curStateS).addArc(arc);
    
                        break;
                    }
                }
                
                if(flag) continue;
                
                _State newState = new _State();
                _Arc arc = new _Arc(ss.getKey(), newState);
                stateMap.get(curStateS).addArc(arc);
                
                stateMap.put(ss.getValue(), newState);
                
                stateStack.push(ss.getValue());
            }
        }
        return startS;
    }
    
    private boolean isFinalState(_State state)
    {
        for(int i = 0; i < state.narcs; i++)
        {
            if(state.arcs[i].nextState == null)
            {
                return true;
            }
        }
        return false;
    }
    
    private _State getOneStateFromSet(Set<_State> states)
    {
        for(_State state : states)
        {
            return state;
        }
        return null; 
    }
    
    //设置one2oneMap和one2multiMap的初始状态
    private void initOoOmMap(_State startS, 
            Map<_State, _State> one2oneMap, 
            Map<_State, Set<_State>> one2multiMap)
    {
        Set<_State> finalStates = new HashSet<_State>();
        Set<_State> normalStates = new HashSet<_State>();
        
        Stack<_State> stateStack = new Stack<_State>();
        Set<_State> allState = new HashSet<_State>();
        
        stateStack.push(startS);
        
        // divide state to normal state and final state
        while(!stateStack.empty())
        {
            _State state = stateStack.pop();
            
            if(allState.contains(state)) continue;
            allState.add(state);
            
            if(this.isFinalState(state)) finalStates.add(state);
            else normalStates.add(state);
            
            for(int i = 0; i < state.narcs; i++)
            {
                if(state.arcs[i].nextState != null)
                {
                    stateStack.push(state.arcs[i].nextState);
                }
            }
        }
        
        _State normalState = null;
        _State finalState = null;
        
        /* get one state in normal states
         * have to insure the startS is the sign of collection
         */
        
        if(normalStates.contains(startS))
            normalState = startS;
        else
            normalState = this.getOneStateFromSet(normalStates);
        
        if(finalStates.contains(startS))
            finalState = startS;
        else
            finalState = this.getOneStateFromSet(finalStates);
        
        if(normalState != null)
        {
            for(_State state : normalStates)
            {
                one2oneMap.put(state, normalState);
            }
            one2multiMap.put(normalState, normalStates);
        }
        
        if(finalState == null) 
            throw new PyExceptions("Not find final state");
        for(_State state : finalStates)
        {
            one2oneMap.put(state, finalState);
        }
        one2multiMap.put(finalState, finalStates);
    }
    
    //dfa最小化
    private void minimize(_State startS)
    {
        //state of key mean the raw state, state of value mean the colleaction's representative
        Map<_State, _State> one2oneMap = new HashMap<_State, _State>();
        Map<_State, Set<_State>> one2multiMap = new HashMap<_State, Set<_State>>();
        
        this.initOoOmMap(startS, one2oneMap, one2multiMap);
        
        boolean changedCollecton;   //标记集合是否被拆分
        for(;;)
        {
            changedCollecton = false;
            
            /*
             * 由于在循环体内对Map进行增加删除操作可能会导致ConcurrentModificationException异常，
             * 所以在这里设置了个setCache用于保存循环中需要在one2multiMap中创建的集合
             */
            List<Set<_State>> setCache = new ArrayList<Set<_State>>();
            
            for(Map.Entry<_State, Set<_State>> o2m : one2multiMap.entrySet())
            {
                _State markedState = o2m.getKey();
                Set<_State> states = o2m.getValue();
                
                //集合内长度为1就不需要进行判断了
                if(states.size() == 1)
                {
                    continue;
                }
                
                //key为指向的state集合，value为指向集合为key的所有state的集合
                Map<Set<_State>, Set<_State>> m2mMap = new HashMap<Set<_State>, Set<_State>>();
                
                for(_State state : states)
                {
                    Set<_State> nextStates = new HashSet<_State>();
                    for(int i = 0; i < state.narcs; i++)
                    {
                        if(state.arcs[i].nextState != null)
                            nextStates.add(state.arcs[i].nextState);
                    }
                    
                    // 相同指向的所有state集合
                    Set<_State> valueStates = m2mMap.getOrDefault(nextStates, null);
                    
                    if(valueStates != null)
                    {
                        valueStates.add(state);
                    }
                    else
                    {
                        valueStates = new HashSet<_State>();
                        valueStates.add(state);
                        m2mMap.put(nextStates, valueStates);
                    }
                }
                
                //如果m2mMap长度为1的话则不需要划分
                if(m2mMap.size() == 1)
                {
                    continue;
                }
                changedCollecton = true;
                
                for(Set<_State> newStates : m2mMap.values())
                {
                    //如果存在标记结点，就不需要对此创建新的集合。
                    if(newStates.contains(markedState)) continue;
                    
                    states.removeAll(newStates);
                    
                    // 将需要新增的集合放入到setCache中，用于后面的新增操作
                    setCache.add(newStates);

                }
            }
            
            for(Set<_State> newStates : setCache)
            {               
                    _State markedState2 = this.getOneStateFromSet(newStates);
                    one2multiMap.put(markedState2, newStates);
                    
                    //将one2oneMap中在newStates中出现的标记结点都改为markedStates2
                    newStates.forEach((s) -> one2oneMap.put(s, markedState2));
            }
            
            if(!changedCollecton) break;
        }
 
        //连接新的结点
        for(Map.Entry<_State, Set<_State>> om : one2multiMap.entrySet())
        {
            _State state = om.getKey();
            _State nextState = null;
            for(int i = 0; i < state.narcs; i++)
            {
                _Arc curArc = state.arcs[i];
                nextState = curArc.nextState;
                if(nextState != null)
                {
                    curArc.nextState = one2oneMap.get(nextState);
                }
            }
        }
        
        
        return;
    }
    
    // 设置可以跳转到本处的DFA
    private void setNextTrueDFA(_DFA dfa)
    {
        if(dfa.jumpedDFAs != null) return;
        dfa.jumpedDFAs = new HashMap<_Label, _DFA>();
        
        List<_Label> labels = dfa.getAllStartLabel();
        
        for(_Label label : labels)
        {


            /* 
             * 如果是终结符的话就指向本dfa，否则就获取下一个终结符中jumpedDFAs的所有label放入
             * 到本终结符的jumpedDFAs中，并设置DFA为nextDfa
             */
            
            if(label.isTerminal)
            {
                dfa.jumpedDFAs.put(label, dfa);
            }
            else
            {
                _DFA nextDfa = this.grammar.getDFA(label.nextDfa);
                if(nextDfa.jumpedDFAs == null) this.setNextTrueDFA(nextDfa);
                
                for(Map.Entry<_Label, _DFA> ld : nextDfa.jumpedDFAs.entrySet())
                {
                    if(dfa.jumpedDFAs.containsKey(ld.getKey()))
                    {
                        throw new PyExceptions("same label");
                    }
                    dfa.jumpedDFAs.put(ld.getKey(), nextDfa);
                }
            }
        }
    }
    
    public void showFA(_State state)
    {
        Stack<_State> stateStack = new Stack<_State>();
        Stack<_State> stateStack2 = new Stack<_State>();
        stateStack.push(state);
        
        HashSet<_State> stateSet = new HashSet<_State>();    //保存已经访问过的State，防止重复遍历
        
        for(;;)
        {
            while(!stateStack.empty())
            {
                state = stateStack.pop();
                if(stateSet.contains(state))    //如果某个State已经访问过，那么就访问下一个State
                {
                    continue;
                }
                stateSet.add(state);
                
                //开始遍历state的所有arc。
                for(int i = 0; i < state.narcs; i++)
                {
                    _Arc arc = state.arcs[i];
                    _State state2 = arc.nextState;
                    if(state2 == null)
                    {
                        if(!arc.label.isTerminal && arc.label.nextDfa == "EMPTY")
                        {
                            System.out.println(state.hashCode() + " final state");
                            continue;
                        }
                        throw new PyExceptions("State ERROR");
                    }
                    
                    stateStack2.push(state2);
                                    
                    if(arc.label == null)
                    {
                        System.out.println(state.hashCode() + " -> " + state2.hashCode());
                    }
                    else if(arc.label.isTerminal)
                    {
                        System.out.println(state.hashCode() + " -> " + arc.label.hashCode() + " " +
                                arc.label.tokState  + " " + arc.label.str + " -> " +
                                state2.hashCode());
                    }
                    else
                    {
                        System.out.println(state.hashCode() + " -> " + arc.label.hashCode() + 
                                " DFA " + arc.label.nextDfa + 
                                " -> " + state2.hashCode());
                    }
                }
            }
            if(stateStack2.empty()) break;
            
            stateStack = stateStack2;
            stateStack2 = new Stack<_State>();
            
        }
        System.out.println();
    }
    
    public boolean createGrammar()
    {
        do
        {
            String DFAName = this.getDFAName();
            _Label label = new _Label(DFAName);
            this.grammar.setLabel(label);
            _DFA dfa = new _DFA(DFAName);
            
            _Node tree = this.createTree();
            DoubleS ds = this.createFA(tree);
            this.removeNonArc(ds.start);
            

            
            dfa.initial = this.modifyToDFA(ds.start);
            
            System.out.println(dfa.name);
            this.showFA(dfa.initial);
            
            this.minimize(dfa.initial);

            this.showFA(dfa.initial);
            dfa.setStates();
            this.grammar.setDFA(dfa);
            
            // break;
        }while(this.nextLine());
        
        for(int i = 0; i < this.grammar.ndfas; i++)
        {
            this.setNextTrueDFA(this.grammar.dfas[i]);
            
            /*
            System.out.println(this.grammar.dfas[i].name);
            for(Map.Entry<_Label, _DFA> jd : this.grammar.dfas[i].jumpedDFAs.entrySet())
            {
                System.out.println(jd.getKey().tokState + " " + jd.getValue().name);
            }
            System.out.println();
            */
        }
        
        //设置起始dfa
        /* 这段代码毫无无意义
        for(int i = 0; i < this.grammar.ndfas; i++)
        {
            if(this.grammar.dfas[i].name.equals("file_input"))
            {
                //把起始dfa和0号位的dfa交换
                _DFA dfa = this.grammar.dfas[0];
                this.grammar.dfas[0] = this.grammar.dfas[i];
                this.grammar.dfas[i] = dfa;
                
                this.grammar.start = this.grammar.dfas[0];
                break;
            }
        }
        */

        return true;
    }

    //绘制所有label对应的代码字符串
    private StringBuilder labelToString(Map<String, String> DFAStringMap)
    {      
        StringBuilder sb = new StringBuilder();
        sb.append("    public static Label[] labels = {\n");
        _Label label = null;
        for(int i = 0; i < this.grammar.nlabels; i++)
        {
            label = this.grammar.labels[i];
            if(label.isTerminal)
            {
                if(label.str == null)
                {
                    sb.append("        new Label(TokState." + label.tokState.toString() + ", null),");
                }
                else
                {
                    sb.append("        new Label(TokState." + label.tokState.toString() + ", \"" + label.str + "\"),");
                }
            }
            else
            {
                sb.append("        new Label(" + DFAStringMap.getOrDefault(label.nextDfa, "-1") + "),");
            }
            sb.append("\n");
        }
        sb.append("    };\n\n");
        return sb;
    }
 
    //绘制某个state所有arc的代码字符串
    private StringBuilder arcToString(_State state, 
            String arcsName, 
            Map<_State, String>stateStringMap,
            Map<_Label, String>labelStringMap,
            String fileName)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("    public static Arc[] " + arcsName + " = {\n");
        
        for(int i = 0; i < state.narcs; i++)
        {
            sb.append("        new Arc(" + labelStringMap.get(state.arcs[i].label) +  ", " +
                    stateStringMap.getOrDefault(state.arcs[i].nextState, "-1") + 
                    "),\n");
        }
        sb.append("    };\n\n");
        
        return sb;
    }

    
    //返回某个DFA所有state的代码字符串，并设置state与名字的对应关系
    private StringBuilder stateToString(_DFA dfa, 
            String statesName, 
            Map<_Label, String>labelStringMap, 
            int index,
            String fileName) 
    {
        StringBuilder sb = new StringBuilder();
        
        //保存arc数组与它对应的名字
        Map<_Arc[], String> arcsStringMap = new HashMap<_Arc[], String>(); 
        //state数组中所对应的states的名字（like states_1[0]）
        Map<_State, String> stateStringMap = new HashMap<_State, String>();
        
        
        //设置state与它对应的名字
        for(int i = 0; i < dfa.nstates; i++)
        {
            stateStringMap.put(dfa.states[i], Integer.toString(i));
        }
        
        //添加arc的数据
        for(int i = 0; i < dfa.nstates; i++)
        {
            sb.append(this.arcToString(dfa.states[i], 
                    "arcs_" + index + "_" + i, 
                    stateStringMap, 
                    labelStringMap, fileName));
            arcsStringMap.put(dfa.states[i].arcs, fileName + ".arcs_" + index + "_" + i);
        }

        
        sb.append("    public static State[] " + statesName + " = {\n");
        
        for(int i = 0; i < dfa.nstates; i++)
        {
            sb.append("        new State(" + dfa.states[i].narcs + ", " + 
                    arcsStringMap.get(dfa.states[i].arcs) + "),\n");
        }
        
        sb.append("    };\n\n");
        
        return sb;
    }
    
    private StringBuilder acceptLabelToString(Set<_Label>acceptLabel,
            String jumpedDFAName,
            Map<_Label, String> labelStringMap,
            Map<String, String> DFAStringMap)
    {
        StringBuilder sb = new StringBuilder();
        
        String jumped[] = new String[acceptLabel.size()];
        
        
        int index = 0;
        for(_Label label  : acceptLabel)
        {
            jumped[index++] = labelStringMap.get(label);
        }
        
        sb.append("    public final static int[] " + jumpedDFAName + " = {");
        for(int i = 0; i < jumped.length; i++)
        {
            sb.append(jumped[i] + ", ");
        }
        
        sb.append("};\n");
        return sb;
    }
    
    //绘制所有的DFA
    private StringBuilder DFAToString(_Grammar grammar, 
            Map<_Label, String>labelStringMap, 
            Map<String, String>DFAStringMap,
            String fileName)
    {
        
       StringBuilder sb = new StringBuilder();
       
       //保存states数组与它对应的名字
       Map<_State[], String> statesStringMap = new HashMap<_State[], String>();
       
       //添加所有states的字符串
       for(int i = 0; i < grammar.ndfas; i++)
       {
           sb.append(this.stateToString(grammar.dfas[i], 
                   "states_" + i, 
                   labelStringMap, 
                   i,
                   fileName));
           statesStringMap.put(grammar.dfas[i].states, "states_" + i);
       }
       
       //设置DFA与对应的名字
       for(int i = 0; i < grammar.ndfas; i++)
       {
           DFAStringMap.put(grammar.dfas[i].name, Integer.toString(i));
       }
       
       //保存jumpedDFA与它对应的名字
        Map<Map<_Label, _DFA>, String> jumpedDFAStringMap = new  HashMap<Map<_Label, _DFA>, String>();

       for(int i = 0; i < grammar.ndfas; i++)
       {
           sb.append(this.acceptLabelToString(grammar.dfas[i].jumpedDFAs.keySet(), 
                   "acceptLabel_" + i, 
                   labelStringMap, 
                   DFAStringMap));
           jumpedDFAStringMap.put(grammar.dfas[i].jumpedDFAs, "acceptLabel_" + i);
       }
       
       sb.append("    public static DFA[] dfas = {\n");
       

       //设置所有的DFA
       for(int i = 0; i < grammar.ndfas; i++)
       {
           sb.append("        new DFA(" +
                   "DFAType." + grammar.dfas[i].name + ", " +
                   "0, " +
                   grammar.dfas[i].nstates + ", " +
                   fileName + "." + statesStringMap.get(grammar.dfas[i].states) + ", " +
                   jumpedDFAStringMap.get(grammar.dfas[i].jumpedDFAs) + 
                   "),\n");
       }

       sb.append("    };\n\n");
        return sb;
    }
    

    public StringBuilder grammarToString(_Grammar grammar, String fileName)
    {
        //label对应的名字
        Map<_Label, String> labelStringMap = new HashMap<_Label, String>();
        
        //dfa名字所对应的实际名字
        Map<String, String> DFAStringMap = new HashMap<String, String>();
        
        //逆转label和它的下标
        String labelsName = "%d";
        for(int i = 0; i < this.grammar.nlabels; i++)
        {
            labelStringMap.put(this.grammar.labels[i], String.format(labelsName, i));
        }
        
        //逆转label和它的下标
        String DFAType = "%d";
        for(int i = 0; i < this.grammar.ndfas; i++)
        {
            DFAStringMap.put(this.grammar.dfas[i].name, String.format(DFAType, i));
        }
        

        //保存整个语法文件的内容
        StringBuilder sb = new StringBuilder(); 
        
        sb.append("/* Created by Pgen */\n");
        sb.append("package pers.xia.jpython.grammar;\n\n" +
                "import pers.xia.jpython.tokenizer.TokState;\n\n");
        sb.append("public final class GramInit{\n\n");
        

        sb.append(this.DFAToString(grammar, labelStringMap, DFAStringMap, fileName));
        
        // 将所有的label转换为对应的代码字符串
        sb.append(this.labelToString(DFAStringMap));

        sb.append("    public static Grammar grammar = new Grammar(" + 
                grammar.ndfas + ", " + 
                fileName + ".dfas" + ", " +
                grammar.nlabels + ", " +
                fileName + ".labels, " +
                "0" +
                ");\n");
        
        sb.append("};\n");

        return sb;
    }
    
    private void writeDFATypeFILE(File file)
    {
        StringBuilder sb = new StringBuilder(); 
        sb.append("package pers.xia.jpython.grammar;\n\n" +
                "public enum DFAType\n" + 
                "{\n");
        for(int i = 0; i < this.grammar.ndfas; i++)
        {
            sb.append("    " + this.grammar.dfas[i].name + ",\n");
        }
        
        sb.append("\n/* The follow dfa type are copyed from TokState.java */\n");
        sb.append("/* The follow type a used to make analyze easier */\n\n");
        
        try
        {
            BufferedReader file2 = new BufferedReader(new FileReader(
                    "src/pers/xia/jpython/tokenizer/TokState.java"));
            String s;
            while((s = file2.readLine()) != null)
            {
                if(s.indexOf(',') < 0) continue;
                sb.append("    " + s.trim() + "\n");
            }
            file2.close();
        } 
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        
        sb.append("}");
        
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e)
        {
            throw new PyExceptions("Write " + file.getName() + " error");  
        }
    }
    
    private void writeGramInitFile(File file)
    {
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(file);
            fw.write(this.grammarToString(this.grammar, file.getName().substring(0, file.getName().indexOf('.'))).toString());
            fw.close();

        } catch (IOException e)
        {
            throw new PyExceptions("Write " + file.getName() + " error");  
        }
    }
    
    public static void main(String[] args)
    {
        File grammarFile = new File("grammar/Grammar");
        Pgen pgen = new Pgen(grammarFile);
        pgen.createGrammar();
       
        File targetFile = new File("src/pers/xia/jpython/grammar/DFAType.java");
        pgen.writeDFATypeFILE(targetFile);
        targetFile = new File("src/pers/xia/jpython/grammar/GramInit.java");
        pgen.writeGramInitFile(targetFile);
    }
}
