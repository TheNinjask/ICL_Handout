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
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        String elsy = comp.genLabel();
        String exit = comp.genLabel();
        typecheker(type);
        comp.emit("sipush 1");
        condition.compile(env, type, comp);
        comp.emit(String.format("if_icmpne %s", elsy));
        trueBody.compile(env, type, comp);
        comp.emit(String.format("goto %s", exit));
        comp.emit(String.format("%s:", elsy));
        falseBody.compile(env, type, comp);
        comp.emit(String.format("%s:", exit));
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType condType = condition.typecheker(env);
        if(condType.equals(TBool.getInstance())){
            IType trueType = trueBody.typecheker(env);
            IType falseType = falseBody.typecheker(env);
            if(trueType.equals(falseType))
                return trueType;
            else
                return TVoid.getInstance();
        }
        throw new TypeError("Illegal Type for if condition");
    }

}