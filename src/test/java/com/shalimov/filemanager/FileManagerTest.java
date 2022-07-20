package com.shalimov.filemanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.shalimov.filemanager.FileManager.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileManagerTest {
    FileManager fileManager = new FileManager();

    @Before
    public void before() throws IOException {
        new File("testDirectory").mkdir();
        new File("testDirectory/src").mkdir();
        new File("testDirectory/src/dir1").mkdir();
        new File("testDirectory/src/dir1/dir2").mkdir();
        new File("testDirectory2").mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream("testDirectory/src/dir1/dir2/file1.txt");
        fileOutputStream.write("Hello world".getBytes());
        fileOutputStream.close();
    }

    @After
    public void after() {
        clean(new File("testDirectory"));
        clean(new File("testDirectory2"));
    }

    @Test
    public void whenCountFiles_thenQuantityOfFilesReturned() {
        int expectedCount = 1;
        int actualCount = countFiles("testDirectory/src/dir1/dir2");
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void whenCountFilesInEmptyDir_thenQuantityOfFilesIsZero() {
        int expectedCount = 0;
        int actualCount = countFiles("testDirectory2");
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountFilesThrowNullPointExceptionWhenCountNotExist() {
        assertThrows(NullPointerException.class, () -> {
            countFiles("notExistingDir");
        });
    }

    @Test
    public void whenCountNotExistingDirs_thenNullPointerExceptionReturned() {
        assertThrows(NullPointerException.class, () -> {
            countDirs("notExistingDir");
        });
    }

    @Test
    public void whenCountDirs_thenQuantityOfDirsReturned() {
        int expectedCount = 3;
        int actualCount = countDirs("testDirectory");
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void whenCopyFile_thenQuantityOfFiles_InDirectionDirIncreased() {
        String from = "testDirectory/src/dir1/dir2";
        String to = "testDirectory2";
        assertEquals(1, countFiles(from));
        assertEquals(0, countFiles(to));
        copy(from, to);
        assertEquals(1, countFiles(from));
        assertEquals(1, countFiles(to));
    }

    @Test
    public void whenMoveFile_thenQuantityOfFiles_InDirectionDir_becomeOneMore() {
        File dir1 = new File("testDirectory/src/dir1/dir2");
        File dir2 = new File("testDirectory2");
        assertEquals(0, countFiles(dir2.getAbsolutePath()));
        move(dir1.getPath(), dir2.getPath());
        assertEquals(1, countFiles(dir2.getAbsolutePath()));
    }
}
