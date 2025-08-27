
class Task {
    private final String name;
    private boolean isDone = false;

    Task(String name) {
        this.name = name;
    }

    public void markAsDone() {
        isDone = true;
    }
    public void markAsNotDone() {
        isDone = false;
    }

    public String toFileString() {
        String isDoneMark = (isDone ? "1" : "0");
        return " | " + isDoneMark + " | " + name;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " + name : "[ ] " + name);
    }
}