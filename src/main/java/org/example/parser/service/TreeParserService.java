package org.example.parser.service;

import ai.serenade.treesitter.Languages;
import ai.serenade.treesitter.Node;
import ai.serenade.treesitter.Parser;
import ai.serenade.treesitter.Tree;
import org.dto.BlockDTO;
import org.dto.ClassDTO;
import org.dto.MemberVariableDeclarationDTO;
import org.dto.MethodDeclarationDTO;
import org.dto.PackageDTO;
import org.dto.StmtVariableDeclarationDTO;
import org.dto.SymbolReferenceDTO;
import org.example.config.GeneratorIdentifier;
import org.example.management.BlockManager;
import org.example.management.ClassManager;
import org.example.management.MethodManager;
import org.example.management.PackageManager;
import org.example.management.SymbolReferenceManager;
import org.example.management.VariableManager;
import org.example.sourceCode.SourceCode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TreeParserService {

    private final GeneratorIdentifier generatorIdentifier = new GeneratorIdentifier();

    private final SymbolReferenceManager symbolReferenceManager = new SymbolReferenceManager();

    private final BlockManager blockManager = new BlockManager();

    private final PackageManager packageManager = new PackageManager();

    private final ClassManager classManager = new ClassManager();

    private final VariableManager variableManager = new VariableManager();

    private final MethodManager methodManager = new MethodManager();

    public List<SymbolReferenceDTO> getSymbolReferenceDTOList() {
        return symbolReferenceManager.getSymbolReferenceDTOList();
    }
    public List<BlockDTO> getBlockDTOList() {
        return blockManager.getBlockDTOList();
    }

    public List<ClassDTO> getClassDTOList() {
        return classManager.getClassDTOList();
    }

    public List<PackageDTO> getPackageDTOList() {
        return packageManager.getPackageDTOList();
    }

    public List<MemberVariableDeclarationDTO> getMemberVariableDeclarationDTO(){return variableManager.getMemberVariableDeclarationDTOList();}

    public List<StmtVariableDeclarationDTO> getStmtVariableDeclarationDTO(){return variableManager.getStmtVariableDeclarationDTOList();}

    public void makeSymbol(Long symbolStatusId, SourceCode sourceCode) throws UnsupportedEncodingException {

        //if(!sourceCode.getPath().equals("/Users/kws/tmax_cloud/tree-sitter/tree-sitter-parser/src/main/resources/java-baseball-main/src/main/java/application/baseball/Game.java")){
         //   return;
        //}
        System.out.println("sourceCode.getContent().length = " + sourceCode.getContent().length);
        System.out.println("sourceCode.getPath() = " + sourceCode.getPath());
        this.parse(symbolStatusId, sourceCode);
    }
    private void parse(Long symbolStatusId, SourceCode sourceCode) throws UnsupportedEncodingException {
        try(Parser parser = new Parser()){
            parser.setLanguage(this.makeTreeSitterLanguage(sourceCode.getLanguage()));

            String code = new String(sourceCode.getContent(), StandardCharsets.UTF_16LE);

            try (Tree tree = parser.parseString(code)){
                Node node = tree.getRootNode();
                System.out.println("node.getNodeString() = " + node.getNodeString());
                BlockDTO rootBlock = this.visitAndBuildRoot(node, symbolStatusId, sourceCode);
                this.visitAndBuild(node, symbolStatusId, sourceCode, rootBlock);
            }
        }
    }

    private void visitAndBuild(Node node, Long symbolStatusId, SourceCode sourceCode, BlockDTO parentBlockDTO) {

        Map<String, Long> symbolIds = generatorIdentifier.symbolIds;
        PackageDTO packageDTO = null;
        BlockDTO[] parentBlockDTOList = new BlockDTO[node.getChildCount()];
        boolean[] stopVisitAndBuild = new boolean[node.getChildCount()];
        for(int i=0; i<node.getChildCount(); i++)
        {
            Node childNode = node.getChild(i);
            String language = sourceCode.getLanguage(); String type = childNode.getType();
            if(PackageManager.isPackage(language, type)){
                packageDTO = packageManager.buildPackage(symbolIds.get("package"), parentBlockDTO.getBlockId(), childNode, sourceCode);
                symbolIds.put("package", symbolIds.get("package")+1);
                System.out.println("packageDTO = " + packageDTO);
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = false;
            }
            else if(ClassManager.isClass(language, type)){
                ClassDTO classDTO = classManager.buildClassDTO(symbolIds.get("class"),
                        parentBlockDTO.getBlockId(), packageDTO != null ? packageDTO.getPackageId() : -100L,
                        childNode,
                        sourceCode);
                symbolIds.put("class", symbolIds.get("class")+1);
                System.out.println("classDTO = " + classDTO);
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = true;
            }
            else if(BlockManager.isBlock(language, type)){
                BlockDTO blockDTO = blockManager.buildBlock(symbolIds.get("block"), parentBlockDTO.getDepth()+1,
                        parentBlockDTO.getBlockId(), node.getType(), node, parentBlockDTO.getSymbolReferenceId());
                symbolIds.put("block", symbolIds.get("block")+1);
                System.out.println("blockDTO = " + blockDTO);
                parentBlockDTOList[i] = blockDTO;
                stopVisitAndBuild[i] = true;
            }
            else if(VariableManager.isMemberVariableDecl(language, type)){
                ClassDTO belongedClassDTO = classManager.getClassDTOList().get(classManager.getClassDTOList().size() - 1);
                MemberVariableDeclarationDTO memberVarDeclDTO = variableManager.buildMemberVariableDeclDTO(symbolIds.get("member_var_decl"), parentBlockDTO.getBlockId(),
                        belongedClassDTO.getClassId(), childNode, sourceCode);
                symbolIds.put("member_var_decl", symbolIds.get("member_var_decl") + 1);
                System.out.println("memberVarDeclDTO = " + memberVarDeclDTO);
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = false;
            }
            else if(VariableManager.isStmtVariableDecl(language, type)){
                StmtVariableDeclarationDTO stmtVarDecl = variableManager.buildStmtVariableDeclDTO(symbolIds.get("stmt_var_decl"), parentBlockDTO.getBlockId(), childNode, sourceCode);
                symbolIds.put("stmt_var_decl", symbolIds.get("stmt_var_decl") + 1);
                System.out.println("stmtVarDecl = " + stmtVarDecl);
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = false;
            }
            else if(MethodManager.isMethodDecl(language, type)){
                ClassDTO belongedClassDTO = new ClassDTO();
                if(language.equals("java"))
                {
                    belongedClassDTO = classManager.getClassDTOList().get(classManager.getClassDTOList().size() - 1);
                }
                MethodDeclarationDTO methodDeclDTO = methodManager.buildMethodDeclaration(symbolIds.get("method_decl"),
                        parentBlockDTO.getBlockId(),
                        belongedClassDTO.getClassId(), childNode, generatorIdentifier, sourceCode);
                symbolIds.put("method_decl", symbolIds.get("method_decl") + 1);
                System.out.println("methodDeclDTO = " + methodDeclDTO);
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = true;
            }
            else{
                parentBlockDTOList[i] = parentBlockDTO;
                stopVisitAndBuild[i] = true;
            }
        }
        for(int i=0; i<node.getChildCount(); i++){
            Node childNode = node.getChild(i);
            if(stopVisitAndBuild[i])
            {
                visitAndBuild(childNode, symbolStatusId, sourceCode, parentBlockDTOList[i]);
            }
        }
    }

    private BlockDTO visitAndBuildRoot(Node node, Long symbolStatusId, SourceCode sourceCode){

        Map<String, Long> symbolIds = generatorIdentifier.symbolIds;
        String nodeType = node.getType();
        Long currentSymbolReferenceId = symbolIds.get("symbol_reference");
        SymbolReferenceDTO symbolReferenceDTO = symbolReferenceManager.buildSymbolReference(currentSymbolReferenceId, symbolStatusId, sourceCode.getPath());
        symbolIds.put("symbol_reference", currentSymbolReferenceId + 1);
        Long symbolReferenceId = symbolReferenceDTO.getSymbolReferenceId();
        BlockDTO rootBlockDTO = blockManager.buildBlock(symbolIds.get("block"), 1, null, nodeType, node, symbolReferenceId);
        symbolIds.put("block", symbolIds.get("block")+1);

        return rootBlockDTO;
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
}
