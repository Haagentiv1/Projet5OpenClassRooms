package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    //FOR DATA
    private TodocDataBase dataBase;

    private static long projectId = 1L;
    private Task TEST_TASK = new Task(projectId,"Test",1595945393);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{

        this.dataBase =
                Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                        TodocDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception{
        dataBase.close();
    }

    @Test
    public void getTasksWhenNoTasksInserted() throws InterruptedException {
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        Project project = new Project(1L,"ProjectTest",0xFFEADAD1);
        this.dataBase.projectDao().createProject(project);
        this.dataBase.taskDao().insertTask(TEST_TASK);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(1, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException{
        Project project = new Project(1L,"ProjectTest",0xFFEADAD1);
        this.dataBase.projectDao().createProject(project);
        this.dataBase.taskDao().insertTask(TEST_TASK);
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(1,tasks.size());
        this.dataBase.taskDao().deleteTask(tasks.get(0));
        List<Task> tasks1 = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        assertEquals(0,tasks1.size());




    }
}
