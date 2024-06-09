package ts.myapp.models.shares;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.models.conversations.Conversation;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shares")
public class Share {
        @Id
        @Column(name="id")
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private int id;

        @Column(name="slug")
        private String slug;

        @Column(name="show_name")
        private boolean showName;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "conv_id")
        private Conversation conversation;

        @Column(name="date")
        private LocalDateTime date;

        @Column(name="expire_date")
        private LocalDateTime expireDate;

//        hidden-relations
        @OneToMany(mappedBy = "share", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<SharesUsersPivot> users;

        public Share(String slug, boolean showName, Conversation conversation, LocalDateTime date, LocalDateTime expireDate) {
                this.slug = slug;
                this.showName = showName;
                this.conversation = conversation;
                this.date = date;
                this.expireDate = expireDate;
        }
}
