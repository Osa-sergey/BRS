package com.misis.brs;

public class Mark {
    private Integer id;
    private Integer semester;
    private Integer type;
    private Integer mark;
    private Integer maxMark;
    private String description;

    public Mark() {}

    public Mark(Integer semester, Integer type, Integer mark, Integer maxMark, String description)
    {
        this.id = -1;
        this.semester = semester;
        this.type = type;
        this.mark = mark;
        this.maxMark = maxMark;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public Integer getSemester() {
        return semester;
    }

    protected void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getType() {
        return type;
    }

    protected void setType(Integer type) {
        this.type = type;
    }

    public Integer getMark() {
        return mark;
    }

    protected void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getMaxMark() {
        return maxMark;
    }

    protected void setMaxMark(Integer maxMark) {
        this.maxMark = maxMark;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + "\nSemester: " + getSemester() + "\nType: " + getType() + "\nMark: " + getMark() + "\nMaxMark: " + getMaxMark() + "\nDescription: " + getDescription();
    }
}
