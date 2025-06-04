package com.isaac.ggmanager.domain.repository.base;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;

import java.util.List;

/**
 * Interfaz base para operaciones CRUD con Firebase Firestore
 *
 * @param <T> Tipo de la entidad/modelo de dominio.
 */
public interface FirestoreRepository<T> {

    /**
     * Obtiene una entidad por su ID.
     *
     * @param id Identificador del documento en Firestore (String)
     * @return LiveData con Resource que contiene la entidad o error
     */
    LiveData<Resource<T>> getById(String id);

    /**
     * Obtiene todas las entidades de la colección.
     *
     * @return LiveData con Resource que contiene la lista de entidades o error
     */
    LiveData<Resource<List<T>>> getAll();

    /**
     * Crea una nueva entidad en Firestore.
     * Asigna automáticamente un ID si la entidad no lo tiene.
     *
     * @param model Entidad a crear
     * @return LiveData con Resource que indica éxito o error
     */
    //LiveData<Resource<Boolean>> create(T model);

    /**
     * Actualiza una entidad existente en Firestore.
     * La entidad debe tener un ID válido.
     *
     * @param model Entidad con los cambios
     * @return LiveData con Resource que indica éxito o error
     */
    LiveData<Resource<Boolean>> update(T model);

    /**
     * Elimina una entidad de Firestore.
     *
     * @param id ID del documento a eliminar
     * @return LiveData con Resource que indica éxito o error
     */
    LiveData<Resource<Boolean>> deleteById(String id);
}
