package com.isaac.ggmanager.ui.home.team.member;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.R;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.TextWatcherUtils;
import com.isaac.ggmanager.databinding.DialogEmailInputBinding;
import com.isaac.ggmanager.databinding.FragmentCreateTeamBinding;
import com.isaac.ggmanager.databinding.FragmentMemberBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MemberFragment extends Fragment {

    private FragmentMemberBinding binding;
    private DialogEmailInputBinding bindingDialog;
    private MemberViewModel memberViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater, container, false);

        memberViewModel = new ViewModelProvider(this).get(MemberViewModel.class);

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
        observeViewModel();
    }

    private void observeViewModel() {
        memberViewModel.getMemberViewState().observe(getViewLifecycleOwner(), memberViewState -> {

            switch (memberViewState.getValidationState()){
                case VALIDATING:
                    bindingDialog.tilEmail.setError(memberViewState.isEmailValid() ? null : "Email no permitido");
                    break;
                case IDLE:
                    bindingDialog.tilEmail.setError(null);
                    break;
            }

            switch (memberViewState.getStatus()){
                case LOADING:
                    bindingDialog.btnSend.setEnabled(false);
                    break;
                case SUCCESS:
                    // COSAS
                    break;
                case ERROR:
                    Toast.makeText(getContext(), memberViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void setUpListeners() {
        binding.fabAddMember.setOnClickListener(v -> {
            showAlertDialog();
        });
    }

    private void showAlertDialog() {
        bindingDialog = DialogEmailInputBinding.inflate(getLayoutInflater());

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(bindingDialog.getRoot())
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextWatcherUtils.enableViewOnTextChange(
                bindingDialog.etEmail,
                bindingDialog.btnSend,
                bindingDialog.tilEmail
        );

        bindingDialog.btnCancel.setOnClickListener(v -> dialog.dismiss());

        bindingDialog.btnSend.setOnClickListener(v -> {
            String email = bindingDialog.etEmail.getText().toString().trim();
            memberViewModel.validateEmail(email);
        });

        dialog.show();
    }
}