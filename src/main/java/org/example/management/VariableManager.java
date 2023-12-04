package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.MemberVariableDeclarationDTO;
import org.dto.Position;
import org.dto.StmtVariableDeclarationDTO;
import org.example.sourceCode.SourceCode;

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
        put("python", Arrays.asList("assignment"));
    }};

    public static Map<String, List<String>> stmtVariableDeclTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("local_variable_declaration"));
        put("c", Arrays.asList("declaration"));
        put("python", Arrays.asList("assignment"));
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

    public static boolean isMemberVariableDecl(String language, String nodeType){
        //파이썬은 따로 처리해줘야함(부모 확인)
        if(!memberVariableDeclTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = memberVariableDeclTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public static boolean isStmtVariableDecl(String language, String nodeType){
        //파이썬 따로 처리해줘야함
        if(!stmtVariableDeclTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = stmtVariableDeclTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public void variableDeclarationListClear() {
        this.memberVariableDeclarationDTOList.clear();
        this.stmtVariableDeclarationDTOList.clear();
    }

    public MemberVariableDeclarationDTO buildMemberVariableDeclDTO(Long variableId, Long blockId,
                                                                       Long belongedClassId, Node node, SourceCode sourceCode) {

        MemberVariableDeclarationDTO variableDeclarationDTO = new MemberVariableDeclarationDTO();


        String modifierKeyword = "";
        String accessModifierKeyword = "";
        String type = "";
        String name = "";
        Optional<Node> initializer = Optional.empty();

        // 변수 제어자
        Node modifierNode = node.getChildByFieldName("modifiers");

        if(modifierNode != null){
            String[] modifiers = ByteToString.byteArrayToString(sourceCode.getContent(), modifierNode.getStartByte(), modifierNode.getEndByte()).split(" ");
            for(String modifier : modifiers){
                if(modifier.equals("public") || modifier.equals("protected") || modifier.equals("private") ){
                    accessModifierKeyword = modifier;
                }
                else{
                    modifierKeyword = modifier;
                }
            }
        }

        // 변수 이름, 타입
        Node typeNode = node.getChildByFieldName("type");
        if(typeNode != null){
            type = ByteToString.byteArrayToString(sourceCode.getContent(), typeNode.getStartByte(), typeNode.getEndByte());
        }
        List<String> t = this.getTypeAndName(type, name, node, sourceCode);
        type = t.get(0); name = t.get(1);

        variableDeclarationDTO.setVariableId(variableId);
        variableDeclarationDTO.setBlockId(blockId);
        variableDeclarationDTO.setBelongedClassId(belongedClassId);
        // variableDeclarationDTO.setTypeClassId(1L); // 일단 1로 한다
        variableDeclarationDTO.setImportId(1L); // 일단 1로 한다
        variableDeclarationDTO.setType(type);
        variableDeclarationDTO.setName(name);
        variableDeclarationDTO.setModifier(modifierKeyword);
        variableDeclarationDTO.setAccessModifier(accessModifierKeyword);
        variableDeclarationDTO.setInitializer(initializer);
        variableDeclarationDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow(),
                        node.getStartPoint().getColumn(),
                        node.getEndPoint().getRow(),
                        node.getEndPoint().getColumn()));

        memberVariableDeclarationDTOList.add(variableDeclarationDTO);
        return variableDeclarationDTO;
    }

    public StmtVariableDeclarationDTO buildStmtVariableDeclDTO(Long variableId, Long blockId, Node node, SourceCode sourceCode) {

        StmtVariableDeclarationDTO variableDeclarationDTO = new StmtVariableDeclarationDTO();

        String modifierKeyword = "";
        String accessModifierKeyword = "";
        String type = "";
        String name = "";
        Optional<Node> initializer = Optional.empty();

        // 변수 제어자
        Node modifierNode = node.getChildByFieldName("modifiers");
        if(modifierNode != null){
            String[] modifiers = ByteToString.byteArrayToString(sourceCode.getContent(), modifierNode.getStartByte(), modifierNode.getEndByte()).split(" ");
            for(String modifier : modifiers){
                if(modifier.equals("public") || modifier.equals("protected") || modifier.equals("private") ){
                    accessModifierKeyword = modifier;
                }
                else{
                    modifierKeyword = modifier;
                }
            }
        }
        // 변수 이름, 타입
        Node typeNode = node.getChildByFieldName("type");
        if(typeNode != null){
            type = ByteToString.byteArrayToString(sourceCode.getContent(), typeNode.getStartByte(), typeNode.getEndByte());
        }
        List<String> t = this.getTypeAndName(type, name, node, sourceCode);
        type = t.get(0); name = t.get(1);

        variableDeclarationDTO.setVariableId(variableId);
        variableDeclarationDTO.setBlockId(blockId);
        // variableDeclarationDTO.setTypeClassId(1L); // 일단 1로 한다
        variableDeclarationDTO.setImportId(1L); // 일단 1로 한다
        variableDeclarationDTO.setType(type);
        variableDeclarationDTO.setName(name);
        variableDeclarationDTO.setModifier(modifierKeyword);
        variableDeclarationDTO.setAccessModifier(accessModifierKeyword);
        variableDeclarationDTO.setInitializer(initializer);
        variableDeclarationDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow(),
                        node.getStartPoint().getColumn(),
                        node.getEndPoint().getRow(),
                        node.getEndPoint().getColumn()));

        stmtVariableDeclarationDTOList.add(variableDeclarationDTO);
        return variableDeclarationDTO;
    }

    private List<String> getTypeAndName(String type, String name, Node node, SourceCode sourceCode){
        Node declaratorNode = node.getChildByFieldName("declarator");
        List<String> t = new ArrayList<>();
        t.add(""); t.add("");
        if(declaratorNode != null){
            if(declaratorNode.getType().equals("array_declarator")){
                return this.getTypeAndName(type+"[]", name, declaratorNode, sourceCode);
            }
            else if(declaratorNode.getType().equals("identifier") || declaratorNode.getType().equals("field_identifier")){
                name = ByteToString.byteArrayToString(sourceCode.getContent(), declaratorNode.getStartByte(), declaratorNode.getEndByte());
                t.set(0, type); t.add(1, name);
                return t;
            }
            else{
                Node nameNode = declaratorNode.getChildByFieldName("name");
                if(nameNode != null){
                    name = ByteToString.byteArrayToString(sourceCode.getContent(), nameNode.getStartByte(), nameNode.getEndByte());
                    t.set(0, type); t.add(1, name);
                    return t;
                }
                else{
                    return this.getTypeAndName(type, name, declaratorNode, sourceCode);
                }
            }
        }
        return t;
    }
}
