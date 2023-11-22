package org.example.sourceCode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class SourceCode {
    private final String path;

    private final String language;

    private byte[] content;

    private SourceCode(String path, String language, byte[] content){
        this.path = path; this.language = language; this.content = content;
    }

    public static SourceCode create(String path, String language) {
        File file = new File(path);
        byte[] fileContentUtf8 = new byte[0];
        byte[] fileContentUtf16Le = new byte[0];
        try {
            fileContentUtf8 = Files.readAllBytes(file.toPath());
            fileContentUtf16Le = new String(fileContentUtf8, Charset.forName("UTF-8"))
                    .getBytes(Charset.forName("UTF-16LE"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SourceCode(path, language, fileContentUtf16Le);
    }

    public String getPath(){
        return this.path;
    }

    public String getLanguage(){
        return this.language;
    }

    public byte[] getContent(){
        return this.content;
    }
}
