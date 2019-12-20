package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTPrintln implements ASTNode {

    ASTNode val;

    public ASTPrintln(ASTNode val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        IValue value = val.eval(env);
        value.show();
        return value;
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        comp.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
        val.compile(env, comp);
        comp.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
        comp.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType valType = val.typecheker(env);
        if(valType.equals(TInt.getInstance()))
            return TVoid.getInstance();
        throw new TypeError(String.format("Illegal type (%s) to println operator!"));
    }

}