package org.example.management;

import org.dto.SymbolReferenceDTO;

import java.util.ArrayList;
import java.util.List;

public class SymbolReferenceManager {

    private final List<SymbolReferenceDTO> symbolReferencdDTOList;

    public SymbolReferenceManager() {
        this.symbolReferencdDTOList = new ArrayList<>();
    }

    public List<SymbolReferenceDTO> getSymbolReferenceDTOList() {
        return this.symbolReferencdDTOList;
    }

    public void symbolReferenceListClear() {
        this.symbolReferencdDTOList.clear();
    }

    public SymbolReferenceDTO buildSymbolReference(Long symbolReferenceId, Long symbolStatusId, String src_path) {
        SymbolReferenceDTO symbolReferenceDTO = new SymbolReferenceDTO();

        symbolReferenceDTO.setSymbolReferenceId(symbolReferenceId);
        symbolReferenceDTO.setSymbolStatusId(symbolStatusId);
        symbolReferenceDTO.setSrcPath(src_path);

        this.symbolReferencdDTOList.add(symbolReferenceDTO);

        return symbolReferenceDTO;
    }
}
