package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.ClassDTO;
import org.dto.Position;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassManager {

    private final List<ClassDTO> classDTOList = new ArrayList<>();

    public static Map<String, List<String>> classTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("class_declaration", "interface_declaration", "enum_declaration"));
        put("c", Arrays.asList("struct_specifier"));
        put("python", Arrays.asList("class_definition"));
    }};

    public ClassManager() {
    }

    public List<ClassDTO> getClassDTOList() {
        return this.classDTOList;
    }

    public void classListClear() {
        this.classDTOList.clear();
    }

    public static boolean isClass(String language, String nodeType){
        if(!classTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = classTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public ClassDTO buildClassDTO(Long classId, Long blockId, Long packageId, Node node, SourceCode sourceCode){
        ClassDTO classDTO = new ClassDTO();

        boolean isImplemented = false;
        String implementClass = "";
        String modifierKeyword = "";
        String accessModifierKeyword = "default";
        String className = "";
        String classType;

        Node identifierNode = node.getChildByFieldName("name");
        if(identifierNode != null) {
            className = ByteToString.byteArrayToString(sourceCode.getContent(), identifierNode.getStartByte(), identifierNode.getEndByte());
        }
        //typedef 경우 struct 이름 재정의 됨
        Node parentNode = node.getParent();
        Node declaratorNode = parentNode.getChildByFieldName("declarator");
        if(parentNode.getType().equals("type_definition") && declaratorNode != null){
            className = ByteToString.byteArrayToString(sourceCode.getContent(), declaratorNode.getStartByte(), declaratorNode.getEndByte());
        }

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
        Node interfacesNode = node.getChildByFieldName("interfaces");
        if (interfacesNode != null) {
            isImplemented = true;
            Node interfaceListNode = interfacesNode.getChildByFieldName("implement_list");
            implementClass = ByteToString.byteArrayToString(sourceCode.getContent(), interfaceListNode.getStartByte(), interfacesNode.getEndByte()).split(",")[0];
        }

        classType = node.getType().split("_")[0];
        classDTO.setClassId(classId);
        classDTO.setBlockId(blockId);
        classDTO.setPackageId(packageId);
        classDTO.setName(className);
        classDTO.setModifier(modifierKeyword);
        classDTO.setAccessModifier(accessModifierKeyword);
        classDTO.setType(classType);
        classDTO.setIsImplemented(isImplemented);
        classDTO.setImplementClass(implementClass);
        classDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));

        this.classDTOList.add(classDTO);

        return classDTO;
    }

}
