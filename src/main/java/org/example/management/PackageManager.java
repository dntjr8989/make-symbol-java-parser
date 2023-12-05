package org.example.management;

import ai.serenade.treesitter.Node;
import org.dto.PackageDTO;
import org.dto.Position;
import org.example.sourceCode.SourceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageManager {

    private final List<PackageDTO> packageDTOList;

    public static Map<String, List<String>> packageTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("package_declaration"));
        put("c", Arrays.asList());
        put("python", Arrays.asList());
    }};

    public PackageManager() {
        this.packageDTOList = new ArrayList<>();
    }

    public List<PackageDTO> getPackageDTOList() {
        return this.packageDTOList;
    }

    public void packageListClear() {
        this.packageDTOList.clear();
    }

    public static boolean isPackage(String language, String nodeType){
        if(!packageTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = packageTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }

    public PackageDTO buildPackage(Long packageId, Long blockId, Node node, SourceCode sourceCode) {
        PackageDTO packageDTO = new PackageDTO();
        String packageName = null;
        String pattern = "[a-z0-9\\.]+\\;"; // 마지막에 $ 붙이면 왜 안되지
        String nodeValue = ByteToString.byteArrayToString(sourceCode.getContent(), node.getStartByte(), node.getEndByte());
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(nodeValue);
        while (m.find()) {
            packageName = m.group();
        }
        if (packageName != null) {
            packageName = packageName.replace(";", "");
        }
        // nodeValue = nodeValue.replace("package", "");
        // nodeValue = nodeValue.trim();

        packageDTO.setPackageId(packageId);
        packageDTO.setBlockId(blockId);
        packageDTO.setName(packageName);

        packageDTO.setPosition(
                new Position(
                        node.getStartPoint().getRow()+1,
                        node.getStartPoint().getColumn()+1,
                        node.getEndPoint().getRow()+1,
                        node.getEndPoint().getColumn()+1));

        this.packageDTOList.add(packageDTO);

        return packageDTO;
    }
}
