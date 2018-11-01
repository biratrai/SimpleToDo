package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * UnitTest To Check ToDoPresenterImpl's loadToDo()
 */
public class ToDoToDoPresenterImplTest {

    @Mock
    private ToDoContract.ToDoView toDoView;

    @Mock
    private ToDoRepository repository;

    @InjectMocks
    private ToDoPresenterImpl toDoPresenterImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadToDo_whenCalled_fetchesDataFromDatabaseAndFeedsDataToView() {
        toDoPresenterImpl.loadToDo();

        verify(repository, times(1)).getToDoList();
    }

    @Test
    public void loadToDo_whenCalled_showsDataToView() {
        toDoPresenterImpl.loadToDo();

        verify(repository, times(1)).getToDoList();
        verify(toDoView, times(1))
                .showToDoUi(ArgumentMatchers.<ToDoModel>anyList());
    }

    @Test
    public void addNewToDo_whenStringReceived_savesInRepository() {
        toDoPresenterImpl.addNewToDo("My new ToDO");

        verify(repository).saveToDo(anyString());
    }

    @Test
    public void addNewToDo_whenStringReceived_updatesChangesInView() {
        toDoPresenterImpl.addNewToDo("My new ToDO");

        verify(toDoView).updateChanges();
    }

    @Test
    public void deleteToDo_whenToDoModelReceived_deletesFromRespository() {
        toDoPresenterImpl.deleteToDo(new ToDoModel());

        verify(repository).deleteToDoFromDatabase(any(ToDoModel.class));
    }

    @Test
    public void deleteToDo_whenToDoModelReceived_updatesChangesInView() {
        toDoPresenterImpl.deleteToDo(new ToDoModel());

        verify(toDoView).updateChanges();
    }
}