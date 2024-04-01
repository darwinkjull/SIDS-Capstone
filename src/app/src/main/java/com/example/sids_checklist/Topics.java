package com.example.sids_checklist;

/**
 * The Topics class represents topics related to safe baby sleeping practices.
 * It provides methods to initialize lists of topics.
 */
public class Topics {
    private final String title;
    private final int image;

    /**
     * Constructs a Topics object with the given title and image.
     *
     * @param title The title of the topic.
     * @param image The image resource ID associated with the topic.
     */
    public Topics(String title, int image) {
        this.title = title;
        this.image = image;
    }


    /**
     * Retrieves the title of the topic.
     *
     * @return The title of the topic.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the image resource ID associated with the topic.
     *
     * @return The image resource ID associated with the topic.
     */
    public int getImage() {
        return image;
    }
}
