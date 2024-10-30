import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ASTParserExample03 {
    public static void main(String[] args) {
        // 示例 Java 代码
        String javaSourceCode =
                "public class ComplexExample {\n" +
                        "    private int number;\n" +
                        "    \n" +
                        "    public ComplexExample(int number) {\n" +
                        "        this.number = number;\n" +
                        "    }\n" +
                        "    \n" +
                        "    public int getNumber() {\n" +
                        "        return number;\n" +
                        "    }\n" +
                        "    \n" +
                        "    public void setNumber(int number) {\n" +
                        "        this.number = number;\n" +
                        "    }\n" +
                        "    \n" +
                        "    public static void main(String[] args) {\n" +
                        "        ComplexExample example = new ComplexExample(42);\n" +
                        "        System.out.println(\"Number: \" + example.getNumber());\n" +
                        "        example.setNumber(100);\n" +
                        "        System.out.println(\"Updated Number: \" + example.getNumber());\n" +
                        "    }\n" +
                        "}";

        parseAndPrintXPath(javaSourceCode);
    }

    // 封装的函数：用于解析并打印源代码的 XPath 表示
    public static void parseAndPrintXPath(String sourceCode) {
        ParseResult<CompilationUnit> parsedResult = new JavaParser().parse(sourceCode);
        if (parsedResult.isSuccessful() && parsedResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parsedResult.getResult().get();
            // 生成并打印 XPath 树表示
            printNodeXPath(compilationUnit, "/");
        } else {
            System.out.println("解析失败，无法生成 AST。");
        }
    }

    // 打印节点的 XPath
    private static void printNodeXPath(Node currentNode, String currentPath) {
        String nodeTypeName = currentNode.getMetaModel().getTypeName();
        String updatedPath = currentPath + nodeTypeName;

        System.out.println(updatedPath);

        for (Node childNode : currentNode.getChildNodes()) {
            printNodeXPath(childNode, updatedPath + "/");
        }
    }
}