import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class CFGTestSimple02 {

    public static void main(String[] args) throws IOException {
        // 读取Java文件
        FileInputStream in = new FileInputStream("/Users/a0000/mywork/commonLLM/opensource/javabenchmark/src/test/java/sample/Example02.java");

        // 解析Java文件
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(in);
        if(parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parseResult.getResult().get();
            // 获取方法声明
            MethodDeclaration method = compilationUnit.findFirst(MethodDeclaration.class).orElseThrow();

            // 生成CFG
            Graph<String, DefaultEdge> cfg = generateCFG(method);

            // 输出CFG
            cfg.vertexSet().forEach(vertex ->
                    System.out.println("Vertex: " + vertex + ", Edges: " + cfg.edgesOf(vertex)));
        }
    }

    public static Graph<String, DefaultEdge> generateCFG(MethodDeclaration method){
            Graph<String, DefaultEdge> cfg = new DefaultDirectedGraph<>(DefaultEdge.class);

            // 获取方法体的语句
            List<Statement> statements = method.getBody().orElseThrow().getStatements();

            // 简化的CFG生成逻辑
            String prevVertex = null;
            for (Statement stmt : statements) {
                String currentVertex = stmt.toString();
                cfg.addVertex(currentVertex);

                if (prevVertex != null) {
                    cfg.addEdge(prevVertex, currentVertex);
                }

                if (stmt instanceof IfStmt) {
                    handleIfStmt((IfStmt) stmt, cfg, currentVertex);
                } else if (stmt instanceof ForStmt) {
                    handleForStmt((ForStmt) stmt, cfg, currentVertex);
                }

                prevVertex = currentVertex;
            }


        return cfg;
    }

    private static void handleIfStmt(IfStmt ifStmt, Graph<String, DefaultEdge> cfg, String parentVertex) {
        Statement thenStmt = ifStmt.getThenStmt();
        String thenVertex = thenStmt.toString();
        cfg.addVertex(thenVertex);
        cfg.addEdge(parentVertex, thenVertex);

        if (thenStmt instanceof BlockStmt) {
            for (Statement stmt : ((BlockStmt) thenStmt).getStatements()) {
                String currentVertex = stmt.toString();
                cfg.addVertex(currentVertex);
                cfg.addEdge(thenVertex, currentVertex);
                thenVertex = currentVertex;
            }
        }

        if (ifStmt.getElseStmt().isPresent()) {
            Statement elseStmt = ifStmt.getElseStmt().get();
            String elseVertex = elseStmt.toString();
            cfg.addVertex(elseVertex);
            cfg.addEdge(parentVertex, elseVertex);

            if (elseStmt instanceof BlockStmt) {
                for (Statement stmt : ((BlockStmt) elseStmt).getStatements()) {
                    String currentVertex = stmt.toString();
                    cfg.addVertex(currentVertex);
                    cfg.addEdge(elseVertex, currentVertex);
                    elseVertex = currentVertex;
                }
            }
        }
    }

    private static void handleForStmt(ForStmt forStmt, Graph<String, DefaultEdge> cfg, String parentVertex) {
        String forVertex = forStmt.toString();
        cfg.addVertex(forVertex);
        cfg.addEdge(parentVertex, forVertex);

        Statement bodyStmt = forStmt.getBody();
        if (bodyStmt instanceof BlockStmt) {
            for (Statement stmt : ((BlockStmt) bodyStmt).getStatements()) {
                String currentVertex = stmt.toString();
                cfg.addVertex(currentVertex);
                cfg.addEdge(forVertex, currentVertex);
                forVertex = currentVertex;
            }
        } else {
            String bodyVertex = bodyStmt.toString();
            cfg.addVertex(bodyVertex);
            cfg.addEdge(forVertex, bodyVertex);
        }
    }
}
