package lab02v2MV;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    Validator<Student> studentValidator;
    Validator<Tema> temaValidator;
    Validator<Nota> notaValidator;

    StudentXMLRepository fileRepository1;
    TemaXMLRepository fileRepository2;
    NotaXMLRepository fileRepository3;

    Service service;

    private String goodEmail = "test@test.com";
    private String goodProfesor = "Profesor";
    private int goodGroup = 936;

    private void buildEmptyXmls() throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("Enititati");
        doc.appendChild(rootElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("studenti_test.xml"));
        StreamResult result2 = new StreamResult(new File("teme_test.xml"));
        StreamResult result3 = new StreamResult(new File("note_test.xml"));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
        transformer.transform(source, result2);
        transformer.transform(source, result3);
    }

    @Before
    public void setUp()
    {
        try {
            buildEmptyXmls();
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();
        fileRepository1 = new StudentXMLRepository(studentValidator, "studenti_test.xml");
        fileRepository2 = new TemaXMLRepository(temaValidator, "teme_test.xml");
        fileRepository3 = new NotaXMLRepository(notaValidator, "note_test.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addStudentTest() {
        service.saveStudent("1","test",goodGroup,goodProfesor,goodEmail);
        boolean found = false;
        for (Student s:
             service.findAllStudents()) {
            if(s.getID().equals("1"))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void addStudentTest_validGroup() {
        service.saveStudent("1","test",935,goodProfesor,goodEmail);
        boolean found = false;
        for (Student s:
                service.findAllStudents()) {
            if(s.getID().equals("1"))
            {
                if(s.getGrupa()==935)
                {
                    found = true;
                }
            }
        }
        assertTrue(found);
    }

    @Test
    public void addStudentTest_invalidGroup() {
        try {
            service.saveStudent("1", "test", 0, goodProfesor, goodEmail);
            fail("student added with wrong group");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }
        try {
            service.saveStudent("1", "test", -1, goodProfesor, goodEmail);
            fail("student added with wrong group");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }
    }

    @Test
    public void addStudentTest_validEmail()
    {
        service.saveStudent("1","test",goodGroup,goodProfesor,goodEmail);
        boolean found = false;
        for (Student s:
                service.findAllStudents()) {
            if(s.getID().equals("1"))
            {
                if(s.getEmail().equals(goodEmail))
                {
                    found = true;
                }
            }
        }
        assertTrue(found);
    }
    @Test
    public void addStudentTest_invalidEmail()
    {
        try {
            service.saveStudent("1", "test", goodGroup, goodProfesor, "aaa");
            fail("student added with wrong email");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }
        try {
            service.saveStudent("1", "test", goodGroup, goodProfesor, "");
            fail("student added with empty email");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }
    }
    @Test
    public void addStudentTest_validProfesor()
    {
        service.saveStudent("1","test",goodGroup,goodProfesor,goodEmail);
        boolean found = false;
        for (Student s:
                service.findAllStudents()) {
            if(s.getID().equals("1"))
            {
                if(s.getProfesor().equals(goodProfesor))
                {
                    found = true;
                }
            }
        }
        assertTrue(found);
    }
    @Test
    public void addStudentTest_invalidProfesor()
    {
        try {
            service.saveStudent("1", "test", goodGroup, "", goodEmail);
            fail("student added with empty profesor");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }

    }





}
