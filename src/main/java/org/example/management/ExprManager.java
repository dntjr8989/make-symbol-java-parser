package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.AssignExprDTO;
import org.dto.Position;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExprManager {

    public static Map<String, List<String>> assignExprTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("assignment_expression"));
        put("c", Arrays.asList("assignment_expression"));
        put("python", Arrays.asList("assignment"));
    }};

    public static boolean isAssignExpr(String language, String nodeType){
        if(!assignExprTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = assignExprTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }
    private final List<AssignExprDTO> assignExprDTOList;

    public ExprManager() {
        this.assignExprDTOList = new ArrayList<>();
    }

    public List<AssignExprDTO> getAssignExprDTOList() {
        return this.assignExprDTOList;
    }

    public void assignExprDTOListClear() {
        this.assignExprDTOList.clear();
    }

    public AssignExprDTO buildAssignExpr(Long assignExprId, Long blockId, Node node, SourceCode sourceCode) {
        AssignExprDTO assignExprDTO = new AssignExprDTO();

        String target = null;
        String value = null;

        Node targetNode = node.getChildByFieldName("left");
        if(targetNode != null){
            target = ByteToString.byteArrayToString(sourceCode.getContent(), targetNode.getStartByte(), targetNode.getEndByte());
        }
        Node valueNode = node.getChildByFieldName("right");
        if(valueNode != null){
            value = ByteToString.byteArrayToString(sourceCode.getContent(), valueNode.getStartByte(), valueNode.getEndByte());
        }

        assignExprDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));
        assignExprDTO.setAssignExprId(assignExprId);
        assignExprDTO.setBlockId(blockId);
        assignExprDTO.setTarget(target);
        assignExprDTO.setValue(value);

        assignExprDTOList.add(assignExprDTO);

        return assignExprDTO;
    }
}
