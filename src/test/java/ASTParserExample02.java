import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class ASTParserExample02 {
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

        parseAndPrintAST(javaSourceCode);
    }

    /**
     * 解析 Java 源代码并打印 AST 结构
     *
     * @param javaSourceCode Java 源代码字符串
     */
    public static void parseAndPrintAST(String javaSourceCode) {
        // 解析源代码
        ParseResult<CompilationUnit> compilationUnitResult = new JavaParser().parse(javaSourceCode);
        if (compilationUnitResult.isSuccessful() && compilationUnitResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = compilationUnitResult.getResult().get();

            // 打印 AST 结构
            System.out.println("Classes:");
            compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
                System.out.println("  Class name: " + classDeclaration.getName());
            });

            System.out.println("\nFields:");
            compilationUnit.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
                System.out.println("  Field: " + fieldDeclaration.getVariables().get(0).getName() + ", Type: " + fieldDeclaration.getElementType());
            });

            System.out.println("\nConstructors:");
            compilationUnit.findAll(ConstructorDeclaration.class).forEach(constructorDeclaration -> {
                System.out.println("  Constructor name: " + constructorDeclaration.getName());
            });

            System.out.println("\nMethods:");
            compilationUnit.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                System.out.println("  Method name: " + methodDeclaration.getName() + ", Return type: " + methodDeclaration.getType());
            });

            System.out.println("\nMethod Calls:");
            compilationUnit.findAll(MethodCallExpr.class).forEach(methodCallExpression -> {
                System.out.println("  Method call: " + methodCallExpression.getName() + ", Arguments: " + methodCallExpression.getArguments());
            });
        }
    }
}
