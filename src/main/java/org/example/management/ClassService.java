package org.example.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassService {

    public static Map<String, List<String>> classTypeMap = new HashMap<String, List<String>>(){{
        put("java", Arrays.asList("class_declaration", "interface_declaration", "enum_declaration"));
        put("c", Arrays.asList("struct_specifier"));
        put("python", Arrays.asList("class_definition"));
    }};

    public static boolean isClass(String language, String nodeType){
        if(!classTypeMap.containsKey(language)){
            return false;
        }
        List<String> nodeTypeList = classTypeMap.get(language);
        return nodeTypeList.contains(nodeType);
    }
}
