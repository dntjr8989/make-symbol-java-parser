package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.ImportDTO;
import org.dto.Position;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportManager {

    public static Map<String, List<String>> importTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("import_declaration"));
        put("c", Arrays.asList("preproc_include"));
        put("python", Arrays.asList("import_statement", "import_from_statement"));
    }};

    public static boolean isImport(String language, String nodeType){
        if(!importTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = importTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    private final List<ImportDTO> importDTOList;

    public ImportManager() {
        this.importDTOList = new ArrayList<>();
    }

    public List<ImportDTO> getImportDTOList() {
        return this.importDTOList;
    }

    public void importListClear() {
        this.importDTOList.clear();
    }

    public ImportDTO buildImport(Long importId, Long blockId, Node node, SourceCode sourceCode) {
        ImportDTO importDTO = new ImportDTO();
        String importName = "";

        //java, c
        Node pathNode = node.getChildByFieldName("path");
        if(pathNode != null){
            importName = ByteToString.byteArrayToString(sourceCode.getContent(), pathNode.getStartByte(), pathNode.getEndByte())
                    .replace("<", "")
                    .replace(">", "")
                    .trim();
        }

        importDTO.setImportId(importId);
        importDTO.setBlockId(blockId);
        importDTO.setName(importName);
        importDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));

        importDTOList.add(importDTO);
        return importDTO;
    }
}
