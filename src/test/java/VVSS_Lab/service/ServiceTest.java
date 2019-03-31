package VVSS_Lab.service;

import VVSS_Lab.console.UI;
import VVSS_Lab.domain.Nota;
import VVSS_Lab.domain.Student;
import VVSS_Lab.domain.Tema;
import VVSS_Lab.repository.NotaXMLRepository;
import VVSS_Lab.repository.StudentXMLRepository;
import VVSS_Lab.repository.TemaXMLRepository;
import VVSS_Lab.validation.NotaValidator;
import VVSS_Lab.validation.StudentValidator;
import VVSS_Lab.validation.TemaValidator;
import VVSS_Lab.validation.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTest {

    @Test
    public void saveStudent1() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        boolean find = false;
        service.saveStudent("999", "John Doe", 931);
        for(Student s : service.findAllStudents())
        {
            if(s.getNume().equals("John Doe"))
                find = true;
        }
        assertEquals(true,find);
    }
    @Test
    public void saveStudent2() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

        boolean find = false;
        for(Student s : service.findAllStudents())
        {
            if(s.getNume().equals("Doe"))
                find = true;
        }

        assertEquals(false,find);
    }
}