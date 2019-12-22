package parserElements;

import java.util.ArrayList;
import java.util.List;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTFunType implements ASTNode {

    ASTNode type;

    List<ASTNode> paramsTypes;

    public ASTFunType(List<ASTNode> paramsTypes, ASTNode type) {
        this.type = type;
        this.paramsTypes = paramsTypes;
    }

    public IValue eval(Env<IValue> env) {
        return null;
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        List<IType> types = new ArrayList<>();
        for (ASTNode param : paramsTypes) {
            types.add(param.typecheker(env));
        }
        return TFunType.getInstance(types, type.typecheker(env));
    }

}