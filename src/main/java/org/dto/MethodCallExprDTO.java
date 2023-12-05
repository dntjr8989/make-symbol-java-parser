package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

import java.util.List;

public class MethodCallExprDTO {
    private String name;
    private Long blockId;
    private Long methodCallExprId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private Position position;
    private String nameExpr;
    private List<ArgumentDTO> arguments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getMethodCallExprId() {
        return methodCallExprId;
    }

    public Long getModifiedMethodCallExprIdByDbSeq() {
        return methodCallExprId + firstSymbolIds.get("method_call_expr");
    }

    public void setMethodCallExprId(Long methodCallExprId) {
        this.methodCallExprId = methodCallExprId;
    }

    public Long getFullQualifiedNameId() {
        return fullQualifiedNameId;
    }

    public Long getModifiedFullQualifiedNameIdByDbSeq() {
        return fullQualifiedNameId + firstSymbolIds.get("full_qualified_name");
    }

    public void setFullQualifiedNameId(Long fullQualifiedNameId) {
        this.fullQualifiedNameId = fullQualifiedNameId;
    }

    public Boolean getIsFullQualifiedNameIdFromDB() {
        return isFullQualifiedNameIdFromDB;
    }

    public void setIsFullQualifiedNameIdFromDB(Boolean isFullQualifiedNameIdFromDB) {
        this.isFullQualifiedNameIdFromDB = isFullQualifiedNameIdFromDB;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ArgumentDTO> getArguments() {
        return arguments;
    }

    public void setArguments(List<ArgumentDTO> arguments) {
        this.arguments = arguments;
    }

    public String getNameExpr() {
        return nameExpr;
    }

    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    @Override
    public String toString() {
        return "MethodCallExprDTO : {" +
                "methodCallExprId : " + methodCallExprId +
                ", blockId : " + blockId +
                ", fullQualifiedNameId : " + fullQualifiedNameId +
                ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
                ", name : '" + name + '\'' +
                ", arguments : '" + arguments + '\'' +
                ", Position : '" + position +
                ", NameExpr : " + nameExpr +
                "}\n";
    }

}