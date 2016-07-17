package pers.xia.jpython.tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pers.xia.jpython.config.Config;
import pers.xia.jpython.object.PyExceptions;

public class Token
{
    public String buf;
    int cur;
    // ErrorCode done;
    File file;
    int tabSize;
    int indent;
    int indstack[]; // Stack of indents
    boolean atbol;
    int pendin;
    String prompt; // For interactive prompting
    String nextPrompt; // For interactive prompting
    public int lineNo;
    public int level; // Parentheses nesting level
    String enc;
    public String str;
    String input;
    int start;
    int end;
    boolean contLine;
    boolean asyncDef;

    public TokState state;
    public int lineStart; // 一行的起始位置
    public int lineEnd; // 一行的结束位置

    public Token()
    {
        this.buf = null;
        this.cur = 0;
        // this.done = ErrorCode.E_OK;
        this.file = null;
        this.indent = 0;
        this.tabSize = Config.TABSIZE;
        this.indstack = new int[Config.MAXINDENT];
        this.atbol = true;
        this.pendin = 0;
        this.prompt = null;
        this.nextPrompt = null;
        this.lineNo = 1;
        this.level = 0;
        this.enc = null;
        this.str = null;
        this.input = null;
        this.start = this.end = 0;
        this.contLine = false;
        this.asyncDef = false;

        this.lineStart = lineEnd = 0;
    }

    public Token(String buf)
    {
        this();
        this.buf = buf;
    }

    public Token(File file)
    {
        this();
        this.file = file;
        try
        {
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            this.buf = new String(data);
            this.buf = this.buf.replaceAll("\r\n", "\n").replace('\r', '\n');
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new PyExceptions("File not found");
        }
    }

    public String toString()
    {
        if(this.state == TokState.NEWLINE || this.state == TokState.ENDMARKER
                || this.state == TokState.INDENT
                || this.state == TokState.DEDENT)
        {
            return this.lineNo + " " + this.state.toString();
        }
        return this.lineNo + " " + this.state.toString() + ": "
                + this.buf.substring(this.start, this.end);
    }

    private char nextC()
    {
        if(this.buf == null)
        {
            throw new PyExceptions("Parser Error: buf is null");
        }
        if(this.cur < this.buf.length())
        {
            if(this.buf.charAt(this.cur) == '\n')
            {
                this.lineNo++;
            }
            return this.buf.charAt(this.cur++);
        }
        else
        {
            return '\0';
        }
    }

    private void backup(char c)
    {
        if(c != '\0')
        {
            if(--this.cur < 0)
            {
                throw new PyExceptions("token.backup: beginning of buffer");
            }
            if(this.buf.charAt(this.cur) != c)
            {
                throw new PyExceptions("token.backup: backup error");
            }
            if(c == '\n')
            {
                this.lineNo--;
            }
        }
    }

