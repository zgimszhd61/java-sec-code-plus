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
import java.nio.file.Paths;

// 如何将 Java 代码转化为控制流图（CFG）？请提供一个具体而简单的例子
public class ControlFlowGraphExample {

    public static void main(String[] args) throws IOException {
        // 读取 Java 文件
        FileInputStream fileInputStream = new FileInputStream(Paths.get("src", "test", "java", "sample", "Example01.java").toString());

        // 解析 Java 文件
        ParseResult<CompilationUnit> parsingResult = new JavaParser().parse(fileInputStream);
        if (parsingResult.isSuccessful() && parsingResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parsingResult.getResult().get();
            // 获取方法声明
            MethodDeclaration targetMethod = compilationUnit.findFirst(MethodDeclaration.class).orElseThrow();

            // 生成控制流图（CFG）
            Graph<String, DefaultEdge> controlFlowGraph = generateControlFlowGraph(targetMethod);

            // 输出控制流图（CFG）
            controlFlowGraph.vertexSet().forEach(vertex ->
                    System.out.println("节点: " + vertex + ", 边: " + controlFlowGraph.edgesOf(vertex)));
        }
    }

    public static Graph<String, DefaultEdge> generateControlFlowGraph(MethodDeclaration methodDeclaration) {
        Graph<String, DefaultEdge> controlFlowGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // 简化的控制流图生成逻辑
        for (Statement statement : methodDeclaration.getBody().orElseThrow().getStatements()) {
            controlFlowGraph.addVertex(statement.toString());
        }

        // 添加边（这里是简化版，实际应根据控制流逻辑添加）
        // 例如：controlFlowGraph.addEdge(statement1, statement2);

        return controlFlowGraph;
    }
}