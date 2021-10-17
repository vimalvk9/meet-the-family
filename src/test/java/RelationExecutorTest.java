import enitity.Family;
import enums.Gender;
import manager.FileManager;
import manager.RelationshipManager;
import manager.relations.RelationExecutorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import util.OutputPrinter;

import java.io.File;

import static org.mockito.Mockito.*;
import static util.Constants.INIT_FILE_PATH;

@RunWith(MockitoJUnitRunner.class)
public class RelationExecutorTest {

    @Mock
    private OutputPrinter outputPrinter;

    private Family family;
    private RelationExecutorFactory relationshipExecutorFactory;
    private RelationshipManager relationshipManager;
    private FileManager fileManager;

    @Before
    public void setUp() {

        family = new Family();
        outputPrinter = mock(OutputPrinter.class);
        relationshipExecutorFactory = new RelationExecutorFactory(family, outputPrinter);
        relationshipManager = new RelationshipManager(family, relationshipExecutorFactory, outputPrinter);
        fileManager = new FileManager(relationshipManager, outputPrinter);

        fileManager.processInputFile(new File(INIT_FILE_PATH), true);
        reset(outputPrinter); // clears the output for default file
    }

    @Test
    public void testAddChildCommandSuccess() {
        relationshipManager.addChildThroughMother("Queen", "Vimal", Gender.MALE);
        verify(outputPrinter, times(1)).childAdditionSucceeded();
        reset(outputPrinter);
    }

    @Test
    public void testAddChildCommandFailureForWrongGender() {
        relationshipManager.addChildThroughMother("King", "Vimal", Gender.MALE);
        verify(outputPrinter, times(1)).childAdditionFailed();
        reset(outputPrinter);
    }

    @Test
    public void testAddChildCommandFailureForInvalidPerson() {
        relationshipManager.addChildThroughMother("unknown", "Vimal", Gender.MALE);
        verify(outputPrinter, times(1)).childAdditionFailed();
        reset(outputPrinter);
    }

    @Test
    public void testAddSpouseFailureForInvalidPerson() {
        relationshipManager.addSpouse("unknown", "Vimal", Gender.MALE);
        verify(outputPrinter, times(1)).personNotFound();
        reset(outputPrinter);
    }


    @Test
    public void testAddSpouseSuccess() {
        relationshipManager.addSpouse("Yodhan", "Yodhi", Gender.FEMALE);
        verify(outputPrinter, never()).personNotFound();
        reset(outputPrinter);
    }

    @Test
    public void testSonSuccess() {
        relationshipManager.getRelatedMembers("King", "Son");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testDaughterSuccess() {
        relationshipManager.getRelatedMembers("King", "Daughter");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testSiblings() {
        relationshipManager.getRelatedMembers("King", "Siblings");
        verify(outputPrinter, times(1)).noRelatedMembersFound();
    }

    @Test
    public void testMaternalUncle() {
        relationshipManager.getRelatedMembers("Asva", "Maternal-Uncle");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testMaternalUncleFailure() {
        relationshipManager.getRelatedMembers("King", "Maternal-Uncle");
        verify(outputPrinter, never()).printMembers(anyList());
    }

    @Test
    public void testPaternalUncle() {
        relationshipManager.getRelatedMembers("King", "Paternal-Uncle");
        verify(outputPrinter, times(1)).noRelatedMembersFound();
    }

    @Test
    public void testMaternalAunt() {
        relationshipManager.getRelatedMembers("Yodhan", "Maternal-Aunt");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }
    @Test
    public void testPaternalAunt() {
        relationshipManager.getRelatedMembers("Tritha", "Paternal-Aunt");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testSisterInLaw() {
        relationshipManager.getRelatedMembers("Satvy", "Sister-In-Law");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testBrotherInLaw() {
        relationshipManager.getRelatedMembers("Lika", "Brother-In-Law");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

}
