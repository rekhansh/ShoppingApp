package rekhansh.shoppingappproject.entities;


public class ItemObject
{
    private int Id;
    private String Title;
    private int Price;
    private String ImageUrl;
    private String Description;
    public ItemObject(){}

    public ItemObject(int id, String title, int price, String imageUrl, String description) {
        Id = id;
        Title = title;
        Price = price;
        ImageUrl = imageUrl;
        Description = description;
    }


    public int getPrice()
    {
        return Price;
    }
    public void setPrice(int price)
    {
        Price=price;
    }
    public void setImageUrl(String imageUrl)
    {
        ImageUrl = imageUrl;
    }
    public String getImageUrl()
    {
        return ImageUrl;
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
