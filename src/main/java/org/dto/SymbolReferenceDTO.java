package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class SymbolReferenceDTO {
    private Long symbolReferenceId;
    private Long symbolStatusId;
    private String srcPath;

    public Long getSymbolReferenceId() {
        return symbolReferenceId;
    }

    public Long getModifiedSymbolReferenceIdByDbSeq() {
        return symbolReferenceId + firstSymbolIds.get("symbol_reference");
    }

    public void setSymbolReferenceId(Long symbolReferenceId) {
        this.symbolReferenceId = symbolReferenceId;
    }

    public Long getSymbolStatusId() {
        return symbolStatusId;
    }

    public void setSymbolStatusId(Long symbolStatusId) {
        this.symbolStatusId = symbolStatusId;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    @Override
    public String toString() {
        return "SymbolReferenceDTO : {" +
                "symbolReferenceId : " + symbolReferenceId + "\n" +
                ", symbolStatusId : " + symbolStatusId + "\n" +
                ", srcPath : " + srcPath + "\n" +
                "} \n";
    }
}
