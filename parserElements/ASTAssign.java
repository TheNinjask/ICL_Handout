package parserElements;

import compilerElements.CodeBlock;
import parserExceptions.TypeError;

public class ASTAssign implements ASTNode {

    ASTNode ref;
    ASTNode value;

    public ASTAssign(ASTNode ref, ASTNode value) {
        this.ref = ref;
        this.value = value;
    }

    public IValue eval(Env<IValue> env) {
        IValue iRef = ref.eval(env);
        IValue iVal = value.eval(env);
        if(!(iRef instanceof VCell))
            throw new TypeError("Assignment to non reference!");
        ((VCell)iRef).set(iVal);
        return iRef;
    }

    @Override
    public void compile(Env<String> env, CodeBlock comp) {
        //TODO 
    }
}