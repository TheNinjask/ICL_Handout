package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTEq implements ASTNode {
    ASTNode t1;
    ASTNode t2;

    public ASTEq(ASTNode t1, ASTNode t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env<IValue> env) {
        IValue v1 = t1.eval(env);
        String error = v1.getClass().getSimpleName();
        if (v1 instanceof VInt) {
            IValue v2 = t2.eval(env);
            if (v2 instanceof VInt) {
                return new VBool(((VInt) v1).getval() == ((VInt) v2).getval());
            }
            error = v2.getClass().getSimpleName();
        }
        throw new TypeError(String.format("Illegal arguments (%s) to == operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO
        String elsy = comp.genLabel();
        String exit = comp.genLabel();
        t1.compile(env, comp);
        t2.compile(env, comp);
        comp.emit(String.format("if_icmpne %s", elsy));
        comp.emit("sipush 1");
        comp.emit(String.format("goto %s", exit));
        comp.emit(String.format("%s:", elsy));
        comp.emit("sipush 0");
        comp.emit(String.format("%s:", exit));
    }

}