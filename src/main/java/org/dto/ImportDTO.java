package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class ImportDTO {
    private Long importId;
    private Long blockId;
    private String name;
    private Position position;

    public Long getImportId() {
        return importId;
    }

    public Long getModifiedImportIdByDbSeq() {
        return importId + firstSymbolIds.get("import");
    }

    public void setImportId(Long importId) {
        this.importId = importId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ImportDTO :{" +
                "importId : " + importId +
                ", blockId : " + blockId +
                ", name : '" + name + '\'' +
                ", Position : '" + position +
                "}\n";
    }
}
