package org.example.sourceCode.service;

import org.example.sourceCode.SourceCode;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceCodeService {

    private final Path root;

    private final List<SourceCode> sourceCodeList = new ArrayList<>();

    private final Map<String, String>  extLanguageMap;
    public SourceCodeService(Path root){
        this.root = root;
        this.extLanguageMap = makeExtLanguageMap();
        this.makeSourceCodeList(root.toString());
    }

    private Map<String, String> makeExtLanguageMap(){
        return Stream.of(new String[][]{
                {"java", "java"},
                {"c", "c"},
                {"py", "python"}
        }).collect(Collectors.toMap(data->data[0], data->data[1]));
    }

    private void makeSourceCodeList(String path){
        File[] files = new File(path).listFiles();

        for(File file : files){
            String filePath = file.getAbsolutePath();
            if(file.isDirectory()){
                makeSourceCodeList(filePath);
            }else{
                String fileName = file.getName();
                String language = this.findLanguage(fileName);
                if(language != null) {
                    this.sourceCodeList.add(SourceCode.create(filePath, language));
                }
            }
        }
    }

    private String findLanguage(String fileName){
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
        return this.extLanguageMap.get(ext);
    }
    public Path getRoot(){
        return this.root;
    }

    public List<SourceCode> getSourceCodeList(){
        return this.sourceCodeList;
    }
}
