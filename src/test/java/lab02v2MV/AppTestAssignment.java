package lab02v2MV;

import static org.junit.Assert.assertEquals;
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
import java.util.Iterator;

/**
 * Unit test for simple App.
 */
public class AppTestAssignment {

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
    public void setUp() {
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
    public void addAssignment_invalidId() {
        try {
            int result = service.saveTema("","test assignment",10,7);
            fail("the id cannot be empty string");
        }
        catch (ValidationException exception){
            assertTrue(true);
        }

    }

    @Test
    public void addAssignment() {
        int result = service.saveTema("1","test assignment",10,7);

        boolean found = false;
        for (Tema t:
                service.findAllTeme()) {
            if(t.getID().equals("1"))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void addAssignment_duplicates(){
        service.saveTema("1","Descriere",10,8);
        service.saveTema("1","Descriere",10,8);
        int i = 0;
        Iterator it=service.findAllTeme().iterator();
        while(it.hasNext()) {
            i++;
            it.next();
        }
        assertEquals(1, i);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_id_null() {
        service.saveTema(null, "Descriere", 10, 13);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_description_null() {
        service.saveTema("2", null, 10, 13);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_description_empty() {
        service.saveTema("1", "", 10, 13);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_deadline_less1(){
        service.saveTema("1", "tema", 0, 10);
    }


    @Test(expected = ValidationException.class)
    public void addAssignment_deadline_greater14(){
        service.saveTema("1", "tema", 15, 10);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_startline_less1(){
        service.saveTema("1", "tema", 10, 0);
    }


    @Test(expected = ValidationException.class)
    public void addAssignment_startline_greater14(){
        service.saveTema("1", "tema", 10, 15);
    }

    @Test(expected = ValidationException.class)
    public void addAssignment_startline_greater_deadline(){
        service.saveTema("1", "tema", 10, 12);
    }
}