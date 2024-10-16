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

        // 解析源代码
        ParseResult<CompilationUnit> parsedResult = new JavaParser().parse(javaSourceCode);
        if(parsedResult.isSuccessful() && parsedResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parsedResult.getResult().get();
            // 生成并打印 XPath 树表示
            printNodeXPath(compilationUnit, "/");
        }
    }

    private static void printNodeXPath(Node currentNode, String currentPath) {
        String nodeTypeName = currentNode.getMetaModel().getTypeName();
        String updatedPath = currentPath + nodeTypeName;

        System.out.println(updatedPath);

        for (Node childNode : currentNode.getChildNodes()) {
            printNodeXPath(childNode, updatedPath + "/");
        }
    }
}