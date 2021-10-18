import enitity.Family;
import enums.Gender;
import enums.Mode;
import manager.FileManager;
import manager.RelationshipManager;
import manager.relations.RelationExecutorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import util.Constants;
import util.OutputPrinter;

import java.io.File;
import java.util.List;

import static org.mockito.Mockito.*;

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

        List<String> initCommands = Constants.iniitCommands;
        for(String command: initCommands) {
            fileManager.processInitCommand(command);
        }
        reset(outputPrinter); // clears the output for default file
    }

    @Test
    public void testAddChildCommandSuccess() {
        relationshipManager.addChildThroughMother("Dritha", "Vimal", Gender.MALE, Mode.INPUT);
        verify(outputPrinter, times(1)).childAdditionSucceeded();
        reset(outputPrinter);
    }

    @Test
    public void testAddChildCommandFailureForWrongGender() {
        relationshipManager.addChildThroughMother("King", "Vimal", Gender.MALE, Mode.INPUT);
        verify(outputPrinter, times(1)).personNotFound();
        reset(outputPrinter);
    }

    @Test
    public void testAddChildCommandFailureForInvalidPerson() {
        relationshipManager.addChildThroughMother("unknown", "Vimal", Gender.MALE, Mode.INPUT);
        verify(outputPrinter, times(1)).personNotFound();
        reset(outputPrinter);
    }

    @Test
    public void testAddSpouseFailureForInvalidPerson() {
        relationshipManager.addSpouse("unknown", "Vimal", Gender.MALE, Mode.INPUT);
        verify(outputPrinter, times(1)).personNotFound();
        reset(outputPrinter);
    }


    @Test
    public void testAddSpouseSuccess() {
        relationshipManager.addSpouse("Yodhan", "Yodhi", Gender.FEMALE, Mode.INPUT);
        verify(outputPrinter, never()).personNotFound();
        reset(outputPrinter);
    }

    @Test
    public void testSonSuccess() {
        relationshipManager.getRelatedMembers("Chit", "Son");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testDaughterSuccess() {
        relationshipManager.getRelatedMembers("Vich", "Daughter");
        verify(outputPrinter, times(1)).printMembers(anyList());
    }

    @Test
    public void testSiblings() {
        relationshipManager.getRelatedMembers("Yodhan", "Siblings");
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
        relationshipManager.getRelatedMembers("Yodhan", "Paternal-Uncle");
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
