package ts.myapp.models.table1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Table1Repository extends JpaRepository<Table1, Long> { }