import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.MethodDeclaration;

// 如何将java代码转化为抽象语法树（Abstract Syntax Tree, AST）？请提供一个具体而简单的例子

public class ASTParserExample01 {
    public static void main(String[] args) {
        String sourceCode = "public class HelloWorld { "
                + "public static void main(String[] args) { "
                + "System.out.println(\"Hello, World!\"); "
                + "} "
                + "}";

        // 解析源代码
        ParseResult<CompilationUnit> parsingResult = new JavaParser().parse(sourceCode);

        if(parsingResult.isSuccessful() && parsingResult.getResult().isPresent()) {
            CompilationUnit astRoot = parsingResult.getResult().get();

            // 打印AST
            System.out.println(astRoot.toString());

            // 遍历AST并输出方法名称
            astRoot.accept(new MethodNamePrinter(), null);

            // 打印类名
            astRoot.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterface -> {
                System.out.println("Class name: " + classOrInterface.getName());
            });

            // 打印方法名
            astRoot.findAll(MethodDeclaration.class).forEach(method -> {
                System.out.println("Method name: " + method.getName());
            });
        } else {
            System.out.println("Parsing failed");
        }
    }

    // 访问者类，用于遍历AST中的方法声明
    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration methodDeclaration, Void arg) {
            super.visit(methodDeclaration, arg);
            System.out.println("Method name: " + methodDeclaration.getName());
        }
    }
}