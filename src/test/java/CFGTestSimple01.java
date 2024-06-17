import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.FileInputStream;
import java.io.IOException;

// 如何将java代码转化为CFG？请提供一个具体而简单的例子
public class CFGTestSimple01 {

    public static void main(String[] args) throws IOException {
        // 读取Java文件
        FileInputStream in = new FileInputStream("/Users/a0000/mywork/commonLLM/opensource/javabenchmark/src/test/java/sample/Example01.java");

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

    public static Graph<String, DefaultEdge> generateCFG(MethodDeclaration method) {
        Graph<String, DefaultEdge> cfg = new DefaultDirectedGraph<>(DefaultEdge.class);

        // 简化的CFG生成逻辑
        for (Statement stmt : method.getBody().orElseThrow().getStatements()) {
            cfg.addVertex(stmt.toString());
        }

        // 添加边（这里是简化版，实际应根据控制流逻辑添加）
        // 例如：cfg.addEdge(stmt1, stmt2);

        return cfg;
    }
}
