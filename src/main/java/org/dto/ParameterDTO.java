package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class ParameterDTO {
    private Long parameterId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private Integer index;
    private String name;
    private String type;
    private Position position;

    public Long getParameterId() {
        return parameterId;
    }

    public Long getModifiedParameterIdByDbSeq() {
        return parameterId + firstSymbolIds.get("parameter");
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public Long getMethodDeclId() {
        return methodDeclId;
    }

    public Long getModifiedMethodDeclIdByDbSeq() {
        return methodDeclId + firstSymbolIds.get("method_decl");
    }

    public void setMethodDeclId(Long methodDeclId) {
        this.methodDeclId = methodDeclId;
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
        return "ParameterDTO{" +
                "parameterId: " + parameterId +
                ", methodDeclarationId: " + methodDeclId +
                ", fullQualifiedNameId : " + fullQualifiedNameId +
                ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
                ", index: " + index +
                ", name: '" + name + '\'' +
                ", type: '" + type + '\'' +
                ", position: " + position +
                '}';
    }

}
