package com.isaac.ggmanager.data.repository.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.base.FirestoreRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación base abstracta de un repositorio para operaciones CRUD sobre Firestore.
 * <p>
 * Esta clase implementa operaciones genéricas para acceder a una colección Firestore,
 * obtener documentos, actualizar y eliminar, usando LiveData y Resource para comunicar
 * el estado asíncrono al ViewModel.
 * </p>
 *
 * @param <T> Tipo de modelo que representa los documentos de la colección Firestore.
 */
public abstract class FirestoreRepositoryImpl<T> implements FirestoreRepository<T> {

    protected final FirebaseFirestore firestore;

    /**
     * Constructor que recibe la instancia de FirebaseFirestore para acceso a la base de datos.
     *
     * @param firestore instancia de FirebaseFirestore
     */
    public FirestoreRepositoryImpl(FirebaseFirestore firestore){
        this.firestore = firestore;
    }

    /**
     * Método abstracto que debe devolver la referencia a la colección Firestore específica
     * del modelo T en la base de datos.
     *
     * @return CollectionReference de la colección Firestore
     */
    protected abstract CollectionReference getCollection();

    /**
     * Método abstracto que debe devolver la clase del modelo T, usada para la deserialización.
     *
     * @return Clase de tipo T
     */
    protected abstract Class<T> getModelClass();

    /**
     * Método abstracto que debe devolver el identificador único (ID) del documento Firestore
     * para el modelo proporcionado, usado para operaciones de actualización o eliminación.
     *
     * @param model instancia del modelo T
     * @return ID del documento Firestore asociado al modelo
     */
    protected abstract String getDocumentId(T model);

    /**
     * Obtiene un documento Firestore por su ID, devolviendo un LiveData que emite
     * Resource con el estado de la operación y el modelo resultante.
     *
     * @param id identificador del documento
     * @return LiveData<Resource<T>> con el resultado (carga, éxito o error)
     */
    @Override
    public LiveData<Resource<T>> getById(String id) {
        MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(id)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()){
                        T model = snapshot.toObject(getModelClass());
                        result.setValue(Resource.success(model));
                    } else {
                        // Documento no encontrado, se devuelve null pero estado éxito
                        result.setValue(Resource.success(null));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Obtiene todos los documentos de la colección Firestore como una lista de modelos T.
     * <p>
     * Retorna un LiveData con Resource que refleja los estados de carga, éxito o error.
     * </p>
     *
     * @return LiveData<Resource<List<T>>> con la lista de todos los documentos
     */
    @Override
    public LiveData<Resource<List<T>>> getAll() {
        MutableLiveData<Resource<List<T>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection().get()
                .addOnSuccessListener(snapshot -> {
                    List<T> items = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        T model = doc.toObject(getModelClass());
                        if (model != null) items.add(model);
                    }
                    result.setValue(Resource.success(items));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /*
    // Método create comentado - se puede implementar si se necesita crear documentos con ID automático.
    @Override
    public LiveData<Resource<Boolean>> create(T model) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document()
                .set(model)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
    */

    /**
     * Actualiza un documento existente en la colección Firestore.
     * <p>
     * Se usa el ID obtenido del modelo para localizar el documento.
     * </p>
     *
     * @param model instancia del modelo con datos actualizados
     * @return LiveData<Resource<Boolean>> indicando éxito o error
     */
    @Override
    public LiveData<Resource<Boolean>> update(T model) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String id = getDocumentId(model);
        if (id == null || id.isEmpty()) {
            result.setValue(Resource.error("ID inválido"));
            return result;
        }
        getCollection().document(id).set(model)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Elimina un documento de la colección Firestore por su ID.
     *
     * @param id identificador del documento a eliminar
     * @return LiveData<Resource<Boolean>> indicando éxito o error
     */
    @Override
    public LiveData<Resource<Boolean>> deleteById(String id) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection().document(id).delete()
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
