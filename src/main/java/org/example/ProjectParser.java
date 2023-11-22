package org.example;

import org.example.parser.service.TreeParserService;
import org.example.sourceCode.SourceCode;
import org.example.sourceCode.service.SourceCodeService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ProjectParser {
    public static void main(String[] args) throws UnsupportedEncodingException {
/*
        String str = "컴퓨터";
        System.out.println("str.getBytes(StandardCharsets.UTF_8) = " + str.getBytes(StandardCharsets.UTF_8).length);
        System.out.println("str.getBytes(StandardCharsets.UTF_16LE) = " + str.getBytes(StandardCharsets.UTF_16LE).length);
*/
        System.load("/Users/kws/tmax_cloud/tree-sitter/tree-sitter-parser/libjava-tree-sitter.dylib");
        Path root = Paths.get(("/Users/kws/tmax_cloud/tree-sitter/tree-sitter-parser/src/main/resources/java-baseball-main"));
        //Path root = Paths.get(("/Users/kws/tmax_cloud/tree-sitter/tree-sitter-parser/src/main/resources/C-master"));

        TreeParserService treeParserService = new TreeParserService();

        System.out.println("root = " + root);

        SourceCodeService sourceCodeService = new SourceCodeService(root);
        List<SourceCode> sourceCodeList = sourceCodeService.getSourceCodeList();
        sourceCodeList.forEach(
                sourceCode -> {
                    try {
                        treeParserService.makeSymbol(sourceCode);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}