import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class ASTTestSimple02 {
    public static void main(String[] args) {
        // 示例 Java 代码
        String source =
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
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(source);
        if(parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parseResult.getResult().get();
            // 打印 AST 结构
            System.out.println("Classes:");
            compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterface -> {
                System.out.println("  Class name: " + classOrInterface.getName());
            });

            System.out.println("\nFields:");
            compilationUnit.findAll(FieldDeclaration.class).forEach(field -> {
                System.out.println("  Field: " + field.getVariables().get(0).getName() + ", Type: " + field.getElementType());
            });

            System.out.println("\nConstructors:");
            compilationUnit.findAll(ConstructorDeclaration.class).forEach(constructor -> {
                System.out.println("  Constructor name: " + constructor.getName());
            });

            System.out.println("\nMethods:");
            compilationUnit.findAll(MethodDeclaration.class).forEach(method -> {
                System.out.println("  Method name: " + method.getName() + ", Return type: " + method.getType());
            });

            System.out.println("\nMethod Calls:");
            compilationUnit.findAll(MethodCallExpr.class).forEach(methodCall -> {
                System.out.println("  Method call: " + methodCall.getName() + ", Arguments: " + methodCall.getArguments());
            });
        }
    }
}
