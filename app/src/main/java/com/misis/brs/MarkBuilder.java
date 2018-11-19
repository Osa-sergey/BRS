package com.misis.brs;

public class MarkBuilder {
    private Integer id;
    private Integer semester;
    private Integer type;
    private Integer mark;
    private Integer maxMark = -1;
    /* это значение заглушка, если данное поле равно -1, то данный тип не имеет нестандартного максимального значения или оно не указано
    // данное поле предназначено, в первую очередь, для вывода количество баллов при работе в классе и для предотвращения
    // ошибок в сумме количества этих баллов за семестер
    */
    private String description = null;

    protected MarkBuilder buildId(Integer id){
        this.id = id;
        return this;
    }

    public  MarkBuilder buildSemester(Integer semester){
        this.semester = semester;
        return this;
    }

    public MarkBuilder buildType(Integer type){
        this.type = type;
        return this;
    }

    public MarkBuilder buildMark(Integer mark){
        this.mark = mark;
        return this;
    }

    public MarkBuilder buildMaxMark(Integer maxMark){
        this.maxMark = maxMark;
        return this;
    }

    public MarkBuilder buildDescription(String description){
        this.description = description;
        return this;
    }

    public Mark build(){
        Mark mark = new Mark();
        mark.setId(id);
        mark.setSemester(semester);
        mark.setType(type);
        mark.setMark(this.mark);
        mark.setMaxMark(maxMark);
        mark.setDescription(description);
        return mark;
    }
}
