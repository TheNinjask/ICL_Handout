package compilerElements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FrameComp {

    private static final String SL_FORMAT = "L%s";
    private static final String FIELD_FORMAT = "x%s";
    private int fieldCounter;
    private String id;
    private FrameComp sl;
    private Map<String, String> hopeDreams;

    public FrameComp(String id) {
        this.id = id;
        this.sl = null;
        this.hopeDreams = new HashMap<>();
        this.fieldCounter = 0;
    }

    public FrameComp(String id, FrameComp sl) {
        this.id = id;
        this.sl = sl;
        this.hopeDreams = new HashMap<>();
        this.fieldCounter = 0;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return String.format(SL_FORMAT, id);
    }

    public FrameComp getSl() {
        return sl;
    }

    public String addField(String type) {
        String field = String.format(FIELD_FORMAT, fieldCounter++);
        hopeDreams.put(field, type);
        return field;
    }

    public String getField(String field){
        return hopeDreams.get(field);
    }

    public void dump(String path) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(Paths.get(path, String.format("%s.j", id)).toString()));
        out.write(String.format(".class %s\n", id));
        out.write(".super java/lang/Object\n");
        out.write(String.format(".field public sl %s;\n", 
        sl == null ? "Ljava/lang/Object" : sl.getLabel()
        ));
        for (Entry<String, String> entry: hopeDreams.entrySet()) {
            out.write(String.format(".field public %s %s\n", entry.getKey(), entry.getValue()));
        }
        out.write("\n");
        out.write(".method public <init>()V\n");
        out.write("\taload_0\n");
        out.write("\tinvokenonvirtual java/lang/Object/<init>()V\n");
        out.write("\treturn\n");
        out.write(".end method\n");
        out.flush();
        out.close();
    }
}
