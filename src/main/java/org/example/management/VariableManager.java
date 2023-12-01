package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.MemberVariableDeclarationDTO;
import org.dto.StmtVariableDeclarationDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// initializer에 value 안들어감, Initializer의 type 제대로 못가져옴
public class VariableManager {

    public static Map<String, List<String>> memberVariableDeclTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("field_declaration"));
        put("c", Arrays.asList("field_declaration"));
        put("python", Arrays.asList());
    }};

    public static Map<String, List<String>> stmtVariableDeclTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("local_variable_declaration"));
        put("c", Arrays.asList("declaration"));
        put("python", Arrays.asList());
    }};
    private final List<MemberVariableDeclarationDTO> memberVariableDeclarationDTOList;
    private final List<StmtVariableDeclarationDTO> stmtVariableDeclarationDTOList;

    public VariableManager() {
        this.memberVariableDeclarationDTOList = new ArrayList<>();
        this.stmtVariableDeclarationDTOList = new ArrayList<>();
    }

    public List<MemberVariableDeclarationDTO> getMemberVariableDeclarationDTOList() {
        return this.memberVariableDeclarationDTOList;
    }

    public List<StmtVariableDeclarationDTO> getStmtVariableDeclarationDTOList() {
        return this.stmtVariableDeclarationDTOList;
    }

    public void variableDeclarationListClear() {
        this.memberVariableDeclarationDTOList.clear();
        this.stmtVariableDeclarationDTOList.clear();
    }
/*
    public MemberVariableDeclarationDTO buildVariableDeclInMemberField(Long variableId, Long blockId,
            Long belongedClassId, Node node) {

        MemberVariableDeclarationDTO variableDeclarationDTO = new MemberVariableDeclarationDTO();

        FieldDeclaration fieldDeclaration = (FieldDeclaration) node;

        String modifierKeyword = "";
        String accessModifierKeyword = "";
        Type variableType = null;
        String type = "";
        String name = "";
        Optional<Node> initializer = Optional.empty();

        // 변수 제어자
        NodeList<Modifier> modifiers = fieldDeclaration.getModifiers();
        for (Modifier modifier : modifiers) {
            // 접근 제어자 분별
            if (modifier.getKeyword().equals(Modifier.Keyword.DEFAULT) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PUBLIC) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PROTECTED) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PRIVATE)) {
                accessModifierKeyword = modifier.getKeyword().asString();
            } else {
                modifierKeyword = modifier.getKeyword().asString();
            }
        }
        // 변수 이름, 타입
        NodeList<VariableDeclarator> variableDeclarators = fieldDeclaration.getVariables();
        for (VariableDeclarator variableDeclarator : variableDeclarators) {
            variableType = variableDeclarator.getType();
            // type =
            // variableType.getChildNodes().stream().collect(Collectors.toList()).toString();
            type = variableDeclarator.getType().asString();
            name = variableDeclarator.getName().asString();

            Expression initializerExpr = variableDeclarator.getInitializer().isPresent()
                    ? variableDeclarator.getInitializer().get()
                    : null;
            if (initializerExpr != null) {
                List<Node> initializerChildren = initializerExpr.getChildNodes();
                for (Node child : initializerChildren) {
                    if (child.getMetaModel().getTypeName().equals("SimpleName")) {
                        initializer = Optional.of(child);
                    }
                }
            }
        }

        variableDeclarationDTO.setVariableId(variableId);
        variableDeclarationDTO.setBlockId(blockId);
        variableDeclarationDTO.setBelongedClassId(belongedClassId);
        // variableDeclarationDTO.setTypeClassId(1L); // 일단 1로 한다
        variableDeclarationDTO.setImportId(1L); // 일단 1로 한다
        variableDeclarationDTO.setVariableType(variableType);
        variableDeclarationDTO.setType(type);
        variableDeclarationDTO.setName(name);
        variableDeclarationDTO.setModifier(modifierKeyword);
        variableDeclarationDTO.setAccessModifier(accessModifierKeyword);
        variableDeclarationDTO.setInitializer(initializer);
        variableDeclarationDTO.setNode(node);
        variableDeclarationDTO.setPosition(
                new Position(
                        node.getRange().get().begin.line,
                        node.getRange().get().begin.column,
                        node.getRange().get().end.line,
                        node.getRange().get().end.column));

        memberVariableDeclarationDTOList.add(variableDeclarationDTO);
        return variableDeclarationDTO;
    }

    public StmtVariableDeclarationDTO buildVariableDeclInMethod(Long variableId, Long blockId, Node node) {

        StmtVariableDeclarationDTO variableDeclarationDTO = new StmtVariableDeclarationDTO();
        VariableDeclarationExpr variableDeclarationExpr = (VariableDeclarationExpr) node;

        String modifierKeyword = "";
        String accessModifierKeyword = "";
        Type variableType = null;
        String type = "";
        String name = "";
        Optional<Node> initializer = Optional.empty();

        // 변수 제어자
        NodeList<Modifier> modifiers = variableDeclarationExpr.getModifiers();
        for (Modifier modifier : modifiers) {
            // 접근 제어자 분별
            if (modifier.getKeyword().equals(Modifier.Keyword.DEFAULT) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PUBLIC) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PROTECTED) ||
                    modifier.getKeyword().equals(Modifier.Keyword.PRIVATE)) {
                accessModifierKeyword = modifier.getKeyword().asString();
            } else {
                modifierKeyword = modifier.getKeyword().asString();
            }
        }
        // 변수 이름, 타입
        NodeList<VariableDeclarator> variableDeclarators = variableDeclarationExpr.getVariables();
        for (VariableDeclarator variableDeclarator : variableDeclarators) {
            variableType = variableDeclarator.getType();
            // type =
            // variableType.getChildNodes().stream().collect(Collectors.toList()).toString();
            type = variableDeclarator.getType().asString();
            name = variableDeclarator.getName().asString();
            // List<Node> childNodes = variableDeclarator.getChildNodes();
            // for (Node childNode : childNodes) {
            // String childNodeTypeName = childNode.getMetaModel().getTypeName();
            //
            // }
            Expression initializerExpr = variableDeclarator.getInitializer().isPresent()
                    ? variableDeclarator.getInitializer().get()
                    : null;
            if (initializerExpr != null) {
                List<Node> initializerChildren = initializerExpr.getChildNodes();
                for (Node child : initializerChildren) {
                    if (child.getMetaModel().getTypeName().equals("SimpleName")) {
                        initializer = Optional.of(child);
                    }
                }
            }
        }

        variableDeclarationDTO.setVariableId(variableId);
        variableDeclarationDTO.setBlockId(blockId);
        // variableDeclarationDTO.setTypeClassId(1L); // 일단 1로 한다
        variableDeclarationDTO.setImportId(1L); // 일단 1로 한다
        variableDeclarationDTO.setVariableType(variableType);
        variableDeclarationDTO.setType(type);
        variableDeclarationDTO.setName(name);
        variableDeclarationDTO.setModifier(modifierKeyword);
        variableDeclarationDTO.setAccessModifier(accessModifierKeyword);
        variableDeclarationDTO.setInitializer(initializer);
        variableDeclarationDTO.setNode(node);
        variableDeclarationDTO.setPosition(
                new Position(
                        node.getRange().get().begin.line,
                        node.getRange().get().begin.column,
                        node.getRange().get().end.line,
                        node.getRange().get().end.column));

        stmtVariableDeclarationDTOList.add(variableDeclarationDTO);
        return variableDeclarationDTO;
    }*/

}
