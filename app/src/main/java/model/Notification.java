package model;

public class Notification {
    private String title;
    private String description;
    private boolean read;

    public Notification(String title, String description, boolean read) {
        this.title = title;
        this.description = description;
        this.read = read;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
