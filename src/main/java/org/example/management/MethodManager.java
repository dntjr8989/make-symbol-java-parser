package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.ArgumentDTO;
import org.dto.MethodCallExprDTO;
import org.dto.MethodDeclarationDTO;
import org.dto.ParameterDTO;
import org.dto.Position;
import org.dto.ReturnMapperDTO;
import org.example.config.GeneratorIdentifier;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MethodManager {

    public static Map<String, List<String>> methodDeclTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("method_declaration", "constructor_declaration"));
        put("c", Arrays.asList("function_definition"));
        put("python", Arrays.asList("function_definition"));
    }};

    public static Map<String, List<String>> methodCallExprTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("method_invocation"));
        put("c", Arrays.asList("call_expression"));
        put("python", Arrays.asList("call"));
    }};


    public static boolean isMethodDecl(String language, String nodeType){
        if(!methodDeclTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = methodDeclTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public static boolean isMethodCallExpr(String language, String nodeType){
        if(!methodCallExprTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = methodCallExprTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    private final List<MethodDeclarationDTO> methodDeclarationDTOList;
    private final List<MethodCallExprDTO> methodCallExprDTOList;

    private final List<ParameterDTO> parameterDTOList;
    private final List<ArgumentDTO> argumentDTOList;
    private final List<ReturnMapperDTO> returnMapperDTOList;

    public List<MethodDeclarationDTO> getMethodDeclarationDTOList() {
        return this.methodDeclarationDTOList;
    }

    public List<MethodCallExprDTO> getMethodCallExprDTOList() {
        return this.methodCallExprDTOList;
    }

    public List<ParameterDTO> getParameterDTOList() {
        return this.parameterDTOList;
    }

    public List<ArgumentDTO> getArgumentDTOList() {
        return this.argumentDTOList;
    }

    public List<ReturnMapperDTO> getReturnMapperDTOList() {
        return this.returnMapperDTOList;
    }

    public void methodDeclarationListClear() {
        this.methodDeclarationDTOList.clear();
    }

    public void methodCallExprListClear() {
        this.methodCallExprDTOList.clear();
    }

    public void parameterDTOListClear() {
        this.parameterDTOList.clear();
    }

    public void argumentDTOListClear() {
        this.argumentDTOList.clear();
    }

    public void returnMapperDTOListClear() {
        this.returnMapperDTOList.clear();
    }

    public MethodManager() {
        this.methodDeclarationDTOList = new ArrayList<>();
        this.methodCallExprDTOList = new ArrayList<>();
        this.parameterDTOList = new ArrayList<>();
        this.argumentDTOList = new ArrayList<>();
        this.returnMapperDTOList = new ArrayList<>();
    }

    public MethodDeclarationDTO buildMethodDeclaration(Long methodDeclarationId, Long blockId, Long belongedClassId,
                                                       Node node, GeneratorIdentifier generatorIdentifier, SourceCode sourceCode) {

        MethodDeclarationDTO methodDeclarationDTO = new MethodDeclarationDTO();
        ReturnMapperDTO returnMapperDTO = new ReturnMapperDTO();

        List<ParameterDTO> localParameterDTOList = new ArrayList<>();

        String modifierKeyword = "";
        String accessModifierKeyword = "";
        String methodName = "";

        List<String> t = this.findModifiers(node, sourceCode);
        modifierKeyword = t.get(0);
        accessModifierKeyword = t.get(1);
        methodName = this.findMethodName(node, sourceCode);
        this.findParameter(node, sourceCode, localParameterDTOList, methodDeclarationId, generatorIdentifier);
        this.findReturnMapper(node, sourceCode, returnMapperDTO, methodDeclarationId, generatorIdentifier);



        methodDeclarationDTO.setMethodDeclId(methodDeclarationId);
        methodDeclarationDTO.setBlockId(blockId);
        methodDeclarationDTO.setBelongedClassId(belongedClassId);
        methodDeclarationDTO.setName(methodName);
        methodDeclarationDTO.setModifier(modifierKeyword);
        methodDeclarationDTO.setAccessModifier(accessModifierKeyword);
        // add to methodDeclarationDTO
        methodDeclarationDTO.setReturnMapper(returnMapperDTO);
        methodDeclarationDTO.setParameters(localParameterDTOList);

        methodDeclarationDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));

        methodDeclarationDTOList.add(methodDeclarationDTO);
        return methodDeclarationDTO;
    }

    public MethodCallExprDTO buildMethodCallExpr(Long methodCallExprId, Long blockId, Node node, GeneratorIdentifier generatorIdentifier, SourceCode sourceCode) {

        MethodCallExprDTO methodCallExprDTO = new MethodCallExprDTO();
        List<ArgumentDTO> localArgumentDTOList = new ArrayList<>();

        String methodName = "";
        String varName = "";

        //methodName, varName

        //java
        Node nameNode = node.getChildByFieldName("name");
        if(nameNode!= null){
            methodName = ByteToString.byteArrayToString(sourceCode.getContent(), nameNode.getStartByte(), nameNode.getEndByte());
        }
        Node objectNode = node.getChildByFieldName("object");
        if(objectNode!=null){
            varName = ByteToString.byteArrayToString(sourceCode.getContent(), objectNode.getStartByte(), objectNode.getEndByte());
        }

        //c
        Node functionNode = node.getChildByFieldName("function");
        if(functionNode!= null){
            Node argumentNode = functionNode.getChildByFieldName("argument");
            if(argumentNode != null){
                varName = ByteToString.byteArrayToString(sourceCode.getContent(), argumentNode.getStartByte(), argumentNode.getEndByte());
            }
            Node fieldNode = functionNode.getChildByFieldName("field");
            if(fieldNode != null){
                methodName = ByteToString.byteArrayToString(sourceCode.getContent(), fieldNode.getStartByte(), fieldNode.getEndByte());
            }
            else{
                methodName = ByteToString.byteArrayToString(sourceCode.getContent(), functionNode.getStartByte(), functionNode.getEndByte());
            }
        }

        //argument
        this.findArguments(node, sourceCode, localArgumentDTOList, methodCallExprId, generatorIdentifier);

        methodCallExprDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));

        methodCallExprDTO.setMethodCallExprId(methodCallExprId);
        methodCallExprDTO.setBlockId(blockId);
        methodCallExprDTO.setName(methodName);
        methodCallExprDTO.setArguments(localArgumentDTOList);
        methodCallExprDTO.setNameExpr(varName);

        methodCallExprDTOList.add(methodCallExprDTO);
        return methodCallExprDTO;
    }

    private void findArguments(Node node, SourceCode sourceCode, List<ArgumentDTO> localArgumentDTOList, Long methodCallExprId, GeneratorIdentifier generatorIdentifier){
        Map<String, Long> symbolIds = generatorIdentifier.getSymbolIds();
        int argumentIndex = 1;
        Node argumentsNode = node.getChildByFieldName("arguments");
        if(argumentsNode != null){
            for(int i=0; i< argumentsNode.getChildCount(); i++){
                Node argumentNode = argumentsNode.getChild(i);
                if(argumentNode.getType().equals("(") || argumentNode.getType().equals(")") || argumentNode.getType().equals(",")){
                    continue;
                }
                String argumentName = ByteToString.byteArrayToString(sourceCode.getContent(), argumentNode.getStartByte(), argumentNode.getEndByte());
                String argumentType = argumentNode.getType();

                ArgumentDTO argumentDTO = new ArgumentDTO();
                argumentDTO.setIndex(argumentIndex++);
                argumentDTO.setName(argumentName);
                argumentDTO.setArgumentId(symbolIds.get("argument"));
                symbolIds.put("argument", symbolIds.get("argument") + 1);
                argumentDTO.setMethodCallExprId(methodCallExprId);
                // 임시로 Node Type 으로 저장
                argumentDTO.setType(argumentType);
                argumentDTO.setPosition(
                        new Position(
                                argumentNode.getStartPoint().getRow()+1,
                                argumentNode.getStartPoint().getColumn()+1,
                                argumentNode.getEndPoint().getRow()+1,
                                argumentNode.getEndPoint().getColumn()+1));
                argumentDTOList.add(argumentDTO);
                localArgumentDTOList.add(argumentDTO);
            }
        }
    }
    private void findReturnMapper(Node node, SourceCode sourceCode, ReturnMapperDTO returnMapperDTO, Long methodDeclarationId, GeneratorIdentifier generatorIdentifier){

        Node returnTypeNode = node.getChildByFieldName("type");
        Map<String, Long> symbolIds = generatorIdentifier.getSymbolIds();

        if(returnTypeNode != null){
            String returnValueTypeName = ByteToString.byteArrayToString(sourceCode.getContent(), returnTypeNode.getStartByte(), returnTypeNode.getEndByte());
            returnMapperDTO.setReturnMapperId(symbolIds.get("return_mapper"));
            symbolIds.put("return_mapper", symbolIds.get("return_mapper") + 1);
            returnMapperDTO.setMethodDeclId(methodDeclarationId);
            // returnMapperDTO.setTypeClassIdImpl(1L); // 일단 1로 박는다
            returnMapperDTO.setType(returnValueTypeName);
            returnMapperDTO.setPosition(
                    new Position(
                            returnTypeNode.getStartPoint().getRow()+1,
                            returnTypeNode.getStartPoint().getColumn()+1,
                            returnTypeNode.getEndPoint().getRow()+1,
                            returnTypeNode.getEndPoint().getColumn()+1));
        }
        returnMapperDTOList.add(returnMapperDTO);
    }

    private void findParameter(Node node, SourceCode sourceCode, List<ParameterDTO> localParameterDTOList, Long methodDeclarationId, GeneratorIdentifier generatorIdentifier){

        //java
        Node parametersNode = node.getChildByFieldName("parameters");

        //c
        if(parametersNode == null){
            Node declaratorNode = node.getChildByFieldName("declarator");
            if(declaratorNode != null){
                parametersNode = declaratorNode.getChildByFieldName("parameters");
            }
        }
        Map<String, Long> symbolIds = generatorIdentifier.getSymbolIds();
        if(parametersNode != null){
            int parameterIndex = 1;
            for(int i=0; i<parametersNode.getChildCount(); i++){
                Node parameterNode = parametersNode.getChild(i);
                if(parameterNode.getType().equals("(") || parameterNode.getType().equals(")") || parameterNode.getType().equals(",")){
                    continue;
                }
                String parameterType=""; String parameterName="";
                // 변수 이름, 타입
                Node typeNode = parameterNode.getChildByFieldName("type");
                if(typeNode != null){
                    parameterType = ByteToString.byteArrayToString(sourceCode.getContent(), typeNode.getStartByte(), typeNode.getEndByte());
                }
                List<String> t = this.getTypeAndName(parameterType, parameterName, parameterNode, sourceCode);
                parameterType = t.get(0); parameterName = t.get(1);

                ParameterDTO parameterDTO = new ParameterDTO();
                parameterDTO.setParameterId(symbolIds.get("parameter"));
                symbolIds.put("parameter", symbolIds.get("parameter") + 1);
                parameterDTO.setMethodDeclId(methodDeclarationId);
                // parameterDTO.setTypeClassIdImpl(1L); // 일단 1로 박는다
                parameterDTO.setIndex(parameterIndex++);
                parameterDTO.setName(parameterName);
                parameterDTO.setType(parameterType);
                parameterDTO.setPosition(
                        new Position(
                                parameterNode.getStartPoint().getRow()+1,
                                parameterNode.getStartPoint().getColumn()+1,
                                parameterNode.getEndPoint().getRow()+1,
                                parameterNode.getEndPoint().getColumn()+1));

                parameterDTOList.add(parameterDTO);
                localParameterDTOList.add(parameterDTO);
            }
        }

    }
    private List<String> getTypeAndName(String type, String name, Node node, SourceCode sourceCode){
        List<String> t = new ArrayList<>();
        t.add(""); t.add("");

        //java
        Node nameNode = node.getChildByFieldName("name");
        if(nameNode != null){
            name = ByteToString.byteArrayToString(sourceCode.getContent(), nameNode.getStartByte(), nameNode.getEndByte());
            t.set(0, type); t.add(1, name);
            return t;
        }

        //c
        Node declaratorNode = node.getChildByFieldName("declarator");
        if(declaratorNode != null){
            if(declaratorNode.getType().equals("array_declarator")){
                return this.getTypeAndName(type+"[]", name, declaratorNode, sourceCode);
            }
            else if(declaratorNode.getType().equals("pointer_declarator")){
                return this.getTypeAndName(type+"*", name, declaratorNode, sourceCode);
            }
            else if(declaratorNode.getType().equals("identifier")){
                name = ByteToString.byteArrayToString(sourceCode.getContent(), declaratorNode.getStartByte(), declaratorNode.getEndByte());
                t.set(0, type); t.add(1, name);
                return t;
            }
            return this.getTypeAndName(type, name, declaratorNode, sourceCode);
        }

        return t;
    }
    private String findMethodName(Node node, SourceCode sourceCode){

        //java
        Node nameNode = node.getChildByFieldName("name");
        if(nameNode != null){
            return ByteToString.byteArrayToString(sourceCode.getContent(), nameNode.getStartByte(), nameNode.getEndByte());
        }
        //c
        Node funcDeclaratorNode = node.getChildByFieldName("declarator");
        if(funcDeclaratorNode != null){
            if(funcDeclaratorNode.getType().equals("function_declarator")){
                Node identifierNode = funcDeclaratorNode.getChildByFieldName("declarator");
                if(identifierNode.getType().equals("identifier")){
                    return ByteToString.byteArrayToString(sourceCode.getContent(), identifierNode.getStartByte(), identifierNode.getEndByte());
                }
            }
        }
        return "";
    }
    private List<String> findModifiers(Node node,
                                               SourceCode sourceCode){
        List<String> t = new ArrayList<>(); // 0 : modifierKeyword, 1 : accessModifierKeyword
        t.add(""); t.add("default");

        for (int i=0; i<node.getChildCount(); i++) {

            Node childNode = node.getChild(i);
            Node modifierNode = childNode.getChildByFieldName("modifiers");
            if (modifierNode != null) {
                String[] modifiers = ByteToString.byteArrayToString(sourceCode.getContent(), modifierNode.getStartByte(), modifierNode.getEndByte()).split(" ");
                for (String modifier : modifiers) {
                    if (modifier.equals("public") || modifier.equals("protected") || modifier.equals("private")) {
                        t.set(1, modifier);
                    } else {
                        t.set(0, modifier);
                    }
                }
            }
        }
        return t;
    }
}
