public class ASTLet implements ASTNode{

    String id;
    ASTNode init;
    ASTNode body;

    public ASTLet(String id, ASTNode v1, ASTNode v2){
        this.id = id;
        init = v1;
        body = v2;
    }

    public IValue eval(Env env){
        env = env.beginScope();
        IValue res = init.eval(env);
        env.assoc(id, res);
        res = body.eval(env);
        env = env.endScope();
        return res;
    }
}