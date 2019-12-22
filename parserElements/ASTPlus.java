package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTPlus implements ASTNode {
    ASTNode t1;
    ASTNode t2;

    public ASTPlus(ASTNode t1, ASTNode t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env<IValue> env) {
        IValue v1 = t1.eval(env);
        String error = v1.getClass().getSimpleName();
        IValue v2 = t2.eval(env);
        String error2 = v2.getClass().getSimpleName();
        if (v1 instanceof VInt) {
            if (v2 instanceof VInt) {
                return new VInt(((VInt) v1).getval() + ((VInt) v2).getval());
            }
        }else if(v1 instanceof VStruct){
            if (v2 instanceof VStruct) {
                return new VStruct(((VStruct) v1), ((VStruct) v2));
            }
        }else if(v1 instanceof VString){
            if (v2 instanceof VString) {
                return new VString(((VString) v1).getval() + ((VString) v2).getval());
            }
        }
        throw new TypeError(String.format("Illegal pair arguments (%s,%s) to + operator", error, error2));
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        typecheker(type);
        t1.compile(env, type, comp);
        t2.compile(env, type, comp);
        comp.emit("iadd");
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType left = t1.typecheker(env);
        IType right = t2.typecheker(env);
        if(left.equals(TInt.getInstance()) && right.equals(TInt.getInstance()))
            return TInt.getInstance();
        throw new TypeError("Illegal type to + operator!");
    }

}