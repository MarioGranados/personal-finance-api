import jakarta.annotation.Generated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String budgetName;

     @ManyToOne(fetch = Fetch.Type.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
}
