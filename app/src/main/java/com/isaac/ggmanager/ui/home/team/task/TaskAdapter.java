package com.isaac.ggmanager.ui.home.team.task;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.databinding.ItemUserTaskBinding;
import com.isaac.ggmanager.domain.model.TaskModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskModel> tasks;
    private final OnTaskCheckListener listener;

    public TaskAdapter(List<TaskModel> tasks, OnTaskCheckListener listener){
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemUserTaskBinding binding = ItemUserTaskBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        holder.bind(tasks.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void updateData(List<TaskModel> newTasks){
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        private final ItemUserTaskBinding binding;

        public TaskViewHolder(ItemUserTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TaskModel task, OnTaskCheckListener listener){
            binding.tvTaskTitle.setText(task.getTaskTitle());
            Log.i("IYO", "Título de la tarea: " + task.getTaskTitle());
            binding.tvTaskPriority.setText(task.getPriority());
            binding.cbMarkAsCompleted.setOnCheckedChangeListener(null);
            binding.cbMarkAsCompleted.setChecked(task.isCompleted());

            binding.cbMarkAsCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                listener.onTaskChecked(task, isChecked);
            });
        }
    }
}