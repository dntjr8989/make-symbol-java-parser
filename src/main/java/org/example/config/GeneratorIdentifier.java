package org.example.config;

import java.util.LinkedHashMap;
import java.util.Map;


// TODO : symbolId들을 관리하는걸 중앙화 해서 DB에서 가져와서 바로 +1 되도록 하는 느낌으로 변경 필요
public class GeneratorIdentifier {

    // fetchForEndSymbolId할 때 처음 값을 가지고 있어야 제대로 수정할 수 있다.
    // 이 값은 lock이 걸리는 SymbolSaver에서만 사용
    public static final Map<String, Long> firstSymbolIds = new LinkedHashMap<>();
    public static boolean isFetchedForStartId = false;

    private static void initializeFirstSymbolId() {
        firstSymbolIds.put("symbol_reference", 0L);
        firstSymbolIds.put("block", 0L);
        firstSymbolIds.put("package", 0L);
        firstSymbolIds.put("import", 0L);
        firstSymbolIds.put("class", 0L);
        firstSymbolIds.put("member_var_decl", 0L);
        firstSymbolIds.put("stmt_var_decl", 0L);
        firstSymbolIds.put("method_decl", 0L);
        firstSymbolIds.put("parameter", 0L);
        firstSymbolIds.put("argument", 0L);
        firstSymbolIds.put("return_mapper", 0L);
        firstSymbolIds.put("method_call_expr", 0L);
        firstSymbolIds.put("assign_expr", 0L);
        firstSymbolIds.put("full_qualified_name", 0L);
    }

    public final Map<String, Long> symbolIds = new LinkedHashMap<>();

    public GeneratorIdentifier() {
        symbolIds.put("symbol_reference", 0L);
        symbolIds.put("block", 0L);
        symbolIds.put("package", 0L);
        symbolIds.put("import", 0L);
        symbolIds.put("class", 0L);
        symbolIds.put("member_var_decl", 0L);
        symbolIds.put("stmt_var_decl", 0L);
        symbolIds.put("method_decl", 0L);
        symbolIds.put("parameter", 0L);
        symbolIds.put("argument", 0L);
        symbolIds.put("return_mapper", 0L);
        symbolIds.put("method_call_expr", 0L);
        symbolIds.put("assign_expr", 0L);
        symbolIds.put("full_qualified_name", 0L);
    }

    public Map<String, Long> getSymbolIds() {
        return symbolIds;
    }

}
