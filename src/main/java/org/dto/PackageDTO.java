package org.dto;

public class PackageDTO {
    private Long packageId;
    private Long blockId;
    private String name;
    private Position position;

    public Long getPackageId() {
        return this.packageId;
    }
    public Long getBlockId() {
        return this.blockId;
    }
    public String getName() {
        return this.name;
    }
    public Position getPosition() {
        return this.position;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PackageDTO : {" +
                "packageId : " + packageId +
                ", blockId : " + blockId +
                ", name : '" + name + '\'' +
                ", Position : '" + position +
                "}\n";
    }
}
