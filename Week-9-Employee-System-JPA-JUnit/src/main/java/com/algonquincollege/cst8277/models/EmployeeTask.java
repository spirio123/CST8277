/***************************************************************************
 * File:  EmployeeTask.java
 * Course materials CST 8277
 * @author Mike Norman
 * @date 2020 04
 *
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeTask {

    protected String description;
    protected LocalDateTime taskStart;
    protected LocalDateTime taskEnd;
    protected boolean taskDone;

    public EmployeeTask() {
    }

    @Column(name="TASK_DESCRIPTION")
    //property named 'description'
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="TASK_START")
    public LocalDateTime getTaskStart() {
        return taskStart;
    }
    public void setTaskStart(LocalDateTime taskStart) {
        this.taskStart = taskStart;
    }

    @Column(name="TASK_END")
    public LocalDateTime getTaskEnd() {
        return taskEnd;
    }
    public void setTaskEnd(LocalDateTime taskEnd) {
        this.taskEnd = taskEnd;
    }

    @Column(name="TASK_DONE")
    public boolean isTaskDone() {
        return taskDone;
    }
    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }

}