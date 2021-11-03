import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.madbrains.simpleList.ArrayIndexOutOfBoundsException;
import ru.madbrains.simpleList.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ListOperationTest {
    private Cars octavia;
    private Cars rapid;
    private Cars karoq;
    private Cars corolla;
    private Cars camry;
    private Cars superb;
    private Cars kodiaq;
    private Cars vesta;

    private SimpleList test;
    private SimpleList testNull;


    @Before
    public void setUp() throws NoEntityException {
        testNull = new ListOperation();
        test = new ListOperation();
        octavia = new Cars("Octavia", "2018", 1.7f);
        rapid = new Cars("Rapid", "2015", 0.7f);
        karoq = new Cars("Karoq", "2019", 2f);
        kodiaq = new Cars("Kodiaq", "2020", 2.4f);
        superb = new Cars("Superb", "2021", 3.1f);
        camry = new Cars("Camry", "2015", 1f);
        corolla = new Cars("Corolla", "2020", 1.5f);
        vesta = new Cars("Vesta", "2021", 1f);

        test.add(rapid);
        test.add(octavia);
        test.add(karoq);
        test.add(kodiaq);
        test.add(superb);
        test.add(camry);
        test.add(corolla);
    }


    @Test
    public void add() throws ArrayIndexOutOfBoundsException, NoEntityException {
        SimpleList test2 = new ListOperation();
        test2.add(octavia);
        test2.add(karoq);
        test2.add(rapid);
        //todo test2.add(null);


        List<Cars> actual = new ArrayList<>();
        actual.add(octavia);
        actual.add(rapid);

        Assert.assertEquals(test2.get(0).get(), actual.get(0));

        Assert.assertEquals(test2.get(2).get(), actual.get(1));
        //todo если null среди листа
        // Assert.assertNotNull(test2.get(3).get());

    }

    @Test
    public void insert() throws Exception {
        test.insert(5, camry);
        Assert.assertEquals(test.get(5).get(), camry);
    }

    @Test
    public void insert_in_begin() throws Exception {
        testNull.insert(0, corolla);
        Assert.assertEquals(testNull.get(0).get(), corolla);
    }

    @Test
    public void remove() throws Exception {
        Object buf = test.get(6).get();
        test.remove(5);
        Assert.assertEquals(6, test.size());
        Assert.assertEquals(test.get(5).get(), buf);
    }

    @Test
    public void remove_from_nullArray() throws Exception {
        System.out.println(testNull.size());
        testNull.add(camry);
        testNull.remove(0);
    }

    @Test
    public void get() throws Exception {
        for (int i = 0; i < test.size(); i++) {
            Assert.assertTrue(test.get(i) == Optional.empty() ? false : true);
        }
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(test.size(), 7);
        SimpleList test2 = new ListOperation();

        test2.add(karoq);
        test2.add(octavia);
        Assert.assertEquals(test2.size(), 2);

        test.addAll(test2);
        Assert.assertEquals(test.size(), 9);

        test.remove(3);
        Assert.assertEquals(test.size(), 8);

        test.insert(0, octavia);
        Assert.assertEquals(test.size(), 9);
    }


    @Test
    public void first() {
        Assert.assertTrue(test.first(vesta) == -1 ? true : false);
        Assert.assertEquals(test.first(camry), 5);
    }

    @Test
    public void last() {
        Assert.assertTrue(test.last(vesta) == -1 ? true : false);
        Assert.assertEquals(test.last(camry), 5);
    }

    @Test
    public void contains() {
        Assert.assertTrue(test.contains(vesta) == false ? true : false);
        Assert.assertTrue(test.contains(superb));

    }

    @Test
    public void addAll() throws NoEntityException, ArrayIndexOutOfBoundsException {
        SimpleList test2 = new ListOperation();
        test2.add(corolla);
        test2.add(octavia);
        test2.add(rapid);
        test2.add(kodiaq);
        int sizeTest2 = test2.size();
        test2.addAll(test);
        Assert.assertTrue((sizeTest2 + test.size()) == test2.size() ? true : false);
        for (int i = sizeTest2, j = 0; i < test2.size(); j++, i++) {
            Assert.assertTrue(test2.get(i).equals(test.get(j)));
        }
    }


    @Test
    public void isEmpty() {
        Assert.assertTrue((test.size() > 0 && test.isEmpty()) ? true : false);
    }

    @Test
    public void shuffle() throws NoEntityException {
        SimpleList basicTest = test;
        SimpleList shufTest = test.shuffle();
        Assert.assertTrue((shufTest.size() == test.size()) ? true : false);
        Assert.assertNotEquals(shufTest, test);
        Assert.assertEquals(test, basicTest);
    }

    @Test
    public void sortFloat() throws NoEntityException, ArrayIndexOutOfBoundsException {
        SimpleList basicTest = test;
        SimpleList sortPrice = new ListOperation();
        SimpleList sortAge = new ListOperation();
        SimpleList sortName = new ListOperation();

        sortPrice.add(rapid);
        sortPrice.add(camry);
        sortPrice.add(corolla);
        sortPrice.add(octavia);
        sortPrice.add(karoq);

        sortAge.add(camry);
        sortAge.add(rapid);
        sortAge.add(octavia);
        sortAge.add(karoq);
        sortAge.add(corolla);

        sortName.add(camry);
        sortName.add(corolla);
        sortName.add(karoq);
        sortName.add(kodiaq);
        sortName.add(octavia);


        Comparator<Cars> comparatorPrice = Comparator.comparing(obj -> obj.getPrice());
        SimpleList sortTestPrice = test.sort(comparatorPrice);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestPrice, test);


        Comparator<Cars> comparatorAge = Comparator.comparing(obj -> obj.getAge());
        SimpleList sortTestAge = test.sort(comparatorAge);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestAge, test);


        Comparator<Cars> comparatorName = Comparator.comparing(obj -> obj.getName());
        SimpleList sortTestName = test.sort(comparatorName);
        Assert.assertEquals(test, basicTest);
        Assert.assertNotEquals(sortTestName, test);


        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sortPrice.get(i), sortTestPrice.get(i));
            Assert.assertEquals(sortAge.get(i), sortTestAge.get(i));
            Assert.assertEquals(sortName.get(i), sortTestName.get(i));
        }

    }
}


