package com.freedom.javacodesimple.pmdTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.sourceforge.pmd.lang.LanguageProcessor;
import net.sourceforge.pmd.lang.LanguageProcessorRegistry;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.PmdCapableLanguage;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.Parser;
import net.sourceforge.pmd.lang.ast.RootNode;
import net.sourceforge.pmd.lang.ast.SemanticErrorReporter;
import net.sourceforge.pmd.lang.document.TextDocument;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodCall;
import net.sourceforge.pmd.lang.java.ast.JavaVisitorBase;

public class TreeExportTestVisitDi {
    public static void main(String[] args) throws IOException {
        // 指定要解析的Java文件路径
        String filePath = "/Users/a0000/mywork/mmjava/java-code-simple/src/main/java/com/freedom/javacodesimple/api/bsh/BshController.java";
        // 读取文件内容
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // 获取Java语言处理器
        PmdCapableLanguage java = (PmdCapableLanguage) LanguageRegistry.PMD.getLanguageById("java");
        LanguageProcessor processor = java.createProcessor(java.newPropertyBundle());
        Parser parser = processor.services().getParser();

        // 创建文本文档和语言处理器注册表
        try (TextDocument textDocument = TextDocument.readOnlyString(fileContent, java.getDefaultVersion());
             LanguageProcessorRegistry lpr = LanguageProcessorRegistry.singleton(processor)) {
            // 创建解析任务
            Parser.ParserTask task = new Parser.ParserTask(textDocument, SemanticErrorReporter.noop(), lpr);
            // 解析文件内容，生成语法树
            RootNode root = parser.parse(task);

            // 如果根节点是ASTCompilationUnit类型，则开始访问节点
            if (root instanceof ASTCompilationUnit) {
                ASTCompilationUnit compilationUnit = (ASTCompilationUnit) root;
                // 使用MainMethodVisitor访问节点
                compilationUnit.acceptVisitor(new MainMethodVisitor(), null);
            }
        }
    }

    // 访问主方法的访客类
    private static class MainMethodVisitor extends JavaVisitorBase<Object, Object> {
        @Override
        public Object visit(ASTMethodDeclaration node, Object data) {
            // 判断是否为main方法
            if (isMainMethod(node)) {
                System.out.println("Found main method:");
                // 使用MethodCallVisitor访问main方法中的方法调用
                node.acceptVisitor(new MethodCallVisitor(0), null);
            }
            return super.visit(node, data);
        }

        // 判断是否为main方法
        private boolean isMainMethod(ASTMethodDeclaration node) {
            return node.getName().equals("main") && node.isStatic();
        }
    }

    // 访问方法调用的访客类
    private static class MethodCallVisitor extends JavaVisitorBase<Object, Object> {
        private static final int MAX_DEPTH = 10; // 最大递归深度
        private int depth; // 当前递归深度

        // 构造函数，初始化递归深度
        public MethodCallVisitor(int depth) {
            this.depth = depth;
        }

        @Override
        public Object visit(ASTMethodCall node, Object data) {
            // 如果当前深度小于最大深度，则继续递归
            if (depth < MAX_DEPTH) {
                System.out.println("Method call at depth " + depth + ": " + node.getMethodName());
                // 递归调用子节点
                for (Node child : node.children()) {
                    if (child instanceof ASTMethodCall) {
                        // 递归访问子方法调用节点
                        child.acceptVisitor(new MethodCallVisitor(depth + 1), null);
                    }
                }
            }
            return super.visit(node, data);
        }
    }
}
