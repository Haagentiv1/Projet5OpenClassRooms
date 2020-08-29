package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskDataRepository taskDataRepository;
    private final ProjectDataRepository projectDataRepository;
    private final Executor executor;

    //DATA
    @Nullable
    private LiveData<List<Project>> projects;


    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
        this.taskDataRepository = taskDataRepository;
        this.projectDataRepository = projectDataRepository;
        this.executor = executor;
    }

    public void init(){if (this.projects != null){return;}
    projects = projectDataRepository.getProjects();}

    // FOR PROJECT
    public LiveData<List<Project>> getProjects(){return projects;}

    //FOR TASK
    public LiveData<List<Task>> getTasks(){return this.taskDataRepository.getTasks();}

    public void createTask(Task task){
        executor.execute(() -> {taskDataRepository.createTask(task);
        });
    }

    public void deleteTask(Task task){
        executor.execute(() ->{taskDataRepository.deleteTask(task);
        });
    }

}
