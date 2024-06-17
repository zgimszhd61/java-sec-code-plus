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

public class TreeExportTestVisit {
    public static void main(String[] args) throws IOException {

        String filePath = "/Users/a0000/mywork/mmjava/java-code-simple/src/main/java/com/freedom/javacodesimple/api/bsh/BshController.java";
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        PmdCapableLanguage java = (PmdCapableLanguage) LanguageRegistry.PMD.getLanguageById("java");
        LanguageProcessor processor = java.createProcessor(java.newPropertyBundle());
        Parser parser = processor.services().getParser();

        try (TextDocument textDocument = TextDocument.readOnlyString(fileContent, java.getDefaultVersion());
             LanguageProcessorRegistry lpr = LanguageProcessorRegistry.singleton(processor)) {
            Parser.ParserTask task = new Parser.ParserTask(textDocument, SemanticErrorReporter.noop(), lpr);
            RootNode root = parser.parse(task);

            if (root instanceof ASTCompilationUnit) {
                ASTCompilationUnit compilationUnit = (ASTCompilationUnit) root;
                compilationUnit.acceptVisitor(new MainMethodVisitor(), null);
            }
        }
    }

    private static class MainMethodVisitor extends JavaVisitorBase<Object, Object> {
        @Override
        public Object visit(ASTMethodDeclaration node, Object data) {
            if (isMainMethod(node)) {
                System.out.println("Found main method:");
                node.acceptVisitor(new MethodCallVisitor(), null);
            }
            return super.visit(node, data);
        }

        private boolean isMainMethod(ASTMethodDeclaration node) {
            return node.getName().equals("main") && node.isStatic();
        }
    }

    private static class MethodCallVisitor extends JavaVisitorBase<Object, Object> {
        @Override
        public Object visit(ASTMethodCall node, Object data) {
            System.out.println("Method call: " + node.getMethodName());
            return super.visit(node, data);
        }
    }
}