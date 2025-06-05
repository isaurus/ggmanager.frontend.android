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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Actividad que permite crear una nueva tarea dentro de un equipo.
 *
 * Esta clase gestiona la interfaz para ingresar datos de la tarea, seleccionar
 * fecha límite, prioridad y asignar la tarea a un miembro del equipo.
 *
 * La tarea se guarda en Firebase Firestore y se actualizan las referencias en el equipo y usuario.
 */
public class CreateTaskActivity extends AppCompatActivity {

    private ActivityCreateTaskBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String selectedMemberId;
    private String teamId;

    /**
     * Método llamado al crear la actividad. Inicializa la interfaz y configura componentes.
     *
     * @param savedInstanceState Estado guardado previamente (si lo hay).
     */
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

    /**
     * Configura el selector de fecha para la fecha límite de la tarea.
     * Muestra un DatePickerDialog y actualiza el campo de texto con la fecha seleccionada.
     */
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

    /**
     * Configura el dropdown de selección de prioridad con opciones predefinidas ("Alta", "Media", "Baja").
     */
    private void setupPriorityDropdown() {
        String[] priorities = {"Alta", "Media", "Baja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, priorities);
        ((MaterialAutoCompleteTextView) binding.atvPriority).setAdapter(adapter);
    }

    /**
     * Carga el usuario autenticado actual y obtiene su equipo.
     * Si el usuario no está autenticado o no pertenece a un equipo, muestra mensaje y cierra la actividad.
     * En caso exitoso, carga los miembros del equipo para asignar tareas.
     */
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

    /**
     * Carga los miembros del equipo dado su ID y configura el dropdown para seleccionar el miembro asignado.
     *
     * @param teamId Identificador del equipo cuyos miembros se cargarán.
     */
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

    /**
     * Crea una nueva tarea con los datos ingresados en la interfaz.
     * Valida que todos los campos estén completos, crea el documento en Firestore y actualiza referencias en equipo y usuario.
     * Muestra mensajes de éxito o error según corresponda.
     */
    private void createTask() {
        String title = binding.etTeamName.getText().toString().trim();
        String description = binding.etTeamDescription.getText().toString().trim();
        String deadline = binding.etDeadline.getText().toString().trim();
        String priority = binding.atvPriority.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || deadline.isEmpty() || priority.isEmpty() || selectedMemberId == null) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference taskRef = db.collection("tasks").document(); // Crea ID pero no guarda aún
        String taskId = taskRef.getId(); // Genera ID manualmente

        Map<String, Object> task = new HashMap<>();
        task.put("id", taskId);
        task.put("taskTitle", title);
        task.put("taskDescription", description);
        task.put("taskDeadLine", formatDate(deadline));
        task.put("priority", priority);
        task.put("assignedTo", selectedMemberId);
        task.put("createdBy", auth.getUid());
        task.put("teamId", teamId);
        task.put("createdAt", new Date());
        task.put("isCompleted", false);

        taskRef.set(task).addOnSuccessListener(unused -> {
            updateTeamWithTaskId(taskId);
            updateUserWithTaskId(taskId);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al crear tarea", Toast.LENGTH_SHORT).show();
            Log.e("CreateTaskActivity", e.getMessage());
        });
    }

    /**
     * Actualiza el documento del equipo agregando el ID de la tarea creada a la lista de tareas del equipo.
     *
     * @param taskId Identificador de la tarea creada.
     */
    private void updateTeamWithTaskId(String taskId) {
        DocumentReference teamRef = db.collection("teams").document(teamId);
        teamRef.update("teamTasksId", FieldValue.arrayUnion(taskId));
    }

    /**
     * Actualiza el documento del usuario asignado agregando el ID de la tarea creada a su lista de tareas.
     * Muestra un mensaje de éxito al finalizar o un mensaje de error en caso de fallo.
     *
     * @param taskId Identificador de la tarea creada.
     */
    private void updateUserWithTaskId(String taskId) {
        DocumentReference userRef = db.collection("users").document(selectedMemberId);
        userRef.update("teamTasksId", FieldValue.arrayUnion(taskId))
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al asignar tarea", Toast.LENGTH_SHORT).show());
    }

    /**
     * Convierte una cadena con formato "dd/MM/yyyy" a un objeto {@link Date}.
     *
     * @param birthdate Fecha en formato cadena "dd/MM/yyyy".
     * @return Objeto Date correspondiente o null si el formato es inválido.
     */
    private Date formatDate(String birthdate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try{
            return simpleDateFormat.parse(birthdate);
        } catch (ParseException e){
            Log.e("CreateTaskActivity", "Error al parsear fecha: " + e.getMessage());
        }
        return null;
    }
}
