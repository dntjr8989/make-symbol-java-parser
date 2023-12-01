package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.BlockDTO;
import org.dto.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockManager {

    private final List<BlockDTO> blockDTOList;

    public static Map<String, List<String>> blockTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("class_body", "interface_body", "enum_body", "constructor_body", "block"));
        put("c", Arrays.asList("compound_statement", "field_declaration_list"));
        put("python", Arrays.asList("block"));
    }};

    public BlockManager() {
        this.blockDTOList = new ArrayList<>();
    }

    public List<BlockDTO> getBlockDTOList() { return this.blockDTOList; }

    public void blockListClear(){this.blockDTOList.clear();}

    public BlockDTO buildBlock(Long blockId, Integer depth, Long ParentBlockId, String blockType, Node node,
                               Long symbolReferenceId) {
        BlockDTO blockDTO = new BlockDTO();

        blockDTO.setBlockId(blockId);
        blockDTO.setDepth(depth);
        blockDTO.setParentBlockId(ParentBlockId);
        blockDTO.setBlockType(blockType);
        blockDTO.setNode(node);
        blockDTO.setSymbolReferenceId(symbolReferenceId);
        blockDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow(),
                        node.getStartPoint().getColumn(),
                        node.getEndPoint().getRow(),
                        node.getEndPoint().getColumn()));
        this.blockDTOList.add(blockDTO);

        return blockDTO;
    }

    public static boolean isBlock(String language, String nodeType){
        if(!blockTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = blockTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }
}
