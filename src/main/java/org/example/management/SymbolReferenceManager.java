package org.example.management;

import org.dto.SymbolReferenceDTO;

import java.util.ArrayList;
import java.util.List;

public class SymbolReferenceManager {

    private final List<SymbolReferenceDTO> symbolReferenceDTOList;

    public SymbolReferenceManager() {
        this.symbolReferenceDTOList = new ArrayList<>();
    }

    public List<SymbolReferenceDTO> getSymbolReferenceDTOList() {
        return this.symbolReferenceDTOList;
    }

    public void symbolReferenceListClear() {
        this.symbolReferenceDTOList.clear();
    }

    public SymbolReferenceDTO buildSymbolReference(Long symbolReferenceId, Long symbolStatusId, String src_path) {
        SymbolReferenceDTO symbolReferenceDTO = new SymbolReferenceDTO();

        symbolReferenceDTO.setSymbolReferenceId(symbolReferenceId);
        symbolReferenceDTO.setSymbolStatusId(symbolStatusId);
        symbolReferenceDTO.setSrcPath(src_path);

        this.symbolReferenceDTOList.add(symbolReferenceDTO);

        return symbolReferenceDTO;
    }
}
