package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTInvertBool implements ASTNode {

    ASTNode value;

    public ASTInvertBool(ASTNode value) {
        this.value = value;
    }

    public IValue eval(Env<IValue> env) {
        IValue res = value.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VBool) {
            return new VBool(!((VBool) res).getBool());
        }
        throw new TypeError(String.format("Illegal argument (%s) to ~ operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        String elsy = comp.genLabel();
        String exit = comp.genLabel();
        comp.emit("sipush 1");
        value.compile(env, comp);
        comp.emit(String.format("if_icmpne %s", elsy));
        comp.emit(String.format("sipush %s", 0));
        comp.emit(String.format("goto %s", exit));
        comp.emit(String.format("%s:", elsy));
        comp.emit(String.format("sipush %s", 1));
        comp.emit(String.format("%s:", exit));
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType valType = value.typecheker(env);
        if(valType.equals(TBool.getInstance()))
            return TBool.getInstance();
        throw new TypeError("Illegal type for ~ operator");
    }
}