package grafex;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class GraphTest {

    @Test
    @DisplayName("Czytanie grafu 2x3 z pliku")
    public void ReadFromFileTest(){
        try {
            Graph g = new Graph("src/g2_na_3");

            if(g.getRows()==2&&g.getColumns()==3&&g.getRelations().size()==9) {
                assertTrue(true);
            }else{
                fail("Nieprawidłowo wczytane dane!");
            }
        } catch (Exception e){
            fail(e.getMessage());
        }


    }

}