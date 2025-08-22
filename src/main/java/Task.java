
class Task {
    private String name;
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

    @Override
    public String toString() {
        return (isDone ? "[X] " + name : "[ ] " + name);
    }
}