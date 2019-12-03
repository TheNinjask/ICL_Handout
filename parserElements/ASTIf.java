package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTIf implements ASTNode {

    ASTNode condition;
    ASTNode trueBody;
    ASTNode falseBody;

    public ASTIf(ASTNode condition, ASTNode trueBody, ASTNode falseBody) {
        this.condition = condition;
        this.trueBody = trueBody;
        this.falseBody = falseBody;
    }

    public IValue eval(Env<IValue> env) {
        IValue iCond = condition.eval(env);
        String error = condition.getClass().getSimpleName();
        if(iCond instanceof VBool)
            if(((VBool)iCond).getBool())
                return trueBody.eval(env);
            else
                return falseBody.eval(env);
        throw new TypeError(String.format("Illegal arguments (%s) to if condition", error));
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO 
    }
}