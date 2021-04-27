package com.khangse616.crawldatazanado.models.DTO;

public class ImageDTO {
    private String title;
    private String link;

    public ImageDTO() {
    }

    public ImageDTO(String title, String link){
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
