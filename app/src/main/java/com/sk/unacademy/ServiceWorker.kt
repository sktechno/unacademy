package com.sk.unacademy

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


//
// Created by SK(Sk) on 30/05/20.
// Copyright (c) 2020 Sktech. All rights reserved.

class ServiceWorker<T>(private val name: String) {

    private val taskList: Queue<Task<T>> = LinkedList()
    private var isRunning = false;

    /**
     * TODO for enqueuing task in [taskList] and run them
     *
     * @param task is add in [taskList]
     */
    fun addTask(task: Task<T>) {
        taskList.add(task);
        runTask()

    }

    /**
     * TODO this method is use to run task from Task List
     *
     */
    private fun runTask() {
        while (taskList.isNotEmpty()) {
            isRunning = true
            val task = taskList.poll();
            task?.let {
                GlobalScope.launch {
                    run(it)
                }
            }
        }

    }


    /**
     * TODO this method is use to run an particular task
     *
     * @param task this is is executed
     */
    private suspend fun run(task: Task<T>) {
        val t = doInBackground(task)
        withContext(Dispatchers.Main) {
            onCompleted(task, t)
            isRunning = false
        }
    }


    /**
     * TODO to run task in background
     *
     * @param task
     * @return the result by [Task.onExecuteTask]
     */
    @WorkerThread
    private suspend fun doInBackground(task: Task<T>): T {
        Log.e("$name Back", Thread.currentThread().name)
        return task.onExecuteTask()
    }

    /**
     * TODO this method is use to send data after completing the task
     *
     * @param task
     * @param t
     */
    @MainThread
    @UiThread
    private fun onCompleted(task: Task<T>, t: T) {
        Log.e("$name Main", Thread.currentThread().name)
        task.onTaskComplete(t)
    }
}