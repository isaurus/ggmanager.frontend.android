package com.isaac.ggmanager.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.data.repository.task.TaskRepositoryImpl;
import com.isaac.ggmanager.data.repository.team.TeamRepositoryImpl;
import com.isaac.ggmanager.data.repository.user.UserRepositoryImpl;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.data.repository.auth.FirebaseAuthRepositoryImpl;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.DeleteTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.GetAllTeamsUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.GetTeamByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.RemoveUserFromTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.UpdateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.task.DeleteTaskByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.task.GetUserTasksUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.DeleteUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetAllUsersUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByEmailUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUsersByTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateAdminTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Módulo de Dagger Hilt responsable de proveer todas las dependencias necesarias
 * para la aplicación, incluyendo Firebase Auth, Firestore y los casos de uso
 * relacionados con usuarios, equipos y tareas.
 *
 * <p>Este módulo centraliza la configuración y creación de instancias singleton
 * que se inyectarán a lo largo de la aplicación para mantener una arquitectura
 * limpia, desacoplada y testable.</p>
 */
@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    //-----------------------------------------//
    //----DEPENDENCIAS PARA FIREBASE AUTH------//

    /**
     * Proporciona la instancia singleton de {@link FirebaseAuth} para autenticación.
     *
     * @return Instancia única de FirebaseAuth
     */
    @Provides
    @Singleton
    public static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    /**
     * Proporciona la implementación concreta de {@link FirebaseAuthRepository}
     * que utiliza FirebaseAuth para gestionar la autenticación.
     *
     * @param firebaseAuth Instancia de FirebaseAuth inyectada
     * @return Implementación de FirebaseAuthRepository
     */
    @Provides
    @Singleton
    public static FirebaseAuthRepository provideFirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        return new FirebaseAuthRepositoryImpl(firebaseAuth);
    }

    /**
     * Proporciona el caso de uso para iniciar sesión con Google.
     *
     * @param firebaseAuthRepository Repositorio de autenticación Firebase
     * @return Caso de uso LoginWithGoogleUseCase
     */
    @Provides
    @Singleton
    public static LoginWithGoogleUseCase provideLoginWithGoogleUseCase(
            FirebaseAuthRepository firebaseAuthRepository
    ) {
        return new LoginWithGoogleUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para verificar si ya existe un usuario autenticado,
     * permitiendo saltar la pantalla de login si es así.
     *
     * @param firebaseAuthRepository Repositorio de autenticación Firebase
     * @return Caso de uso CheckAuthenticatedUserUseCase
     */
    @Provides
    @Singleton
    public static CheckAuthenticatedUserUseCase provideCheckAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new CheckAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para cerrar sesión del usuario autenticado.
     *
     * @param firebaseAuthRepository Repositorio de autenticación Firebase
     * @return Caso de uso SignOutUseCase
     */
    @Provides
    @Singleton
    public static SignOutUseCase provideSignoutUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new SignOutUseCase(firebaseAuthRepository);
    }

    /**
     * Proporciona el caso de uso para obtener información del usuario actualmente autenticado.
     *
     * @param firebaseAuthRepository Repositorio de autenticación Firebase
     * @return Caso de uso GetAuthenticatedUserUseCase
     */
    @Provides
    @Singleton
    public static GetAuthenticatedUserUseCase provideGetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        return new GetAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    //-----------------------------------------//
    //----DEPENDENCIAS PARA FIRESTORE DATABASE------//

    /**
     * Proporciona la instancia singleton de {@link FirebaseFirestore}
     * para acceso a la base de datos Firestore.
     *
     * @return Instancia única de FirebaseFirestore
     */
    @Provides
    @Singleton
    public static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    /**
     * Proporciona la implementación concreta de {@link UserRepository}
     * para operaciones CRUD y consultas sobre usuarios.
     *
     * @param firestore Instancia de FirebaseFirestore inyectada
     * @return Implementación de UserRepository
     */
    @Provides
    @Singleton
    public static UserRepository provideUserRepository(FirebaseFirestore firestore) {
        return new UserRepositoryImpl(firestore);
    }

    /**
     * Proporciona la implementación concreta de {@link TeamRepository}
     * para operaciones CRUD y consultas sobre equipos.
     *
     * @param firestore Instancia de FirebaseFirestore inyectada
     * @return Implementación de TeamRepository
     */
    @Provides
    @Singleton
    public static TeamRepository provideTeamRepository(FirebaseFirestore firestore) {
        return new TeamRepositoryImpl(firestore);
    }

    /**
     * Proporciona la implementación concreta de {@link TaskRepository}
     * para operaciones CRUD y consultas sobre tareas.
     *
     * @param firestore Instancia de FirebaseFirestore inyectada
     * @return Implementación de TaskRepository
     */
    @Provides
    @Singleton
    public static TaskRepository provideTaskRepository(FirebaseFirestore firestore){
        return new TaskRepositoryImpl(firestore);
    }

    //--------------------------------------//
    //-------CASOS DE USO - TEAM------------//

    /**
     * Proporciona el caso de uso para añadir un usuario a un equipo.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso AddUserToTeamUseCase
     */
    @Provides
    @Singleton
    public static AddUserToTeamUseCase provideAddUserToTeamUseCase(TeamRepository teamRepository) {
        return new AddUserToTeamUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para crear un nuevo equipo.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso CreateTeamUseCase
     */
    @Provides
    @Singleton
    public static CreateTeamUseCase provideCreateTeamUseCase(TeamRepository teamRepository) {
        return new CreateTeamUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para eliminar un equipo.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso DeleteTeamUseCase
     */
    @Provides
    @Singleton
    public static DeleteTeamUseCase provideDeleteTeamUseCase(TeamRepository teamRepository) {
        return new DeleteTeamUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para obtener todos los equipos.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso GetAllTeamsUseCase
     */
    @Provides
    @Singleton
    public static GetAllTeamsUseCase provideGetAllTeamsUseCase(TeamRepository teamRepository) {
        return new GetAllTeamsUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para obtener un equipo por su ID.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso GetTeamByIdUseCase
     */
    @Provides
    @Singleton
    public static GetTeamByIdUseCase provideGetTeamByIdUseCase(TeamRepository teamRepository) {
        return new GetTeamByIdUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para eliminar a un usuario de un equipo.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso RemoveUserFromTeamUseCase
     */
    @Provides
    @Singleton
    public static RemoveUserFromTeamUseCase provideRemoveUserFromTeamUseCase(TeamRepository teamRepository) {
        return new RemoveUserFromTeamUseCase(teamRepository);
    }

    /**
     * Proporciona el caso de uso para actualizar la información de un equipo.
     *
     * @param teamRepository Repositorio para gestión de equipos
     * @return Caso de uso UpdateTeamUseCase
     */
    @Provides
    @Singleton
    public static UpdateTeamUseCase provideUpdateTeamUseCase(TeamRepository teamRepository) {
        return new UpdateTeamUseCase(teamRepository);
    }

    //------------------------------------------//
    //-----------CASOS DE USO - USER------------//

    /**
     * Proporciona el caso de uso para crear un nuevo usuario.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso CreateUserUseCase
     */
    @Provides
    @Singleton
    public static CreateUserUseCase provideCreateUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para eliminar un usuario.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso DeleteUserUseCase
     */
    @Provides
    @Singleton
    public static DeleteUserUseCase provideDeleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para obtener todos los usuarios.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso GetAllUsersUseCase
     */
    @Provides
    @Singleton
    public static GetAllUsersUseCase provideGetAllUsersUseCase(UserRepository userRepository) {
        return new GetAllUsersUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para obtener el usuario actualmente autenticado.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso GetCurrentUserUseCase
     */
    @Provides
    @Singleton
    public static GetCurrentUserUseCase provideGetCurrentUserUseCase(UserRepository userRepository) {
        return new GetCurrentUserUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para obtener un usuario por su email.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso GetUserByEmailUseCase
     */
    @Provides
    @Singleton
    public static GetUserByEmailUseCase provideGetUserByEmailUseCase(UserRepository userRepository) {
        return new GetUserByEmailUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para obtener un usuario por su ID.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso GetUserByIdUseCase
     */
    @Provides
    @Singleton
    public static GetUserByIdUseCase provideGetUserByIdUseCase(UserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para obtener todos los usuarios de un equipo.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso GetUsersByTeamUseCase
     */
    @Provides
    @Singleton
    public static GetUsersByTeamUseCase provideGetUsersByTeamUseCase(UserRepository userRepository) {
        return new GetUsersByTeamUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para actualizar los permisos de administrador de un usuario en un equipo.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso UpdateAdminTeamUseCase
     */
    @Provides
    @Singleton
    public static UpdateAdminTeamUseCase provideUpdateAdminTeamUseCase(UserRepository userRepository) {
        return new UpdateAdminTeamUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para actualizar la información de un usuario.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso UpdateUserUseCase
     */
    @Provides
    @Singleton
    public static UpdateUserUseCase provideUpdateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCase(userRepository);
    }

    /**
     * Proporciona el caso de uso para actualizar el equipo asignado a un usuario.
     *
     * @param userRepository Repositorio para gestión de usuarios
     * @return Caso de uso UpdateUserTeamUseCase
     */
    @Provides
    @Singleton
    public static UpdateUserTeamUseCase provideUpdateUserTeamUseCase(UserRepository userRepository) {
        return new UpdateUserTeamUseCase(userRepository);
    }

    //------------------------------------------//
    //---------CASOS DE USO - TASK---------------//

    /**
     * Proporciona el caso de uso para eliminar una tarea por su ID.
     *
     * @param taskRepository Repositorio para gestión de tareas
     * @return Caso de uso DeleteTaskByIdUseCase
     */
    @Provides
    @Singleton
    public static DeleteTaskByIdUseCase provideDeleteTaskByIdUseCase(TaskRepository taskRepository) {
        return new DeleteTaskByIdUseCase(taskRepository);
    }

    /**
     * Proporciona el caso de uso para obtener las tareas asignadas a un usuario.
     *
     * @param taskRepository Repositorio para gestión de tareas
     * @return Caso de uso GetUserTasksUseCase
     */
    @Provides
    @Singleton
    public static GetUserTasksUseCase provideGetUserTasksUseCase(TaskRepository taskRepository) {
        return new GetUserTasksUseCase(taskRepository);
    }
}
