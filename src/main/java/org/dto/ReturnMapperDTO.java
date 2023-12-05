package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class ReturnMapperDTO {
    private Long returnMapperId;
    private Long methodDeclId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String type;
    private Position position;

    public Long getReturnMapperId() {
        return returnMapperId;
    }

    public Long getModifiedReturnMapperIdByDbSeq() {
        return returnMapperId + firstSymbolIds.get("return_mapper");
    }

    public void setReturnMapperId(Long returnMapperId) {
        this.returnMapperId = returnMapperId;
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

    public Long getMethodDeclId() {
        return methodDeclId;
    }

    public Long getModifiedMethodDeclIdByDbSeq() {
        return methodDeclId + firstSymbolIds.get("method_decl");
    }

    public void setMethodDeclId(Long methodDeclId) {
        this.methodDeclId = methodDeclId;
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
        return "ReturnMapperDTO{" +
                "returnMapperId: " + returnMapperId +
                ", methodDeclId: " + methodDeclId +
                ", fullQualifiedNameId : " + fullQualifiedNameId +
                ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
                ", type: '" + type + '\'' +
                ", position: " + position +
                '}';
    }

}
