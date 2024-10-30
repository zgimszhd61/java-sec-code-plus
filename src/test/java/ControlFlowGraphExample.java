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
import java.nio.file.Path;

public class ControlFlowGraphExample {

    public static void main(String[] args) throws IOException {
        // 调用封装的函数来生成控制流图（CFG）
        Graph<String, DefaultEdge> controlFlowGraph = generateControlFlowGraphFromFile(Path.of("src", "test", "java", "sample", "Example01.java"));

        // 输出控制流图（CFG）
        if (controlFlowGraph != null) {
            controlFlowGraph.vertexSet().forEach(vertex ->
                    System.out.println("节点: " + vertex + ", 边: " + controlFlowGraph.edgesOf(vertex)));
        }
    }

    // 封装的函数，用于从 Java 文件生成控制流图（CFG）
    public static Graph<String, DefaultEdge> generateControlFlowGraphFromFile(Path filePath) throws IOException {
        // 读取 Java 文件
        try (FileInputStream fileInputStream = new FileInputStream(filePath.toString())) {
            // 解析 Java 文件
            ParseResult<CompilationUnit> parsingResult = new JavaParser().parse(fileInputStream);
            if (parsingResult.isSuccessful() && parsingResult.getResult().isPresent()) {
                CompilationUnit compilationUnit = parsingResult.getResult().get();
                // 获取方法声明
                MethodDeclaration targetMethod = compilationUnit.findFirst(MethodDeclaration.class).orElseThrow();

                // 生成控制流图（CFG）
                return generateControlFlowGraph(targetMethod);
            } else {
                System.err.println("无法成功解析 Java 文件。");
            }
        }
        return null;
    }

    // 生成控制流图（CFG）
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