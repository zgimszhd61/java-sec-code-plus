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
import java.nio.file.Paths;

public class ControlFlowGraphExample02 {

    public static void main(String[] args) throws IOException {
        // 读取Java文件
        FileInputStream fileInputStream = new FileInputStream(Paths.get("src", "test", "java", "sample", "Example02.java").toString());

        // 解析Java文件
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(fileInputStream);
        if(parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parseResult.getResult().get();
            // 获取方法声明
            MethodDeclaration methodDeclaration = compilationUnit.findFirst(MethodDeclaration.class).orElseThrow();

            // 生成控制流图（CFG）
            Graph<String, DefaultEdge> controlFlowGraph = generateControlFlowGraph(methodDeclaration);

            // 输出控制流图（CFG）
            controlFlowGraph.vertexSet().forEach(vertex ->
                    System.out.println("节点: " + vertex + ", 边: " + controlFlowGraph.edgesOf(vertex)));
        }
    }

    public static Graph<String, DefaultEdge> generateControlFlowGraph(MethodDeclaration methodDeclaration){
        Graph<String, DefaultEdge> controlFlowGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // 获取方法体的语句
        List<Statement> methodStatements = methodDeclaration.getBody().orElseThrow().getStatements();

        // 简化的控制流图生成逻辑
        String previousNode = null;
        for (Statement statement : methodStatements) {
            String currentNode = statement.toString();
            controlFlowGraph.addVertex(currentNode);

            if (previousNode != null) {
                controlFlowGraph.addEdge(previousNode, currentNode);
            }

            if (statement instanceof IfStmt) {
                handleIfStatement((IfStmt) statement, controlFlowGraph, currentNode);
            } else if (statement instanceof ForStmt) {
                handleForStatement((ForStmt) statement, controlFlowGraph, currentNode);
            }

            previousNode = currentNode;
        }

        return controlFlowGraph;
    }

    private static void handleIfStatement(IfStmt ifStatement, Graph<String, DefaultEdge> controlFlowGraph, String parentNode) {
        Statement thenStatement = ifStatement.getThenStmt();
        String thenNode = thenStatement.toString();
        controlFlowGraph.addVertex(thenNode);
        controlFlowGraph.addEdge(parentNode, thenNode);

        if (thenStatement instanceof BlockStmt) {
            for (Statement statement : ((BlockStmt) thenStatement).getStatements()) {
                String currentNode = statement.toString();
                controlFlowGraph.addVertex(currentNode);
                controlFlowGraph.addEdge(thenNode, currentNode);
                thenNode = currentNode;
            }
        }

        if (ifStatement.getElseStmt().isPresent()) {
            Statement elseStatement = ifStatement.getElseStmt().get();
            String elseNode = elseStatement.toString();
            controlFlowGraph.addVertex(elseNode);
            controlFlowGraph.addEdge(parentNode, elseNode);

            if (elseStatement instanceof BlockStmt) {
                for (Statement statement : ((BlockStmt) elseStatement).getStatements()) {
                    String currentNode = statement.toString();
                    controlFlowGraph.addVertex(currentNode);
                    controlFlowGraph.addEdge(elseNode, currentNode);
                    elseNode = currentNode;
                }
            }
        }
    }

    private static void handleForStatement(ForStmt forStatement, Graph<String, DefaultEdge> controlFlowGraph, String parentNode) {
        String forNode = forStatement.toString();
        controlFlowGraph.addVertex(forNode);
        controlFlowGraph.addEdge(parentNode, forNode);

        Statement forBodyStatement = forStatement.getBody();
        if (forBodyStatement instanceof BlockStmt) {
            for (Statement statement : ((BlockStmt) forBodyStatement).getStatements()) {
                String currentNode = statement.toString();
                controlFlowGraph.addVertex(currentNode);
                controlFlowGraph.addEdge(forNode, currentNode);
                forNode = currentNode;
            }
        } else {
            String bodyNode = forBodyStatement.toString();
            controlFlowGraph.addVertex(bodyNode);
            controlFlowGraph.addEdge(forNode, bodyNode);
        }
    }
}