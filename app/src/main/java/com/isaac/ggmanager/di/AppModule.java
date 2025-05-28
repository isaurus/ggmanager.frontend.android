package com.isaac.ggmanager.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.data.remote.FirestoreTeamRepositoryImpl;
import com.isaac.ggmanager.data.remote.FirestoreUserRepositoryImpl;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.data.remote.FirebaseAuthRepositoryImpl;
import com.isaac.ggmanager.domain.usecase.home.CheckUserHasTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.AssignTeamToUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithEmailUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;
import com.isaac.ggmanager.domain.usecase.login.RegisterWithEmailUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Módulo de Dagger Hilt que proporciona las dependencias necesarias
 * para la autenticación con Firebase y los casos de uso asociados.
 */
@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    /**
     * Proporciona una instancia singleton de {@link FirebaseAuth}.
     *
     * @return Instancia de FirebaseAuth
     */
    @Provides
    @Singleton
    public static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    /**
     * Proporciona una implementación de {@link FirebaseAuthRepository}.
     *
     * @param firebaseAuth Instancia de FirebaseAuth
     * @return Implementación de FirebaseAuthRepository
     */
    @Provides
    @Singleton
    public static FirebaseAuthRepository provideFirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        return new FirebaseAuthRepositoryImpl(firebaseAuth);
    }

    /**
     * Proporciona el caso de uso para iniciar sesión con email y contraseña.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de LoginWithEmailUseCase
     */
    @Provides
    @Singleton
    public static LoginWithEmailUseCase provideLoginWithEmailUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new LoginWithEmailUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para iniciar sesión con una cuenta de Google.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de LoginWithGoogleUseCase
     */
    @Provides
    @Singleton
    public static LoginWithGoogleUseCase provideLoginWithGoogleUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new LoginWithGoogleUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para registrar un usuario con email y contraseña.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de RegisterWithEmailUseCase
     */
    @Provides
    @Singleton
    public static RegisterWithEmailUseCase provideRegisterWithEmailUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new RegisterWithEmailUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para comprobar si un usuario ya está logeado para saltarse la fase de login.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de CheckAuthenticatedUseCase
     */
    @Provides
    @Singleton
    public static CheckAuthenticatedUserUseCase provideCheckAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository){
        return new CheckAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static SignOutUseCase provideSignoutUseCase(FirebaseAuthRepository firebaseAuthRepository){
        return new SignOutUseCase(firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static GetAuthenticatedUserUseCase provideGetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository){
        return new GetAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    //-----------------------------------------//
    // DEPENDENCIAS PARA FIRESTORE (¿CREAR OTRA CLASE PARA SEPARAR RESPONSABILIDADES?)

    @Provides
    @Singleton
    public static FirebaseFirestore provideFirebaseFirestore(){
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    public static FirestoreUserRepository provideFirestoreUserRepository(FirebaseFirestore firestore, FirebaseAuthRepository firebaseAuthRepository){
        return new FirestoreUserRepositoryImpl(firestore, firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static FirestoreTeamRepository provideFirestoreTeamRepository(FirebaseFirestore firestore, FirebaseAuthRepository firebaseAuthRepository){
        return new FirestoreTeamRepositoryImpl(firestore, firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static GetCurrentUserUseCase provideGetCurrentUserUseCase(FirestoreUserRepository firestoreUserRepository){
        return new GetCurrentUserUseCase(firestoreUserRepository);
    }

    @Provides
    @Singleton
    public static CheckUserHasTeamUseCase provideCheckUserHasTeamUseCase(FirestoreUserRepository firestoreUserRepository){
        return new CheckUserHasTeamUseCase(firestoreUserRepository);
    }

    @Provides
    @Singleton
    public static CreateTeamUseCase provideCreateTeamUseCase(FirestoreTeamRepository firestoreTeamRepository){
        return new CreateTeamUseCase(firestoreTeamRepository);
    }

    @Provides
    @Singleton
    public static AssignTeamToUserUseCase provideAssignTeamToUserUseCase(FirestoreUserRepository firestoreUserRepository){
        return new AssignTeamToUserUseCase(firestoreUserRepository);
    }
}
