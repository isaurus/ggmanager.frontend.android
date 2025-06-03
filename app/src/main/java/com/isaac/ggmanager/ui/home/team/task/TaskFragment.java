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

@AndroidEntryPoint
public class TaskFragment extends Fragment{

    private FragmentTaskBinding binding;
    private TaskViewModel taskViewModel;

    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        setUpListeners();
        observeViewModel();

        taskViewModel.getCurrentUser();
    }

    private void setUpListeners() {
        binding.fabAddTask.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateTaskActivity.class)));
    }

    private void observeViewModel(){
        taskViewModel.getTaskViewState().observe(getViewLifecycleOwner(), taskViewState -> {

            switch (taskViewState.getStatus()){
                case SUCCESS:
                    updateTasksList(taskViewState.getTaskList());
                    Log.i("IYO", "Tengo la lista: " + taskViewState.getTaskList().toString());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), taskViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
                case LOADING:
                    // ProgressBar
                    break;
            }
        });

    }

    private void updateTasksList(List<TaskModel> tasks){
        if (tasks != null){
            taskAdapter.updateData(tasks);
        }
    }


    private void setUpRecyclerView(){
        taskAdapter = new TaskAdapter(new ArrayList<>(), (task, isChecked) -> {
            if (isChecked){
                taskViewModel.markTaskAsCompleted(task);
            }
        });
        binding.rvTaskList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTaskList.setAdapter(taskAdapter);
    }
}