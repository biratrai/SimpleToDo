package com.gooner10.simpletodo.todo;

import com.gooner10.simpletodo.model.ToDoModel;
import com.gooner10.simpletodo.model.ToDoRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * UnitTest To Check ToDoPresenterImpl's loadToDo()
 */
public class ToDoToDoPresenterImplTest {

    private ToDoPresenterImpl toDoPresenterImpl;

    @Mock
    ToDoContract.ToDoView toDoView;

    @Mock
    ToDoRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        toDoPresenterImpl = new ToDoPresenterImpl(toDoView, repository);
//        doReturn(null).when(repository).getToDoList();
    }

    @Test
    public void verifyPresenterFetchesDataFromDatabaseAndFeedsDataToView() throws Exception {
        toDoPresenterImpl.loadToDo();
        verify(repository, times(1)).getToDoList();
        verify(toDoView, times(1)).showToDoUi(anyListOf(ToDoModel.class));
    }
}