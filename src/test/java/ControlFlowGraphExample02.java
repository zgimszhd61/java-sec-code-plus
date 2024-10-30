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
import java.nio.file.Paths;
import java.util.List;

public class ControlFlowGraphExample02 {

    public static void main(String[] args) throws IOException {
        CompilationUnit compilationUnit = parseJavaFile("src/test/java/sample/Example02.java");
        if (compilationUnit != null) {
            MethodDeclaration methodDeclaration = getFirstMethodDeclaration(compilationUnit);
            if (methodDeclaration != null) {
                Graph<String, DefaultEdge> controlFlowGraph = generateControlFlowGraph(methodDeclaration);
                printControlFlowGraph(controlFlowGraph);
            }
        }
    }

    private static CompilationUnit parseJavaFile(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(Paths.get(filePath).toString());
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(fileInputStream);
        if (parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
            return parseResult.getResult().get();
        }
        return null;
    }

    private static MethodDeclaration getFirstMethodDeclaration(CompilationUnit compilationUnit) {
        return compilationUnit.findFirst(MethodDeclaration.class).orElse(null);
    }

    private static void printControlFlowGraph(Graph<String, DefaultEdge> controlFlowGraph) {
        controlFlowGraph.vertexSet().forEach(vertex ->
                System.out.println("节点: " + vertex + ", 边: " + controlFlowGraph.edgesOf(vertex)));
    }

    public static Graph<String, DefaultEdge> generateControlFlowGraph(MethodDeclaration methodDeclaration) {
        Graph<String, DefaultEdge> controlFlowGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        List<Statement> methodStatements = methodDeclaration.getBody().orElseThrow().getStatements();
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

        handleBlockStatements(thenStatement, controlFlowGraph, thenNode);

        if (ifStatement.getElseStmt().isPresent()) {
            Statement elseStatement = ifStatement.getElseStmt().get();
            String elseNode = elseStatement.toString();
            controlFlowGraph.addVertex(elseNode);
            controlFlowGraph.addEdge(parentNode, elseNode);

            handleBlockStatements(elseStatement, controlFlowGraph, elseNode);
        }
    }

    private static void handleForStatement(ForStmt forStatement, Graph<String, DefaultEdge> controlFlowGraph, String parentNode) {
        String forNode = forStatement.toString();
        controlFlowGraph.addVertex(forNode);
        controlFlowGraph.addEdge(parentNode, forNode);

        Statement forBodyStatement = forStatement.getBody();
        handleBlockStatements(forBodyStatement, controlFlowGraph, forNode);
    }

    private static void handleBlockStatements(Statement statement, Graph<String, DefaultEdge> controlFlowGraph, String parentNode) {
        if (statement instanceof BlockStmt) {
            for (Statement innerStatement : ((BlockStmt) statement).getStatements()) {
                String currentNode = innerStatement.toString();
                controlFlowGraph.addVertex(currentNode);
                controlFlowGraph.addEdge(parentNode, currentNode);
                parentNode = currentNode;
            }
        } else {
            String bodyNode = statement.toString();
            controlFlowGraph.addVertex(bodyNode);
            controlFlowGraph.addEdge(parentNode, bodyNode);
        }
    }
}