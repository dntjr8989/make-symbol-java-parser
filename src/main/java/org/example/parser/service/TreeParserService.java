package org.example.parser.service;

import ai.serenade.treesitter.Languages;
import ai.serenade.treesitter.Node;
import ai.serenade.treesitter.Parser;
import ai.serenade.treesitter.Tree;
import org.example.management.ClassService;
import org.example.sourceCode.SourceCode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TreeParserService {

    private final ClassService classService = new ClassService();

    public void makeSymbol(SourceCode sourceCode) throws UnsupportedEncodingException {

        if(!sourceCode.getPath().equals("/Users/kws/tmax_cloud/tree-sitter/tree-sitter-parser/src/main/resources/java-baseball-main/src/main/java/application/baseball/Game.java")){
            return;
        }
        System.out.println("sourceCode.getContent().length = " + sourceCode.getContent().length);
        System.out.println("sourceCode.getPath() = " + sourceCode.getPath());
        this.parse(sourceCode);
    }
    private void parse(SourceCode sourceCode) throws UnsupportedEncodingException {
        try(Parser parser = new Parser()){
            parser.setLanguage(this.makeTreeSitterLanguage(sourceCode.getLanguage()));

            String code = new String(sourceCode.getContent(), StandardCharsets.UTF_16LE);

            try (Tree tree = parser.parseString(code)){
                this.convertToSymbol(tree.getRootNode(), sourceCode);
            }
        }
    }

    private void convertToSymbol(Node node, SourceCode sourceCode) {
        if(ClassService.isClass(sourceCode.getLanguage(), node.getType())){
            System.out.println("node.getChildByFieldName(\"name\").getNodeString() = " + node.getChildByFieldName("name").getNodeString());
            System.out.println("node.getType() = " + node.getType());
            System.out.println("node.getNodeString() = " + node.getNodeString());
            System.out.println("node.getStartByte() = " + node.getStartByte());
            System.out.println("node.getEndByte() = " + node.getEndByte());
            System.out.println("node String = " + byteArrayToString(sourceCode.getContent(), node.getStartByte(), node.getEndByte()));
        }
        for(int i=0; i<node.getChildCount(); i++){
            Node childNode = node.getChild(i);
            convertToSymbol(childNode, sourceCode);
        }
    }

    private long makeTreeSitterLanguage(String language){
        if(Objects.equals(language, "java")){
            return Languages.java();
        }
        else if( Objects.equals(language, "c")){
            return Languages.c();
        }
        else if( Objects.equals(language, "python")){
            return Languages.python();
        }
        throw new Error();
    }

    private String byteArrayToString(byte[] byteArray, int startIndex, int endIndex) {
        // 변환할 범위의 길이 계산
        int length = endIndex - startIndex;

        // 새로운 byte 배열 생성하여 복사
        byte[] subArray = new byte[length];
        System.arraycopy(byteArray, startIndex, subArray, 0, length);

        // String으로 변환
        String resultString = new String(subArray, StandardCharsets.UTF_16LE);

        return resultString;
    }
}
