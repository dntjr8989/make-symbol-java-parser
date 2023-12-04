package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;

public class StmtVariableDeclarationDTO extends VariableDeclarationDTO {

    public Long getModifiedStmtVarDeclIdByDbSeq() {
        return getVariableId() + firstSymbolIds.get("stmt_var_decl");
    }

    @Override
    public String toString() {
        return "StmtVariableDeclarationDTO : {" +
                "variableId : " + getVariableId() +
                ", blockId : " + getBlockId() +
                ", importId : " + getImportId() +
                ", name : '" + getName() + '\'' +
                ", modifier : '" + getModifier() + '\'' +
                ", accessModifier : '" + getAccessModifier() + '\'' +
                ", type : '" + getType() + '\'' +
                // ", variableType : " + variableType.getClass().getSimpleName() +
                ", initializer : " + getInitializer() +
                ", Position : '" + getPosition() +
                "}\n";
    }
}
