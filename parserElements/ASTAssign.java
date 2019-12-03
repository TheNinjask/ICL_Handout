package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
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
        if(!(iRef instanceof VRef))
            throw new TypeError("Assignment to non reference!");
        ((VRef)iRef).set(iVal);
        return iVal;
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO 
    }
}