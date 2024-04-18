import com.example.firsttomcat.model.impl.DatabaseUtilImpl;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DatabaseUtilImplTest {
    private static final String FILE_NAME = "gosling.jpg";
    DatabaseUtilImpl databaseUtil = new DatabaseUtilImpl();

    @Test
    public void uploadFileTest() throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/" + FILE_NAME);
        if (inputStream != null) {
            databaseUtil.uploadFile(inputStream, FILE_NAME);
        } else {
            throw new FileNotFoundException("File " + FILE_NAME + " not found in the resources directory");
        }
    }

    @Test
    public void deleteFileByEmailTest() {
        databaseUtil.deleteFileByEmail(FILE_NAME);
    }
}
