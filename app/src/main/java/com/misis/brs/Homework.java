package com.misis.brs;

public class Homework {
    private Integer semester;
    private long deadline;
    private String hometask;
    private Boolean check;

    public Homework(Integer semester, long deadline, String hometask, Boolean check) {
        setSemester(semester);
        setDeadline(deadline);
        setHometask(hometask);
        setCheck(check);
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getHometask() {
        return hometask;
    }

    public void setHometask(String hometask) {
        this.hometask = hometask;
    }

    @Override
    public String toString() {
        return "Semester: " + getSemester() + "\nDeadline: " + getDeadline() + "\nHometask: " + getHometask();
    }
}
