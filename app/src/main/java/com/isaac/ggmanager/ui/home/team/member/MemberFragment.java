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

import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.TextWatcherUtils;
import com.isaac.ggmanager.databinding.DialogEmailInputBinding;
import com.isaac.ggmanager.databinding.FragmentMemberBinding;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento que gestiona la visualización y gestión de miembros del equipo.
 * Permite listar miembros existentes y añadir nuevos mediante un diálogo de entrada de email.
 */
@AndroidEntryPoint
public class MemberFragment extends Fragment {

    private FragmentMemberBinding binding;
    private DialogEmailInputBinding bindingDialog;
    private MemberViewModel memberViewModel;

    private MemberAdapter memberAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater, container, false);

        // Obtiene el ViewModel usando ViewModelProvider
        memberViewModel = new ViewModelProvider(this).get(MemberViewModel.class);

        // Aplica padding para las ventanas del sistema (status bar, navigation bar)
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        setUpListeners();
        observeViewModel();

        // Carga la lista inicial de miembros
        memberViewModel.loadMembers();
    }

    /**
     * Configura los listeners para la UI.
     */
    private void setUpListeners() {
        // Cuando se pulsa el FAB, muestra el diálogo para añadir un miembro
        binding.fabAddMember.setOnClickListener(v -> showAlertDialog());
    }

    /**
     * Observa los cambios en el estado del ViewModel para actualizar la UI.
     */
    private void observeViewModel() {
        memberViewModel.getMemberViewState().observe(getViewLifecycleOwner(), memberViewState -> {

            // Manejo del estado de validación del email
            switch (memberViewState.getValidationState()){
                case VALIDATING:
                    // Muestra error si el email no es válido
                    bindingDialog.tilEmail.setError(memberViewState.isEmailValid() ? null : "Email no permitido");
                    break;
                case IDLE:
                    // Resetea el error y habilita el botón de envío cuando está inactivo
                    if (bindingDialog != null) {
                        bindingDialog.tilEmail.setError(null);
                        bindingDialog.btnSend.setEnabled(true);
                    }
                    break;
            }

            // Manejo del estado general (carga, éxito, error)
            switch (memberViewState.getStatus()){
                case LOADING:
                    if (bindingDialog != null) bindingDialog.btnSend.setEnabled(false);
                    break;
                case SUCCESS:
                    if (bindingDialog != null) {
                        bindingDialog.btnSend.setEnabled(true);
                    }
                    // Actualiza la lista de miembros con los datos recibidos
                    updateMemberList(memberViewState.getMemberList());
                    break;
                case ERROR:
                    if (bindingDialog != null) bindingDialog.btnSend.setEnabled(true);
                    Toast.makeText(getContext(), memberViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    /**
     * Actualiza el adaptador con la nueva lista de miembros.
     * @param members Lista actualizada de usuarios.
     */
    private void updateMemberList(List<UserModel> members){
        if (members != null){
            memberAdapter.updateData(members);
        }
    }

    /**
     * Configura el RecyclerView con su adaptador y layout manager.
     */
    private void setUpRecyclerView(){
        memberAdapter = new MemberAdapter(new ArrayList<>());
        binding.rvMemberList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMemberList.setAdapter(memberAdapter);
    }

    /**
     * Muestra un diálogo personalizado para ingresar un email y añadir un nuevo miembro.
     */
    private void showAlertDialog() {
        bindingDialog = DialogEmailInputBinding.inflate(getLayoutInflater());

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(bindingDialog.getRoot())
                .create();

        // Hace transparente el fondo del diálogo para un diseño más limpio
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Habilita/deshabilita el botón "Enviar" basado en el texto del EditText y muestra errores de validación
        TextWatcherUtils.enableViewOnTextChange(
                bindingDialog.etEmail,
                bindingDialog.btnSend,
                bindingDialog.tilEmail
        );

        // Cancela el diálogo al pulsar "Cancelar"
        bindingDialog.btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Valida el email introducido cuando se pulsa "Enviar"
        bindingDialog.btnSend.setOnClickListener(v -> {
            String email = bindingDialog.etEmail.getText().toString().trim();
            memberViewModel.validateEmail(email);
        });

        dialog.show();
    }
}
