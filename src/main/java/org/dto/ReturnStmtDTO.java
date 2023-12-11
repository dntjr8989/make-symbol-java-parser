package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class ReturnStmtDTO {
    private Long returnStmtId;
    private Long blockId;
    private String expression;
    private Position position;

    public Long getModifiedReturnStmtIdByDbSeq() {
        return returnStmtId + firstSymbolIds.get("return_stmt");
    }

    public Long getModifiedBlockIdByDbSeq() {
        return blockId + firstSymbolIds.get("block");
    }

    public Long getReturnStmtId() {
        return returnStmtId;
    }

    public void setReturnStmtId(Long returnStmtId) {
        this.returnStmtId = returnStmtId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ReturnStmtDTO{" +
                "returnStmtId=" + returnStmtId +
                ", blockId=" + blockId +
                ", expression='" + expression + '\'' +
                ", position=" + position +
                '}';
    }

}

