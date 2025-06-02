package com.isaac.ggmanager.ui.home.team.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.isaac.ggmanager.databinding.FragmentTaskBinding;
import com.isaac.ggmanager.ui.home.team.task.create.CreateTaskActivity;

public class TaskFragment extends Fragment{

    private FragmentTaskBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
    }

    private void setUpListeners() {
        binding.fabAddTask.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateTaskActivity.class)));
    }
}