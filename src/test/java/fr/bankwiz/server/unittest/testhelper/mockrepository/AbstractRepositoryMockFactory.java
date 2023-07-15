package fr.bankwiz.server.unittest.testhelper.mockrepository;

import java.util.List;
import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractRepositoryMockFactory<T, R extends JpaRepository<T, ID>, ID> {

    protected final R repository;

    protected AbstractRepositoryMockFactory(Class<R> repositoryClass) {
        this.repository = Mockito.mock(repositoryClass);
    }

    public R getRepository() {
        return repository;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockFindById(ID id, T object) {
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(object));
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockFindById(ID id, Optional<T> object) {
        Mockito.when(repository.findById(id)).thenReturn(object);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockFindAll(List<T> objects) {
        Mockito.when(repository.findAll()).thenReturn(objects);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockDelete(T object) {
        Mockito.doNothing().when(repository).delete(object);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockDeleteById(ID id) {
        Mockito.doNothing().when(repository).deleteById(id);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockDeleteAll() {
        Mockito.doNothing().when(repository).deleteAll();
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> mockSave() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<T>getArgument(0);
        });
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifySaveCalled(T object) {
        verify(repository, times(1)).save(object);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifySaveCalled(T object, int timesCalled) {
        verify(repository, times(timesCalled)).save(object);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifyDeleteCalled(T object, int timesCalled) {
        verify(repository, times(timesCalled)).delete(object);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifyDeleteByIdCalled(ID id, int timesCalled) {
        verify(repository, times(timesCalled)).deleteById(id);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifyFindByIdCalled(ID id, int timesCalled) {
        verify(repository, times(timesCalled)).findById(id);
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifyFindAllCalled(int timesCalled) {
        verify(repository, times(timesCalled)).findAll();
        return this;
    }

    public AbstractRepositoryMockFactory<T, R, ID> verifyDeleteAllCalled(int timesCalled) {
        verify(repository, times(timesCalled)).deleteAll();
        return this;
    }
}
