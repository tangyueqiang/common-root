package org.micro.commons.basic.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTree extends FileMeta {

    /**
     * 中文名称
     */
    private String zhname;

    private List<FileTree> children;

    public FileTree() {
        super();
        this.children = new ArrayList<>();
    }

    public FileTree(String path) {
        super(path);
        this.children = new ArrayList<>();
    }

    public FileTree(File file) {
        if (file != null) {
            setPath(file.getAbsolutePath());
            setName(file.getName());
            setDirectory(file.isDirectory());
            setFile(file.isFile());
            setSize(file.length());
        }
        this.children = new ArrayList<>();
    }

    public FileTree(String path, long size) {
        super(path, size);
        this.children = new ArrayList<>();
    }

    public String getZhname() {
        return zhname;
    }

    public void setZhname(String zhname) {
        this.zhname = zhname;
    }

    public List<FileTree> getChildren() {
        return children;
    }

    public void setChildren(List<FileTree> children) {
        this.children = children;
    }

}
