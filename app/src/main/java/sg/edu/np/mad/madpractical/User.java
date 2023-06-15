package sg.edu.np.mad.madpractical;

public class User {
    String name;
    String description;
    int id;
    boolean followed;

    public String getName() {
        return name;
    }
    public void setName(String n) {
        this.name = n;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String d) {
        this.description = d;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public boolean getFollowed(){
        return followed;
    }
    public void setFollowed(boolean f){
        this.followed = f;
    }

    // constructors
    public User() {
    }
}