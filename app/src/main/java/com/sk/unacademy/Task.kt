package com.sk.unacademy


//
// Created by SK(Sk) on 30/05/20.
// Copyright (c) 2020 Sktech. All rights reserved.

interface Task<T> {
    fun onExecuteTask(): T
    fun onTaskComplete(result:T)
}