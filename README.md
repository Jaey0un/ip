# Mang User Guide

![Ui](Ui.png)

Mang is a friendly, lightweight chatbot that helps you manage your tasks from a simple command-line or JavaFX GUI
interface.  
You can add, view, search, mark, delete, and sort tasks easily.  
This guide explains how to use the main features of Mang.

## Adding deadlines

Add a task with a description and a due date.  
Dates must be in `yyyy-MM-dd` format.

Example: `deadline Submit report /by 2025-10-05`

```
expected output:
Got it. I've added this task:
[D][ ] Submit report (by: Oct 5 2025)
Now you have 3 tasks in the list.
```

## Adding todos

Add a simple task without a due date.

Example: `todo Prepare slides`

```
expected output:
Got it. I've added this task:
[T][ ] Prepare slides
Now you have 4 tasks in the list.
```

## Adding events

Add a task with a start and end time.

Example: `event Team sync /from Mon 2pm /to 3pm`

```
expected output:
Got it. I've added this task:
[E][ ] Team sync (from: Mon 2pm to: 3pm)
Now you have 5 tasks in the list.
```

## Listing tasks

View all tasks currently in your list.

Example: `list`

```
expected output:
Here are the tasks in your list:
1.[T][ ] Prepare slides
2.[D][ ] Submit report (by: Oct 5 2025)
3.[E][ ] Team sync (from: Mon 2pm to: 3pm)
```

## Marking and unmarking tasks

Mark a task as done or undone by its number in the list.

Example: `mark 2`

```
expected output:
Nice! I've marked this task as done:
[D][X] Submit report (by: Oct 5 2025)
```

## Deleting tasks

Remove a task from the list by its number.

Example: `delete 1`

```
expected output:
Noted. I've removed this task:
[T][ ] Prepare slides
Now you have 2 tasks in the list.
```

## Finding tasks

Search for tasks whose description contains a keyword.

Example: `find report`

```
expected output:
Here are the matching tasks in your list:
1.[D][ ] Submit report (by: Oct 5 2025)
```

## Sorting tasks

Sort tasks either by description or by deadline.

Example: `sort deadline`

```
expected output:
Tasks have been sorted by deadline.
Here are the tasks in your list:
1.[D][ ] Pay rent (by: Oct 1 2025)
2.[D][ ] Submit iP (by: Oct 5 2025)
```

## Exiting the program

End the session gracefully.

Example: `bye`

```
expected output:
Bye. Hope to see you again soon!
```

## Credits

Some refactoring and code improvements were done with the help of ChatGPT (AI-assisted coding).
