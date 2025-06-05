package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.DeleteUserUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeleteUserUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private DeleteUserUseCase deleteUserUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    @Test
    public void execute_shouldReturnSuccess_whenUserDeleted() {
        // Arrange
        String userId = "user123";
        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(userRepository.deleteById(userId)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = deleteUserUseCase.execute(userId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
