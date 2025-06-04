package com.isaac.ggmanager.authtest;

import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckAuthenticatedUserUseCaseTest {

    @Mock
    private FirebaseAuthRepository firebaseAuthRepository;

    private CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        checkAuthenticatedUserUseCase = new CheckAuthenticatedUserUseCase(firebaseAuthRepository);
    }

    @Test
    public void execute_whenUserIsAuthenticated_shouldReturnTrue() {
        // Arrange
        when(firebaseAuthRepository.isUserAuthenticated()).thenReturn(true);

        // Act
        boolean result = checkAuthenticatedUserUseCase.execute();

        // Assert
        assertTrue(result);
        verify(firebaseAuthRepository).isUserAuthenticated();
    }

    @Test
    public void execute_whenUserIsNotAuthenticated_shouldReturnFalse() {
        // Arrange
        when(firebaseAuthRepository.isUserAuthenticated()).thenReturn(false);

        // Act
        boolean result = checkAuthenticatedUserUseCase.execute();

        // Assert
        assertFalse(result);
        verify(firebaseAuthRepository).isUserAuthenticated();
    }
}
