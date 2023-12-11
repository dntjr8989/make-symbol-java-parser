package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.Position;
import org.dto.ReturnStmtDTO;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnStmtManager {

    private final List<ReturnStmtDTO> returnStmtDTOList;

    public static Map<String, List<String>> returnStmtTypeMap = new HashMap<String, List<String>>() {{
        put("java", Arrays.asList("return_statement"));
        put("c", Arrays.asList("return_statement"));
        put("python", Arrays.asList());
    }};

    public ReturnStmtManager() {
        this.returnStmtDTOList = new ArrayList<>();
    }

    public List<ReturnStmtDTO> getReturnStmtDTOList() {
        return this.returnStmtDTOList;
    }

    public void returnStmtListClear() {
        this.returnStmtDTOList.clear();
    }

    public static boolean isReturnStmt(String language, String nodeType) {
        if (!returnStmtTypeMap.containsKey(language)) {
            return false;
        }
        List<String> nodeTypeList = returnStmtTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public ReturnStmtDTO buildReturnStmt(Long returnStmtId, Long blockId, Node node, SourceCode sourceCode) {
        ReturnStmtDTO returnStmtDTO = new ReturnStmtDTO();

        Node expressionNode = node.getChildByFieldName("expression");
        String expression = "";
        if (expressionNode != null) {
            expression = ByteToString.byteArrayToString(sourceCode.getContent(), expressionNode.getStartByte(), expressionNode.getEndByte());
        }

        returnStmtDTO.setReturnStmtId(returnStmtId);
        returnStmtDTO.setBlockId(blockId);
        returnStmtDTO.setExpression(expression);
        returnStmtDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow() + 1,
                        node.getStartPoint().getColumn() + 1,
                        node.getEndPoint().getRow() + 1,
                        node.getEndPoint().getColumn() + 1
                )
        );

        returnStmtDTOList.add(returnStmtDTO);

        return returnStmtDTO;
    }
}
