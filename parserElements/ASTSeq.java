package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTSeq implements ASTNode {

    ASTNode left;
    ASTNode right;

    public ASTSeq(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Env<IValue> env) {
        left.eval(env);//.show();
        return right.eval(env);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        left.compile(env, comp);
        right.compile(env, comp);
    }

}