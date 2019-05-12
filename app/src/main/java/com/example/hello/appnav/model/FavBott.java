package com.example.hello.appnav.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "fav_table")
public class FavBott extends Model {

    @Column( name = "bot_id",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE )
    private int buttonId;
    @Column( name = "bot_text",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String buttonText;

    // Default constructor
    public FavBott(){
        super();
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int bottomId) {
        this.buttonId = bottomId;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String bottomText) {
        this.buttonText = bottomText;
    }

    public static List<FavBott> getAllfav_table() {
        return new Select()
                .from(FavBott.class)
                .execute();
    }
}
//unique = true, onUniqueConflict = Column.ConflictAction.REPLACE