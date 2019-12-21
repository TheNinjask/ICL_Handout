package compilerElements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class RefComp {

    private String type;
    private String typeShortName;
    public RefComp(String type, String... typeShortName) {
        this.typeShortName = String.format("ref_%s", typeShortName.length>0 ? typeShortName[0] : type);
    }

    public String dump(String path) throws IOException {
        String truePath = Paths.get(path, String.format("%s.j", typeShortName)).toString();
        BufferedWriter out = new BufferedWriter(new FileWriter(truePath));
        out.write(String.format(".class %s\n", typeShortName));
        out.write(".super java/lang/Object\n");
        out.write(String.format(".field public v %s;\n", type));
        out.write("\n");
        out.write(".method public <init>()V\n");
        out.write("\taload_0\n");
        out.write("\tinvokenonvirtual java/lang/Object/<init>()V\n");
        out.write("\treturn\n");
        out.write(".end method\n");
        out.flush();
        out.close();
        return truePath;
    }
}
