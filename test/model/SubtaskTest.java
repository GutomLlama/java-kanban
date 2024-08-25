package test.model;

import model.Subtask;
import static model.Status.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void testEqualitySubtasksById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", NEW);
        Subtask subtask2 = new Subtask("Subtask 1", "Description 1", NEW);
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2);
    }

    @Test
    void testInequalitySubtasksById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", NEW);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", IN_PROGRESS);
        subtask1.setId(1);
        subtask2.setId(2);
        assertNotEquals(subtask1, subtask2);
    }
}