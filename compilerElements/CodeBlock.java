package compilerElements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CodeBlock {

    // singleton(?)

    private final static CodeBlock instance = new CodeBlock();
    private static final String FRAME_FORMAT = "f%s";
    private long incLabel;
    private long incFrame;
    private StringBuffer inst;
    private FrameComp currentFrame;
    private List<String> frames;

    private CodeBlock() {
        incFrame = 0L;
        incLabel = 0L;
        inst = new StringBuffer();
        currentFrame = null;
        frames = new ArrayList<>();
    }

    public static CodeBlock getInstance() {
        return instance;
    }

    public void setCurrentFrame(FrameComp newFrame) {
        this.currentFrame = newFrame;
    }

    public FrameComp getCurrentFrame(FrameComp... newFrame) {
        if (newFrame.length > 0)
            this.currentFrame = newFrame[0];
        return this.currentFrame;
    }

    public void emit(String instruction) {
        inst.append(String.format("\t%s\n", instruction));
    }

    public void dump(String filename, int... args) throws IOException {
        int local;
        int stack;
        switch (args.length) {
        case 2:
            local = args[0];
            stack = args[1];
        default:
            local = 10;
            stack = 256;
            break;
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        generateMainInit(out, local, stack);
        out.append(inst);
        generateMainEnd(out);
        out.flush();
        out.close();
    }

    public void compJasminWin(String file, String... jarPath) throws IOException, InterruptedException {
        String path = jarPath.length == 0 ? ".\\jasmin.jar" : jarPath[0];
        String cmd = String.format("java -jar %s %s", new File(path).getAbsolutePath(), new File(file).getAbsolutePath());
        Process proc = Runtime.getRuntime().exec(cmd);
        // Then retreive the process output
        proc.waitFor();
        for (String frame : frames) {
            cmd = String.format("java -jar %s %s", new File(path).getAbsolutePath(), new File(frame).getAbsolutePath());
            proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
        }
    }
    
    @Deprecated
    public String genLabel(String... label){
        return String.format("L%s", label.length>0? label[0] : incLabel++);
    }

    public FrameComp genFrame(FrameComp... sl){
        String frameId = String.format(FRAME_FORMAT, incFrame++);
        FrameComp frame;
        if(sl.length>0)
            frame =  new FrameComp(frameId, sl[0]);
        else
            frame = new FrameComp(frameId);
        emit(String.format("new %s", frame.getId()));
        emit("dup");
        emit(String.format("\tinvokespecial %s/<init>()V", frame.getId()));
        emit("dup");
        emit("aload 4");
        FrameComp father = frame.getSl();
        emit(String.format("putfield %s/sl %s;", frame.getId(), 
            father == null ? "Ljava/lang/Object" : father.getLabel()
        ));
        emit("astore 4");
        currentFrame = frame;
        return frame;
    }

    public void endFrame(){
        emit("aload 4");
        FrameComp sl = currentFrame.getSl();
        emit(String.format("getfield %s/sl %s;", currentFrame.getId(),
            sl == null ? "Ljava/lang/Object" : sl.getLabel()
        ));
        emit("astore 4"); 
        try {
            String path = currentFrame.dump(".");
            frames.add(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentFrame = sl;
    }

    private void generateMainInit(Writer out, int local, int stack) throws IOException {
        out.write(".class public Main\n");
        out.write(".super java/lang/Object\n");
        out.write(".method public <init>()V\n");
        out.write("\taload_0\n");
        out.write("\tinvokenonvirtual java/lang/Object/<init>()V\n");
        out.write("\treturn\n");
        out.write(".end method\n");
        out.write(".method public static main([Ljava/lang/String;)V\n");
        out.write(String.format("\t.limit locals %s\n", local));
        out.write(String.format("\t.limit stack %s\n", stack));
        //question urself about next line
        out.write("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
        out.write("\taconst_null\n");
        out.write("\tastore 4\n");
    }

    private void generateMainEnd(Writer out) throws IOException {
        out.write("\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n");
        out.write("\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
        out.write("\treturn\n");
        out.write(".end method\n");
    }

}