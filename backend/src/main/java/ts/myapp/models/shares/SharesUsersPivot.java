package ts.myapp.models.shares;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.models.users.User;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shares_users")
public class SharesUsersPivot {
        @Id
        @Column(name="id")
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private int id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "share_id")
        private Share share;

        @Column(name="date")
        private LocalDateTime date;

        @Column(name="expire_date")
        @Nullable
        private LocalDateTime expireDate;

        public SharesUsersPivot(User user, Share share, LocalDateTime date, LocalDateTime expireDate) {
                this.user = user;
                this.share = share;
                this.date = date;
                this.expireDate = expireDate;
        }

        public SharesUsersPivot(User user, Share share, LocalDateTime date) {
                this.user = user;
                this.share = share;
                this.date = date;
        }
}
