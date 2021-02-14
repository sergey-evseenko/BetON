package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Page {
    @Expose
    int id;
    @Expose
    String name;
    @Expose
    String title;
    @Expose
    List<ChildrenPage> children;
}
