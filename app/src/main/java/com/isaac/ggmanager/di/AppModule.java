package com.isaac.ggmanager.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.data.repository.TeamRepositoryImpl;
import com.isaac.ggmanager.data.repository.UserRepositoryImpl;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.data.repository.FirebaseAuthRepositoryImpl;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.DeleteTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.GetAllTeamsUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.GetTeamByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.RemoveUserFromTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.UpdateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.DeleteUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetAllUsersUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByEmailUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUsersByTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.IsUserHasTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateAdminTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Módulo de Dagger Hilt que proporciona las dependencias necesarias
 * para la autenticación con Firebase y los casos de uso asociados.
 */
@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    //-----------------------------------------//
    //----DEPENDENCIAS PARA FIREBASE AUTH------//

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
     * Proporciona el caso de uso para iniciar sesión con una cuenta de Google.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de LoginWithGoogleUseCase
     */
    @Provides
    @Singleton
    public static LoginWithGoogleUseCase provideLoginWithGoogleUseCase(
            FirebaseAuthRepository firebaseAuthRepository
    ) {
        return new LoginWithGoogleUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para comprobar si un usuario ya está logeado para saltarse la fase de login.
     *
     * @param firebaseAuthRepository Repositorio de autenticación
     * @return Instancia de CheckAuthenticatedUseCase
     */
    @Provides
    @Singleton
    public static CheckAuthenticatedUserUseCase provideCheckAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new CheckAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static SignOutUseCase provideSignoutUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new SignOutUseCase(firebaseAuthRepository);
    }

    @Provides
    @Singleton
    public static GetAuthenticatedUserUseCase provideGetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new GetAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    //-----------------------------------------//
    //----DEPENDENCIAS PARA FIRESTORE DATABASE------//

    @Provides
    @Singleton
    public static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    public static UserRepository provideUserRepository(FirebaseFirestore firestore) {
        return new UserRepositoryImpl(firestore);
    }

    @Provides
    @Singleton
    public static TeamRepository provideTeamRepository(FirebaseFirestore firestore) {
        return new TeamRepositoryImpl(firestore);
    }

    // CASOS DE USO - TEAM

    @Provides
    @Singleton
    public static AddUserToTeamUseCase provideAddUserToTeamUseCase(TeamRepository teamRepository, UserRepository userRepository) {
        return new AddUserToTeamUseCase(teamRepository, userRepository);
    }

    @Provides
    @Singleton
    public static CreateTeamUseCase provideCreateTeamUseCase(TeamRepository teamRepository,
                                                             UserRepository userRepository,
                                                             FirebaseAuthRepository authRepository) {
        return new CreateTeamUseCase(teamRepository, userRepository, authRepository);
    }

    @Provides
    @Singleton
    public static DeleteTeamUseCase provideDeleteTeamUseCase(TeamRepository teamRepository) {
        return new DeleteTeamUseCase(teamRepository);
    }

    @Provides
    @Singleton
    public static GetAllTeamsUseCase provideGetAllTeamsUseCase(TeamRepository teamRepository) {
        return new GetAllTeamsUseCase(teamRepository);
    }

    @Provides
    @Singleton
    public static GetTeamByIdUseCase provideGetTeamByIdUseCase(TeamRepository teamRepository) {
        return new GetTeamByIdUseCase(teamRepository);
    }

    @Provides
    @Singleton
    public static RemoveUserFromTeamUseCase provideRemoveUserFromTeamUseCase(TeamRepository teamRepository) {
        return new RemoveUserFromTeamUseCase(teamRepository);
    }

    @Provides
    @Singleton
    public static UpdateTeamUseCase provideUpdateTeamUseCase(TeamRepository teamRepository) {
        return new UpdateTeamUseCase(teamRepository);
    }

    // CASOS DE USO - USER

    @Provides
    @Singleton
    public static CreateUserUseCase provideCreateUserUseCase(UserRepository userRepository, FirebaseAuthRepository authRepository) {
        return new CreateUserUseCase(userRepository, authRepository);
    }

    @Provides
    @Singleton
    public static DeleteUserUseCase provideDeleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static GetAllUsersUseCase provideGetallUsersUseCase(UserRepository userRepository) {
        return new GetAllUsersUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static GetUserByEmailUseCase provideGetUserByEmailUseCase(UserRepository userRepository) {
        return new GetUserByEmailUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static GetUserByIdUseCase provideGetUserByIdUseCase(UserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static GetCurrentUserUseCase provideGetCurrentUserUseCase(UserRepository userRepository, FirebaseAuthRepository authRepository){
        return new GetCurrentUserUseCase(userRepository, authRepository);
    }

    @Provides
    @Singleton
    public static GetUsersByTeamUseCase provideGetUsersByIdUseCase(UserRepository userRepository) {
        return new GetUsersByTeamUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static IsUserHasTeamUseCase provideIsUserHasTeamUseCase(UserRepository userRepository) {
        return new IsUserHasTeamUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static UpdateUserTeamUseCase provideUpdateUserTeamUseCase(UserRepository userRepository) {
        return new UpdateUserTeamUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static UpdateAdminTeamUseCase provideUpdateAdminTeamUseCase(UserRepository userRepository,
                                                                       FirebaseAuthRepository authRepository){
        return new UpdateAdminTeamUseCase(userRepository, authRepository);
    }

    @Provides
    @Singleton
    public static UpdateUserUseCase provideUpdateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCase(userRepository);
    }

}
