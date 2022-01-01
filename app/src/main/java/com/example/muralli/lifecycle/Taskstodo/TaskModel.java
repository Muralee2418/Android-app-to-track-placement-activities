package com.example.muralli.lifecycle.Taskstodo;

/**
 * Created by Muralli on 08-09-2018.
 */
public class TaskModel {
    int status;
    String task_name;
    String task_frm_date;
    String task_to_date;
    public TaskModel(){}

    public TaskModel(int status, String task_to_date, String task_frm_date, String task_name) {
        this.status = status;
        this.task_to_date = task_to_date;
        this.task_frm_date = task_frm_date;
        this.task_name = task_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_frm_date() {
        return task_frm_date;
    }

    public void setTask_frm_date(String task_frm_date) {
        this.task_frm_date = task_frm_date;
    }

    public String getTask_to_date() {
        return task_to_date;
    }

    public void setTask_to_date(String task_to_date) {
        this.task_to_date = task_to_date;
    }
}
