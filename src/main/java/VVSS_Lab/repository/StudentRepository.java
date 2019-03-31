package VVSS_Lab.repository;
import VVSS_Lab.domain.Student;
import VVSS_Lab.validation.*;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}

