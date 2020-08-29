package com.cleanup.todoc.injection;

import android.content.ContentValues;
import android.content.Context;

import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static TaskDataRepository provideTaskDataSource(Context context){
        TodocDataBase dataBase = TodocDataBase.getInstance(context);
        return new TaskDataRepository(dataBase.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        TodocDataBase dataBase = TodocDataBase.getInstance(context);
        return new ProjectDataRepository(dataBase.projectDao());
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
