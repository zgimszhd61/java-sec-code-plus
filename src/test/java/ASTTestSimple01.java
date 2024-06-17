import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.MethodDeclaration;

// 如何将java代码转化为抽象语法树（Abstract Syntax Tree, AST）？请提供一个具体而简单的例子

public class ASTTestSimple01 {
    public static void main(String[] args) {
        String code = "public class HelloWorld { "
                + "public static void main(String[] args) { "
                + "System.out.println(\"Hello, World!\"); "
                + "} "
                + "}";

        // 解析源代码
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(code);

        if(parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
            CompilationUnit compilationUnit = parseResult.getResult().get();

            // 打印AST
            System.out.println(compilationUnit.toString());

            // 遍历AST并输出方法名称
            compilationUnit.accept(new MethodVisitor(), null);

            // 打印 AST
            compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterface -> {
                System.out.println("Class name: " + classOrInterface.getName());
            });

            compilationUnit.findAll(MethodDeclaration.class).forEach(method -> {
                System.out.println("Method name: " + method.getName());
            });
        } else {
            System.out.println("解析失败");
        }
    }

    // 访问者类，用于遍历AST中的方法声明
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("方法名称: " + md.getName());
        }
    }
}
