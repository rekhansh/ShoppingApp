package rekhansh.shoppingappproject.entities;

public class Offers {
    private int Id;
    private String Title;
    private String Description;

    public Offers(){}
    public Offers(int id, String title, String description) {
        Id = id;
        Title = title;
        Description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
