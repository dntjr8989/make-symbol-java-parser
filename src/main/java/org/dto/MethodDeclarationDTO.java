package org.dto;

import java.util.List;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class MethodDeclarationDTO {
    private Long methodDeclId;
    private Long blockId;
    private Long belongedClassId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String name;
    private String modifier;
    private String accessModifier;
    private ReturnMapperDTO returnMapper;
    private List<ParameterDTO> parameters;
    private Position position;

    public Long getMethodDeclId() {
        return methodDeclId;
    }

    public Long getModifiedMethodDeclIdByDbSeq() {
        return methodDeclId + firstSymbolIds.get("method_decl");
    }

    public void setMethodDeclId(Long methodDeclId) {
        this.methodDeclId = methodDeclId;
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

    public Long getBelongedClassId() {
        return belongedClassId;
    }

    public Long getModifiedBelongedClassIdByDbSeq() {
        return belongedClassId + firstSymbolIds.get("class");
    }

    public void setBelongedClassId(Long belongedClassId) {
        this.belongedClassId = belongedClassId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public ReturnMapperDTO getReturnMapper() {
        return returnMapper;
    }

    public void setReturnMapper(ReturnMapperDTO returnMapper) {
        this.returnMapper = returnMapper;
    }

    public List<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "MethodDeclarationDTO{" +
                "methodDeclarationId: " + methodDeclId +
                ", blockId: " + blockId +
                ", belongedClassId: " + belongedClassId +
                ", fullQualifiedNameId : " + fullQualifiedNameId +
                ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
                ", name: '" + name + '\'' +
                ", modifier: '" + modifier + '\'' +
                ", accessModifier: '" + accessModifier + '\'' +
                ", returnMappers: " + returnMapper +
                ", parameters: " + parameters +
                ", position: " + position +
                "}\n";
    }
}
