package com.isaac.ggmanager.ui.home.team.task;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.databinding.ItemUserTaskBinding;
import com.isaac.ggmanager.domain.model.TaskModel;

import java.util.List;

/**
 * Adaptador para mostrar una lista de tareas en un RecyclerView.
 * <p>
 * Cada elemento representa una tarea con su título, prioridad y estado de completado,
 * permitiendo marcar o desmarcar la tarea como completada.
 * </p>
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskModel> tasks;
    private final OnTaskCheckListener listener;

    /**
     * Constructor del adaptador.
     *
     * @param tasks    Lista inicial de tareas a mostrar.
     * @param listener Listener para manejar eventos de marcado/desmarcado de tareas.
     */
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

    /**
     * Actualiza la lista de tareas mostradas en el adaptador y notifica el cambio.
     *
     * @param newTasks Nueva lista de tareas.
     */
    public void updateData(List<TaskModel> newTasks){
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder para representar cada tarea en la lista.
     */
    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        private final ItemUserTaskBinding binding;

        /**
         * Constructor del ViewHolder.
         *
         * @param binding Binding generado para el layout del ítem de tarea.
         */
        public TaskViewHolder(ItemUserTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Vincula los datos de la tarea al layout y configura el listener para el checkbox.
         *
         * @param task     Objeto TaskModel con los datos de la tarea.
         * @param listener Listener para eventos de marcado o desmarcado.
         */
        public void bind(TaskModel task, OnTaskCheckListener listener){
            binding.tvTaskTitle.setText(task.getTaskTitle());
            binding.tvTaskPriority.setText(task.getPriority());
            binding.cbMarkAsCompleted.setOnCheckedChangeListener(null);
            binding.cbMarkAsCompleted.setChecked(task.isCompleted());

            binding.cbMarkAsCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                listener.onTaskChecked(task, isChecked);
            });
        }
    }
}
