package com.isaac.ggmanager.usertest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CreateUserUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private UserRepository userRepository;
    private FirebaseAuthRepository authRepository;
    private FirebaseUser mockFirebaseUser;

    private CreateUserUseCase createUserUseCase;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authRepository = mock(FirebaseAuthRepository.class);
        mockFirebaseUser = mock(FirebaseUser.class);

        when(authRepository.getAuthenticatedUser()).thenReturn(mockFirebaseUser);
        createUserUseCase = new CreateUserUseCase(userRepository, authRepository);
    }

    @Test
    public void execute_shouldSetUserIdAndEmailAndCallRepository() {
        // Given
        String uid = "123ABC";
        String email = "test@example.com";
        UserModel inputUser = new UserModel();

        when(mockFirebaseUser.getUid()).thenReturn(uid);
        when(mockFirebaseUser.getEmail()).thenReturn(email);

        MutableLiveData<Resource<Boolean>> expectedLiveData = new MutableLiveData<>();
        expectedLiveData.setValue(Resource.success(true));

        when(userRepository.create(any(UserModel.class), eq(uid))).thenReturn(expectedLiveData);

        // When
        LiveData<Resource<Boolean>> result = createUserUseCase.execute(inputUser);

        // Then
        verify(userRepository).create(inputUser, uid);
        assertEquals(uid, inputUser.getFirebaseUid());
        assertEquals(email, inputUser.getEmail());
        assertEquals(expectedLiveData, result);
    }
}