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
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        left.compile(env, type, comp);
        right.compile(env, type, comp);
    }

    @Override
    public IType typecheker(Env<IType> env) {
        left.typecheker(env);
        return right.typecheker(env);
    }

}