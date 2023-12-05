package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class ArgumentDTO {
    private Long argumentId;
    private Long methodCallExprId;
    private Integer index;
    private String name;
    private String type;

    private Position position;

    public Long getArgumentId() {
        return argumentId;
    }

    public Long getModifiedArgumentIdByDbSeq() {
        return argumentId + firstSymbolIds.get("argument");
    }

    public void setArgumentId(Long argumentId) {
        this.argumentId = argumentId;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ArgumentDTO{" +
                "argumentId: " + argumentId +
                ", methodCallExprId: " + methodCallExprId +
                ", index: " + index +
                ", name: '" + name + '\'' +
                ", type: '" + type + '\'' +
                ", position: " + position +
                '}';
    }
}
