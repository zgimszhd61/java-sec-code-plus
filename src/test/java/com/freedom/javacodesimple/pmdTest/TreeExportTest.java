//用于生成AST树. - 2024.06.27

package com.freedom.javacodesimple.pmdTest;

import java.io.IOException;
import net.sourceforge.pmd.lang.LanguageProcessor;
import net.sourceforge.pmd.lang.LanguageProcessorRegistry;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.PmdCapableLanguage;
import net.sourceforge.pmd.lang.ast.Parser;
import net.sourceforge.pmd.lang.ast.RootNode;
import net.sourceforge.pmd.lang.ast.SemanticErrorReporter;
import net.sourceforge.pmd.lang.document.TextDocument;
import net.sourceforge.pmd.util.treeexport.XmlTreeRenderer;

public class TreeExportTest {
    public static void main(String[] args) throws IOException {
        PmdCapableLanguage java = (PmdCapableLanguage) LanguageRegistry.PMD.getLanguageById("java");
        LanguageProcessor processor = java.createProcessor(java.newPropertyBundle());
        Parser parser = processor.services().getParser();

        try (TextDocument textDocument = TextDocument.readOnlyString("class Foo { int a; }", java.getDefaultVersion());
             LanguageProcessorRegistry lpr = LanguageProcessorRegistry.singleton(processor)) {
            Parser.ParserTask task = new Parser.ParserTask(textDocument, SemanticErrorReporter.noop(), lpr);
            RootNode root = parser.parse(task);
            new XmlTreeRenderer().renderSubtree(root, System.out);
        }
    }
}