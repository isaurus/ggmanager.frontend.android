package com.isaac.ggmanager.ui.home.team.task.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.ActivityCreateTaskBinding;

import java.text.SimpleDateFormat;
import java.util.*;

public class CreateTaskActivity extends AppCompatActivity {

    private ActivityCreateTaskBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String selectedMemberId;
    private String teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());
        setContentView(binding.getRoot());

        setupDatePicker();
        setupPriorityDropdown();
        loadCurrentUserAndTeamMembers();

        binding.btnCreateTask.setOnClickListener(v -> createTask());
    }

    private void setupDatePicker() {
        binding.etDeadline.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                binding.etDeadline.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupPriorityDropdown() {
        String[] priorities = {"Alta", "Media", "Baja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, priorities);
        ((MaterialAutoCompleteTextView) binding.atvPriority).setAdapter(adapter);
    }

    private void loadCurrentUserAndTeamMembers() {
        String uid = auth.getUid();
        if (uid == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db.collection("users").document(uid).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                teamId = snapshot.getString("teamId");

                if (teamId == null || teamId.isEmpty()) {
                    Toast.makeText(this, "No perteneces a un equipo", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                loadTeamMembers(teamId);
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadTeamMembers(String teamId) {
        db.collection("users")
                .whereEqualTo("teamId", teamId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<String> memberNames = new ArrayList<>();
                    Map<String, String> nameToIdMap = new HashMap<>();

                    querySnapshot.forEach(doc -> {
                        String name = doc.getString("name");
                        String id = doc.getId();
                        if (name != null) {
                            memberNames.add(name);
                            nameToIdMap.put(name, id);
                        }
                    });

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, memberNames);
                    ((MaterialAutoCompleteTextView) binding.atvMember).setAdapter(adapter);

                    binding.atvMember.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedName = adapter.getItem(position);
                        selectedMemberId = nameToIdMap.get(selectedName);
                    });
                });
    }

    private void createTask() {
        String title = binding.etTeamName.getText().toString().trim();
        String description = binding.etTeamDescription.getText().toString().trim();
        String deadline = binding.etDeadline.getText().toString().trim();
        String priority = binding.atvPriority.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || deadline.isEmpty() || priority.isEmpty() || selectedMemberId == null) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> task = new HashMap<>();
        task.put("title", title);
        task.put("description", description);
        task.put("deadline", deadline);
        task.put("priority", priority);
        task.put("assignedTo", selectedMemberId);
        task.put("createdBy", auth.getUid());
        task.put("teamId", teamId);
        task.put("createdAt", new Date());

        db.collection("tasks").add(task).addOnSuccessListener(taskRef -> {
            String taskId = taskRef.getId();
            updateTeamWithTaskId(taskId);
            updateUserWithTaskId(taskId);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al crear tarea", Toast.LENGTH_SHORT).show();
            Log.e("Prueba", e.getMessage());
        });
    }

    private void updateTeamWithTaskId(String taskId) {
        DocumentReference teamRef = db.collection("teams").document(teamId);
        teamRef.update("teamTasksId", FieldValue.arrayUnion(taskId));
    }

    private void updateUserWithTaskId(String taskId) {
        DocumentReference userRef = db.collection("users").document(selectedMemberId);
        userRef.update("teamTasksId", FieldValue.arrayUnion(taskId))
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al asignar tarea", Toast.LENGTH_SHORT).show());
    }
}
