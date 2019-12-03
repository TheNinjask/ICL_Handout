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
        //TODO 
    }
}