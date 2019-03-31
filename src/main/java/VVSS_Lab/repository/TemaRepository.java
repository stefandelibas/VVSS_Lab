package VVSS_Lab.repository;

import VVSS_Lab.domain.Tema;
import VVSS_Lab.validation.*;

public class TemaRepository extends AbstractCRUDRepository<String, Tema> {
    public TemaRepository(Validator<Tema> validator) {
        super(validator);
    }
}

