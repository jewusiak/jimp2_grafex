package grafex;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class GraphTest {

    @Test
    @DisplayName("Czytanie grafu 2x3 z pliku")
    public void ReadFromFileTest() {
        try {
            Graph g = new Graph("src/g2_na_3.graph");

            if (g.getRows() == 2 && g.getColumns() == 3 && g.getRelations().size() == 9) {
                assertTrue(true);
            } else {
                fail("Nieprawidłowo wczytane dane!");
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }

    @Test
    @DisplayName("Pobieranie niespójnych wierzchołków")
    void getIncoherent() {
        try {
            Graph sp = new Graph("src/graf_spojny.graph");
            Graph nsp = new Graph("src/graf_niespojny.graph");
            List<Integer> lsp = new ArrayList<>();
            assertTrue(sp.getIncoherent().size() == 0 && nsp.getIncoherent().size() == 1 && nsp.getIncoherent().get(0) == 89);


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Sprawdzenie spójności")
    void isCoherent() {
        try {
            Graph sp = new Graph("src/graf_spojny.graph");
            Graph nsp = new Graph("src/graf_niespojny.graph");
            assertTrue(sp.isCoherent() && !nsp.isCoherent());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Ujemna wielkość grafu")
    void invalidFilename() {
        try {
            Graph g = new Graph("src/graf_ujemny.graph");

            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException, e.getClass().getName());
        }
    }


    @Test
    @DisplayName("Zły format grafu")
    void invalidGraphFormat() {
        try {
            Graph g = new Graph("src/graf_zlyformat.graph");

            fail();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            assertTrue(e instanceof IllegalFormatFlagsException);
        }
    }

    @Test
    @DisplayName("Szukanie ścieżki")
    void findPath(){
        try {
            Graph g = new Graph("src/g2_na_3.graph");

            GraphPath gp=g.findPath(1,4);

            //Expected: 1 -> 5 -> 4 (9.067499999999999)
            assertIterableEquals( Arrays.stream((new Integer[]{1,5,4})).collect(Collectors.toList()), gp.getPoints());
            assertTrue(9.0674<gp.getTotalLength() && 9.0675>gp.getTotalLength());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Zapis do pliku")
    void saveToFile(){
        try {
            Graph g=new Graph("src/g2_na_3.graph");
            g.saveToFile("src/save-test.graph");
        }catch(Exception e){
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }

    }

}