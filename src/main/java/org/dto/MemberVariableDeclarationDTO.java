package org.dto;

import static org.example.config.GeneratorIdentifier.firstSymbolIds;
public class MemberVariableDeclarationDTO extends VariableDeclarationDTO {

    private Long belongedClassId; // 변수가 포함되어 있는 클래스

    public Long getModifiedMemberVarDeclId() {
        return getVariableId() + firstSymbolIds.get("member_var_decl");
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

    @Override
    public String toString() {
        return "MemberVariableDeclarationDTO : {" +
                "variableId : " + getVariableId() +
                ", blockId : " + getBlockId() +
                ", belongedClassId : " + getBelongedClassId() +
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
