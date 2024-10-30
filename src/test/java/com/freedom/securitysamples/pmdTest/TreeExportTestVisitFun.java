package com.freedom.securitysamples.pmdTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.sourceforge.pmd.lang.LanguageProcessor;
import net.sourceforge.pmd.lang.LanguageProcessorRegistry;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.PmdCapableLanguage;
import net.sourceforge.pmd.lang.ast.Parser;
import net.sourceforge.pmd.lang.ast.RootNode;
import net.sourceforge.pmd.lang.ast.SemanticErrorReporter;
import net.sourceforge.pmd.lang.document.TextDocument;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaVisitorBase;

public class TreeExportTestVisitFun {
    public static void main(String[] args) throws IOException {
        // 定义要读取的Java文件的路径
        String filePath = "src/main/java/com/freedom/javacodesimple/api/bsh/BshController.java";
        // 读取文件内容并转换为字符串
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // 获取PMD支持的Java语言对象
        PmdCapableLanguage java = (PmdCapableLanguage) LanguageRegistry.PMD.getLanguageById("java");
        // 创建语言处理器
        LanguageProcessor processor = java.createProcessor(java.newPropertyBundle());
        // 获取解析器
        Parser parser = processor.services().getParser();

        // 使用try-with-resources语句确保资源自动关闭
        try (TextDocument textDocument = TextDocument.readOnlyString(fileContent, java.getDefaultVersion());
             LanguageProcessorRegistry lpr = LanguageProcessorRegistry.singleton(processor)) {
            // 创建解析任务
            Parser.ParserTask task = new Parser.ParserTask(textDocument, SemanticErrorReporter.noop(), lpr);
            // 解析文件内容，生成语法树
            RootNode root = parser.parse(task);

            // 检查根节点是否为ASTCompilationUnit类型
            if (root instanceof ASTCompilationUnit) {
                ASTCompilationUnit compilationUnit = (ASTCompilationUnit) root;
                // 使用自定义的访问者遍历语法树
                compilationUnit.acceptVisitor(new MethodSummaryVisitor(), null);
            }
        }
    }

    // 自定义访问者类，用于访问方法声明节点
    private static class MethodSummaryVisitor extends JavaVisitorBase<Object, Object> {
        @Override
        public Object visit(ASTMethodDeclaration node, Object data) {
            // 打印方法名称
            System.out.println("Method name: " + node.getName());
            // 打印方法参数
            System.out.println("Parameters: " + node.getFormalParameters());
            return super.visit(node, data);
        }
    }
}