    private boolean is_potential_identifier_start(char c)
    {
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'
                || c >= 128)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean is_potential_identifier_char(char c)
    {
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9') || c == '_' || c >= 128)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // 设置字符串
    private boolean checkString(char quote)
    {
        int quote_size = 1; // 1 or 3
        int end_quote_size = 0;

        char c = this.nextC();

        if(c == quote)
        {
            c = this.nextC();
            if(c == quote)
            {
                quote_size = 3;
            }
            else
            {
                end_quote_size = 1; // empty string found
            }
        }

        if(c != quote)
        {
            this.backup(c);
        }

        while (quote_size != end_quote_size)
        {
            c = this.nextC();
            if(c == '\0')
            {
                throw new PyExceptions("badly token");
            }
            if(quote_size == 1 && c == '\n')
            {
                throw new PyExceptions("End of line");
            }
            if(c == quote)
            {
                end_quote_size++;
            }
            else
            {
                end_quote_size = 0;
                if(c == '\\')
                {
                    c = this.nextC();
                }
            }
        }
        this.end = this.cur;
        this.state = TokState.STRING;
        return true;
    }

    // 设置缩进信息
    private boolean setIndentifier()
    {
        char c = 0;
        boolean blankline = false;
        int col = 0;
        this.atbol = false;

        // 获取缩进占位数
        for (;;)
        {
            c = this.nextC();
            if(c == ' ')
            {
                col++;
            }
            else if(c == '\t')
            {
                col += Config.TABSIZE;
            }
            else
            {
                break;
            }
        }
        this.backup(c);

        // 如果是空行的话做一个标记
        if(c == '#' || c == '\n')
        {
            if(col == 0 && c == '\n' && this.prompt != null)
            {
                // 在交互模式中完全的空行被用来当做代码输入的结束
                blankline = false;
            }
            else
            {
                blankline = true;
            }
        }

        // 如果非空行且不在括号内部的话设置缩进
        if(!blankline && this.level == 0)
        {
            if(col == this.indstack[this.indent])
            {
                /* No changed */
            }
            else if(col > this.indstack[this.indent])
            {
                if(this.indent + 1 >= Config.MAXINDENT)
                {
                    throw new PyExceptions(
                            "IndentationError: too many levels of indentation");
                }
                this.pendin++;
                this.indstack[++this.indent] = col;
            }
            else
            {
                // 向后缩进的话一次可能缩进多次
                while (this.indent > 0 && col < this.indstack[this.indent])
                {
                    this.pendin--;
                    this.indent--;
                }
                if(col != this.indstack[this.indent])
                {
                    //log.error(col + " " + this.indstack[this.indent]);
                    throw new PyExceptions();
                }
            }
        }
        return blankline;
    }

    private boolean isxdigit(char c)
    {
        if((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')
                || (c >= 'a' && c <= 'f'))
            return true;
        else
            return false;
    }

    // 检查是否读取到了Name
    private boolean checkName(char c)
    {
        /* Process b"", r"", u"", br"" and rb"" */
        boolean saw_b = false, saw_u = false, saw_r = false;

        //boolean nonAscii = false;

        // 处理字符串签名可能出现的b、u、r
        while (true)
        {
            if(!(saw_b || saw_u) && (c == 'b' || c == 'B'))
                saw_b = true;
            else if(!(saw_u || saw_b || saw_r) && (c == 'u' || c == 'U'))
                saw_u = true;
            else if(!(saw_r || saw_u) && (c == 'r' || c == 'R'))
                saw_r = true;
            else
                break;
            c = this.nextC();
            if(c == '\'' || c == '"')
            {
                return checkString(c);
            }
        }

        while (is_potential_identifier_char(c))
        {
            /*if(c >= 128)
            {
                nonAscii = true;
            }*/
            c = this.nextC();
        }

        this.backup(c);
        this.end = this.cur;
        this.state = TokState.NAME;

        if(this.cur - this.start == 5)
        {
            if(this.asyncDef)
            {
                if(this.buf.substring(this.start, this.end).equals("async"))
                {
                    this.state = TokState.ASYNC;
                    return true;
                }
                if(this.buf.substring(this.start, this.end).equals("await"))
                {
                    this.state = TokState.AWAIT;
                    return true;
                }
            }
            else if(this.buf.substring(this.start, this.end).equals("async"))
            {
                int curTmp = this.end;

                char cc = buf.charAt(curTmp);
                while (cc != '\0')
                {
                    /*if(cc == '#')
                    {
                        while (cc != '\n')
                        {
                            cc = buf.charAt(++curTmp);
                        }
                    }*/
                    if(cc != ' ' && cc != '\t')
                    {
                        break;
                    }
                    cc = buf.charAt(++curTmp);
                }

                int endTmp = curTmp + 1;
                cc = buf.charAt(endTmp);

                while (is_potential_identifier_char(cc))
                {
                    cc = buf.charAt(++endTmp);
                }
                if(buf.substring(curTmp, endTmp).equals("def"))
                {
                    this.state = TokState.ASYNC;
                    this.asyncDef = true;
                    return true;
                }
            }
        }

        return true;
    }

    // 虚数
    private boolean imaginary()
    {
        this.end = this.cur;
        this.state = TokState.NUMBER;
        return true;
    }

    private boolean checkNumber(char c)
    {
        if(c == '0')
        {
            c = this.nextC();
            if(c == '.')
            {
                return fraction();
            }
            if(c == 'j' || c == 'J')
            {
                return imaginary();
            }
            if(c == 'x' || c == 'X')
            {
                c = this.nextC();
                if(!this.isxdigit(c))
                {
                    this.backup(c);
                    throw new PyExceptions();
                }
                do
                {
                    c = this.nextC();
                } while (this.isxdigit(c));
            }
            else if(c == 'o' || c == 'O')
            {
                c = this.nextC();
                if(c < '0' || c > '7')
                {
                    this.backup(c);
                    throw new PyExceptions();
                }
                do
                {
                    c = this.nextC();
                } while (c >= '0' && c <= '8');
            }
            else if(c == 'b' || c == 'B')
            {
                c = this.nextC();
                if(c != '0' && c != '1')
                {
                    this.backup(c);
                    throw new PyExceptions();
                }
                do
                {
                    c = this.nextC();
                } while (c == '0' || c == '1');
            }
            else
            {
                boolean nonZero = false;
                while (c == '0')
                {
                    c = this.nextC();
                }
                while (Character.isDigit(c))
                {
                    nonZero = true;
                    c = this.nextC();
                }

                if(c == '.')
                {
                    return fraction();
                }
                else if(c == 'e' || c == 'E')
                {
                    return exponent(c);
                }
                else if(c == 'j' || c == 'J')
                {
                    return imaginary();
                }
                else if(nonZero)
                {
                    this.backup(c);
                    throw new PyExceptions();
                }
            }
        }
        else
        {
            do
            {
                c = this.nextC();
            } while (Character.isDigit(c));

            if(c == '.')
            {
                return fraction();
            }
            if(c == 'e' || c == 'E')
            {
                return exponent(c);
            }
            if(c == 'j' || c == 'J')
            {
                return imaginary();
            }
        }

        this.backup(c);
        this.end = this.cur;
        this.state = TokState.NUMBER;

        return true;
    }

    // 指数
    private boolean exponent(char e)
    {
        char c = this.nextC();
        if(c == '+' || c == '-')
        {
            c = this.nextC();
            if(!Character.isDigit(c))
            {
                this.backup(c);
                throw new PyExceptions();
            }
        }
        else if(!Character.isDigit(c))
        {
            this.backup(c);
            this.backup(e);

            this.end = this.cur;
            this.state = TokState.NUMBER;
            return true;
        }

        do
        {
            c = this.nextC();
        } while (Character.isDigit(c));

        if(c == 'j' || c == 'J')
        {
            return imaginary();
        }

        this.backup(c);

        this.end = this.cur;
        this.state = TokState.NUMBER;
        return true;
    }

    // 实数
    private boolean fraction()
    {
        char c = 0;
        do
        {
            c = this.nextC();
        } while (Character.isDigit(c));

        if(c == 'e' || c == 'E')
        {
            return exponent(c);
        }
        if(c == 'j' || c == 'J')
        {
            return imaginary();
        }

        this.backup(c);

        this.end = this.cur;
        this.state = TokState.NUMBER;
        return true;
    }

    private boolean checkDot()
    {
        char c = this.nextC();
        if(Character.isDigit(c))
        {
            return fraction();
        }
        else if(c == '.')
        {
            c = this.nextC();
            if(c == '.')
            {
                this.end = this.cur;
                this.state = TokState.ELLIPSIS;
                return true;
            }
            else
            {
                this.backup(c);
            }
            this.backup('.');
        }
        else
        {
            this.backup(c);
        }
        this.end = this.cur;
        this.state = TokState.DOT;
        return true;
    }

    private TokState oneChar(char c)
    {
        switch (c)
        {
        case '(':
            return TokState.LPAR;
        case ')':
            return TokState.RPAR;
        case '[':
            return TokState.LSQB;
        case ']':
            return TokState.RSQB;
        case ':':
            return TokState.COLON;
        case ',':
            return TokState.COMMA;
        case ';':
            return TokState.SEMI;
        case '+':
            return TokState.PLUS;
        case '-':
            return TokState.MINUS;
        case '*':
            return TokState.STAR;
        case '/':
            return TokState.SLASH;
        case '|':
            return TokState.VBAR;
        case '&':
            return TokState.AMPER;
        case '<':
            return TokState.LESS;
        case '>':
            return TokState.GREATER;
        case '=':
            return TokState.EQUAL;
        case '.':
            return TokState.DOT;
        case '%':
            return TokState.PERCENT;
        case '{':
            return TokState.LBRACE;
        case '}':
            return TokState.RBRACE;
        case '^':
            return TokState.CIRCUMFLEX;
        case '~':
            return TokState.TILDE;
        case '@':
            return TokState.AT;
        default:
            return TokState.OP;

        }
    }

    private TokState twoChars(char c1, char c2)
    {
        switch (c1)
        {
        case '=':
            switch (c2)
            {
            case '=':
                return TokState.EQEQUAL;
            }
            break;
        case '!':
            switch (c2)
            {
            case '=':
                return TokState.NOTEQUAL;
            }
            break;
        case '<':
            switch (c2)
            {
            case '>':
                return TokState.NOTEQUAL;
            case '=':
                return TokState.LESSEQUAL;
            case '<':
                return TokState.LEFTSHIFT;
            }
            break;
        case '>':
            switch (c2)
            {
            case '=':
                return TokState.LESSEQUAL;
            case '>':
                return TokState.RIGHTSHIFT;
            }
            break;
        case '+':
            switch (c2)
            {
            case '=':
                return TokState.PLUSEQUAL;
            }
            break;
        case '-':
            switch (c2)
            {
            case '=':
                return TokState.MINEQUAL;
            case '>':
                return TokState.RARROW;
            }
            break;
        case '*':
            switch (c2)
            {
            case '*':
                return TokState.DOUBLESTAR;
            case '=':
                return TokState.STAREQUAL;
            }
            break;
        case '/':
            switch (c2)
            {
            case '/':
                return TokState.DOUBLESLASH;
            case '=':
                return TokState.SLASHEQUAL;
            }
            break;
        case '|':
            switch (c2)
            {
            case '=':
                return TokState.VBAREQUAL;
            }
            break;
        case '%':
            switch (c2)
            {
            case '=':
                return TokState.PERCENTEQUAL;
            }
            break;
        case '&':
            switch (c2)
            {
            case '=':
                return TokState.AMPEREQUAL;
            }
            break;
        case '^':
            switch (c2)
            {
            case '=':
                return TokState.CIRCUMFLEXEQUAL;
            }
            break;
        case '@':
            switch (c2)
            {
            case '=':
                return TokState.ATEQUAL;
            }
            break;

        }
        return TokState.OP;
    }

    private TokState threeChars(char c1, char c2, char c3)
    {
        if(c1 == '<' && c2 == '<' && c3 == '=')
        {
            return TokState.LEFTSHIFTEQUAL;
        }
        if(c1 == '>' && c2 == '>' && c3 == '=')
        {
            return TokState.RIGHTSHIFTEQUAL;
        }
        if(c1 == '*' && c2 == '*' && c3 == '=')
        {
            return TokState.DOUBLESTAREQUAL;
        }
        if(c1 == '/' && c2 == '/' && c3 == '=')
        {
            return TokState.DOUBLESLASHEQUAL;
        }
        if(c1 == '.' && c2 == '.' && c3 == '.')
        {
            return TokState.ELLIPSIS;
        }
        return TokState.OP;
    }

    public boolean get()
    {
        char c;
        boolean blankline = false;

        // 替代goto语句的使用
        nextline: for (;;)
        {
            // 如果是在一行的开始的话则设置缩进信息
            if(this.atbol)
            {

                // 设置当前行的起始和结束位置
                this.lineStart = this.cur;
                this.lineEnd = this.buf.indexOf('\n', this.lineStart);
                if(this.lineEnd < 0)
                {
                    this.lineEnd = this.buf.length();
                }

                this.start = this.cur;
                blankline = setIndentifier();
                this.end = this.cur;
            }

            // check pendin
            if(this.pendin != 0)
            {
                if(this.pendin < 0)
                {
                    this.pendin++;
                    this.state = TokState.DEDENT;
                    return true;
                }
                else
                {
                    this.pendin--;
                    this.state = TokState.INDENT;
                    return true;
                }
            }

            this.start = this.cur;

            // 替代goto语句的使用
            again: for (;;)
            {
                this.start = 0;

                // delete blank char
                do
                {
                    c = this.nextC();
                } while (c == ' ' || c == '\t');

                this.start = this.cur - 1;

                // 删除注释内容
                if(c == '#')
                {
                    while (c != '\0' && c != '\n')
                    {
                        c = this.nextC();
                    }
                }

                if(c == '\0')
                {
                    this.end = this.cur - 1;
                    this.state = TokState.ENDMARKER;
                    return true;
                }

                if(is_potential_identifier_start(c))
                {
                    return checkName(c);
                }

                if(c == '\n')
                {
                    this.atbol = true;
                    if(blankline || this.level > 0)
                    {
                        continue nextline; // 跳转到nextline位置，这里不知道怎么重构了，只能这样写
                    }
                    this.end = this.cur;
                    this.state = TokState.NEWLINE;
                    return true;
                }

                if(c == '.')
                {
                    return checkDot();
                }

                if(Character.isDigit(c))
                {
                    return checkNumber(c);
                }

                if(c == '\'' || c == '"')
                {
                    return checkString(c);
                }

                if(c == '\\')
                {
                    c = this.nextC();
                    if(c != '\n')
                    {
                        throw new PyExceptions("invlid syntax");
                    }
                    this.contLine = true;
                    continue again;
                }

                char c2 = this.nextC();
                TokState state = this.twoChars(c, c2);
                if(state != TokState.OP)
                {
                    char c3 = this.nextC();
                    TokState state3 = this.threeChars(c, c2, c3);
                    if(state3 != TokState.OP)
                    {
                        state = state3;
                    }
                    else
                    {
                        this.backup(c3);
                    }
                    this.state = state;
                    this.end = this.cur;
                    return true;
                }
                this.backup(c2);

                switch (c)
                {
                case '(':
                case '[':
                case '{':
                    this.level++;
                    break;
                case ')':
                case ']':
                case '}':
                    this.level--;
                    break;
                }

                this.end = this.cur;

                this.state = this.oneChar(c);

                if(this.state != TokState.OP)
                {
                    return true;
                }

                //log.error(c + 0);
                throw new PyExceptions("Invlid syntex", this);
            }
        }
    }
}
