package org.dto;

import ai.serenade.treesitter.Node;

import java.util.Optional;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class VariableDeclarationDTO {
    private Long variableId;
    private Long blockId;
    private Long importId;
    private Long fullQualifiedNameId;
    private Boolean isFullQualifiedNameIdFromDB = false;
    private String name;
    private String modifier;
    private String accessModifier;
    private String type;
    private String variableType;
    private Node node;
    private Optional<Node> initializer;
    private Position position;

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Optional<Node> getInitializer() {
        return initializer;
    }

    public void setInitializer(Optional<Node> initializer) {
        this.initializer = initializer;
    }

    public Long getImportId() {
        return importId;
    }

    public Long getModifiedImportIdByDbSeq() {
        return importId + firstSymbolIds.get("import");
    }

    public void setImportId(Long importId) {
        this.importId = importId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "VariableDeclarationDTO : {" +
                "variableId : " + variableId +
                ", blockId : " + blockId +
                ", importId : " + importId +
                ", fullQualifiedNameId : " + fullQualifiedNameId +
                ", isFullQualifiedNameIdFromDB : " + isFullQualifiedNameIdFromDB +
                ", name : '" + name + '\'' +
                "', nodeType: '" + node.getType() +
                ", modifier : '" + modifier + '\'' +
                ", accessModifier : '" + accessModifier + '\'' +
                ", type : '" + type + '\'' +
                // ", variableType : " + variableType.getClass().getSimpleName() +
                ", initializer : " + initializer +
                ", Position : '" + position +
                "}\n";
    }

}
