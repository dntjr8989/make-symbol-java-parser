package org.dto;

import ai.serenade.treesitter.Node;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class BlockDTO {
    private Long blockId;
    private Long parentBlockId;
    private Long symbolReferenceId;
    private Integer depth;
    private String blockType;
    private Node node;
    private Position position;

    public Long getSymbolReferenceId() {
        return symbolReferenceId;
    }

    public Long getModifiedSymbolReferenceIdByDbSeq() {
        return symbolReferenceId + firstSymbolIds.get("symbol_reference");
    }

    public void setSymbolReferenceId(Long symbolReferenceId) {
        this.symbolReferenceId = symbolReferenceId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public Long getModifiedBlockIdByDbSeq() {
        return blockId + firstSymbolIds.get("block");
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getParentBlockId() {
        return parentBlockId;
    }

    public Long getModifiedParentBlockIdByDbSeq() {
        return parentBlockId + firstSymbolIds.get("block");
    }

    public void setParentBlockId(Long parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    // public String getSrcPath() {
    // return srcPath;
    // }

    // public void setSrcPath(String srcPath) {
    // this.srcPath = srcPath;
    // }

    @Override
    public String toString() {
        return "BlockDTO : {" +
                "blockId : " + blockId +
                ", parentBlockId : " + parentBlockId +
                ", depth : " + depth +
                ", blockType : '" + blockType + '\'' +
                // ", srcPath : '" + srcPath + "\"" +
                ", Position : '" + position + '\'' +
                // "}\n";
                // ", \nblockNode : \n" + blockNode +
                ", symbolReferenceId : " + symbolReferenceId +
                "}\n";
    }
}
