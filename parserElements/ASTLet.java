package parserElements;

import java.util.Map;
import java.util.Map.Entry;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTLet implements ASTNode {

    Map<String, ASTNode> vars;
    ASTNode body;

    public ASTLet(Map<String, ASTNode> vars, ASTNode body) {
        this.vars = vars;
        this.body = body;
    }

    public IValue eval(Env<IValue> env) {
        Env<IValue> newEnv = env.beginScope();
        IValue res;
        for (Entry<String, ASTNode> it : vars.entrySet()) {
            res = it.getValue().eval(env);
            newEnv.assoc(it.getKey(), res);
        }
        res = body.eval(newEnv);
        env = newEnv.endScope();
        return res;
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        Env<FrameComp> newEnv = env.beginScope();
        //IValue res;
        FrameComp frame = comp.genFrame(comp.getCurrentFrame());
        for (Entry<String, ASTNode> it : vars.entrySet()) {
            comp.emit("aload 4");
            String var = frame.addField(it.getKey(), "I");
            it.getValue().compile(env, comp);
            newEnv.assoc(it.getKey(), frame);
            comp.emit(String.format("putfield %s/%s %s",
                frame.getId(),
                var,
                "I"
            ));  
        }
        body.compile(newEnv, comp);
        comp.endFrame();
        newEnv.endScope();
    }

}