package ts.myapp.models.table1;

import jakarta.persistence.*;

@Entity
@Table(name="table1")
public class Table1 {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    public Table1(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
