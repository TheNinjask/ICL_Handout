package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTWhile implements ASTNode {

    ASTNode condition;
    ASTNode body;

    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    public IValue eval(Env<IValue> env) {
        IValue iCond = condition.eval(env);
        String error = iCond.getClass().getSimpleName();
        if(!(iCond instanceof VBool))
            throw new TypeError(String.format("Illegal arguments (%s) to if condition", error));
        while(((VBool)iCond).getBool()){
            body.eval(env);
            iCond = condition.eval(env);
        }
        return new VBool(false);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //this might be wrong due to what happens with numerous cycles
        String whily = comp.genLabel();
        String exit = comp.genLabel();
        comp.emit(String.format("%s:", whily));
        comp.emit("sipush 1");
        condition.compile(env, comp);
        comp.emit(String.format("if_icmpne %s", exit));
        body.compile(env, comp); //??
        comp.emit(String.format("goto %s", whily));
        comp.emit(String.format("%s:", exit));
        comp.emit("sipush 0");
    }
}