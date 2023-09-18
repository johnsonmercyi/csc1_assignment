package com.csci.assignment.util;

import java.util.ArrayList;

public class TaskManager {
  public static void loadTask(Runnable task) {
    Task.loadTask(task);
  }

  public static void executeTasks() {
    Task.execute();
  }

  static class Task {
    private static ArrayList<Runnable> tasks = new ArrayList<>();

    private static void loadTask(Runnable task) {
      tasks.add(task);
    }

    private static void execute() {
      if (tasks.size() > 0) {
        for (Runnable runnable : tasks) {
          try {
            runnable.run();
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else {
        System.out.println("⚠️No tasks to execute!");
      }
    }
  }  
}
