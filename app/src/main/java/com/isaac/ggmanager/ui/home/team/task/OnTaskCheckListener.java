package com.isaac.ggmanager.ui.home.team.task;

import com.isaac.ggmanager.domain.model.TaskModel;

public interface OnTaskCheckListener {
    void onTaskChecked(TaskModel task, boolean isChecked);
}
