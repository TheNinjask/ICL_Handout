package parserElements;

import java.util.Map;
import java.util.Map.Entry;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTLet implements ASTNode {

    Map<String, ASTNode> vars;
    Map<String, ASTNode> types;
    ASTNode body;

    public ASTLet(Map<String, ASTNode> vars, Map<String, ASTNode> types,ASTNode body) {
        this.vars = vars;
        this.types = types;
        this.body = body;
    }

    public IValue eval(Env<IValue> env) {
        Env<IValue> newEnv = env.beginScope();
        IValue res;
        for (Entry<String, ASTNode> it : vars.entrySet()) {
            res = it.getValue().eval(newEnv);
            newEnv.assoc(it.getKey(), res);
        }
        res = body.eval(newEnv);
        env = newEnv.endScope();
        return res;
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        Env<FrameComp> newEnv = env.beginScope();
        Env<IType> newEnvType = type.beginScope();
        //IValue res;
        FrameComp frame = comp.genFrame(comp.getCurrentFrame());
        for (Entry<String, ASTNode> it : vars.entrySet()) {
            comp.emit("aload 4");
            
            it.getValue().compile(newEnv, newEnvType, comp);
            
            IType elemType = it.getValue().typecheker(newEnvType);
            IType typing = types.get(it.getKey()).typecheker(newEnvType);
            if(elemType!=typing)
                throw new TypeError("Illegal type in let");
            String var = frame.addField(it.getKey(), elemType.getType());
            newEnvType.assoc(it.getKey(), elemType);
            newEnv.assoc(it.getKey(), frame);
            comp.emit(String.format("putfield %s/%s %s",
                frame.getId(),
                var,
                elemType.getType()
            ));  
        }
        body.compile(newEnv, newEnvType, comp);
        comp.endFrame();
        newEnv.endScope();
        newEnvType.endScope();
    }

    @Override
    public IType typecheker(Env<IType> env) {
        Env<IType> newEnv = env.beginScope();
        for (Entry<String, ASTNode> elem : vars.entrySet()) {
            IType elemType = elem.getValue().typecheker(env);
            IType type = types.get(elem.getKey()).typecheker(env);
            if(elemType!=type)
                throw new TypeError("Illegal type in let");
            newEnv.assoc(elem.getKey(), elemType);
        }
        IType bodyType = body.typecheker(newEnv);
        newEnv.endScope();
        return bodyType;
    }

}