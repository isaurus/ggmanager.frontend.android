package com.isaac.ggmanager.ui.home.team.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.FragmentTaskBinding;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.ui.home.team.task.create.CreateTaskActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento que muestra la lista de tareas de un equipo.
 * <p>
 * Permite visualizar las tareas asignadas, marcar tareas como completadas
 * y crear nuevas tareas mediante un FloatingActionButton.
 * </p>
 */
@AndroidEntryPoint
public class TaskFragment extends Fragment{

    private FragmentTaskBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;

    /**
     * Inflación del layout del fragmento y obtención del ViewModel.
     *
     * @param inflater           Inflater para generar la vista.
     * @param container          Contenedor padre.
     * @param savedInstanceState Estado previo guardado.
     * @return Vista raíz del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());
        return binding.getRoot();
    }

    /**
     * Configura la UI y los observadores una vez creada la vista.
     *
     * @param view               Vista raíz.
     * @param savedInstanceState Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        setUpListeners();
        observeViewModel();

        taskViewModel.getCurrentUser();
    }

    /**
     * Configura el RecyclerView con su adaptador y layout manager.
     */
    private void setUpRecyclerView(){
        taskAdapter = new TaskAdapter(new ArrayList<>(), (task, isChecked) -> {
            if (isChecked){
                taskViewModel.markTaskAsCompleted(task);
            }
        });
        binding.rvTaskList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTaskList.setAdapter(taskAdapter);
    }

    /**
     * Configura los listeners de los elementos interactivos del fragmento.
     */
    private void setUpListeners() {
        binding.fabAddTask.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateTaskActivity.class)));
    }

    /**
     * Observa los cambios en el estado del ViewModel para actualizar la UI.
     */
    private void observeViewModel(){
        taskViewModel.getTaskViewState().observe(getViewLifecycleOwner(), taskViewState -> {

            switch (taskViewState.getStatus()){
                case SUCCESS:
                    updateTasksList(taskViewState.getTaskList());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), taskViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
                case LOADING:
                    // Aquí se podría mostrar un ProgressBar o indicador de carga
                    break;
            }
        });
    }

    /**
     * Actualiza la lista de tareas mostrada en el adaptador.
     *
     * @param tasks Lista actualizada de tareas.
     */
    private void updateTasksList(List<TaskModel> tasks){
        if (tasks != null){
            taskAdapter.updateData(tasks);
        }
    }
}
